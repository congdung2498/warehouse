package com.viettel.vtnet360.vt00.vt000000.dao;

import java.util.List;
import java.util.Map;

import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityDataFindEmployee;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityDataSystemCode;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityListUnit;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityPlace;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRqFindEmp;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRqSystemCode;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntitySmsVsNotify;


/**
 * Class @Service for VT000000DAO,VT010006,VT010007
 * 
 * @author KienHT 11/08/2018
 * 
 */

public interface VT000000DAO {

	/**
	 * find List System Code
	 * 
	 * @param VT000000EntityRequestParamGetSystemCode
	 * @return List<VT000000EntityDataSystemCode>
	 */
	public List<VT000000EntityDataSystemCode> findListSystemCode(VT000000EntityRqSystemCode requestParam);

	/**
	 * find List Employee by pattern
	 * 
	 * @param VT000000EntityRequestParamFindEmployee
	 * @return List<VT000000EntityDataFindEmployee>
	 */
	public List<VT000000EntityDataFindEmployee> findListEmployee(VT000000EntityRqFindEmp requestParam);

	/**
	 * find Information for Sms and Notify by Username
	 * 
	 * @param String
	 * @return findNotifySmsByUserName
	 */
	public VT000000EntitySmsVsNotify findNotifySmsByUserName(String userName);

	/**
	 * find User Name Emp By Id InOut
	 * 
	 * @param String
	 * @return String
	 */
	public String findUserNameEmpByIdInOut(String inOutRegisterId);

	/**
	 * find information By Id InOut
	 * 
	 * @param String
	 * @return String
	 */
	public VT000000EntitySmsVsNotify findNotifySmsByIdInOut(String inOutRegisterId);

	/**
	 * Find place
	 * 
	 * @param VT000000EntityPlace           
	 * @return List<VT000000EntityPlace>
	 */
	public List<VT000000EntityPlace> findPlace(VT000000EntityPlace obj);

	/**
	 * get list of unit
	 * 
	 * @param VT000000EntityListUnit
	 * @return List<VT000000EntityListUnit>
	 */
	public List<VT000000EntityListUnit> findListUnit(VT000000EntityListUnit unitInfo);
	
	/**
	 * invalidate deviceToken
	 * 
	 * @param String
	 * @return void
	 */
	public void invalidateDeviceToken(String username);
	
	/**
	 * invalidate user logged token
	 * 
	 * @param String
	 * @return void
	 */
	public void invalidateToken(Map<String, String> param);
	
	/**
	 * get guard password
	 * 
	 * @param non
	 * @return String
	 */
	public String getPasswordOfGuard();
	
	/**
	 * get guard password
	 * 
	 * @param String
	 * @return String
	 */
	public Integer setPasswordOfGuard(String param);
	
	/**
	 * @return Map<String,Object>
	 */
	public List<Map<String,Object>> findNotifyEachMinute();
	
	
	/**
	 * @return Map<String,Object>
	 */
	public Map<String,Object> findInfoInOutRecord(String id);
}
