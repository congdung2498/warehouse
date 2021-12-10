package com.viettel.vtnet360.vt00.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class @Service for common
 * 
 * @author KienHT 18/09/2018
 * 
 */

public interface VTCommonDAO {

	/**
	 * storageSms
	 * 
	 * @param requestParam HashMap<String,Object>
	 * @return void
	 */
	public void storageSms(HashMap<String, Object> data);

	/**
	 * storageNotification
	 * 
	 * @param requestParam HashMap<String,Object>
	 * @return void
	 */
	public void storageNotification(HashMap<String, Object> data);
	
	/**
	 * update storageNotification
	 * 
	 * @param requestParam Map<String,Object>
	 * @return void
	 */
	public void updateStorageNotification(Map<String, Object> data);

	/**
	 * find TokenDevice by UserName
	 * 
	 * @param requestParam String
	 * @return String
	 */
	public Map<String, String> findTokenDevice(String toUserName);

	/**
	 * find info by UserName
	 * 
	 * @param requestParam String
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> findInfo(String toUserName);
	
	/**
	 * find notifly watting send by UserName
	 * 
	 * @param requestParam String
	 * @return Map<String, Object> 
	 */
	public List<Map<String, Object>> findNotifyWaitSend(String toUserName);
	

}