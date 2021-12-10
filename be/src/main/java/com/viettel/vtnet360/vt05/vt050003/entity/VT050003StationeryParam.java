package com.viettel.vtnet360.vt05.vt050003.entity;

/**
 * @author DuyNK 05/10/2018
 */
public class VT050003StationeryParam {

	/** STATIONERY_ITEMS.STATIONERY_ID */
	private String stationeryID;

	/** quantity of stationery request */
	private int quantity;

	private String stationeryName ;
	
	private String calculationUnit;
	
	private Double unitPrice ; 
	public VT050003StationeryParam() {

	}
  
	public VT050003StationeryParam(String stationeryID, int quantity, String stationeryName, String calculationUnit,
			Double unitPrice) {
		super();
		this.stationeryID = stationeryID;
		this.quantity = quantity;
		this.stationeryName = stationeryName;
		this.calculationUnit = calculationUnit;
		this.unitPrice = unitPrice;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
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
}
