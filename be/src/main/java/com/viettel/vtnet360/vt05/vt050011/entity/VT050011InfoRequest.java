package com.viettel.vtnet360.vt05.vt050011.entity;

import java.util.Date;

public class VT050011InfoRequest {

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

	private String unitName;

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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public VT050011InfoRequest(String fullName, String phoneNumber, String reason, Date appointmentTime, int status,
			String unitName) {
		super();
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.reason = reason;
		this.appointmentTime = appointmentTime;
		this.status = status;
		this.unitName = unitName;
	}

	public VT050011InfoRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}
