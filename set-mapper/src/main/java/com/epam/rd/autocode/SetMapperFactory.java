package com.epam.rd.autocode;


import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SetMapperFactory {

    public SetMapper<Set<Employee>> employeesSetMapper() {
        return resultSet -> {
            try{
                Map<BigInteger, NewEmployee> employeeTable = new HashMap<>();
                resultSet.beforeFirst();

                while (resultSet.next()) {
                    BigInteger id = BigInteger.valueOf(resultSet.getLong("id"));

                    employeeTable.put(id, new NewEmployee(
                            id,
                            new FullName(
                                    resultSet.getString("firstName"),
                                    resultSet.getString("lastName"),
                                    resultSet.getString("middleName")),
                            Position.valueOf(resultSet.getString("position")),
                            resultSet.getDate("hireDate").toLocalDate(),
                            resultSet.getBigDecimal("salary"),
                            BigInteger.valueOf(resultSet.getLong("manager"))
                    ));
                }

                Set<Employee> result= new HashSet<>();

                for (BigInteger id : employeeTable.keySet()) {
                    result.add(buildEmployee(id, employeeTable));
                }

                return result;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }
    private Employee buildEmployee(BigInteger id, Map<BigInteger, NewEmployee> employeeTable) {
        if (!employeeTable.containsKey(id)){
            return null;
        }
        NewEmployee emp = employeeTable.get(id);

        Employee manager = buildEmployee(emp.managerId, employeeTable);

        Employee employee = new Employee(
                emp.id,
                emp.fullName,
                emp.position,
                emp.hired,
                emp.salary,
                manager
        );

        return employee;
    }

    private static class NewEmployee {
        BigInteger id;
        FullName fullName;
        Position position;
        LocalDate hired;
        BigDecimal salary;
        BigInteger managerId;

        NewEmployee(BigInteger id, FullName fullName, Position position, LocalDate hired, BigDecimal salary, BigInteger managerId) {
            this.id = id;
            this.fullName = fullName;
            this.position = position;
            this.hired = hired;
            this.salary = salary;
            this.managerId = managerId;
        }
    }
}
