package com.viettel.vtnet360.vt02.vt020009.entity;

import java.util.Date;

/**
 * @author DuyNK 13/09/2018
 * 
 */
public class VT020009Report {

	/** LUNCH_CALENDAR.LUNCH_DATE */
	private Date lunchDate;

	/** QLDV_EMPLOYEE.FULL_NAME */
	private String fullName;

	/** LUNCH_CALENDAR.COMMENT */
	private String comment;

	/** LUNCH_CALENDAR.COMMENT */
	private int rating;

	public VT020009Report() {

	}

	public VT020009Report(Date lunchDate, String fullName, String comment, int rating) {
		super();
		this.lunchDate = lunchDate;
		this.fullName = fullName;
		this.comment = comment;
		this.rating = rating;
	}

	public Date getLunchDate() {
		return lunchDate;
	}

	public void setLunchDate(Date lunchDate) {
		this.lunchDate = lunchDate;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
