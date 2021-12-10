package com.viettel.vtnet360.vt02.vt020008.controller;

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
import com.viettel.vtnet360.vt02.vt020008.entity.VT020008ParamRequest;
import com.viettel.vtnet360.vt02.vt020008.service.VT020008Service;

/**
 * Class controller for screen VT020008 : rating lunch
 * 
 * @author DuyNK 19/09/2018
 */
@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_HC_DV')")
@RestController
@RequestMapping("/com/viettel/vtnet360/vt02/vt020008")
public class VT020008Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT020008Service vt020008Service;

	@Autowired
  private Properties configProperty;
	
	/**
	 * update rating and comment
	 * 
	 * @param param
	 * @param principal
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020008UpdateRating(@RequestBody VT020008ParamRequest param, Principal principal) {
		logger.info("*********** vt020008_01 update rating lunch date start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      param.setUpdateUser(userName);
	      resp = vt020008Service.updateRating(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020008_01 update rating lunch end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

}
