package com.epam.rd.qa.inheritance;

import java.math.BigDecimal;

public class Manager extends Employee{
    private final int clientAmount;
    public Manager(String name, BigDecimal salary, int clientAmount) {
        super(name, salary);

        if (clientAmount>=0){
            this.clientAmount=clientAmount;
        }else{
            throw new IllegalArgumentException("");
        }
    }
    @Override
    public void setBonus(BigDecimal bonus) {
        if (this.clientAmount>150){
            super.setBonus(bonus.add(new BigDecimal(1000)));
        } else if (this.clientAmount>100) {
            super.setBonus(bonus.add(new BigDecimal(500)));
        }else {
            super.setBonus(bonus);
        }
    }
}
