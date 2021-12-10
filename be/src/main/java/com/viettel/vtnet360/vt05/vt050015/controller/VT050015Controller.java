package com.viettel.vtnet360.vt05.vt050015.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050002.entity.Employee;
import com.viettel.vtnet360.vt05.vt050015.entity.ReportStationery;
import com.viettel.vtnet360.vt05.vt050015.service.VT050015ExcelVPPService;
import com.viettel.vtnet360.vt05.vt050015.service.VT050015Service;

@RestController
@RequestMapping("/com/viettel/vtnet360/vt05/vt050015")
public class VT050015Controller extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private VT050015Service VT050015Service;

	@Autowired
	private VT050015ExcelVPPService VT050015ExcelVPPService;

	@Autowired
	Properties linkTemplateExcel;
	
	@Autowired
  private Properties configProperty;

	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getEmployee(@RequestBody Employee employee) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, employee.getSecurityUsername(), employee.getSecurityPassword())) {
		    resp = VT050015Service.getEmployee(employee);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getReportStationery(@RequestBody BaseEntity info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = VT050015Service.getReportStationery();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getFullfillStationery(@RequestBody ReportStationery rpStationery) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, rpStationery.getSecurityUsername(), rpStationery.getSecurityPassword())) {
		    resp = VT050015Service.getFullfillStationery(rpStationery);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getLastUser(@RequestBody ReportStationery reportStationery) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = reportStationery.getLastUser();
		try {
		  if(isValidated(configProperty, reportStationery.getSecurityUsername(), reportStationery.getSecurityPassword())) {
		    resp = VT050015Service.getLastUser(userName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	public Date atEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public Date atStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchReportStationery(@RequestBody ReportStationery reportStationery,Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		reportStationery.setUserNameVPTCT(userName);
		if (reportStationery.getStartDate() == null) {
			reportStationery.setDateFrom(null);
		} else {
			Date date1 = atStartOfDay(reportStationery.getStartDate());
			reportStationery.setDateFrom(dateFormat.format(date1));
		}

		if (reportStationery.getEndDate() == null) {
			reportStationery.setDateTo(null);
		} else {
			Date date2 = atEndOfDay(reportStationery.getEndDate());
			reportStationery.setDateTo(dateFormat.format(date2));

		}

		if (reportStationery.getListUnitId().size() == 0) {
			reportStationery.setListUnitId(null);
		}

		if (reportStationery.getListStatus().size() == 0) {
			reportStationery.setListStatus(null);
		}
		try {
		  ObjectMapper mapper = new ObjectMapper();
		  System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(reportStationery));
		  if(isValidated(configProperty, reportStationery.getSecurityUsername(), reportStationery.getSecurityPassword())) {
		    resp = VT050015Service.searchReportStationery(reportStationery,roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}


	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Resource> exportExcel(@RequestBody ReportStationery reportStationery, Principal principal)
			throws Exception {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String loginUserName = (String) oauth.getPrincipal();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String userName = (String) oauth.getPrincipal();
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		reportStationery.setUserNameVPTCT(userName);
		if (reportStationery.getStartDate() == null) {
			reportStationery.setDateFrom(null);
		} else {
			Date date1 = atStartOfDay(reportStationery.getStartDate());
			reportStationery.setDateFrom(dateFormat.format(date1));
		}

		if (reportStationery.getEndDate() == null) {
			reportStationery.setDateTo(null);
		} else {
			Date date2 = atEndOfDay(reportStationery.getEndDate());
			reportStationery.setDateTo(dateFormat.format(date2));
		}

		if (reportStationery.getListUnitId().size() == 0) {
			reportStationery.setListUnitId(null);
		}

		if (reportStationery.getListStatus().size() == 0) {
			reportStationery.setListStatus(null);
		}

		File file = VT050015Service.createExcel(reportStationery, loginUserName,roleList);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
		InputStreamResource resource = null;
		try {
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT050015"));
			file = resourcex.getFile();
			resource = new InputStreamResource(new FileInputStream(file));
		}

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@RequestMapping(value = "/07", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> exportExcelVTTB(@RequestBody ReportStationery reportStationery, Principal principal,
			OAuth2Authentication authentication) throws Exception {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String loginUserName = (String) oauth.getPrincipal();
		reportStationery.setUserNameVPTCT(loginUserName);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		File file = VT050015ExcelVPPService.createExcelVPP(reportStationery, principal, authentication,roleList);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
		InputStreamResource resource = null;
		try {
		  if(isValidated(configProperty, reportStationery.getSecurityUsername(), reportStationery.getSecurityPassword())) {
		    resource = new InputStreamResource(new FileInputStream(file));
		  }
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT050015_VPP"));
			file = resourcex.getFile();
			resource = new InputStreamResource(new FileInputStream(file));
		}

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@RequestMapping(value = "/08", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> getLoginInfo(@RequestBody BaseEntity info, Principal principal) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      resp = VT050015Service.getLoginInfo(userName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/09", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> countReportStationery(@RequestBody ReportStationery reportStationery,Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		reportStationery.setUserNameVPTCT(userName);
		if (reportStationery.getStartDate() == null) {
			reportStationery.setDateFrom(null);
		} else {
			Date date1 = atStartOfDay(reportStationery.getStartDate());
			reportStationery.setDateFrom(dateFormat.format(date1));
		}

		if (reportStationery.getEndDate() == null) {
			reportStationery.setDateTo(null);
		} else {
			Date date2 = atEndOfDay(reportStationery.getEndDate());
			reportStationery.setDateTo(dateFormat.format(date2));

		}

		if (reportStationery.getListUnitId().size() == 0) {
			reportStationery.setListUnitId(null);
		}

		if (reportStationery.getListStatus().size() == 0) {
			reportStationery.setListStatus(null);
		}
		try {
//			ObjectMapper mapper = new ObjectMapper();
//			  System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(reportStationery));
		  if(isValidated(configProperty, reportStationery.getSecurityUsername(), reportStationery.getSecurityPassword())) {
		    resp = VT050015Service.countReportStationery(reportStationery, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/10", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getHcdv(@RequestBody Employee employee) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, employee.getSecurityUsername(), employee.getSecurityPassword())) {
		    resp = VT050015Service.getHcdv(employee);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getEmployeeTCT", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getEmployeeTCT(@RequestBody Employee employee) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, employee.getSecurityUsername(), employee.getSecurityPassword())) {
		    resp = VT050015Service.getEmployeeTCT(employee);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getFullnameHCDV", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getFullnameHCDV(@RequestBody ReportStationery reportStationery) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    String userName = reportStationery.getFullName();
    try {
      if(isValidated(configProperty, reportStationery.getSecurityUsername(), reportStationery.getSecurityPassword())) {
        resp = VT050015Service.getLastUser(userName);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }	
	
}
