package com.viettel.vtnet360.stationery.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.stationery.entity.CaculationUnitTemplacte;
import com.viettel.vtnet360.stationery.entity.CanceIssuesStationey;
import com.viettel.vtnet360.stationery.entity.DataSpendingLimitQuota;
import com.viettel.vtnet360.stationery.entity.InfoEmployee;
import com.viettel.vtnet360.stationery.entity.MSystemCodeEntity;
import com.viettel.vtnet360.stationery.entity.PlaceEmployeeParam;
import com.viettel.vtnet360.stationery.entity.PlaceNameAllResponseTemplate;
import com.viettel.vtnet360.stationery.entity.ProcessIssuesStationery;
import com.viettel.vtnet360.stationery.entity.RequestParam;
import com.viettel.vtnet360.stationery.entity.RequestParamEdit;
import com.viettel.vtnet360.stationery.entity.ResultConfigName;
import com.viettel.vtnet360.stationery.entity.SearchData;
import com.viettel.vtnet360.stationery.entity.SearchRequestParamPagging;
import com.viettel.vtnet360.stationery.entity.StationeryEmployeeParam;
import com.viettel.vtnet360.stationery.entity.StationeryNameAllResponseTemplate;
import com.viettel.vtnet360.stationery.entity.StationeryParam;
import com.viettel.vtnet360.stationery.entity.UpdateAllList;
import com.viettel.vtnet360.stationery.entity.UpdateIssuesStationery;
import com.viettel.vtnet360.stationery.entity.VoteHCDV;
import com.viettel.vtnet360.stationery.request.dao.StationeryReportDAO;
import com.viettel.vtnet360.stationery.service.StationeryReportService;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt05.vt050000.service.VT050000Service;
import com.viettel.vtnet360.vt05.vt050001.dao.VT050001DAO;
import com.viettel.vtnet360.vt05.vt050001.entity.Stationery;
import com.viettel.vtnet360.vt05.vt050001.service.VT050001Service;
import com.viettel.vtnet360.vt05.vt050003.service.VT050003Service;
import com.viettel.vtnet360.vt05.vt050006.entity.VT050006RequestParam;
import com.viettel.vtnet360.vt05.vt050015.entity.ReportStationery;

