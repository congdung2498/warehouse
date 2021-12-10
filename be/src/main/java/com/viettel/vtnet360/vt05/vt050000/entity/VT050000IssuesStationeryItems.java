package com.viettel.vtnet360.vt05.vt050000.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

/**
 * @author DuyNK 05/10/2018
 */
public class VT050000IssuesStationeryItems extends BaseEntity {

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ITEM_ID */
	private String issuesStationeryItemID;

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ID */
	private String issuesStationeryID;

	/** ISSUES_STATIONERY_ITEMS.STATIONERY_ID */
	private String stationeryID;

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

	public VT050000IssuesStationeryItems() {

	}

	public VT050000IssuesStationeryItems(String issuesStationeryItemID, String issuesStationeryID, String stationeryID,
			int totalRequest, int totalFulFill, String issuesStationeryApprovedID, String issuesStationeryRegistryID,
			int status) {
		super();
		this.issuesStationeryItemID = issuesStationeryItemID;
		this.issuesStationeryID = issuesStationeryID;
		this.stationeryID = stationeryID;
		this.totalRequest = totalRequest;
		this.totalFulFill = totalFulFill;
		this.issuesStationeryApprovedID = issuesStationeryApprovedID;
		this.issuesStationeryRegistryID = issuesStationeryRegistryID;
		this.status = status;
	}

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

	public String getStationeryID() {
		return stationeryID;
	}

	public void setStationeryID(String stationeryID) {
		this.stationeryID = stationeryID;
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

	public String getIssuesStationeryRegistryID() {
		return issuesStationeryRegistryID;
	}

	public void setIssuesStationeryRegistryID(String issuesStationeryRegistryID) {
		this.issuesStationeryRegistryID = issuesStationeryRegistryID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
