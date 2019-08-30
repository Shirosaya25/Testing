package com.revature.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.revature.dao.UserAccountDao;
import com.revature.models.UserAccount;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {

	@InjectMocks
	private UserAccountService service;
	
	@Mock
	private UserAccountDao dao;
	
	@Before
	public void setUp() {
		
		//setup when and thenReturn statements
		UserAccount tester = new UserAccount("tester", "password", "First", "Last", "test@test.com");
		when(dao.getUserAccountByUsername("tester")).thenReturn(tester);
		when(dao.getUserAccountByEmail("test@test.com")).thenReturn(tester);
		when(dao.getUserAccountByUsername("tester2")).thenReturn(null);
	}
	
	@Test
	public void getUserAccountByValidUsername() {
		
		assertEquals(new UserAccount("tester", "password", "First", "Last", "test@test.com"), service.getUserAccountByUsername("tester"));
	}
	
	//Log In Tests
	
	@Test
	public void validLoginUsername() {
		
		assertEquals(new UserAccount("tester", "password", "First", "Last", "test@test.com"), service.logInUserAccount("Tester", "password"));
	}
	
	@Test
	public void validLogInEmail() {
		
		assertEquals(new UserAccount("tester", "password", "First", "Last", "test@test.com"), service.logInUserAccount("tEsT@test.coM", "password"));
	}
	
	@Test
	public void invalidLoginWrongPassword() {
		
		assertEquals(null, service.logInUserAccount("tester", "passworD"));
	}
	
	@Test
	public void noUserFound() {
		
		assertEquals(null, service.logInUserAccount("tester2", "anything"));
	}
	
	@Test
	public void invalidUsername() {
		
		assertEquals(null, service.logInUserAccount("Invalid Username", "anything"));
	}
	
}
