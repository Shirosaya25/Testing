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
	
	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String username = request.getHeader("username");
		String password = request.getHeader("password");
		
		Employee user = eservice.logInEmployee(username, password);
		
		if(user == null) {
			
			response.setHeader("Login", "failed");
			return;
		}
		
		String token = EncryptionUtil.encrypt(StringUtil.getRandomString() + "." + StringUtil.getRandomString() + "." + StringUtil.getRandomString());
		
		LoggerUtil.log.info(token);
		
		response.setHeader("Login", "Success");
		response.setHeader("User", om.writeValueAsString(user));
		response.setHeader("URL", "landing");
		response.setHeader("Token", token);
		
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void validateToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = request.getHeader("token");
		
		if(StringUtil.isValidToken(token)) {
			
			return;
		}
		
		response.setStatus(400);
	}
}
