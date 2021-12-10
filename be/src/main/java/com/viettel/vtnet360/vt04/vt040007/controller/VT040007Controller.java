package com.viettel.vtnet360.vt04.vt040007.controller;

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
import com.viettel.vtnet360.issuesService.service.IssuesServiceService;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040007.entity.VT040007EntityRqExecutive;
import com.viettel.vtnet360.vt04.vt040007.entity.VT040007EntityRqFindSt;
import com.viettel.vtnet360.vt04.vt040007.service.VT040007Service;

/**
 * Class VT040004Controller
 * 
 * @author KienHT 8/10/2018
 * 
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt04")
public class VT040007Controller extends BaseController {

  @Autowired
  private IssuesServiceService issuesServiceService;

  @Autowired
  private VT040007Service vt040007Service;

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
  private Properties configProperty;

	/**
	 * User executive Service
	 * 
	 * @param VT040004EntityRq
	 * @param Principal
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/vt040007/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> api04000701(@RequestBody VT040007EntityRqExecutive requestParam, Principal principal) {

		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = issuesServiceService.executiveService(requestParam, principal);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reponse, HttpStatus.OK);
	}

	/**
	 * User find Stationery
	 * 
	 * @param VT040004EntityRq
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/vt040007/02", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> api04000702(@RequestBody VT040007EntityRqFindSt requestParam, Principal principal) {

		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = vt040007Service.findStationery(requestParam, principal);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reponse, HttpStatus.OK);
	}
}
