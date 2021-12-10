package com.viettel.vtnet360.vt02.vt020001.controller;

import java.security.Principal;
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
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000RequestParam;
import com.viettel.vtnet360.vt02.vt020001.entity.VT020001Place;
import com.viettel.vtnet360.vt02.vt020001.service.VT020001Service;

/**
 * Class controller for screen VT020001 : setting place
 * 
 * @author DuyNK 08/09/2018
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt02/vt020001")
public class VT020001Controller extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT020001Service vt020001Service;

	@Autowired
  private Properties configProperty;
	
	/**
	 * Select all place from QLDV_PLACE
	 * 
	 * @return ResponseEntityBase(status, List<VT020001Place>)
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020001FindListPlace(@RequestBody VT020001Place place) {
		logger.info("*********** vt020001_01 get list place start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, place.getSecurityUsername(), place.getSecurityPassword())) {
		    resp = vt020001Service.findListPlace(place);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020001_01 get list place end***********");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	/**
	 * count total record of places
	 * 
	 * @author ThangBT
	 * @param place
	 * @return number of record
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020001CountTotalRecord(@RequestBody VT020001Place place) {
		logger.info("*********** vt020001_02 count record start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, place.getSecurityUsername(), place.getSecurityPassword())) {
		    resp = vt020001Service.counTotalRecord(place);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020001_02 count record end***********");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	/**
	 * Insert, delete, update place to QLDV_PLACE
	 * 
	 * @param param
	 * @param principal
	 * @return ResponseEntityBase
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020001SettingPlace(
			@RequestBody VT000000RequestParam<VT020001Place> param, Principal principal) {
		logger.info("*********** vt020001_03 setting place start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			String userName = (String) oauth.getPrincipal();
			VT020001Place place = param.getData();
			place.setUpdateUser(userName);
			if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
			  if (param.getAction() == Constant.REQUEST_ACTION_INSERT) {
	        logger.info("*********** vt020001_03 insert place start***********");
	        resp = vt020001Service.insertPlace(place);
	        logger.info("*********** vt020001_03 insert place start***********");
	      } else if (param.getAction() == Constant.REQUEST_ACTION_UPDATE) {
	        logger.info("*********** vt020001_03 update place start***********");
	        resp = vt020001Service.updatePlace(place);
	        logger.info("*********** vt020001_03 update place start***********");
	      } else if (param.getAction() == Constant.REQUEST_ACTION_DELETE) {
	        logger.info("*********** vt020001_03 delete place start***********");
	        resp = vt020001Service.deletePlace(place);
	        logger.info("*********** vt020001_03 delete place start***********");
	      } else {
	        resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	      }
      }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020001_03 setting place end***********");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
