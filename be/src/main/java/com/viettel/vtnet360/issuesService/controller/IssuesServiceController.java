package com.viettel.vtnet360.issuesService.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.vtnet360.checking.service.Condition;
import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.issuesService.entity.EmployeeEntity;
import com.viettel.vtnet360.issuesService.entity.IssuesServiceEntity;
import com.viettel.vtnet360.issuesService.entity.IssuesServiceSearch;
import com.viettel.vtnet360.issuesService.entity.MSystemCodeEntity;
import com.viettel.vtnet360.issuesService.entity.PlaceEntity;
import com.viettel.vtnet360.issuesService.entity.ResponsEntity;
import com.viettel.vtnet360.issuesService.entity.ServiceEntity;
import com.viettel.vtnet360.issuesService.entity.UnitEntity;
import com.viettel.vtnet360.issuesService.service.IssuesServiceService;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityRqFind;
import com.viettel.vtnet360.vt04.vt040002.entity.VT040002Stationery;
import com.viettel.vtnet360.vt04.vt040002.entity.VT040002SystemCode;
import com.viettel.vtnet360.vt04.vt040002.service.VT040002Service;
import com.viettel.vtnet360.vt04.vt040004.entity.VT040004EntityRq;
import com.viettel.vtnet360.vt04.vt040007.entity.VT040007EntityRqExecutive;

@RestController
@RequestMapping("/com/viettel/vtnet360/issuesService")
public class IssuesServiceController extends BaseController {
	public static final long HOUR = 3600 * 1000;
	private final Logger logger = Logger.getLogger(this.getClass());
	public static final String MEDIATYPE_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String HEADER_CACHE_CONTROL = "must-revalidate, post-check=0, pre-check=0";

	@Autowired
	Properties linkTemplateExcel;

	@Autowired
	private IssuesServiceService issuesServiceService;

	@Autowired
	private VT040002Service vt040002Service;

	@Autowired
	Properties messages;

