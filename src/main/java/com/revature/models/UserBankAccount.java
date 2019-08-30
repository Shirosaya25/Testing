package com.revature.models;

import java.io.Serializable;

public class UserBankAccount implements Serializable{

	private static final long serialVersionUID = 1L;

	private UserAccount userAccount = null;
	private BankAccount bankAccount = null;
	
	public UserAccount getUserAccount() { return this.userAccount;}
	public BankAccount getBankAccount() { return this.bankAccount;}
	
}
