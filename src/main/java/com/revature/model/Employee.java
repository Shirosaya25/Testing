package com.revature.model;

import com.revature.util.EncryptionUtil;
import com.revature.util.StringUtil;

public class Employee {

	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private int authority;
	
	public Employee() {
		
		super();
	}
	
	public Employee(String username, String password, String firstname, String lastname, String email, int authority) {
		
		this.username = username.toLowerCase();
		this.password = password;
		this.firstname = firstname.toUpperCase();
		this.lastname = lastname.toUpperCase();
		this.email = email.toLowerCase();
		this.authority = authority;
	}
	
	public boolean login(String unencryptedPassword) {
		
		return this.password.equals(EncryptionUtil.encrypt(unencryptedPassword));
	}
	
	public boolean logout() {
		
		this.clean();
		return true;
	}
	
	private void clean() {
		
		this.username = null;
		this.password = null;
		this.firstname = null;
		this.lastname = null;
		this.email = null;
		this.authority = -1;
	}
	
	public String getUsername() { return this.username;}
	public String getPassword() { return this.password;}
	public String getFirstname() {return this.firstname;}
	public String getLastname() { return this.lastname;}
	public String getEmail() 	{ return this.email;}
	public int getAuthority() 	{ return this.authority;}
	
	public boolean setUsername(String username) {
		
		if(!StringUtil.isValidUsername(username)) return false;
		
		this.username = username.toLowerCase();
		return true;
	}
	
	public boolean setPassword(String password) { 
		
		this.password = password;
		return true;
	}
	
	public boolean setFirstname(String firstname) {
		
		this.firstname = firstname.toUpperCase();
		return true;
	}
	
	public boolean setLastname(String lastname) { 
		
		this.lastname = lastname.toUpperCase();
		return true;
	}
	
	public boolean setEmail(String email) { 
		
		if(!StringUtil.isValidEmail(email)) return false;
		
		this.email = email.toLowerCase();
		return true;
	}
	
	public boolean setAuthority(int authority) {
		
		if(authority < 0) return false;
		
		this.authority = authority;
		return true;
	}
}
