package com.viettel.vtnet360.vt05.vt050018.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050018RequestParamToConfirm extends BaseEntity {

	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private String requestID;

	private String hcdvReasonReject;

	public VT050018RequestParamToConfirm() {

	}

	public VT050018RequestParamToConfirm(String requestID, String hcdvReasonReject) {
		super();
		this.requestID = requestID;
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
}
