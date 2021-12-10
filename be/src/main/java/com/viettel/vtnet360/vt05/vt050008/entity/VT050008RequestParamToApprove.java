package com.viettel.vtnet360.vt05.vt050008.entity;

import java.util.List;

/**
 * @author DuyNK
 *
 */
public class VT050008RequestParamToApprove {

	/**
	 * 0:reject 1:approve (Constant.STATIONERY_ACTION_APPROVE &
	 * Constant.STATIONERY_ACTION_REJECT)
	 */
	private int action;

	/** list ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private List<String> listRequestID;

	/** ISSUES_STATIONERY_APPROVED.CVP_REASON_REJECT */
	private String reasonReject;

	/** ISSUES_STATIONERY_APPROVED.CVP_USERNAME */
	private String cvpUserName;

	/**
	 * use for update status in ISSUES_STATIONERY_APPROVED & ISSUES_STATIONERY_ITEMS
	 */
	private int status;

	/** status now of this record */
	private int statusNow;
	
	public VT050008RequestParamToApprove() {

	}

	public VT050008RequestParamToApprove(int action, List<String> listRequestID, String reasonReject,
			String cvpUserName, int status, int statusNow) {
		super();
		this.action = action;
		this.listRequestID = listRequestID;
		this.reasonReject = reasonReject;
		this.cvpUserName = cvpUserName;
		this.status = status;
		this.statusNow = statusNow;
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

	public String getCvpUserName() {
		return cvpUserName;
	}

	public void setCvpUserName(String cvpUserName) {
		this.cvpUserName = cvpUserName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatusNow() {
		return statusNow;
	}

	public void setStatusNow(int statusNow) {
		this.statusNow = statusNow;
	}
}