	@Autowired
  private Properties configProperty;
	
	
	@RequestMapping(value = "/employeeManager", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getEmployeeManager(@RequestBody EmployeeEntity employee) {
		logger.info("*********** dishconfig get list dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, employee.getSecurityUsername(), employee.getSecurityPassword())) {
		    resp = issuesServiceService.findEmployeeByFullName(employee);
	      System.out.println(employee.getFullNameEmployee() + "ssss");
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** dishconfig get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> insertIssuesService(@RequestBody IssuesServiceEntity requestParam,
			Principal principal) {
		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = issuesServiceService.insertIssuesService(requestParam, principal);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/employeeSeachMulti", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getEmployeeSeachMulti(@RequestBody EmployeeEntity employee) {
		logger.info("*********** dishconfig get list dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, employee.getSecurityUsername(), employee.getSecurityPassword())) {
		    resp = issuesServiceService.findEmployeeByMulti(employee);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** dishconfig get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/place", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getPlace(@RequestBody PlaceEntity place) {
		logger.info("*********** dishconfig get list dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, place.getSecurityUsername(), place.getSecurityPassword())) {
		    resp = issuesServiceService.findListPlace(place);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** dishconfig get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/service", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getService(@RequestBody ServiceEntity service) {
		logger.info("*********** dishconfig get list dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, service.getSecurityUsername(), service.getSecurityPassword())) {
		    resp = issuesServiceService.findListService(service);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** dishconfig get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/issuesServiceBrowser", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getIssuesService(@RequestBody IssuesServiceSearch issuesServiceSearch,
			Principal principal) {
		logger.info("*********** dishconfig get list dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, issuesServiceSearch.getSecurityUsername(), issuesServiceSearch.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      issuesServiceSearch.setUserNameUser((String) oauth.getPrincipal());
	      resp = issuesServiceService.findListIssuesServiceBrowser(issuesServiceSearch);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** dishconfig get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/approvedIssuesService", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> approvedIssuesService(
			@RequestBody IssuesServiceSearch issuesServiceSearch, Principal principal) {
		System.out.println("ssss" + issuesServiceSearch.getListIdIssuesService().get(0).getAppoverCvp());
		logger.info("*********** dishconfig get list dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, issuesServiceSearch.getSecurityUsername(), issuesServiceSearch.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      issuesServiceSearch.setUserNameUser((String) oauth.getPrincipal());
	      resp = issuesServiceService.updateApprovedIssesService(issuesServiceSearch);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** dishconfig get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchFullNameInRole", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchFullNameInRole(@RequestBody Condition condition) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    resp = issuesServiceService.searchFullNameInRole(condition.getSearch());
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchPlaceIsRole", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchPlaceIsRole(@RequestBody Condition condition) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    resp = issuesServiceService.searchPlaceIsRole(condition.getSearch());
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchUnitName", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchUnitName(@RequestBody Condition condition) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    resp = issuesServiceService.searchUnitName(condition.getSearch());
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/detail", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getIssuesService(@RequestBody IssuesServiceEntity issuesService) {
		logger.info("*********** dishconfig get list dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, issuesService.getSecurityUsername(), issuesService.getSecurityPassword())) {
		    resp = issuesServiceService.findIssuesServiceById(issuesService);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** dishconfig get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/unit", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getuNIT(@RequestBody UnitEntity unit) {
		logger.info("*********** dishconfig get list dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, unit.getSecurityUsername(), unit.getSecurityPassword())) {
		    resp = issuesServiceService.findListUnit(unit);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** dishconfig get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/approvedRequestIssuesService", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getListIssuesServiceApprovedRequest(
			@RequestBody IssuesServiceSearch issuesServiceSearch, Principal principal) {
		System.out.println("ssss" + issuesServiceSearch.getListIdIssuesService().get(0).getAppoverCvp());
		logger.info("*********** dishconfig get list dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, issuesServiceSearch.getSecurityUsername(), issuesServiceSearch.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      issuesServiceSearch.setUserNameUser((String) oauth.getPrincipal());
	      resp = issuesServiceService.updateApprovedIssesService(issuesServiceSearch);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** dishconfig get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/issuesServiceRequestApproved", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getIssuesServiceRequestApproved(
			@RequestBody IssuesServiceSearch issuesServiceSearch, Principal principal) {

		logger.info("*********** dishconfig get list dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, issuesServiceSearch.getSecurityUsername(), issuesServiceSearch.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      issuesServiceSearch.setUserNameUser((String) oauth.getPrincipal());
	      resp = issuesServiceService.findListIssuesServiceApprovedRequest(issuesServiceSearch);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** dishconfig get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateIssuedService", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> updateIssuedService(@RequestBody IssuesServiceEntity requestParam,
			Principal principal, OAuth2Authentication authentication) {
		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = issuesServiceService.updateIssuedService(requestParam, principal, authentication);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/executive-Service", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> executiveService(@RequestBody VT040007EntityRqExecutive requestParam,
			Principal principal) {

		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reponse = issuesServiceService.executiveService(requestParam, principal);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reponse, HttpStatus.OK);
	}

	@RequestMapping(value = "totalIssuesService", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase totalIssuesService(@RequestBody VT040000EntityRqFind requestParam, Principal principal,
			OAuth2Authentication authentication) {

		ResponseEntityBase reb = null;
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reb = issuesServiceService.totalIssuedService(requestParam, principal, authentication);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return reb;
	}

	@RequestMapping(value = "totalIssuesServiceForApprove", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase totalIssuesServiceForApprove(@RequestBody VT040004EntityRq requestParam,
			Principal principal, OAuth2Authentication authentication) {

	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    reb = issuesServiceService.totalIssuesedServiceForApprove(requestParam, principal, authentication);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return reb;
	}

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_HC_DV')")
	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListIssuseService(@RequestBody IssuesServiceSearch issuesServiceSearch, Principal principal) {
	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, issuesServiceSearch.getSecurityUsername(), issuesServiceSearch.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String loginUserName = (String) oauth.getPrincipal();
	      issuesServiceSearch.setLoginUserName(loginUserName);
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = issuesServiceService.findListIssuesService(issuesServiceSearch, listRole);
		  }
		} catch (Exception e) {
			reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@RequestMapping(value = "/history", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityBase> findListIssuesServiceHistoryByIdService(@RequestBody IssuesServiceSearch condition) {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    reb = issuesServiceService.findListIssuesServiceHistoryByIdService(condition);
		  }
		} catch (Exception e) {
			reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(reb, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/exportStationery", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> findStationeryReport(@RequestBody VT040002Stationery stationeryInfo,
			Principal principal) throws Exception {

		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String loginUserName = (String) oauth.getPrincipal();
		stationeryInfo.setLoginUserName(loginUserName);
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		
		long content = 0;
		InputStreamResource resource = null;
		File file = null;
		HttpHeaders headers = new HttpHeaders();
		
		try {
		  if(isValidated(configProperty, stationeryInfo.getSecurityUsername(), stationeryInfo.getSecurityPassword())) {
	      file = issuesServiceService.createExcelOutputExcel(stationeryInfo, listRole);
	      headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
	      resource = new InputStreamResource(new FileInputStream(file));
	      content = file.length();
	    }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT010011"));
			file = resourcex.getFile();
			content = file.length();
			resource = new InputStreamResource(new FileInputStream(file));
		}

		return ResponseEntity.ok().headers(headers).contentLength(content)
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadTemplateStationery", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> downloadTemplate(@RequestBody BaseEntity info) {

		HttpHeaders headers = new HttpHeaders();
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    File fileTemp = getFileTemplate("EXCEL_FILE_PATH_IMPORT_STATIONERYREPORT_TEMPLATE");
	      Workbook wb = getWorkbook(fileTemp);
	      ResponseEntityBase reb;

	      Sheet sheet = wb.getSheetAt(1);
	      reb = vt040002Service.findListStationeryTypeOrUnit("s008");

	      List<VT040002SystemCode> listSystemCode08 = (List<VT040002SystemCode>) reb.getData();

	      int rowNum = 0;
	      Row row;
	      for (VT040002SystemCode systemCode : listSystemCode08) {
	        row = sheet.createRow(rowNum++);

	        Cell cell = row.createCell(1);
	        cell.setCellValue(systemCode.getCodeName());
	      }

	      sheet = wb.getSheetAt(2);
	      rowNum = 0;
	      reb = vt040002Service.findListStationeryTypeOrUnit("s009");
	      List<VT040002SystemCode> listSystemCode09 = (List<VT040002SystemCode>) reb.getData();
	      for (VT040002SystemCode systemCode : listSystemCode09) {
	        row = sheet.createRow(rowNum++);

	        Cell cell = row.createCell(1);
	        cell.setCellValue(systemCode.getCodeName());
	      }

	      ByteArrayOutputStream bos = new ByteArrayOutputStream();
	      wb.write(bos);
	      byte[] barray = bos.toByteArray();
	      return new ResponseEntity<>(barray, headers, HttpStatus.OK);

		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);
	}

	private File getFileTemplate(String path) {
		String excelFilePath = linkTemplateExcel.getProperty(path);
		File file = null;
		try {
			Resource resource = new ClassPathResource(excelFilePath);
			file = resource.getFile();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return file;
	}

	private Workbook getWorkbook(File file) throws IOException {
		String extension = FilenameUtils.getExtension(file.getName());
		FileInputStream inputStream = new FileInputStream(file);
		Workbook wb;
		if ("xls".equals(extension)) {
			wb = new HSSFWorkbook(inputStream);
		} else {
			wb = new XSSFWorkbook(inputStream);
		}

		return wb;
	}

	//TODO [Thanh]: review and rewrite
	@SuppressWarnings({ "resource" })
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP')")
	@RequestMapping(value = "/uploadStationery", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> uploadStationery(Principal principal, @RequestPart("file") MultipartFile uploadfile,
			HttpServletRequest httpServletRequest) {

		HttpHeaders headers = new HttpHeaders();

		try {

			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			Collection<GrantedAuthority> listRole = oauth.getAuthorities();
			String loginUserName = (String) oauth.getPrincipal();

			String extension = FilenameUtils.getExtension(uploadfile.getOriginalFilename());
			Workbook workbook;
			if ("xls".equals(extension)) {
				workbook = new HSSFWorkbook(uploadfile.getInputStream());
			} else {
				workbook = new XSSFWorkbook(uploadfile.getInputStream());
			}

			Sheet worksheet = workbook.getSheetAt(0);
			int lastRowNum = worksheet.getLastRowNum();
			DataFormatter formatter = new DataFormatter();
			if(lastRowNum<2){
				headers.set("messageCode", "empty");
				return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);
			}

			if (!validateFormat(worksheet)) {
				headers.set("messageCode", "failFormat");
				return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);
			}

			int startRow = 2;
			int startCellIndex;

			CellStyle styleRed = workbook.createCellStyle();
			CellStyle styleBlack = workbook.createCellStyle();
			Font fontRed = workbook.createFont();
			fontRed.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
			Font fontBlack = workbook.createFont();
			fontBlack.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
			styleRed.setFont(fontRed);
			styleBlack.setFont(fontBlack);
			styleRed.setWrapText(true);
			int count = 0;

			for (int i = startRow; i <= lastRowNum; i++) {

				Row row = worksheet.getRow(i);
				if (row != null) {
					startCellIndex = 0;

					Cell cellStationeryName = row.getCell(startCellIndex++);
					Cell cellStationeryType = row.getCell(startCellIndex++);
					Cell cellcalCulationUnit = row.getCell(startCellIndex++);
					Cell cellUnitPrice = row.getCell(startCellIndex++);
					Cell cellStatus = row.getCell(startCellIndex++);
					Cell cellError = row.createCell(startCellIndex);

					row.getCell(5).setCellStyle(styleRed);

					String stationeryName = formatter.formatCellValue(cellStationeryName).trim();
					String stationeryType = formatter.formatCellValue(cellStationeryType).trim();
					String culationUnit = formatter.formatCellValue(cellcalCulationUnit).trim();
					String unitPrice = formatter.formatCellValue(cellUnitPrice).trim();
					String status = formatter.formatCellValue(cellStatus).trim();

					VT040002Stationery stationeryInfo = new VT040002Stationery();
					stationeryInfo.setLoginUserName(loginUserName);
					List<String> listError = new ArrayList<>();
					if (!validateCellValue(stationeryInfo, stationeryName, stationeryType, culationUnit, unitPrice,
							status, cellError, listError)) {
						headers.set("messageCode", "fail");
						StringBuilder errorAll = new StringBuilder();
						for (String error : listError) {
							errorAll.append(error);
							errorAll.append("\n");
						}

						cellError.setCellValue(errorAll.toString());
						count++;
						continue;
					}

					if (vt040002Service.insertStationery(stationeryInfo, listRole).getStatus() == 2) {
						cellError.setCellValue(messages.getProperty("import.stationery.stationery.exist"));
					} else {
						row.getCell(5).setCellStyle(styleBlack);
						cellError.setCellValue(messages.getProperty("import.stationery.stationery.success"));
					}

				}
			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);
			byte[] barray = bos.toByteArray();
			InputStream is = new ByteArrayInputStream(barray);
			IOUtils.readFully(is, barray);

			headers.setContentType(MediaType.parseMediaType(MEDIATYPE_EXCEL));
			String filename = uploadfile.getName();
			List<String> customHeaders = new ArrayList<>();
			customHeaders.add(CONTENT_DISPOSITION);
			headers.setAccessControlExposeHeaders(customHeaders);
			headers.setContentDispositionFormData(filename, filename);
			headers.setCacheControl(HEADER_CACHE_CONTROL);
			if (count == 0) {
				headers.set("messageCode", "success");
			}

			return new ResponseEntity<>(barray, headers, HttpStatus.OK);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		headers.set("messageCode", "error");
		return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);
	}

	private boolean validateFormat(Sheet worksheet) {
		DataFormatter formatter = new DataFormatter();

		Row rowTitle = worksheet.getRow(0);
		Cell cellTitle = rowTitle.getCell(0);
		String title = formatter.formatCellValue(cellTitle).trim();
		if (!messages.getProperty("import.stationery.title").equals(title)) {
			return false;
		}
		Row header = worksheet.getRow(1);
		int startCellIndex = 0;

		Cell cellStationeryName = header.getCell(startCellIndex++);
		Cell cellStationeryType = header.getCell(startCellIndex++);
		Cell cellcalCulationUnit = header.getCell(startCellIndex++);
		Cell cellUnitPrice = header.getCell(startCellIndex++);
		Cell cellStatus = header.getCell(startCellIndex++);

		if ((!messages.getProperty("import.stationery.header.name")
				.equals(formatter.formatCellValue(cellStationeryName).trim()))
				|| (!messages.getProperty("import.stationery.header.type")
						.equals(formatter.formatCellValue(cellStationeryType).trim()))
				|| (!messages.getProperty("import.stationery.header.calculationUnit")
						.equals(formatter.formatCellValue(cellcalCulationUnit).trim()))
				|| (!messages.getProperty("import.stationery.header.unitPrice")
						.equals(formatter.formatCellValue(cellUnitPrice).trim()))
				|| (!messages.getProperty("import.stationery.header.status")
						.equals(formatter.formatCellValue(cellStatus).trim()))) {
			return false;
		}
		Cell error = header.createCell(header.getLastCellNum());
		error.setCellValue(messages.getProperty("import.service.result"));
		error.setCellStyle(header.getCell(header.getLastCellNum() - 2).getCellStyle());
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean validateCellValue(VT040002Stationery stationeryInfo, String stationeryName, String stationeryType,
			String culationUnit, String unitPrice, String status, Cell error, List<String> listError) throws Exception {

		if (stationeryName == null || "".equals(stationeryName)) {
			listError.add(messages.getProperty("import.stationery.stationeryName.required"));
			if (stationeryType == null || "".equals(stationeryType)) {
				listError.add(messages.getProperty("import.stationery.stationeryType.required"));
			} else {
				MSystemCodeEntity mSystemCodeEntity = new MSystemCodeEntity();
				mSystemCodeEntity.setCodeName(stationeryType);
				mSystemCodeEntity.setMasterClass("S008");
				List<MSystemCodeEntity> lstMSystemCodeEntity = (List<MSystemCodeEntity>) issuesServiceService
						.findMSystemCodeByCodeName(mSystemCodeEntity).getData();
				if (lstMSystemCodeEntity.isEmpty()) {
					listError.add(messages.getProperty("import.stationery.stationeryType.notExist"));
				}
			}
		} else {
			if (stationeryType == null || "".equals(stationeryType)) {
				listError.add(messages.getProperty("import.stationery.stationeryType.required"));
			} else {
				MSystemCodeEntity mSystemCodeEntity = new MSystemCodeEntity();
				mSystemCodeEntity.setCodeName(stationeryType);
				mSystemCodeEntity.setMasterClass("S008");
				List<MSystemCodeEntity> lstMSystemCodeEntity = (List<MSystemCodeEntity>) issuesServiceService
						.findMSystemCodeByCodeName(mSystemCodeEntity).getData();
				if (lstMSystemCodeEntity.isEmpty()) {
					listError.add(messages.getProperty("import.stationery.stationeryType.notExist"));
				} else {
					stationeryInfo.setStationeryType(lstMSystemCodeEntity.get(0).getCodeValue());
					stationeryInfo.setStationeryName(stationeryName);
					int isDuplicate = (int) vt040002Service.checkDuplicateName(stationeryInfo).getData();
					if (isDuplicate > 0) {
						listError.add(messages.getProperty("import.stationery.stationeryName.exist"));
					}
				}
			}

		}
		if (culationUnit == null || "".equals(culationUnit)) {
			listError.add(messages.getProperty("import.stationery.stationeryUnit.required"));
		} else {
			MSystemCodeEntity mSystemCodeEntity = new MSystemCodeEntity();
			mSystemCodeEntity.setCodeName(culationUnit);
			mSystemCodeEntity.setMasterClass("S009");
			List<MSystemCodeEntity> lstMSystemCodeEntity = (List<MSystemCodeEntity>) issuesServiceService
					.findMSystemCodeByCodeName(mSystemCodeEntity).getData();
			if (lstMSystemCodeEntity.isEmpty()) {
				listError.add(messages.getProperty("import.stationery.stationeryUnit.notExist"));
			} else {
				stationeryInfo.setStationeryUnitCal(lstMSystemCodeEntity.get(0).getCodeValue());
			}
		}

		if (unitPrice == null || "".equals(unitPrice)) {
			listError.add(messages.getProperty("import.stationery.unitPrice.required"));
		} else {
			try {
				int unitPriceInt = Integer.parseInt(unitPrice);
				if (unitPriceInt <= 0) {
					listError.add(messages.getProperty("import.stationery.unitPrice.negative"));
				} else {
					stationeryInfo.setStationeryPrice(unitPriceInt);
				}
			} catch (Exception e) {
				listError.add(messages.getProperty("import.stationery.unitPrice.number"));
			}
		}

		if (status == null || "".equals(status)) {
			listError.add(messages.getProperty("import.stationery.status.required"));
		} else {
			if (!validateStatus(status)) {
				listError.add(messages.getProperty("import.service.status.notExist"));
			} else {
				stationeryInfo.setStatus(getStatus(status));
			}
		}
		if (listError.size() != 0) {
			return false;
		}
		return true;
	}

	private boolean validateStatus(String status) {
		return messages.getProperty("import.stationery.status.active").equalsIgnoreCase(status)
				|| messages.getProperty("import.stationery.status.notactive").equalsIgnoreCase(status);
	}

	private String getStatus(String status) {
		if (status.equalsIgnoreCase(messages.getProperty("import.service.status.active"))) {
			return "1";
		}

		return "0";
	}
	
	@RequestMapping(value = "/get-unit-tree", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponsEntity> getUnitListByID(@RequestBody UnitEntity query, Principal principal) {
		
		logger.info("******************** Start get list location ********************");
		
		/** Initialization response object */
		ResponsEntity reponse = new ResponsEntity(0, null);
		
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		
		/** Get userName */
		String userName = (String) oauth.getPrincipal();
		
		/** Get authority of user */
		Collection<GrantedAuthority> authority = oauth.getAuthorities();
		
		try {
		  if(isValidated(configProperty, query.getSecurityUsername(), query.getSecurityPassword())) {
		    reponse.setData(issuesServiceService.getUnitListByID(userName, authority, query.getQuery()));
	      reponse.setStatus(1);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			reponse.setStatus(0);
		}
		
		logger.info("******************** End get list location ********************");
		
		return new ResponseEntity<ResponsEntity>(reponse, HttpStatus.OK);
	}

}
