package com.viettel.vtnet360.vt05.vt050000.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

/**
 * @author DuyNK 04/10/2018
 */
public class VT050000Stationery extends BaseEntity {

	/** STATIONERY_ITEMS.STATIONERY_ID */
	private String stationeryID;

	/** STATIONERY_ITEMS.STATIONERY_NAME */
	private String stationeryName;

	/** STATIONERY_ITEMS.STATIONERY_TYPE */
	private String stationeryType;

	/** STATIONERY_ITEMS.CALCULATION_UNIT */
	private String calculationUnit;

	/** STATIONERY_ITEMS.UNIT_PRICE */
	private double unitPrice;

	/** STATIONERY_ITEMS.STATUS */
	private int status;

	public VT050000Stationery() {

	}

	public VT050000Stationery(String stationeryID, String stationeryName, String stationeryType, String calculationUnit,
			double unitPrice, int status) {
		super();
		this.stationeryID = stationeryID;
		this.stationeryName = stationeryName;
		this.stationeryType = stationeryType;
		this.calculationUnit = calculationUnit;
		this.unitPrice = unitPrice;
		this.status = status;
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
}
