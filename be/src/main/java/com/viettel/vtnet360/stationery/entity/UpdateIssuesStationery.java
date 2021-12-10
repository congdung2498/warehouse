package com.viettel.vtnet360.stationery.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

public class UpdateIssuesStationery extends BaseEntity {

	private String ldReasonReject;
	private String userName;
	private String issuesStationeryApprovedId;
	private String issueStationeryId;
	private List<String> listIssuesStationeryItemId;
	private List<String> listIssueStationeryId;
	private String issuesStationeryIdOld;
	

	public String getIssuesStationeryIdOld() {
		return issuesStationeryIdOld;
	}

	public void setIssuesStationeryIdOld(String issuesStationeryIdOld) {
		this.issuesStationeryIdOld = issuesStationeryIdOld;
	}

	public String getIssueStationeryId() {
		return issueStationeryId;
	}

	public void setIssueStationeryId(String issueStationeryId) {
		this.issueStationeryId = issueStationeryId;
	}

	public List<String> getListIssuesStationeryItemId() {
		return listIssuesStationeryItemId;
	}

	public void setListIssuesStationeryItemId(List<String> listIssuesStationeryItemId) {
		this.listIssuesStationeryItemId = listIssuesStationeryItemId;
	}

	public List<String> getListIssueStationeryId() {
		return listIssueStationeryId;
	}

	public void setListIssueStationeryId(List<String> listIssueStationeryId) {
		this.listIssueStationeryId = listIssueStationeryId;
	}

	public String getIssuesStationeryApprovedId() {
		return issuesStationeryApprovedId;
	}

	public void setIssuesStationeryApprovedId(String issuesStationeryApprovedId) {
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLdReasonReject() {
		return ldReasonReject;
	}

	public void setLdReasonReject(String ldReasonReject) {
		this.ldReasonReject = ldReasonReject;
	}

	public UpdateIssuesStationery(String ldReasonReject, String userName, String issuesStationeryApprovedId,
			String issueStationeryId, List<String> listIssuesStationeryItemId, List<String> listIssueStationeryId) {
		super();
		this.ldReasonReject = ldReasonReject;
		this.userName = userName;
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
		this.issueStationeryId = issueStationeryId;
		this.listIssuesStationeryItemId = listIssuesStationeryItemId;
		this.listIssueStationeryId = listIssueStationeryId;
	}

	public UpdateIssuesStationery() {
		super();
		// TODO Auto-generated constructor stub
	}

}
