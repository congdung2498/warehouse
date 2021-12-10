package com.viettel.vtnet360.vt05.vt050009.entity;

/**
 * @author DuyNK
 *
 */
public class VT050009RequestParam {

	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private String requestID;

	public VT050009RequestParam() {

	}

	public VT050009RequestParam(String requestID) {
		super();
		this.requestID = requestID;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
}
