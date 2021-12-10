package com.viettel.vtnet360.vt05.vt050008.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt05.vt050008.entity.VT050008DataResponse;
import com.viettel.vtnet360.vt05.vt050008.entity.VT050008InfoOfEmployeeRequest;
import com.viettel.vtnet360.vt05.vt050008.entity.VT050008RequestParamToApprove;
import com.viettel.vtnet360.vt05.vt050008.entity.VT050008RequestParamToSearch;

/**
 * @author DuyNK 04/10/2018
 */
@Repository
public interface VT050008DAO {

	/**
	 * find list ISSUES_STATIONERY_APPROVED to approve
	 * 
	 * @param param
	 * @return List<VT050008DataResponse>
	 */
	public List<VT050008DataResponse> findIssuesStationeryApprove(VT050008RequestParamToSearch param);

	/**
	 * update status && info to ISSUES_STATIONERY_APPROVED when CVP approve or
	 * reject
	 * 
	 * @param info
	 */
	public void updateIssuesStationeryApprove(VT050008RequestParamToApprove param);

	/**
	 * update status && info to ISSUES_STATIONERY_ITEMS when CVP approve or reject
	 * 
	 * @param param
	 */
	public void updateIssuesStationeryItems(VT050008RequestParamToApprove param);
	
	/**
	 * get info of employee request
	 * 
	 * @param listRequestID
	 * @return List<VT050008InfoOfEmployeeRequest>
	 */
	public List<VT050008InfoOfEmployeeRequest> findInfoOfEmployeeRequest(List<String> listRequestID);
	
	/**
	 * get list ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ID
	 * 
	 * @param listRequestID
	 * @return List<String>
	 */
	public List<String> findListIssuesStationeryID(List<String> listRequestID);
	
	/**
	 * get list vptctUserName to send sms and notify
	 * 
	 * @param listRequestID
	 * @return List<String>
	 */
	public List<String> findVptctUserName(List<String> listRequestID);
}
