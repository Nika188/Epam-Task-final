package com.epam.rd.qa.collections;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SpecialDeposit extends Deposit implements Prolongable {
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

    @Override
    public boolean canToProlong() {
        return this.getAmount().compareTo(new BigDecimal(1000)) > 0;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpecialDeposit that = (SpecialDeposit) o;

        if (that.getAmount().compareTo(getAmount())!=0) return false;
        return true;
    }
    @Override
    public int hashCode() {
        int result = 17;
        long amountBits =  getAmount().longValue();
        result = 31 * result + (int) (amountBits ^ (amountBits >>> 32));
        return result;
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
