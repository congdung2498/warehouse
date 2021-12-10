package com.viettel.vtnet360.vt02.vt020006.entity;

/**
 * @author DuyNK 17/09/2018
 *
 */
public class VT020006UserInfoLunchDate {

	/** LUNCH_CALENDAR.EMPLOYEE_USER_NAME */
	private String userName;

	/** LUNCH_CALENDAR.KITCHEN_ID */
	private String kitchenID;

	/** WEEKDAY(LUNCH_CALENDAR.LUNCH_DATE) */
	private int dayOfWeek;

	public VT020006UserInfoLunchDate() {

	}

	public VT020006UserInfoLunchDate(String userName, String kitchenID, int dayOfWeek) {
		super();
		this.userName = userName;
		this.kitchenID = kitchenID;
		this.dayOfWeek = dayOfWeek;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

}
