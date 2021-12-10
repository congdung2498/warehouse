package com.viettel.vtnet360.vt02.vt020008.entity;

import java.util.Date;

/**
 * @author DuyNK 19/09/2018
 *
 */
public class VT020008InfoCheckHasBooking {

	/** MENU_SETTING.DATE_OF_MENU */
	private Date lunchDate;

	/** MENU_SETTING.KITCHEN_ID */
	private String kitchenID;

	public VT020008InfoCheckHasBooking() {

	}

	public VT020008InfoCheckHasBooking(Date lunchDate, String kitchenID) {
		super();
		this.lunchDate = lunchDate;
		this.kitchenID = kitchenID;
	}

	public Date getLunchDate() {
		return lunchDate;
	}

	public void setLunchDate(Date lunchDate) {
		this.lunchDate = lunchDate;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}

}
