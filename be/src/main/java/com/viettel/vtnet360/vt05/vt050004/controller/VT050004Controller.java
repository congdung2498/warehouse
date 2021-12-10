package com.viettel.vtnet360.vt05.vt050004.controller;

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
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004InfoToSearchIssuesStationeryItems;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004ParamToInsert;
import com.viettel.vtnet360.vt05.vt050004.service.VT050004Service;

/**
 * @author DuyNK 09/10/2018
 */
@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HC_DV')")
@RestController
@RequestMapping("/com/viettel/vtnet360/vt05/vt050004")
public class VT050004Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050004Service vt050004Service;

	@Autowired
  private Properties configProperty;
	
	/**
	 * find list issues stationery items to send manager approve
	 * 
	 * @param info
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050004FindIssuesStationeryItems(
			@RequestBody VT050004InfoToSearchIssuesStationeryItems info, Principal principal) {
		logger.info("*********** vt050004_01 find Issues Stationery Items start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      info.setManagerUserName(userName);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = vt050004Service.findIssuesStationeryItems(info, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050004_01 find Issues Stationery Items end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> countIssuesStationeryItems(
			@RequestBody VT050004InfoToSearchIssuesStationeryItems info, Principal principal) {
		logger.info("*********** vt050004_01 find Issues Stationery Items start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      info.setManagerUserName(userName);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = vt050004Service.countIssuesStationeryItems(info, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050004_01 find Issues Stationery Items end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	
	/**
	 * HCDV send request approve to manager
	 * 
	 * @param param
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050004SendRequestToManagerApprove(
			@RequestBody VT050004ParamToInsert param, Principal principal) {
		logger.info("*********** vt050004_02 Send Request To Manager Approve start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      param.setEmployeeUserName(userName);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = vt050004Service.approveIssuesStationeryItems(param, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050004_02 Send Request To Manager Approve end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
}
