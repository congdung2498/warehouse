package com.viettel.vtnet360.vt05.vt050010.controller;

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
import com.viettel.vtnet360.vt05.vt050010.entity.CheckPlaceByUser;
import com.viettel.vtnet360.vt05.vt050010.entity.VT050010RequestParam;
import com.viettel.vtnet360.vt05.vt050010.service.VT050010Service;

/**
 * @author DuyNK 09/10/2018
 */
@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP_VPP')")
@RestController
@RequestMapping("/com/viettel/vtnet360/vt05/vt050010")
public class VT050010Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050010Service vt050010Service;
	
	@Autowired
  private Properties configProperty;

	/**
	 * find data request
	 * 
	 * @param param
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050010FindDataToGiveOut(@RequestBody VT050010RequestParam param,
			Principal principal) {
		logger.info("*********** vt050010_01 Find Data To Give Out start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = vt050010Service.findData(param, userName, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050010_01 Find Data To Give Out end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	@RequestMapping(value = "/findPlaceByUserName", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findPlaceByUserName( @RequestBody CheckPlaceByUser checkPlaceByUser ,
			Principal principal) {
		logger.info("*********** vt050010_01 Find Data To Give Out start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, checkPlaceByUser.getSecurityUsername(), checkPlaceByUser.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      checkPlaceByUser.setUserName(userName);
	      resp = vt050010Service.findPlaceByUserName( checkPlaceByUser,roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050010_01 Find Data To Give Out end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
}
