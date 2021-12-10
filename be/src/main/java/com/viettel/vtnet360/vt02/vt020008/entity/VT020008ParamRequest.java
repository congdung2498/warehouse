package com.viettel.vtnet360.vt02.vt020008.entity;

import java.util.Date;

import com.viettel.vtnet360.vt00.common.BaseEntity;

/**
 * @author DuyNK 19/09/2018
 *
 */
public class VT020008ParamRequest extends BaseEntity {

	/** LUNCH_CALENDAR.RATTING */
	private int rating;

	/** LUNCH_CALENDAR.COMMENT */
	private String comment;

	/** lunchDate */
	private Date lunchDate;

	/** LUNCH_CALENDAR.HAS_BOOKING */
	private int hasBooking;

	/** LUNCH_CALENDAR.QUANTITY */
	private int quantity;

	public VT020008ParamRequest() {

	}

	public VT020008ParamRequest(int rating, String comment, Date lunchDate, int hasBooking, int quantity) {
		super();
		this.rating = rating;
		this.comment = comment;
		this.lunchDate = lunchDate;
		this.hasBooking = hasBooking;
		this.quantity = quantity;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
