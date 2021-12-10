package com.viettel.vtnet360.vt04.vt040010.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt04.vt040010.entity.VT040010Condition;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010ReportService;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010Stationery;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010StationeryExcel;

/**
 * interface dao get data from database VT040010
 * 
 * @author ThangBT 17/09/2018
 *
 */

@Repository
public interface VT040010DAO {

	/**
	 * get list of registration service
	 * 
	 * @param condition
	 * @return List<VT040010ListRegistrationService>
	 */
	public List<VT040010ReportService> findListReportService(VT040010Condition condition);

	/**
	 * count total record of issue service
	 * 
	 * @param condition
	 * @return number
	 */
	public VT040010Condition countTotalRecord(VT040010Condition condition);

	/**
	 * get list of stationery
	 * 
	 * @param conStat
	 * @return List<VT040010ListStationery>
	 */
	public List<VT040010Stationery> findListStationery(VT040010Condition conStat);

	/**
	 * get total record of stationery
	 * 
	 * @param conStat
	 * @return number
	 */
	public int countTotalRecordStationery(VT040010Condition conStat);

	/**
	 * @return list of stationery to export excel
	 */
	public List<VT040010StationeryExcel> findListStationeryExcel(VT040010Condition condition);

	public String[] findListUnit();

	public String[] findUnitManager(String userName);

}
