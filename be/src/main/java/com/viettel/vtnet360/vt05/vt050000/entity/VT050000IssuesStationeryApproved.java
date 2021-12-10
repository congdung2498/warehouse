package com.viettel.vtnet360.vt05.vt050000.entity;

import java.util.Date;

import com.viettel.vtnet360.vt00.common.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050000IssuesStationeryApproved extends BaseEntity {

	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private String issuesStationeryApproveID;

	/** ISSUES_STATIONERY_APPROVED.HCDV_USERNAME */
	private String hcdvUserName;

	/** ISSUES_STATIONERY_APPROVED.MESSAGE */
	private String message;

	/** ISSUES_STATIONERY_APPROVED.APPROVED_USERNAME */
	private String approveUserName;

	/** ISSUES_STATIONERY_APPROVED.APPROVED_DATE */
	private Date approveDate;

	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private int status;

	/** ISSUES_STATIONERY_APPROVED.REASON_REJECT */
	private String reasonReject;

	public VT050000IssuesStationeryApproved() {

	}

	public VT050000IssuesStationeryApproved(String issuesStationeryApproveID, String hcdvUserName, String message,
			String approveUserName, Date approveDate, int status, String reasonReject) {
		super();
		this.issuesStationeryApproveID = issuesStationeryApproveID;
		this.hcdvUserName = hcdvUserName;
		this.message = message;
		this.approveUserName = approveUserName;
		this.approveDate = approveDate;
		this.status = status;
		this.reasonReject = reasonReject;
	}

	public String getIssuesStationeryApproveID() {
		return issuesStationeryApproveID;
	}

	public void setIssuesStationeryApproveID(String issuesStationeryApproveID) {
		this.issuesStationeryApproveID = issuesStationeryApproveID;
	}

	public String getHcdvUserName() {
		return hcdvUserName;
	}

	public void setHcdvUserName(String hcdvUserName) {
		this.hcdvUserName = hcdvUserName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getApproveUserName() {
		return approveUserName;
	}

	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReasonReject() {
		return reasonReject;
	}

	public void setReasonReject(String reasonReject) {
		this.reasonReject = reasonReject;
	}
}
