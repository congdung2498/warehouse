package com.viettel.vtnet360.vt03.vt030001.controller;
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
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityEmployee;
import com.viettel.vtnet360.vt03.vt030001.service.VT030001Service;

/**
 * 
 * @author CuongHD 08/09/2018
 *
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt03/vt030001")
public class VT030001Controller extends BaseController {
	
	@Autowired 
	private VT030001Service vt030001Service;
	
	@Autowired
  private Properties configProperty;
	

	private final Logger logger = Logger.getLogger(this.getClass());	
	/**
	 * tim kiem truong ban xe theo vi tri va ten
	 * 
	 * @param int placeId
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value="/02", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase api03000102(@RequestBody VT030000EntityEmployee obj) throws Exception{	
		ResponseEntityBase response = null;
		try{
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    int pageNumber = obj.getPageNumber() * obj.getPageSize();
	      obj.setPageNumber(pageNumber);
	      response = vt030001Service.findDriver(obj);
		  }
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}	
		return response;	
	}
	
	/**
	 * Find Drive Squad objects according to it's information that end user want searching  
	 * 
	 * @param VT030000EntityDriveSquad obj
	 * @return List<DriveSquad>
	 */
	@RequestMapping(value="/03", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase findDriveSquads(@RequestBody VT030000EntityDriveSquad obj) throws Exception{	
		ResponseEntityBase response = null;
		try{
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    response = vt030001Service.findSquad(obj);
		  }
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}	
		return response;	
	}
	
	/**
	 * Insert a Drive Squad if squadName not exist in database 
	 * Otherwise update this Drive Squad 
	 * 
	 * @param VT030000EntityDriveSquad object
	 * @param Principal principal
	 * @return APIVT030001Message
	 */
	@RequestMapping(value="/04", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase insertDriveSquad(@RequestBody VT030000EntityDriveSquad object, Principal principal) throws Exception{
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try{
		  if(isValidated(configProperty, object.getSecurityUsername(), object.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      object.setUserCreate(userName);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      response = vt030001Service.insertSquad(object, roleList);
		  }
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
		}
		
		
		return response;
	}
	
	/**
	 * 
	 * @param object
	 * @param principal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/05", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase deleteSquad(@RequestBody VT030000EntityDriveSquad object, Principal principal) throws Exception{
		ResponseEntityBase response = null;
		try{
		  if(isValidated(configProperty, object.getSecurityUsername(), object.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      object.setUserCreate(userName);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      response = vt030001Service.deleteSquad(object, roleList);
		  }
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	
	

}
