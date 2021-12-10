package com.viettel.vtnet360.vt02.vt020009.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK 13/09/2018
 * 
 */
public class VT020009InfoToFindReport extends BaseEntity {

	/** month of feedback MONTH(LUNCH_CALENDAR.LUNCH_DATE) */
	private int month;

	/** year of feedback YEAR(LUNCH_CALENDAR.LUNCH_DATE) */
	private int year;

	/** LUNCH_CALENDAR.KITCHEN_ID */
	private String kitchenID;

	public VT020009InfoToFindReport() {

	}

	public VT020009InfoToFindReport(int month, int year, String kitchenID) {
		super();
		this.month = month;
		this.year = year;
		this.kitchenID = kitchenID;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}
}
