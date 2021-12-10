//package com.viettel.vtnet360.vt05.vt050007.controller;
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
//import com.viettel.vtnet360.vt05.vt050007.entity.VT050007RequestParam;
//import com.viettel.vtnet360.vt05.vt050007.entity.VT050007RequestParamToFindData;
//import com.viettel.vtnet360.vt05.vt050007.entity.VT050007RequestParamToReject;
//import com.viettel.vtnet360.vt05.vt050007.service.VT050007Service;
//
///**
// * @author DuyNK 09/10/2018
// */
//@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP_VPP')")
//@RestController
//@RequestMapping("/com/viettel/vtnet360/vt05/vt050007")
//public class VT050007Controller {
//
//	private final Logger logger = Logger.getLogger(this.getClass());
//
//	@Autowired
//	private VT050007Service vt050007Service;
//
//	/**
//	 * find data of 1 request to approve
//	 * 
//	 * @param param
//	 * @param principal
//	 * @return ResponseEntity<ResponseEntityBase>
//	 */
//	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
//	public ResponseEntity<ResponseEntityBase> vt050007FindDataToApprove(
//			@RequestBody VT050007RequestParamToFindData param, Principal principal) {
//
//		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
//		try {
//			// get userName
//			OAuth2Authentication oauth = (OAuth2Authentication) principal;
//			String userName = (String) oauth.getPrincipal();
//			Collection<GrantedAuthority> roleList = oauth.getAuthorities();
//			resp = vt050007Service.findDataToApprove(param, userName, roleList);
//		} catch (Exception e) {
//			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
//		}
//		logger.info("*********** vt050007_01 Find Data To Approve end***********");
//		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
//	}
//
//	/**
//	 * approve request, insert to ISSUES_STATIONERY_REGISTRY && update status to
//	 * ISSUES_STATIONERY_ITEMS
//	 * 
//	 * @param param
//	 * @param principal
//	 * @return ResponseEntity<ResponseEntityBase>
//	 */
//	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
//	public ResponseEntity<ResponseEntityBase> vt050007Approve(@RequestBody VT050007RequestParam param,
//			Principal principal) {
//		logger.info("*********** vt050007_02 Approve request start***********");
//		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
//		try {
//			// get userName
//			OAuth2Authentication oauth = (OAuth2Authentication) principal;
//			String userName = (String) oauth.getPrincipal();
//			param.setVptctUserName(userName);
//			resp = vt050007Service.approveIssuesStationeryItems(param);
//		} catch (Exception e) {
//			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
//		}
//		logger.info("*********** vt050007_02 Approve request end***********");
//		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
//	}
//
//	/**
//	 * VP TCT reject request, not send to CVP
//	 * 
//	 * @param param
//	 * @param principal
//	 * @return ResponseEntity<ResponseEntityBase>
//	 */
//	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
//	public ResponseEntity<ResponseEntityBase> vt050007Reject(@RequestBody VT050007RequestParamToReject param,
//			Principal principal) {
//		logger.info("*********** vt050007_02 Approve request start***********");
//		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
//		try {
//			// get userName
//			OAuth2Authentication oauth = (OAuth2Authentication) principal;
//			String userName = (String) oauth.getPrincipal();
//			param.setVptctUserName(userName);
//			resp = vt050007Service.rejectIssuesStationeryItems(param);
//		} catch (Exception e) {
//			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
//		}
//		logger.info("*********** vt050007_02 Approve request end***********");
//		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
//	}
//}
