package com.viettel.vtnet360.vt01.vt010001.controller;

import java.security.Principal;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt01.vt010001.entity.VT010001EntityRpRegister;
import com.viettel.vtnet360.vt01.vt010001.entity.VT010001EntityRqRegister;
import com.viettel.vtnet360.vt01.vt010001.entity.VT010001SystemCode;
import com.viettel.vtnet360.vt01.vt010001.service.VT010001Service;
import com.viettel.vtnet360.vt03.vt030000.entity.Employee;

/**
 * Class controller VT010001
 * 
 * @author KienHT 09/08/2018
 * 
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt01")
public class VT010001Controller extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  
  
	@Autowired
	private VT010001Service vt010001Service;

	@Autowired
	Notification notification;

	@Autowired
  private Properties configProperty;
	
	
	/**
	 * register in_out_record
	 * 
	 * @param requestParam VT010001EntityRequestParam and Principal principal
	 * @return ResponseEntity<VT010001EntityRpRegister>
	 */
	@RequestMapping(value = "/vt010001/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VT010001EntityRpRegister> api010001_01(@RequestBody VT010001EntityRqRegister requestParam,
			Principal principal) {

		VT010001EntityRpRegister reponse = new VT010001EntityRpRegister();
		reponse.setStatus(Constant.RESPONSE_STATUS_ERROR);
		reponse.setData(null);

		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = vt010001Service.inOutregister(requestParam, principal);
		  }
		} catch (DuplicateKeyException e) {
			reponse.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return new ResponseEntity<VT010001EntityRpRegister>(reponse, HttpStatus.OK);
	}

	/**
	 *Get employee
	 * @param employee
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/vt010001/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findEmployee(@RequestBody Employee employee) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, employee.getSecurityUsername(), employee.getSecurityPassword())) {
		    reb = vt010001Service.findEmployee(employee);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * Get system code theo master class, code name => de lay destination, ly do ra ngoai (reason)
	 * @param des
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/vt010001/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getSystemCodeByParams(@RequestBody VT010001SystemCode des) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, des.getSecurityUsername(), des.getSecurityPassword())) {
		    reb = vt010001Service.getSystemCodeByParams(des);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * Get employee theo user name, quyen
	 * @param employee
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/vt010001/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findEmployeeByUserName(@RequestBody Employee employee) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, employee.getSecurityUsername(), employee.getSecurityPassword())) {
		    reb = vt010001Service.findEmployeeByUserName(employee);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

}