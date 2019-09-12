package com.revature.deligates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewDeligate {
	
	private static final String LOGIN_VIEW = "/static/views/login.html";
	private static final String LANDING_VIEW = "/static/views/landing.html";

	public void returnView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String uri = request.getServletPath();
		
		switch(uri) {
		
		case "/":
			
			request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
			break;
		
		case "/login":
			
			request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
			break;
			
		case "/landing":
			
			request.getRequestDispatcher(LANDING_VIEW).forward(request, response);
			break;
			
		default:
			response.setStatus(400);
			break;
		}
	}
}
