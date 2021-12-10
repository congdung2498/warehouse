package com.viettel.vtnet360.vt05.vt050005.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050005.entity.VT050005RequestParamToApprove;
import com.viettel.vtnet360.vt05.vt050005.entity.VT050005RequestParamToSearch;
import com.viettel.vtnet360.vt05.vt050005.service.VT050005Service;

/**
 * @author DuyNK 09/10/2018
 */
@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL')")
@RestController
@RequestMapping("/com/viettel/vtnet360/vt05/vt050005")
public class VT050005Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050005Service vt050005Service;
	
	@Autowired
  private Properties configProperty;
  
  	/**
	 * find list ISSUES_STATIONERY_APPROVED to approve
	 * 
	 * @param param
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050005FindDataToApprove(@RequestBody VT050005RequestParamToSearch param,
			Principal principal) {
		logger.info("*********** vt050005_01 Find Data To Approve start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      param.setApproveUserName(userName);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = vt050005Service.findDataToApprove(param, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050005_01 Find Data To Approve end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * approve or reject request
	 * 
	 * @param param
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050005ApproveOrReject(@RequestBody VT050005RequestParamToApprove param,
			Principal principal) {
		logger.info("*********** vt050005_02 Approve or Reject start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      param.setApproveUserName(userName);
	      resp = vt050005Service.approveOrReject(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050005_02 Approve or Reject end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

}
