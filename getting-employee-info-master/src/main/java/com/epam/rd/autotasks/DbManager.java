package com.epam.rd.autotasks;

import java.sql.*;

public class DbManager {

	private DbManager() {
		throw new UnsupportedOperationException();
	}

	public static int callCountDepartments(Connection connection) throws SQLException {
		try (CallableStatement stmt = connection.prepareCall("{call COUNT_DEPARTMENTS(?)}")) {
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.execute();
			return stmt.getInt(1);
		}
	}

	public static int callCountEmployees(Connection connection) throws SQLException {
		try (CallableStatement stmt = connection.prepareCall("{call COUNT_EMPLOYEES(?)}")) {
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.execute();
			return stmt.getInt(1);
		}
	}

	public static int callCountEmployeesByDepartmentId(Connection connection, int departmentId) throws SQLException {
		try (CallableStatement stmt = connection.prepareCall("{call COUNT_EMPLOYEES_BY_DEPARTMENT_ID(?, ?)}")) {
			stmt.setInt(1, departmentId);
			stmt.registerOutParameter(2, Types.INTEGER);
			stmt.execute();
			return stmt.getInt(2);
		}
	}
}