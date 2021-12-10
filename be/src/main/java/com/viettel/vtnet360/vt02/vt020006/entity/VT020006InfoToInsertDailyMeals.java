package com.viettel.vtnet360.vt02.vt020006.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT020006InfoToInsertDailyMeals {

	/** DAILY_MEALS.KITCHEN_ID */
	private String kitchenID;
	
	/** DAILY_MEALS.TOTAL */
	private int total;
	
	/** DAILY_MEALS.DATE */
	private Date date;
	
	public VT020006InfoToInsertDailyMeals() {
	
	}

	public VT020006InfoToInsertDailyMeals(String kitchenID, int total, Date date) {
		super();
		this.kitchenID = kitchenID;
		this.total = total;
		this.date = date;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
