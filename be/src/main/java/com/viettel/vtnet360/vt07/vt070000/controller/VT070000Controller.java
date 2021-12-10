package com.viettel.vtnet360.vt07.vt070000.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
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
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.AuthenticationProviderImpl;
import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070000.entity.ExportSearchDocumentRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.ImportDataRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.ImportedDataRow;
import com.viettel.vtnet360.vt07.vt070000.entity.RackDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.RequestParam;
import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxDetailsRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxSearchRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxSearchResult;
import com.viettel.vtnet360.vt07.vt070000.entity.UpdateStatusRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseRequestParam;
import com.viettel.vtnet360.vt07.vt070000.entity.search.DocumentDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.DocumentSearchRequest;
import com.viettel.vtnet360.vt07.vt070000.service.ImportDataService;
import com.viettel.vtnet360.vt07.vt070000.service.TinBoxSearchService;
import com.viettel.vtnet360.vt07.vt070000.service.VT070000Service;
import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;

//@PreAuthorize("hasAuthority('ADVANCE_SETTING') and hasAuthority('VERSION_SETTING')")
@RestController
@RequestMapping("/com/viettel/vtnet360/vt07/vt070000")
public class VT070000Controller extends BaseController {
	@Autowired
	AuthenticationProviderImpl authenticationProviderImpl;

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	VT070000Service vt070000Service;

	@Autowired
	TinBoxSearchService tinBoxSearchService;

	@Autowired
	ImportDataService importDataService;

	@Autowired
	Properties linkTemplateExcel;

