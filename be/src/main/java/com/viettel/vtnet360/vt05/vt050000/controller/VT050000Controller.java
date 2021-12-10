package com.viettel.vtnet360.vt05.vt050000.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoToFindListEmployeeOfUnit;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoToFindStationery;
import com.viettel.vtnet360.vt05.vt050000.service.VT050000Service;

@RestController
@RequestMapping("/com/viettel/vtnet360/vt05/vt050000")
public class VT050000Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050000Service vt050000Service;
	
	@Autowired
  private Properties configProperty;

	/**
	 * find list stationery by stationeryName
	 * 
	 * @param stationery
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050000FindStationeryByName(
			@RequestBody VT050000InfoToFindStationery info) {
		logger.info("*********** vt050000_01 Get Stationery By Name start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt050000Service.findStationeryByName(info);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050000_01 Get Stationery By Name end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * find remaining spending limit of this user logged on in this month
	 * 
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050000FindRemainingSpendingLimit(@RequestBody BaseEntity info, Principal principal) {
		logger.info("*********** vt050000_02 Get Remaining Spending start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = vt050000Service.findRemainingSpendingLimit(userName, roleList );
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050000_02 Get Remaining Spending end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	/**
	 * find list employee of unit of hcdv and unitChild
	 * 
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050000FindListEmployeeOfUnit(@RequestBody VT050000InfoToFindListEmployeeOfUnit info, Principal principal) {
		logger.info("*********** vt050000_03 Get List Employee Of Unit Of Hcdv And Child start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String hcdvUserName = (String) oauth.getPrincipal();
	      info.setHcdvUserName(hcdvUserName);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = vt050000Service.findListEmployeeOfUnit(info, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050000_03 Get List Employee Of Unit Of Hcdv And Child end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
}
