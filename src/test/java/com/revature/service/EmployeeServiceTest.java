package com.revature.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.revature.dao.EmployeeDao;
import com.revature.model.Employee;
import com.revature.util.EncryptionUtil;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
	
	@InjectMocks
	private EmployeeService service;
	
	@Mock
	private EmployeeDao dao;
	
	@Before
	public void setup() {
		
		when(dao.getEmployeeByUsername("admin2")).thenReturn(null);
		when(dao.getEmployeeByEmail("mail2@mail.com")).thenReturn(null);
	}
	
	
	@Test
	public void loginTest4() {
		
		assertEquals(service.logInEmployee("admin2", "password123"), null);
	}
	
	@Test
	public void loginTest5() {
		
		assertEquals(service.logInEmployee("mail2@mail.com", "password123"), null);
	}
	
	@Test
	public void loginTest6() {
		
		assertEquals(service.logInEmployee(null, null), null);
	}
	
}
