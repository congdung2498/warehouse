package com.viettel.vtnet360.vt02.vt020003.service;

import java.io.File;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003Condition;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003KitchenInfo;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003RequestQuery;

/**
 * Class service for screen VT020003 : setting kitchen
 * 
 * @author DuyNK 10/09/2018
 */
public interface VT020003Service {

	/**
	 * Select all kitchen place( from QLDV_PLACE)
	 * 
	 * @param query
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findKitchenPlace(VT020003RequestQuery query);

	/**
	 * Select all list chef(QLDV_EMPLOYEE where `ROLE` = 'PMQT_Bep_truong')
	 * 
	 * @param query
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListChef(VT020003RequestQuery query);

	/**
	 * Select all kitchen place( from QLDV_PLACE) and list chef(QLDV_EMPLOYEE where
	 * `ROLE` = 'PMQT_Bep_truong')
	 * 
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListKitchenInfo(VT020003Condition condition);

	/**
	 * count total record of kitchen
	 * 
	 * @param condition
	 * @return number
	 */
	public ResponseEntityBase countTotalRecord(VT020003Condition condition);

	/**
	 * Insert new kitchen to KITCHEN_SETTING
	 * 
	 * @param kitchenInfo
	 * @param useName
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase insertKitchen(VT020003KitchenInfo kitchenInfo, String useName);

	/**
	 * Update kitchen to KITCHEN_SETTING
	 * 
	 * @param kitchenInfo
	 * @param useName
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase updateKitchen(VT020003KitchenInfo kitchenInfo, String useName);

	/**
	 * delete kitchen in KITCHEN_SETTING
	 * 
	 * @param kitchenID
	 * @param userName
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase deleteKitchen(String kitchenID, String userName);

	File exportListKitchenInfo(VT020003Condition condition);
	/**
	 * 
	 * @param query
	 * @return
	 */
	public ResponseEntityBase findListChef2(VT020003RequestQuery query);
}
