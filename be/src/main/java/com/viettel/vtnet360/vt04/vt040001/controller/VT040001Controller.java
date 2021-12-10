package com.viettel.vtnet360.vt04.vt040001.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000RequestParam;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001InfoToFindEmployee;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001ListEmployee;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001ListPlace;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001ListService;
import com.viettel.vtnet360.vt04.vt040001.service.VT040001Service;

/**
 * Class controller of DMDV VT040001
 * 
 * @author ThangBT 04/10/2018
 *
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt04/vt040001")
@CrossOrigin
public class VT040001Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());
	public static final String MEDIATYPE_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String HEADER_CACHE_CONTROL = "must-revalidate, post-check=0, pre-check=0";

	@Autowired
	private VT040001Service vt040001Service;

	@Autowired
	Properties linkTemplateExcel;

	@Autowired
	Properties messages;
	
	@Autowired
  private Properties configProperty;

	/**
	 * get list places when type character
	 * 
	 * @param placeInfo
	 * @return List<VT040001ListPlace>
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_NV') "
	    + "or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_Bep_truong')")
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListPlace(@RequestBody VT040001ListPlace placeInfo) throws Exception {
	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, placeInfo.getSecurityUsername(), placeInfo.getSecurityPassword())) {
		    reb = vt040001Service.findListPlace(placeInfo);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get list services when type character
	 * 
	 * @param serviceInfo
	 * @return List<VT040001ListService>
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')  or hasAuthority('PMQT_NV') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP')")
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListService(@RequestBody VT040001ListService serviceInfo) throws Exception {
	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, serviceInfo.getSecurityUsername(), serviceInfo.getSecurityPassword())) {
		    reb = vt040001Service.findListService(serviceInfo);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * @param get
	 *            list units when type character, reused class VT040001ListPlace
	 * @return List<VT040001ListPlace>
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP')")
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListUnit(@RequestBody VT040001ListPlace unitInfo) throws Exception {
	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, unitInfo.getSecurityUsername(), unitInfo.getSecurityPassword())) {
		    reb = vt040001Service.findListUnit(unitInfo);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get list receivers when type character
	 * 
	 * @param empInfo
	 * @return List<VT040001ListEmployee>
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP')")
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListReceiver(@RequestBody VT040001ListEmployee empInfo) throws Exception {
	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, empInfo.getSecurityUsername(), empInfo.getSecurityPassword())) {
		    reb = vt040001Service.findListReceiver(empInfo);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * find list receiver of specify service
	 * 
	 * @param empInfo
	 * @return VT040001ListEmployee
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP')")
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListReceiverOfService(@RequestBody VT040001ListEmployee empInfo) throws Exception {
	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, empInfo.getSecurityUsername(), empInfo.getSecurityPassword())) {
		    reb = vt040001Service.findListReceiverOfService(empInfo);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * find list menu service
	 * 
	 * @param serviceInfo
	 * @param principal
	 * @return VT040001ListService
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP')")
	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListMenuService(@RequestBody VT040001ListService serviceInfo, Principal principal) throws Exception {
	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, serviceInfo.getSecurityUsername(), serviceInfo.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = vt040001Service.findListMenuService(serviceInfo, listRole);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return reb;
	}

	@SuppressWarnings("unchecked")
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP')")
	@RequestMapping(value = "/10", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> exportService(@RequestBody VT040001ListService serviceInfo, Principal principal,
			HttpServletRequest request) throws Exception {

		ResponseEntityBase reb = null;

		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		serviceInfo.setStartRow(-1);
		serviceInfo.setRowSize(-1);

		String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT040001");
		File file = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
		InputStreamResource resource = null;
		try {
		  if(isValidated(configProperty, serviceInfo.getSecurityUsername(), serviceInfo.getSecurityPassword())) {
		    reb = vt040001Service.findListMenuService(serviceInfo, listRole);
		    file = writeExcel((List<VT040001ListService>) reb.getData(), excelFilePath, (String) oauth.getPrincipal());
		  }
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT010011"));
			file = resourcex.getFile();
			resource = new InputStreamResource(new FileInputStream(file));
		}
		if(file == null) return ResponseEntity.ok().headers(headers).contentLength(0)
        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	private File writeExcel(List<VT040001ListService> lstService, String excelFilePath, String userName)
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
				sheet.setFitToPage(true);
				sheet.getPrintSetup().setLandscape(true);
				sheet.getPrintSetup().setFitWidth((short) 1);
			    sheet.getPrintSetup().setFitHeight((short) 0);
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
				for (VT040001ListService item : lstService) {
					row = sheet.createRow(rowNum++);
					celNum = 0;

					Cell cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue((double) rowNum - 2);

					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(item.getPlaceName());

					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(item.getServiceName());

					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(item.getDetailUnit());

					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(item.getResponseTime());

					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(item.getStatusService().equals("1") ? "Hoạt động" : "Ngưng hoạt động");

					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					if (item.getReceivers() != null && !"".equals(item.getReceivers())) {
						cell.setCellValue(item.getReceivers());
					}
					
					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(item.getRequireSign()==1 ? "Bắt buộc ký lãnh đạo" : "Không bắt buộc ký lãnh đạo");
				}

				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				}
			}
		}

		return outFile;
	}

	/**
	 * number of total menu service
	 * 
	 * @param serviceInfo
	 * @param principal
	 * @return integer
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP')")
	@RequestMapping(value = "/07", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase countTotalMenuService(@RequestBody VT040001ListService serviceInfo, Principal principal) throws Exception {
	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, serviceInfo.getSecurityUsername(), serviceInfo.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = vt040001Service.countTotalMenuService(serviceInfo, listRole);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * status after doing action
	 * 
	 * @param param
	 * @param principal
	 * @return integer
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP')")
	@RequestMapping(value = "/08", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase handleSetUpService(@RequestBody VT000000RequestParam<VT040001ListService> param,
			Principal principal) throws Exception {
	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			String loginUserName = (String) oauth.getPrincipal();
			VT040001ListService serviceInfo = param.getData();
			serviceInfo.setLoginUserName(loginUserName);
			Collection<GrantedAuthority> listRole = oauth.getAuthorities();
			
			if(isValidated(configProperty, serviceInfo.getSecurityUsername(), serviceInfo.getSecurityPassword())) {
			  if (param.getAction() == Constant.REQUEST_ACTION_INSERT) {
	        reb = vt040001Service.insertServiceAndReceiver(serviceInfo, listRole);
	      } else if (param.getAction() == Constant.REQUEST_ACTION_UPDATE) {
	        reb = vt040001Service.updateServiceAndReceiver(serviceInfo, listRole);
	      } else if (param.getAction() == Constant.REQUEST_ACTION_DELETE) {
	        reb = vt040001Service.deleteServiceAndReceiver(serviceInfo, listRole);
	      }
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get list receivers when type character for modul 4 web (Thong ke yeu cau dich
	 * vu)
	 * 
	 * @param empInfo
	 * @return List<VT040001ListEmployee>
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP')")
	@RequestMapping(value = "/09", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getReceiversForSussgest(@RequestBody VT040001InfoToFindEmployee empInfo,
			Principal principal) throws Exception {
	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, empInfo.getSecurityUsername(), empInfo.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      empInfo.setUserName(userName);
	      Collection<GrantedAuthority> roleList = oauth.getAuthorities();
	      reb = vt040001Service.findListReceiverForSussgest(empInfo, roleList);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@SuppressWarnings("unchecked")
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP')")
	@RequestMapping(value = "/downloadTemplate", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> downloadTemplate(Principal principal, @RequestBody BaseEntity info) {

		HttpHeaders headers = new HttpHeaders();

		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    File fileTemp = getFileTemplate("EXCEL_FILE_PATH_IMPORT_SERVICE_TEMPLATE");
	      Workbook wb = getWorkbook(fileTemp);
	      ResponseEntityBase reb;

	      Sheet sheet = wb.getSheetAt(1);
	      reb = vt040001Service.getListPlace(new VT040001ListPlace());
	      List<VT040001ListPlace> listPlaces = (List<VT040001ListPlace>) reb.getData();

	      int rowNum = 0;
	      Row row;
	      for (VT040001ListPlace place : listPlaces) {
	        row = sheet.createRow(rowNum++);

	        Cell cell = row.createCell(1);
	        cell.setCellValue(place.getPlaceName());
	      }


	      sheet = wb.getSheetAt(2);
	      rowNum = 1;
	      VT040001ListPlace vT040001ListPlace = new VT040001ListPlace();
	      vT040001ListPlace.setIsLimit(1);
	      reb = vt040001Service.findListUnit(vT040001ListPlace);
	      List<VT040001ListPlace> listUnit = (List<VT040001ListPlace>) reb.getData();
	      for (VT040001ListPlace unit : listUnit) {
	        row = sheet.createRow(rowNum++);

	        Cell cell = row.createCell(1);
	        cell.setCellValue(unit.getPlaceId());
	        
	        cell = row.createCell(2);
	        cell.setCellValue(unit.getThreeLevelUnit());
	      }

	      sheet = wb.getSheetAt(4);
	      rowNum = 1;
	      reb = vt040001Service.findListReceiver(new VT040001ListEmployee());
	      List<VT040001ListEmployee> listEmployees = (List<VT040001ListEmployee>) reb.getData();
	      for (VT040001ListEmployee employee : listEmployees) {
	        row = sheet.createRow(rowNum++);

	        Cell cell = row.createCell(1);
	        cell.setCellValue(employee.getReceiverUserName());

	        cell = row.createCell(2);
	        cell.setCellValue(employee.getReceiverName());
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

	/**
	 * 
	 * @param path
	 * @return
	 */
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

	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
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
	@RequestMapping(value = "/uploadService", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> uploadService(Principal principal, @RequestPart("file") MultipartFile uploadfile,
			HttpServletRequest httpServletRequest) {

		HttpHeaders headers = new HttpHeaders();

		try {

			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			Collection<GrantedAuthority> listRole = oauth.getAuthorities();
			String extension = FilenameUtils.getExtension(uploadfile.getOriginalFilename());
			Workbook workbook;
			if ("xls".equals(extension)) {
				workbook = new HSSFWorkbook(uploadfile.getInputStream());
			} else {
				workbook = new XSSFWorkbook(uploadfile.getInputStream());
			}

			Sheet worksheet = workbook.getSheetAt(0);
			int lastRowNum = worksheet.getLastRowNum();
			if(lastRowNum <3){
				headers.set("messageCode", "empty");
				return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);
			}
			DataFormatter formatter = new DataFormatter();
			if(!validateFormat(worksheet)){
				headers.set("messageCode", "failFormat");
				return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);
			}
			
			int startRow = 3;
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

					Cell cellPlace = row.getCell(startCellIndex++);
					Cell cellService = row.getCell(startCellIndex++);
					Cell cellUnit = row.getCell(startCellIndex++);
					Cell cellResponseTime = row.getCell(startCellIndex++);
					Cell cellStatus = row.getCell(startCellIndex++);
					Cell cellReceivers = row.getCell(startCellIndex++);
					Cell cellRequireSign = row.getCell(startCellIndex++);
					Cell cellError = row.createCell(startCellIndex);

					row.getCell(7).setCellStyle(styleRed);
					
					String place = formatter.formatCellValue(cellPlace).trim();
					String service = formatter.formatCellValue(cellService).trim();
					String unit = formatter.formatCellValue(cellUnit).trim();
					String responseTime = formatter.formatCellValue(cellResponseTime).trim();
					String status = formatter.formatCellValue(cellStatus).trim();
					String receivers = formatter.formatCellValue(cellReceivers).trim();
					String requireSign = formatter.formatCellValue(cellRequireSign).trim();
					VT040001ListService serviceInfo = new VT040001ListService();
					List<String> listError =  new ArrayList<>();
					if (!validateCellValue(serviceInfo, place, service, unit, responseTime, status, receivers,requireSign,listError)) {
						headers.set("messageCode", "fail");
						StringBuilder errorAll = new StringBuilder();
						for(String error : listError){
							errorAll.append(error);
							errorAll.append("\n");
						}
						
						cellError.setCellValue(errorAll.toString());
						count++;
						continue;
					}
					serviceInfo.setLoginUserName((String) oauth.getPrincipal());

					vt040001Service.insertServiceAndReceiver(serviceInfo, listRole);
					row.getCell(7).setCellStyle(styleBlack);
					cellError.setCellValue(messages.getProperty("import.stationery.stationery.success"));
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
			if(count == 0){
				headers.set("messageCode", "success");
			}
			
 			return new ResponseEntity<>(barray, headers, HttpStatus.OK);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		headers.set("messageCode", "error");
		return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);
	}

	private String getStatus(String status) {
		if (status.equalsIgnoreCase(messages.getProperty("import.service.status.active"))) {
			return "1";
		}

		return "0";
	}
	private String getRequireSign(String status) {
		if (status.equalsIgnoreCase(messages.getProperty("import.service.requireSign.active"))) {
			return "1";
		}
		
		return "0";
	}

	@SuppressWarnings("unchecked")
	private boolean validateCellValue(VT040001ListService serviceInfo, String place, String service, String unit, String responseTime, String status,
			String receivers,String requireSign, List<String> listError) throws Exception {

		String placeId = null;
		String unitId = null;

		if (place == null || "".equals(place)) {
			listError.add(messages.getProperty("import.service.place.required"));
		} else {
			VT040001ListPlace vt040001ListPlace = new VT040001ListPlace();
			vt040001ListPlace.setPlaceName(place);
			List<VT040001ListPlace> lstPlace = (List<VT040001ListPlace>) vt040001Service.getPlaceByName(vt040001ListPlace)
					.getData();
			if (lstPlace.isEmpty()) {
				listError.add(messages.getProperty("import.service.place.notExist"));
			
			} else {
				placeId = lstPlace.get(0).getPlaceId();
				serviceInfo.setPlaceId(lstPlace.get(0).getPlaceId());
			}
		}

		if (service == null || "".equals(service)) {
			listError.add(messages.getProperty("import.service.servicename.required"));
		} else {
			VT040001ListService vt040001ListService = new VT040001ListService();
			vt040001ListService.setServiceName(service);
				vt040001ListService.setPlaceId(placeId);
			List<VT040001ListService> listServices = (List<VT040001ListService>) vt040001Service
					.getServiceByName(vt040001ListService).getData();
			if (!listServices.isEmpty()) {
				listError.add(messages.getProperty("import.service.servicename.exist"));
			} else {
				serviceInfo.setServiceName(service);
			}
		}
		List<VT040001ListPlace> lstUnit = null;
		if (unit == null || "".equals(unit)) {
			listError.add(messages.getProperty("import.service.unit.required"));
		} else {
			VT040001ListPlace vt040001ListPlace = new VT040001ListPlace();
			vt040001ListPlace.setUnitId(unit);
			lstUnit =  (List<VT040001ListPlace>) vt040001Service.getUnitByName(vt040001ListPlace).getData();
			if (lstUnit.isEmpty()) {
				listError.add(messages.getProperty("import.service.unit.notExist"));
			} else {
				serviceInfo.setUnitId(lstUnit.get(0).getPlaceId());
				unitId = lstUnit.get(0).getPlaceId();
			}
		}
		

		if (responseTime == null || "".equals(responseTime)) {
			listError.add(messages.getProperty("import.service.respinseTime.required"));
		} else {
			try {
				long res = Long.parseLong(responseTime);
				if (res <= 0) {
					listError.add(messages.getProperty("import.service.respinseTime.invalid"));
				}else if(res > (2000000000)){
					listError.add(messages.getProperty("import.service.respinseTime.max"));
				} else{
					serviceInfo.setResponseTime(responseTime);
				}
			} catch (Exception e) {
				listError.add(messages.getProperty("import.service.respinseTime.invalid"));
			}
		}

		if (status == null || "".equals(status)) {
			listError.add(messages.getProperty("import.service.status.required"));
		} else {
			if (!validateStatus(status)) {
				listError.add(messages.getProperty("import.service.status.notExist"));
			}
			serviceInfo.setStatusService(getStatus(status));
		}


		if (receivers == null || "".equals(receivers)) {
			listError.add(messages.getProperty("import.service.receiver.required"));
		} else {
			String[] receiver = receivers.split(",");
			for (String rc : receiver) {
				VT040001ListEmployee employee = new VT040001ListEmployee();
				employee.setUnitId(unitId);
				employee.setReceiverUserName(rc);
				if (((List<VT040001ListEmployee>) vt040001Service.findReceiverByUserName(employee).getData()).isEmpty()) {
					listError.add(messages.getProperty("import.service.receiver.notExist"));
				}
			}
			serviceInfo.setListReceiverUserName(receivers.split(","));
		}


		if (requireSign == null || "".equals(requireSign)) {
			listError.add(messages.getProperty("import.service.requireSign.required"));
		} else {
			if (!validateRequireSign(requireSign)) {
				listError.add(messages.getProperty("import.service.requireSign.notExist"));
			}else {
				serviceInfo.setRequireSign(Integer.parseInt(getRequireSign(requireSign)));
			}
		}
		
		if(listError.size()!=0){
			return false;
		}
		return true;
	}

	private boolean validateStatus(String status) {
		return messages.getProperty("import.service.status.active").equalsIgnoreCase(status)
				|| messages.getProperty("import.service.status.notactive").equalsIgnoreCase(status);
	}
	private boolean validateRequireSign(String requireSign) {
		return messages.getProperty("import.service.requireSign.active").equalsIgnoreCase(requireSign)
				|| messages.getProperty("import.service.requireSign.notactive").equalsIgnoreCase(requireSign);
	}
	private boolean validateFormat(Sheet worksheet) {
		DataFormatter formatter = new DataFormatter();
		
		Row rowTitle = worksheet.getRow(0);
		Cell cellTitle = rowTitle.getCell(0);
		String title = formatter.formatCellValue(cellTitle).trim();
		if(!messages.getProperty("import.service.title").equals(title)){
			return false;
		}
		Row header = worksheet.getRow(2);
		int startCellIndex = 0;

		Cell cellPlace = header.getCell(startCellIndex++);
		Cell cellService = header.getCell(startCellIndex++);
		Cell cellUnit = header.getCell(startCellIndex++);
		Cell cellResponseTime = header.getCell(startCellIndex++);
		Cell cellStatus = header.getCell(startCellIndex++);
		Cell cellReceivers = header.getCell(startCellIndex++);
		Cell cellRequireSign = header.getCell(startCellIndex++);

		if((!messages.getProperty("import.service.header.place").equals(formatter.formatCellValue(cellPlace).trim()))||
			(!messages.getProperty("import.service.header.service").equals(formatter.formatCellValue(cellService).trim()))||
				(!messages.getProperty("import.service.header.unit").equals(formatter.formatCellValue(cellUnit).trim()))||
					(!messages.getProperty("import.service.header.time").equals(formatter.formatCellValue(cellResponseTime).trim()))||
						(!messages.getProperty("import.service.header.status").equals(formatter.formatCellValue(cellStatus).trim()))||
							(!messages.getProperty("import.service.header.stationery").equals(formatter.formatCellValue(cellReceivers).trim()))||
								(!messages.getProperty("import.service.header.requireSign").equals(formatter.formatCellValue(cellRequireSign).trim()))){
			return false;
		}
		Cell error = header.createCell(header.getLastCellNum());
		error.setCellValue(messages.getProperty("import.service.result"));
		error.setCellStyle(header.getCell(header.getLastCellNum() - 2).getCellStyle());
		return true;
	}
}
