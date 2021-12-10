package com.viettel.vtnet360.vt07.vt070001.controller;

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

import com.viettel.vtnet360.common.security.AuthenticationProviderImpl;
import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeDetail;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodePrefix;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeRange;
import com.viettel.vtnet360.vt07.vt070001.service.VT070001Service;

@RestController
@RequestMapping("/com/viettel/vtnet360/vt07/vt070001")
public class VT070001Controller  extends BaseController{
	@Autowired
	AuthenticationProviderImpl authenticationProviderImpl;

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	VT070001Service vt070001Service;

	@Autowired
	private Properties configProperty;


	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase findPlace(@RequestBody VT070001EntityBarcodePrefix obj, Principal principal) throws Exception {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
				response = vt070001Service.findTypeBarcode(obj, userRoles);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	@RequestMapping(value="/03", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase insertBarcodeRange(@RequestBody VT070001EntityBarcodeRange object, Principal principal) throws Exception{
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try{
			if(isValidated(configProperty, object.getSecurityUsername(), object.getSecurityPassword())) {
				OAuth2Authentication oauth = (OAuth2Authentication) principal;
				String userName = ((String) oauth.getPrincipal()).toLowerCase();
				Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
				object.setCreateUser(userName);
				response = vt070001Service.insertBarcodeRange(object, userRoles);
			}
		}catch(Exception e){

			logger.error(e.getMessage(), e);
		}


		return response;
	}

	@RequestMapping(value="/04", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase findDriveSquads(@RequestBody VT070001EntityBarcodeRange obj, Principal principal) throws Exception{	
		ResponseEntityBase response = null;
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try{
			if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
				response = vt070001Service.findBarcodeRange(obj, userRoles);
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}	
		return response;	
	}

	@RequestMapping(value="/05", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase findBarcodeDetail(@RequestBody VT070001EntityBarcodeRange obj, Principal principal) throws Exception{	
		ResponseEntityBase response = null;
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try{
			if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
				response = vt070001Service.findBarcodeDetail(obj, userRoles);
			}


		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}	
		return response;	
	}

	@RequestMapping(value="/06", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase changeBarcodeRangeDetail(@RequestBody VT070001EntityBarcodeRange obj, Principal principal) throws Exception{	
		ResponseEntityBase response = null;
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try{
			if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
				response = vt070001Service.changeBarcodeRangeDetail(obj, userRoles);
			}


		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}	
		return response;	
	}

	@RequestMapping(value="/07", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase checkChangeBarcodeRange(@RequestBody VT070001EntityBarcodeRange obj, Principal principal) throws Exception{	
		ResponseEntityBase response = null;
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try{
			if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
				response = vt070001Service.checkChangeBarcodeRange(obj, userRoles);
			}


		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}	
		return response;	
	}

	@RequestMapping(value="/08", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase checkBarcodeAvailable(@RequestBody VT070001EntityBarcodeDetail obj, Principal principal) throws Exception{	
		ResponseEntityBase response = null;
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try{
			if(isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
				response = vt070001Service.checkBarcodeAvailable(obj, userRoles);
			}

		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}	
		return response;	
	}
}
