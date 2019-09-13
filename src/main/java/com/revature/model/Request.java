package com.revature.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.revature.util.StringUtil;

public class Request {
	
	private static final String COLUMN_1 = "RequestId";
	private static final String COLUMN_2 = "Applicant";
	private static final String COLUMN_3 = "Status";
	private static final String COLUMN_4 = "TicketLevel";
	private static final String COLUMN_5 = "Amount";
	private static final String COLUMN_6 = "Description";
	private static final String COLUMN_7 = "Reference";
	private static final String COLUMN_8 = "SubmissionDate";
	private static final String COLUMN_9 = "ResolutionDate";
	private static final String COLUMN_10 = "ResolvedBy";
	private static final String COLUMN_11 = "ResolutionDescription";
	private static final String COLUMN_12 = "Approved";

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
	private String resolutionDescription;
	private boolean approved;
	
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
	
	public Request(ResultSet results) throws SQLException {
		
		this.requestId = results.getInt(COLUMN_1);
		this.applicant = results.getString(COLUMN_2);
		this.status = results.getInt(COLUMN_3);
		this.ticketLevel = results.getInt(COLUMN_4);
		this.amount = results.getDouble(COLUMN_5);
		this.description = results.getString(COLUMN_6);
		this.reference = results.getString(COLUMN_7);
		this.submissionDate = results.getTimestamp(COLUMN_8);
		this.resolutionDate = results.getTimestamp(COLUMN_9);
		this.resolvedBy = results.getString(COLUMN_10);
		this.resolutionDescription = results.getString(COLUMN_11);
		this.approved = results.getBoolean(COLUMN_12);
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
	
	public void resolve(Employee employee) {
		
		this.resolvedBy = employee.getUsername();
		this.setResolutionDate();
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
	public String getResolutionDescription() { return this.resolutionDescription; }
	public boolean getApproved() { return this.approved; }
	
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
	
	public boolean setResolutionDescription(String resolutionDescription) {
		
		this.resolutionDescription = resolutionDescription;
		return true;
	}
	
	public boolean setApproved(boolean approved) {
		
		this.approved = approved;
		return true;
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
