package com.revature.dao;

import java.util.List;

import com.revature.models.UserAccount;

public interface UserAccountDao {

	public List<UserAccount> getUserAccounts();
	public UserAccount getUserAccountByUsername(String username);
	public UserAccount getUserAccountByEmail(String email);
	
	public boolean createUserAccount(UserAccount user);
	public boolean updateUserAccount(UserAccount user);
	public boolean removeUserAccount(String username);
}
