package com.viettel.vtnet360.vt02.vt020006.controller;

import java.security.Principal;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000RequestParam;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006InfoToGetListLunchDate;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006Lunch;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006ParamRequest;
import com.viettel.vtnet360.vt02.vt020006.service.VT020006Service;

/**
 * Class controller for screen VT020006 : set lunch time
 * 
 * @author DuyNK 17/09/2018
 */
@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP') "
    + "or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_Bep_truong')")
@RestController
@RequestMapping("/com/viettel/vtnet360/vt02/vt020006")
public class VT020006Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT020006Service vt020006Service;

	@Autowired
  private Properties configProperty;
	
	/**
	 * Select days of week periodic and list date of lunch
	 * 
	 * @param info
	 * @param principal
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020006GetInfoLunchDate(@RequestBody VT020006InfoToGetListLunchDate info,
			Principal principal) {
		logger.info("*********** vt020006_01 get info lunch date start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
	      OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      info.setUserName(userName);
	      resp = vt020006Service.getInfoLunchDate(info);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020006_01 get info lunch date end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Select info of one date to update
	 * 
	 * @param lunch
	 * @param principal
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020006GetInfoOneLunch(@RequestBody VT020006Lunch lunch,
			Principal principal) {
		logger.info("*********** vt020006_02 get info of 1 lunch start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, lunch.getSecurityUsername(), lunch.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      lunch.setUserName(userName);
	      resp = vt020006Service.getInfoOneLunch(lunch);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020006_02 get info of 1 lunch end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Select info of menu in a date in a kitchenID
	 * 
	 * @param lunch
	 * @return ResponseEntityBase(status, listDishName)
	 */
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020006GetInfoMenu(@RequestBody VT020006Lunch lunch) {
		logger.info("*********** vt020006_03 get info of 1 menu in a date in a kitchenID start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, lunch.getSecurityUsername(), lunch.getSecurityPassword())) {
		    resp = vt020006Service.getInfoMenu(lunch);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020006_03 get info of 1 menu in a date in a kitchenID end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * insert && delete list lunchdate && update or insert a new lunch date
	 * 
	 * @param param
	 * @param principal
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020006SettingLunch(
			@RequestBody VT000000RequestParam<VT020006ParamRequest> param, Principal principal) {
		logger.info("*********** vt020006_04 setting lunch start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			VT020006ParamRequest paramData = param.getData();
			if (paramData == null) {
				paramData = new VT020006ParamRequest();
			}
			
			ObjectMapper mapper = new ObjectMapper();
      System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(param));
			
			if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
			  OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      paramData.setUserName(userName);
	      if (param.getAction() == Constant.REQUEST_ACTION_INSERT) {
	        resp = vt020006Service.insertLunch(paramData);
	      } else if (param.getAction() == Constant.REQUEST_ACTION_UPDATE) {
	        resp = vt020006Service.updateLunch(paramData);
	      } else if (param.getAction() == Constant.REQUEST_ACTION_DELETE) {
	        resp = vt020006Service.deleteLunch(paramData);
	      } else {
	        resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	      }
			}
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020006_04 setting lunch end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	
	
	/**
	 * 
	 * @param info
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/getTotalLunchDate", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getTotalLunchDate(@RequestBody VT020006InfoToGetListLunchDate info,
			Principal principal) {
		logger.info("*********** get total lunch date ***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      info.setUserName(userName);
	      resp = vt020006Service.getTotalLunchDate(info);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
}
