package com.epam.rd.qa.inheritance;

import java.math.BigDecimal;

public class SalesPerson extends Employee {
    private final int percent;
    public SalesPerson(String name, BigDecimal salary, int percent) {
        super(name, salary);
        if (percent>=0){
            this.percent=percent;
        }else{
            throw new IllegalArgumentException("");
        }
    }
    @Override
    public void setBonus(BigDecimal bonus) {
        if (this.percent>200){
            super.setBonus(bonus.multiply(new BigDecimal(3)));
        }else if (this.percent>100){
            super.setBonus(bonus.multiply(new BigDecimal(2)));
        }else {
            super.setBonus(bonus);
        }
    }
}
