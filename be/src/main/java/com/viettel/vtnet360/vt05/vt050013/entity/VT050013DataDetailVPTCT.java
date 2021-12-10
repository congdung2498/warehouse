package com.viettel.vtnet360.vt05.vt050013.entity;

import java.util.Date;

public class VT050013DataDetailVPTCT {

	/** ISSUES_STATIONERY.ISSUE_LOCATION */
	private int placeID;

	/** QLDV_PLACE.PLACE_NAME */
	private String placeName;

	/** ISSUES_STATIONERY.NOTE */
	private Date createDate;

	/** ISSUES_STATIONERY.STATUS */
	private int status;

	/** ISSUES_STATIONERY.RATING */
	private String hcdvMessage;

	/** ISSUES_STATIONERY.FEEDBACK */
	private int totalRequest;

	private int totalFulfill;
	
	/** ISSUES_STATIONERY_ITEMS.STATIONERY_ID */
	private double totalMoney;

	/** STATIONERY_ITEMS.STATIONERY_NAME */
	private double totalFullfill;

	/** ISSUES_STATIONERY_ITEMS.TOTAL_REQUEST */
	private String fullName;

	/** ISSUES_STATIONERY_ITEMS.TOTAL_FULFILL */
	private Date requestDate;

	/** ISSUES_STATIONERY_ITEMS.UNIT_PRICE */
	private String stationeryName;

	/** STATIONERY_ITEMS.CALCULATION_UNIT */
	private double unitPrice;

	/** ISSUES_STATIONERY_ITEMS.STATUS */
	private double calculationUnit;
	
	private String feedbackToVptct;

	/**
	 * ISSUES_STATIONERY_APPROVED.REASON_REJECT or
	 * ISSUES_STATIONERY_REGISTRY.REASON_REJECT or
	 * ISSUES_STATIONERY_ITEMS.REASON_REJECT
	 */
	private String ratingToVptct;

	public int getPlaceID() {
		return placeID;
	}

	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHcdvMessage() {
		return hcdvMessage;
	}

	public void setHcdvMessage(String hcdvMessage) {
		this.hcdvMessage = hcdvMessage;
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

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public double getTotalFullfill() {
		return totalFullfill;
	}

	public void setTotalFullfill(double totalFullfill) {
		this.totalFullfill = totalFullfill;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getStationeryName() {
		return stationeryName;
	}

	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getCalculationUnit() {
		return calculationUnit;
	}

	public void setCalculationUnit(double calculationUnit) {
		this.calculationUnit = calculationUnit;
	}

	public String getFeedbackToVptct() {
		return feedbackToVptct;
	}

	public void setFeedbackToVptct(String feedbackToVptct) {
		this.feedbackToVptct = feedbackToVptct;
	}

	public String getRatingToVptct() {
		return ratingToVptct;
	}

	public void setRatingToVptct(String ratingToVptct) {
		this.ratingToVptct = ratingToVptct;
	}

	public VT050013DataDetailVPTCT(int placeID, String placeName, Date createDate, int status, String hcdvMessage,
			int totalRequest, int totalFulfill, double totalMoney, double totalFullfill, String fullName,
			Date requestDate, String stationeryName, double unitPrice, double calculationUnit, String feedbackToVptct,
			String ratingToVptct) {
		super();
		this.placeID = placeID;
		this.placeName = placeName;
		this.createDate = createDate;
		this.status = status;
		this.hcdvMessage = hcdvMessage;
		this.totalRequest = totalRequest;
		this.totalFulfill = totalFulfill;
		this.totalMoney = totalMoney;
		this.totalFullfill = totalFullfill;
		this.fullName = fullName;
		this.requestDate = requestDate;
		this.stationeryName = stationeryName;
		this.unitPrice = unitPrice;
		this.calculationUnit = calculationUnit;
		this.feedbackToVptct = feedbackToVptct;
		this.ratingToVptct = ratingToVptct;
	}

	public VT050013DataDetailVPTCT() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	

}
