package com.viettel.vtnet360.vt02.vt020002.entity;

import java.util.Date;
import java.util.List;

/** 
 * @author DuyNK 08/09/2018
 */
public class VT020002InfoToDeleteDishInMenu {

	/** use for delete dish in menu */
	private List<String> listDishID;
	
	/** use for delete dish in menu */
	private Date dayOfMenu;
	
	public VT020002InfoToDeleteDishInMenu() {
	
	}

	public VT020002InfoToDeleteDishInMenu(List<String> listDishID, Date dayOfMenu) {
		super();
		this.listDishID = listDishID;
		this.dayOfMenu = dayOfMenu;
	}

	public List<String> getListDishID() {
		return listDishID;
	}

	public void setListDishID(List<String> listDishID) {
		this.listDishID = listDishID;
	}

	public Date getDayOfMenu() {
		return dayOfMenu;
	}

	public void setDayOfMenu(Date dayOfMenu) {
		this.dayOfMenu = dayOfMenu;
	}

}
