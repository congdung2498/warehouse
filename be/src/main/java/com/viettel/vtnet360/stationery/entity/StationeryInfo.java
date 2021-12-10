package com.viettel.vtnet360.stationery.entity;

public class StationeryInfo {

	/** STATIONERY_ITEMS.STATIONERY_ID */
	private String stationeryId;

	/** STATIONERY_ITEMS.STATIONERY_NAME */
	private String stationeryName;

	/** STATIONERY_ITEMS.CALCULATION_UNIT */
	private String calculationUnit;

	/** STATIONERY_ITEMS.UNIT_PRICE */
	private double unitPrice;

	public String getStationeryId() {
		return stationeryId;
	}

	public void setStationeryId(String stationeryId) {
		this.stationeryId = stationeryId;
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

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public StationeryInfo(String stationeryId, String stationeryName, String calculationUnit, double unitPrice) {
		super();
		this.stationeryId = stationeryId;
		this.stationeryName = stationeryName;
		this.calculationUnit = calculationUnit;
		this.unitPrice = unitPrice;
	}

	public StationeryInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
