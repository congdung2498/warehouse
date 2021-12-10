package com.viettel.vtnet360.vt05.vt050005.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050005RequestParamToApprove extends BaseEntity {

	/** 0:reject 1:approve */
	private int action;

	/** list ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private List<String> listRequestID;

	/** ISSUES_STATIONERY_APPROVED.REASON_REJECT */
	private String reasonReject;

	/** ISSUES_STATIONERY_APPROVED.APPROVED_USERNAME */
	private String approveUserName;

	public VT050005RequestParamToApprove() {

	}

	public VT050005RequestParamToApprove(int action, List<String> listRequestID, String reasonReject,
			String approveUserName) {
		super();
		this.action = action;
		this.listRequestID = listRequestID;
		this.reasonReject = reasonReject;
		this.approveUserName = approveUserName;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public List<String> getListRequestID() {
		return listRequestID;
	}

	public void setListRequestID(List<String> listRequestID) {
		this.listRequestID = listRequestID;
	}

	public String getReasonReject() {
		return reasonReject;
	}

	public void setReasonReject(String reasonReject) {
		this.reasonReject = reasonReject;
	}

	public String getApproveUserName() {
		return approveUserName;
	}

	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}
}
