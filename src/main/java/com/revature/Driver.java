package com.revature;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import com.revature.util.StringUtil;
import com.revature.models.BankAccount;
import com.revature.models.UserAccount;
import com.revature.service.BankAccountService;
import com.revature.service.UserAccountService;
import com.revature.service.UserBankAccountService;

public class Driver {
	
	static Scanner scan = new Scanner(System.in);
	static UserAccountService service = new UserAccountService();
	static BankAccountService bservice = new BankAccountService();
	static UserBankAccountService ubservice = new UserBankAccountService();
	
	static UserAccount user = null;
	
	static final String[] ACCOUNT_TYPES = {"Checking", "Savings"};
	

	public static void main(String[] args) {
		
		PropertyConfigurator.configure("C:\\Users\\micha\\Documents\\sts-3.9.7.RELEASE\\plugins\\org.apache.axis_1.4.0.v201411182030\\lib\\log4j.properties");
	
		boolean flag = true;
		int opt = 0;
		
		programLoop:
		while(flag == true) {
			
			while(user == null) {
				
				opt = 0;
				
				System.out.printf("Select Option:%n- 1 - Log Into Existing Account.%n- 2 - Create a New Account.%n- 3 - Exit Program%n");
				
				try {
					
					opt = scan.nextInt();	
					scan.nextLine();
				}
				
				catch(InputMismatchException e) {
					
					e.printStackTrace();
				}
				
				switch(opt) {
				
				case 1:
					
					logIn();
					break;
				
				
				case 2:
					
					createNewUser();
					break;
				
				
				case 3: 
					
					flag = false;
					System.out.println("Program Terminated");
					break programLoop;
					
				}
			}
			
			while(user != null) {
				
				opt = 0;
				
				clearScreen();
				
				System.out.printf("Current Banking Accounts:%n");
				
				List<BankAccount> accounts = service.getUserBankAccount(user);
				
				for(BankAccount acc : accounts) {
					
					System.out.printf("%8s Account %3d: $%.2f%n", ACCOUNT_TYPES[acc.getAccountType()] ,acc.getAccountId(), acc.getAccountBalance());
				}
				
				System.out.printf("Select Option:%n- 1 - Manage Current Banking Accounts%n- 2 - Open a New Banking Account%n- 3 - Log Out of Account%n");
				
				try {
					
					opt = scan.nextInt();	
					scan.nextLine();
				}
				
				catch(InputMismatchException e) {
					
					e.printStackTrace();
				}
				
				switch(opt) {
				
				case 1:
					
					manageAccounts(accounts);
					break;
					
				case 2:
					
					createNewBankAccount();
					break;
					
				case 3:
					
					user.logOut();
					user = null;
					break;
				}
				
			}
		}
	}
	
	public static void logIn() {
		
		String username;
		String password;
		
		System.out.printf("Enter a valid username or -q to quit.%n");
		username = scan.nextLine();
		
		if(username.contentEquals("-q")) return;
		
		System.out.println("Enter the password associated.");
		password = scan.nextLine();
		
		user = service.logInUserAccount(username, password);
	}
	
	public static void createNewUser() {
		
		String username;
		String password;
		String firstname;
		String lastname;
		String email;
		
		boolean flag = true;
		
		System.out.printf("Enter desired username.%n");
		
		do {
			
			username = scan.nextLine().trim();
			
			if(service.existingUsername(username)) {
				
				System.out.printf("Username already taken: Please enter another.%n");
			}
			
			else {
				
				flag = false;
			}
		} while (flag == true);
		
		flag = true;
		
		System.out.printf("Enter desired password.%n");
		
		password = scan.nextLine().trim();
		
		System.out.printf("Enter your first name.%n");
		
		firstname = scan.nextLine().trim();
		
		System.out.printf("Enter your last name.%n");
		
		lastname = scan.nextLine().trim();
		
		System.out.printf("Enter your email.%n");
		
		do {
			
			email = scan.nextLine().trim();
			
			if(service.existingEmail(email)) {
				
				System.out.printf("Email already taken: Please enter another.%n");
			}
			
			else {
				
				flag = false;
			}
		} while (flag == true);
		
		user = new UserAccount(username, password, firstname, lastname, email);
		user.logIn(password);
		service.createUserAccount(user);
		service.logInUserAccount(username, password);
		
	}
	
