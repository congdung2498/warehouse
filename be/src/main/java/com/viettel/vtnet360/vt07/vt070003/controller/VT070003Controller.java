package com.viettel.vtnet360.vt07.vt070003.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001ListService;
import com.viettel.vtnet360.vt07.vt070003.service.VT070003ImportDataService;
import com.viettel.vtnet360.vt07.vt070003.entity.FileUploadInfo;
import com.viettel.vtnet360.vt07.vt070003.entity.ImportDataRequest;
import com.viettel.vtnet360.vt07.vt070003.entity.ImportedDataRow;
import com.viettel.vtnet360.vt07.vt070003.service.VT070003Service;

@RestController
@RequestMapping("/com/viettel/vtnet360/vt07/vt070003")
public class VT070003Controller extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private Properties configProperty;

	@Autowired
	VT070003ImportDataService vt070003ImportDataService;
	
	@Autowired
	VT070003Service vt070003Service;

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_VT')")
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase importSimple(@RequestBody ImportDataRequest req, Principal principal)throws Exception {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();

		try {
			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				List<ImportedDataRow> dataRows = req.getData();
				Object importResult = vt070003ImportDataService.importSimpleData(dataRows, userRoles);
				response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				response.setData(importResult);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_VT')")
	@RequestMapping(value="/02", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getSimpleImportSample(@RequestBody ImportDataRequest req, Principal principal) throws Exception{	
		HttpHeaders headers = new HttpHeaders();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try{
		  if(isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
			  String propName = "EXCEL_TEMPLATE_OFFICIAL_DISPATCH_SIMPLE_IMPORT_SAMPLE";
			  Workbook wb = vt070003ImportDataService.getExcelTemplate(propName, userRoles);
			  ByteArrayOutputStream bos = new ByteArrayOutputStream();
		      wb.write(bos);
		      byte[] barray = bos.toByteArray();		      
		      return new ResponseEntity<>(barray, headers, HttpStatus.OK);
		  }
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}	
		return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_VT')")
	@RequestMapping(value="/03", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getSimpleUploadReport(@RequestBody ImportDataRequest req, Principal principal) throws Exception{	
		HttpHeaders headers = new HttpHeaders();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try{
		  if(isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
			  String propName2 = "EXCEL_TEMPLATE_OFFICIAL_DISPATCH_SIMPLE_IMPORT_RESULT";
			  List<ImportedDataRow> dataRows = req.getData();
			  Workbook template = vt070003ImportDataService.getExcelTemplate(propName2, userRoles);
			  Workbook wb = vt070003ImportDataService.bindResultDataToTemplate(dataRows, template, "simple", userRoles);
			  ByteArrayOutputStream bos = new ByteArrayOutputStream();
		      wb.write(bos);
		      byte[] barray = bos.toByteArray();		      
		      return new ResponseEntity<>(barray, headers, HttpStatus.OK);
		  }
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}	
		return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);	
	}
	

	/**
	 * Upload file
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase uploadFile(Principal principal, @RequestPart(name="file") MultipartFile uploadfile,
			@RequestParam("documentId") Long documentId, @RequestParam("documentType") long documentType,
			HttpServletRequest httpServletRequest) {
		HttpHeaders headers = new HttpHeaders();
		ResponseEntityBase response = null;

		try {

			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			Collection<GrantedAuthority> listRole = oauth.getAuthorities();

			String extension = FilenameUtils.getExtension(uploadfile.getOriginalFilename());
			Workbook workbook;
			if (Constant.PDF.equals(extension) || Constant.SVG.equals(extension) || Constant.DOC.equals(extension)
					|| Constant.DOCX.equals(extension) || Constant.XLS.equals(extension) || Constant.XLSX.equals(extension)) {
				// thực thi
				response= vt070003ImportDataService.uploadFile(uploadfile, oauth, listRole, documentId, documentType); 
			} else {
				// thông báo file không đúng định dạng
				response = new ResponseEntityBase(Constant.RESPONSE_FILE_TYPE_NOT_MATCH, null);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return response;
	}
	
	/**
	 * view file
	 */
	@RequestMapping(value = "/viewFile", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase viewFile(Principal principal,@RequestBody FileUploadInfo req,
			HttpServletRequest httpServletRequest) {
		HttpHeaders headers = new HttpHeaders();
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try{
			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			Collection<GrantedAuthority> listRole = oauth.getAuthorities();
			  if(isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				  FileUploadInfo file = vt070003ImportDataService.viewFileUpload(oauth, listRole, req.getDocumentId(), req.getDocumentType());
				  response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				  response.setData(file);
			  }
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}	

		return response;
	}
	
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_VT')")
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase importFull(@RequestBody ImportDataRequest req, Principal principal)throws Exception {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();

		try {
			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				List<ImportedDataRow> dataRows = req.getData();
				Object importResult = vt070003ImportDataService.importFullData(dataRows, userRoles);
				response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				response.setData(importResult);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_VT')")
	@RequestMapping(value="/05", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getFullImportSample(@RequestBody ImportDataRequest req, Principal principal) throws Exception{	
		HttpHeaders headers = new HttpHeaders();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try{
		  if(isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
			  String propName = "EXCEL_TEMPLATE_OFFICIAL_DISPATCH_FULL_IMPORT_SAMPLE";
			  Workbook wb = vt070003ImportDataService.getExcelTemplate(propName, userRoles);
			  ByteArrayOutputStream bos = new ByteArrayOutputStream();
		      wb.write(bos);
		      byte[] barray = bos.toByteArray();		      
		      return new ResponseEntity<>(barray, headers, HttpStatus.OK);
		  }
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}	
		return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_VT')")
	@RequestMapping(value="/06", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getFullUploadReport(@RequestBody ImportDataRequest req, Principal principal) throws Exception{	
		HttpHeaders headers = new HttpHeaders();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try{
		  if(isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
			  String propName2 = "EXCEL_TEMPLATE_OFFICIAL_DISPATCH_FULL_IMPORT_RESULT";
			  List<ImportedDataRow> dataRows = req.getData();
			  Workbook template = vt070003ImportDataService.getExcelTemplate(propName2, userRoles);
			  Workbook wb = vt070003ImportDataService.bindResultDataToTemplate(dataRows, template, "full", userRoles);
			  ByteArrayOutputStream bos = new ByteArrayOutputStream();
		      wb.write(bos);
		      byte[] barray = bos.toByteArray();		      
		      return new ResponseEntity<>(barray, headers, HttpStatus.OK);
		  }
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}	
		return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);	
	}
	
	
	@RequestMapping(value = "/08", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getOfficialDispatchDocByOfficialId(@RequestParam("officialDispatchId") long officialDispatchId) throws Exception {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			resp.setData(vt070003Service.getOfficialDispatchDocById(officialDispatchId));
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/09", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getOfficialDispatchByOfficialId(@RequestParam("officialDispatchId") long officialDispatchId) throws Exception {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			resp.setData(vt070003Service.getOfficialDispatchById(officialDispatchId));
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
}
