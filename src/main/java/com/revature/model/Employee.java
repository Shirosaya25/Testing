package com.revature.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.util.EncryptionUtil;
import com.revature.util.StringUtil;

public class Employee {

	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private int authority;
	
	private static final String COLUMN_1 = "Username";
	private static final String COLUMN_2 = "Password";
	private static final String COLUMN_3 = "FirstName";
	private static final String COLUMN_4 = "LastName";
	private static final String COLUMN_5 = "Email";
	private static final String COLUMN_6 = "Authority";
	
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
	
	public Employee(ResultSet results) throws SQLException {
		
		this.username = results.getString(COLUMN_1);
		this.password = results.getString(COLUMN_2);
		this.firstname = results.getString(COLUMN_3);
		this.lastname = results.getString(COLUMN_4);
		this.email = results.getString(COLUMN_5);
		this.authority = results.getInt(COLUMN_6);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + authority;
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
		Employee other = (Employee) obj;
		if (authority != other.authority)
			return false;
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
	
	
}
