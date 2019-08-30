package com.revature.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.UserAccountDao;
import com.revature.models.UserAccount;
import com.revature.util.ConnectionUtil;

public class UserAccountDaoImpl implements UserAccountDao{
	
	private static final String TABLE_NAME = "UserAccount";
	
	private final static String COLUMN_1 = "Username";
	private final static String COLUMN_2 = "Password";
	private final static String COLUMN_3 = "FirstName";
	private final static String COLUMN_4 = "LastName";
	private final static String COLUMN_5 = "Email";
	
	private final static String USERNAME_VIOLATION  = "ERROR: duplicate key value violates unique constraint \"username\"";
	private final static String EMAIL_VIOLATION 	= "ERROR: duplicate key value violates unique constraint \"email_u\"";

	public List<UserAccount> getUserAccounts() {

		String sqltemplate = String.format("Select * from \"%s\"", TABLE_NAME);
		
		Connection conn = ConnectionUtil.getConnection();
		
		List<UserAccount> useraccounts = new ArrayList<UserAccount>();
		
		try {
			
			Statement statement = conn.createStatement();
			ResultSet results 	= statement.executeQuery(sqltemplate);
			
			while(results.next()) {
				
				String username 	= results.getString(COLUMN_1);
				String password 	= results.getString(COLUMN_2);
				String firstname 	= results.getString(COLUMN_3);
				String lastname 	= results.getString(COLUMN_4);
				String email 		= results.getString(COLUMN_5);
				
				useraccounts.add(new UserAccount(username, password, firstname, lastname, email));
			}
			
			conn.close();
			
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return useraccounts;
	}

	public UserAccount getUserAccountByUsername(String username_input) {
		
		String sqltemplate = String.format("Select * from \"%s\" as x where x.\"%s\" = ?", TABLE_NAME, COLUMN_1);
		
		Connection conn = ConnectionUtil.getConnection();
		
		UserAccount useraccount = null;
		
		try {
			
			PreparedStatement statement = conn.prepareStatement(sqltemplate);
			
			statement.setString(1,  username_input);
			ResultSet results 	= statement.executeQuery();
			
			while(results.next()) {
				
				String username 	= results.getString(COLUMN_1);
				String password 	= results.getString(COLUMN_2);
				String firstname 	= results.getString(COLUMN_3);
				String lastname 	= results.getString(COLUMN_4);
				String email 		= results.getString(COLUMN_5);
				
				useraccount = new UserAccount(username, password, firstname, lastname, email);
			}
			
			conn.close();
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return useraccount;
	}
	
	public UserAccount getUserAccountByEmail(String email_input) {
		
		String sqltemplate = String.format("Select * from \"%s\" as x where x.\"%s\" = ?", TABLE_NAME, COLUMN_5);
		
		Connection conn = ConnectionUtil.getConnection();
		
		UserAccount useraccount = null;
		
		try {
			
			PreparedStatement statement = conn.prepareStatement(sqltemplate);
			
			statement.setString(1,  email_input);
			ResultSet results 	= statement.executeQuery();
			
			while(results.next()) {
				
				String username 	= results.getString(COLUMN_1);
				String password 	= results.getString(COLUMN_2);
				String firstname 	= results.getString(COLUMN_3);
				String lastname 	= results.getString(COLUMN_4);
				String email 		= results.getString(COLUMN_5);
				
				useraccount = new UserAccount(username, password, firstname, lastname, email);
			}
			
			conn.close();
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return useraccount;
	}

	public boolean createUserAccount(UserAccount user) {
		
		int updated = 0;
		String sqltemplate = String.format("insert into \"%s\"(\"%s\", \"%s\", \"%s\", \"%s\", \"%s\")"
											+ "values(?, ?, ?, ?, ?);", TABLE_NAME,
											COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4, COLUMN_5);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try {
			
			PreparedStatement statement = conn.prepareStatement(sqltemplate);
			
			statement.setString(1,  user.getUsername());
			statement.setString(2,  user.getPassword());
			statement.setString(3,  user.getFirstname());
			statement.setString(4,  user.getLastname());
			statement.setString(5,  user.getEmail());
			
			updated += statement.executeUpdate();
			conn.close();
		}
		
		catch(SQLException e) {
			
			e.printStackTrace();
			
			String emsg = e.getMessage().split("\n")[0];
			
			if(emsg.equals(USERNAME_VIOLATION)) 	System.out.println("Insert Failed: Username already taken.");

			else if(emsg.equals(EMAIL_VIOLATION)) 	System.out.println("Insert Failed: Email already taken.");
		}
		
		return updated == 1;
	}

	public boolean updateUserAccount(UserAccount user) {
		
		int updated = 0;
		String sqltemplate = String.format("update \"%s\" set"
											+ "\"%s\" = ?,"
											+ "\"%s\" = ?,"
											+ "\"%s\" = ?,"
											+ "\"%s\" = ? "
											+ "where \"%s\" = ?;",
											TABLE_NAME,
											COLUMN_2, COLUMN_3, COLUMN_4, COLUMN_5,
											COLUMN_1);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try {
			
			PreparedStatement statement = conn.prepareStatement(sqltemplate);
			
			statement.setString(1,  user.getPassword());
			statement.setString(2,  user.getFirstname());
			statement.setString(3,  user.getLastname());
			statement.setString(4,  user.getEmail());
			statement.setString(5,  user.getUsername());
			
			updated += statement.executeUpdate();
			conn.close();
		}
		
		catch(SQLException e) {
			
			e.printStackTrace();
			
			String emsg = e.getMessage().split("\n")[0];
			
			if(emsg.equals(USERNAME_VIOLATION)) 	System.out.println("Insert Failed: Username already taken.");

			else if(emsg.equals(EMAIL_VIOLATION)) 	System.out.println("Insert Failed: Email already taken.");
		}
		
		return updated == 1;
	}

	public boolean removeUserAccount(String username) {
		
		int updated = 0;
		String sqltemplate = String.format("delete from \"%s\" where \"%s\" = ?",
											TABLE_NAME,
											COLUMN_1);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try {
			
			PreparedStatement statement = conn.prepareStatement(sqltemplate);
			
			statement.setString(1, username);
			
			updated += statement.executeUpdate();
			conn.close();
		}
		
		catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		return updated == 1;
	}

}
