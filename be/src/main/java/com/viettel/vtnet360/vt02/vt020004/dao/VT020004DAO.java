package com.viettel.vtnet360.vt02.vt020004.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt02.vt020004.entity.VT020004Dish;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindDishByChefID;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindDishInMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindKitchen;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004Menu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004MenuItem;

/**
 * Class DAO for screen VT020004 : setting menu
 * 
 * @author DuyNK 12/09/2018
 */
@Repository
public interface VT020004DAO {

  public List<VT020004Dish> findListDishByKitchen(VT020004InfoToFindDishByChefID info);
  
	/**
	 * Select all menu in MENU_SETTING by ChefID
	 * 
	 * @param info
	 * @return List<VT020004MenuItem>
	 */
	public List<VT020004MenuItem> findListMenu(VT020004InfoToFindMenu info);

	/**
	 * Select list dish by kitchen id
	 * 
	 * @param info
	 * @return List<VT020004Dish>
	 */
	public List<VT020004Dish> findListDishByChefID(VT020004InfoToFindDishByChefID info);

	/**
	 * check a day exist in database or not before insert to database ( result > 0
	 * => existed)
	 * 
	 * @param info
	 * @return Integer
	 */
	public int checkMenuExist(VT020004InfoToFindDishInMenu info);

	/**
	 * delete all record that have date = ... and chefID = ...
	 * 
	 * @param info
	 */
	public void deleteMenuByday(VT020004InfoToFindDishInMenu info);

	/**
	 * insert new record to MENU_SETTING
	 * 
	 * @param info
	 */
	public void inserMenu(VT020004Menu info);

	/**
	 * check kitchen exist or not
	 * 
	 * @param info
	 * @return Integer
	 */
	public int checkKitchenExisted(VT020004InfoToFindKitchen info);
	
	/**
	 * get kitchenID by chef userName
	 * 
	 * @param chefUserName
	 * @return String
	 */
	public String findKitchenIDByChefUserName(String chefUserName);
}
