package com.viettel.vtnet360.vt05.vt050018.entity;

/**
 * @author DuyNK
 *
 */
public class VT050018InfoOfEmployeeRequest {

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ITEM_ID */
	private String issuesStationeryItemID;
	
	/** ISSUES_STATIONERY_ITEMS.TOTAL_FULFILL */
	private int totalRequest;
	
	public VT050018InfoOfEmployeeRequest() {
	
	}

	public VT050018InfoOfEmployeeRequest(String issuesStationeryItemID, int totalRequest) {
		super();
		this.issuesStationeryItemID = issuesStationeryItemID;
		this.totalRequest = totalRequest;
	}

	public String getIssuesStationeryItemID() {
		return issuesStationeryItemID;
	}

	public void setIssuesStationeryItemID(String issuesStationeryItemID) {
		this.issuesStationeryItemID = issuesStationeryItemID;
	}

	public int getTotalRequest() {
		return totalRequest;
	}

	public void setTotalRequest(int totalRequest) {
		this.totalRequest = totalRequest;
	}
}
