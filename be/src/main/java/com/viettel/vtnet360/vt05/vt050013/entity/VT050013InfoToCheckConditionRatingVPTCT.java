package com.viettel.vtnet360.vt05.vt050013.entity;

import java.util.List;

public class VT050013InfoToCheckConditionRatingVPTCT {


	/** ISSUES_STATIONERY_APPROVED_ID */
	private String issuesStationeryApprovedId;

	/** complete a part or complete all => can rating */
	private List<Integer> listStatus;

	public String getIssuesStationeryApprovedId() {
		return issuesStationeryApprovedId;
	}

	public void setIssuesStationeryApprovedId(String issuesStationeryApprovedId) {
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
	}

	public List<Integer> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<Integer> listStatus) {
		this.listStatus = listStatus;
	}

	public VT050013InfoToCheckConditionRatingVPTCT(String issuesStationeryApprovedId, List<Integer> listStatus) {
		super();
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
		this.listStatus = listStatus;
	}

	public VT050013InfoToCheckConditionRatingVPTCT() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
