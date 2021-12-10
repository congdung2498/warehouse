package com.viettel.vtnet360.vt02.vt020006.entity;

/**
 * @author DuyNK
 *
 */
public class VT020006TotalLunchDate {

	/** LUNCH_CALENDAR.KITCHEN_ID */
	private String kitchenID;

	/** KITCHEN_SETTING.KITCHEN_NAME */
	private String kitchenName;

	/** KITCHEN_SETTING.KITCHEN_NAME */
	private int userID;

	/** KITCHEN_SETTING.KITCHEN_NAME */
	private String chefName;

	/** KITCHEN_SETTING.KITCHEN_NAME */
	private String chefPhone;

	/** total lunch date in tomorrow in this kitchenID */
	private int total;

	public VT020006TotalLunchDate() {

	}

	public VT020006TotalLunchDate(String kitchenID, String kitchenName, int userID, String chefName, String chefPhone,
			int total) {
		super();
		this.kitchenID = kitchenID;
		this.kitchenName = kitchenName;
		this.userID = userID;
		this.chefName = chefName;
		this.chefPhone = chefPhone;
		this.total = total;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}

	public String getKitchenName() {
		return kitchenName;
	}

	public void setKitchenName(String kitchenName) {
		this.kitchenName = kitchenName;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getChefName() {
		return chefName;
	}

	public void setChefName(String chefName) {
		this.chefName = chefName;
	}

	public String getChefPhone() {
		return chefPhone;
	}

	public void setChefPhone(String chefPhone) {
		this.chefPhone = chefPhone;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
