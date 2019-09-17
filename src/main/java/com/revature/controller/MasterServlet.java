package com.revature.controller;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.PropertyConfigurator;

import com.revature.util.LoggerUtil;

public class MasterServlet extends DefaultServlet{

	private static final long serialVersionUID = 1L;
	private static final RequestHelper requestHelper = new RequestHelper();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		PropertyConfigurator.configure("C:\\Users\\micha\\Documents\\Git\\batch-source\\Project1\\src\\main\\resources\\log4j\\log4j.properties");
		super.init(config);
	}
	
	/* (non-Javadoc)
	 * @see org.apache.catalina.servlets.DefaultServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			LoggerUtil.log.info(String.format("%s: %s - Recieved%n", request.getMethod(), request.getRequestURI()));
			
			String path = request.getRequestURI().substring(request.getContextPath().length());
			
			if(path.startsWith("/static/")) {
				
				super.doGet(request, response);
			}
			
			else {
				
				requestHelper.processGet(request, response);
			}
		}
		
		catch(UnknownHostException e) {
			
			LoggerUtil.log.info(e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.apache.catalina.servlets.DefaultServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LoggerUtil.log.info(String.format("%s: %s - Recieved%n", request.getMethod(), request.getRequestURI()));
		
		String path = request.getRequestURI().substring(request.getContextPath().length());
		
		if(path.startsWith("/static/")) {
			
			response.setStatus(400);
		}
		
		else {
			
			requestHelper.processPost(request, response);
		}
	}
}
