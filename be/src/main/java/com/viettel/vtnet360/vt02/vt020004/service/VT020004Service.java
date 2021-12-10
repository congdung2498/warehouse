package com.viettel.vtnet360.vt02.vt020004.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004Dish;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindDishByChefID;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindDishInMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToInsertMenu;

/**
 * Class service for screen VT020004 : setting kitchen
 * 
 * @author DuyNK 12/09/2018
 */
public interface VT020004Service {
  
  public List<VT020004Dish> findListDishByKitchen(VT020004InfoToFindDishByChefID info);

	/**
	 * Select list menu each day from ... to ...
	 * 
	 * @param info
	 * @param useName
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findLishMenu(VT020004InfoToFindMenu info, String useName, Collection<GrantedAuthority> roleList);

	/**
	 * Select list dish by chefID(MENU_SETTING.CHEF_ID = QLDV_EMPLOYEE.USER_NAME)
	 * 
	 * @param info
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListDishByChefID(VT020004InfoToFindDishByChefID info, Collection<GrantedAuthority> roleList);

	/**
	 * check menu in this day existed in database or not (0:not 1:exist -1:check error)
	 * 
	 * @param info
	 * @param userName
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase checkMenuExist(VT020004InfoToFindDishInMenu info, String userName, Collection<GrantedAuthority> roleList);

	/**
	 * insert new reocrd to MENU_SETTING ( if date existed => delete all record of
	 * this date => insert new data)
	 * 
	 * @param info
	 * @param userName
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase insertMenu(VT020004InfoToInsertMenu info, String userName, Collection<GrantedAuthority> roleList);
}
