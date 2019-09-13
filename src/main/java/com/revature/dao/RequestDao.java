package com.revature.dao;

import java.util.List;

import com.revature.model.Request;

public interface RequestDao {
	
	public int getNextRequestId();
	
	public Request getRequestById(int requestid);
	public List<Request> getRequestsByApplicant(String applicant);
	public List<Request> getRequestsByAuthority(int authority);
	
	public int createRequest(Request request);
	public int updateRequest(Request request);
	public int deleteRequest(Request request);

}
