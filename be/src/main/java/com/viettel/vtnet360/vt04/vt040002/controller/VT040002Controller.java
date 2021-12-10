package com.viettel.vtnet360.vt04.vt040002.controller;

import java.security.Principal;
import java.util.Collection;
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

import com.viettel.vtnet360.checking.service.Condition;
import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.issuesService.service.IssuesServiceService;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000RequestParam;
import com.viettel.vtnet360.vt04.vt040002.entity.VT040002HistoryService;
import com.viettel.vtnet360.vt04.vt040002.entity.VT040002Stationery;
import com.viettel.vtnet360.vt04.vt040002.service.VT040002Service;

/**
 * class controller of VT040002
 * 
 * @author ThangBT 18/10/2018
 *
 */

@RestController
@RequestMapping("/com/viettel/vtnet360/vt04/vt040002")
public class VT040002Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT040002Service vt040002Service;
	
	@Autowired
	private IssuesServiceService issuesServiceService;
	
	@Autowired
  private Properties configProperty;

	/**
	 * get list stationery name and id
	 * 
	 * @param stationeryInfo
	 * @return VT040002Stationery
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListStationery(@RequestBody VT040002Stationery stationeryInfo) throws Exception {

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, stationeryInfo.getSecurityUsername(), stationeryInfo.getSecurityPassword())) {
		    reb = vt040002Service.findListStationery(stationeryInfo);
		  }
		} catch (Exception e) {
			reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * @param masterClass
	 * @return list stationery type or unit
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListStationeryTypeOrUnit(@RequestBody Condition condition) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);;
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    reb = vt040002Service.findListStationeryTypeOrUnit(condition.getSearch());
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get list stationery report
	 * 
	 * @param stationeryInfo
	 * @param principal
	 * @return VT040002Stationery
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findStationeryReport(@RequestBody VT040002Stationery stationeryInfo, Principal principal) {
	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
		  if(isValidated(configProperty, stationeryInfo.getSecurityUsername(), stationeryInfo.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = vt040002Service.findStationeryReport(stationeryInfo, listRole);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * count total record of stationery
	 * 
	 * @param stationeryInfo
	 * @param principal
	 * @return integer
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase countTotalRecord(@RequestBody VT040002Stationery stationeryInfo, Principal principal) {

	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, stationeryInfo.getSecurityUsername(), stationeryInfo.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = vt040002Service.countTotalRecord(stationeryInfo, listRole);
		  }
		} catch (Exception e) {
			reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * status after doing action
	 * 
	 * @param param
	 * @param principal
	 * @return integer
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase handleSetUpStationery(@RequestBody VT000000RequestParam<VT040002Stationery> param, Principal principal) {
	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			String loginUserName = (String) oauth.getPrincipal();
			VT040002Stationery stationeryInfo = param.getData();
			stationeryInfo.setLoginUserName(loginUserName);
			Collection<GrantedAuthority> listRole = oauth.getAuthorities();
			
			if(isValidated(configProperty, stationeryInfo.getSecurityUsername(), stationeryInfo.getSecurityPassword())) {
			  if (param.getAction() == Constant.REQUEST_ACTION_INSERT) {
	        reb = vt040002Service.insertStationery(stationeryInfo, listRole);
	      } else if (param.getAction() == Constant.REQUEST_ACTION_UPDATE) {
	        reb = vt040002Service.updateStationery(stationeryInfo, listRole);
	      } else if (param.getAction() == Constant.REQUEST_ACTION_DELETE) {
	        reb = vt040002Service.deleteStationery(stationeryInfo, listRole);
	      }
			}
		} catch (Exception e) {
			reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}
	
	/**
	 * get history service by user name and service name
	 * 
	 * @param historyService
	 * @param principal
	 * @return integer
	 * @throws Exception
	 */
	/*@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")*/
	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> findListIssuesServiceHistoryByIdService(
			@RequestBody VT040002HistoryService issuesServiceSearch) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, issuesServiceSearch.getSecurityUsername(), issuesServiceSearch.getSecurityPassword())) {
		    reb = issuesServiceService.findListIssuesServiceHistoryForMobile(issuesServiceSearch);
		  }
		} catch (Exception e) {
			reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reb, HttpStatus.OK);
	}
	
}
