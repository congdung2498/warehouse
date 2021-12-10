package com.viettel.vtnet360.vt01.vt010002.controller;

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
import com.viettel.vtnet360.vt01.vt010002.entity.VT010002EntityRpUpdate;
import com.viettel.vtnet360.vt01.vt010002.entity.VT010002EntityRqUpdate;
import com.viettel.vtnet360.vt01.vt010002.service.VT010002Service;

/**
 * Class controller VT010002
 * 
 * @author KienHT 09/08/2018
 * 
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt01")
public class VT010002Controller extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  
  
	@Autowired
	private VT010002Service vt010002Service;

	@Autowired
  private Properties configProperty;
	
	/**
	 * register in_out_record
	 * 
	 * @param requestParam VT010002EntityRqUpdate
	 * @param Principal
	 * @return ResponseEntity<VT010002EntityRpUpdate>
	 */
	@RequestMapping(value = "/vt010002/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VT010002EntityRpUpdate> api010002_01(@RequestBody VT010002EntityRqUpdate requestParam,
			Principal principal) {
		VT010002EntityRpUpdate reponse = new VT010002EntityRpUpdate();
		reponse.setStatus(Constant.RESPONSE_STATUS_ERROR);
		reponse.setData(null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = vt010002Service.inOutRegisterUpdate(requestParam, principal);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<VT010002EntityRpUpdate>(reponse, HttpStatus.OK);
	}
}
