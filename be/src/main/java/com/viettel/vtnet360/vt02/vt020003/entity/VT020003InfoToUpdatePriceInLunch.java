package com.viettel.vtnet360.vt02.vt020003.entity;

import java.util.Date;

/** 
 * @author DuyNK 09/08/2018
 * 
 */
public class VT020003InfoToUpdatePriceInLunch {
	
	/** LUNCH_CALENDAR.PRICE */
	private double price;
	
	/** LUNCH_CALENDAR.KITCHEN_ID */
	private String kitchenID;
	
	/** LUNCH_CALENDAR.LUNCH_DATE */
	private Date lunchDate;
	
	public VT020003InfoToUpdatePriceInLunch() {
	
	}

	public VT020003InfoToUpdatePriceInLunch(double price, String kitchenID, Date lunchDate) {
		super();
		this.price = price;
		this.kitchenID = kitchenID;
		this.lunchDate = lunchDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}

	public Date getLunchDate() {
		return lunchDate;
	}

	public void setLunchDate(Date lunchDate) {
		this.lunchDate = lunchDate;
	}
}
