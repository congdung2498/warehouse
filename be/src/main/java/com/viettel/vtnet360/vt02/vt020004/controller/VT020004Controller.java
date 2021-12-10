package com.viettel.vtnet360.vt02.vt020004.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004Dish;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindDishByChefID;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindDishInMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToInsertMenu;
import com.viettel.vtnet360.vt02.vt020004.service.VT020004Service;

/**
 * Class controller for screen VT020004 : setting menu
 * 
 * @author DuyNK 12/09/2018
 */
@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_Bep_truong') or hasAuthority('PMQT_NV')")
@RestController
@RequestMapping("/com/viettel/vtnet360/vt02/vt020004")
public class VT020004Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT020004Service vt020004Service;
	
	@Autowired
  private Properties configProperty;

	/**
	 * Select list menu each day from ... to ...
	 * 
	 * @param info
	 * @param principal
	 * @return ResponseEntityBase(status, List<VT020004Menu>)
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020004FindLishMenu(@RequestBody VT020004InfoToFindMenu info,
			Principal principal) {
		logger.info("*********** vt020004_01 get list menu each day start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
	      OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = vt020004Service.findLishMenu(info, userName, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020004_01 get list get list menu each day end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Select list dish by chefID(MENU_SETTING.CHEF_ID = QLDV_EMPLOYEE.USER_NAME)
	 * 
	 * @param info
	 * @param principal
	 * @return ResponseEntityBase<List<VT020004Dish>>
	 */
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020004FindListDishByChefID(
			@RequestBody VT020004InfoToFindDishByChefID info, Principal principal) {
		logger.info("*********** vt020004_02 get list dish by chef ID start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
	      OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      info.setChefID(userName);
	      resp = vt020004Service.findListDishByChefID(info, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020004_02 get list dish by chef ID end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * check menu in this day existed in database or not (0:not 1:exist -1:check
	 * error)
	 * 
	 * @param info
	 * @param principal
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020004CheckMenuExist(@RequestBody VT020004InfoToFindDishInMenu info,
			Principal principal) {
		logger.info("*********** vt020004_03 check menu existed start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
	      OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = vt020004Service.checkMenuExist(info, userName, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020004_03 check menu existed end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * insert new reocrd to MENU_SETTING ( if date existed => delete all record of
	 * this date => insert new data)
	 * 
	 * @param info
	 * @param principal
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020004InsertMenu(@RequestBody VT020004InfoToInsertMenu info, Principal principal) {
		logger.info("*********** vt020004_04 insert menu start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = vt020004Service.insertMenu(info, userName, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020004_04 insert menu end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> vt020004FindListDishByKitchen(
      @RequestBody VT020004InfoToFindDishByChefID info, Principal principal) {
    logger.info("*********** vt020004_05 get list dish by kitchen start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
        List<VT020004Dish> dish = vt020004Service.findListDishByKitchen(info);
        resp.setData(dish);
        resp.setStatus(Constant.SUCCESS);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    logger.info("*********** vt020004_05 get list dish by kitchen end***********");
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
}
