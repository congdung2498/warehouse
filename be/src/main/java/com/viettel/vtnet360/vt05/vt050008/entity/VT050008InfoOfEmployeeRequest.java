package com.viettel.vtnet360.vt05.vt050008.entity;

/**
 * @author DuyNK
 *
 */
public class VT050008InfoOfEmployeeRequest {

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ID */
	private String issuesStationeryID;
	
	/** ISSUES_STATIONERY_ITEMS.STATIONERY_NAME */
	private String stationeryName;
	
	/** ISSUES_STATIONERY_ITEMS.CREATE_USER */
	private String employeeUserName;
	
	public VT050008InfoOfEmployeeRequest() {
	
	}

	public VT050008InfoOfEmployeeRequest(String issuesStationeryID, String stationeryName, String employeeUserName) {
		super();
		this.issuesStationeryID = issuesStationeryID;
		this.stationeryName = stationeryName;
		this.employeeUserName = employeeUserName;
	}

	public String getIssuesStationeryID() {
		return issuesStationeryID;
	}

	public void setIssuesStationeryID(String issuesStationeryID) {
		this.issuesStationeryID = issuesStationeryID;
	}

	public String getStationeryName() {
		return stationeryName;
	}

	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}

	public String getEmployeeUserName() {
		return employeeUserName;
	}

	public void setEmployeeUserName(String employeeUserName) {
		this.employeeUserName = employeeUserName;
	}
}
