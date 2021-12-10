package com.viettel.vtnet360.vt01.vt010000.controller;

import java.security.Principal;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityCard;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRpGetData;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRpGetRecord;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqGetData;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqGetRecord;
import com.viettel.vtnet360.vt01.vt010000.service.VT010000Service;

import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRpMgAp;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqMgAp;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqUpdateRecord;

/**
 * Class controller for VT010005,VT010006,VT010007
 * 
 * @author KienHT 09/08/2018
 * 
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt01")
public class VT010000Controller extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  
  
	@Autowired
	private VT010000Service vt010000Service;

	@Autowired
  private Properties configProperty;
	
	/**
	 * get list entity common for 3 sceen VT010005,VT010006,VT010007
	 * 
	 * @param VT010000EntityRqGetData 
	 * @param Principal
	 * @return ResponseEntity<VT010000EntityReponseGetList>
	 */
	@RequestMapping(value = "/vt010000/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VT010000EntityRpGetData> api010000_01(@RequestBody VT010000EntityRqGetData requestParam,
			Principal principal,OAuth2Authentication authentication) {
		VT010000EntityRpGetData reponse = new VT010000EntityRpGetData();
		reponse.setStatus(Constant.RESPONSE_STATUS_ERROR);
		reponse.setData(null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = vt010000Service.findListInOut(requestParam, principal,authentication);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<VT010000EntityRpGetData>(reponse, HttpStatus.OK);
	}

	/**
	 * find in List InOut to get Record
	 * 
	 * @param VT010000EntityRqGetRecord 
	 * @param Principal
	 * @return ResponseEntity<VT010000EntityRpGetRecord>
	 */
	@RequestMapping(value = "/vt010000/02", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VT010000EntityRpGetRecord> api010000_02(@RequestBody VT010000EntityRqGetRecord requestParam,
			Principal principal) {
		VT010000EntityRpGetRecord reponse = new VT010000EntityRpGetRecord();
		reponse.setStatus(Constant.RESPONSE_STATUS_ERROR);
		reponse.setData(null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = vt010000Service.findListInOutGetRecord(requestParam, principal);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<VT010000EntityRpGetRecord>(reponse, HttpStatus.OK);
	}

	/**
	 * inOut Manager Approve
	 * 
	 * @param VT010000EntityRqMgAp
	 * @param Principal
	 * @return ResponseEntity<VT010000EntityRpMgAp>
	 */
	@PostMapping(value = "/vt010000/03", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VT010000EntityRpMgAp> api010000_03(@RequestBody VT010000EntityRqMgAp requestParam, Principal principal) {
		VT010000EntityRpMgAp reponse = new VT010000EntityRpMgAp();
		reponse.setStatus(Constant.RESPONSE_STATUS_ERROR);
		reponse.setData(null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    OAuth2Authentication authentication = (OAuth2Authentication) principal;
	      reponse = vt010000Service.inOutManagerApprove(requestParam, principal, authentication);
	      reponse.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<VT010000EntityRpMgAp>(reponse, HttpStatus.OK);
	}

	/**
	 * update Record
	 * 
	 * @param requestParam VT010000EntityRqUpdateRecord
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/vt010000/04", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> api010000_04(@RequestBody VT010000EntityRqUpdateRecord requestParam) {
		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = vt010000Service.updateRecord(requestParam);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reponse, HttpStatus.OK);
	}

	/**
	 * idcard used
	 * 
	 * @param VT010000EntityCard
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/vt010000/05", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> api01000005(@RequestBody VT010000EntityCard requestParam) {

		// set reponse
		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = vt010000Service.idCard(requestParam);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reponse, HttpStatus.OK);
	}

}
