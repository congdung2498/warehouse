package com.viettel.vtnet360.vt02.vt020006.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT020006InfoToFindTotalLunchDate {

	/** LUNCH_CALENDAR.LUNCH_DATE */
	private Date lunchDate;
	
	/** LUNCH_CALENDAR.HAS_BOOKING */
	private int hasBooking;
	
	public VT020006InfoToFindTotalLunchDate() {
	
	}

	public VT020006InfoToFindTotalLunchDate(Date lunchDate, int hasBooking) {
		super();
		this.lunchDate = lunchDate;
		this.hasBooking = hasBooking;
	}

	public Date getLunchDate() {
		return lunchDate;
	}

	public void setLunchDate(Date lunchDate) {
		this.lunchDate = lunchDate;
	}

	public int getHasBooking() {
		return hasBooking;
	}

	public void setHasBooking(int hasBooking) {
		this.hasBooking = hasBooking;
	}
}
