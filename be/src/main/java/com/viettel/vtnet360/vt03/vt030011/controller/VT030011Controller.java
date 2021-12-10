package com.viettel.vtnet360.vt03.vt030011.controller;

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
import com.viettel.vtnet360.vt03.vt030011.entity.VT030011CancelRequest;
import com.viettel.vtnet360.vt03.vt030011.service.VT030011Service;
/**
 * Class VT030011Controller
 * 
 * @author CuongHD
 *
 */
@RestController
@RequestMapping(value="/com/viettel/vtnet360/vt03/vt030011")
public class VT030011Controller extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  
	@Autowired
	private VT030011Service vt030011Service;
	
	@Autowired
  private Properties configProperty;
	
	/**
	 * Cancel resquest
	 * 
	 * @param VT030011CancelRequest obj
	 * @param Principal principal
	 * @return ResponseEntity
	 * @throws Exception
	 */
	@RequestMapping(value="/01", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase api03001101(@RequestBody VT030011CancelRequest obj, Principal principal) throws Exception{
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String username = (String) oauth.getPrincipal();
	      obj.setUserName(username);
	      response = vt030011Service.updateRequest(obj);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
}
