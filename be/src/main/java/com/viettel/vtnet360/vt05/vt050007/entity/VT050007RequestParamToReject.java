package com.viettel.vtnet360.vt05.vt050007.entity;

import java.util.List;

public class VT050007RequestParamToReject {

	/** ISSUES_STATIONERY_APPROVED.VPTCT_SEND_REJECT */
	private String reason;
	
	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private List<String> listRequestID;
	
	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID & ISSUES_STATIONERY_ITEMS.UPDATE_USER */
	private String vptctUserName;
	
	/** use for update status in ISSUES_STATIONERY_APPROVED & ISSUES_STATIONERY_ITEMS */
	private int status;
	
	public VT050007RequestParamToReject() {
	
	}

	public VT050007RequestParamToReject(String reason, List<String> listRequestID, String vptctUserName, int status) {
		super();
		this.reason = reason;
		this.listRequestID = listRequestID;
		this.vptctUserName = vptctUserName;
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<String> getListRequestID() {
		return listRequestID;
	}

	public void setListRequestID(List<String> listRequestID) {
		this.listRequestID = listRequestID;
	}

	public String getVptctUserName() {
		return vptctUserName;
	}

	public void setVptctUserName(String vptctUserName) {
		this.vptctUserName = vptctUserName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
