package com.viettel.vtnet360.vt02.vt020002.service;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002Dish;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002DishOffset;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002InfoToFindKitchen;

/**
 * Class service for screen VT020002 : setting dish
 * 
 * @author DuyNK 08/09/2018
 */
public interface VT020002Service {

  public void saveImage(byte[] data, VT020002Dish dish) throws IOException;
	 /**
	 * Select active kitchen from KITCHEN_SETTING
	 * 
	 * @param info
	 * @return ResponseEntityBase(status,List<VT020002Kitchen>)
	 */
	public ResponseEntityBase findListKitchen(VT020002InfoToFindKitchen info);
	
	/**
     * Select list dish from DISH_SETTING by KITCHEN_ID, DISH_NAME, limit record by pageNumber and page size
     * 
     * @param dishOffset
     * @return ResponseEntityBase(status, List<VT020002Kitchen>)
     */
	public ResponseEntityBase findListDish(VT020002DishOffset dishOffset);
	
	/**
     * Select kitchenInfoOfThisChef 
     * 
     * @param userName
     * @return ResponseEntity(status, List<VT020002Kitchen>)
     */
	public ResponseEntityBase findKitchenInfoOfThisChef(String userName);
	
	/**
     * Insert new Dish to DISH_SETTING
     * 
	 * @param dish
	 * @param userName
     * @return ResponseEntityBase
	 * @throws Exception 
     */
	public ResponseEntityBase insertDish(VT020002Dish dish, String userName, Collection<GrantedAuthority> roleList, MultipartFile image) throws Exception;
	
	/**
     * update Dish to DISH_SETTING
     * 
	 * @param dish
     * @return ResponseEntityBase
     */
	public ResponseEntityBase updateDish(VT020002Dish dish, String userName, Collection<GrantedAuthority> roleList, MultipartFile image);
	
	/**
     * change dish status to inactive(0) in DISH_SETTING
     * 
	 * @param dish
	 * @param userName
     * @return ResponseEntityBase
     */
	public ResponseEntityBase deleteDish(VT020002Dish dish, String userName, Collection<GrantedAuthority> roleList);
	
	/**
	 * Select all kitchen from KITCHEN_SETTING
	 * 
	 * @param info
	 * @return ResponseEntityBase(status,List<VT020002Kitchen>)
	 */
	public ResponseEntityBase findAllKitchen(VT020002InfoToFindKitchen info);
	
	/**
	 * 
	 * @param dishID
	 * @return
	 */
	public VT020002Dish findDishById(String dishID);
}
