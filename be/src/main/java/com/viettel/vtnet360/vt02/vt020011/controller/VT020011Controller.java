package com.viettel.vtnet360.vt02.vt020011.controller;

import java.security.Principal;
import java.util.List;
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

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.kitchen.service.DayOffSettingWrapper;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020011.entity.VT020011DayOffSettingEntity;
import com.viettel.vtnet360.vt02.vt020011.service.VT020011Service;

/**
 * Class controller for screen VT020011 : setting day off
 * 
 * @author DuyNK 05/09/2018
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt02/vt020011")
public class VT020011Controller extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT020011Service vt020011service;
	
	@Autowired
  private Properties configProperty;
	
	/**
	 * Select all day off from DAY_OFF_SETTING
	 * 
	 * @param dayOff
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_Bep_truong')")
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020011GetDayOff(@RequestBody VT020011DayOffSettingEntity dayOff) {
		logger.info("*********** vt020011_01 getDayOff start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, dayOff.getSecurityUsername(), dayOff.getSecurityPassword())) {
		    resp = vt020011service.getDayOff(dayOff);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020011_01 getDayOff end***********");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	/**
	 * Insert day off to DAY_OFF_SETTING
	 * 
	 * @param dayOff
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_Bep_truong')")
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020011InsertDayOff(@RequestBody VT020011DayOffSettingEntity dayOff,
			Principal principal) {
		logger.info("*********** vt020011_02 insert day off start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, dayOff.getSecurityUsername(), dayOff.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      dayOff.setCreateUser(userName);
	      resp = vt020011service.insertDayOff(dayOff);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020011_02 insert day off end***********");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	/**
	 * Update day off to DAY_OFF_SETTING
	 * 
	 * @param dayOff
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_Bep_truong')")
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020011UpdateDayOff(@RequestBody VT020011DayOffSettingEntity dayOff,
			Principal principal) {
		logger.info("*********** vt020011_03 update day off start***********");
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		dayOff.setUpdateUser(userName);
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, dayOff.getSecurityUsername(), dayOff.getSecurityPassword())) {
		    resp = vt020011service.updateDayOff(dayOff);
	    }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt02001_03 update day off end***********");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	/**
	 * count total record dayoff
	 * 
	 * @author ThangBT
	 * @param dayOff
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_Bep_truong')")
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020011CountTotalDayOff(
			@RequestBody VT020011DayOffSettingEntity dayOff) {
		logger.info("*********** vt020011_04 countTotalDayOff start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, dayOff.getSecurityUsername(), dayOff.getSecurityPassword())) {
		    resp = vt020011service.countTotalDayOff(dayOff);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020011_04 countTotalDayOff end***********");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	/**
	 * copy all dayoff of this year to (this year + 1)
	 * 
	 * @author ThangBT
	 * @param listDayOff
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_Bep_truong')")
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020011CopyDayOff(
			@RequestBody DayOffSettingWrapper wrapper, Principal principal) {
		logger.info("*********** vt020011_05 copy day off start ***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, wrapper.getSecurityUsername(), wrapper.getSecurityPassword())) {
		    List<VT020011DayOffSettingEntity> listDayOff = wrapper.getListDayOff();;
	      OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      for (int i = 0; i < listDayOff.size(); i++) {
	        listDayOff.get(i).setCreateUser(userName);
	      }
	      resp = vt020011service.copyDayOff(listDayOff);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020011_05 copy day off end ***********");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	/**
	 * find all year to filter
	 * 
	 * @author ThangBT
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_Bep_truong')")
	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020011FindAllYears(@RequestBody BaseEntity info) {
		logger.info("*********** vt020011_06 get all years start ***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt020011service.findAllYears();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020011_06 get all years end ***********");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
