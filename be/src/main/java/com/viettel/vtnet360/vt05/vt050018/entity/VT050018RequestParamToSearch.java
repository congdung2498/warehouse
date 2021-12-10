package com.viettel.vtnet360.vt05.vt050018.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050018RequestParamToSearch extends BaseEntity {

	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private String requestID;

	public VT050018RequestParamToSearch() {

	}

	public VT050018RequestParamToSearch(String requestID) {
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
