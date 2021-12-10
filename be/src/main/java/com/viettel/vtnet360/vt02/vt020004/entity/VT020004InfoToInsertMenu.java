package com.viettel.vtnet360.vt02.vt020004.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 * 29/01/2019
 *
 */
public class VT020004InfoToInsertMenu extends BaseEntity {

	/** MENU_SETTING.DATE_OF_MENU use for delete old menu if update from date a => date b */
	private Date dateOfMenuOld;

	/** MENU_SETTING.DATE_OF_MENU use for insert menu in this date */
	private Date dateOfMenu;
	
	/** list dish in dateOffMenu */
	private List<VT020004Dish> listDish;

	/** MENU_SETTING.CHEF_ID = QLDV_EMPLOYEE.USER_NAME */
	private String chefID;

	/** MENU_SETTING.KITCHEN_ID */
	private String kitchenID;
	
	/** flag to delete menu of a day (dateOfMenuOld), 1=delete 0=default */
	private int copyFlag;
	
	public VT020004InfoToInsertMenu() { }

	public VT020004InfoToInsertMenu(Date dateOfMenuOld, Date dateOfMenu, List<VT020004Dish> listDish, String chefID,
			String kitchenID, int copyFlag) {
		super();
		this.dateOfMenuOld = dateOfMenuOld;
		this.dateOfMenu = dateOfMenu;
		this.listDish = listDish;
		this.chefID = chefID;
		this.kitchenID = kitchenID;
		this.copyFlag = copyFlag;
	}

	
	public Date getDateOfMenuOld() { return dateOfMenuOld; }
	public void setDateOfMenuOld(Date dateOfMenuOld) { this.dateOfMenuOld = dateOfMenuOld; }

	public Date getDateOfMenu() { return dateOfMenu; }
	public void setDateOfMenu(Date dateOfMenu) { this.dateOfMenu = dateOfMenu; }

	public List<VT020004Dish> getListDish() { return listDish; }
	public void setListDish(List<VT020004Dish> listDish) { this.listDish = listDish; }

	public String getChefID() { return chefID; }
	public void setChefID(String chefID) { this.chefID = chefID; }

	public String getKitchenID() { return kitchenID; }
	public void setKitchenID(String kitchenID) { this.kitchenID = kitchenID; }

	public int getCopyFlag() { return copyFlag; }
	public void setCopyFlag(int copyFlag) { this.copyFlag = copyFlag; }
}
