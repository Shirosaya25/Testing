package com.revature.util;

import org.apache.log4j.Logger;

import com.revature.controller.MasterServlet;

public class LoggerUtil {

	public static final Logger log = Logger.getLogger(MasterServlet.class);
	
	private LoggerUtil() {
		
		throw new IllegalStateException("Utility Class");
	}
}
