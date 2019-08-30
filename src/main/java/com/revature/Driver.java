package com.revature;

import com.revature.models.BankAccount;
import com.revature.models.UserAccount;
import com.revature.service.UserAccountService;

public class Driver {

	public static void main(String[] args) {
		
		UserAccountService service = new UserAccountService();
		UserAccount ua = service.logInUserAccount("mjzhang2", "password123");
		//service.openBankAccount(ua, 2, 1970.32);
		for(BankAccount i :service.getUserBankAccount(ua)) {
			
			System.out.printf("%d, %d, %f\n", i.getAccountId(), i.getAccountType(), i.getAccountBalance());
		}
		ua.logOut();
	}
}
