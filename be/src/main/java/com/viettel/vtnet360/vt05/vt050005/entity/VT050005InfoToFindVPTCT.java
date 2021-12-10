package com.viettel.vtnet360.vt05.vt050005.entity;

import java.util.List;

/**
 * @author DuyNK
 *
 */
public class VT050005InfoToFindVPTCT {

	/** list ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_APPROVED_ID */
	private List<String> listIssuesStationeryApproveID;

	/** STATIONERY_STAFF.JOB_CODE */
	private String jobCode;

	public VT050005InfoToFindVPTCT() {

	}

	public VT050005InfoToFindVPTCT(List<String> listIssuesStationeryApproveID, String jobCode) {
		super();
		this.listIssuesStationeryApproveID = listIssuesStationeryApproveID;
		this.jobCode = jobCode;
	}

	public List<String> getListIssuesStationeryApproveID() {
		return listIssuesStationeryApproveID;
	}

	public void setListIssuesStationeryApproveID(List<String> listIssuesStationeryApproveID) {
		this.listIssuesStationeryApproveID = listIssuesStationeryApproveID;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
}
