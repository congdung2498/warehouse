package com.viettel.vtnet360.vt05.vt050011.controller;

import java.security.Principal;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011RequestParamToExecute;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011RequestParamToSearch;
import com.viettel.vtnet360.vt05.vt050011.service.VT050011Service;

/**
 * @author DuyNK 09/10/2018
 */
@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP_VPP')")
@RestController
@RequestMapping("/com/viettel/vtnet360/vt05/vt050011")
public class VT050011Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050011Service vt050011Service;
	
	@Autowired
  private Properties configProperty;

	/**
	 * find data of 1 request
	 * 
	 * @param param
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050011FindDataToGiveOut(
			@RequestBody VT050011RequestParamToSearch param) {
		logger.info("*********** vt050011_01 Find Data To Give Out start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = vt050011Service.findData(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050011_01 Find Data To Give Out end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * execute give out stationery request
	 * 
	 * @param param
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050011ExecuteGiveOut(@RequestBody VT050011RequestParamToExecute param,
			Principal principal) {
		logger.info("*********** vt050011_02 Execute Give Out start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      param.setVptctUserName(userName);
	      resp = vt050011Service.executeGiveOut(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050011_02 Execute Give Out end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
}
