package com.viettel.vtnet360.vt05.vt050018.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT050018DataDetail {

	/** full name of ISSUES_STATIONERY_ITEMS.EMPLOYEE_USERNAME */
	private String fullName;
	
	/** ISSUES_STATIONERY.REQUEST_DATE */
	private Date dateRequest;

	/** STATIONERY_ITEMS.STATIONERY_NAME */
	private String stationeryName;

	/** ISSUES_STATIONERY_ITEMS.TOTAL_REQUEST */
	private int quantity;

	/** STATIONERY_ITEMS.UNIT_PRICE */
	private double price;

	/** quantity * price */
	private double totalMoney;
	
	public VT050018DataDetail() {
	
	}

	public VT050018DataDetail(String fullName, Date dateRequest, String stationeryName, int quantity, double price,
			double totalMoney) {
		super();
		this.fullName = fullName;
		this.dateRequest = dateRequest;
		this.stationeryName = stationeryName;
		this.quantity = quantity;
		this.price = price;
		this.totalMoney = totalMoney;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
}
