package com.revature.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.RequestDao;
import com.revature.model.Request;
import com.revature.util.ConnectionUtil;
import com.revature.util.LoggerUtil;

public class RequestDaoImpl implements RequestDao {
	
	private static final String TABLE_NAME = "Request";
	
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
	
	private static final String FUNC_1 = "nextRequestId";
	
	private List<Request> getRequestsFromResultSet(PreparedStatement statement) {
		
		List<Request> ret = new ArrayList<Request>();
		
		try (ResultSet results = statement.executeQuery()){
			
			while(results.next()) {
				
				ret.add(new Request(results));
			}
		}
		
		catch (SQLException e) {
			
			LoggerUtil.log.warn(e.getMessage());
		}
		
		return ret;
	}

	/**
	 * Calls database function to get the current account id serial and returns the next value
	 * @return integer representing the next requestid
	 */
	@Override
	public int getNextRequestId() {

		int ret = 1;
		
		String sql = String.format("{? = call \"%s\"()}", FUNC_1);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try(CallableStatement statement = conn.prepareCall(sql)){
			
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.execute();
			
			ret += statement.getInt(1);
			
			conn.close();
		}
		
		catch(SQLException e) {
			
			LoggerUtil.log.error(e.getMessage());
		}
		
		return ret;
	}
	
	/**
	 * Gets an individual request via unique serial ID
	 * @param requestIdParam: Unique serial id
	 * @return Request Object corresponding to given id within the database or Null
	 */
	@Override
	public Request getRequestById(int requestIdParam) {

		Request ret = null;
		
		String sql = String.format("select * from \"%s\" where \"%s\" = ?",
									TABLE_NAME, COLUMN_1);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try(PreparedStatement statement = conn.prepareCall(sql)){
			
			statement.setInt(1,  requestIdParam);
			
			ret = getRequestsFromResultSet(statement).get(0);
			
		}
		
		catch(SQLException e) {
			
			LoggerUtil.log.error(e.getMessage());
		}
		
		return ret;
		
	}
	
	/**
	 * Gets all request submitted by a given applicant
	 * @param applicantParam: username of applicant
	 * @return list of Request objects
	 */
	@Override
	public List<Request> getRequestsByApplicant(String applicantParam) {
		
		List<Request> ret = new ArrayList<Request>();
		
		String sql = String.format("select * from \"%s\" where \"%s\" = ? order by \"%s\" desc",
									TABLE_NAME, COLUMN_2, COLUMN_8);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try(PreparedStatement statement = conn.prepareStatement(sql)){
			
			statement.setString(1, applicantParam);
			
			ret = getRequestsFromResultSet(statement);
		}
		
		catch(SQLException e) {
			
			LoggerUtil.log.error(e.getMessage());
		}
		
		return ret;
	}
	
	public List<Request> getRequestsByAuthority(int authority) {
		
		List<Request> ret = new ArrayList<Request>();
		
		String sql = String.format("select * from \"%s\" where \"%s\" <= ? order by \"%s\" desc",
									TABLE_NAME, COLUMN_4, COLUMN_8);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try(PreparedStatement statement = conn.prepareStatement(sql)){
			
			statement.setInt(1, authority);
			
			ret = getRequestsFromResultSet(statement);
		}
		
		catch(SQLException e) {
			
			LoggerUtil.log.error(e.getMessage());
		}
		
		return ret;
	}

	/**
	 * Creates a new entry in the database from given Request object
	 * @param request: Object representing an entry in the database
	 * @return integer denoting how many updates happened in the database
	 */
	@Override
	public int createRequest(Request request) {
		
		int ret = 0;
		
		String sql = String.format("insert into \"%s\"(\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\")"
								 + "values(?, ?, ?, ?, ?, ?, ?, ?, ?)",
								 	TABLE_NAME, COLUMN_2, COLUMN_3, COLUMN_4, COLUMN_5, COLUMN_6, COLUMN_7, COLUMN_8, COLUMN_9, COLUMN_10);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try(PreparedStatement statement = conn.prepareStatement(sql)){
			
			statement.setString(1, request.getApplicant());
			statement.setInt(2, request.getStatus());
			statement.setInt(3, request.getTicketLevel());
			statement.setDouble(4, request.getAmount());
			statement.setString(5, request.getDescription());
			statement.setString(6, request.getReference());
			statement.setTimestamp(7, request.getSubmissionDate());
			statement.setTimestamp(8, request.getResolutionDate());
			statement.setString(9, request.getResolvedBy());
			
			ret = statement.executeUpdate();
			
			conn.close();
		}
		
		catch(SQLException e) {
			
			LoggerUtil.log.error(e.getMessage());
		}
		
		return ret;
	}

	/**
	 * Updates an entry in the database to match the given Request object
	 * @param request: object representing an entry in the database
	 * @return integer denoting the number of updates that happened in the database
	 */
	@Override
	public int updateRequest(Request request) {
		
		int ret = 0;
		
		String sql = String.format("update \"%s\" set \"%s\" = ?, \"%s\" = ?, \"%s\" = ?, \"%s\" = ?,"
								 + "\"%s\" = ?, \"%s\" = ?, \"%s\" = ?, \"%s\" = ?, \"%s\" = ?,"
								 + "\"%s\" = ?, \"%s\" = ?"
								 + "where \"%s\" = ?",
								 	TABLE_NAME, COLUMN_2, COLUMN_3, COLUMN_4, COLUMN_5, 
								 	COLUMN_6, COLUMN_7, COLUMN_8, COLUMN_9, COLUMN_10,
								 	COLUMN_11, COLUMN_12,
								 	COLUMN_1);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try(PreparedStatement statement = conn.prepareStatement(sql)){
			
			statement.setString(1, request.getApplicant());
			statement.setInt(2, request.getStatus());
			statement.setInt(3, request.getTicketLevel());
			statement.setDouble(4, request.getAmount());
			statement.setString(5, request.getDescription());
			statement.setString(6, request.getReference());
			statement.setTimestamp(7, request.getSubmissionDate());
			statement.setTimestamp(8, request.getResolutionDate());
			statement.setString(9, request.getResolvedBy());
			statement.setString(10, request.getResolutionDescription());
			statement.setBoolean(11, request.getApproved());
			statement.setInt(12, request.getRequestId());
			
			ret = statement.executeUpdate();
			
			conn.close();
		}
		
		catch(SQLException e) {
			
			LoggerUtil.log.error(e.getMessage());
		}
		
		return ret;
	}

	/**
	 * Deletes an entry in the atabase whose requestId is equal to the given Request object's
	 * @param request: object representing an entry in the database
	 * @return integer denoting the number of updates that happened in the database
	 */
	@Override
	public int deleteRequest(Request request) {

		int ret = 0;
		
		String sql = String.format("delete from \"%s\" where \"%s\" = ?", TABLE_NAME, COLUMN_1);
		
		Connection conn = ConnectionUtil.getConnection();
		
		try(PreparedStatement statement = conn.prepareStatement(sql)){
			
			statement.setInt(1, request.getRequestId());
			
			ret = statement.executeUpdate();
			
			conn.close();
		}
		
		catch(SQLException e) {
			
			LoggerUtil.log.error(e.getMessage());
		}
		
		return ret;
	}

}
