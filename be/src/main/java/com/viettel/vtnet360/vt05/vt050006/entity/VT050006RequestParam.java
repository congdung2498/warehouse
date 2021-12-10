package com.viettel.vtnet360.vt05.vt050006.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050006RequestParam extends BaseEntity {

	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private String requestID;
	
	public VT050006RequestParam() {

	}
	public VT050006RequestParam(String requestID) {
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
