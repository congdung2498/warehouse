package com.viettel.vtnet360.vt05.vt050013.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050013RequestParam extends BaseEntity {

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ID */
	private String requestID;
	
	private String userName;
	
	public VT050013RequestParam() {

	}

	
	public VT050013RequestParam(String requestID, String userName) {
		super();
		this.requestID = requestID;
		this.userName = userName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
}
