package com.viettel.vtnet360.vt05.vt050011.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT050011InfoToInsertStationeryItemHistory {

	/** ISSUES_STATIONERY_ITEM_HISTORY.ISSUES_STATIONERY_APPROVED_ID */
	private String requestID;
	
	/** ISSUES_STATIONERY_ITEM_HISTORY.POSTPONE_TO_TIME */
	private Date postponeToTime;
	
	/** ISSUES_STATIONERY_ITEM_HISTORY.REASON */
	private String reason;
	
	/** ISSUES_STATIONERY_ITEM_HISTORY.CREATE_USER */
	private String createUser;
	
	/** ISSUES_STATIONERY_ITEM_HISTORY.CREATE_DATE */
	private Date createDate;
	
	public VT050011InfoToInsertStationeryItemHistory() {
	
	}

	public VT050011InfoToInsertStationeryItemHistory(String requestID, Date postponeToTime, String reason,
			String createUser, Date createDate) {
		super();
		this.requestID = requestID;
		this.postponeToTime = postponeToTime;
		this.reason = reason;
		this.createUser = createUser;
		this.createDate = createDate;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public Date getPostponeToTime() {
		return postponeToTime;
	}

	public void setPostponeToTime(Date postponeToTime) {
		this.postponeToTime = postponeToTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
