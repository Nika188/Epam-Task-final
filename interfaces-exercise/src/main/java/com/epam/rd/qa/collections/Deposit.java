package com.epam.rd.qa.collections;

import java.math.BigDecimal;

public abstract class Deposit implements Comparable<Deposit> {
    protected final BigDecimal amount;
    protected final int period;

    protected Deposit(BigDecimal depositAmount, int depositPeriod) {
        if (depositPeriod>0 && depositAmount.compareTo(new BigDecimal(0))>0){
            this.amount = depositAmount;
            this.period = depositPeriod;
        } else {
            throw new IllegalArgumentException();
        }

    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public int getPeriod() {
        return period;
    }

    public abstract BigDecimal income();
    public BigDecimal calculateTotalAmount() {
        return this.getAmount().add(income());
    }

    @Override
    public int compareTo(Deposit other) {
        return this.calculateTotalAmount().compareTo(other.calculateTotalAmount());
    }
}

