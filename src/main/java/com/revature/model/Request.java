package com.revature.model;

import java.sql.Timestamp;
import java.util.Date;

import com.revature.util.StringUtil;

public class Request {

	private int requestId;
	private String applicant;
	private int status;
	private int ticketLevel;
	private double amount;
	private String description;
	private String reference;
	
	private Timestamp submissionDate;
	private Timestamp resolutionDate;
	
	private String resolvedBy;
	
	public Request() {
		
		super();
	}
	
	public Request(int requestId, String applicant, int status, int ticketLevel, double amount) {
		
		this.requestId = requestId;
		this.applicant = applicant;
		this.status = status;
		this.ticketLevel = ticketLevel;
		this.amount = amount;
		
		this.setSubmissionDate();
	}
	
	public Request(int requestId, String applicant, int status, int ticketLevel, double amount, String description, String reference, Timestamp submissionDate, Timestamp resolutionDate, String resolvedBy) {
		
		this.requestId = requestId;
		this.applicant = applicant;
		this.status = status;
		this.ticketLevel = ticketLevel;
		this.amount = amount;
		this.description = description;
		this.reference = reference;
		this.submissionDate = submissionDate;
		this.resolutionDate = resolutionDate;
		this.resolvedBy = resolvedBy;
	}
	
	public Request(Employee employee, int requestId, double amount, String description, String reference) {
		
		this.requestId = requestId;
		this.applicant = employee.getUsername();
		this.status = 0;
		this.ticketLevel = employee.getAuthority() + 1;
		this.amount = amount;
		this.description = description;
		this.reference = reference;
		
		this.setSubmissionDate();
	}
	
	public int getRequestId() { return this.requestId; }
	public String getApplicant() { return this.applicant; }
	public int getStatus() { return this.status; }
	public int getTicketLevel() { return this.ticketLevel; }
	public double getAmount() { return this.amount; }
	public String getDescription() { return this.description; }
	public String getReference() { return this.reference; }
	public Timestamp getSubmissionDate() { return this.submissionDate; }
	public Timestamp getResolutionDate() { return this.resolutionDate; }
	public String getResolvedBy() { return this.resolvedBy; }
	
	public boolean setRequestId(int requestId) {
		
		if(requestId < 0) return false;
		
		this.requestId = requestId;
		return true;
	}
	
	public boolean setApplicant(String applicant) {
		
		if(!StringUtil.isValidUsername(applicant)) return false;
		
		this.applicant = applicant.toLowerCase();
		return true;
	}
	
	public boolean setStatus(int status) {
		
		if(status < 0 || status > 2) return false;
		
		this.status = status;
		return true;
	}
	
	public boolean setTicketLevel(int ticketLevel) {
		
		if(ticketLevel < 0) return false;
		
		this.ticketLevel = ticketLevel;
		return true;
	}
	
	public boolean setAmount(double amount) {
		
		if(!StringUtil.isValidAmount(amount)) return false;
		
		this.amount = amount;
		return true;
	}
	
	public boolean setDescription(String description) {
		
		
		this.description = description;
		return true;
	}
	
	public boolean setReference(String reference) {
		
		this.reference = reference;
		return true;
	}
	
	public boolean setSubmissionDate() {
		
		this.submissionDate = new Timestamp(new Date().getTime());
		return true;
	}
	
	public boolean setSubmissionDate(Timestamp submissionDate) {
		
		this.submissionDate = submissionDate;
		return true;
	}
	
	public boolean setResolutionDate() {
		
		this.resolutionDate = new Timestamp(new Date().getTime());
		return true;
	}
	
	public boolean setResolutionDate(Timestamp resolutionDate) {
		
		this.resolutionDate = resolutionDate;
		return true;
	}
	
	public boolean setResolvedBy(String username) {
		
		if(username == null) {
			this.resolvedBy = null;
			return true;
		}
		
		if(!StringUtil.isValidUsername(username)) return false;
		
		this.resolvedBy = username;
		return true;
	}
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
