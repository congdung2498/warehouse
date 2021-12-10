package com.viettel.vtnet360.vt02.vt020012.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000ResponseEntity;
import com.viettel.vtnet360.vt02.vt020012.entity.VT020012RequestParam;
import com.viettel.vtnet360.vt02.vt020012.service.VT020012Service;


/**
 * Controller for VT020012 API of vt02 module
 * 
 * @author VinhNVQ 9/10/2018
 *
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt02/vt020012")
public class VT020012Controller extends BaseController {

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());

	/** Service - Business and Data provide */
	@Autowired
	private VT020012Service vT020012Service;
	
	@Autowired
  private Properties configProperty;

	/**
	 * API return KitchenList
	 * 
	 * @author VinhNVQ 9/8/2018
	 * @param query ( map data call from client )
	 * @return ResponseEntity<VT020000ResponseEntity>
	 */
	@PreAuthorize("hasAuthority('LUNCH_MANAGEMENT') and hasAuthority('REPORT_BY_UNIT')")
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VT020000ResponseEntity> getListReportByUnit(@RequestBody VT020012RequestParam param, Principal principal) {

		logger.info("******************** Start get report by unit ********************");

		/** Initialization response object */
		VT020000ResponseEntity reponse = new VT020000ResponseEntity(1, null);
		
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		
		/** Get userName */
		String userName = (String) oauth.getPrincipal();
		
		/** Get authority of user */
		Collection<GrantedAuthority> authority = oauth.getAuthorities();
		
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    reponse.setData(vT020012Service.kitchenList(param, userName, authority));
		  }
		} catch (Exception e) {
			reponse.setStatus(0);
		}

		logger.info("******************** End get report by unit ********************");

		return new ResponseEntity<>(reponse, HttpStatus.OK);
	}
	
	/**
	 * API return excel
	 * 
	 * @author VinhNVQ 13/12/2018
	 * @param param
	 * @param principal
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('LUNCH_MANAGEMENT') and hasAuthority('REPORT_BY_UNIT')")
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> getListReportByUnitExcel(@RequestBody VT020012RequestParam param,
			Principal principal) throws Exception {

		logger.info("******************** Start get report by unit ********************");

		/** Initialization response object */
		HttpHeaders headers = new HttpHeaders();
		
		
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		
		/** Get userName */
		String userName = (String) oauth.getPrincipal();
		
		/** Get authority of user */
		Collection<GrantedAuthority> authority = oauth.getAuthorities();
		
		File file = null;
		
		InputStreamResource resource = null;
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    file = vT020012Service.kitchenReport(param, userName, authority);
	      resource = new InputStreamResource(new FileInputStream(file));
	      headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
	      return ResponseEntity.ok()
		            .headers(headers)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType("application/octet-stream"))
		            .body(resource);
		  }
		} catch (FileNotFoundException e) {
		    logger.error(e.getMessage(), e);
			throw e;
		}

		logger.info("******************** End get report by unit ********************");

		return ResponseEntity.ok().headers(headers).contentLength(0)
		        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}
}
