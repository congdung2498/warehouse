package com.viettel.vtnet360.vt05.vt050000.entity;

import java.util.Date;

import com.viettel.vtnet360.vt00.common.BaseEntity;

/**
 * @author DuyNK 05/10/2018
 */
public class VT050000IssuesStationery extends BaseEntity {

	/** ISSUES_STATIONERY.ISSUE_STATIONERY_ID */
	private String issuesStationeryID;

	/** ISSUES_STATIONERY.EMPLOYEE_USERNAME */
	private String employeeUserName;

	/** ISSUES_STATIONERY.ISSUE_LOCATION */
	private int issueLocation;

	/** ISSUES_STATIONERY.NOTE */
	private String note;

	/** ISSUES_STATIONERY.REQUEST_DATE */
	private Date requestDate;

	/** ISSUES_STATIONERY.STATUS */
	private int status;

	/** ISSUES_STATIONERY.RATING */
	private int rating;

	/** ISSUES_STATIONERY.FEEDBACK */
	private String feedback;

	public VT050000IssuesStationery() {

	}

	public VT050000IssuesStationery(String issuesStationeryID, String employeeUserName, int issueLocation, String note,
			Date requestDate, int status, int rating, String feedback) {
		super();
		this.issuesStationeryID = issuesStationeryID;
		this.employeeUserName = employeeUserName;
		this.issueLocation = issueLocation;
		this.note = note;
		this.requestDate = requestDate;
		this.status = status;
		this.rating = rating;
		this.feedback = feedback;
	}

	public String getIssuesStationeryID() {
		return issuesStationeryID;
	}

	public void setIssuesStationeryID(String issuesStationeryID) {
		this.issuesStationeryID = issuesStationeryID;
	}

	public String getEmployeeUserName() {
		return employeeUserName;
	}

	public void setEmployeeUserName(String employeeUserName) {
		this.employeeUserName = employeeUserName;
	}

	public int getIssueLocation() {
		return issueLocation;
	}

	public void setIssueLocation(int issueLocation) {
		this.issueLocation = issueLocation;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
}
