package com.viettel.vtnet360.vt02.vt020006.entity;

/**
 * @author DuyNK 17/09/2018
 *
 */
public class VT020006InfoToCheckKitchenExist {

	/** KITCHEN_SETTING.KITCHEN_ID */
	private String kitchenID;

	/** KITCHEN_SETTING.STATUS */
	private int status;

	public VT020006InfoToCheckKitchenExist() {

	}

	public VT020006InfoToCheckKitchenExist(String kitchenID, int status) {
		super();
		this.kitchenID = kitchenID;
		this.status = status;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
