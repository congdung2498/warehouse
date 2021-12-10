package com.viettel.vtnet360.vt02.vt020006.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK 17/09/2018
 *
 */
public class VT020006InfoToGetListLunchDate extends BaseEntity {

	/** month of feedback MONTH(LUNCH_CALENDAR.LUNCH_DATE) */
	private int month;

	/** year of feedback YEAR(LUNCH_CALENDAR.LUNCH_DATE) */
	private int year;

	/** LUNCH_CALENDAR.EMPLOYEE_USER_NAME */
	private String userName;

	/** LUNCH_CALENDAR.QUANTITY */
	private int quantity;

	/** LUNCH_CALENDAR.HAS_BOOKING */
	private int hasBooking;

	/** LUNCH_CALENDAR.IS_PERIODIC */
	private int isPeriodic;

	private int startRow;

	private int rowSize;

	public VT020006InfoToGetListLunchDate() {
	}

	public VT020006InfoToGetListLunchDate(int month, int year, String userName, int quantity, int hasBooking,
			int isPeriodic) {
		super();
		this.month = month;
		this.year = year;
		this.userName = userName;
		this.quantity = quantity;
		this.hasBooking = hasBooking;
		this.isPeriodic = isPeriodic;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getHasBooking() {
		return hasBooking;
	}

	public void setHasBooking(int hasBooking) {
		this.hasBooking = hasBooking;
	}

	public int getIsPeriodic() {
		return isPeriodic;
	}

	public void setIsPeriodic(int isPeriodic) {
		this.isPeriodic = isPeriodic;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

}
