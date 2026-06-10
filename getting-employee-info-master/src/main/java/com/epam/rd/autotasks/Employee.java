package com.epam.rd.autotasks;

import java.util.Objects;

public class Employee {
	private int id;
	private String firstName;
	private String lastName;
	private Department department;

	public Employee(int employeeId, String employeeFirstName, String employeeLastName, Department employeeDepartment) {
		id = employeeId;
		firstName = employeeFirstName;
		lastName = employeeLastName;
		department = employeeDepartment;
	}

	public Employee(String employeeFirstName, String employeeLastName, Department employeeDepartment) {
		firstName = employeeFirstName;
		lastName = employeeLastName;
		department = employeeDepartment;
	}

	public int getId() {
		return id;
	}

	public void setId(int studentId) {
		id = studentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String employeeFirstName) {
		firstName = employeeFirstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String employeeLastName) {
		lastName = employeeLastName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department employeeDepartment) {
		department = employeeDepartment;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName + ": " + department;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, department);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		Employee other = (Employee) object;
		return Objects.equals(firstName, other.firstName) && Objects.equals(department, other.department)
				&& Objects.equals(lastName, other.lastName);
	}
}