package com.revature.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.deligates.LandingDeligate;
import com.revature.deligates.LoginDeligate;
import com.revature.deligates.ViewDeligate;
import com.revature.util.EncryptionUtil;
import com.revature.util.StringUtil;

public class RequestHelper {
	
	private static ViewDeligate viewDeligate = new ViewDeligate();
	
	public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getServletPath();
		String token = request.getHeader("token");
		System.out.println(token + " : " + StringUtil.isValidToken(EncryptionUtil.decrypt(token)));
		
		if(uri.startsWith("/api/")) {
			
			String record = uri.substring(5);
			
			
			if(record.contentEquals("login")) {
				
				new LoginDeligate().login(request, response);
			}
			
			else if(record.startsWith("landing/")) {
				
				String command = record.substring(8);
				
				if (command.contentEquals("request/employee")) new LandingDeligate().getRequestsByEmployee(request, response);
				else if (command.contentEquals("request/authority")) new LandingDeligate().getRequestsByAuthority(request, response);
				else if (command.contentEquals("employee/authority")) new LandingDeligate().getEmployeesByAuthority(request, response);
			}
			
			else {
				
				response.setStatus(400);
			}
		}
		
		
		else {
			
			viewDeligate.returnView(request, response);
		}
	}
	
	public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getServletPath();
		
		String token = request.getHeader("token");
		
		System.out.println(token + " : " + StringUtil.isValidToken(EncryptionUtil.decrypt(token)));
		
		if(uri.startsWith("/api/")) {
			
			String record = uri.substring(5);
			
			if(record.startsWith("landing/")){
				
				String command = record.substring(8);
				
				if(command.contentEquals("employee/new")) new LandingDeligate().postNewEmployee(request, response);
				else if(command.contentEquals("employee/setPassword")) new LandingDeligate().postNewPassword(request, response);
				else if(command.contentEquals("request/update")) new LandingDeligate().postUpdateRequest(request, response);	
				else if(command.contentEquals("request/new")) new LandingDeligate().postNewRequest(request, response);	
				else if(command.contentEquals("request/final")) new LandingDeligate().postFinalRequest(request, response);
			}
			
			else {
				
				response.setStatus(400);
			}
		}
		
		else {
			
			response.setStatus(400);
		}
	}
}
