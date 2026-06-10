package com.epam.rd.autotasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ThreadUnionImpl implements ThreadUnion {

    private final String name;
    private final AtomicInteger counter = new AtomicInteger(0);
    private final AtomicInteger active = new AtomicInteger(0);

    private final List<Thread> threads = Collections.synchronizedList(new ArrayList<>());
    private final List<FinishedThreadResult> results = Collections.synchronizedList(new ArrayList<>());
    private final AtomicBoolean shutdown = new AtomicBoolean(false);

    ThreadUnionImpl(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable runnable) {


        if (shutdown.get()) {
            throw new IllegalStateException("ThreadUnion is shutdown");
        }

        String threadName = name + "-worker-" + counter.getAndIncrement();

        Runnable wrapped = () -> {
            active.incrementAndGet();
            Throwable thrown = null;
            try {
                runnable.run();
            } catch (Throwable t) {
                thrown = t;

            } finally {
                active.decrementAndGet();

                results.add(new FinishedThreadResult(threadName, thrown));
                synchronized (threads) {
                    threads.notifyAll();
                }
            }
        };

        Thread thread = new Thread(wrapped, threadName);
        threads.add(thread);

        return thread;
    }

    @Override
    public int totalSize() {
        return counter.get();
    }

    @Override
    public int activeSize() {
        return active.get();
    }

    @Override
    public void shutdown() {
        if (!shutdown.compareAndSet(false, true)) {
            return;
        }
        synchronized (threads) {
            for (Thread t : threads) {
                t.interrupt();
            }
        }
    }

    @Override
    public boolean isShutdown() {
        return shutdown.get();
    }

    @Override
    public void awaitTermination() {
        synchronized (threads) {
            while (true) {
                boolean allDone = threads.stream().allMatch(t -> !t.isAlive());
                if (allDone) {
                    return;
                }
                try {
                    threads.wait();
                } catch (InterruptedException e) {
                    // keep waiting
                }
            }
        }
    }

    @Override
    public boolean isFinished() {
        return isShutdown() && active.get() == 0;
    }

    @Override
    public List<FinishedThreadResult> results() {
        synchronized (results) {
            return new ArrayList<>(results);
        }
    }
}
