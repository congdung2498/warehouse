package com.viettel.vtnet360.vt04.vt040010.controller;

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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.issuesService.entity.IssuesServiceSearch;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010Condition;
import com.viettel.vtnet360.vt04.vt040010.service.VT040010Service;
import com.viettel.vtnet360.vt04.vt040010.service.VT040010ServiceExportExcel;

@RestController
@RequestMapping("/com/viettel/vtnet360/vt04/vt040010")
public class VT040010Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT040010Service vt040010Service;

	@Autowired
	private VT040010ServiceExportExcel vt040010ServiceExport;

	@Autowired
	Properties linkTemplateExcel;
	
	@Autowired
  private Properties configProperty;

	/**
	 * get list of registration service
	 * 
	 * @param condition
	 * @return List<VT040010ListRegistrationService>
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_HC_DV')")
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListRegistrationService(@RequestBody VT040010Condition condition, Principal principal) {

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String loginUserName = (String) oauth.getPrincipal();
	      condition.setLoginUserName(loginUserName);
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = vt040010Service.findListReportService(condition, listRole);
		  }
		} catch (Exception e) {
			reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get total record of list report service
	 * 
	 * @param condition
	 * @param principal
	 * @return number
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_HC_DV')")
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase countTotalRecord(@RequestBody VT040010Condition condition, Principal principal) throws Exception {
	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String loginUserName = (String) oauth.getPrincipal();
	      condition.setLoginUserName(loginUserName);
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = vt040010Service.countTotalRecord(condition, listRole);
		  }
		} catch (Exception e) {
			reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get list of stationery
	 * 
	 * @param conStat
	 * @return List<VT040010ListStationery>
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_HC_DV')")
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListStationery(@RequestBody VT040010Condition conStat) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, conStat.getSecurityUsername(), conStat.getSecurityPassword())) {
		    reb = vt040010Service.findListStationery(conStat);
		  }
		} catch (Exception e) {
			reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get total record of stationery
	 * 
	 * @param conStat
	 * @return number
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_HC_DV')")
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase countTotalRecordStationery(@RequestBody VT040010Condition conStat) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, conStat.getSecurityUsername(), conStat.getSecurityPassword())) {
		    reb = vt040010Service.countTotalRecordStationery(conStat);
		  }
		} catch (Exception e) {
			reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get report excel file
	 * 
	 * @param condition
	 * @return file excel
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_HC_DV') ")
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> exportExcel(@RequestBody IssuesServiceSearch issuesServiceSearch, Principal principal)
			throws Exception {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String loginUserName = (String) oauth.getPrincipal();
		issuesServiceSearch.setLoginUserName(loginUserName);
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		
		File file = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
		InputStreamResource resource = null;
		try {
		  if(isValidated(configProperty, issuesServiceSearch.getSecurityUsername(), issuesServiceSearch.getSecurityPassword())) {
		    file = vt040010Service.createExcel(issuesServiceSearch, listRole);
		    resource = new InputStreamResource(new FileInputStream(file));
		  }
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT040010"));
			file = resourcex.getFile();
			resource = new InputStreamResource(new FileInputStream(file));
		}
		
		if(file == null) return ResponseEntity.ok().headers(headers).contentLength(0)
        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	/**
	 * export excel
	 * 
	 * @param condition
	 * @param principal
	 * @return file excel
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_HC_DV')")
	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> exportExcelVTTB(@RequestBody VT040010Condition condition, Principal principal,
			OAuth2Authentication authentication) throws Exception {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String loginUserName = (String) oauth.getPrincipal();
		condition.setLoginUserName(loginUserName);
		if (oauth.getAuthorities().contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
			condition.setLoginRole(Constant.PMQT_ROLE_ADMIN);
		} else if (oauth.getAuthorities().contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))
				|| oauth.getAuthorities().contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP))) {
			condition.setLoginRole(Constant.PMQT_ROLE_MANAGER);
		}

		File file = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
		InputStreamResource resource = null;
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    file = vt040010ServiceExport.createExcelVTTB(condition);
		    resource = new InputStreamResource(new FileInputStream(file));
	    }
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT040010_VTTB"));
			file = resourcex.getFile();
			resource = new InputStreamResource(new FileInputStream(file));
		}

		if(file == null) return ResponseEntity.ok().headers(headers).contentLength(0)
        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}
}
