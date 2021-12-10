package com.viettel.vtnet360.vt04.vt040007.dao;

import java.util.List;
import java.util.Map;

import com.viettel.vtnet360.vt04.vt040007.entity.VT040007EntityDataSt;
import com.viettel.vtnet360.vt04.vt040007.entity.VT040007EntityRqExecutive;
import com.viettel.vtnet360.vt04.vt040007.entity.VT040007EntityRqFindSt;

/**
 * Class interface VT040004DAO
 * 
 * @author KienHT 4/10/2018
 * 
 */
public interface VT040007DAO {

	/**
	 * User executiveService
	 * 
	 * @param Map<String, Object> data
	 */
	public void executiveService(Map<String, Object> data);

	/**
	 * User save PostPone History
	 * 
	 * @param Map<String, Object> data
	 */
	public void savePostPoneHistory(Map<String, Object> data);

	/**
	 * User find Stationery
	 * 
	 * @param VT040007EntityRqFindSt
	 * @return List<VT040007EntityDataSt>
	 */
	public List<VT040007EntityDataSt> findStationery(VT040007EntityRqFindSt requestParam);

	/**
	 * Used fill Stationery
	 * 
	 * @param Map<String, Object>
	 */
	public void fillStationery(Map<String, Object> data);
	
	/**
	 * Check ServiceID has existed on ISSUES_SERVICE_HISTORY table yet?
	 * 
	 * @param id
	 * @return int
	 */
	public int checkExisted(String id);

	public String findIssuesServiceHistoryByIdIss(String id);
	
	/**
	 * Update history
	 * 
	 * @param VT040007EntityRqExecutive obj
	 * @return int
	 */
	public int updateHistory(Map<String, Object> data);

}
