package com.viettel.vtnet360.vt02.vt020013.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000ResponseEntity;
import com.viettel.vtnet360.vt02.vt020013.entity.ReportByUnitParam;
import com.viettel.vtnet360.vt02.vt020013.entity.VT020013KitchenReport;
import com.viettel.vtnet360.vt02.vt020013.entity.VT020013RequestParam;
import com.viettel.vtnet360.vt02.vt020013.service.VT020013Service;

/**
 * Controller for VT020013 API of vt02 module
 * 
 * @author VinhNVQ 9/10/2018
 *
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt02/vt020013")
public class VT020013Controller extends BaseController {

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());

	/** Service - Business and Data provide */
	@Autowired
	private VT020013Service vT020013Service;

	@Autowired
	Properties linkTemplateExcel;
	
	@Autowired
	Properties messages;
	
	@Autowired
  private Properties configProperty;

	/**
	 * API return KitchenList
	 * 
	 * @author VinhNVQ 9/8/2018
	 * @param query
	 *            ( map data call from client )
	 * @return ResponseEntity<VT020000ResponseEntity>
	 */
	@PreAuthorize("hasAnyAuthority('PMQT_ADMIN') or hasAnyAuthority('LUNCH_MANAGEMENT, REPORT_BY_EMPLOYEE')  or hasAuthority('PMQT_Bep_truong') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL')")
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VT020000ResponseEntity> getListReportByUnit(@RequestBody VT020013RequestParam param,
			Principal principal) {
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
		    reponse.setData(vT020013Service.kitchenList(param, userName, authority));
		  }
		} catch (Exception e) {
			reponse.setStatus(0);
		}

		logger.info("******************** End get report by unit ********************");

		return new ResponseEntity<>(reponse, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('LUNCH_MANAGEMENT, REPORT_BY_EMPLOYEE')  or hasAuthority('PMQT_Bep_truong') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL')")
  @PostMapping(value = "/report-unit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<VT020000ResponseEntity> getListReportByUnitForMobile(@RequestBody ReportByUnitParam param, Principal principal) {
    logger.info("******************** Start get report by unit ********************");
    
    VT020000ResponseEntity reponse = new VT020000ResponseEntity(1, null);
    OAuth2Authentication oauth = (OAuth2Authentication) principal;
    Collection<GrantedAuthority> authority = oauth.getAuthorities();

    try {
      if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
        reponse.setData(vT020013Service.kitchenListForMobile(param, authority));
        reponse.setStatus(1);
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
	 * @author VinhNVQ 9/8/2018
	 * @param query
	 *            ( map data call from client )
	 * @return ResponseEntity<VT020000ResponseEntity>
	 */
	@PreAuthorize("hasAnyAuthority('LUNCH_MANAGEMENT, REPORT_BY_EMPLOYEE')  or hasAuthority('PMQT_Bep_truong') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL')")
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> getListReportByUnitExcel(@RequestBody VT020013RequestParam param,
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
		    file = vT020013Service.kitchenReport(param, userName, authority);
	      resource = new InputStreamResource(new FileInputStream(file));
	      headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
	      return ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		  }
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		logger.info("******************** End get report by unit ********************");

		return ResponseEntity.ok().headers(headers).contentLength(0)
		        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@PreAuthorize("hasAnyAuthority('LUNCH_MANAGEMENT, REPORT_BY_EMPLOYEE')  or hasAuthority('PMQT_Bep_truong') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL')")
	@RequestMapping(value = "/exportReportResponse", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> exportReportResponse(@RequestBody VT020013RequestParam param, Principal principal, HttpServletRequest httpServletRequest)
			throws Exception {

		logger.info("exportReportResponse");

		HttpHeaders headers = new HttpHeaders();

		try {
		  if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
		    File fileTemp = getFileTemplate("EXCEL_FILE_PATH_EXPORT_REPORT_RESPONSE");
	      Workbook wb = getWorkbook(fileTemp);

	      Sheet sheet = wb.getSheetAt(0);
	      List<VT020013KitchenReport> vt020013KitchenReports = vT020013Service.getReportResponse(param);
	      CellStyle style = wb.createCellStyle();
	      style.setWrapText(true);

	      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	      int rowStart = 2;
	      int index = 1;
	      int cellIndex;
	      Row row;
	      int totalNotNull = 0;
	      int numNotNull = 0;
	      for (VT020013KitchenReport kitchenReport : vt020013KitchenReports) {
	        cellIndex = 0;
	        row = sheet.createRow(rowStart++);

	        Cell cell = row.createCell(cellIndex++);
	        cell.setCellValue(index++);

	        cell = row.createCell(cellIndex++);
	        cell.setCellValue(kitchenReport.getKitchenName());

	        cell = row.createCell(cellIndex++);
	        cell.setCellValue(dateFormat.format(kitchenReport.getLunchDate()));

	        cell = row.createCell(cellIndex++);
	        cell.setCellValue(kitchenReport.getFullName());

	        cell = row.createCell(cellIndex++);
	        cell.setCellValue(kitchenReport.getComment());

	        cell = row.createCell(cellIndex++);
	        cell.setCellValue(kitchenReport.getDetailUnit());
	        cell.setCellStyle(style);

	        cell = row.createCell(cellIndex++);
	        if (kitchenReport.getRatting() != null) {
	          cell.setCellValue(kitchenReport.getRatting());
	          totalNotNull = totalNotNull + kitchenReport.getRatting();
	          numNotNull++;
	        }
	      }
	      
	      String evge = "";
	      if (!vt020013KitchenReports.isEmpty()) {
	        if (numNotNull > 0) {
	          DecimalFormat formatter = new DecimalFormat("#,###.00");
	          evge = formatter.format((totalNotNull * 100 )/((float) numNotNull * 5));
	          evge = evge + "%";
	        }
	      }
	      
	      row = sheet.createRow(rowStart++);
	      Cell cell = row.createCell(6);
	      cell.setCellValue(messages.getProperty("export.report.response.everageRatting") + ": " + evge);

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
}
