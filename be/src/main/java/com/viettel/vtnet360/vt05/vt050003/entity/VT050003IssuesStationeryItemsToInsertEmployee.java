package com.viettel.vtnet360.vt05.vt050003.entity;

import com.viettel.vtnet360.vt05.vt050000.entity.VT050000IssuesStationeryItems;

public class VT050003IssuesStationeryItemsToInsertEmployee extends VT050000IssuesStationeryItems {

	private String stationeryID;

	
	private int quantity;

	private String stationeryName ;
	
	private String calculationUnit;
	
	private Double unitPrice ;

	public String getStationeryID() {
		return stationeryID;
	}

	public void setStationeryID(String stationeryID) {
		this.stationeryID = stationeryID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStationeryName() {
		return stationeryName;
	}

	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}

	public String getCalculationUnit() {
		return calculationUnit;
	}

	public void setCalculationUnit(String calculationUnit) {
		this.calculationUnit = calculationUnit;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public VT050003IssuesStationeryItemsToInsertEmployee(String stationeryID, int quantity, String stationeryName,
			String calculationUnit, Double unitPrice) {
		super();
		this.stationeryID = stationeryID;
		this.quantity = quantity;
		this.stationeryName = stationeryName;
		this.calculationUnit = calculationUnit;
		this.unitPrice = unitPrice;
	}

	public VT050003IssuesStationeryItemsToInsertEmployee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VT050003IssuesStationeryItemsToInsertEmployee(String issuesStationeryItemID, String issuesStationeryID,
			String stationeryID, int totalRequest, int totalFulFill, String issuesStationeryApprovedID,
			String issuesStationeryRegistryID, int status) {
		super(issuesStationeryItemID, issuesStationeryID, stationeryID, totalRequest, totalFulFill, issuesStationeryApprovedID,
				issuesStationeryRegistryID, status);
		// TODO Auto-generated constructor stub
	} 

	

	
}
