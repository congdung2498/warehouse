package com.viettel.vtnet360.vt05.vt050010.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT050010DataResponse {

	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private String requestID;

	/** QLDV_PLACE.PLACE_NAME */
	private String placeName;

	/** QLDV_UNIT.UNIT_NAME */
	private String unitName;

	/** ISSUES_STATIONERY_APPROVED.CVP_APPROVED_DATE */
	private Date dateApprove;

	/** ISSUES_STATIONERY_APPROVED.HCDV_MESSAGE */
	private String message;

	/** total money of 1 request */
	private double money;

	/** money limited of 1 unit */
	private double limitMoney;
	
	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private int status;

	public VT050010DataResponse() {
	
	}
	
	public VT050010DataResponse(String requestID, String placeName, String unitName, Date dateApprove, String message,
			double money, double limitMoney, int status) {
		super();
		this.requestID = requestID;
		this.placeName = placeName;
		this.unitName = unitName;
		this.dateApprove = dateApprove;
		this.message = message;
		this.money = money;
		this.limitMoney = limitMoney;
		this.status = status;
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

	public Date getDateApprove() {
		return dateApprove;
	}

	public void setDateApprove(Date dateApprove) {
		this.dateApprove = dateApprove;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getLimitMoney() {
		return limitMoney;
	}

	public void setLimitMoney(double limitMoney) {
		this.limitMoney = limitMoney;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}