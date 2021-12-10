package com.viettel.vtnet360.vt04.vt040000.controller;

import java.security.Principal;
import java.util.Properties;

import org.apache.log4j.Logger;
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
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityRqApprove;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityRqFind;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityRqUpdate;
import com.viettel.vtnet360.vt04.vt040000.service.VT040000Service;

/**
 * Class VT040003Controller
 * 
 * @author KienHT 27/09/2018
 * 
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt04")
public class VT040000Controller extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  
	@Autowired
	private VT040000Service vt040000Service;

	@Autowired
  private Properties configProperty;

	/**
	 * User find location by request
	 * 
	 * @param VT040000EntityRqFind and
	 * @param Principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/vt040000/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> api04000001(@RequestBody VT040000EntityRqFind requestParam,
			Principal principal) {
		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = vt040000Service.findService(requestParam, principal);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reponse, HttpStatus.OK);
	}

	/**
	 * User find Issued Service by request
	 * 
	 * @param VT040000EntityRqFind
	 * @param Principal
	 * @param OAuth2Authentication
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/vt040000/02", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> api04000002(@RequestBody VT040000EntityRqFind requestParam,
			Principal principal, OAuth2Authentication authentication) {
		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = vt040000Service.findIssuedService(requestParam, principal, authentication);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reponse, HttpStatus.OK);
	}

	/**
	 * find Detail Issud Service request
	 * 
	 * @param VT040000EntityRqFind
	 * @param Principal
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/vt040000/03", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> api04000003(@RequestBody VT040000EntityRqFind requestParam, Principal principal) {
		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = vt040000Service.finDatailService(requestParam, principal);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reponse, HttpStatus.OK);
	}

	/**
	 * Appove for issued Service
	 * 
	 * @param VT040000EntityRqFind
	 * @param Principal
	 * @param OAuth2Authentication
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/vt040000/04", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> api04000004(@RequestBody VT040000EntityRqApprove requestParam,
			Principal principal, OAuth2Authentication authentication) {
		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = vt040000Service.approveIssuedService(requestParam, principal, authentication);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reponse, HttpStatus.OK);
	}

	/**
	 * update for issued Service
	 * 
	 * @param VT040000EntityRqUpdate
	 * @param Principal
	 * @param OAuth2Authentication
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/vt040000/05", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> api04000005(@RequestBody VT040000EntityRqUpdate requestParam,
			Principal principal, OAuth2Authentication authentication) {
		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = vt040000Service.updateIssuedService(requestParam, principal, authentication);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reponse, HttpStatus.OK);
	}

}
