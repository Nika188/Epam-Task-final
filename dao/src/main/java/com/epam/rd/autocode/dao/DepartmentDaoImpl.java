package com.epam.rd.autocode.dao;

import com.epam.rd.autocode.ConnectionSource;
import com.epam.rd.autocode.domain.Department;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentDaoImpl implements DepartmentDao{

    private static final ConnectionSource connectionSource = ConnectionSource.instance();
    private static Connection connection;

    public DepartmentDaoImpl(){
        try {
            connection = connectionSource.createConnection();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
    @Override
    public Optional<Department> getById(BigInteger Id) {
        String sql = "select * from department where id = ?";

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
    public List<Department> getAll() {
        String sql = "select * from department";
        List<Department> result = new ArrayList<>();

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
    public Department save(Department department) {
        String sql;
        if (getById(department.getId()).isEmpty()){
            sql = "insert into department (name, location, id) values (?, ?, ?)";
        }else{
            sql = "update department set name = ?, location = ? where id = ?";
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, department.getName());
            ps.setString(2, department.getLocation());
            ps.setLong(3, department.getId().longValue());
            ps.execute();

            return department;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Department department) {
        String sql = "delete from department where id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, department.getId().longValue());
            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Department map(ResultSet rs) throws SQLException {
        return new Department(
                BigInteger.valueOf(rs.getLong("id")),
                rs.getString("name"),
                rs.getString("location")
        );
    }
}
