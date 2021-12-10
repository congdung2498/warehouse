package com.viettel.vtnet360.vt02.vt020009.controller;

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

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020009.entity.VT020009InfoToFindReport;
import com.viettel.vtnet360.vt02.vt020009.service.VT020009Service;

/**
 * Class controller for screen VT020009 : report kitchen rating
 * 
 * @author DuyNK 13/09/2018
 */
@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_Bep_truong') or hasAuthority('PMQT_HC_DV')")
@RestController
@RequestMapping("/com/viettel/vtnet360/vt02/vt020009")
public class VT020009Controller extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT020009Service vt020009Service;
	
	@Autowired
  private Properties configProperty;

	/**
	 * Select list report in a month
	 * 
	 * @param info
	 * @param principal
	 * @return ResponseEntityBase
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020009FindReport(@RequestBody VT020009InfoToFindReport info, Principal principal) {
		logger.info("*********** vt020009_01 get report start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = vt020009Service.findReport(info, userName, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020009_01 get report end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
}
