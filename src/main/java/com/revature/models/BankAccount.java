package com.revature.models;

import java.io.Serializable;

public class BankAccount implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int accountId;
	private int accountType;
	private double accountBalance;
	
	private boolean verified = false;
	
	public BankAccount() {
		
		super();
	}
	
	public BankAccount(int accountId, int accountType, double accountBalance) {
		
		this.accountId 		= accountId;
		this.accountType 	= accountType;
		this.accountBalance = accountBalance;
	}
	
	public int getAccountId() 		{ return this.accountId; }
	public int getAccountType() 	{ return this.accountType; }
	public double getAccountBalance() 	{ return this.accountBalance; }
	
	public boolean getBankAccountFromId(UserAccount user) {
		
		if(!user.isLoggedIn()) {
			
			System.out.println("Bank Account Retrieval Failed: User not logged in.");
			return false;
		}
		
		verified = true;
		
		return true;
	}
	
	public boolean withdraw(double amount) {
		
		if(!this.verified) {
			
			System.out.println("Bank Account Withdraw Failed: No verified owner.");
		}
		
		if(this.accountBalance - amount < 0.0) return false;
		
		
		
		return true;
	}

}
