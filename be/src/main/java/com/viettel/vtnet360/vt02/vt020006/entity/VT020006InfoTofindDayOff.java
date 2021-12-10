package com.viettel.vtnet360.vt02.vt020006.entity;

/**
 * @author DuyNK
 *
 */
public class VT020006InfoTofindDayOff {

	/** MONTH(DAY_OFF_SETTING.DAY_OFF) */
	private int month;

	/** YEAR(DAY_OFF_SETTING.DAY_OFF) */
	private int year;

	/** DAY_OFF_SETTING.STATUS */
	private int status;

	public VT020006InfoTofindDayOff() {

	}

	public VT020006InfoTofindDayOff(int month, int year, int status) {
		super();
		this.month = month;
		this.year = year;
		this.status = status;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
