package com.viettel.vtnet360.vt05.vt050015.entity;

import java.util.Date;

public class VT050015StationeryExcel {

	/** id of unit */
	private String unitId;

	/** name of unit */
	private String unitName;

	/** 3 levels of unit */
	private String detailUnit;

	/** id of issue service id */
	private String requestID;

	/** name of service */
	private String stationeryID;

	/** id of stationery */
	private String stationeryName;

	/** name of stationery */
	private String calculatorUnit;

	/** unit calculation */
	private int totalRequest;

	/** quantity of stationery */
	private int totalFulfill;

	/** date start */
	private Date dateExecute;

	/** name of requesting employee */
	private String userReceive;

	/** name of requesting employee */
	private String userExecute;
	
	private int status;
	
	/** note */
	private String note;
	
	private String placeName;
	
	private double unitPrice;
	
	private double totalFulfillMoney;

	public VT050015StationeryExcel() {
	}
	


	public VT050015StationeryExcel(String unitId, String unitName, String detailUnit, String requestID,
			String stationeryID, String stationeryName, String calculatorUnit, int totalRequest, int totalFulfill,
			Date dateExecute, String userReceive, String userExecute, int status, String note, String placeName,
			double unitPrice, double totalFulfillMoney) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
		this.detailUnit = detailUnit;
		this.requestID = requestID;
		this.stationeryID = stationeryID;
		this.stationeryName = stationeryName;
		this.calculatorUnit = calculatorUnit;
		this.totalRequest = totalRequest;
		this.totalFulfill = totalFulfill;
		this.dateExecute = dateExecute;
		this.userReceive = userReceive;
		this.userExecute = userExecute;
		this.status = status;
		this.note = note;
		this.placeName = placeName;
		this.unitPrice = unitPrice;
		this.totalFulfillMoney = totalFulfillMoney;
	}



	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getTotalFulfillMoney() {
		return totalFulfillMoney;
	}

	public void setTotalFulfillMoney(double totalFulfillMoney) {
		this.totalFulfillMoney = totalFulfillMoney;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDetailUnit() {
		return detailUnit;
	}

	public void setDetailUnit(String detailUnit) {
		this.detailUnit = detailUnit;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
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

	public String getCalculatorUnit() {
		return calculatorUnit;
	}

	public void setCalculatorUnit(String calculatorUnit) {
		this.calculatorUnit = calculatorUnit;
	}


	public int getTotalRequest() {
		return totalRequest;
	}



	public void setTotalRequest(int totalRequest) {
		this.totalRequest = totalRequest;
	}



	public int getTotalFulfill() {
		return totalFulfill;
	}



	public void setTotalFulfill(int totalFulfill) {
		this.totalFulfill = totalFulfill;
	}



	public Date getDateExecute() {
		return dateExecute;
	}

	public void setDateExecute(Date dateExecute) {
		this.dateExecute = dateExecute;
	}

	public String getUserReceive() {
		return userReceive;
	}

	public void setUserReceive(String userReceive) {
		this.userReceive = userReceive;
	}

	public String getUserExecute() {
		return userExecute;
	}

	public void setUserExecute(String userExecute) {
		this.userExecute = userExecute;
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

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
}
