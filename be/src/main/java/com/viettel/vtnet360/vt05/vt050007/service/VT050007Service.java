//package com.viettel.vtnet360.vt05.vt050007.service;
//
//import java.util.Collection;
//
//import org.springframework.security.core.GrantedAuthority;
//import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
//import com.viettel.vtnet360.vt05.vt050007.entity.VT050007RequestParam;
//import com.viettel.vtnet360.vt05.vt050007.entity.VT050007RequestParamToFindData;
//import com.viettel.vtnet360.vt05.vt050007.entity.VT050007RequestParamToReject;
//
///**
// * @author DuyNK
// * 09/10/2018
// */
//public interface VT050007Service {
//
//	/**
//	 * find data of 1 request to approve
//	 * 
//	 * @param param
//	 * @return ResponseEntityBase
//	 */
//	public ResponseEntityBase findDataToApprove(VT050007RequestParamToFindData param, String userName, Collection<GrantedAuthority> roleList);
//	
//	/**
//	 * insert to ISSUES_STATIONERY_REGISTRY && update status to ISSUES_STATIONERY_ITEMS
//	 * 
//	 * @param param
//	 * @return ResponseEntityBase
//	 */
//	public ResponseEntityBase approveIssuesStationeryItems(VT050007RequestParam param);
//	
//	/**
//	 * VPTCT reject request, not send to CVP
//	 * 
//	 * @param param
//	 * @return ResponseEntityBase
//	 */
//	public ResponseEntityBase rejectIssuesStationeryItems(VT050007RequestParamToReject param);
//	
//	/**
//	 * send sms and notify to CVP
//	 * 
//	 * @param approveUserName
//	 * @param userName
//	 * @param idRecord
//	 * @param statusRecord
//	 * @throws Exception
//	 */
//	public void sendSmsAndNotifyToCVP(String approveUserName, String userName, int statusRecord) throws Exception;
//}
