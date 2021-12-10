//package com.viettel.vtnet360.vt05.vt050008.controller;
//
//import java.security.Principal;
//import java.util.Collection;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.viettel.vtnet360.vt00.common.Constant;
//import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
//import com.viettel.vtnet360.vt05.vt050008.entity.VT050008RequestParamToApprove;
//import com.viettel.vtnet360.vt05.vt050008.entity.VT050008RequestParamToSearch;
//import com.viettel.vtnet360.vt05.vt050008.service.VT050008Service;
//
///**
// * @author DuyNK 09/10/2018
// */
//@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_CVP')")
//@RestController
//@RequestMapping("/com/viettel/vtnet360/vt05/vt050008")
//public class VT050008Controller {
//
//	private final Logger logger = Logger.getLogger(this.getClass());
//
//	@Autowired
//	private VT050008Service vt050008Service;
//
//	/**
//	 * find list ISSUES_STATIONERY_APPROVED to approve
//	 * 
//	 * @param param
//	 * @param principal
//	 * @return ResponseEntity<ResponseEntityBase>
//	 */
//	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
//	public ResponseEntity<ResponseEntityBase> vt050008FindDataToApprove(@RequestBody VT050008RequestParamToSearch param,
//			Principal principal) {
//		logger.info("*********** vt050008_01 Find Data To Approve start***********");
//		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
//		try {
//			// get userName
//			OAuth2Authentication oauth = (OAuth2Authentication) principal;
//			String userName = (String) oauth.getPrincipal();
//			param.setCvpUserName(userName);
//			Collection<GrantedAuthority> roleList = oauth.getAuthorities();
//			resp = vt050008Service.findDataToApprove(param, roleList);
//		} catch (Exception e) {
//			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
//		}
//		logger.info("*********** vt050008_01 Find Data To Approve end***********");
//		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
//	}
//
//	/**
//	 * approve or reject request
//	 * 
//	 * @param param
//	 * @param principal
//	 * @return ResponseEntity<ResponseEntityBase>
//	 */
//	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
//	public ResponseEntity<ResponseEntityBase> vt050008ApproveOrReject(@RequestBody VT050008RequestParamToApprove param,
//			Principal principal) {
//		logger.info("*********** vt050008_02 Approve or Reject start***********");
//		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
//		try {
//			// get userName
//			OAuth2Authentication oauth = (OAuth2Authentication) principal;
//			String userName = (String) oauth.getPrincipal();
//			param.setCvpUserName(userName);
//			resp = vt050008Service.approveOrReject(param);
//		} catch (Exception e) {
//			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
//		}
//		logger.info("*********** vt050008_02 Approve or Reject end***********");
//		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
//	}
//
//}
