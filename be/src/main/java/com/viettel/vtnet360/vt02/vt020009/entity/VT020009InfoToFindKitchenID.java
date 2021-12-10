package com.viettel.vtnet360.vt02.vt020009.entity;

/**
 * @author DuyNK
 *
 */
public class VT020009InfoToFindKitchenID {

	/** KITCHEN_SETTING.CHEF_ACCOUNT */
	private String userName;

	/** KITCHEN_SETTING.KITCHEN_ID */
	private int status;

	public VT020009InfoToFindKitchenID() {

	}

	public VT020009InfoToFindKitchenID(String userName, int status) {
		super();
		this.userName = userName;
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
