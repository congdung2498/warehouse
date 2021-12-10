package com.viettel.vtnet360.vt05.vt050003.entity;

import java.util.Date;

public class IssuesStationeryItems {
		
	private String issuesStationeryItemId ;
	private String issuesStationeryId ;
	private String stationeryId ;
	private int totalRequest ;
	private int totalFulfill ;
	private double unitPrice ;
	private Date dateFulfill ;
	private String issuesStationeryApprovedId ;
	private int status ;
	private String reasonReject ;
	private String unitName;
	
	
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getIssuesStationeryItemId() {
		return issuesStationeryItemId;
	}
	public void setIssuesStationeryItemId(String issuesStationeryItemId) {
		this.issuesStationeryItemId = issuesStationeryItemId;
	}
	public String getIssuesStationeryId() {
		return issuesStationeryId;
	}
	public void setIssuesStationeryId(String issuesStationeryId) {
		this.issuesStationeryId = issuesStationeryId;
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
	public int getTotalFulfill() {
		return totalFulfill;
	}
	public void setTotalFulfill(int totalFulfill) {
		this.totalFulfill = totalFulfill;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Date getDateFulfill() {
		return dateFulfill;
	}
	public void setDateFulfill(Date dateFulfill) {
		this.dateFulfill = dateFulfill;
	}
	public String getIssuesStationeryApprovedId() {
		return issuesStationeryApprovedId;
	}
	public void setIssuesStationeryApprovedId(String issuesStationeryApprovedId) {
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getReasonReject() {
		return reasonReject;
	}
	public void setReasonReject(String reasonReject) {
		this.reasonReject = reasonReject;
	}
	
	public IssuesStationeryItems(String issuesStationeryItemId, String issuesStationeryId, String stationeryId,
			int totalRequest, int totalFulfill, double unitPrice, Date dateFulfill, String issuesStationeryApprovedId,
			int status, String reasonReject, String unitName) {
		super();
		this.issuesStationeryItemId = issuesStationeryItemId;
		this.issuesStationeryId = issuesStationeryId;
		this.stationeryId = stationeryId;
		this.totalRequest = totalRequest;
		this.totalFulfill = totalFulfill;
		this.unitPrice = unitPrice;
		this.dateFulfill = dateFulfill;
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
		this.status = status;
		this.reasonReject = reasonReject;
		this.unitName = unitName;
	}
	public IssuesStationeryItems() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
