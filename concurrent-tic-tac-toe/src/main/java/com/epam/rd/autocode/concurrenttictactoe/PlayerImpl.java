package com.epam.rd.autocode.concurrenttictactoe;

import static com.epam.rd.autocode.concurrenttictactoe.TicTacToeImpl.coordinator;
import static com.epam.rd.autocode.concurrenttictactoe.TicTacToeImpl.lock;

public class PlayerImpl implements Player {

    private final TicTacToe table;
    private final char mark;
    private final PlayerStrategy strategy;


    public PlayerImpl(TicTacToe table, char mark, PlayerStrategy strategy) {
        this.table = table;
        this.mark = mark;
        this.strategy = strategy;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                while (!coordinator.finished && coordinator.turn != mark) {
                    try { lock.wait(); }
                    catch (InterruptedException ignored) {}
                }

                if (coordinator.finished)
                    return;

                Move m = strategy.computeMove(mark, table);
                if (m == null)
                    return;

                try {
                    table.setMark(m.row, m.column, mark);
                } catch (Exception e) {
                    return;
                }

                char[][] t = table.table();

                if (coordinator.checkWin(t, mark)) {
                    coordinator.finished = true;
                    lock.notifyAll();
                    return;
                }

                if (coordinator.isBoardFull(t)) {
                    coordinator.finished = true;
                    lock.notifyAll();
                    return;
                }

                coordinator.turn = (mark == 'X') ? 'O' : 'X';
                lock.notifyAll();
            }
        }
    }
}
