//package com.viettel.vtnet360.vt05.vt050008.service;
//
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//
//import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
//import com.viettel.vtnet360.vt05.vt050008.entity.VT050008RequestParamToApprove;
//import com.viettel.vtnet360.vt05.vt050008.entity.VT050008RequestParamToSearch;
//
///**
// * @author DuyNK 09/10/2018
// */
//public interface VT050008Service {
//
//	/**
//	 * find list ISSUES_STATIONERY_APPROVED to approve
//	 * 
//	 * @param param
//	 * @return ResponseEntityBase
//	 */
//	public ResponseEntityBase findDataToApprove(VT050008RequestParamToSearch param,
//			Collection<GrantedAuthority> roleList);
//
//	/**
//	 * approve or reject request
//	 * 
//	 * @param param
//	 * @return ResponseEntityBase
//	 */
//	public ResponseEntityBase approveOrReject(VT050008RequestParamToApprove param);
//
//	/**
//	 * send sms and notify to HCDV
//	 * 
//	 * @param approveStatus
//	 * @param approveUserName
//	 * @param listIssuesStationeryApproveID
//	 * @throws Exception
//	 */
//	public void sendSmsAndNotifyToHCDV(Boolean approveStatus, String approveUserName,
//			List<String> listIssuesStationeryApproveID) throws Exception;
//
//	/**
//	 * send sms and notify to VPTCT
//	 * 
//	 * @param approveUserName
//	 * @param listIssuesStationeryApproveID
//	 * @throws Exception
//	 */
//	public void sendSmsAndNotifyToVPTCT(String approveUserName, List<String> listIssuesStationeryApproveID)
//			throws Exception;
//}
