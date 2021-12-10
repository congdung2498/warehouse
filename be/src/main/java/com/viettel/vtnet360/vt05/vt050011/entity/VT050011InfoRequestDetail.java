package com.viettel.vtnet360.vt05.vt050011.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT050011InfoRequestDetail {

	/** QLDV_EMPLOYEE.FULL_NAME */
	private String fullName;

	/** QLDV_EMPLOYEE.PHONE_NUMBER */
	private String phoneNumber;
	
	/** ISSUES_STATIONERY_APPROVED.VPTCT_REASON */
	private String reason;
	
	/** ISSUES_STATIONERY_APPROVED.VPTCT_POSTPONE_TO_TIME */
	private Date appointmentTime;

	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private int status;
	
	/** ISSUES_STATIONERY_ITEMS.STATIONERY_ID */
	private String stationeryID;

	/** STATIONERY_ITEMS.STATIONERY_NAME */
	private String stationeryName;

	/** ISSUES_STATIONERY_ITEMS.TOTAL_REQUEST */
	private int quantity;
	
	private String issuesStationeryId;

	public VT050011InfoRequestDetail() {

	}

	

	public VT050011InfoRequestDetail(String fullName, String phoneNumber, String reason, Date appointmentTime,
			int status, String stationeryID, String stationeryName, int quantity, String issuesStationeryId) {
		super();
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.reason = reason;
		this.appointmentTime = appointmentTime;
		this.status = status;
		this.stationeryID = stationeryID;
		this.stationeryName = stationeryName;
		this.quantity = quantity;
		this.issuesStationeryId = issuesStationeryId;
	}



	public String getIssuesStationeryId() {
		return issuesStationeryId;
	}



	public void setIssuesStationeryId(String issuesStationeryId) {
		this.issuesStationeryId = issuesStationeryId;
	}



	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStationeryID() {
		return stationeryID;
	}

	public void setStationeryID(String stationeryID) {
		this.stationeryID = stationeryID;
	}

	public String getStationeryName() {
		return stationeryName;
	}

	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
