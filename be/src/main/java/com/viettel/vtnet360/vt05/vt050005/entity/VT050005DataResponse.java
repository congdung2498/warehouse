package com.viettel.vtnet360.vt05.vt050005.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT050005DataResponse {

	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private String requestID;

	/** QLDV_EMPLOYEE.FULL_NAME on ISSUES_STATIONERY_APPROVED.HCDV_USERNAME */
	private String fullName;

	/** ISSUES_STATIONERY_APPROVED.CREATE_DATE */
	private Date dateRequest;

	/** ISSUES_STATIONERY_APPROVED.MESSAGE */
	private String message;
	
	private int status;

	public VT050005DataResponse() {

	}

	
	public VT050005DataResponse(String requestID, String fullName, Date dateRequest, String message, int status) {
		super();
		this.requestID = requestID;
		this.fullName = fullName;
		this.dateRequest = dateRequest;
		this.message = message;
		this.status = status;
	}

	
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(Date dateRequest) {
		this.dateRequest = dateRequest;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
