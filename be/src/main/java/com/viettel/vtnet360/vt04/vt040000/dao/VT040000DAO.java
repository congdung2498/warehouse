package com.viettel.vtnet360.vt04.vt040000.dao;

import java.util.List;
import java.util.Map;

import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityDataFindService;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityDataIssuedService;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityDataSt;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityDetailIssuedSv;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityRqFind;
import java.util.Date;

/**
 * Class interface VT040000DAO
 * 
 * @author KienHT 27/09/2018
 * 
 */
public interface VT040000DAO {

	/**
	 * User find location by request
	 * 
	 * @param Map<String, Object>
	 * @return List<VT040000EntityDataIssuedService>
	 */
	public List<VT040000EntityDataIssuedService> findIssuedService(Map<String, Object> dataPram);

	/**
	 * User find Service by request
	 * 
	 * @param VT040000EntityRqFind
	 * @return List<VT040000EntityDataFindService>
	 */
	public List<VT040000EntityDataFindService> findService(VT040000EntityRqFind requestParam);

	/**
	 * find Detail Issued Service request
	 * 
	 * @param VT040000EntityRqFind
	 * @return VT040000EntityDetailIssuedSv
	 */
	public VT040000EntityDetailIssuedSv finDatailService(VT040000EntityRqFind requestParam);

	/**
	 * find Detail Issued Service request
	 * 
	 * @param VT040000EntityRqFind
	 * @return VT040000EntityDataSt[]
	 */
	public VT040000EntityDataSt[] finStDatailService(VT040000EntityRqFind requestParam);

	/**
	 * approve Issued Service
	 * 
	 * @param Map<String, Object> data
	 */
	public void approveIssuedService(Map<String, Object> data);

	/**
	 * find info Issued Service by issuedServiceId
	 * 
	 * @param String
	 * @return Map<String, Object>
	 */
	public Map<String, Object> infoIssuedService(String issuedServiceId);

	/**
	 * update for issued Service
	 * 
	 * @param Map<String, Object> data
	 */
	public void updateIssuedService(Map<String, Object> data);

	/**
	 * find list service receiver
	 * 
	 * @param String
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> findServiceReceiver(String serviceId);
	
	/**
	 * countInvalidDay
	 * 
	 * @param Map  dataPram
	 * @return int
	 */
	public int countInvalidDay(Map<String, Object> dataPram);
}
