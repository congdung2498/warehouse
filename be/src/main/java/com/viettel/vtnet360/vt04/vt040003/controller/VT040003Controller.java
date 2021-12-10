package com.viettel.vtnet360.vt04.vt040003.controller;

import java.security.Principal;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040003.entity.VT040003EntityRqREGService;
import com.viettel.vtnet360.vt04.vt040003.service.VT040003Service;

/**
 * Class VT040003Controller
 * 
 * @author KienHT 27/09/2018
 * 
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt04")
public class VT040003Controller extends BaseController {

	@Autowired
	private VT040003Service vt040003Service;

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
  private Properties configProperty;

	/**
	 * User request issues Service
	 * 
	 * @param VT040003EntityRqREGService
	 * @param Principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/vt040003/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> api04000301(@RequestBody VT040003EntityRqREGService requestParam, Principal principal) {

		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = vt040003Service.issuesService(requestParam, principal);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reponse, HttpStatus.OK);
	}
}
