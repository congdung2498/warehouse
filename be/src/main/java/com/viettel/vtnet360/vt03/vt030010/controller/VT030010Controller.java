package com.viettel.vtnet360.vt03.vt030010.controller;

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
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.driving.dto.SearchData;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030010.service.VT030010Service;
import com.viettel.vtnet360.vt03.vt030010.service.VT030010ServiceImpl;

@RestController
@RequestMapping("/com/viettel/vtnet360/vt03/vt030010")
public class VT030010Controller extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private VT030010ServiceImpl vt030010ServiceImpl;
	
	@Autowired
	private VT030010Service vt030010Service;
	
	@Autowired
  private Properties configProperty;
	
	@RequestMapping(value = "/01", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getListBCbyEmployee() {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			resp = vt030010ServiceImpl.getListBCbyEmployee();
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}

		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getJourney(@RequestBody BaseEntity info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt030010ServiceImpl.getJourney();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}

		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getJourneyById", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	  public ResponseEntity<ResponseEntityBase> getJourneyById(@RequestBody SearchData searchData) {
	   
	    logger.info("*********** getJourneyById get list searchAllCars start***********");
	    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	    try {
	      if(isValidated(configProperty, searchData.getSecurityUsername(), searchData.getSecurityPassword())) {
	        resp = vt030010Service.getJourneyById(searchData);
	        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
	      }
	    } catch (Exception e) {
	      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	    }
	    logger.info("*********** getJourneyById get list searchAllCars end***********");
	    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	  }
	
	
	
	
}
