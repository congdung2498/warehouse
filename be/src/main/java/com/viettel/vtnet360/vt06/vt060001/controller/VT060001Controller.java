package com.viettel.vtnet360.vt06.vt060001.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.AuthenticationProviderImpl;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt06.vt060001.entity.SearchCondition;
import com.viettel.vtnet360.vt06.vt060001.entity.VersionDetail;
import com.viettel.vtnet360.vt06.vt060001.service.VT060001Service;

@PreAuthorize("hasAuthority('ADVANCE_SETTING') and hasAuthority('VERSION_SETTING')")
@RestController
@RequestMapping("/com/viettel/vtnet360/vt06/vt060001")
public class VT060001Controller {
	
	@Autowired
	VT060001Service vt060001Service;
	
	@Autowired
	AuthenticationProviderImpl authenticationProviderImpl;
	
	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getMobileVersionDetail(@RequestBody SearchCondition searchCondition) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		try {
			entityBase.setData(vt060001Service.getListVersionDetail(searchCondition));
			entityBase.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}
	
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase updateMobileVersionDetail(@RequestBody VersionDetail versionDetail, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		
		/** Get userName */
		String userName = (String) oauth.getPrincipal();
		
		try {
			versionDetail.setCreateUser(userName);
			vt060001Service.updateVersionDetail(versionDetail);
			entityBase.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}
	
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase insertMobileVersionDetail(@RequestBody VersionDetail versionDetail, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		
		/** Get userName */
		String userName = (String) oauth.getPrincipal();
		
		try {
			versionDetail.setCreateUser(userName);
			if (vt060001Service.insertVersionDetail(versionDetail)) {
				entityBase.setStatus(Constant.SUCCESS);
			} else
				entityBase.setStatus(2);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}
	
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase deleteMobileVersionDetail(@RequestBody SearchCondition versionDetail, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		
		try {
			versionDetail.setStatus(3);
			if (vt060001Service.deleteVersionDetail(versionDetail)) {
				entityBase.setStatus(Constant.SUCCESS);
			} else
				entityBase.setStatus(Constant.ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}
	
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getAppLink(@RequestBody Map<String, String> param, Principal principal) throws Exception {

		ResponseEntityBase reb = new ResponseEntityBase(0, null);
		
		logger.info("*********** vt000000_11 getLinkAPP START ***********");
		
		try {
			Map<String, Object> output = new HashMap<>();
			VersionDetail versionDetail = vt060001Service.getVersionDetail(param.get("device_type"));
			output.put("version", versionDetail.getVersion());
			output.put("linkApp", versionDetail.getVersionLink());
			reb.setData(output);
			reb.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("*********** vt000000_11 getLinkAPP END - ERROR ***********");
			reb.setStatus(0);
		}
		return reb;
	}
	
	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase updateUserVersionForMonitor(@RequestBody Map<String, String> param, Principal principal) throws Exception {
		
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		
		/** Get userName */
		String userName = (String) oauth.getPrincipal();
		
		ResponseEntityBase reb = new ResponseEntityBase(0, null);
		
		logger.info("*********** vt000000_12 updateUserVersionForMonitor START ***********");
		
		try {
			vt060001Service.setUserVersionDetail(param.get("device_type"), param.get("deviceType"), userName);
			reb.setStatus(1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("*********** vt000000_11 updateUserVersionForMonitor END - ERROR ***********");
			reb.setStatus(0);
		}
		logger.info("*********** vt000000_11 updateUserVersionForMonitor END ***********");
		return reb;
	}
	@RequestMapping(value = "/deleteAllSession", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteAllSession() {
		authenticationProviderImpl.deleteAllSession();
		
	}
}
