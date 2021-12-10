package com.viettel.vtnet360.vt03.vt030002.controller;

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
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.driving.dto.SearchData;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030002.entity.Cars;
import com.viettel.vtnet360.vt03.vt030002.service.VT030002Service;
import com.viettel.vtnet360.vt03.vt030002.service.VT030002ServiceImpl;

/**
 * Class controller for screen VT030002 : add list car
 * 
 * @author SonVSH 17/09/2018
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt03/vt030002")
public class VT030002Controller extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private VT030002ServiceImpl vt030002ServiceImpl;
	@Autowired
	private VT030002Service vt030002Service;
	
	@Autowired
  private Properties configProperty;
	
	/**
	 * Select all car in list cars
	 * 
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> selectallCars123(@RequestBody BaseEntity info) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt030002ServiceImpl.selectallCars();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}

		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Select all seats of car
	 * 
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getCarSeat(@RequestBody BaseEntity info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt030002ServiceImpl.getCarSeat();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Select all types of car
	 * 
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getCarType(@RequestBody BaseEntity info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt030002ServiceImpl.getCarType();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Select all squad to set up car
	 * 
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getSquad(@RequestBody BaseEntity info) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt030002ServiceImpl.getSquad();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}

		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Search car with some conditions
	 * 
	 * @param car
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchCars(@RequestBody Cars cars) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, cars.getSecurityUsername(), cars.getSecurityPassword())) {
		    resp = vt030002ServiceImpl.searchCar(cars);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Add new cars
	 * 
	 * @param car
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> insertCars(@RequestBody Cars cars, Principal principal) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, cars.getSecurityUsername(), cars.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userAssign = (String) oauth.getPrincipal();
	      cars.setUserAssign(userAssign);
	      resp = vt030002ServiceImpl.insertCars(cars);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Update information of cars
	 * 
	 * @param car
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/07", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateCars(@RequestBody Cars cars, Principal principal) {
		System.out.println("------------------CAR IDD = "+cars.getCarId());
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, cars.getSecurityUsername(), cars.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userAssign = (String) oauth.getPrincipal();
	      cars.setUserAssign(userAssign);
	      resp = vt030002ServiceImpl.updateCars(cars);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Delete cars from list car
	 * 
	 * @param car
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/08", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase deleteCar(@RequestBody Cars car, Principal principal) throws Exception {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, car.getSecurityUsername(), car.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userAssign = (String) oauth.getPrincipal();
	      car.setUserAssign(userAssign);
	      resp = vt030002ServiceImpl.deleteCar(car);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}
	
	@RequestMapping(value = "/getCarTypeById", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	  public ResponseEntity<ResponseEntityBase> getCarTypeById(@RequestBody SearchData searchData) {
	   
	    logger.info("*********** getCarTypeById get list searchAllCars start***********");
	    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	    try {
	      if(isValidated(configProperty, searchData.getSecurityUsername(), searchData.getSecurityPassword())) {
	        resp = vt030002Service.getCarTypeById(searchData);
	        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
	      }
	    } catch (Exception e) {
	      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	    }
	    logger.info("*********** getCarTypeById get list searchAllCars end***********");
	    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	  }
	
	
	@RequestMapping(value = "/getCarSeatById",method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getCarSeatById(@RequestBody SearchData searchData) {
		 logger.info("*********** getCarSeatById get list searchAllCars start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchData.getSecurityUsername(), searchData.getSecurityPassword())) {
		    resp = vt030002Service.getCarSeatById(searchData);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}
		 logger.info("*********** getCarSeatById get list searchAllCars END***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

}
