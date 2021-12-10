package com.viettel.vtnet360.vt05.vt050004.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt05.vt050000.entity.VT050000IssuesStationeryApproved;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004DataResponse;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004InfoToCheckNumberOfValidRecordsRequest;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004InfoToFindTotalMoneyUsedOfUnit;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004InfoToInsertStorageHcdvRequest;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004InfoToSearchIssuesStationeryItems;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004InfoToUpdateIssuesStationeryItems;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004Stationery;
import com.viettel.vtnet360.vt05.vt050004.entity.VT50004CheckListRequestID;

/**
 * @author DuyNK 04/10/2018
 */
@Repository
public interface VT050004DAO {

	/**
	 * find list issues stationery items to send manager approve
	 * 
	 * @param info
	 * @return List<VT050004DataResponse>
	 */
	public List<VT050004DataResponse> findIssuesStationeryItems(VT050004InfoToSearchIssuesStationeryItems info);

	/**
	 * HCDV send request approve to manager
	 * 
	 * @param param
	 */
	public void insertIssuesStationeryApprove(VT050000IssuesStationeryApproved param);

	/**
	 * find IssuesStationeryApproveID just create to update to
	 * ISSUES_STATIONERY_ITEMS
	 * 
	 * @param userName
	 * @return String
	 */
	public String findIssuesStationeryApproveID(String userName);

	/**
	 * update status && ISSUES_STATIONERY_APPROVED_ID
	 * 
	 * @param info
	 */
	public void updateIssuesStationeryItems(VT050004InfoToUpdateIssuesStationeryItems info);
	
	/**
	 * find stationery of this request group by stationeryID to insert to STORAGE_HCDV_REQUEST
	 * 
	 * @param id
	 * @return List<VT050004Stationery>
	 */
	public List<VT050004Stationery> findStationeryByIssuesStationeryApproveID(String id);
	
	/**
	 * save info to STORAGE_HCDV_REQUEST
	 * 
	 * @param info
	 */
	public void insertToStorageHcdvRequest(VT050004InfoToInsertStorageHcdvRequest info);
	
	/**
	 * Check the number of valid records before send to LDDV approve
	 * 
	 * @param info
	 * @return Integer
	 */
	public int checkNumberOfValidRecords(VT050004InfoToCheckNumberOfValidRecordsRequest info);
	
	/**
	 * find unit of Hcdv logged on in STATIONERY_STAFF
	 * 
	 * @param userName
	 * @return Integer
	 */
	public int findUnitIDOfHcdvLoginInStationeryStaff(String userName);
	
	/**
	 * find all Hcdv was configed in STATIONERY_STAFF that has same unit of Hcdv logged on
	 * 
	 * @param unitID
	 * @return List<String>
	 */
	public List<String> findAllHcdvInStationeryStaffByUnitID(int unitID);
	
	/**
	 * calculate total money in all request not finish (except reject) in this unit of hcdv logged on in this month
	 * 
	 * @param info
	 * @return Double
	 */
	public double findTotalMoneyUsedNotFinishOfUnitInThisMonth(VT050004InfoToFindTotalMoneyUsedOfUnit info);
	
	/**
	 * calculate total money in all request finish (VPTCT execute && HCDV confirm receive) in this unit of hcdv logged on in this month
	 * 
	 * @param info
	 * @return Double
	 */
	public double findTotalMoneyUsedFinishOfUnitInThisMonth(VT050004InfoToFindTotalMoneyUsedOfUnit info);
	
	/**
	 *  calculate total money of this request
	 * 
	 * @param listIssuesStationeryID
	 * @return Double
	 */
	public double findTotalMoneyOfHcdvRequest(List<String> listIssuesStationeryID);
	
	/**
	 * 
	 * check user logged on configed in STATIONERY_STAFF or not
	 * 
	 * @param hcdvUserName
	 * @return Integer
	 */
	public int checkHcdvInStaffConfig(String hcdvUserName);
	
	public List<VT050004DataResponse> countIssuesStationeryItems(VT050004InfoToSearchIssuesStationeryItems info);
	
	public List<VT50004CheckListRequestID> getListRequestID(List<String> listRequestID);
}
