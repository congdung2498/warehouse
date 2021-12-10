package com.viettel.vtnet360.issuesService.entity;

import java.util.Date;

public class IssuesStationeryApprovedStatus {

	private int status ;
	private Date ldApprovedDate;
	private String approveStationeryId;
	
	
	
	public String getApproveStationeryId() {
		return approveStationeryId;
	}
	public void setApproveStationeryId(String approveStationeryId) {
		this.approveStationeryId = approveStationeryId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Date getLdApprovedDate() {
		return ldApprovedDate;
	}
	public void setLdApprovedDate(Date ldApprovedDate) {
		this.ldApprovedDate = ldApprovedDate;
	}
	
	public IssuesStationeryApprovedStatus(int status, Date ldApprovedDate, String approveStationeryId) {
		super();
		this.status = status;
		this.ldApprovedDate = ldApprovedDate;
		this.approveStationeryId = approveStationeryId;
	}
	public IssuesStationeryApprovedStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