	public static void createNewBankAccount() {
		
		int accountType = 0;
		double accountBalance = 0;
		
		boolean validResp = false;
		int resp = 0;
		
		while(!validResp) {
		
			System.out.printf("Creating Account: Select Option.%n- 1 - Checking%n- 2 - Savings%n");
	
			try {
				
				resp = scan.nextInt();	
				scan.nextLine();
			}
			
			catch(InputMismatchException e) {
				
				e.printStackTrace();
			}
			
			switch(resp) {
			
			case 1:
				accountType = 0;
				validResp = true;
				break;
				
			case 2:
				accountType = 1;
				validResp = true;
				break;
			}
		}
		
		validResp = false;
		
		while(!validResp) {
			
			System.out.printf("Enter Starting Balance.%n");
			
			try {
				
				accountBalance = scan.nextDouble();
				scan.nextLine();
			}
			
			catch(InputMismatchException e) {
				
				e.printStackTrace();
			}
			
			if(StringUtil.isValidAmount(accountBalance)) {
				
				validResp = true;
			}
		}
		
		service.openBankAccount(user, accountType, accountBalance);
	}
	
	public static void manageAccounts(List<BankAccount> accounts) {
		
		int resp = 0;
		
		while(true) {
			
			System.out.printf("Current Accounts:%n");
			
			int i = 1;
			
			for(BankAccount acc : accounts) {
				
				System.out.printf("%d: %8s Account %3d: $%.2f%n", i, ACCOUNT_TYPES[acc.getAccountType()] ,acc.getAccountId(), acc.getAccountBalance());
				i++;
			}
			
			System.out.printf("Which one to manage - Enter 0 to return:%n");
			
			try {
				
				resp = scan.nextInt();	
				scan.nextLine();
			}
			
			catch(InputMismatchException e) {
				
				e.printStackTrace();
			}
			
			if(resp <= accounts.size() && resp > 0) {
				
				manageAccount(accounts.get(resp - 1));
			}
			
			else if (resp == 0) return;
		}
	}
	
	public static void manageAccount(BankAccount account) {
		
		int resp = 0;
		
		while(true) {
			
			System.out.printf("Current Account:%n");
			System.out.printf("%8s Account %3d: $%.2f%n", ACCOUNT_TYPES[account.getAccountType()] ,account.getAccountId(), account.getAccountBalance());
			System.out.printf("Select Option:%n- 1 - Deposit%n- 2 - Withdraw%n- 3 - Link Account%n- 4 - Back%n");
			
			try {
				
				resp = scan.nextInt();	
				scan.nextLine();
			}
			
			catch(InputMismatchException e) {
				
				e.printStackTrace();
			}
			
			switch(resp) {
			
			case 1:
				deposit(account);
				break;
				
			case 2:
				withdraw(account);
				break;
				
			case 3:
				link(account);
				
			case 4:
				return;
			}
		}
	}
	
	public static void deposit(BankAccount account) {
		
		boolean validResp = false;
		double amount = 0;
		
		while(!validResp) {
			
			System.out.printf("Enter Amount to Deposit.%n");
			
			try {
				
				amount = scan.nextDouble();
				scan.nextLine();
			}
			
			catch(InputMismatchException e) {
				
				e.printStackTrace();
			}
			
			if(StringUtil.isValidAmount(amount)) {
				
				validResp = true;
			}
		}
		
		
		account.deposit(amount);
		bservice.updateBankAccount(account);
		
	}
	
	public static void withdraw(BankAccount account) {
		
		boolean validResp = false;
		double amount = 0;
		
		while(!validResp) {
			
			System.out.printf("Enter Amount to Withdraw.%n");
			
			try {
				
				amount = scan.nextDouble();
				scan.nextLine();
			}
			
			catch(InputMismatchException e) {
				
				e.printStackTrace();
			}
			
			if(StringUtil.isValidAmount(amount)) {
				
				validResp = true;
			}
		}
		
		
		account.withdraw(amount);
		bservice.updateBankAccount(account);
		
	}
	
	public static void link(BankAccount account) {
		
		boolean validResp = false;
		String line;
		
		while(!validResp) {
			
			System.out.printf("Currently Linked Accounts:%n");
			
			for(String username : ubservice.getUserAccountFromBankAccount(account)) {
				
				System.out.printf("%s%n", username);
			}
			
			System.out.printf("Enter new user to link or -q to quit.%n");
			
			line = scan.nextLine();
			
			if(line.contentEquals("-q")) return;
			
			ubservice.createNewUserBankLink(service.getUserAccountByUsername(line), account);
		}
	}
	
	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}  
}