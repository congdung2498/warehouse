package com.viettel.vtnet360.vt00.vt000000.service;

import java.util.Map;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityListUnit;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityPlace;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRpFindEmp;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRpSystemCode;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRqFindEmp;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRqSystemCode;

/**
 * Class interface VT000000Service
 * 
 * @author KienHT 11/08/2018
 * 
 */
public interface VT000000Service {

	/**
	 * find list SystemCode
	 * 
	 * @param VT000000EntityRequestParamGetSystemCode
	 * @return VT000000EntityReponseGetListSystemCode
	 */
	public VT000000EntityRpSystemCode findListSystemCode(VT000000EntityRqSystemCode requestParam) throws Exception;

	/**
	 * find List Employee
	 * 
	 * @param VT000000EntityRequestParamFindEmployee
	 * @return VT000000EntityReponseFindEmployee
	 */
	public VT000000EntityRpFindEmp findListEmployee(VT000000EntityRqFindEmp requestParam) throws Exception;

	/**
	 * Find Place
	 * 
	 * @param VT000000EntityPlace
	 * @return ResponseEntityBase
	 * @throws Exception
	 * @author CuongHD
	 */
	public ResponseEntityBase findPlace(VT000000EntityPlace obj) throws Exception;

	/**
	 * get list of units
	 * 
	 * @param VT000000EntityListUnit
	 * @return List<VT000000EntityListUnit>
	 * @throws Exception
	 */
	public ResponseEntityBase findListUnit(VT000000EntityListUnit unitInfo) throws Exception;
	
	/**
	 * invalidate device token
	 * 
	 * @param String user name
	 * @return void
	 * @throws Exception
	 */
	public void invalidateDeviceToken(String username) throws Exception;
	
	/**
	 * invalidate access token, device token
	 * 
	 * @param String user name, String client name
	 * @return void
	 * @throws Exception
	 */
	public void invalidateToken(String username, String client) throws Exception;
	
	/**
	 * change password of user canhve
	 * 
	 * @param String old password, new password
	 * @return void
	 * @throws Exception
	 */
	public boolean changePassword(String oPassword, String nPassword) throws Exception;
	
	/**
	 * update device token and resent notification
	 * 
	 * @param String old password, new password
	 * @return void
	 * @throws Exception
	 */
	public boolean updateDeviceToken(String device_token, String device_type, String username, String client) throws Exception;
	
	
	/**
	 * send Notify Each Minute to User not yet recive Notify
	 *
	 * @throws Exception
	 */
	public void sendNotifyEachMinute() throws Exception;
	
	/**
	 * Get AppVersion and AppLink from DB
	 * 
	 * @author VinhNVQ
	 * @since 12/04/2018
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getAppVersion() throws Exception;
}
