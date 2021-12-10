package com.viettel.vtnet360.vt03.vt030000.controller;

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
import com.viettel.vtnet360.driving.dto.SearchData;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000ResponseBookCarRequest;
import com.viettel.vtnet360.vt03.vt030000.service.VT030000Service;

/**
 * Class controller of VT030000
 * 
 * @author ThangBT 11/09/2018
 * 
 *
 */

@RestController
@RequestMapping("/com/viettel/vtnet360/vt03/vt030000")
public class VT030000Controller extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  
	@Autowired
	private VT030000Service vt030000Service;

	@Autowired
  private Properties configProperty;

	/**
	 * Find Bookcar's Resquest base on it's id
	 * 
	 * @author CuongHD
	 * @param VT030006ApproveBookCar
	 * @return VT030006ResponseApproveBookCar
	 * @throws Exception
	 */
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase apiVt0300002(@RequestBody VT030000ResponseBookCarRequest obj) throws Exception {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    response = vt030000Service.findBookCarRequest(obj);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return response;

	}

	/**
	 * API used to select all information of DriveSquad object
	 * 
	 * @author ThangBT
	 * @return List<DriveSquad>
	 * @throws Exception
	 */
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findAllDriveSquad(@RequestBody VT030000EntityDriveSquad squadInfo) throws Exception {
		ResponseEntityBase reb = null;
		try {
		  if(isValidated(configProperty, squadInfo.getSecurityUsername(), squadInfo.getSecurityPassword())) {
		    reb = vt030000Service.findAllSquad(squadInfo);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;

	}
	
	@RequestMapping(value = "/findAllSquadById", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	  public ResponseEntity<ResponseEntityBase> findAllSquadById(@RequestBody SearchData searchData) {
	    logger.info("*********** findAllSquadById get list searchAllCars start***********");
	    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	    try {
	      if(isValidated(configProperty, searchData.getSecurityUsername(), searchData.getSecurityPassword())) {
	        resp = vt030000Service.findAllSquadById(searchData);
	        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
	      }
	    } catch (Exception e) {
	      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	    }
	    logger.info("*********** findAllSquadById get list searchAllCars end***********");
	    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	  }
	
	
}
