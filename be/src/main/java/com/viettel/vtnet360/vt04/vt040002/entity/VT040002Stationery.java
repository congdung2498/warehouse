package com.viettel.vtnet360.vt04.vt040002.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * class entity stationery VT040002
 * 
 * @author ThangBT 18/10/2018
 *
 */
public class VT040002Stationery extends BaseEntity {

	/** user name of login user */
	private String loginUserName;

	/** role of login user */
	private String loginRole;

	/** id of stationery */
	private String stationeryId;

	/** name of stationery */
	private String stationeryName;

	/** type of stationery */
	private String stationeryType;

	/** price of stationery */
	private int stationeryPrice;

	/** unit caculation of stationery */
	private String stationeryUnitCal;

	/** status of stationer */
	private String status;

	/** master class of stationery type */
	private String masterClassType;

	/** master class of stationery unit calculation */
	private String masterClassUnit;

	/** code value of stationery type */
	private String codeTypeValue;

	/** code value of stationery unit calculation */
	private String codeUnitValue;

	/** select from which row */
	private int startRow;

	/** number of row to select */
	private int rowSize;

	public VT040002Stationery() {
		super();
	}

	public VT040002Stationery(String stationeryId, String stationeryName, String stationeryType, int stationeryPrice,
			String stationeryUnitCal, String status, String masterClassType, String masterClassUnit,
			String codeTypeValue, String codeUnitValue, int startRow, int rowSize) {
		super();
		this.stationeryId = stationeryId;
		this.stationeryName = stationeryName;
		this.stationeryType = stationeryType;
		this.stationeryPrice = stationeryPrice;
		this.stationeryUnitCal = stationeryUnitCal;
		this.status = status;
		this.masterClassType = masterClassType;
		this.masterClassUnit = masterClassUnit;
		this.codeTypeValue = codeTypeValue;
		this.codeUnitValue = codeUnitValue;
		this.startRow = startRow;
		this.rowSize = rowSize;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getLoginRole() {
		return loginRole;
	}

	public void setLoginRole(String loginRole) {
		this.loginRole = loginRole;
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

	public int getStationeryPrice() {
		return stationeryPrice;
	}

	public void setStationeryPrice(int stationeryPrice) {
		this.stationeryPrice = stationeryPrice;
	}

	public String getStationeryUnitCal() {
		return stationeryUnitCal;
	}

	public void setStationeryUnitCal(String stationeryUnitCal) {
		this.stationeryUnitCal = stationeryUnitCal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMasterClassType() {
		return masterClassType;
	}

	public void setMasterClassType(String masterClassType) {
		this.masterClassType = masterClassType;
	}

	public String getMasterClassUnit() {
		return masterClassUnit;
	}

	public void setMasterClassUnit(String masterClassUnit) {
		this.masterClassUnit = masterClassUnit;
	}

	public String getCodeTypeValue() {
		return codeTypeValue;
	}

	public void setCodeTypeValue(String codeTypeValue) {
		this.codeTypeValue = codeTypeValue;
	}

	public String getCodeUnitValue() {
		return codeUnitValue;
	}

	public void setCodeUnitValue(String codeUnitValue) {
		this.codeUnitValue = codeUnitValue;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}
}
