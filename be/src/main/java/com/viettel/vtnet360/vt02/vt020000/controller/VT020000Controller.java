package com.viettel.vtnet360.vt02.vt020000.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000RequestParam;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000ResponseEntity;
import com.viettel.vtnet360.vt02.vt020000.service.VT020000Service;

/**
 * Controller for Common API of vt02 module
 * 
 * @author VinhNVQ 9/8/2018
 *
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt02")
public class VT020000Controller extends BaseController {
	
	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());

	/** Service - Business and Data provide */
	@Autowired
	private VT020000Service vt020000Service;
	
	@Autowired
  private Properties configProperty;
	

	/**
	 * API return KitchenList
	 * 
	 * @author VinhNVQ 9/8/2018
	 * @param query ( map data call from client )
	 * @return ResponseEntity<VT020000ResponseEntity>
	 */
	@RequestMapping(value = "/vt020000/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VT020000ResponseEntity> getListKitchen(@RequestBody VT020000RequestParam query, Principal principal) {

		logger.info("******************** Start get list kitchen ********************");

		/** Initialization response object */
		VT020000ResponseEntity reponse = new VT020000ResponseEntity(0, null);

		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		
		/** Get userName */
		String userName = (String) oauth.getPrincipal();
		
		/** Get authority of user */
		Collection<GrantedAuthority> authority = oauth.getAuthorities();
		
		try {
		  if(isValidated(configProperty, query.getSecurityUsername(), query.getSecurityPassword())) {
		    reponse.setData(vt020000Service.kitchenList(query.getQuery(), userName,authority));
		    reponse.setStatus(1);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			reponse.setStatus(0);
		}

		logger.info("******************** End get list kitchen ********************");

		return new ResponseEntity<VT020000ResponseEntity>(reponse, HttpStatus.OK);
	}

	/**
	 * API return LocationList
	 * 
	 * @author VinhNVQ 9/8/2018
	 * @return ResponseEntity<VT020000ResponseEntity>
	 */
	@RequestMapping(value = "/vt020000/02", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VT020000ResponseEntity> getListUnit(@RequestBody VT020000RequestParam query) {

		logger.info("******************** Start get list location ********************");

		/** Initialization response object */
		VT020000ResponseEntity reponse = new VT020000ResponseEntity(0, null);

		try {
		  if(isValidated(configProperty, query.getSecurityUsername(), query.getSecurityPassword())) {
		    reponse.setData(vt020000Service.locationList(query.getQuery()));
		    reponse.setStatus(1);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			reponse.setStatus(0);
		}

		logger.info("******************** End get list location ********************");

		return new ResponseEntity<VT020000ResponseEntity>(reponse, HttpStatus.OK);
	}
	
	/**
	 * API return UnitList By ParentID
	 * 
	 * @author VinhNVQ 9/8/2018
	 * @return ResponseEntity<VT020000ResponseEntity>
	 */
	@RequestMapping(value = "/vt020000/03", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VT020000ResponseEntity> getListUnitByID(@RequestBody VT020000RequestParam query, Principal principal) {

		logger.info("******************** Start get list location ********************");

		/** Initialization response object */
		VT020000ResponseEntity reponse = new VT020000ResponseEntity(0, null);

		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		
		/** Get userName */
		String userName = (String) oauth.getPrincipal();
		
		/** Get authority of user */
		Collection<GrantedAuthority> authority = oauth.getAuthorities();

		try {
		  if(isValidated(configProperty, query.getSecurityUsername(), query.getSecurityPassword())) {
		    reponse.setData(vt020000Service.locationListByID(userName, authority, query.getQuery()));
	      reponse.setStatus(1);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			reponse.setStatus(0);
		}

		logger.info("******************** End get list location ********************");

		return new ResponseEntity<VT020000ResponseEntity>(reponse, HttpStatus.OK);
	}
	
	/**
	 * API return UnitList By ParentID
	 * 
	 * @author VinhNVQ 9/8/2018
	 * @return ResponseEntity<VT020000ResponseEntity>
	 */
	@RequestMapping(value = "/vt020000/04", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VT020000ResponseEntity> getListUnitGreatFatherByID(@RequestBody VT020000RequestParam query,
			Principal principal) {

		logger.info("******************** Start get list location ********************");

		/** Initialization response object */
		VT020000ResponseEntity reponse = new VT020000ResponseEntity(0, null);
		try {
		  if(isValidated(configProperty, query.getSecurityUsername(), query.getSecurityPassword())) {
		    reponse.setData(vt020000Service.getUnitParent(query.getQuery()));
	      reponse.setStatus(1);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			reponse.setStatus(0);
		}

		logger.info("******************** End get list location ********************");

		return new ResponseEntity<VT020000ResponseEntity>(reponse, HttpStatus.OK);
	}
}
