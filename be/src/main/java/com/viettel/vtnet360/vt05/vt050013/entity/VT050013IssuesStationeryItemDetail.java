package com.viettel.vtnet360.vt05.vt050013.entity;

public class VT050013IssuesStationeryItemDetail {

	/** ISSUES_STATIONERY_ITEMS.STATIONERY_ID */
	private String stationeryID;

	/** STATIONERY_ITEMS.STATIONERY_NAME */
	private String stationeryName;

	/** ISSUES_STATIONERY_ITEMS.TOTAL_REQUEST */
	private int quantity;

	/** ISSUES_STATIONERY_ITEMS.TOTAL_FULFILL */
	private int totalFulFill;

	/** ISSUES_STATIONERY_ITEMS.UNIT_PRICE */
	private double unitPrice;

	/** STATIONERY_ITEMS.CALCULATION_UNIT */
	private String calculationUnit;

	/** ISSUES_STATIONERY_ITEMS.STATUS */
	private int status;

	/**
	 * ISSUES_STATIONERY_APPROVED.REASON_REJECT or
	 * ISSUES_STATIONERY_REGISTRY.REASON_REJECT or
	 * ISSUES_STATIONERY_ITEMS.REASON_REJECT
	 */
	private String reasonReject;

	/** ISSUES_STATIONERY_ITEMS.REASON */
	private String reasonPause;

	public VT050013IssuesStationeryItemDetail() {

	}

	public VT050013IssuesStationeryItemDetail(String stationeryID, String stationeryName, int quantity,
			int totalFulFill, double unitPrice, String calculationUnit, int status, String reasonReject,
			String reasonPause) {
		super();
		this.stationeryID = stationeryID;
		this.stationeryName = stationeryName;
		this.quantity = quantity;
		this.totalFulFill = totalFulFill;
		this.unitPrice = unitPrice;
		this.calculationUnit = calculationUnit;
		this.status = status;
		this.reasonReject = reasonReject;
		this.reasonPause = reasonPause;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTotalFulFill() {
		return totalFulFill;
	}

	public void setTotalFulFill(int totalFulFill) {
		this.totalFulFill = totalFulFill;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getCalculationUnit() {
		return calculationUnit;
	}

	public void setCalculationUnit(String calculationUnit) {
		this.calculationUnit = calculationUnit;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReasonReject() {
		return reasonReject;
	}

	public void setReasonReject(String reasonReject) {
		this.reasonReject = reasonReject;
	}

	public String getReasonPause() {
		return reasonPause;
	}

	public void setReasonPause(String reasonPause) {
		this.reasonPause = reasonPause;
	}
}
