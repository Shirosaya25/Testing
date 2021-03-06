package com.revature.dao;

import java.util.List;

import com.revature.model.Employee;

public interface EmployeeDao {
	
	public Employee getEmployeeByUsername(String username);
	public Employee getEmployeeByEmail(String email);
	
	public List<Employee> getEmployeesByAuthority(int authority);
	
	public int createEmployee(Employee employee);
	public int updateEmployee(Employee employee);
	public int deleteEmployee(Employee employee);
}
