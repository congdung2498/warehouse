package com.viettel.vtnet360.vt05.vt050005.entity;

/**
 * @author DuyNK
 *
 */
public class VT050005InfoOfEmployeeRequest {

	/** ISSUES_STATIONERY_ITEMS.CREATE_USER */
	private String employeeUserName;

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ID */
	private String stationeryID;

	/** ISSUES_STATIONERY_ID.STATUS */
	private int status;

	public VT050005InfoOfEmployeeRequest() {

	}

	public VT050005InfoOfEmployeeRequest(String employeeUserName, String stationeryID, int status) {
		super();
		this.employeeUserName = employeeUserName;
		this.stationeryID = stationeryID;
		this.status = status;
	}

	public String getEmployeeUserName() {
		return employeeUserName;
	}

	public void setEmployeeUserName(String employeeUserName) {
		this.employeeUserName = employeeUserName;
	}

	public String getStationeryID() {
		return stationeryID;
	}

	public void setStationeryID(String stationeryID) {
		this.stationeryID = stationeryID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
