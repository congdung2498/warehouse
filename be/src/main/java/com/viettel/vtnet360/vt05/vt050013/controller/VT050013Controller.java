package com.viettel.vtnet360.vt05.vt050013.controller;

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
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013InfoToEditRequest;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013ParamToRating;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013ParamToRatingVPTCT;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013RequestParam;
import com.viettel.vtnet360.vt05.vt050013.service.VT050013Service;

/**
 * @author DuyNK 09/10/2018
 */
@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_HCVP_VPP')")
@RestController
@RequestMapping("/com/viettel/vtnet360/vt05/vt050013")
public class VT050013Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050013Service vt050013Service;
	
	@Autowired
  private Properties configProperty;

	/**
	 * find info request of this user logged on
	 * 
	 * @param param
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050013FindData(@RequestBody VT050013RequestParam param) {
		logger.info("*********** vt050013_01 Find Data start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = vt050013Service.findData(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050013_01 Find Data end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * rating
	 * 
	 * @param param
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050013Rating(@RequestBody VT050013ParamToRating param, Principal principal) {
		logger.info("*********** vt050013_02 Rating start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      param.setUpdateUser(userName);
	      resp = vt050013Service.rating(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050013_02 Rating end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * cancel request
	 * 
	 * @param param
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050013CancelRequest(@RequestBody VT050013RequestParam param, Principal principal) {
		logger.info("*********** vt050013_03 Cancel Request start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      resp = vt050013Service.cancelRequest(param, userName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050013_03 Cancel Request end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * edit request or delete some item in request
	 * 
	 * @param info
	 * @param principal
	 * @return ResponseEntity<ResponseEntityBase>
	 */
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050013EditRequest(@RequestBody VT050013InfoToEditRequest info, Principal principal) {
		logger.info("*********** vt050013_04 Edit Request start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      info.setUpdateUser(userName);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = vt050013Service.editRequest(info, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050013_03 Edit Request end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050013RatingVPTCT(@RequestBody VT050013ParamToRatingVPTCT param, Principal principal) {
		logger.info("*********** vt050013_02 RatingVPTCT start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      param.setUpdateUser(userName);
	      resp = vt050013Service.ratingVPTCT(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050013_02 RatingVPTCT end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt050013RatingHCDV(@RequestBody VT050013ParamToRatingVPTCT param, Principal principal) {
		logger.info("*********** vt050013_02 RatingHCDV start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      param.setUpdateUser(userName);
	      resp = vt050013Service.ratingHCDV(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050013_02 RatingHCDV end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findDataVPTCT", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> findDataVPTCT(@RequestBody VT050013RequestParam param,
      Principal principal) {
    logger.info("*********** vt050013_02 findDataVPTCT start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        String userName = (String) oauth.getPrincipal();
        param.setUserName(userName);
        resp = vt050013Service.findDataVPTCT(param);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    logger.info("*********** vt050013_02 findDataVPTCT end***********");
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
	
}
