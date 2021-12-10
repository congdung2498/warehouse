package com.viettel.vtnet360.vt05.vt050013.entity;

/**
 * @author DuyNK
 *
 */
public class VT050013InfoToFindDataDetail {

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ID */
	private String issuesStationeryID;
	
	/** M_SYSTEM_CODE.MASTER_CLASS */
	private String masterClass;
	
	public VT050013InfoToFindDataDetail() {
	
	}

	public VT050013InfoToFindDataDetail(String issuesStationeryID, String masterClass) {
		super();
		this.issuesStationeryID = issuesStationeryID;
		this.masterClass = masterClass;
	}

	public String getIssuesStationeryID() {
		return issuesStationeryID;
	}

	public void setIssuesStationeryID(String issuesStationeryID) {
		this.issuesStationeryID = issuesStationeryID;
	}

	public String getMasterClass() {
		return masterClass;
	}

	public void setMasterClass(String masterClass) {
		this.masterClass = masterClass;
	}
}
