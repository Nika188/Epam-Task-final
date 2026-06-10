package com.epam.rd.qa.collections;

import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class Client implements Iterable<Deposit>{
    private Deposit[] deposits;
    private int depositCount;

    public Client() {
        this.deposits = new Deposit[10];
        this.depositCount = 0;
    }
    public Client(Deposit[] deposits) {
        if (deposits == null || deposits.length==0) {
            throw new IllegalArgumentException();
        }
        this.deposits = Arrays.copyOf(deposits, deposits.length);
        this.depositCount = 0;
        for (Deposit deposit : this.deposits) {
            if (deposit != null) {
                this.depositCount++;
            }
        }
    }

    public boolean addDeposit(Deposit deposit) {
        if (depositCount < deposits.length) {
            deposits[depositCount++] = deposit;
            return true;
        }
        return false;
    }

    public BigDecimal totalIncome() {
        double totalIncome = 0;
        for (int i = 0; i < depositCount; i++) {
            totalIncome += deposits[i].income().doubleValue();
        }
        return round(totalIncome);
    }

    public BigDecimal maxIncome() {
        if (depositCount == 0) {
            return new BigDecimal("0.00");
        }
        double maxIncome = deposits[0].income().doubleValue();
        for (int i = 1; i < depositCount; i++) {
            double currentIncome = deposits[i].income().doubleValue();
            if (currentIncome > maxIncome) {
                maxIncome = currentIncome;
            }
        }
        return round(maxIncome);
    }

    public BigDecimal getIncomeByNumber(int number) {
        if (number >= 0 && number < depositCount) {
            return round(deposits[number].income().doubleValue());
        }
        return new BigDecimal("0.00");
    }
    public void sortDeposits() {
        Arrays.sort(deposits, 0, depositCount, Comparator.reverseOrder());
    }

    public int countPossibleToProlongDeposit() {
        int count = 0;
        for (int i = 0; i < depositCount; i++) {
            if (deposits[i] instanceof Prolongable && ((Prolongable) deposits[i]).canToProlong()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Iterator<Deposit> iterator() {
        return new DepositIterator();
    }

    private class DepositIterator implements Iterator<Deposit> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < depositCount;
        }

        @Override
        public Deposit next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return deposits[currentIndex++];
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported.");
        }
    }
    private BigDecimal round(double value) {
        if (value==0){
            return new BigDecimal("0.00");
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.DOWN);
        return bd;
    }
}

