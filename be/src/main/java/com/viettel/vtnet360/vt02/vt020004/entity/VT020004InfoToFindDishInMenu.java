package com.viettel.vtnet360.vt02.vt020004.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK 08/09/2018
 */
public class VT020004InfoToFindDishInMenu extends BaseEntity {

	/** MENU_SETTING.DATE_OF_MENU */
	private Date dateOfMenu;

	/** MENU_SETTING.CHEF_ID = QLDV_EMPLOYEE.USER_NAME */
	private String chefID;

	/** MENU_SETTING.KITCHEN_ID */
	private String kitchenID;
	
	public VT020004InfoToFindDishInMenu() {

	}

	public VT020004InfoToFindDishInMenu(Date dateOfMenu, String chefID, String kitchenID) {
		super();
		this.dateOfMenu = dateOfMenu;
		this.chefID = chefID;
		this.kitchenID = kitchenID;
	}

	public Date getDateOfMenu() {
		return dateOfMenu;
	}

	public void setDateOfMenu(Date dateOfMenu) {
		this.dateOfMenu = dateOfMenu;
	}

	public String getChefID() {
		return chefID;
	}

	public void setChefID(String chefID) {
		this.chefID = chefID;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}
}
