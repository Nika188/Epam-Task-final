package com.epam.rd.qa.inheritance;

import java.math.BigDecimal;

public class Employee  {
    private final String  name;
    private final BigDecimal salary;
    private BigDecimal bonus;
    public Employee(String name, BigDecimal salary) {
        if (name!=null && !name.trim().isEmpty() && salary!=null && salary.compareTo(new BigDecimal(0))>0 ){
            this.name=name;
            this.salary=salary;
        }else{
            throw new IllegalArgumentException("");
        }
    }
    public String getName() {
        return name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setBonus(BigDecimal bonus) {
        if (bonus!=null && bonus.compareTo(new BigDecimal(0))>0){
            this.bonus = bonus;
        }else{
            throw new IllegalArgumentException("");
        }
    }

    public BigDecimal toPay() {
        return this.salary.add(this.bonus);
    }
}
