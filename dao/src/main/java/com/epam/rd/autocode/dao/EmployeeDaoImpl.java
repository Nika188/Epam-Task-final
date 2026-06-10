package com.epam.rd.autocode.dao;

import com.epam.rd.autocode.ConnectionSource;
import com.epam.rd.autocode.domain.Department;
import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao{

    private static final ConnectionSource connectionSource = ConnectionSource.instance();
    private static Connection connection;

    public EmployeeDaoImpl(){
        try {
            connection = connectionSource.createConnection();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }


    @Override
    public List<Employee> getByDepartment(Department department) {
        String sql = "select * from employee where department = ?";
        List<Employee> result = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, department.getId().longValue());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(map(rs));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> getByManager(Employee employee) {
        String sql = "select * from employee WHERE manager = ?";
        List<Employee> result = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, employee.getId().longValue());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(map(rs));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Employee> getById(BigInteger Id) {
        String sql = "select * from employee where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, Id.longValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(map(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> getAll() {
        String sql = "select * from employee";
        List<Employee> result = new ArrayList<>();

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                result.add(map(rs));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee save(Employee employee) {
        String sql;
        if (getById(employee.getId()).isEmpty()){
            sql ="insert into employee (firstName, lastName, middleName, position, hireDate, salary, manager, department, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        }else{
            sql = "update employee set firstName = ?, lastName = ?, middleName = ?, position = ?, hireDate = ?, salary = ?, manager = ?, department = ? where id = ? ";
        }


        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, employee.getFullName().getFirstName());
            ps.setString(2, employee.getFullName().getLastName());
            ps.setString(3, employee.getFullName().getMiddleName());
            ps.setString(4, employee.getPosition().name());
            ps.setDate(5, Date.valueOf(employee.getHired()));
            ps.setBigDecimal(6, employee.getSalary());

            if (employee.getManagerId() == null) {
                ps.setNull(7, Types.BIGINT);
            } else {
                ps.setLong(7, employee.getManagerId().longValue());
            }

            if (employee.getDepartmentId() == null) {
                ps.setNull(8, Types.BIGINT);
            } else {
                ps.setLong(8, employee.getDepartmentId().longValue());
            }
            ps.setLong(9, employee.getId().longValue());
            ps.execute();

            return employee;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Employee employee) {
        String sql = "delete from employee where id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, employee.getId().longValue());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Employee map(ResultSet rs) throws SQLException {
        return new Employee(
                BigInteger.valueOf(rs.getLong("id")),
                new FullName(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("middleName")
                ),
                Position.valueOf(rs.getString("position")),
                rs.getDate("hireDate").toLocalDate(),
                rs.getBigDecimal("salary"),
                rs.getObject("manager") == null
                        ? BigInteger.valueOf(0)
                        : BigInteger.valueOf(rs.getLong("manager")),
                rs.getObject("department") == null
                        ? BigInteger.valueOf(0)
                        : BigInteger.valueOf(rs.getLong("department"))
        );
    }
}
