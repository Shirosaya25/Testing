package com.revature.models;

import java.io.Serializable;
import com.revature.util.StringUtil;

public class UserAccount implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	
	private boolean loggedin = false;
	
	public UserAccount() { 
		
		super(); 
	}
	
	public UserAccount(String username, String password, String firstname, String lastname, String email) {
		
		this();
		
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}
	
	public boolean logIn(String password) {
		
		if(password.contentEquals(this.getPassword())) {
			
			this.loggedin = true;
			return true;
		}
		
		return false;
	}
	
	public boolean logOut() {
		
		if(!this.loggedin) {
			
			System.out.println("Log Out Failed: No user currently logged in.");
			return false;
		}
		
		this.clean();
		System.out.println("Successfully logged out.");
		return true;
	}
	
	public String getUsername() 	{ return this.username;}
	public String getPassword() 	{ return this.password;}
	public String getFirstname() 	{ return this.firstname;}
	public String getLastname() 	{ return this.lastname;}
	public String getEmail() 		{ return this.email;}
	
	public boolean isLoggedIn() 	{ return this.loggedin;}
	
	public boolean setUsername(String username) {
		
		if(StringUtil.isValidUsername(username)) {
			
			this.username = username.toLowerCase();
			return true;
		}
		
		System.out.println("Username Change Failed: Invalid Username");
		return false;
	}
	
	public boolean setPassword(String password) {
		
		if(this.loggedin) {
			
			this.password = password;
			return true;
		}
		
		System.out.println("Password Change Failed: No Valid Login");
		return false;
	}
	
	public boolean setFirstName(String firstname) {
		
		this.firstname = firstname;
		return true;
	}
	
	public boolean setLastName(String lastname) {
		
		this.lastname = lastname;
		return true;
	}
	
	public boolean setEmail(String email) {
		
		if(StringUtil.isValidEmail(email)){
			
			this.email = email.toLowerCase();
			return true;
		}
		
		System.out.println("Email Change Failed: Invalid Email.");
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	private void clean() {
		
		this.username = null;
		this.password = null;
		this.firstname = null;
		this.lastname = null;
		this.email = null;
		
		this.loggedin = false;
	}
}
