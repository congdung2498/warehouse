package com.viettel.vtnet360.stationery.entity;

import java.util.Date;

public class DetailsEmployeeInfo {
	
	private String issuesStationeryItemId;
	private String stationeryId;
	private int totalRequest;
	private double unitPrice;
	private int status;
	private String note;
	private String statusUnit;
	private String totalFulfill;
	private Date requestDate;
	private String fullName;
	private String email;
	private String startPlace;
	private String calUnit;
	private String calUnitId;
	private String stationeryName;
	
	
	public String getIssuesStationeryItemId() {
		return issuesStationeryItemId;
	}
	public void setIssuesStationeryItemId(String issuesStationeryItemId) {
		this.issuesStationeryItemId = issuesStationeryItemId;
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
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getStatusUnit() {
		return statusUnit;
	}
	public void setStatusUnit(String statusUnit) {
		this.statusUnit = statusUnit;
	}
	public String getTotalFulfill() {
		return totalFulfill;
	}
	public void setTotalFulfill(String totalFulfill) {
		this.totalFulfill = totalFulfill;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStartPlace() {
		return startPlace;
	}
	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}
	public String getCalUnit() {
		return calUnit;
	}
	public void setCalUnit(String calUnit) {
		this.calUnit = calUnit;
	}
	public String getCalUnitId() {
		return calUnitId;
	}
	public void setCalUnitId(String calUnitId) {
		this.calUnitId = calUnitId;
	}
	public String getStationeryName() {
		return stationeryName;
	}
	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}
	public DetailsEmployeeInfo(String issuesStationeryItemId, String stationeryId, int totalRequest, double unitPrice,
			int status, String note, String statusUnit, String totalFulfill, Date requestDate, String fullName,
			String email, String startPlace, String calUnit, String calUnitId, String stationeryName) {
		super();
		this.issuesStationeryItemId = issuesStationeryItemId;
		this.stationeryId = stationeryId;
		this.totalRequest = totalRequest;
		this.unitPrice = unitPrice;
		this.status = status;
		this.note = note;
		this.statusUnit = statusUnit;
		this.totalFulfill = totalFulfill;
		this.requestDate = requestDate;
		this.fullName = fullName;
		this.email = email;
		this.startPlace = startPlace;
		this.calUnit = calUnit;
		this.calUnitId = calUnitId;
		this.stationeryName = stationeryName;
	}
	public DetailsEmployeeInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