	@Autowired
	private Properties configProperty;

	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getListWarehouse(@RequestBody RequestParam rackRequestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);

		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		try {
			if (isValidated(configProperty, rackRequestParam.getSecurityUsername(),
					rackRequestParam.getSecurityPassword())) {
				entityBase.setData(vt070000Service.getListWarehouseDetail(rackRequestParam, listRole));
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/01/1", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getListMainWarehouse(@RequestBody RequestParam rackRequestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		try {
			if (isValidated(configProperty, rackRequestParam.getSecurityUsername(),
					rackRequestParam.getSecurityPassword())) {
				entityBase.setData(vt070000Service.getListMainWarehouseDetail(rackRequestParam, listRole));
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase updateWarehouse(@RequestBody WarehouseDetail warehouseDetail, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);

		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		/** Get userName */
		String userName = ((String) oauth.getPrincipal()).toLowerCase();
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();

		try {
			if (isValidated(configProperty, warehouseDetail.getSecurityUsername(),
					warehouseDetail.getSecurityPassword())) {
				entityBase.setData(vt070000Service.updateWarehouseDetail(warehouseDetail, userName, listRole));
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getLocationByQrCode(@RequestBody RequestParam rackRequestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();

		try {
			if (isValidated(configProperty, rackRequestParam.getSecurityUsername(),
					rackRequestParam.getSecurityPassword())) {
				entityBase.setData(vt070000Service.getLocationByQrCode(rackRequestParam, listRole));
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	// 03->10
	@RequestMapping(value = "/10", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getListWarehouse(@RequestBody WarehouseRequestParam warehouseDetail,
			Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		try {
			if (isValidated(configProperty, warehouseDetail.getSecurityUsername(),
					warehouseDetail.getSecurityPassword())) {
				entityBase = vt070000Service.getWarehouseDetail(warehouseDetail, listRole);
			}
			entityBase.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase insertUpdateWarehouse(@RequestBody WarehouseRequestParam warehouseDetail,
			Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		/** Get userName */
		String userName = ((String) oauth.getPrincipal()).toLowerCase();

		try {

			int colNum = Integer.parseInt(warehouseDetail.getColumnNum());
			int rowNum = Integer.parseInt(warehouseDetail.getRowNum());
			int height = Integer.parseInt(warehouseDetail.getHeightNum());

			if (isValidated(configProperty, warehouseDetail.getSecurityUsername(),
					warehouseDetail.getSecurityPassword())) {
				Collection<GrantedAuthority> roleList = oauth.getAuthorities();
				warehouseDetail.setCreateUser(userName);
				if (colNum < 1 || colNum > 100 || rowNum < 1 || rowNum > 100 || height < 1 || height > 5) {
					entityBase.setStatus(-1);
					entityBase.setData("Invalid parameter!");
				} else {
					entityBase = vt070000Service.insertUpdateWarehouse(warehouseDetail, roleList);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase deleteWarehouse(@RequestBody WarehouseRequestParam warehouseDetail, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		/** Get userName */
		String userName = ((String) oauth.getPrincipal()).toLowerCase();
		try {
			if (isValidated(configProperty, warehouseDetail.getSecurityUsername(),
					warehouseDetail.getSecurityPassword())) {
				Collection<GrantedAuthority> roleList = oauth.getAuthorities();
				warehouseDetail.setCreateUser(userName);
				entityBase = vt070000Service.deleteWarehouse(warehouseDetail, roleList);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getListRackSlot(@RequestBody WarehouseRequestParam warehouseDetail, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		/** Get userName */
		String userName = ((String) oauth.getPrincipal()).toLowerCase();
		try {
			if (isValidated(configProperty, warehouseDetail.getSecurityUsername(),
					warehouseDetail.getSecurityPassword())) {
				Collection<GrantedAuthority> roleList = oauth.getAuthorities();
				warehouseDetail.setCreateUser(userName);
				entityBase = vt070000Service.getDiagram(warehouseDetail.getWarehouseId(), roleList);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/07", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getListWarehouseForDropdown(@RequestBody BaseEntity baseEntity, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		try {
			WarehouseRequestParam warehouseDetail = new WarehouseRequestParam("1", false, 0, 10, 0);
			if (isValidated(configProperty, baseEntity.getSecurityUsername(), baseEntity.getSecurityPassword())) {
				entityBase = vt070000Service.getAllListWarehouseDetail(warehouseDetail, roleList);
			}

			entityBase.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/08", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getListSlotOfWarehouse(@RequestBody WarehouseRequestParam warehouseDetail,
			Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		/** Get userName */
		try {
			if (isValidated(configProperty, warehouseDetail.getSecurityUsername(),
					warehouseDetail.getSecurityPassword())) {
				Collection<GrantedAuthority> roleList = oauth.getAuthorities();
				entityBase = vt070000Service.getListSlotOfWarehouse(warehouseDetail, roleList);
			}

			entityBase.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/09", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> exportExcelListTrip(@RequestBody WarehouseRequestParam condition,
			Principal principal) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource = null;
		if (isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			String loginUserName = (String) oauth.getPrincipal();
			condition.setCreateUser(loginUserName);
			Collection<GrantedAuthority> listRole = oauth.getAuthorities();
			File file = vt070000Service.createExcelOutputExcel(condition, listRole);
			headers.add("Content-Disposition", "attachment; filename=\"FileName\"");
			try {
				resource = new InputStreamResource(new FileInputStream(file));
				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
			}
		}
		Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT70000"));
		File file = resourcex.getFile();
		resource = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok().headers(headers).contentLength(0)
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	/**
	 * Get all active warehouse
	 **/
	@RequestMapping(value = "/11", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase getAllActiveWarehouse(@RequestBody BaseEntity req, Principal principal) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		try {

			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {

				resp.setData(tinBoxSearchService.getAllActiveWarehouse(listRole));
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		// return new ResponseEntity<>(resp, HttpStatus.OK);
		return resp;
	}

	@RequestMapping(value = "/12", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase searchDocumentInWarehouse(@RequestBody TinBoxSearchRequest req, Principal principal) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		try {

			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				int warehouseId = req.getWarehouseId();
				String keyword = req.getKeyword();
				boolean byTinBox = req.isByTinBox();
				boolean byFolder = req.isByFolder();
				boolean byProject = req.isByProject();
				boolean byPackage = req.isByPackage();
				boolean byContract = req.isByContract();
				boolean byConstruction = req.isByConstruction();

				List<TinBoxSearchResult> searchResult = tinBoxSearchService.searchTinBox(warehouseId, keyword, byTinBox,
						byFolder, byProject, byPackage, byContract, byConstruction, listRole);

				resp.setData(searchResult);
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		// return new ResponseEntity<>(resp, HttpStatus.OK);
		return resp;
	}

	@RequestMapping(value = "/13", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase getTinBoxDetails(@RequestBody TinBoxDetailsRequest req, Principal principal) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		try {

			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword()) && req.getId() > 0) {
				// resp.setData(tinBoxSearchService.getDocumentsInBox(req.getTinBoxId()));
				resp.setData(tinBoxSearchService.getDocumentsInBoxV2(req, listRole));
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@RequestMapping(value = "/14", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase findSearchingRackResult(@RequestBody RackDetail obj, Principal principal)
			throws Exception {
		ResponseEntityBase response = null;
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		try {
			if (isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
				response = vt070000Service.findRack(obj, listRole);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	@RequestMapping(value = "/15", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase updatePrintTimeRack(@RequestBody RackDetail obj, Principal principal) throws Exception {
		ResponseEntityBase response = null;
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		try {
			if (isValidated(configProperty, obj.getSecurityUsername(), obj.getSecurityPassword())) {
				response = vt070000Service.updatePrintTimeRack(obj, listRole);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	@RequestMapping(value = "/16", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase searchDocumentInWarehouseV2(@RequestBody TinBoxSearchRequest req, Principal principal) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		try {
			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				List<TinBoxDetail> searchResult = tinBoxSearchService.searchTinBox(req, listRole);
				resp.setData(searchResult);
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		// return new ResponseEntity<>(resp, HttpStatus.OK);
		return resp;
	}

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_DA') or hasAuthority('WAREHOUSE_VT')")
	@RequestMapping(value = "/99/1", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase importData(@RequestBody ImportDataRequest req, Principal principal) throws Exception {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();

		try {
			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				List<ImportedDataRow> dataRows = req.getData();
				Object importResult = importDataService.importFullData(dataRows, userRoles);
				response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				response.setData(importResult);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_DA') or hasAuthority('WAREHOUSE_VT')")
	@RequestMapping(value = "/99/2", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase importDocuments(@RequestBody ImportDataRequest req, Principal principal)
			throws Exception {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();

		try {
			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				List<ImportedDataRow> dataRows = req.getData();
				Object importResult = importDataService.importSimpleData(dataRows, userRoles);
				response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				response.setData(importResult);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_DA') or hasAuthority('WAREHOUSE_VT')")
	@RequestMapping(value = "/99/3", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getProjectImportSample(@RequestBody ImportDataRequest req, Principal principal)
			throws Exception {
		HttpHeaders headers = new HttpHeaders();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				String propName = "EXCEL_TEMPLATE_PROJECT_SIMPLE_IMPORT_SAMPLE";
				Workbook wb = importDataService.getExcelTemplate(propName, userRoles);
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

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_DA') or hasAuthority('WAREHOUSE_VT')")
	@RequestMapping(value = "/99/4", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getFolderImportSample(@RequestBody ImportDataRequest req, Principal principal)
			throws Exception {
		HttpHeaders headers = new HttpHeaders();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				String propName1 = "EXCEL_TEMPLATE_FOLDER_FULL_IMPORT_SAMPLE";
				Workbook wb = importDataService.getExcelTemplate(propName1, userRoles);
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

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_DA') or hasAuthority('WAREHOUSE_VT')")
	@RequestMapping(value = "/99/5", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getSimpleUploadReport(@RequestBody ImportDataRequest req, Principal principal)
			throws Exception {
		HttpHeaders headers = new HttpHeaders();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				String propName2 = "EXCEL_TEMPLATE_PROJECT_SIMPLE_IMPORT_RESULT";
				List<ImportedDataRow> dataRows = req.getData();
				Workbook template = importDataService.getExcelTemplate(propName2, userRoles);
				Workbook wb = importDataService.bindResultDataToTemplate(dataRows, template, "simple", userRoles);
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

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('WAREHOUSE_DA') or hasAuthority('WAREHOUSE_VT')")
	@RequestMapping(value = "/99/6", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getFullUploadReport(@RequestBody ImportDataRequest req, Principal principal)
			throws Exception {
		HttpHeaders headers = new HttpHeaders();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				String propName3 = "EXCEL_TEMPLATE_FOLDER_FULL_IMPORT_RESULT";
				List<ImportedDataRow> dataRows = req.getData();
				Workbook template = importDataService.getExcelTemplate(propName3, userRoles);
				Workbook wb = importDataService.bindResultDataToTemplate(dataRows, template, "full", userRoles);
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

	@RequestMapping(value = "/99/7", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase getTinBoxDetails(@RequestBody DocumentSearchRequest req, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> listRole = oauth.getAuthorities();
		try {
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			switch (req.getType()) {
			case 1:
					resp.setData(vt070000Service.getTinBoxByName(req, listRole));
				break;
			case 2:
					resp.setData(vt070000Service.getFolderByName(req, listRole));
				break;
			case 3:
				if (isProject(listRole,resp)) {
					resp.setData(vt070000Service.getProjectByDocName(req, listRole));
				}
				break;
			case 4:
				if (isProject(listRole,resp)) {
					resp.setData(vt070000Service.getPackageByDocName(req, listRole));
				}
				break;
			case 5:
				if (isProject(listRole,resp)) {
					resp.setData(vt070000Service.getContractByDocName(req, listRole));
				}
				break;
			case 6:
				if (isProject(listRole,resp)) {
					resp.setData(vt070000Service.getConstructionByDocName(req, listRole));
				}
				break;
			case 7:
				if (isOfficialDispatch(listRole,resp)) {
					resp.setData(vt070000Service.getOfficialDispatchBytravelsDocName(req, listRole));
				}
				break;
			case 8:
				if (isOfficialDispatch(listRole,resp)) {
					resp.setData(vt070000Service.getOfficialDispatchByIncomingDocName(req, listRole));
				}
				break;
			case 9:
				if (isVoucher(listRole,resp)) {
					resp.setData(vt070000Service.getVoucherByDocNameAndType(req, listRole));
				}
				break;
			case 10:
				if (isVoucher(listRole,resp)) {
					resp.setData(vt070000Service.getVoucherDocByDocNameAndType(req, listRole));
				}
				break;
			case 11:
				if (isVoucher(listRole,resp)) {
					resp.setData(vt070000Service.getPaymentSummaryByDocName(req, listRole));
				}
				break;
			default:
				resp.setStatus(Constant.RESPONSE_ERROR_VALIDATE);
				break;
			}
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	private void sendError(ResponseEntityBase resp) {
		resp.setStatus(403);
		resp.setData("Do not have permission to this document");
	}

	private boolean isOfficialDispatch(Collection<GrantedAuthority> userRoles, ResponseEntityBase resp) {
		if (checkRoleExits("PMQT_ADMIN", userRoles) || checkRoleExits("WAREHOUSE_VT", userRoles)) {
			return true;
		}
		sendError(resp);
		return false;
	}

	private boolean isProject(Collection<GrantedAuthority> userRoles, ResponseEntityBase resp) {
		if (checkRoleExits("PMQT_ADMIN", userRoles) || checkRoleExits("WAREHOUSE_VT", userRoles)
				|| checkRoleExits("WAREHOUSE_DA", userRoles)) {
			return true;
		}
		sendError(resp);
		return false;
	}

	private boolean isVoucher(Collection<GrantedAuthority> userRoles, ResponseEntityBase resp) {
		if (checkRoleExits("PMQT_ADMIN", userRoles) || checkRoleExits("WAREHOUSE_VT", userRoles)
				|| checkRoleExits("WAREHOUSE_TC", userRoles)) {
			return true;
		}
		sendError(resp);
		return false;
	}

	private boolean checkRoleExits(String role, Collection<GrantedAuthority> userRoles) {
		return userRoles.stream().anyMatch(r -> r.getAuthority().equals(role));
	}

	/**
	 * Cap nhat trang thai dong bo/khong dong bo theo loai tai lieu
	 * 
	 * @return
	 */
	@RequestMapping(value = "/17", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase updateStatus(@RequestBody UpdateStatusRequest updateSttRequest, Principal principal) {
		ResponseEntityBase response = null;

		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		/** Get userName */
		String userName = ((String) oauth.getPrincipal()).toLowerCase();
		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		// chua co luong cho tap doan
		try {
			if (isValidated(configProperty, updateSttRequest.getSecurityUsername(),
					updateSttRequest.getSecurityPassword())) {
				response = vt070000Service.updateStatusBySearchType(updateSttRequest);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	@RequestMapping(value = "/99/8", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntityBase getDocDetailByIdAndFolderIdAndType(@org.springframework.web.bind.annotation.RequestParam Long id,
			@org.springframework.web.bind.annotation.RequestParam Long type,@org.springframework.web.bind.annotation.RequestParam Long folderId) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.SUCCESS, null);
		try {
			response.setData(vt070000Service.getDocDetailByIdAndType(id, type,folderId));
		} catch (Exception e) {
			response.setStatus(Constant.ERROR);
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	@RequestMapping(value = "/99/9", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getExportDocument(@RequestBody ExportSearchDocumentRequest req, Principal principal)
			throws Exception {
		HttpHeaders headers = new HttpHeaders();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if (isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
				String propName3 = "EXCEL_TEMPLATE_EXPORT_DOCUMENT";
				List<DocumentDetailSearch> dataRows = req.getData();
				Workbook template = importDataService.getExcelTemplate(propName3, userRoles);
				Workbook wb = importDataService.bindSearchDocumentData(dataRows, template, userRoles);
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
}
