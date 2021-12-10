package com.viettel.vtnet360.stationery.service;

import java.util.Date;

public class ItemModel {

	private int      quantity;
	private String   stationeryId;
	private Date     createDate;
	private int      totalRequest;
	private String   fullName;
	private double   totalMoney;
	private int      totalFullfill;
	private double   totalMoneyFullfill;
	private String   stationeryName;
	private double   unitPrice;
	private String   unitName;
	private String   issuesStationeryItemId;
	private String 	 requestID;
	
	
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getIssuesStationeryItemId() { return issuesStationeryItemId; }
	public void setIssuesStationeryItemId(String itemId) { this.issuesStationeryItemId = itemId; }

	public String getUnitName() { return unitName; }
	public void setUnitName(String unitName) { this.unitName = unitName; }

	public int getQuantity() { return quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; }

	public String getStationeryId() { return stationeryId; }
	public void setStationeryId(String stationeryId) { this.stationeryId = stationeryId; }

	public Date getCreateDate() { return createDate; }
	public void setCreateDate(Date createDate) { this.createDate = createDate; }

	public int getTotalRequest() { return totalRequest; }
	public void setTotalRequest(int totalRequest) { this.totalRequest = totalRequest; }

	public String getFullName() { return fullName; }
	public void setFullName(String fullName) { this.fullName = fullName; }

	public double getTotalMoney() { return totalMoney; }
	public void setTotalMoney(double totalMoney) { this.totalMoney = totalMoney; }

	public int getTotalFullfill() { return totalFullfill; }
	public void setTotalFullfill(int totalFullfill) { this.totalFullfill = totalFullfill; }

	public double getTotalMoneyFullfill() { return totalMoneyFullfill; }
	public void setTotalMoneyFullfill(double totalMoneyFullfill) { this.totalMoneyFullfill = totalMoneyFullfill; }

	public String getStationeryName() { return stationeryName; }
	public void setStationeryName(String stationeryName) { this.stationeryName = stationeryName; }

	public double getUnitPrice() { return unitPrice; }
	public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
}
