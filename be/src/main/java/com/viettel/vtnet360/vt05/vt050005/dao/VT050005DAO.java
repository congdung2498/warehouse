package com.viettel.vtnet360.vt05.vt050005.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt05.vt050005.entity.VT050005DataResponse;
import com.viettel.vtnet360.vt05.vt050005.entity.VT050005InfoOfEmployeeRequest;
import com.viettel.vtnet360.vt05.vt050005.entity.VT050005InfoToFindItemRequest;
import com.viettel.vtnet360.vt05.vt050005.entity.VT050005InfoToUpdateIssuesStationeryApproveStatus;
import com.viettel.vtnet360.vt05.vt050005.entity.VT050005InfoUpdateIssuesStationeryItemStatus;
import com.viettel.vtnet360.vt05.vt050005.entity.VT050005PlaceIDOfIssuesStationeryApprove;
import com.viettel.vtnet360.vt05.vt050005.entity.VT050005RequestParamToSearch;

/**
 * @author DuyNK 04/10/2018
 */
@Repository
public interface VT050005DAO {

	/**
	 * @param param
	 * @return List<VT050005DataResponse>
	 */
	public List<VT050005DataResponse> findIssuesStationeryApprove(VT050005RequestParamToSearch param);

	/**
	 * update status(approve or reject) to ISSUES_STATIONERY_APPROVED
	 * 
	 * @param info
	 */
	public void updateIssuesStationeryApproveStatus(VT050005InfoToUpdateIssuesStationeryApproveStatus info);

	/**
	 * update status(approve or reject) to ISSUES_STATIONERY_ITEMS
	 * 
	 * @param info
	 */
	public void updateIssuesStationeryItemStatus(VT050005InfoUpdateIssuesStationeryItemStatus info);

	/**
	 * find ISSUES_STATIONERY_ID to change status in table ISSUES_STATIONERY
	 * 
	 * @param issuesStationeryApproveID
	 * @return List<String>
	 */
	public List<String> findIssuesStationeryID(String issuesStationeryApproveID);

	/**
	 * find info of employee request to send sms and notify
	 * 
	 * @param approveID
	 * @return List<VT050005InfoOfEmployeeRequest>
	 */
	public List<VT050005InfoOfEmployeeRequest> findEmployeeRequest(String approveID);

	/**
	 * find info of items that employee request to send sms and notify
	 * 
	 * @param info
	 * @return List<String>
	 */
	public List<String> findStationeryItemRequeset(VT050005InfoToFindItemRequest info);

	/**
	 * find placeID for each request
	 * 
	 * @param listIssuesStationeryApproveID
	 * @return VT050005PlaceIDOfIssuesStationeryApprove
	 */
	public List<VT050005PlaceIDOfIssuesStationeryApprove> findLocationOfRequestForEachRequest(List<String> listIssuesStationeryApproveID);
	
	/**
	 * find info of VPTCT to send sms and notify
	 * 
	 * @param placeID
	 * @return List<String>
	 */
	public List<String> findVPTCT(int placeID);

	/**
	 * check condition of request before update to ISSUES_STATIONERY_APPROVED
	 * 
	 * @param info
	 * @return Integer
	 */
	public int checkConditionBeforeUpdateIssuesApprove(VT050005InfoToUpdateIssuesStationeryApproveStatus info);
}
