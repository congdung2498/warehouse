package com.viettel.vtnet360.vt03.vt030013.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030001.entity.DriveSquad;
import com.viettel.vtnet360.vt03.vt030013.dao.VT030013DAO;
import com.viettel.vtnet360.vt03.vt030013.entity.CarDriverManage;
import com.viettel.vtnet360.vt03.vt030013.entity.CarPlate;
import com.viettel.vtnet360.vt03.vt030013.entity.Driver;
import com.viettel.vtnet360.vt03.vt030013.entity.FreeCar;
import com.viettel.vtnet360.vt03.vt030013.entity.RequestSearch;
import com.viettel.vtnet360.vt03.vt030013.service.VT030013Service;
import com.viettel.vtnet360.vt03.vt030013.service.VT030013ServiceImpl;

@RestController
@RequestMapping("/com/viettel/vtnet360/vt03/vt030013")
public class VT030013Controller extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private final static String[] DRIVER_STATUS = {"Lái xe mới", "", "", "", "Đã xếp xe", "Bắt đầu đi", "Đến nơi", "Về vị trí"};
  private final static String[] CAR_STATUS = {"Xe mới", "", "", "", "Đã xếp xe", "Bắt đầu đi", "Đến nơi", "Về vị trí"};
  
	@Autowired
	private VT030013ServiceImpl vt030013ServiceImpl;

	@Autowired
	private VT030013DAO vt030013dao;
	
	@Autowired
	private VT030013Service vt030013Service;
	
	@Autowired
	Properties linkTemplateExcel;
	
	@Autowired
  private Properties configProperty;

	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getCarDriverManage(@RequestBody RequestSearch rqSearch,
			Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<String> listRole = new ArrayList<>();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		Collection<GrantedAuthority> authority = oauth.getAuthorities();

		for (GrantedAuthority ga : authority) {
			listRole.add(ga.getAuthority());
		}

		try {
		  if(isValidated(configProperty, rqSearch.getSecurityUsername(), rqSearch.getSecurityPassword())) {
		    if (!listRole.contains("PMQT_ADMIN") && !listRole.contains("PMQT_QL") && listRole.contains("PMQT_QL_Doi_xe")) {
		      String squadId = vt030013dao.getSquadIdByQLDX(userName).getSquadId();
		      rqSearch.setSquadOfLead(squadId);
		      rqSearch.setCarLead(true);
		    } 
		    resp = vt030013ServiceImpl.getCarDriverManage(rqSearch);
		  }
		} catch (Exception e) {
		  resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		  logger.error(e.getMessage(), e);
		  throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getLocationList(@RequestBody BaseEntity info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt030013ServiceImpl.getLocationList();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getLicensePlate(@RequestBody CarPlate plate, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
		  if(isValidated(configProperty, plate.getSecurityUsername(), plate.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userManager = (String) oauth.getPrincipal();
	      plate.setUserName(userManager);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities(); 
	      int pageNumber = plate.getPageNumber() * plate.getPageSize();
	      plate.setPageNumber(pageNumber);
	      resp = vt030013ServiceImpl.getLicensePlate(plate, roleList);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getSquadIdByQLDX(@RequestBody BaseEntity info, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<String> listRole = new ArrayList<>();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		DriveSquad squad = new DriveSquad();
		String userName = (String) oauth.getPrincipal();
		System.out.println("USSERNAME = " + userName);
		Collection<GrantedAuthority> authority = oauth.getAuthorities();
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    for (GrantedAuthority ga : authority) {
	        System.out.println("ROLE = " + ga.getAuthority());
	        listRole.add(ga.getAuthority());
	      }

	      if (!listRole.contains("PMQT_ADMIN") && !listRole.contains("PMQT_QL")
	          && listRole.contains("PMQT_QL_Doi_xe")) {
	        squad.setSquadId(vt030013dao.getSquadIdByQLDX(userName).getSquadId());
	        squad.setSquadName(vt030013dao.getSquadIdByQLDX(userName).getSquadName());
	        resp.setData(squad);
	      }
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase getDriverInSquad(@RequestBody Driver driver, Principal principal) {
		logger.info("******************** START APIVT030013_05: GET DRIVER IN THE SQUAD ********************");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
		  if(isValidated(configProperty, driver.getSecurityUsername(), driver.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userManager = (String) oauth.getPrincipal();
	      driver.setUserName(userManager);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      int pageNumber = driver.getPageNumber() * driver.getPageSize();
	      driver.setPageNumber(pageNumber);
	      resp = vt030013Service.getDriverInSquad(driver, roleList);
		  }
		} catch (Exception e) {
			throw (e);
		}
		logger.info("******************** END APIVT030013_05: GET DRIVER IN THE SQUAD ********************");
		return resp;
	}
	
	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Resource> exportExcel(@RequestBody CarDriverManage carDriverManage, Principal principal) throws Exception {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String loginUserName = (String) oauth.getPrincipal();
		File file = vt030013Service.createExcel(carDriverManage, loginUserName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
		InputStreamResource resource = null;
		try {
		  if(isValidated(configProperty, carDriverManage.getSecurityUsername(), carDriverManage.getSecurityPassword())) {
		    resource = new InputStreamResource(new FileInputStream(file));
		  }
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT050015"));
			file = resourcex.getFile();
			resource = new InputStreamResource(new FileInputStream(file));
		}

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@RequestMapping(value = "/07", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getCarDriverManageCount(@RequestBody RequestSearch rqSearch,
			Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		if(isValidated(configProperty, rqSearch.getSecurityUsername(), rqSearch.getSecurityPassword())) {
		  resp = vt030013Service.getCarDriverManageCount(rqSearch);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/free-car", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getListFreeCar(@RequestBody RequestSearch rqSearch, Principal principal) {
	  ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	  if(isValidated(configProperty, rqSearch.getSecurityUsername(), rqSearch.getSecurityPassword())) {
	    resp = vt030013Service.getListFreeCar(rqSearch);
	  }
	  return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/export-file-free-car", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Resource> exportFileFreeCar(@RequestBody RequestSearch rqSearch, Principal principal) throws IOException {
	  
	  ResponseEntityBase reb = null;
	  rqSearch.setPageNumber(-1);
    OAuth2Authentication oauth = (OAuth2Authentication) principal;
    HttpHeaders headers = new HttpHeaders();
    File file = null;
    InputStreamResource resource = null;

    if(isValidated(configProperty, rqSearch.getSecurityUsername(), rqSearch.getSecurityPassword())) {
      reb = vt030013Service.getListFreeCar(rqSearch);
      String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_EXPORT_FREE_CAR");
      headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
      try {
        file = writeExcel((FreeCar) reb.getData(), excelFilePath, (String) oauth.getPrincipal());
        resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_EXPORT_FREE_CAR"));
        file = resourcex.getFile();
        resource = new InputStreamResource(new FileInputStream(file));
      }
    }
    return ResponseEntity.ok().headers(headers).contentLength(0)
        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}
	private File writeExcel(FreeCar freeCar, String excelFilePath, String userName)
      throws IOException {

    File outFile = null;
    File file = null;

    Date date = new Date();
    Calendar cal = GregorianCalendar.getInstance();
    cal.setTime(date);
    String fileName = userName + "_" + cal.get(Calendar.YEAR) + "_" + (cal.get(Calendar.MONTH) + 1) + "_"
        + cal.get(Calendar.DAY_OF_MONTH) + "_" + cal.get(Calendar.HOUR_OF_DAY) + "h" + cal.get(Calendar.MINUTE)
        + "m" + cal.get(Calendar.SECOND) + "s" + cal.get(Calendar.MILLISECOND) + "ms.xlsx";
    try {
      Resource resource = new ClassPathResource(excelFilePath);
      file = resource.getFile();
      String filePath = file.getAbsolutePath();
      outFile = new File(filePath.split("templateExcel")[0] + "saveExcel\\VT04\\" + fileName);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }

    try (FileInputStream inputStream = new FileInputStream(file)) {
      try (SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(inputStream))) {
        SXSSFSheet sheet = workbook.getSheetAt(0);

        CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
        CellStyle combined = sheet.getWorkbook().createCellStyle();
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();

        cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFillPattern(FillPatternType.NO_FILL);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        combined.cloneStyleFrom(cellStyle);
        combined.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));

        // number header of row in excel template
        int rowNum = 2;
        int celNum;
        // fill data
        Row row;
        for (CarDriverManage item : freeCar.getListFreeCar()) {
          row = sheet.createRow(rowNum++);
          celNum = 0;

          Cell cell = row.createCell(celNum++);
          cell.setCellStyle(cellStyle);
          cell.setCellValue((double) rowNum - 2);

          cell = row.createCell(celNum++);
          cell.setCellStyle(cellStyle);
          cell.setCellValue(item.getPlace());

          cell = row.createCell(celNum++);
          cell.setCellStyle(cellStyle);
          cell.setCellValue(item.getSquadName());

          cell = row.createCell(celNum++);
          cell.setCellStyle(cellStyle);
          cell.setCellValue(item.getDriver());

          cell = row.createCell(celNum++);
          cell.setCellStyle(cellStyle);
          cell.setCellValue(item.getPhoneNumber());

          cell = row.createCell(celNum++);
          cell.setCellStyle(cellStyle);
          cell.setCellValue(item.getUnit());

          cell = row.createCell(celNum++);
          cell.setCellStyle(cellStyle);
          cell.setCellValue(DRIVER_STATUS[item.getDriverStatus()]);
          
          cell = row.createCell(celNum++);
          cell.setCellStyle(cellStyle);
          cell.setCellValue(item.getType());
          
          cell = row.createCell(celNum++);
          cell.setCellStyle(cellStyle);
          cell.setCellValue(item.getSeat());
          
          cell = row.createCell(celNum++);
          cell.setCellStyle(cellStyle);
          cell.setCellValue(item.getLicensePlate());
          
          cell = row.createCell(celNum++);
          cell.setCellStyle(cellStyle);
          cell.setCellValue(CAR_STATUS[item.getCarStatus()]);

        }

        try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
          workbook.write(outputStream);
        }
      }
    }

    return outFile;
  }
	
	
}
