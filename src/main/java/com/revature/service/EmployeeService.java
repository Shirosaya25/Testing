package com.revature.service;

import java.util.List;

import com.revature.dao.EmployeeDao;
import com.revature.dao.impl.EmployeeDaoImpl;
import com.revature.model.Employee;
import com.revature.util.EncryptionUtil;
import com.revature.util.LoggerUtil;
import com.revature.util.StringUtil;

public class EmployeeService {

	private EmployeeDao dao = new EmployeeDaoImpl();
	
	public Employee logInEmployee(String username, String unencryptedPassword) {
		
		if(username == null || unencryptedPassword == null) return null;
		
		Employee ret = null;
		
		if(StringUtil.isValidUsername(username)) {
			
			ret = dao.getEmployeeByUsername(username);
		}
		
		else if (StringUtil.isValidEmail(username)) {
			
			ret = dao.getEmployeeByEmail(username);
		}
		
		else {
			
			LoggerUtil.log.info(String.format("Invalid credentials provided:%nusername: %s%npassword: %s%n", username, unencryptedPassword));
			return null;
		}
		
		
		if(ret != null && ret.login(unencryptedPassword)) {
			
			LoggerUtil.log.info(String.format("Successfully logged in with credentials:%nusername: %s%npassword: %s%n", username, EncryptionUtil.encrypt(unencryptedPassword)));
			return ret;
		}
		
		
		LoggerUtil.log.info(String.format("Invalid credentials provided:%nusername: %s%npassword: %s%n", username, unencryptedPassword));
		return null;
	}
	
	public void logoutEmployee(Employee employee) {
		
		employee.logout();
	}
	
	public boolean changePassword(Employee employee, String newPassword) {
		
		employee.setPassword(EncryptionUtil.encrypt(newPassword));
		return dao.updateEmployee(employee) > 0;
	}
	
	public List<Employee> getEmployeesByAuthority(int authority){
		
		return dao.getEmployeesByAuthority(authority);
	}
	
	public boolean createEmployee(Employee employee) {
		
		return dao.createEmployee(employee) > 0;
	}
}
