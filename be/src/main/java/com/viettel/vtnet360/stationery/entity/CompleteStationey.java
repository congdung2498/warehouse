package com.viettel.vtnet360.stationery.entity;

public class CompleteStationey {
	
	private String issuesStationeryApprovedId;
	private int quantity;
	private String stationeryId;
	public String getIssuesStationeryApprovedId() {
		return issuesStationeryApprovedId;
	}
	public void setIssuesStationeryApprovedId(String issuesStationeryApprovedId) {
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getStationeryId() {
		return stationeryId;
	}
	public void setStationeryId(String stationeryId) {
		this.stationeryId = stationeryId;
	}
	
	public CompleteStationey(String issuesStationeryApprovedId, int quantity, String stationeryId) {
		super();
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
		this.quantity = quantity;
		this.stationeryId = stationeryId;
	}
	public CompleteStationey() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
