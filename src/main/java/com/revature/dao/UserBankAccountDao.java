package com.revature.dao;

import java.util.List;

import com.revature.models.UserAccount;
import com.revature.models.BankAccount;

public interface UserBankAccountDao {

	public List<Integer> getBankAccountFromUserAccount(UserAccount user);
	public List<String> getUserAccountFromBankAccount(BankAccount bank);
	
	public boolean createUserBankAccount(UserAccount user, BankAccount bank);
}