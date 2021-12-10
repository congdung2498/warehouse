package com.viettel.vtnet360.vt05.vt050000.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoHcdvRequest;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoOfEmployee;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoToFindListEmployeeOfUnit;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoToFindSpendingLimit;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoToFindSpendingLimitUnit;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoToFindStationery;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoTofindMoneyUsed;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000IssuesStationery;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000StationeryInfo;

/**
 * @author DuyNK 04/10/2018
 */
/**
 * @author DuyNK
 *
 */
/**
 * @author DuyNK
 *
 */
@Repository
public interface VT050000DAO {

	/**
	 * find list stationery by stationeryName
	 * 
	 * @param info
	 * @return List<VT050000StationeryInfo>
	 */
	public List<VT050000StationeryInfo> findStationeryByName(VT050000InfoToFindStationery info);

	/**
	 * find total money used in this month of this user logged on ( do not receive
	 * items )
	 * 
	 * @param info
	 * @return Double
	 */
	public double findMoneyUseInThisMonthNotFinish(VT050000InfoTofindMoneyUsed info);

	/**
	 * find total money used in this month of this user logged on ( received items )
	 * 
	 * @param info
	 * @return Double
	 */
	public double findMoneyUseInThisMonthFinish(VT050000InfoTofindMoneyUsed info);

	/**
	 * find spending limit of user logged
	 * 
	 * @param info
	 * @return Double
	 */
	public double findSpendingLimit(int unitId);

	/**
	 * find all status of 1 request(ISSUES_STATIONERY)
	 * 
	 * @param issuesStationeryID
	 * @return List<Integer>
	 */
	public List<Integer> findIssuesStationeryStatus(String issuesStationeryID);

	/**
	 * update status to ISSUES_STATIONERY
	 * 
	 * @param param
	 */
	public void updateIssuesStaioneryStatus(VT050000IssuesStationery param);

	/**
	 * find info hcdv request to send sms and notify
	 * 
	 * @param listRequestID
	 * @return List<VT050000InfoHcdvRequest>
	 */
	public List<VT050000InfoHcdvRequest> findInfoHcdvRequest(List<String> listRequestID);
	
	/**
	 * find fullname of employee by userName use for content of sms and notify
	 * 
	 * @param userName
	 * @return String
	 */
	public String findFullNameByUserName(String userName);
	

	/**
	 * find spending limit of unit that HCDV was configed
	 * 
	 * @param info
	 * @return Double
	 */
	public double findSpendingLimitUnit(VT050000InfoToFindSpendingLimitUnit info);
	
	/**
	 * if user logged on is HCDV =>
	 * check configed in STATIONERY_STAFF or not
	 * 
	 * @param userName
	 * @return
	 */
	public int checkHcdvInStaffConfig(String userName);
	
	
	public int checkHctctInStaffConfig(String userName);
	
	public int checkUnitdQuotaConfig(int unitId);
	
	/**
	 * find list employee of unit of hcdv and unitChild
	 * 
	 * @param info
	 * @return List<VT050000InfoOfEmployee>
	 */
	public List<VT050000InfoOfEmployee> findListEmployeeOfUnit(VT050000InfoToFindListEmployeeOfUnit info);
	
	public double findSpendingLimitUnitExcel(int unitId);
}
