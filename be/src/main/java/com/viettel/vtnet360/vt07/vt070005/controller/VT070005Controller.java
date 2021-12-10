package com.viettel.vtnet360.vt07.vt070005.controller;

import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
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
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070005.entity.ImportDataRequest;
import com.viettel.vtnet360.vt07.vt070005.entity.ImportedDataRow;
import com.viettel.vtnet360.vt07.vt070005.service.VT070005ImportDataService;
import com.viettel.vtnet360.vt07.vt070005.service.VT070005Service;

@RestController
@RequestMapping("/com/viettel/vtnet360/vt07/vt070005")
public class VT070005Controller extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private Properties configProperty;

	@Autowired
	VT070005Service vt070005Service;

	@Autowired
	VT070005ImportDataService vt070003ImportDataService;

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_TC')")
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase importFull(@RequestBody ImportDataRequest req, Principal principal) throws Exception {
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

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_TC')")
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getFullImportSample(@RequestBody ImportDataRequest req, Principal principal)
			throws Exception {
		HttpHeaders headers = new HttpHeaders();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				String propName = "EXCEL_TEMPLATE_VOUCHER_FULL_IMPORT_SAMPLE";
				Workbook wb = vt070003ImportDataService.getExcelTemplate(propName, userRoles);
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

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_TC')")
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getFullUploadReport(@RequestBody ImportDataRequest req, Principal principal)
			throws Exception {
		HttpHeaders headers = new HttpHeaders();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				String propName2 = "EXCEL_TEMPLATE_VOUCHER_FULL_IMPORT_RESULT";
				List<ImportedDataRow> dataRows = req.getData();
				Workbook template = vt070003ImportDataService.getExcelTemplate(propName2, userRoles);
				Workbook wb = vt070003ImportDataService.bindResultDataToTemplate(dataRows, template, "full", userRoles);
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

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_TC')")
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase importSimple(@RequestBody ImportDataRequest req, Principal principal) throws Exception {
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

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_TC')")
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getSimpleImportSample(@RequestBody ImportDataRequest req, Principal principal)
			throws Exception {
		HttpHeaders headers = new HttpHeaders();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				String propName = "EXCEL_TEMPLATE_VOUCHER_SIMPLE_IMPORT_SAMPLE";
				Workbook wb = vt070003ImportDataService.getExcelTemplate(propName, userRoles);
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

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_TC')")
	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getSimpleUploadReport(@RequestBody ImportDataRequest req, Principal principal)
			throws Exception {
		HttpHeaders headers = new HttpHeaders();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				String propName2 = "EXCEL_TEMPLATE_VOUCHER_SIMPLE_IMPORT_RESULT";
				List<ImportedDataRow> dataRows = req.getData();
				Workbook template = vt070003ImportDataService.getExcelTemplate(propName2, userRoles);
				Workbook wb = vt070003ImportDataService.bindResultDataToTemplate(dataRows, template, "simple",
						userRoles);
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

	@RequestMapping(value = "/07", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getFolderDetailById(@RequestParam("folderId") long folderId,
			@RequestParam("type") long type) throws Exception {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			resp.setData(vt070005Service.getFolderDetailById(folderId, type));
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/08", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getPaymentSummaryDocBySummaryId(
			@RequestParam("paymentSummaryId") long paymentSummaryId) throws Exception {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			resp.setData(vt070005Service.getPaymentSummaryDocByPaymentSummaryId(paymentSummaryId));
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/09", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getVoucherDocByVoucherId(@RequestParam("voucherId") long voucherId)
			throws Exception {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			resp.setData(vt070005Service.getVoucherDocByVoucherId(voucherId));
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/10", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getVoucherByVoucherId(@RequestParam("voucherId") long voucherId) throws Exception {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			resp.setData(vt070005Service.getVoucherById(voucherId));
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/11", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getPaymentSummaryById(@RequestParam("paymentSummaryId") long paymentSummaryId) throws Exception {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			resp.setData(vt070005Service.getPaymentSummaryById(paymentSummaryId));
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
}
