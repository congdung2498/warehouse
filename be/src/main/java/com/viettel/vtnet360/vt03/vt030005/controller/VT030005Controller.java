package com.viettel.vtnet360.vt03.vt030005.controller;


import java.security.Principal;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityBookCarResult;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityPlace;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000ResponseBookCarRequest;
import com.viettel.vtnet360.vt03.vt030005.service.VT030005Service;

/**
 * Class controller cfor VT030005_Bookcar's request
 * 
 * @author CuongHD 11/09/2018
 * 
 */

@RestController
@RequestMapping("/com/viettel/vtnet360/vt03/vt030005")
public class VT030005Controller extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  
	@Autowired 
	private VT030005Service vt030005Service;
	
	@Autowired
  private Properties configProperty;
	
	/**
	 * Get place start and place target
	 * 
	 * @param VT030000EntityPlace obj
	 * @return VT030005ResponsePlace
	 * @throws Exception
	 */
	@RequestMapping(value="/01", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase apiVt03000501(@RequestBody VT030000EntityPlace obj) throws Exception{
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    response = vt030005Service.findPlaceStart(obj);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	
	/**
	 * Insert new reqquest book car
	 * 
	 * @param VT030000EntityBookCar obj
	 * @param Principal principal
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	@RequestMapping(value="/03", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase apiVt03000503(@RequestBody VT030000ResponseBookCarRequest obj, Principal principal) throws Exception{
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      obj.setUserName(userName);
	      response = vt030005Service.insertRequest(obj);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}	
	@RequestMapping(value="/04", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase apiVt03000504(@RequestBody VT030000EntityBookCarResult obj, Principal principal) throws Exception{
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      obj.setUserName(userName);
	      response = vt030005Service.insertRequestResult(obj);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}	
}