@Controller
@RequestMapping("/com/viettel/vtnet360/stationeryReport")
public class StationeryReportController extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());
	public static final String MEDIATYPE_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String HEADER_CACHE_CONTROL = "must-revalidate, post-check=0, pre-check=0";

	@Autowired
	private StationeryReportService stationeryReportService;

	
	@Autowired
	Properties linkTemplateExcel;

	@Autowired
	private VT050001DAO vt050001DAO;

	@Autowired
	VT000000DAO vt000000Dao;

	@Autowired
	private VT050000Service vt050000Service;

	@Autowired
	private VT050003Service vt050003Service;

	@Autowired
	private StationeryReportDAO stationeryReportDAO;

	@Autowired
	private VT050001Service vt050001Service;

	@Autowired
	Properties messages;
	
	@Autowired
  private Properties configProperty;

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

	private boolean validateFormat(Sheet worksheet) {
		DataFormatter formatter = new DataFormatter();

		Row rowTitle = worksheet.getRow(0);
		if(rowTitle== null){
			return false;
		}
		Cell cellTitle = rowTitle.getCell(0);
		String title = formatter.formatCellValue(cellTitle).trim();
		if (!messages.getProperty("import.stationery.stationeryEmployee.title").equals(title)) {
			return false;
		}
		Row header = worksheet.getRow(1);
		int startCellIndex = 0;

		Cell cellStt = header.getCell(startCellIndex++);
		Cell cellStationeryName = header.getCell(startCellIndex++);
		Cell cellUnitPrice = header.getCell(startCellIndex++);
		Cell cellcalCulationUnit = header.getCell(startCellIndex++);
		

		if ((!messages.getProperty("import.stationery.stationeryEmployee.header.stt")
				.equals(formatter.formatCellValue(cellStt).trim())
				||(!messages.getProperty("import.stationery.stationeryEmployee.header.name")
				.equals(formatter.formatCellValue(cellStationeryName).trim()))
				|| (!messages.getProperty("import.stationery.stationeryEmployee.header.calculationUnit")
						.equals(formatter.formatCellValue(cellcalCulationUnit).trim()))
				|| (!messages.getProperty("import.stationery.stationeryEmployee.header.unitPrice")
						.equals(formatter.formatCellValue(cellUnitPrice).trim())))) {
			return false;
		}
		Cell error = header.createCell(header.getLastCellNum());
		error.setCellValue(messages.getProperty("import.service.result"));
		error.setCellStyle(header.getCell(header.getLastCellNum() - 2).getCellStyle());
		return true;
	}

	@RequestMapping(value = "/searchStationeryByEmployee", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchReportStationery(
	    @RequestBody ReportStationery reportStationery, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		if(isValidated(configProperty, reportStationery.getSecurityUsername(), reportStationery.getSecurityPassword())) {
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
	      OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String loginUserName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      resp = stationeryReportService.searchStationeryByEmployee(reportStationery, loginUserName, listRole);
	    } catch (Exception e) {
	      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	      logger.error(e.getMessage(), e);
	      throw (e);
	    }
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/createReportExcel", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Resource> exportExcel(@RequestBody ReportStationery reportStationery, Principal principal) throws Exception {
	  HttpHeaders headers = new HttpHeaders();
	  File file = null;
	  InputStreamResource resource = null;
	  
	  if(isValidated(configProperty, reportStationery.getSecurityUsername(), reportStationery.getSecurityPassword())) {
	    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	    String loginUserName = (String) oauth.getPrincipal();
	    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

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

	    file = stationeryReportService.createExcel(reportStationery, loginUserName);
	    headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
	    try {
	      resource = new InputStreamResource(new FileInputStream(file));
	    } catch (FileNotFoundException e) {
	      logger.error(e.getMessage(), e);
	      Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_EMPLOYEE"));
	      file = resourcex.getFile();
	      resource = new InputStreamResource(new FileInputStream(file));
	      return ResponseEntity.ok().headers(headers).contentLength(file.length())
	          .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	    }
	  }
	  return ResponseEntity.ok().headers(headers).contentLength(0)
		        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	public Date atStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public Date atEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	@RequestMapping(value = "/getDetailsIssuesStationeryItems", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getDetailsIssuesStationeryItems(@RequestBody SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		if(isValidated(configProperty, searchData.getSecurityUsername(), searchData.getSecurityPassword())) {
		  try {
	      resp = stationeryReportService.getDetailsIssuesStationeryItems(searchData);
	    } catch (Exception e) {
	      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	      logger.error(e.getMessage(), e);
	    }
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateApproveIssuesStationery", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateApproveIssuesStationery(
			@RequestBody UpdateIssuesStationery updateIssuesStationery, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
		  if(isValidated(configProperty, updateIssuesStationery.getSecurityUsername(), updateIssuesStationery.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      updateIssuesStationery.setUserName(userName);
	      resp = stationeryReportService.updateApproveIssuesStationery(updateIssuesStationery);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateAllIssuesStationery", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateAllIssuesStationery(
			@RequestBody UpdateAllList updateIssuesStationery, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
		  if(isValidated(configProperty, updateIssuesStationery.getSecurityUsername(), updateIssuesStationery.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      updateIssuesStationery.setUserName(userName);
	      resp = stationeryReportService.updateApproveIssuesStationeryAllList(updateIssuesStationery);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/countStationeryByEmployee", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> countStationeryByEmployee(
			@RequestBody ReportStationery reportStationery, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

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
		  if(isValidated(configProperty, reportStationery.getSecurityUsername(), reportStationery.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String loginUserName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      resp = stationeryReportService.countStationeryByEmployee(reportStationery, loginUserName, listRole);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/checkLimitStationeryByEmployee", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> checkLimitStationeryByEmployee(
			@RequestBody ReportStationery reportStationery, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
		  if(isValidated(configProperty, reportStationery.getSecurityUsername(), reportStationery.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      reportStationery.setUserName(userName);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      double remainingSpendingLimit = vt050000Service.caculRemainingSpendingLimit(userName, roleList);
	      resp.setData(remainingSpendingLimit);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getIssuesStationeryItemsById", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getIssuesStationeryItemsById(@RequestBody SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchData.getSecurityUsername(), searchData.getSecurityPassword())) {
		    resp = stationeryReportService.getIssuesStationeryItemsById(searchData);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCancelIssuesStationery", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateCancelIssuesStationery(
			@RequestBody CanceIssuesStationey canceIssuesStationey, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
		  if(isValidated(configProperty, canceIssuesStationey.getSecurityUsername(), canceIssuesStationey.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      canceIssuesStationey.setUserName(userName);
	      resp = stationeryReportService.updateCancelIssuesStationery(canceIssuesStationey);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/editIssuesStationery", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> editIssuesStationery(@RequestBody RequestParamEdit requestParam,
			Principal principal) {
		logger.info("*********** editIssuesStationery Insert Issues Stationery start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = stationeryReportService.editIssuesStationery(requestParam, userName, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** editIssuesStationery Insert Issues Stationery end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateRefuseIssuesStationery", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateRefuseIssuesStationery(
			@RequestBody UpdateIssuesStationery updateIssuesStationery, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
		  if(isValidated(configProperty, updateIssuesStationery.getSecurityUsername(), updateIssuesStationery.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      updateIssuesStationery.setUserName(userName);
	      resp = stationeryReportService.updateRefuseIssuesStationery(updateIssuesStationery);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	// xac nhan tiep nhan
	@RequestMapping(value = "/confirmReceiptIssuesStationery", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> confirmReceiptIssuesStationery(
			@RequestBody ProcessIssuesStationery processIssuesStationery, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, processIssuesStationery.getSecurityUsername(), processIssuesStationery.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      processIssuesStationery.setUserName(userName);
	      resp = stationeryReportService.confirmReceiptIssuesStationery(processIssuesStationery);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	// hoan thuc hien
	@RequestMapping(value = "/postponedImplementationIssuesItems", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> postponedImplementationIssuesItems(
			@RequestBody ProcessIssuesStationery processIssuesStationery, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, processIssuesStationery.getSecurityUsername(), processIssuesStationery.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      processIssuesStationery.setUserName(userName);
	      resp = stationeryReportService.postponedImplementationIssuesItems(processIssuesStationery);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	// khong thuc hien duoc
	@RequestMapping(value = "/notPossibleApprove", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> notPossibleApprove(
			@RequestBody ProcessIssuesStationery processIssuesStationery, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, processIssuesStationery.getSecurityUsername(), processIssuesStationery.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      processIssuesStationery.setUserName(userName);
	      resp = stationeryReportService.notPossibleApprove(processIssuesStationery);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	// hoan thanh
	@RequestMapping(value = "/isCompleteIssusStationery", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> isCompleteIssusStationery(
			@RequestBody ProcessIssuesStationery processIssuesStationery, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, processIssuesStationery.getSecurityUsername(), processIssuesStationery.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      processIssuesStationery.setUserName(userName);
	      resp = stationeryReportService.isCompleteIssusStationery(processIssuesStationery);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	// xac nhan cap phat
	@RequestMapping(value = "/confirmationPendingApprove", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> confirmationPendingApprove(
			@RequestBody ProcessIssuesStationery processIssuesStationery, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, processIssuesStationery.getSecurityUsername(), processIssuesStationery.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      processIssuesStationery.setUserName(userName);
	      resp = stationeryReportService.confirmationPendingApprove(processIssuesStationery);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	// tu choi cap phat
	@RequestMapping(value = "/refuseConfirmationPendingApprove", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> refuseConfirmationPendingApprove(
			@RequestBody ProcessIssuesStationery processIssuesStationery, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, processIssuesStationery.getSecurityUsername(), processIssuesStationery.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      processIssuesStationery.setUserName(userName);
	      resp = stationeryReportService.refuseConfirmationPendingApprove(processIssuesStationery);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/findInfoRequestEmployee", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findInfoRequestEmployee(@RequestBody StationeryEmployeeParam param) {
		logger.info("*********** vt050006_01 Find Data To Approve start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = stationeryReportService.findDataToStationeryEmployee(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050006_01 Find Data To Approve end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchPlaceEmployeeById", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchPlaceEmployeeById(@RequestBody PlaceEmployeeParam param) {
		logger.info("*********** vt050006_01 Find Data To Approve start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = stationeryReportService.searchPlaceEmployeeById(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt050006_01 Find Data To Approve end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/findDataToRatting", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findDataToRatting(@RequestBody SearchRequestParamPagging param) {
		logger.info("*********** findDataToRatting start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = stationeryReportService.findDataToRatting(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** findDataToRatting end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findDataToRattingDetails", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findDataToRattingDetails(@RequestBody SearchRequestParamPagging param) {
		logger.info("*********** findDataToRatting start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = stationeryReportService.findDataToRattingDetails(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** findDataToRatting end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/findDataProcessToRatting", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findDataProcessToRatting(@RequestBody SearchRequestParamPagging param) {
		logger.info("*********** findDataProcessToRatting start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = stationeryReportService.findDataProcessToRatting(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** findDataProcessToRatting end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findDataToProcess", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findDataToProcess(@RequestBody SearchRequestParamPagging param) {
		logger.info("*********** findDataToProcess start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = stationeryReportService.findDataToProcess(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** findDataToProcess end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/voteHcdv", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> voteHcdv(@RequestBody VoteHCDV voteHCDV) {
		logger.info("*********** findDataToRatting start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, voteHCDV.getSecurityUsername(), voteHCDV.getSecurityPassword())) {
		    resp = stationeryReportService.voteHcdv(voteHCDV);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** findDataToRatting end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/voteVptct", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> voteVptct(@RequestBody VoteHCDV voteHCDV) {
		logger.info("*********** findDataToRatting start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, voteHCDV.getSecurityUsername(), voteHCDV.getSecurityPassword())) {
		    resp = stationeryReportService.voteVptct(voteHCDV);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** findDataToRatting end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/countInfoRequestHcdvDetails", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> countInfoRequestHcdvDetails(@RequestBody VT050006RequestParam param) {
		logger.info("*********** countInfoRequestHcdvDetails start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = stationeryReportService.countInfoRequestHcdvDetails(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** countInfoRequestHcdvDetails end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	// get VPP da dat theo user/thang
	@RequestMapping(value = "/requestByUserName", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> requestByUserName(@RequestBody StationeryParam param, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      param.setStationeryId(userName);
	      resp = stationeryReportService.requestByUserName(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	// định mức theo đơn vị với role HCVPP

	// VPP đã yêu cầu theo đươn vị
	@RequestMapping(value = "/requestByUnitId", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> requestByUnitId(@RequestBody StationeryParam param, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = stationeryReportService.requestByUnitId(param);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	// định mức theo đơn vị đã được cấu hình
	@RequestMapping(value = "/findSpendingLimit", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> requestByUnitId(@RequestBody BaseEntity param, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      int unitId = stationeryReportDAO.getUnitIdByUser(userName);
	      resp = stationeryReportService.findSpendingLimit(unitId);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/findSpendingLimitString", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> requestByUnitIdString(@RequestBody BaseEntity param, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      int unitId = stationeryReportDAO.getUnitIdByUser(userName);
	      String input = String.valueOf(unitId);
	      resp = stationeryReportService.findSpendingLimitString(input);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/downloadTemplateImport", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> downloadTemplateImport(@RequestBody BaseEntity param) {

		HttpHeaders headers = new HttpHeaders();
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    File fileTemp = getFileTemplate("EXCEL_FILE_PATH_IMPORT_TEMPLATE");
	      Workbook wb = getWorkbook(fileTemp);
	      CellStyle cellStyleNumber = wb.createCellStyle();
	      Font font = wb.createFont();
				font.setFontName("Times New Roman");
				font.setFontHeightInPoints((short) 12);
				cellStyleNumber.setFont(font);
				cellStyleNumber.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
				cellStyleNumber.setFillPattern(FillPatternType.NO_FILL);
				cellStyleNumber.setBorderTop(BorderStyle.THIN);
				cellStyleNumber.setBorderRight(BorderStyle.THIN);
				cellStyleNumber.setBorderBottom(BorderStyle.THIN);
				cellStyleNumber.setBorderLeft(BorderStyle.THIN);
				cellStyleNumber.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
	      Sheet sheet = wb.getSheetAt(1);
	      List<PlaceNameAllResponseTemplate> data = new ArrayList<>();
	      data = stationeryReportDAO.getPlaceNameAllTemplate();

	      int rowNum = 2;
	      int numNo =1; 
	      Row row;
	      for (PlaceNameAllResponseTemplate systemCode : data) {
	        row = sheet.createRow(rowNum++);
	        Cell cellNo = row.createCell(0);
	        cellNo.setCellValue(numNo++);
	        Cell cell = row.createCell(1);
	        cell.setCellValue(systemCode.getPlaceName());
	        Cell cellDescption = row.createCell(2);
	        cellDescption.setCellValue(systemCode.getDescription());
	      }

	      sheet = wb.getSheetAt(2);
	      int rowNumSheet2 = 2;
	      int numNoSheet1 =1; 
	      List<StationeryNameAllResponseTemplate> listSystemCode09 = stationeryReportDAO.getStaioneryAllNameTemplate();
	      for (StationeryNameAllResponseTemplate systemCode : listSystemCode09) {
	        row = sheet.createRow(rowNumSheet2++);
	        Cell cellNo = row.createCell(0);
	        cellNo.setCellValue(numNoSheet1++);
	        Cell cell = row.createCell(1);
	        cell.setCellValue(systemCode.getStationeryName());
	        Cell cellCaculationUnit = row.createCell(3);
	        cellCaculationUnit.setCellValue(systemCode.getCalculationUnit());
	        Cell cellPrice = row.createCell(2);
	        cellPrice.setCellValue(systemCode.getUnitPrice());
	        cellPrice.setCellStyle(cellStyleNumber);
	      }

	      sheet = wb.getSheetAt(3);
	      int rowNumCreateSheet3 = 2;
	      for (PlaceNameAllResponseTemplate systemCode : data) {
	        row = sheet.createRow(rowNumCreateSheet3++);

	        Cell cell = row.createCell(1);
	        cell.setCellValue(systemCode.getPlaceName());
	      }
	      int rowNumCreateSheet4 = 2;
	      sheet = wb.getSheetAt(4);
	      for (StationeryNameAllResponseTemplate systemCode : listSystemCode09) {
	        row = sheet.createRow(rowNumCreateSheet4++);

	        Cell cell = row.createCell(1);
	        cell.setCellValue(systemCode.getStationeryName());
	      }
	      
	      wb.setSheetHidden(3, true);
	      wb.setSheetHidden(4, true);
	      
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

	@RequestMapping(value = "/exportExcelEmployee", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Resource> exportExcelEmployee(@RequestBody ReportStationery reportStationery,
			Principal principal) throws Exception {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String loginUserName = (String) oauth.getPrincipal();
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

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
		reportStationery.setPageNumber(-1);
		File file = null; 
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
		InputStreamResource resource = null;
		try {
		  if(isValidated(configProperty, reportStationery.getSecurityUsername(), reportStationery.getSecurityPassword())) {
		    file = stationeryReportService.createExcelEmployee(reportStationery, loginUserName, listRole);
		    resource = new InputStreamResource(new FileInputStream(file));
		    return ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		  }
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_EXPORT_EMPLOYEE"));
			file = resourcex.getFile();
			resource = new InputStreamResource(new FileInputStream(file));
		}

		 return ResponseEntity.ok().headers(headers).contentLength(0)
			        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@RequestMapping(value = "/downloadTemplateImportStationery", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> downloadTemplateImportStationery(@RequestBody BaseEntity param) {

		HttpHeaders headers = new HttpHeaders();
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    File fileTemp = getFileTemplate("EXCEL_FILE_EXPORT_STATIONERY");
	      Workbook wb = getWorkbook(fileTemp);

	      Sheet sheet = wb.getSheetAt(1);
	      List<CaculationUnitTemplacte> data = new ArrayList<>();
	      data = stationeryReportDAO.getCucalationUnitTemplate();

	      int rowNum = 2;
	      int numNo =1;
	      Row row;
	      for (CaculationUnitTemplacte systemCode : data) {
	        row = sheet.createRow(rowNum++);
	        Cell cellNo = row.createCell(0);
	        cellNo.setCellValue(numNo++);
	        
	        Cell cell = row.createCell(1);
	        cell.setCellValue(systemCode.getCaculationUnitName());
	      }

	      ByteArrayOutputStream bos = new ByteArrayOutputStream();
	      wb.write(bos);
	      byte[] barray = bos.toByteArray();
	      return new ResponseEntity<>(barray, headers, HttpStatus.OK);
		  }
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMasterClass", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getMasterClass(@RequestBody BaseEntity param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = stationeryReportService.getMasterClass();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMasterClassWeb", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getMasterClassWeb(@RequestBody BaseEntity param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    resp = stationeryReportService.getMasterClassWeb();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMasterClassByCode", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getMasterClassByCode(@RequestBody ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, resultConfigName.getSecurityUsername(), resultConfigName.getSecurityPassword())) {
		    resp = stationeryReportService.getMasterClassByCode(resultConfigName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/editTypeParam", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> editTypeParam(@RequestBody ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, resultConfigName.getSecurityUsername(), resultConfigName.getSecurityPassword())) {
		    resp = stationeryReportService.editTypeParam(resultConfigName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getParamWebConfigByCode", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getParamWebConfigByCode(@RequestBody ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, resultConfigName.getSecurityUsername(), resultConfigName.getSecurityPassword())) {
		    resp = stationeryReportService.getParamWebConfigByCode(resultConfigName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/insertMasterCodeWeb", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> insertMasterCodeWeb(@RequestBody ResultConfigName resultConfigName, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, resultConfigName.getSecurityUsername(), resultConfigName.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      resultConfigName.setUpdateUser(userName);
	      resp = stationeryReportService.insertMasterCodeWeb(resultConfigName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/editWebParam", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> editWebParam(@RequestBody ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, resultConfigName.getSecurityUsername(), resultConfigName.getSecurityPassword())) {
		    resp = stationeryReportService.editWebParam(resultConfigName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCodeValueByMasterClass", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getCodeValueByMasterClass(@RequestBody ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, resultConfigName.getSecurityUsername(), resultConfigName.getSecurityPassword())) {
		    resp = stationeryReportService.getCodeValueByMasterClass(resultConfigName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/findSpendingLimitQuota", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findSpendingLimitQuota(@RequestBody BaseEntity param, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, 0);
		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      resp = stationeryReportService.getSpendingLimit(userName);
	      double limit = Double.parseDouble(resp.getData().toString());
	      resp.setData(new DataSpendingLimitQuota(limit));
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	//TODO : review and rewrite
	@SuppressWarnings("resource")
  @RequestMapping(value = "/uploadStationeryManagement", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<byte[]> uploadStationeryManagement(Principal principal,
      @RequestPart("file") MultipartFile uploadfile, HttpServletRequest httpServletRequest) {

    HttpHeaders headers = new HttpHeaders();

    try {
      OAuth2Authentication oauth = (OAuth2Authentication) principal;
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
      if(lastRowNum<2){
        headers.set("messageCode", "fileEmpty");
        return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);
      }
      DataFormatter formatter = new DataFormatter();
      
      if (!validateFormat(worksheet)) {
        headers.set("messageCode", "failFormat");
        return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);
      }

      int startRow = 2;
      int startCellIndex;
      int success = 0;

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
          startCellIndex = 1;
          Cell cellStationeryName = row.getCell(startCellIndex++);
          Cell cellUnitPrice = row.getCell(startCellIndex++);
          Cell cellcalCulationUnit = row.getCell(startCellIndex++);
          Cell cellError = row.createCell(startCellIndex++);

          row.getCell(4).setCellStyle(styleRed);
          
          String stationeryName = formatter.formatCellValue(cellStationeryName).trim();
          String culationUnit = formatter.formatCellValue(cellcalCulationUnit).trim();
          
          String unitPrice = formatter.formatCellValue(cellUnitPrice).trim();
          unitPrice = unitPrice.replaceAll(",", "");
          Stationery stationeryInfo = new Stationery();

          stationeryInfo.setUserName(loginUserName);
          List<String> listError = new ArrayList<>();
          if (!validateCellValueStationeryManagement(stationeryInfo, stationeryName, culationUnit, unitPrice,
              cellError, listError)) {
            headers.set("messageCode", "fail");
            StringBuilder errorAll = new StringBuilder();
            for (String error : listError) {
              errorAll.append(error);
              errorAll.append("\n");
            }

            cellError.setCellValue(errorAll.toString());
            if(listError.size() == 1 && listError.get(0) == "") {
              //TODO
            } else {
              count++;
            }
            continue;
          }

          if (vt050001Service.insertStationery(stationeryInfo, null).getStatus() == 2) {
            cellError.setCellValue(messages.getProperty("import.stationery.stationery.exist"));
          }else {
            row.getCell(4).setCellStyle(styleBlack);
            cellError.setCellValue(messages.getProperty("import.stationery.stationery.success"));
            success++;
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
      if (success > 0) {
        headers.set("messageCode", "success");
      }

      return new ResponseEntity<>(barray, headers, HttpStatus.OK);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    headers.set("messageCode", "error");
    return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);
  }

	@RequestMapping(value = "/find-spent-quota-by-user", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findSpentQuotaByUser(@RequestBody InfoEmployee infoEmployee, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, infoEmployee.getSecurityUsername(), infoEmployee.getSecurityPassword())) {
		    resp = stationeryReportService.getSpendingLimit(infoEmployee.getUserName());
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	private boolean validateCellValueStationeryManagement(Stationery stationeryInfo, String stationeryName,
			String culationUnit, String unitPrice, Cell error, List<String> listError) throws Exception {
		if((stationeryName == null || "".equals(stationeryName)) 
				&& (culationUnit == null || "".equals(culationUnit)) 
				&& (unitPrice == null || "".equals(unitPrice))){
			listError.add("");
		} else {
		List<Stationery> listStationery = vt050001DAO.getAllStationery();
		for (Stationery item : listStationery) {
			if ((item.getStationeryName()).equalsIgnoreCase(stationeryName)) {
				listError.add(messages.getProperty("import.stationery.stationeryEmployeeName.exist"));
				break;
			}
		}
		if ( null == stationeryName || "".equals(stationeryName)) {
			listError.add(messages.getProperty("import.stationery.stationeryEmployeeName.required"));
		}else if (stationeryName.length()>50) {
			listError.add(messages.getProperty("import.stationery.stationeryEmployeeName.maxLength"));
		} else {
			stationeryInfo.setStationeryName(stationeryName);
		}
		if (culationUnit == null || "".equals(culationUnit)) {
			listError.add(messages.getProperty("import.stationery.stationeryUnit.required"));
		} else {
			MSystemCodeEntity mSystemCodeEntity = new MSystemCodeEntity();
			mSystemCodeEntity.setCodeName(culationUnit);
			mSystemCodeEntity.setMasterClass("S009");
			List<MSystemCodeEntity> lstMSystemCodeEntity = (List<MSystemCodeEntity>) stationeryReportService
					.findMSystemCodeByCodeName(mSystemCodeEntity).getData();
			if (lstMSystemCodeEntity.isEmpty()) {
				listError.add(messages.getProperty("import.stationery.stationeryUnit.notExist"));
			} else {
				stationeryInfo.setCalUnitId(lstMSystemCodeEntity.get(0).getCodeValue());
				stationeryInfo.setCalUnit(lstMSystemCodeEntity.get(0).getCodeValue());
			}
			//stationeryInfo.setCalUnit(culationUnit);
		}

		if (unitPrice == null || "".equals(unitPrice)) {
			listError.add(messages.getProperty("import.stationery.unitPrice.required"));
		} else {
			try {
				double unitPriceInt = Double.parseDouble(unitPrice);
				if (unitPriceInt <= 0) {
					listError.add(messages.getProperty("import.stationery.unitPrice.negative"));
				}if (unitPriceInt >9000000) {
					listError.add(messages.getProperty("import.stationery.unitPrice.max"));
				}  else {
					stationeryInfo.setUnitPrice(unitPriceInt);
				}
			} catch (Exception e) {
				listError.add(messages.getProperty("import.stationery.unitPrice.number"));
			}
			}
		}
		if (listError.size() != 0) {
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/getParamProcessConfigByCode", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getParamProcessConfigByCode(@RequestBody ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, resultConfigName.getSecurityUsername(), resultConfigName.getSecurityPassword())) {
		    resp = stationeryReportService.getParamProcessConfigByCode(resultConfigName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCountParamProcessConfigByCode", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getCountParamProcessConfigByCode( @RequestBody ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, resultConfigName.getSecurityUsername(), resultConfigName.getSecurityPassword())) {
		    resp = stationeryReportService.getCountParamProcessConfigByCode(resultConfigName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCountMasterClassByCode", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getCountMasterClassByCode(@RequestBody ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, resultConfigName.getSecurityUsername(), resultConfigName.getSecurityPassword())) {
		    resp = stationeryReportService.getCountMasterClassByCode(resultConfigName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCountParamWebConfigByCode", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getCountParamWebConfigByCode( @RequestBody ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, resultConfigName.getSecurityUsername(), resultConfigName.getSecurityPassword())) {
		    resp = stationeryReportService.getCountParamWebConfigByCode(resultConfigName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/editProcessParam", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> editProcessParam(@RequestBody ResultConfigName resultConfigName, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, resultConfigName.getSecurityUsername(), resultConfigName.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
        String userName = (String) oauth.getPrincipal();
        resultConfigName.setUpdateUser(userName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        resultConfigName.setUpdateDate(sdf.format(new Date()));
		    resp = stationeryReportService.editProcessParam(resultConfigName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllProcessConfig", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getAllProcessConfig(@RequestBody BaseEntity info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = stationeryReportService.getAllProcessConfig();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/insertIssuesStationery", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> insertIssuesStationery(@RequestBody RequestParam requestParam, Principal principal) {
		logger.info("*********** issuesServiceService Insert Issues Stationery start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      resp = stationeryReportService.insertIssuesStationery(requestParam, userName, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** issuesServiceService Insert Issues Stationery end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/checkHcdvInStaff", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> checkHcdvInStaff(@RequestBody BaseEntity info, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      resp = stationeryReportService.checkHcdvInStaff(userName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/checkVptctInStaff", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> checkVPCTCTInStaff(@RequestBody BaseEntity info, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      resp = stationeryReportService.checkVPCTCTInStaff(userName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
//	@RequestMapping(value = "/getDishImage", method = RequestMethod.POST, consumes = {MediaType.ALL_VALUE})
//	public byte[] getDishImage(@RequestBody String dishID) {
//		byte[] bytes = null;
//		
//		try {
//			VT020002Dish vT020002Dish = vT020002Service.findDishById(dishID);
//			if (vT020002Dish.getImage() != null) {
//				File file  = new File(vT020002Dish.getImage());
//				if (file.exists()) {
//					Path path = file.toPath();
//					bytes = Files.readAllBytes(path);
//				}
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//
//		return bytes;
//	}
	
	
}
