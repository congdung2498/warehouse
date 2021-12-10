package com.viettel.vtnet360.vt05.vt050017.controller;

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
import com.viettel.vtnet360.vt05.vt050017.entity.VT050017RequestParam;
import com.viettel.vtnet360.vt05.vt050017.service.VT050017Service;

/**
 * @author DuyNK
 */
@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HC_DV')")
@RestController
@RequestMapping("/com/viettel/vtnet360/vt05/vt050017")
public class VT050017Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050017Service vt050017Service;

	@Autowired
  private Properties configProperty;
	
	/**
	 * find list info request of hcdv
	 * 
	 * @param param
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050017FindDataRequestOfHCDV(@RequestBody VT050017RequestParam param,
			Principal principal) {
		logger.info("*********** vt050017_01 Find Data Request Of HCDV start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      param.setHcdvUserName(userName);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = vt050017Service.findData(param, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050017_01 Find Data Request Of HCDV end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

}
