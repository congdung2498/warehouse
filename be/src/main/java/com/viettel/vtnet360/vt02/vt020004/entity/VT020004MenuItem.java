package com.viettel.vtnet360.vt02.vt020004.entity;

import java.util.Date;

/**
 * @author DuyNK 12/09/2018
 * 
 */
public class VT020004MenuItem {

	/** MENU_SETTING.DATE_OF_MENU */
	private String menuId;
	
	private String kitchenID;
	
	private Date dateOfMenu;

	/** MENU_SETTING.DISH_ID = DISH_SETTING.DISH_ID */
	private String dishID;

	/** DISH_SETTING.DISH_NAME */
	private String dishName;
	
	private String image;
	
	
	
	

	public VT020004MenuItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public VT020004MenuItem(String menuId, String kitchenID, Date dateOfMenu, String dishID, String dishName,
			String image) {
		super();
		this.menuId = menuId;
		this.kitchenID = kitchenID;
		this.dateOfMenu = dateOfMenu;
		this.dishID = dishID;
		this.dishName = dishName;
		this.image = image;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Date getDateOfMenu() {
		return dateOfMenu;
	}

	public void setDateOfMenu(Date dateOfMenu) {
		this.dateOfMenu = dateOfMenu;
	}

	public String getDishID() {
		return dishID;
	}

	public void setDishID(String dishID) {
		this.dishID = dishID;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
}
