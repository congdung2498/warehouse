package com.viettel.vtnet360.vt05.vt050011.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.stationery.entity.ProcessIssuesStationery;
import com.viettel.vtnet360.stationery.entity.StatusById;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011InfoHcdvRequest;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011InfoRequest;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011InfoRequestDetail;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011InfoToInsertStationeryItemHistory;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011InfoToUpdateStorageHcdvRequest;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011RequestParamToExecute;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011RequestParamToSearch;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011StationeryIssuesItems;

/**
 * @author DuyNK 04/10/2018
 */
@Repository
public interface VT050011DAO {

	/**
	 * find info to give out to employee
	 * 
	 * @param info
	 * @return List<VT050011InfoRequestDetail>
	 */
	public List<VT050011InfoRequestDetail> findInfoRequest(VT050011RequestParamToSearch info);

	/**
	 * update ISSUES_STATIONERY_APPROVED
	 * 
	 * @param param
	 * @return Integer
	 */
	public int updateIssuesStationeryApprove(VT050011RequestParamToExecute param);

	/**
	 * update ISSUES_STATIONERY_ITEMS
	 * 
	 * @param param
	 */
	public void updateIssuesStationeryItems(VT050011RequestParamToExecute param);

	/**
	 * insert to ISSUES_STATIONERY_ITEM_HISTORY when vptct pause executing
	 * 
	 * @param info
	 */
	public void insertStationeryItemHistory(VT050011InfoToInsertStationeryItemHistory info);

	/**
	 * get list ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ID
	 * 
	 * @param requestID
	 * @return List<String>
	 */
	public List<String> findListIssuesStationeryID(String requestID);
	
	/**
	 * update total fulfill to STORAGE_HCDV_REQUEST
	 * 
	 * @param info
	 */
	public void updateStorageHcdvRequest(VT050011InfoToUpdateStorageHcdvRequest info);
	
	/**
	 * check condition of request before update to ISSUES_STATIONERY_APPROVED
	 * 
	 * @param info
	 * @return Integer
	 */
	public int checkConditionBeforeUpdateIssuesApprove(VT050011RequestParamToExecute info);
	
	public List<VT050011StationeryIssuesItems> getStatusByApproveId(String requestID);
	
	public VT050011InfoHcdvRequest findInfoHcdvRequest(String requestID);
	
	public VT050011InfoRequest findInfoRequestStationery(VT050011RequestParamToSearch info);
	
	public List<StatusById> findStatusProcessIssuesStationery(VT050011RequestParamToExecute processIssuesStationery);
}
