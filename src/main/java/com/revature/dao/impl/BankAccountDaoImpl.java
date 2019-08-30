package com.revature.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.dao.BankAccountDao;
import com.revature.models.BankAccount;
import com.revature.util.ConnectionUtil;

public class BankAccountDaoImpl implements BankAccountDao {
	
	private final static String TABLE_NAME = "BankAccount";
	
	private final static String COLUMN_1 = "AccountId";
	private final static String COLUMN_2 = "AccountType";
	private final static String COLUMN_3 = "AccountBalance";

	public BankAccount getBankAccountFromId(int id) {

		String sqltemplate = String.format("select * from \"%s\" where \"%s\" = ?",
											TABLE_NAME, COLUMN_1);
		
		Connection conn = ConnectionUtil.getConnection();
		
		BankAccount returnAccount = null;
		
		try {
			
			PreparedStatement statement = conn.prepareStatement(sqltemplate);
			statement.setInt(1, id);
			
			ResultSet results = statement.executeQuery();
			
			while(results.next()) {
				
				int accountId = results.getInt(COLUMN_1);
				int accountType = results.getInt(COLUMN_2);
				double accountBalance = results.getDouble(COLUMN_3);
				
				returnAccount = new BankAccount(accountId, accountType, accountBalance);
			}
			
			conn.close();
			
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnAccount;
	}
	
	public int getNextAccountId() {
		
		int ret = 1;
		
		String sqltemplate = "{? = call nextaccountid()}";
		
		Connection conn = ConnectionUtil.getConnection();
		
		try {
			
			CallableStatement statement = conn.prepareCall(sqltemplate);
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.execute();
			
			ret += statement.getInt(1);
			
			conn.close();
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}

	public boolean createBankAccount(BankAccount account) {
		
		int updated = 0;

		String sqltemplate = String.format("insert into \"%s\"(\"%s\", \"%s\")"
										 + "values(?, ?)",
										 TABLE_NAME, COLUMN_2, COLUMN_3);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try {
			
			PreparedStatement statement = conn.prepareStatement(sqltemplate);
			statement.setInt(1, account.getAccountType());
			statement.setDouble(2, account.getAccountBalance());
			
			updated += statement.executeUpdate();
			
			conn.close();
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return updated > 0;
	}

	public boolean updateBankAccount(BankAccount account) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeBankAccount(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
