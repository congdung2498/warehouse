package com.viettel.vtnet360.vt04.vt040000.entity;

/**
 * Class VT040000EntityDataSt
 * 
 * @author KienHT 27/09/2018
 * 
 */
public class VT040000EntityDataSt {

	/** STATIONERY.STATIONERY_ID */
	private String stationeryId;

	/** STATIONERY.STATIONERY_NAME */
	private String stationeryName;

	/** STATIONERY.STATIONERY_TYPE */
	private String stationeryType;

	/** STATIONERY.CALCULATION_UNIT */
	private String calculationUnit;

	/** ISSUES_SERVICE_SPENT.QUANTITY */
	private int stationeryQuantity;

	/** STATIONERY.STATUS */
	private int stationeryStatus;

	/** ISSUES_SERVICE_SPENT.UNIT_PRICE */
	private int unitPrice;

	public VT040000EntityDataSt() {
		super();
	}

	public VT040000EntityDataSt(String stationeryId, String stationeryName, String stationeryType,
			String calculationUnit, int stationeryQuantity, int stationeryStatus, int unitPrice) {
		super();
		this.stationeryId = stationeryId;
		this.stationeryName = stationeryName;
		this.stationeryType = stationeryType;
		this.calculationUnit = calculationUnit;
		this.stationeryQuantity = stationeryQuantity;
		this.stationeryStatus = stationeryStatus;
		this.unitPrice = unitPrice;
	}

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

	public String getStationeryType() {
		return stationeryType;
	}

	public void setStationeryType(String stationeryType) {
		this.stationeryType = stationeryType;
	}

	public String getCalculationUnit() {
		return calculationUnit;
	}

	public void setCalculationUnit(String calculationUnit) {
		this.calculationUnit = calculationUnit;
	}

	public int getStationeryQuantity() {
		return stationeryQuantity;
	}

	public void setStationeryQuantity(int stationeryQuantity) {
		this.stationeryQuantity = stationeryQuantity;
	}

	public int getStationeryStatus() {
		return stationeryStatus;
	}

	public void setStationeryStatus(int stationeryStatus) {
		this.stationeryStatus = stationeryStatus;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

}
