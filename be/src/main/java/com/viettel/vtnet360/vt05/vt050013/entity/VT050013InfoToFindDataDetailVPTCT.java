package com.viettel.vtnet360.vt05.vt050013.entity;

public class VT050013InfoToFindDataDetailVPTCT {

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ID */
	private String issuesStationeryApprovedId;
	
	/** M_SYSTEM_CODE.MASTER_CLASS */
	private String masterClass;

	public String getIssuesStationeryApprovedId() {
		return issuesStationeryApprovedId;
	}

	public void setIssuesStationeryApprovedId(String issuesStationeryApprovedId) {
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
	}

	public String getMasterClass() {
		return masterClass;
	}

	public void setMasterClass(String masterClass) {
		this.masterClass = masterClass;
	}

	public VT050013InfoToFindDataDetailVPTCT(String issuesStationeryApprovedId, String masterClass) {
		super();
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
		this.masterClass = masterClass;
	}

	public VT050013InfoToFindDataDetailVPTCT() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
