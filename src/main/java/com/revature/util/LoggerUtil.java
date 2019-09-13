package com.revature.util;

import org.apache.log4j.Logger;

public class LoggerUtil {

	public static final Logger log = Logger.getLogger(Logger.class.getName());
	
	private LoggerUtil() {
		
		throw new IllegalStateException("Utility Class");
	}
}
