package com.epam.rd.autotasks.wallet;

import java.util.List;
import java.util.concurrent.locks.Lock;

public class WalletImpl implements Wallet{

    private final List<Account> accounts;
    private final PaymentLog log;

    public WalletImpl(List<Account> accounts, PaymentLog log) {
        this.accounts = accounts;
        this.log = log;
    }

    @Override
    public void pay(String recipient, long amount) throws Exception {
        for (Account account : accounts) {
            Lock lock = account.lock();
            lock.lock();
            try {
                if (account.balance() >= amount) {
                    account.pay(amount);

                    log.add(account, recipient, amount);
                    return;
                }
            } finally {
                lock.unlock();
            }
        }

        throw new ShortageOfMoneyException(recipient, amount);
    }
}
