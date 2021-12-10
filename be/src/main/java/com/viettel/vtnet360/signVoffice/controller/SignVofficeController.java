package com.viettel.vtnet360.signVoffice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

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

import com.google.gson.Gson;
import com.viettel.vtnet360.common.CASign.SecurityPassword;
import com.viettel.vtnet360.common.CASign.SignVofficeCA;
import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.signVoffice.entity.ReportSynthetic;
import com.viettel.vtnet360.signVoffice.entity.SignVoffice;
import com.viettel.vtnet360.signVoffice.entity.SignVofficeEntity;
import com.viettel.vtnet360.signVoffice.entity.SignVofficeSearch;
import com.viettel.vtnet360.signVoffice.service.SignVofficeService;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000SubmitVOfficeInput;
import com.viettel.vtnet360.vt00.vt000000.service.VOfficeAutoSignWebservice;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010Condition;

@RestController
@RequestMapping("/com/viettel/vtnet360/signVoffice")
public class SignVofficeController extends BaseController {
	
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SignVofficeService signVofficeService;

	@Autowired
	Properties linkTemplateExcel;
	
	@Autowired
	Properties messages;
	
	@Autowired
  private Properties configProperty;


	@RequestMapping(value = "/find-sign-voffice", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findSignVoffice(@RequestBody SignVofficeSearch signVofficeSearch, Principal principal) throws Exception {

	  ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);;
	  OAuth2Authentication oauth = (OAuth2Authentication) principal;
	  Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	  String loginUserName = (String) oauth.getPrincipal();
	  try {
	    reb = signVofficeService.findListSignVoffice(signVofficeSearch,listRole,loginUserName);
	  } catch (Exception e) {
	    logger.error(e.getMessage(), e);
	  }

		return reb;
	}
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP')")
	@RequestMapping(value = "/export-signVoffice", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> exportSignVoffice(@RequestBody SignVofficeSearch signVofficeSearch, Principal principal,
			HttpServletRequest request) throws Exception {

		ResponseEntityBase reb = null;
		System.out.println(request.getServletContext().getRealPath(""));
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		String loginUserName = (String) oauth.getPrincipal();
		signVofficeSearch.setStartRow(-1);
		signVofficeSearch.setRowSize(-1);
		reb = signVofficeService.findListSignVoffice(signVofficeSearch,listRole,loginUserName);

		String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_EXPORT_SIGN_VOFFICE");
		File file = writeExcel((SignVoffice) reb.getData(), excelFilePath, (String) oauth.getPrincipal());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
		InputStreamResource resource = null;
		try {
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT010011"));
			file = resourcex.getFile();
			resource = new InputStreamResource(new FileInputStream(file));
		}

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

	}
	
	private File writeExcel(SignVoffice signVoffice, String excelFilePath, String userName)
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
				String statusSign= messages.getProperty("export.sign.status");
				String[] status =statusSign.split(",");
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
				for (SignVofficeEntity item : signVoffice.getListSignVoffice()) {
					row = sheet.createRow(rowNum++);
					celNum = 0;

					Cell cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue((double) rowNum - 2);

					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(item.getDocumentCode());

					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(item.getContent());

					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(item.getFullName());

					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(item.getLastSignFullName());

					cell = row.createCell(celNum++);
					cell.setCellStyle(combined);
					cell.setCellValue(item.getSignTime());

					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(status[item.getStatus()-1]);
					
					cell = row.createCell(celNum++);
					cell.setCellStyle(combined);
					cell.setCellValue(item.getCreateTime());
					
					cell = row.createCell(celNum++);
					cell.setCellStyle(combined);
					cell.setCellValue(item.getApproveTime());
					
					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(item.getType()==1 ? "Báo cáo chi tiết":"báo cáo tổng hợp");
					
					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(item.getSignComment());
					
					cell = row.createCell(celNum++);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(removeAccent(item.getContent())+".pdf");

				}

				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				}
			}
		}

		return outFile;
	}
	public String removeAccent(String s) {
	  if(s== null){
	    return "";
	  }
	  String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
	  Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	  return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D');
	}
	
	 @RequestMapping(value = "/insert-sign-voffice", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntityBase insertSignVoffice(@RequestBody SignVofficeEntity signVofficeEntity, Principal principal) throws Exception {
	   ResponseEntityBase reb = null;
	   try {
	     reb = signVofficeService.insertSignVoffice(signVofficeEntity, principal);
	     if(reb.getStatus()==1){
	       if(signVofficeEntity.getType()==1){
	         VT040010Condition condition = new VT040010Condition();
	         condition.setDetailSignVofficeId(signVofficeEntity.getSignVofficeId());
	         condition.setStartDate(signVofficeEntity.getFromDate());
	         condition.setEndDate(signVofficeEntity.getToDate());
	         condition.setListUnitId(signVofficeEntity.getListUnitId());
	         signVofficeService.findIssueServiceByDetailSignVoffice(condition,principal);	
	       }else if(signVofficeEntity.getType()==2){
	         VT040010Condition condition = new VT040010Condition();
	         condition.setSyntheticSignVofficeId(signVofficeEntity.getSignVofficeId());
	         condition.setStartDate(signVofficeEntity.getFromDate());
	         condition.setEndDate(signVofficeEntity.getToDate());
	         condition.setListUnitId(signVofficeEntity.getListUnitId());
	         signVofficeService.exportStationeryBySyntheticSignVoffice(condition,principal);	
	       }
	     }
	   } catch (Exception e) {
	     logger.error(e.getMessage(), e);
	   }
	   return reb;
	 }
	
	@RequestMapping(value = "/export-stationery-Synthetic", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> exportStationeryBySyntheticSignVoffice(@RequestBody ReportSynthetic reportSynthetic, Principal principal)
			throws Exception {
		
		File file= null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
		InputStreamResource resource = null;
		try {
			if(reportSynthetic.getStatus() ==1 || reportSynthetic.getStatus() ==2 || reportSynthetic.getStatus() ==5 || reportSynthetic.getStatus() ==4){
				file = new File(convertUrl()+"/documentVO/"+reportSynthetic.getSyntheticSignVofficeId()+".out.pdf");
				if(file.exists() && !file.isDirectory()) { 
					resource = new InputStreamResource(new FileInputStream(file));
				}	
			}else if (reportSynthetic.getStatus() ==3) {
				file = new File(convertUrl()+"/documentVO/"+reportSynthetic.getSyntheticSignVofficeId()+".out.out.pdf");
				if(file.exists() && !file.isDirectory()) { 
					resource = new InputStreamResource(new FileInputStream(file));
				}else {
					VT000000SubmitVOfficeInput VT000000SubmitVOfficeInput= new VT000000SubmitVOfficeInput();
					VT000000SubmitVOfficeInput.setTransCode(reportSynthetic.getTransCode());
					VT000000SubmitVOfficeInput.setSignVofficeId(reportSynthetic.getSyntheticSignVofficeId());
					boolean result = new VOfficeAutoSignWebservice().getFileSign( VT000000SubmitVOfficeInput);
					file = new File(convertUrl()+"/documentVO/"+reportSynthetic.getSyntheticSignVofficeId()+".out.out.pdf");
					if(file.exists() && !file.isDirectory()) { 
						resource = new InputStreamResource(new FileInputStream(file));
					}
				}
			}
			if(file != null) {
				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		
		}
		return ResponseEntity.ok().headers(headers).contentLength(0)
		        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}
	
	@RequestMapping(value = "/update-sign-voffice", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase updateSignVoffice(@RequestBody SignVofficeEntity signVofficeEntity)
			throws Exception {
		
		ResponseEntityBase reb = null;
		
		try {
			reb = signVofficeService.updateSignVoffice(signVofficeEntity);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return reb;
	}
	
	@RequestMapping(value = "/export-stationery-detail", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> exportStationeryByDetailSignVoffice(@RequestBody ReportSynthetic reportSynthetic, Principal principal)
			throws Exception {
		File file= null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
		InputStreamResource resource = null;
		try {
			if(reportSynthetic.getStatus() ==1 || reportSynthetic.getStatus() ==2){
				file = new File(convertUrl()+"/documentVO/"+reportSynthetic.getDetailSignVofficeId()+".out.pdf");
				if(file.exists() && !file.isDirectory()) { 
					resource = new InputStreamResource(new FileInputStream(file));
				}	
			}else if (reportSynthetic.getStatus() ==3) {
				file = new File(convertUrl()+"/documentVO/"+reportSynthetic.getDetailSignVofficeId()+".out.out.pdf");
				if(file.exists() && !file.isDirectory()) { 
					resource = new InputStreamResource(new FileInputStream(file));
				}else {
					VT000000SubmitVOfficeInput VT000000SubmitVOfficeInput= new VT000000SubmitVOfficeInput();
					VT000000SubmitVOfficeInput.setTransCode(reportSynthetic.getTransCode());
					VT000000SubmitVOfficeInput.setSignVofficeId(reportSynthetic.getDetailSignVofficeId());
					boolean result = new VOfficeAutoSignWebservice().getFileSign( VT000000SubmitVOfficeInput);
					file = new File(convertUrl()+"/documentVO/"+reportSynthetic.getDetailSignVofficeId()+".out.out.pdf");
					if(file.exists() && !file.isDirectory()) { 
						resource = new InputStreamResource(new FileInputStream(file));
					}
				}
			}
			if(file != null){
			return ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return ResponseEntity.ok().headers(headers).contentLength(0)
		        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}
	
  @RequestMapping(value = "/get-file-sign", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Resource> getFileSign(@RequestBody SignVofficeEntity signVofficeEntity, Principal principal)
      throws Exception {
    System.out.println("kientn: get-file-sign");
    File file = null;
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
    InputStreamResource resource = null;
    try {
      SignVofficeEntity signVoffice = (SignVofficeEntity) signVofficeService
          .findSignVofficeById(signVofficeEntity.getSignVofficeId()).getData();
      System.out.println(new Gson().toJson(signVoffice));
      if (signVoffice == null) {
        resource = null;
      } else if (signVoffice.getStatus() == 1 || signVoffice.getStatus() == 2 || signVoffice.getStatus() == 4) {
        file = new File(convertUrl() + "/documentVO/" + signVoffice.getSignVofficeId() + ".out.pdf");
        if (file.exists() && !file.isDirectory()) {
          resource = new InputStreamResource(new FileInputStream(file));
        }
      } else if (signVoffice.getStatus() == 3 || signVoffice.getStatus() == 5) {
        file = new File(convertUrl() + "/documentVO/" + signVoffice.getSignVofficeId() + ".out.out.pdf");
        System.out.println("file1: " + file);
        if (file.exists() && !file.isDirectory()) {
          System.out.println("file.exists()");
          resource = new InputStreamResource(new FileInputStream(file));
        } else {
          System.out.println("SignVofficeCA.getFileSign");
          SignVofficeCA.getFileSign(signVoffice);
          file = new File(convertUrl() + "/documentVO/" + signVoffice.getSignVofficeId() + ".out.out.pdf");
          if (file.exists() && !file.isDirectory()) {
            resource = new InputStreamResource(new FileInputStream(file));
          }
        }
      }
      if (file != null) {
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return ResponseEntity.ok().headers(headers).contentLength(0)
        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
  }
	
	@PostMapping(value = "download-detail-file", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> downloadDetailFile(@RequestBody SignVofficeEntity signVOffice) {
	  HttpHeaders headers = new HttpHeaders();
	  long data = 0;
	  Resource resource = null;
	  
	  String path = convertUrl() + File.separator + "documentVO" + File.separator + signVOffice.getSignVofficeId() + ".xlsx";
	  File file = null; 
	  headers.add("Content-Disposition", String.format("attachment; filename=\"" + "FileName" + "\""));
	  try {
	    file = new File(path);
	    resource = new InputStreamResource(new FileInputStream(file));
	    data = file.length();
	  } catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    e.printStackTrace();
	  }

	  return ResponseEntity.ok().headers(headers).contentLength(data)
	      .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}
	
	private String convertUrl(){
	  int lastRegex = System.getProperty("catalina.home").lastIndexOf(File.separator);
	  String url=System.getProperty("catalina.home").substring(0, lastRegex);

	  File theDir = new File(url+"/documentVO");
	  if (!theDir.exists()) {
	    theDir.getName();
	    try{
	      theDir.mkdir();
	    } 
	    catch(SecurityException se){
	    }        

	  }
	  return url;
	}
	
	public static void main(String[] args) {
    String pss = "ORJS/ZjzfCqqx8cWJfzFAZ3LAts=";
    System.out.println(SecurityPassword.decryptPassword(pss));
  }
}
