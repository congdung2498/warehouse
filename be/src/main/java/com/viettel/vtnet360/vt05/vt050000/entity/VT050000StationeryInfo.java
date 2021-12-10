package com.viettel.vtnet360.vt05.vt050000.entity;

/**
 * @author DuyNK 04/10/2018
 */
public class VT050000StationeryInfo {

	/** STATIONERY_ITEMS.STATIONERY_ID */
	private String stationeryID;

	/** STATIONERY_ITEMS.STATIONERY_NAME */
	private String stationeryName;

	/** STATIONERY_ITEMS.CALCULATION_UNIT */
	private String calculationUnit;

	/** STATIONERY_ITEMS.UNIT_PRICE */
	private double unitPrice;

	public VT050000StationeryInfo() {

	}

	public VT050000StationeryInfo(String stationeryID, String stationeryName, String calculationUnit,
			double unitPrice) {
		super();
		this.stationeryID = stationeryID;
		this.stationeryName = stationeryName;
		this.calculationUnit = calculationUnit;
		this.unitPrice = unitPrice;
	}

	public String getStationeryID() {
		return stationeryID;
	}

	public void setStationeryID(String stationeryID) {
		this.stationeryID = stationeryID;
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
}
