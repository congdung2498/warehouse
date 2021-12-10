package com.viettel.vtnet360.vt02.vt020004.entity;

/**
 * @author DuyNK
 *
 */
public class VT020004InfoToFindKitchen {

	/** KITCHEN_SETTING.CHEF_ACCOUNT */
	private String userName;

	/** KITCHEN_SETTING.DELETE_FLAG */
	private int deleteFG;

	/** KITCHEN_SETTING.STATUS */
	private int status;
	
	/** MENU_SETTING.KITCHEN_ID */
	private String kitchenID;
	
	public VT020004InfoToFindKitchen() {

	}

	public VT020004InfoToFindKitchen(String userName, int deleteFG, int status, String kitchenID) {
		super();
		this.userName = userName;
		this.deleteFG = deleteFG;
		this.status = status;
		this.kitchenID = kitchenID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getDeleteFG() {
		return deleteFG;
	}

	public void setDeleteFG(int deleteFG) {
		this.deleteFG = deleteFG;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}
}
