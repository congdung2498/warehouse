package com.viettel.vtnet360.vt05.vt050012.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT050012DataResponse {

	/** ISSUES_STATIONERY.ISSUE_STATIONERY_ID */
	private String requestID;

	/** ISSUES_STATIONERY.REQUEST_DATE */
	private Date dateRequest;

	/** ISSUES_STATIONERY.NOTE */
	private String message;

	/** ISSUES_STATIONERY.STATUS */
	private int status;
	
	private String placeName;

	public VT050012DataResponse() {

	}

	

	public VT050012DataResponse(String requestID, Date dateRequest, String message, int status, String placeName) {
		super();
		this.requestID = requestID;
		this.dateRequest = dateRequest;
		this.message = message;
		this.status = status;
		this.placeName = placeName;
	}


	public String getPlaceName() {
		return placeName;
	}



	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}



	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}