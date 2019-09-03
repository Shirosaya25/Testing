package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.UserAccountDao;
import com.revature.dao.impl.UserAccountDaoImpl;
import com.revature.models.BankAccount;
import com.revature.models.UserAccount;
import com.revature.util.LoggerUtil;
import com.revature.util.StringUtil;

public class UserAccountService {

	
	private UserAccountDao dao = new UserAccountDaoImpl();
	
	public UserAccount logInUserAccount(String username, String password) {
			
		UserAccount returnUser = null;
		
		if(StringUtil.isValidEmail(username)){
			
			returnUser =  getUserAccountByEmail(username.toLowerCase());
		}
		
		else if (StringUtil.isValidUsername(username)) {
			
			returnUser = getUserAccountByUsername(username.toLowerCase());
		}
		
		else {
			
			LoggerUtil.log.warn("Log In Failed: Invalid username or email.");
			return null;
		}
		
		if(returnUser == null) {
			
			LoggerUtil.log.warn("Log In Failed: No user found with that username or email.");
			return null;
		}
		
		returnUser.logIn(password);
		
		if(returnUser.isLoggedIn()) {
			
			LoggerUtil.log.info("Successfully Logged In.");
			return returnUser;
		}
		
		else {
			
			LoggerUtil.log.warn("Log In Failed: Incorrect Password.");
			return null;
		}
	}
	
	public boolean logOutUserAccount(UserAccount user) {
		
		return user.logOut();
	}
	
	public boolean openBankAccount(UserAccount user, int bankAccountType, double bankAccountBalance) {
		
		if(!user.isLoggedIn()) {
			
			LoggerUtil.log.warn("Bank Account Creation Failed: No valid login.");
			return false;
		}
		
		BankAccountService baservice = new BankAccountService();
		UserBankAccountService ubservice = new UserBankAccountService();
		
		BankAccount bank = new BankAccount(baservice.getNextAccountId(), bankAccountType, bankAccountBalance);
		
		baservice.createBankAccount(bank);
		return ubservice.createNewUserBankLink(user, bank);
		 
	}
	
	public List<BankAccount> getUserBankAccount(UserAccount user){
		
		if(!user.isLoggedIn()) {
			
			LoggerUtil.log.warn("Bank Account Retrieval Failed: No valid login.");
			return null;
		}
		
		BankAccountService baservice = new BankAccountService();
		UserBankAccountService ubservice = new UserBankAccountService();
		
		List<BankAccount> ret = new ArrayList<BankAccount>();
		
		for(int i : ubservice.getBankAccountFromUserAccount(user)) {
			
			ret.add(baservice.getBankAccountFromId(i));
		}
		
		return ret;
		
	}
	
	
	public UserAccount getUserAccountByUsername(String username) {
		
		return dao.getUserAccountByUsername(username);
	}
	
	public UserAccount getUserAccountByEmail(String email) {
		
		return dao.getUserAccountByEmail(email);
	}
	
	public boolean existingUsername(String username) {
		
		return !(getUserAccountByUsername(username.toLowerCase()) == null);
	}
	
	public boolean existingEmail(String email) {
		
		return !(getUserAccountByUsername(email.toLowerCase()) == null);
	}
	
	public boolean createUserAccount(UserAccount user) {
		
		return dao.createUserAccount(user);
	}
	
	public boolean updateUserAccount(UserAccount user) {
		
		return dao.updateUserAccount(user);
	}
	
	public boolean removeUserAccount(UserAccount user) {
		
		if(!user.isLoggedIn()) {
			
			LoggerUtil.log.warn("Delete User Failed: No valid login");
			return false;
		}
		
		return dao.removeUserAccount(user.getUsername());
		
		
	}
}