package com.revature.deligates;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Employee;
import com.revature.model.Request;
import com.revature.service.EmployeeService;
import com.revature.service.RequestService;

public class LandingDeligate {

	private ObjectMapper om = new ObjectMapper();
	private EmployeeService eservice = new EmployeeService();
	private RequestService rservice = new RequestService();
	
	public void getRequests(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		List<Request> employeeRequests = rservice.getRequestsByApplicant(om.readValue(request.getHeader("User"), Employee.class));
		response.setContentType("application/json;charset=UTF-8");
		try(PrintWriter pw = response.getWriter()){
			
			pw.write(om.writeValueAsString(employeeRequests));
		}
	}
	
	public void postNewPassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		Employee user = eservice.logInEmployee(request.getHeader("Username"), request.getHeader("Password"));
		
		if(user == null) {
			
			response.setStatus(403);
			return;
		}
		
		eservice.changePassword(user, request.getHeader("NewPassword"));
		
		response.setHeader("NewPassword", user.getPassword());
	}
	
	public void postUpdateRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		Request reiRequest = om.readValue(request.getHeader("request"), Request.class);
		
		if(rservice.updateRequest(reiRequest)) {
			
			return;
		}
		
		response.setStatus(403);
	}
	
	public void postNewRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		Employee user = om.readValue(request.getHeader("user"), Employee.class);
		
		Request reiRequest = new Request(user, rservice.getNextRequestId(), Double.valueOf(request.getHeader("amount")), request.getHeader("description"), request.getHeader("reference"));
		
		if(rservice.createRequest(reiRequest)) {
			
			return;
		}
		
		response.setStatus(403);
	}
}
