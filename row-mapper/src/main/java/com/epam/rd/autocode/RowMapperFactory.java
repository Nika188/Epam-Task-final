package com.epam.rd.autocode;

import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDate;

public class RowMapperFactory {

    public RowMapper<Employee> employeeRowMapper() {
        return resultSet -> {
            try{
                BigInteger id = BigInteger.valueOf(resultSet.getLong("id"));

                FullName fullName = new FullName(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("middleName"));

                Position position = Position.valueOf(resultSet.getString("position"));

                LocalDate hired = resultSet.getDate("hireDate").toLocalDate();

                BigDecimal salary = resultSet.getBigDecimal("salary");

                return new Employee(id, fullName, position, hired, salary);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
