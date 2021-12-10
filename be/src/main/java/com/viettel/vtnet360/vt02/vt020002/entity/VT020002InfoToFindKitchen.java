package com.viettel.vtnet360.vt02.vt020002.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/** 
 * @author DuyNK 08/09/2018
 */
public class VT020002InfoToFindKitchen extends BaseEntity {
	
	/** param to find kitchen by kitchen name */
	private String kitchenName;
	
	/** KITCHEN_SETTING.STATUS */
	private int status;
	
	/** KITCHEN_SETTING.DELETE_FLAG */
	private int deleteFG;
	
	/** KITCHEN_SETTING.CHEF_ACCOUNT */
	private String chefUserName;
	
	public VT020002InfoToFindKitchen() { }

	public VT020002InfoToFindKitchen(String kitchenName, int status, int deleteFG, String chefUserName) {
		super();
		this.kitchenName = kitchenName;
		this.status = status;
		this.deleteFG = deleteFG;
		this.chefUserName = chefUserName;
	}

	public String getKitchenName() { return kitchenName; }
	public void setKitchenName(String kitchenName) { this.kitchenName = kitchenName; }

	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }

	public int getDeleteFG() { return deleteFG; }
	public void setDeleteFG(int deleteFG) { this.deleteFG = deleteFG; }

	public String getChefUserName() { return chefUserName; }
	public void setChefUserName(String chefUserName) { this.chefUserName = chefUserName; }
}
