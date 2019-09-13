package com.revature.deligates;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Employee;
import com.revature.service.EmployeeService;
import com.revature.util.EncryptionUtil;
import com.revature.util.LoggerUtil;
import com.revature.util.StringUtil;

public class LoginDeligate {

	private EmployeeService eservice = new EmployeeService();
	private ObjectMapper om = new ObjectMapper();
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String username = request.getHeader("username");
		String password = request.getHeader("password");
		
		Employee user = eservice.logInEmployee(username, password);
		
		if(user == null) {
			
			response.setHeader("Login", "failed");
			return;
		}
		
		String rawToken = StringUtil.getRandomString() + "." + StringUtil.getRandomString() + "." + StringUtil.getRandomString();
		LoggerUtil.log.info(rawToken);
		
		response.setHeader("Login", "Success");
		response.setHeader("User", om.writeValueAsString(user));
		response.setHeader("URL", "landing");
		response.setHeader("Token", EncryptionUtil.encrypt(rawToken));
		
	}
}
