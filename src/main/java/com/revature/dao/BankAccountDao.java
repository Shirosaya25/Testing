package com.revature.dao;

import com.revature.models.BankAccount;

public interface BankAccountDao {

	public BankAccount getBankAccountFromId(int id);
	
	public int getNextAccountId();
	
	public boolean createBankAccount(BankAccount account);
	public boolean updateBankAccount(BankAccount account);
	public boolean removeBankAccount(int id);
}