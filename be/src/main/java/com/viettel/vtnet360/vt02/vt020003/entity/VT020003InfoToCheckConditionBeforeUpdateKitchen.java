package com.viettel.vtnet360.vt02.vt020003.entity;

/**
 * @author DuyNK
 *
 */
public class VT020003InfoToCheckConditionBeforeUpdateKitchen {

	/** KITCHEN_SETTING.KITCHEN_ID */
	private String kitchenID;
	
	/** KITCHEN_SETTING.DELETE_FLAG */
	private int deleteFG;
	
	public VT020003InfoToCheckConditionBeforeUpdateKitchen() {
	
	}

	public VT020003InfoToCheckConditionBeforeUpdateKitchen(String kitchenID, int deleteFG) {
		super();
		this.kitchenID = kitchenID;
		this.deleteFG = deleteFG;
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
}
