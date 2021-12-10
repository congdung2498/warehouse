package com.viettel.vtnet360.vt05.vt050005.entity;

/**
 * @author DuyNK
 *
 */
public class VT050005InfoToFindItemRequest {

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_APPROVED_ID */
	private String issuesStationeryApproveID;

	/** ISSUES_STATIONERY_ITEMS.CREATE_USER */
	private String userName;

	public VT050005InfoToFindItemRequest() {

	}

	public VT050005InfoToFindItemRequest(String issuesStationeryApproveID, String userName) {
		super();
		this.issuesStationeryApproveID = issuesStationeryApproveID;
		this.userName = userName;
	}

	public String getIssuesStationeryApproveID() {
		return issuesStationeryApproveID;
	}

	public void setIssuesStationeryApproveID(String issuesStationeryApproveID) {
		this.issuesStationeryApproveID = issuesStationeryApproveID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
