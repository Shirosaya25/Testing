package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	private final static String SQL_HOST = System.getenv("JDBC_URL");
	private final static String SQL_PORT = System.getenv("JDBC_PORT");
	private final static String SQL_NAME = System.getenv("JDBC_NAME");
	private final static String SQL_USER = System.getenv("JDBC_USER");
	private final static String SQL_PASS = System.getenv("JDBC_PASS");

	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			System.out.println("Connecting to DB...");
			conn = DriverManager.getConnection(String.format("jdbc:postgresql://%s:%s/%s?user=%s&password=%s", SQL_HOST, SQL_PORT, SQL_NAME, SQL_USER, SQL_PASS));
			System.out.println("Successfully Connected to DB.");
			
		} 
		
		catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
		
		catch (ClassNotFoundException e) {

			e.printStackTrace();
			return null;
		}
		
		return conn;
	}
}
