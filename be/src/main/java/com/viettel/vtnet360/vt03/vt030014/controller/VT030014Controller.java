package com.viettel.vtnet360.vt03.vt030014.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListEmployee;
import com.viettel.vtnet360.vt03.vt030014.service.VT030014Service;

/**
 * Class controller of VT030014
 * 
 * @author ThangBT 07/09/2018
 *
 */

@RestController
@RequestMapping("/com/viettel/vtnet360/vt03/vt030014")
public class VT030014Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT030014Service vt030014Service;

	@Autowired
	Properties linkTemplateExcel;
	
	@Autowired
  private Properties configProperty;

	/**
	 * get list of employee who is booker
	 * 
	 * @param empInfo
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') "
      + "          or hasAuthority('PMQT_QL_Doi_xe') or hasAuthority('PMQT_Doi_xe') or hasAuthority('PMQT_HCDV')")
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListEmployee(@RequestBody VT030014ListEmployee empInfo) throws Exception {
		ResponseEntityBase reb = null;
		try {
		  if(isValidated(configProperty, empInfo.getSecurityUsername(), empInfo.getSecurityPassword())) {
		    reb = vt030014Service.findListEmployee(empInfo);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return reb;
	}

	/**
	 * get list of employee's phone number
	 * 
	 * @param empInfo
	 * @return List<VT030014ListEmployee>
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') "
      + "          or hasAuthority('PMQT_QL_Doi_xe') or hasAuthority('PMQT_Doi_xe') or hasAuthority('PMQT_HCDV')")
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListEmployeePhone(@RequestBody VT030014ListEmployee empInfo) throws Exception {
		ResponseEntityBase reb = null;
		try {
		  if(isValidated(configProperty, empInfo.getSecurityUsername(), empInfo.getSecurityPassword())) {
		    reb = vt030014Service.findListEmployeePhone(empInfo);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return reb;
	}

	/**
	 * get list of driver
	 * 
	 * @param driverInfo
	 * @return List<VT030014ListDriver>
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') "
      + "          or hasAuthority('PMQT_QL_Doi_xe') or hasAuthority('PMQT_Doi_xe') or hasAuthority('PMQT_HCDV')")
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListDriver(@RequestBody VT030014ListEmployee driverInfo) throws Exception {
		ResponseEntityBase reb = null;
		try {
		  if(isValidated(configProperty, driverInfo.getSecurityUsername(), driverInfo.getSecurityPassword())) {
		    reb = vt030014Service.findListDriver(driverInfo);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return reb;
	}

	
	/**
	 * @param condition
	 * @param principal
	 * @return List<VT030014ListTrip>
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') "
      + "          or hasAuthority('PMQT_QL_Doi_xe') or hasAuthority('PMQT_Doi_xe') or hasAuthority('PMQT_HCDV')")
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListTrip(@RequestBody VT030014ListCondition condition, Principal principal) throws Exception {
		ResponseEntityBase reb = null;
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String loginUserName = (String) oauth.getPrincipal();
	      condition.setLoginUserName(loginUserName);
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = vt030014Service.findListTrip(condition, listRole);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return reb;
	}
	
	/**
	 * get report excel file
	 * 
	 * @param condition
	 * @param principal
	 * @return excel file
	 * @throws Exception
	 */
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> exportExcelListTrip(@RequestBody VT030014ListCondition condition,
			Principal principal) throws Exception {
	  System.out.println("vt030014/05   exportExcelListTrip START");
	  HttpHeaders headers = new HttpHeaders();
	  InputStreamResource resource = null;
	  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
	    System.out.println("vt030014/05   exportExcelListTrip ");
	    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	    String loginUserName = (String) oauth.getPrincipal();
	    condition.setLoginUserName(loginUserName);
	    Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	    File file = vt030014Service.createExcelOutputExcel(condition, listRole);
	    headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
	    try {
	      resource = new InputStreamResource(new FileInputStream(file));
	      return ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	      
	    } catch (FileNotFoundException e) {
	      logger.error(e.getMessage(), e);
	      Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT030014"));
	      file = resourcex.getFile();
	      resource = new InputStreamResource(new FileInputStream(file));
	    }
	  }
	  Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT030014"));
      File file = resourcex.getFile();
      resource = new InputStreamResource(new FileInputStream(file));
      return ResponseEntity.ok().headers(headers).contentLength(0)
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	/**
	 * count total records
	 * 
	 * @param condition
	 * @param principal
	 * @return number of records
	 * @throws Exception
	 */
	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase countTotalRecord(@RequestBody VT030014ListCondition condition, Principal principal) throws Exception {
		ResponseEntityBase reb = null;
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String loginUserName = (String) oauth.getPrincipal();
	      condition.setLoginUserName(loginUserName);
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = vt030014Service.countTotalRecord(condition, listRole);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return reb;
	}
}
