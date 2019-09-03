package com.revature.models;

import java.io.Serializable;

import com.revature.util.LoggerUtil;

public class BankAccount implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int accountId;
	private int accountType;
	private double accountBalance;
	
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
	
	public boolean deposit(double amount) {
		
		this.accountBalance += amount;
		
		return true;
	}
	
	public boolean withdraw(double amount) {
		

		if(this.accountBalance - amount < 0.0) return false;
		
		this.accountBalance -= amount;
		
		return true;
	}

}