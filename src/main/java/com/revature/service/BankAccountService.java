package com.revature.service;

import com.revature.dao.BankAccountDao;
import com.revature.dao.impl.BankAccountDaoImpl;
import com.revature.models.BankAccount;

public class BankAccountService {
	
	private BankAccountDao dao = new BankAccountDaoImpl();
	
	public BankAccount getBankAccountFromId(int id) {
		
		return dao.getBankAccountFromId(id);
	}
	
	public boolean createBankAccount(BankAccount bank) {
		
		return dao.createBankAccount(bank);
	}
	
	public int getNextAccountId() {
		
		return dao.getNextAccountId();
	}

	public boolean updateBankAccount(BankAccount bank) {
		
		return dao.updateBankAccount(bank);
	}
}