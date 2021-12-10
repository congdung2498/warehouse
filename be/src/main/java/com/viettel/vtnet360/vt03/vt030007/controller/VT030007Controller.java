package com.viettel.vtnet360.vt03.vt030007.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.TokenAdditionInfo;
import com.viettel.vtnet360.vt03.vt030007.entity.VT030007ResquestFindBookCar;
import com.viettel.vtnet360.vt03.vt030007.service.VT030007Service;

@RestController
@RequestMapping(value="/com/viettel/vtnet360/vt03/vt030007")
public class VT030007Controller extends BaseController {
	
	@Autowired 
	private VT030007Service vt030007Service;
	
	@Autowired
	private AuthorizationServerTokenServices tokenServices;
	
	@Autowired
  private Properties configProperty;
	
	/**
	 * Get list bookcar's request
	 * 
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	@RequestMapping(value="/01", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase api03000701(@RequestBody VT030007ResquestFindBookCar obj, Principal principal, OAuth2Authentication authentication) throws Exception {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userManager = (String) oauth.getPrincipal();
	      obj.setUserManager(userManager);  
	      Map<String, Object> additionalInfo = tokenServices.getAccessToken(authentication).getAdditionalInformation();
	      TokenAdditionInfo tokenInfo = (TokenAdditionInfo) additionalInfo.get("userInfor");
	      obj.setUnitUser(tokenInfo.getUnitId());
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      response = vt030007Service.findBookCarRequest(obj, roleList);
		  }
		} catch (Exception e) {
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value="/findBookCarRequestOrder", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase findBookCarRequestOrder(@RequestBody VT030007ResquestFindBookCar obj, Principal principal, OAuth2Authentication authentication) throws Exception{
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userManager = (String) oauth.getPrincipal();
	      obj.setUserManager(userManager);  
	      Map<String, Object> additionalInfo = tokenServices.getAccessToken(authentication).getAdditionalInformation();
	      TokenAdditionInfo tokenInfo = (TokenAdditionInfo) additionalInfo.get("userInfor");
	      obj.setUnitUser(tokenInfo.getUnitId());
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
//	      int pageNumber = obj.getPageNumber() * obj.getPageSize();
//	      obj.setPageNumber(pageNumber);
	      response = vt030007Service.findBookCarRequestOrder(obj, roleList);
		  }
		} catch (Exception e) {
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value="/book-car-list", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase findBookCarRequestList(@RequestBody VT030007ResquestFindBookCar obj, Principal principal) throws Exception{
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userManager = (String) oauth.getPrincipal();
	      obj.setUserManager(userManager);  
	      response = vt030007Service.findBookCarList(obj);
		  }
		} catch (Exception e) {
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value="/book-car-list-approve", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase findBookCarRequestList(@RequestBody VT030007ResquestFindBookCar obj, Principal principal, OAuth2Authentication authentication) throws Exception{
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userManager = (String) oauth.getPrincipal();
	      obj.setUserManager(userManager);  
	      Map<String, Object> additionalInfo = tokenServices.getAccessToken(authentication).getAdditionalInformation();
	      TokenAdditionInfo tokenInfo = (TokenAdditionInfo) additionalInfo.get("userInfor");
	      obj.setUnitUser(tokenInfo.getUnitId());
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      response = vt030007Service.findBookCarListApprove(obj, roleList);
		  }
		} catch (Exception e) {
			throw e;
		}
		return response;
	}
}
