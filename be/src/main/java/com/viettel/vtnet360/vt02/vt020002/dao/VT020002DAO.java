package com.viettel.vtnet360.vt02.vt020002.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt02.vt020002.entity.VT020002Dish;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002DishOffset;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002InfoToCheckKitchenExist;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002InfoToDeleteDishInMenu;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002InfoToFindKitchen;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002Kitchen;

/**
 * Class DAO for screen VT020002 : setting dish
 * 
 * @author DuyNK 09/08/2018
 */
@Repository
public interface VT020002DAO {

	/**
	 * get list kitchen
	 * 
	 * @param info
	 * @return List<VT020002Kitchen>
	 */
	public List<VT020002Kitchen> findListKitchen(VT020002InfoToFindKitchen info);

	/**
	 * get all kitchen
	 * 
	 * @param info
	 * @return List<VT020002Kitchen>
	 */
	public List<VT020002Kitchen> findAllKitchen(VT020002InfoToFindKitchen info);
	
	/**
   * get kitchen by chef
   * 
   * @param info
   * @return List<VT020002Kitchen>
   */
	public List<VT020002Kitchen> findKitchenByChef(VT020002InfoToFindKitchen info);

	/**
	 * check kitchen existed by KITCHEN_ID in KITCHEN_SETTING
	 * 
	 * @param kitchenID
	 * @return Integer
	 */
	public int findKitchen(String kitchenID);

	/**
	 * Select list dish from DISH_SETTING by KITCHEN_ID, DISH_NAME, limit record by
	 * pageNumber and page size
	 * 
	 * @param dishOffset
	 * @return List<VT020002Dish>
	 */
	public List<VT020002Dish> findListDish(VT020002DishOffset dishOffset);

	/**
	 * get dish by KITCHEN_ID and dishName
	 * 
	 * @param VT020002Dish
	 * @return Integer
	 */
	public int findDish(VT020002Dish dish);

	/**
	 * Insert new Dish to DISH_SETTING
	 * 
	 * @param dish
	 * @return Integer
	 */
	public int insertDish(VT020002Dish dish);

	/**
	 * update Dish to DISH_SETTING
	 * 
	 * @param dish
	 * @return Integer
	 */
	public int updateDish(VT020002Dish dish);

	/**
	 * delete dish in DISH_SETTING
	 * 
	 * @param dish
	 */
	public void deleteDish(VT020002Dish dish);

	/**
	 * delete dish in menu in future
	 * 
	 * @param info
	 */
	public void deleteDishInMenu(VT020002InfoToDeleteDishInMenu info);

	/**
	 * get kitchen info by userName
	 * 
	 * @param info
	 * @return VT020002Kitchen
	 */
	public VT020002Kitchen findKitchenByUserName(VT020002InfoToFindKitchen info);
	
	/**
	 * check this user has role to edit this dish or not 0:no 1:yes
	 * 
	 * @param dish
	 * @return Integer
	 */
	public int checkRoleUpdate(VT020002Dish dish);
	
	/**
	 * check kitchen active or not by kitchenID
	 * 
	 * @param info
	 * @return Integer
	 */
	public int checkKitchenActive(VT020002InfoToCheckKitchenExist info);
	
	/**
	 * check condition before update & delete dish (check dish is deleted or not)
	 * 
	 * @param dish
	 * @return Integer
	 */
	public int checkConditionBeforeUpdate(VT020002Dish dish);
	
	/**
	 * 
	 * @param dishID
	 * @return
	 */
	public VT020002Dish findDishById(String dishID);
}
