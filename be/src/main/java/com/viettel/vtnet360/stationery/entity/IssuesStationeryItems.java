package com.viettel.vtnet360.stationery.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class IssuesStationeryItems extends BaseEntity {
	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ITEM_ID */
	private String issuesStationeryItemID;

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ID */
	private String issuesStationeryID;

	/** ISSUES_STATIONERY_ITEMS.STATIONERY_ID */
	private String stationeryId;

	/** ISSUES_STATIONERY_ITEMS.TOTAL_REQUEST */
	private int totalRequest;

	/** ISSUES_STATIONERY_ITEMS.TOTAL_FULFILL */
	private int totalFulFill;

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_APPROVED_ID */
	private String issuesStationeryApprovedID;

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_REGISTRY */
	private String issuesStationeryRegistryID;

	/** ISSUES_STATIONERY_ITEMS.STATUS */
	private int status;

	public String getIssuesStationeryItemID() {
		return issuesStationeryItemID;
	}

	public void setIssuesStationeryItemID(String issuesStationeryItemID) {
		this.issuesStationeryItemID = issuesStationeryItemID;
	}

	public String getIssuesStationeryID() {
		return issuesStationeryID;
	}

	public void setIssuesStationeryID(String issuesStationeryID) {
		this.issuesStationeryID = issuesStationeryID;
	}

	public String getStationeryId() {
		return stationeryId;
	}

	public void setStationeryId(String stationeryId) {
		this.stationeryId = stationeryId;
	}

	public int getTotalRequest() {
		return totalRequest;
	}

	public void setTotalRequest(int totalRequest) {
		this.totalRequest = totalRequest;
	}

	public int getTotalFulFill() {
		return totalFulFill;
	}

	public void setTotalFulFill(int totalFulFill) {
		this.totalFulFill = totalFulFill;
	}

	public String getIssuesStationeryApprovedID() {
		return issuesStationeryApprovedID;
	}

	public void setIssuesStationeryApprovedID(String issuesStationeryApprovedID) {
		this.issuesStationeryApprovedID = issuesStationeryApprovedID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public IssuesStationeryItems(String issuesStationeryItemID, String issuesStationeryID, String stationeryId,
			int totalRequest, int totalFulFill, String issuesStationeryApprovedID, String issuesStationeryRegistryID,
			int status) {
		super();
		this.issuesStationeryItemID = issuesStationeryItemID;
		this.issuesStationeryID = issuesStationeryID;
		this.stationeryId = stationeryId;
		this.totalRequest = totalRequest;
		this.totalFulFill = totalFulFill;
		this.issuesStationeryApprovedID = issuesStationeryApprovedID;
		this.issuesStationeryRegistryID = issuesStationeryRegistryID;
		this.status = status;
	}

	public IssuesStationeryItems() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
