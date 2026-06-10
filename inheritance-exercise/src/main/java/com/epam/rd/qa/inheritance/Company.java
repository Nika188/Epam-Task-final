package com.epam.rd.qa.inheritance;

import java.math.BigDecimal;

public class Company {
    private Employee[] employees;
    public Company(Employee[] employees) {
        if (employees!=null){
            this.employees=employees;
        }else{
            throw new IllegalArgumentException("");
        }
    }

    public void giveEverybodyBonus(BigDecimal companyBonus) {
        for (Employee em: this.employees){
            em.setBonus(companyBonus);
        }
    }

    public BigDecimal totalToPay() {
        BigDecimal totalPay= new BigDecimal(0);
        for (Employee em: this.employees){
            totalPay=totalPay.add(em.toPay());
        }
        return totalPay;
    }

    public String nameMaxSalary() {
        BigDecimal totalPay=new BigDecimal(0);
        String name = new String();
        for (Employee em: this.employees){
            if (totalPay.compareTo(em.toPay())<0){
                totalPay=em.toPay();
                name=em.getName();
            }
        }
        return name;
    }
}
