package com.viettel.vtnet360.vt05.vt050008.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT050008DataResponse {

	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private String requestID;

	/** QLDV_PLACE.PLACE_NAME */
	private String placeName;

	/** QLDV_UNIT.UNIT_NAME */
	private String unitName;

	/** ISSUES_STATIONERY_APPROVED.VPTCT_USERNAME */
	private String userName;

	/** QLDV_EMPLOYEE.FULL_NAME of VPTCT */
	private String fullName;

	/**
	 * ISSUES_STATIONERY_APPROVED.VPTCT_CREATE_DATE (Date that VPTCT send request to CVP)
	 */
	private Date dateRequest;

	/** ISSUES_STATIONERY_APPROVED.VPTCT_MESSAGE */
	private String message;

	/** total money of 1 request */
	private int money;

	/** money limited of 1 unit */
	private int limitMoney;

	public VT050008DataResponse() {

	}

	public VT050008DataResponse(String requestID, String placeName, String unitName, String userName, String fullName,
			Date dateRequest, String message, int money, int limitMoney) {
		super();
		this.requestID = requestID;
		this.placeName = placeName;
		this.unitName = unitName;
		this.userName = userName;
		this.fullName = fullName;
		this.dateRequest = dateRequest;
		this.message = message;
		this.money = money;
		this.limitMoney = limitMoney;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(Date dateRequest) {
		this.dateRequest = dateRequest;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getLimitMoney() {
		return limitMoney;
	}

	public void setLimitMoney(int limitMoney) {
		this.limitMoney = limitMoney;
	}
}
