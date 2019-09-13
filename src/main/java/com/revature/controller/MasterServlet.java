package com.revature.controller;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.PropertyConfigurator;

import com.revature.util.LoggerUtil;

public class MasterServlet extends DefaultServlet{

	private static final long serialVersionUID = 1L;
	private static final RequestHelper requestHelper = new RequestHelper();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
			PropertyConfigurator.configure("C:\\Users\\micha\\Documents\\sts-3.9.7.RELEASE\\plugins\\org.apache.axis_1.4.0.v201411182030\\lib\\log4j.properties");
			
			LoggerUtil.log.info(request.getMethod() + " : " + request.getRequestURI());
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LoggerUtil.log.info(request.getMethod() + " : " + request.getRequestURI());
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
