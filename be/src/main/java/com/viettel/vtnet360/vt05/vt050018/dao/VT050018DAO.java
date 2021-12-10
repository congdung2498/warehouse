package com.viettel.vtnet360.vt05.vt050018.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt05.vt050018.entity.VT050018DataDetail;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018DataResponse;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018InfoOfEmployeeRequest;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018InfoToCheckConditionBeforeUpdateIssuesApprove;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018InfoToUpdateIssuesStaioneryApprove;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018InfoToUpdateIssuesStationeryItem;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018RequestParamToSearch;

/**
 * @author DuyNK
 * 
 */
@Repository
public interface VT050018DAO {

	/**
	 * find info detail of 1 request of NV HCDV(info of request of hcdv)
	 * 
	 * @param param
	 * @return List<vt050018DataResponse>
	 */
	public VT050018DataResponse findData(VT050018RequestParamToSearch param);

	/**
	 * find info detail of 1 request of NV HCDV(info of request of employee )
	 * 
	 * @param param
	 * @return
	 */
	public List<VT050018DataDetail> findDataDetail(VT050018RequestParamToSearch param);

	/**
	 * find total stationery requset of each item
	 * 
	 * @param requestID
	 * @return List<VT050018InfoOfEmployeeRequest>
	 */
	public List<VT050018InfoOfEmployeeRequest> findTotalRequestOfEmployee(String requestID);

	/**
	 * update to ISSUES_STATIONERY_APPROVED when HCDV confirm received stationery
	 * from VPTCT
	 * 
	 * @param info
	 */
	public void updateIssuesStationeryApprove(VT050018InfoToUpdateIssuesStaioneryApprove info);

	/**
	 * update to ISSUES_STATIONERY_ITEMS when HCDV confirm received stationery
	 * from VPTCT
	 * 
	 * @param info
	 */
	public void updateIssuesStationeryItems(VT050018InfoToUpdateIssuesStationeryItem info);
	
	/**
	 * find ISSUES_STATIONERY_ID to change status in table ISSUES_STATIONERY
	 * 
	 * @param issuesStationeryApproveID
	 * @return List<String>
	 */
	public List<String> findIssuesStationeryID(String issuesStationeryApproveID);
	
	/**
	 * check condition of request before update to ISSUES_STATIONERY_APPROVED
	 * 
	 * @param info
	 * @return Integer
	 */
	public int checkConditionBeforeUpdateIssuesApprove(VT050018InfoToCheckConditionBeforeUpdateIssuesApprove info);
	
	public void refuseIssuesStationeryApprove(VT050018InfoToUpdateIssuesStaioneryApprove info);
	
	public void refuseIssuesStationeryItems(VT050018InfoToUpdateIssuesStationeryItem info);

}
