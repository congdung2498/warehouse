package com.viettel.vtnet360.vt05.vt050006.controller;

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
import com.viettel.vtnet360.vt05.vt050006.entity.VT050006RequestParam;
import com.viettel.vtnet360.vt05.vt050006.service.VT050006Service;

/**
 * @author DuyNK 09/10/2018
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt05/vt050006")
public class VT050006Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050006Service vt050006Service;
	
	@Autowired
  private Properties configProperty;

	/**
	 * find data of 1 request to approve
	 * 
	 * @param param
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)	
	public ResponseEntity<ResponseEntityBase> vt050005FindDataToApprove(@RequestBody VT050006RequestParam param) {
		logger.info("*********** vt050006_01 Find Data To Approve start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = vt050006Service.findDataToApprove(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050006_01 Find Data To Approve end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/findDataToApproveProcess", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)	
	public ResponseEntity<ResponseEntityBase> findDataToApproveProcess(@RequestBody VT050006RequestParam param) {
		logger.info("*********** findDataToApproveProcess Find Data To Approve start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = vt050006Service.findDataToApproveProcess(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** findDataToApproveProcess Find Data To Approve end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findInfoRequestDetailProcessDetails", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)	
	public ResponseEntity<ResponseEntityBase> findInfoRequestDetailProcessDetails(@RequestBody VT050006RequestParam param) {
		logger.info("*********** findInfoRequestDetailProcessDetails Find Data To Approve start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = vt050006Service.findInfoRequestDetailProcessDetails(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** findInfoRequestDetailProcessDetails Find Data To Approve end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
}
