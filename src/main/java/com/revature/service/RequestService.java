package com.revature.service;

import java.util.List;

import com.revature.dao.RequestDao;
import com.revature.dao.impl.RequestDaoImpl;
import com.revature.model.Employee;
import com.revature.model.Request;

public class RequestService {

	private RequestDao dao = new RequestDaoImpl();
	
	public int getNextRequestId() {
		
		return dao.getNextRequestId();
	}
	
	public List<Request> getRequestsByApplicant(Employee employee) {
		
		return dao.getRequestsByApplicant(employee.getUsername());
	}
	
	public List<Request> getRequestsByAuthority(int authority) {
		
		return dao.getRequestsByAuthority(authority);
	}
	
	public boolean createRequest(Request request) {
		
		return dao.createRequest(request) > 0;
	}
	
	public boolean updateRequest(Request request) {
		
		return dao.updateRequest(request) > 0;
	}
}
