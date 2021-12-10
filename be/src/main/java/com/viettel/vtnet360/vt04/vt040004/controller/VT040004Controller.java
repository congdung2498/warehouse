package com.viettel.vtnet360.vt04.vt040004.controller;

import java.security.Principal;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040004.entity.VT040004EntityRq;
import com.viettel.vtnet360.vt04.vt040004.service.VT040004Service;
import org.apache.log4j.Logger;
/**
 * Class VT040004Controller
 * 
 * @author KienHT 4/10/2018
 * 
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt04")
public class VT040004Controller extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private VT040004Service vt040004Service;
	
	@Autowired
  private Properties configProperty;

	/**
	 * User request issuesedService For Approve
	 * 
	 * @param VT040004EntityRq
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/vt040004/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> api04000401(@RequestBody VT040004EntityRq requestParam,
			Principal principal, OAuth2Authentication authentication) {

		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = vt040004Service.issuesedServiceForApprove(requestParam, principal, authentication);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reponse, HttpStatus.OK);
	}
}
