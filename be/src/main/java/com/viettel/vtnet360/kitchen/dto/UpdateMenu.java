package com.viettel.vtnet360.kitchen.dto;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.vt02.vt020004.entity.VT020004Dish;

public class UpdateMenu {

	private Date dateOfMenu;
	private List<VT020004Dish> listDish;
	private String updateUser;
	private String menuId; 
	
	
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getDateOfMenu() {
		return dateOfMenu;
	}
	public void setDateOfMenu(Date dateOfMenu) {
		this.dateOfMenu = dateOfMenu;
	}
	public List<VT020004Dish> getListDish() {
		return listDish;
	}
	public void setListDish(List<VT020004Dish> listDish) {
		this.listDish = listDish;
	}
	
	public UpdateMenu(Date dateOfMenu, List<VT020004Dish> listDish, String updateUser, String menuId) {
		super();
		this.dateOfMenu = dateOfMenu;
		this.listDish = listDish;
		this.updateUser = updateUser;
		this.menuId = menuId;
	}
	public UpdateMenu() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
