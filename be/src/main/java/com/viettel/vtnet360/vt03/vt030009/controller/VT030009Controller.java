package com.viettel.vtnet360.vt03.vt030009.controller;

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
import com.viettel.vtnet360.vt03.vt030009.entity.VT030009ResponseCarRoute;
import com.viettel.vtnet360.vt03.vt030009.entity.VT030009UpdateProcessCarRoute;
import com.viettel.vtnet360.vt03.vt030009.service.VT030009Service;

/**
 * Class VT030009Controller
 * 
 * @author CuongHD
 *
 */

@RestController
@RequestMapping(value="/com/viettel/vtnet360/vt03/vt030009")
public class VT030009Controller extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  
	@Autowired 
	private VT030009Service vt030009Service;
	
	@Autowired
  private Properties configProperty;
	
	/**
	 * Update process for car's route
	 * 
	 * @param VT030009UpdateProcessCarRoute obj
	 * @param Principal principal
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	@RequestMapping(value="/01", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase api03000901(@RequestBody VT030009UpdateProcessCarRoute obj, Principal principal) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		Collection<GrantedAuthority> listRole = null;
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      obj.setUserName(userName);
	      listRole = oauth.getAuthorities();
	      response = vt030009Service.updateCarRoute(obj, listRole);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	
	/**
	 * Get information of route
	 * 
	 * @param VT030009UpdateProcessCarRoute obj
	 * @param Principal principal
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	@RequestMapping(value="/02", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase api03000902(@RequestBody VT030009UpdateProcessCarRoute obj, Principal principal ) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		Collection<GrantedAuthority> listRole = null;
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      obj.setUserDriver(userName);
	      listRole = oauth.getAuthorities();
	      response = vt030009Service.findCarRoute(obj, listRole);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	
	/**
	 * Tim kiem tat ca yeu cau da duoc xep xe, den vi tri, hoan thanh
	 * 
	 * @param VT030009ResponseCarRoute obj
	 * @param Principal principal
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	@RequestMapping(value="/03", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase api03000903(@RequestBody VT030009ResponseCarRoute obj, Principal principal ){
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		Collection<GrantedAuthority> listRole = null;
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      obj.setUserName(userName);
	      int pageNumber = obj.getPageNumber() * obj.getPageSize();
	      obj.setPageNumber(pageNumber);
	      listRole = oauth.getAuthorities();
	      response = vt030009Service.getListRequest(obj, listRole);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
}
