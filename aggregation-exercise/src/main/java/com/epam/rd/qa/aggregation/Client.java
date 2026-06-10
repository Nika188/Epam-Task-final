package com.epam.rd.qa.aggregation;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Client {
    private Deposit[] deposits;
    private int depositCount;

    public Client() {
        this.deposits = new Deposit[10];
        this.depositCount = 0;
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
    private BigDecimal round(double value) {
        if (value==0){
            return new BigDecimal("0.00");
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.DOWN);
        return bd;
    }
}
