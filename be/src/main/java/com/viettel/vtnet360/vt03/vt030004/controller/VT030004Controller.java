package com.viettel.vtnet360.vt03.vt030004.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.viettel.vtnet360.vt03.vt030004.entity.DriveCar;
import com.viettel.vtnet360.vt03.vt030004.entity.DriverCarWrapper;
import com.viettel.vtnet360.vt03.vt030004.service.VT030004ServiceImpl;

/**
 * Class controller for screen VT030003 : matched car
 * 
 * @author SonVSH 17/09/2018
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt03/vt030004")
public class VT030004Controller extends BaseController  {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private VT030004ServiceImpl vt030004ServiceImpl;
	
	@Autowired
  private Properties configProperty;

	/**
	 * Match cars for drivers
	 * 
	 * @param listCarDrive
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> insertDriveCar(@RequestBody DriverCarWrapper wrapper, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		String userName = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
		  if(isValidated(configProperty, wrapper.getSecurityUsername(), wrapper.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      for (DriveCar item : wrapper.getMatches()) {
	        item.setCreateUser(userName);
	        item.setCreateDate(dateFormat.format(date));
	      }
	      resp = vt030004ServiceImpl.insertCarDrive(wrapper.getMatches());
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Find cars had been matched for current driver
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findMatchedCar(@RequestBody DriveCar driveCar) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
		  if(isValidated(configProperty, driveCar.getSecurityUsername(), driveCar.getSecurityPassword())) {
		    resp = vt030004ServiceImpl.findMatchedCar(driveCar);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Delete matched car of current driver
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> deleteMatchedCar(@RequestBody DriveCar driveCar) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
		  if(isValidated(configProperty, driveCar.getSecurityUsername(), driveCar.getSecurityPassword())) {
		    resp = vt030004ServiceImpl.deleteMatchedCar(driveCar);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Find suggest car in driver's squad
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getSuggestCar(@RequestBody DriveCar driveCar) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
		  if(isValidated(configProperty, driveCar.getSecurityUsername(), driveCar.getSecurityPassword())) {
		    resp = vt030004ServiceImpl.findSuggestCar(driveCar);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
}
