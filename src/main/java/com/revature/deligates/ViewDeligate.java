package com.revature.deligates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewDeligate {
	
	private static final String LOGIN_VIEW = "/static/views/login.html";
	private static final String LANDING_VIEW = "/static/views/landing.html";

	/**
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
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
			request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
			break;
		}
	}
}
