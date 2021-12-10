package com.viettel.vtnet360.vt05.vt050004.entity;

import java.util.Date;

public class VT050004DataResponseRequest {
	private String requestId;

	/** QLDV_PLACE.PLACE_NAME */
	private String placeName;

	/** QLDV_UNIT.UNIT_NAME */
	private String unitName;

	/** QLDV_EMPLOYEE.FULL_NAME */
	private String fullName;

	/** ISSUES_STATIONERY.EMPLOYEE_USERNAME */
	private String userName;

	/** ISSUES_STATIONERY.REQUEST_DATE */
	private Date dateRequest;

	/** STATIONERY_ITEMS.STATIONERY_NAME */
	private String stationeryName;

	/** ISSUES_STATIONERY_ITEMS.TOTAL_REQUEST */
	private int quantity;

	/** STATIONERY_ITEMS.UNIT_PRICE */
	private double price;

	/** quantity * price */
	private double totalMoney;

	private String stationeryId;

	private String phoneNumber;
	
	private String totalFulfill;
	
	private String issuesStationeryApprovedId;
	
	private int issueLocation;
	
	private String employeeUsername;
	
	private String issuesStationeryId;
	
	private int status;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(Date dateRequest) {
		this.dateRequest = dateRequest;
	}

	public String getStationeryName() {
		return stationeryName;
	}

	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getStationeryId() {
		return stationeryId;
	}

	public void setStationeryId(String stationeryId) {
		this.stationeryId = stationeryId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getTotalFulfill() {
		return totalFulfill;
	}

	public void setTotalFulfill(String totalFulfill) {
		this.totalFulfill = totalFulfill;
	}

	public String getIssuesStationeryApprovedId() {
		return issuesStationeryApprovedId;
	}

	public void setIssuesStationeryApprovedId(String issuesStationeryApprovedId) {
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
	}

	public int getIssueLocation() {
		return issueLocation;
	}

	public void setIssueLocation(int issueLocation) {
		this.issueLocation = issueLocation;
	}

	public String getEmployeeUsername() {
		return employeeUsername;
	}

	public void setEmployeeUsername(String employeeUsername) {
		this.employeeUsername = employeeUsername;
	}

	public String getIssuesStationeryId() {
		return issuesStationeryId;
	}

	public void setIssuesStationeryId(String issuesStationeryId) {
		this.issuesStationeryId = issuesStationeryId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public VT050004DataResponseRequest(String requestId, String placeName, String unitName, String fullName,
			String userName, Date dateRequest, String stationeryName, int quantity, double price, double totalMoney,
			String stationeryId, String phoneNumber, String totalFulfill, String issuesStationeryApprovedId,
			int issueLocation, String employeeUsername, String issuesStationeryId, int status) {
		super();
		this.requestId = requestId;
		this.placeName = placeName;
		this.unitName = unitName;
		this.fullName = fullName;
		this.userName = userName;
		this.dateRequest = dateRequest;
		this.stationeryName = stationeryName;
		this.quantity = quantity;
		this.price = price;
		this.totalMoney = totalMoney;
		this.stationeryId = stationeryId;
		this.phoneNumber = phoneNumber;
		this.totalFulfill = totalFulfill;
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
		this.issueLocation = issueLocation;
		this.employeeUsername = employeeUsername;
		this.issuesStationeryId = issuesStationeryId;
		this.status = status;
	}

	public VT050004DataResponseRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
