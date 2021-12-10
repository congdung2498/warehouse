package com.viettel.vtnet360.vt05.vt050013.entity;

import java.util.List;

/**
 * @author DuyNK
 *
 */
public class VT050013InfoToCheckConditionRating {

	/** ISSUES_STATIONERY.ISSUE_STATIONERY_ID */
	private String issueStationeryID;

	/** complete a part or complete all => can rating */
	private List<Integer> listStatus;

	public VT050013InfoToCheckConditionRating() {

	}

	public VT050013InfoToCheckConditionRating(String issueStationeryID, List<Integer> listStatus) {
		super();
		this.issueStationeryID = issueStationeryID;
		this.listStatus = listStatus;
	}

	public String getIssueStationeryID() {
		return issueStationeryID;
	}

	public void setIssueStationeryID(String issueStationeryID) {
		this.issueStationeryID = issueStationeryID;
	}

	public List<Integer> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<Integer> listStatus) {
		this.listStatus = listStatus;
	}
}
