package com.viettel.vtnet360.vt03.vt030008.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030008.entity.VT030008DriveCarInfo;
import com.viettel.vtnet360.vt03.vt030008.entity.VT030008RequestMatchCar;
import com.viettel.vtnet360.vt03.vt030008.entity.VT030008ResponseCars;
import com.viettel.vtnet360.vt03.vt030008.service.VT030008Service;

/**
 * Class VT030008Controller
 * 
 * @author CuongHD
 *
 */
@RestController
@RequestMapping(value="/com/viettel/vtnet360/vt03/vt030008")
public class VT030008Controller extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  
	@Autowired
	private VT030008Service vt030008Service;
	
	@Autowired
  private Properties configProperty;
	
	/**
	 * Find Driver freedom
	 * 
	 * @param VT030008RequestMatchCar obj
	 * @param Principal principal
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	@RequestMapping(value="/01", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase api03000801(@RequestBody VT030008RequestMatchCar obj, Principal principal){
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth  = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      obj.setUserAssigner(userName);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      int pageNumber = obj.getPageNumber() * obj.getPageSize();
	      obj.setPageNumber(pageNumber);
	      if(roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))){
	        obj.setRole(Constant.PMQT_ROLE_ADMIN);
	      }else if(roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_DOIXE))){
	        obj.setRole(Constant.PMQT_ROLE_MANAGER_DOIXE);
	      }
	      response = vt030008Service.findFreeDriving(obj);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	
	/**
	 * Take the car not working corresponding to the driver 
	 * 
	 * @param String username
	 * @param Principal principal
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	@RequestMapping(value="/02", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase api03000802(@RequestBody VT030008ResponseCars obj, Principal principal){
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth  = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      int pageNumber = obj.getPageNumber() * obj.getPageSize();
	      obj.setPageNumber(pageNumber);
	      obj.setUserAssigner(userName);
	      response = vt030008Service.findCarMatchDriver(obj);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	
	
	/**
	 * update carId, userDriver, userAssigner in CAR_BOOKING
	 * 
	 * @param VT030008DriveCarInfo obj
	 * @param Principal principal
	 * @return ResponseEntityBase 
	 */
	@RequestMapping(value="/03", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase api03000803(@RequestBody VT030008DriveCarInfo obj,  Principal principal) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userManager = (String) oauth.getPrincipal();
	      obj.setUserAssigner(userManager);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      response = vt030008Service.matchCarAndDrive(obj, roleList);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	
	/**
	 * Cancel matching car
	 * 
	 * @param VT030008DriveCarInfo obj
	 * @param Principal principal 
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value="/04", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase api03000804(@RequestBody VT030008DriveCarInfo obj,  Principal principal) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userManager = (String) oauth.getPrincipal();
	      obj.setUserAssigner(userManager);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      response = vt030008Service.cancelMatching(obj, roleList);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
}
