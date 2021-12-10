package com.viettel.vtnet360.vt03.vt030006.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030006.entity.VT030006UpdateBookCar;
import com.viettel.vtnet360.vt03.vt030006.service.VT030006Service;

/**
 * Class VT030006Controller
 * 
 * @author CuongHD
 *
 */

@RestController
@RequestMapping("/com/viettel/vtnet360/vt03/vt030006")
public class VT030006Controller extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  
	@Autowired
	private VT030006Service vt030006Service;
	
	@Autowired
  private Properties configProperty;
	
	/**
	 * Approve Or Refuse resquest book car
	 * 
	 * @param VT030006UpdateBookCar obj
	 * @param  Principal principal
	 * @return VT030006ResponseApproveBookCar
	 * @throws Exception
	 */
	@RequestMapping(value="/01", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase api03000601(@RequestBody VT030006UpdateBookCar obj, Principal principal) throws Exception{
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		Collection<GrantedAuthority> roleList = null;
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      obj.setUserName(userName);
	      roleList = oauth.getAuthorities();
	      response = vt030006Service.updateRequest(obj, roleList);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	
}
