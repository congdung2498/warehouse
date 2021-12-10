package com.viettel.vtnet360.vt05.vt050007.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt05.vt050007.entity.VT050007DataResponse;
import com.viettel.vtnet360.vt05.vt050007.entity.VT050007InfoToFindRequestWaitingChiefOfStaffApprove;
import com.viettel.vtnet360.vt05.vt050007.entity.VT050007RequestParam;
import com.viettel.vtnet360.vt05.vt050007.entity.VT050007RequestParamToReject;

/**
 * @author DuyNK 04/10/2018
 */
@Repository
public interface VT050007DAO {

	/**
	 * search request to send to chief of staff approve
	 * 
	 * @param info
	 * @return List<VT050007DataResponse>
	 */
	public List<VT050007DataResponse> findRequestWaitingChiefOfStaffApprove(
			VT050007InfoToFindRequestWaitingChiefOfStaffApprove info);

	/**
	 * if user Logged on by role PMQT_HCVP_VPP => search by their place
	 * 
	 * @param userName
	 * @return Integer
	 */
	public int findPlaceIDOfVptctUserName(String userName);

	/**
	 * update status && info to ISSUES_STATIONERY_APPROVED when VPTCT send request to CVP
	 * 
	 * @param param
	 */
	public void updateIssuesStationeryApprove(VT050007RequestParam param);
	
	/**
	 * VPTCT reject request, not send to CVP
	 * 
	 * @param param
	 */
	public void rejectIssuesStationeryApprove(VT050007RequestParamToReject param);
	
	/**
	 * update status && info to ISSUES_STATIONERY_ITEMS when VPTCT send request to CVP
	 * 
	 * @param param
	 */
	public void updateIssuesStationeryItems(VT050007RequestParam param);
}
