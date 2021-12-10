package com.viettel.vtnet360.vt02.vt020004.entity;

import java.util.Date;
import java.util.List;

/**
 * @author DuyNK 12/09/2018
 */
public class VT020004Menu {

	private String menuId;
	/** MENU_SETTING.DATE_OF_MENU */
	private Date dateOfMenu;

	/** list dish in dateOffMenu */
	private List<VT020004Dish> listDish;

	/** MENU_SETTING.CHEF_ID = QLDV_EMPLOYEE.USER_NAME */
	private String chefID;

	/** MENU_SETTING.KITCHEN_ID */
	private String kitchenID;
	


	public VT020004Menu(String menuId, Date dateOfMenu, List<VT020004Dish> listDish, String chefID, String kitchenID) {
		super();
		this.menuId = menuId;
		this.dateOfMenu = dateOfMenu;
		this.listDish = listDish;
		this.chefID = chefID;
		this.kitchenID = kitchenID;
	}

	public VT020004Menu() {
		super();
	}
	

	public String getMenuId() { return menuId; }
	public void setMenuId(String menuId) { this.menuId = menuId; }

	public Date getDateOfMenu() { return dateOfMenu; }
	public void setDateOfMenu(Date dateOfMenu) { this.dateOfMenu = dateOfMenu; }

	public List<VT020004Dish> getListDish() { return listDish; }
	public void setListDish(List<VT020004Dish> listDish) { this.listDish = listDish; }

	public String getChefID() { return chefID; }
	public void setChefID(String chefID) { this.chefID = chefID; }

	public String getKitchenID() { return kitchenID; }
	public void setKitchenID(String kitchenID) { this.kitchenID = kitchenID; }
}
