package com.revature.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.UserBankAccountDao;
import com.revature.models.BankAccount;
import com.revature.models.UserAccount;
import com.revature.util.ConnectionUtil;

public class UserBankAccountDaoImpl implements UserBankAccountDao {
	
	private final static String TABLE_NAME = "UserBankAccount";
	
	private final static String COLUMN_1 = "Username";
	private final static String COLUMN_2 = "AccountId";

	public List<Integer> getBankAccountFromUserAccount(UserAccount user) {
		
		String sqltemplate = String.format("select \"%s\" from \"%s\" where \"%s\" = ? order by \"%s\"",
											COLUMN_2, TABLE_NAME, COLUMN_1, COLUMN_2);
		
		List<Integer> accountIdList = new ArrayList<Integer>();
		
		Connection conn = ConnectionUtil.getConnection();
		
		try {
			
			PreparedStatement statement = conn.prepareStatement(sqltemplate);
			statement.setString(1, user.getUsername());
			
			ResultSet results = statement.executeQuery();
			
			while(results.next()) {
				
				accountIdList.add(results.getInt(COLUMN_2));
			}
			
			conn.close();
		} 
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return accountIdList;
	}

	public List<String> getUserAccountFromBankAccount(BankAccount bank) {
		
		String sqltemplate = String.format("select \"%s\" from \"%s\" where \"%s\" = ?",
				COLUMN_1, TABLE_NAME, COLUMN_2);

		List<String> usernameList = new ArrayList<String>();
		
		Connection conn = ConnectionUtil.getConnection();
		
		try {
			
			PreparedStatement statement = conn.prepareStatement(sqltemplate);
			statement.setInt(1, bank.getAccountId());
			
			ResultSet results = statement.executeQuery();
			
			while(results.next()) {
			
				usernameList.add(results.getString(COLUMN_1));
			}
			
			conn.close();
		} 
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return usernameList;
	}

	public boolean createUserBankAccount(UserAccount user, BankAccount bank) {
		
		int updated = 0;

		String sqltemplate = String.format("insert into \"%s\"(\"%s\", \"%s\")"
										 + "values(?, ?);",
										 TABLE_NAME, COLUMN_1, COLUMN_2);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try {
			
			PreparedStatement statement = conn.prepareStatement(sqltemplate);
			statement.setString(1, user.getUsername());
			statement.setInt(2,  bank.getAccountId());
			
			updated = statement.executeUpdate();
			
			conn.close();
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return updated > 0;
		
	}

	public boolean updateUserBankAccount(UserAccount user, BankAccount bank) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeUserBankAccount(UserAccount user, BankAccount bank) {
		// TODO Auto-generated method stub
		return false;
	}

}