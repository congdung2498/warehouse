package com.viettel.vtnet360.vt02.vt020002.entity;

/**
 * @author DuyNK
 *
 */
public class VT020002InfoToCheckKitchenExist {

	/** KITCHEN_SETTING.KITCHEN_ID */
	private String kitchenID;
	
	/** KITCHEN_SETTING.DELETE_FLAG */
	private int deleteFG;
	
	/** KITCHEN_SETTING.STATUS */
	private int status;
	
	public VT020002InfoToCheckKitchenExist() {
	
	}

	public VT020002InfoToCheckKitchenExist(String kitchenID, int deleteFG, int status) {
		super();
		this.kitchenID = kitchenID;
		this.deleteFG = deleteFG;
		this.status = status;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
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
}
