package com.epam.rd.qa.aggregation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SpecialDeposit extends Deposit {
    public SpecialDeposit(BigDecimal depositAmount, int depositPeriod) {
        super(depositAmount, depositPeriod);
    }

    @Override
    public BigDecimal income() {
        double currentAmount = getAmount().doubleValue();
        double totalIncome = 0;
        for (int i = 1; i <= getPeriod(); i++) {
            double monthlyInterestRate = i / 100.0;
            double monthlyInterest = currentAmount * monthlyInterestRate;
            currentAmount += monthlyInterest;
            totalIncome += monthlyInterest;
        }
        return round(totalIncome);
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
