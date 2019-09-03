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
import com.revature.util.LoggerUtil;

public class UserBankAccountDaoImpl implements UserBankAccountDao {
	
	private static final String TABLE_NAME = "UserBankAccount";
	
	private static final String COLUMN_1 = "Username";
	private static final String COLUMN_2 = "AccountId";

	public List<Integer> getBankAccountFromUserAccount(UserAccount user) {
		
		String sqltemplate = String.format("select \"%s\" from \"%s\" where \"%s\" = ? order by \"%s\"",
											COLUMN_2, TABLE_NAME, COLUMN_1, COLUMN_2);
		
		List<Integer> accountIdList = new ArrayList<Integer>();
		
		Connection conn = ConnectionUtil.getConnection();
		
		try(PreparedStatement statement = conn.prepareStatement(sqltemplate)) {
			
			statement.setString(1, user.getUsername());
			
			try(ResultSet results = statement.executeQuery()){
			
				while(results.next()) {
					
					accountIdList.add(results.getInt(COLUMN_2));
				}
				
				conn.close();
			}
		} 
		
		catch (SQLException e) {

			LoggerUtil.log.warn(e.getMessage());
		}
		
		return accountIdList;
	}

	public List<String> getUserAccountFromBankAccount(BankAccount bank) {
		
		String sqltemplate = String.format("select \"%s\" from \"%s\" where \"%s\" = ?",
				COLUMN_1, TABLE_NAME, COLUMN_2);

		List<String> usernameList = new ArrayList<String>();
		
		Connection conn = ConnectionUtil.getConnection();
		
		try (PreparedStatement statement = conn.prepareStatement(sqltemplate)){
			
			statement.setInt(1, bank.getAccountId());
			
			try(ResultSet results = statement.executeQuery()){
			
				while(results.next()) {
				
					usernameList.add(results.getString(COLUMN_1));
				}
				
				conn.close();
			}
		} 
		
		catch (SQLException e) {

			LoggerUtil.log.warn(e.getMessage());
		}

		return usernameList;
	}

	public boolean createUserBankAccount(UserAccount user, BankAccount bank) {
		
		int updated = 0;

		String sqltemplate = String.format("insert into \"%s\"(\"%s\", \"%s\")"
										 + "values(?, ?);",
										 TABLE_NAME, COLUMN_1, COLUMN_2);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try(PreparedStatement statement = conn.prepareStatement(sqltemplate)) {

			statement.setString(1, user.getUsername());
			statement.setInt(2,  bank.getAccountId());
			
			updated = statement.executeUpdate();
			
			conn.close();
		}
		
		catch (SQLException e) {

			LoggerUtil.log.warn(e.getMessage());
		}
		
		return updated > 0;
		
	}
}