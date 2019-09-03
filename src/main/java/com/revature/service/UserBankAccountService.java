package com.revature.service;

import java.util.List;

import com.revature.dao.impl.UserBankAccountDaoImpl;
import com.revature.models.BankAccount;
import com.revature.models.UserAccount;

public class UserBankAccountService {

	private UserBankAccountDaoImpl dao = new UserBankAccountDaoImpl();
	
	public List<Integer> getBankAccountFromUserAccount(UserAccount user){
		
		return dao.getBankAccountFromUserAccount(user);
	}
	
	public List<String> getUserAccountFromBankAccount(BankAccount bank){
		
		return dao.getUserAccountFromBankAccount(bank);
	}
	
	public boolean createNewUserBankLink(UserAccount user, BankAccount bank) {
		
		return dao.createUserBankAccount(user, bank);
	}
}