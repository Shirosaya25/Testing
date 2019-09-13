package com.revature.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.EmployeeDao;
import com.revature.model.Employee;
import com.revature.util.ConnectionUtil;
import com.revature.util.LoggerUtil;

public class EmployeeDaoImpl implements EmployeeDao {
	
	private static final String TABLE_NAME = "Employee";
	
	private static final String COLUMN_1 = "Username";
	private static final String COLUMN_2 = "Password";
	private static final String COLUMN_3 = "FirstName";
	private static final String COLUMN_4 = "LastName";
	private static final String COLUMN_5 = "Email";
	private static final String COLUMN_6 = "Authority";

	private List<Employee> getEmployeesFromResultSet(PreparedStatement statement){
		
		List<Employee> ret = new ArrayList<Employee>();
		
		try (ResultSet results = statement.executeQuery()){
			
			while(results.next()) {
				
				ret.add(new Employee(results));
			}
		}
		
		catch (SQLException e) {
			
			LoggerUtil.log.warn(e.getMessage());
		}
		
		return ret;
	}
	
	/**
	 * Retrieves an employee from the database via unique username
	 * @param username: String to attempt to match with database
	 * @return Matching Employee object or null
	 */
	@Override
	public Employee getEmployeeByUsername(String username) {
		
		Employee ret = null;

		String sql = String.format("Select * from \"%s\" where \"%s\" = ?",
									TABLE_NAME, COLUMN_1);
		

		
		Connection conn = ConnectionUtil.getConnection();
		
		try(PreparedStatement statement = conn.prepareStatement(sql)){
			
			statement.setString(1, username);
			
			ret = getEmployeesFromResultSet(statement).get(0);
			conn.close();
			
		}
		
		catch(SQLException e) {
			
			LoggerUtil.log.error(e.getMessage());
		}
		
		return ret;
	}
	
	public List<Employee> getEmployeesByAuthority(int authority) {
		
		List<Employee> ret = new ArrayList<Employee>();

		String sql = String.format("Select * from \"%s\" where \"%s\" < ?",
									TABLE_NAME, COLUMN_6);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try(PreparedStatement statement = conn.prepareStatement(sql)){
			
			statement.setInt(1, authority);
			
			ret = getEmployeesFromResultSet(statement);
			
			conn.close();
			
		}
		
		catch(SQLException e) {
			
			LoggerUtil.log.error(e.getMessage());
		}
		
		return ret;
	}

	/**
	 * Retrieves an employee from the database via unique email
	 * @param email: String to attempt to match with database
	 * @return Matching Employee object or null;
	 */
	@Override
	public Employee getEmployeeByEmail(String email) {
		
		Employee ret = null;

		String sql = String.format("Select * from \"%s\" where \"%s\" = ?",
									TABLE_NAME, COLUMN_5);
		

		
		Connection conn = ConnectionUtil.getConnection();
		
		try(PreparedStatement statement = conn.prepareStatement(sql)){
			
			statement.setString(1, email);
			
			ret = getEmployeesFromResultSet(statement).get(0);
			
			conn.close();
			
		}
		
		catch(SQLException e) {
			
			LoggerUtil.log.error(e.getMessage());
		}
		
		return ret;
	}
	
	/**
	 * Creates an employee in the database with given Employee object
	 * @param employee
	 * @return integer representing number of database changes
	 */
	@Override
	public int createEmployee(Employee employee) {

		int ret = 0;
		
		String sql = String.format("insert into \"%s\"(\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\")"
								 + "values(?, ?, ?, ?, ?, ?)",
								 	TABLE_NAME, COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4, COLUMN_5, COLUMN_6);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try(PreparedStatement statement = conn.prepareStatement(sql)){
			
			statement.setString(1, employee.getUsername());
			statement.setString(2, employee.getPassword());
			statement.setString(3, employee.getFirstname());
			statement.setString(4, employee.getLastname());
			statement.setString(5, employee.getEmail());
			statement.setInt(6,  employee.getAuthority());
			
			ret = statement.executeUpdate();
			
			conn.close();
			
		}
		
		catch(SQLException e) {
			
			LoggerUtil.log.error(e.getMessage().toString());
		}
		
		return ret;
		
	}
	
	/**
	 * Updates an employee in the database to the values associated with given Employee object
	 * @param employee
	 * @return integer representing number of database changes
	 */
	@Override
	public int updateEmployee(Employee employee) {
		
		int ret = 0;
		
		String sql = String.format("update \"%s\" set"
								 + "\"%s\" = ?, \"%s\" = ?, \"%s\" = ?,"
								 + "\"%s\" = ?, \"%s\" = ?, \"%s\" = ?"
								 + "where \"%s\" = ?",
								 TABLE_NAME,
								 COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4, COLUMN_5, COLUMN_6,
								 COLUMN_1);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try(PreparedStatement statement = conn.prepareStatement(sql)){
			
			statement.setString(1, employee.getUsername());
			statement.setString(2, employee.getPassword());
			statement.setString(3, employee.getFirstname());
			statement.setString(4, employee.getLastname());
			statement.setString(5, employee.getEmail());
			statement.setInt(6,  employee.getAuthority());
			statement.setString(7, employee.getUsername());
			
			ret = statement.executeUpdate();
			
			conn.close();
		}
		
		catch(SQLException e) {
			
			LoggerUtil.log.error(e.getMessage());
		}
		
		return ret;
	}

	/**
	 * Deletes an employee in the database with the username associated with the given Employee Object
	 * @param employee
	 * @return integer representing number of database changes
	 */
	@Override
	public int deleteEmployee(Employee employee) {
		
		int ret = 0;
		
		String sql = String.format("delete from \"%s\" where \"%s\" = ?",
									TABLE_NAME, COLUMN_1);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try(PreparedStatement statement = conn.prepareStatement(sql)){
			
			statement.setString(1, employee.getUsername());
			
			ret = statement.executeUpdate();
			
			conn.close();
		}
		
		catch(SQLException e) {
			
			LoggerUtil.log.error(e.getMessage());
		}
		
		return ret;
	}

}
