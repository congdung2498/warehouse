package com.viettel.vtnet360.vt05.vt050018.entity;

/**
 * @author DuyNK
 *
 */
public class VT050018InfoToCheckConditionBeforeUpdateIssuesApprove {

	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private String issuesStationeryApproveID;
	
	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private int status;
	
	public VT050018InfoToCheckConditionBeforeUpdateIssuesApprove() {
	
	}

	public VT050018InfoToCheckConditionBeforeUpdateIssuesApprove(String issuesStationeryApproveID, int status) {
		super();
		this.issuesStationeryApproveID = issuesStationeryApproveID;
		this.status = status;
	}

	public String getIssuesStationeryApproveID() {
		return issuesStationeryApproveID;
	}

	public void setIssuesStationeryApproveID(String issuesStationeryApproveID) {
		this.issuesStationeryApproveID = issuesStationeryApproveID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
