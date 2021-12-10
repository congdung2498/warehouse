package com.viettel.vtnet360.vt05.vt050009.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT050009ListData {

	/** QLDV_PLACE.PLACE_NAME */
	private String placeName;

	/** QLDV_UNIT.UNIT_NAME */
	private String unitName;

	/** ISSUES_STATIONERY_APPROVED.APPROVED_DATE */
	private Date dateApprove;

	/** ISSUES_STATIONERY_APPROVED.MESSAGE */
	private String message;

	/** total money of 1 request */
	private int money;

	/** money limited of 1 unit */
	private int limitMoney;

	public VT050009ListData() {

	}

	public VT050009ListData(String placeName, String unitName, Date dateApprove, String message, int money,
			int limitMoney) {
		super();
		this.placeName = placeName;
		this.unitName = unitName;
		this.dateApprove = dateApprove;
		this.message = message;
		this.money = money;
		this.limitMoney = limitMoney;
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
