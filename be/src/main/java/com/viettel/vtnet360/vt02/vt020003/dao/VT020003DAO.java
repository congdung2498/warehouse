package com.viettel.vtnet360.vt02.vt020003.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt02.vt020003.entity.VT020003Chef;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003CookerParam;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003InfoToCheckConditionBeforeUpdateKitchen;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003InfoToInsertPhoneNumberReceiveSms;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003InfoToUpdatePriceInLunch;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003KitchenInfo;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003Condition;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003RequestQuery;

/**
 * Class DAO for screen VT020003 : setting kitchen
 * 
 * @author DuyNK 10/09/2018
 */
@Repository
public interface VT020003DAO {

	/**
	 * Select all kitchen place( from QLDV_PLACE)
	 * 
	 * @param query
	 * @return List<VT020003KitchenPlace>
	 */
	public List<VT020003Condition> findListKitchenPlace(VT020003RequestQuery query);

	/**
	 * Select all list chef(QLDV_EMPLOYEE where `ROLE` = 'PMQT_Bep_truong') by
	 * kitchen place
	 * 
	 * @param vt020003CookerParam
	 * @return List<VT020003Chef>
	 */
	public List<VT020003Chef> findListChef(VT020003CookerParam vt020003CookerParam);
	
	/**
	 * 
	 * @param vt020003CookerParam
	 * @return
	 */
	public List<VT020003Chef> findListChef2(VT020003CookerParam vt020003CookerParam);

	/**
	 * Select all list chef(QLDV_EMPLOYEE where `ROLE` = 'PMQT_Bep_truong') by
	 * kitchen place
	 * 
	 * @param deleteFG
	 * @return List<VT020003KitchenInfo>
	 */
	public List<VT020003KitchenInfo> findListKitchenInfo(VT020003Condition condition);

	/**
	 * total record of kitchen
	 * 
	 * @param condition
	 * @return number
	 */
	public int countTotalRecord(VT020003Condition condition);

	/**
	 * insert new record to KITCHEN_SETTING
	 * 
	 * @param kitchenInfo
	 * @return Integer
	 */
	public int insertKitchen(VT020003KitchenInfo kitchenInfo);

	/**
	 * update record to KITCHEN_SETTING
	 * 
	 * @param kitchenInfo
	 * @return Integer
	 */
	public int updateKitchen(VT020003KitchenInfo kitchenInfo);

	/**
	 * update deleteFlag in KITCHEN_SETTING
	 * 
	 * @param kitchenInfo
	 * @return Integer
	 */
	public int deleteKitchen(VT020003KitchenInfo kitchenInfo);

	/**
	 * check userName (get from req of client) existed in KITCHEN_SETTING or not
	 * (1-exist 0-not)
	 * 
	 * @param kitchenInfo
	 * @return Integer
	 */
	public int checkUserNameExist(VT020003KitchenInfo kitchenInfo);

	/**
	 * check placeID (get from req of client) existed in QLDV_PLACE or not (1-exist
	 * 0-not)
	 * 
	 * @param placeID
	 * @return Integer
	 */
	public int checPlaceIdExist(int placeID);

	/**
	 * check kitchen Name (get from req of client) existed in KITCHEN_SETTING or not
	 * (1-exist 0-not) if PLACE_ID && KITCHEN_NAME && CHEF_ACCOUNT existed =>
	 * kitchenName existed
	 * 
	 * @param kitchenInfo
	 * @return Integer
	 */
	public int checkKitchenNameExist(VT020003KitchenInfo kitchenInfo);

	/**
	 * update field price in LUNCH_CALENDAR when update kitchen info
	 * 
	 * @param info
	 */
	public void updatePriceInLunchCalendar(VT020003InfoToUpdatePriceInLunch info);

	/**
	 * delete record in LUNCH_CALENDAR when delete kitchen
	 * 
	 * @param kitchenID
	 */
	public void deleteLunchDate(String kitchenID);
	
	/**
	 * get list phone number of who is configed to receive Sms
	 * 
	 * @param kitchenID
	 * @return List<String>
	 */
	public List<String> findListPhoneNumberReceiveSms(String kitchenID);
	
	/**
	 * delete data phone number in KITCHEN_PHONE_GET_SMS before insert new
	 * 
	 * @param kitchenID
	 */
	public void deletePhoneNumberReceiveSms(String kitchenID);
	
	/**
	 * insert data phone number to KITCHEN_PHONE_GET_SMS
	 * 
	 * @param info
	 */
	public void insertPhoneNumberReceiveSms(VT020003InfoToInsertPhoneNumberReceiveSms info);
	
	/**
	 * @param userName
	 * @return String
	 */
	public String findKitchenIDJustAddNew(String userName);
	
	/**
	 * check condition before update kitchen ( check DELETE_FLAG)
	 * 
	 * @param info
	 * @return Integer
	 */
	public int checkConditionBeforeUpdateKitchen(VT020003InfoToCheckConditionBeforeUpdateKitchen info);
	
	/**
	 * 
	 * @param kitchenID
	 */
	public void deleteChef(String kitchenID);
	
	/**
	 * 
	 * @param kitchenInfo
	 * @return
	 */
	public int checkUserNameExist2(VT020003KitchenInfo kitchenInfo);
	
	/**
	 * 
	 * @param kitchenInfo
	 */
	public void insertChef(VT020003KitchenInfo kitchenInfo);
}
