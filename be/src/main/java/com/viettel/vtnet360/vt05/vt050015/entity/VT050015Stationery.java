package com.viettel.vtnet360.vt05.vt050015.entity;

public class VT050015Stationery {
	
	private String stationeryName;
	private String type;
	private double totalRequest;
	private double totalFulfill;
	private String code_NAME;
	
	public VT050015Stationery(String stationeryName, String type, double totalRequest, double totalFulfill,
			String code_NAME) {
		super();
		this.stationeryName = stationeryName;
		this.type = type;
		this.totalRequest = totalRequest;
		this.totalFulfill = totalFulfill;
		this.code_NAME = code_NAME;
	}
	public String getStationeryName() {
		return stationeryName;
	}
	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getTotalRequest() {
		return totalRequest;
	}
	public void setTotalRequest(double totalRequest) {
		this.totalRequest = totalRequest;
	}
	public double getTotalFulfill() {
		return totalFulfill;
	}
	public void setTotalFulfill(double totalFulfill) {
		this.totalFulfill = totalFulfill;
	}
	public String getCode_NAME() {
		return code_NAME;
	}
	public void setCode_NAME(String code_NAME) {
		this.code_NAME = code_NAME;
	}
}
