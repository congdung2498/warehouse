package com.viettel.vtnet360.vt01.vt010011.controller;

import java.io.File;
import java.io.FileInputStream;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt01.vt010011.entity.VT010011ListCondition;
import com.viettel.vtnet360.vt01.vt010011.entity.VT010011ListEmployee;
import com.viettel.vtnet360.vt01.vt010011.service.VT010011Service;

/**
 * Class controller of VT010011
 * 
 * @author ThangBT 07/09/2018
 *
 */

@RestController
@RequestMapping("/com/viettel/vtnet360/vt01/vt010011")
public class VT010011Controller extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  
  
	@Autowired
	private VT010011Service vt010011Service;

	@Autowired
	Properties linkTemplateExcel;
	
	@Autowired
  private Properties configProperty;


	/**
	 * get list of employee
	 * 
	 * @param empInfo
	 * @return List<VT010011ListEmployee>
	 * @throws Exception
	 */
//	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_Canhve') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListEmployee(@RequestBody VT010011ListEmployee empInfo) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, empInfo.getSecurityUsername(), empInfo.getSecurityPassword())) {
		    reb = vt010011Service.findListEmployee(empInfo);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get list registration go out
	 * 
	 * @param vlc
	 * @param principal
	 * @return List<VT010011ListCondition>
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_Canhve')")
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListInOut(@RequestBody VT010011ListCondition vlc, Principal principal) throws Exception {

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);;
		try {
		  if(isValidated(configProperty, vlc.getSecurityUsername(), vlc.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String loginUserName = (String) oauth.getPrincipal();
	      vlc.setLoginUserName(loginUserName);
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = vt010011Service.findListInOut(vlc, listRole);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get excel file report
	 * 
	 * @param vlc
	 * @param principal
	 * @return excel file
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_Canhve')")
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> exportExcelListInOut(@RequestBody VT010011ListCondition vlc, Principal principal) throws Exception {
	  HttpHeaders headers = new HttpHeaders();
	  long data = 0;
	  InputStreamResource resource = null;
	  
	  if(isValidated(configProperty, vlc.getSecurityUsername(), vlc.getSecurityPassword())) {
	    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	    String loginUserName = (String) oauth.getPrincipal();
	    vlc.setLoginUserName(loginUserName);
	    Collection<GrantedAuthority> listRole = oauth.getAuthorities();

	    File file = vt010011Service.createExcelOutputExcel(vlc, listRole);
	    headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
	    try {
	      resource = new InputStreamResource(new FileInputStream(file));
	      data = file.length();
	    } catch (Exception e) {
	      logger.error(e.getMessage(), e);
	      Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT010011"));
	      file = resourcex.getFile();
	      resource = new InputStreamResource(new FileInputStream(file));
	    }
	  }

		return ResponseEntity.ok().headers(headers).contentLength(data)
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	/**
	 * count total record
	 * 
	 * @param vlc
	 * @param principal
	 * @return number of records
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_Canhve')")
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase countTotalRecord(@RequestBody VT010011ListCondition vlc, Principal principal) throws Exception {
		ResponseEntityBase reb = null;

		try {
		  if(isValidated(configProperty, vlc.getSecurityUsername(), vlc.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String loginUserName = (String) oauth.getPrincipal();
	      vlc.setLoginUserName(loginUserName);
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = vt010011Service.countTotalRecord(vlc, listRole);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}
}
