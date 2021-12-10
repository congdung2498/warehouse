package com.viettel.vtnet360.vt05.vt050018.entity;

public class VT050018InfoToUpdateIssuesStaioneryApprove {

	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private String requestID;

	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private int status;

	/**
	 * use for update ISSUES_STATIONERY_APPROVED.UPDATE_USER when hcdv confirm
	 * received stationery from VPTCT
	 */
	private String hcdvUserName;

	/** ISSUES_STATIONERY_APPROVED.STATUS condition to update status */
	private int statusNow;
	
	private String hcdvReasonReject;
	
	public VT050018InfoToUpdateIssuesStaioneryApprove() {

	}

	
	public VT050018InfoToUpdateIssuesStaioneryApprove(String requestID, int status, String hcdvUserName, int statusNow,
			String hcdvReasonReject) {
		super();
		this.requestID = requestID;
		this.status = status;
		this.hcdvUserName = hcdvUserName;
		this.statusNow = statusNow;
		this.hcdvReasonReject = hcdvReasonReject;
	}

	

	public String getHcdvReasonReject() {
		return hcdvReasonReject;
	}


	public void setHcdvReasonReject(String hcdvReasonReject) {
		this.hcdvReasonReject = hcdvReasonReject;
	}


	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHcdvUserName() {
		return hcdvUserName;
	}

	public void setHcdvUserName(String hcdvUserName) {
		this.hcdvUserName = hcdvUserName;
	}

	public int getStatusNow() {
		return statusNow;
	}

	public void setStatusNow(int statusNow) {
		this.statusNow = statusNow;
	}	
}
