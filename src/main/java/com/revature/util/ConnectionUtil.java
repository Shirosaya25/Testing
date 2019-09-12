package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	private static final String SQL_HOST = System.getenv("JDBC_URL");
	private static final String SQL_PORT = System.getenv("JDBC_PORT");
	private static final String SQL_NAME = System.getenv("JDBC_NAME");
	private static final String SQL_USER = System.getenv("JDBC_USER");
	private static final String SQL_PASS = System.getenv("JDBC_PASS");
	
	private ConnectionUtil() {
		throw new IllegalStateException("Utility Class.");
	}
	
	/**
	 * Establishes a connection with the RDB using JDBC
	 * @return Connection Object representing database connection
	 */
	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			LoggerUtil.log.info("Connecting to DB...");
			conn = DriverManager.getConnection(String.format("jdbc:postgresql://%s:%s/%s?user=%s&password=%s", SQL_HOST, SQL_PORT, SQL_NAME, SQL_USER, SQL_PASS));
			LoggerUtil.log.info("Successfully Connected to DB.");
			
		} 
		
		catch (SQLException e) {
			
			LoggerUtil.log.error(e.getMessage());
			return null;
		}
		
		catch (ClassNotFoundException e) {

			LoggerUtil.log.error(e.getMessage());
			return null;
		}
		
		return conn;
	}
}
