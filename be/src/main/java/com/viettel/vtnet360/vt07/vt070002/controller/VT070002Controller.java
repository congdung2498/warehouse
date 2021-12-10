package com.viettel.vtnet360.vt07.vt070002.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.AuthenticationProviderImpl;
import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ProjectDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.RequestParam;
import com.viettel.vtnet360.vt07.vt070002.entity.SearchDocumentRequest;
import com.viettel.vtnet360.vt07.vt070002.entity.SearchDocumentResponse;
import com.viettel.vtnet360.vt07.vt070002.entity.SearchFolderRequest;
import com.viettel.vtnet360.vt07.vt070002.entity.SearchFolderResponse;
import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;
import com.viettel.vtnet360.vt07.vt070002.service.FolderService;
import com.viettel.vtnet360.vt07.vt070002.service.VT070002Service;

@RestController
@RequestMapping("/com/viettel/vtnet360/vt07/vt070002")
public class VT070002Controller extends BaseController {
	@Autowired
	AuthenticationProviderImpl authenticationProviderImpl;
	@Autowired
	Properties linkTemplateExcel;
	@Autowired
	private Properties configProperty;

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final String MEDIATYPE = "application/octet-stream";

	@Autowired
	VT070002Service vt070002Service;
	@Autowired
	FolderService folderService;
	
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getListTinBox(@RequestBody RequestParam requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		/** Get userName */
		String userName = ((String)oauth.getPrincipal()).toLowerCase();
		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();

		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				if(!vt070002Service.validateRole(requestParam,userRoles)) {
					sendError(entityBase);
					return entityBase;
				}
				entityBase.setData(vt070002Service.getListTinBox(requestParam, userName, userRoles));
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}
	
	private void sendError(ResponseEntityBase resp) {
		resp.setStatus(403);
		resp.setData("Do not have permission to this document");
	}

	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase updateTinBox(@RequestBody TinBoxDetail tinBoxDetail, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);

		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		/** Get userName */
		String userName = ((String) oauth.getPrincipal()).toLowerCase();
		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();

		try {
			if(isValidated(configProperty, tinBoxDetail.getSecurityUsername(), tinBoxDetail.getSecurityPassword())) {
				for (GrantedAuthority temp : userRoles) {
			        // if user had role VIEW ALL TINBOX
			        if (Constant.PMQT_ROLE_TINBOX_UPDATE.equalsIgnoreCase(temp.getAuthority())) {
			        	tinBoxDetail.getRoles().add("edit");
			        }
			        if (Constant.PMQT_ROLE_TINBOX_DELETE.equalsIgnoreCase(temp.getAuthority())) {
			        	tinBoxDetail.getRoles().add("delete");
			        }
				}
				tinBoxDetail.setUpdateUser(userName);
				entityBase.setData(vt070002Service.updateTinBox(tinBoxDetail, userName, userRoles));
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase createTinBox(@RequestBody TinBoxDetail tinBoxDetail, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		/** Get userName */
		String userName = ((String) oauth.getPrincipal()).toLowerCase();
		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		
		try {
			if(isValidated(configProperty, tinBoxDetail.getSecurityUsername(), tinBoxDetail.getSecurityPassword())) {
				vt070002Service.createTinBox(tinBoxDetail, userName, userRoles);
				entityBase.setData(tinBoxDetail);
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getAvailProjects(@RequestBody RequestParam requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		/** Get userRoles */
		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		
		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase.setData(vt070002Service.getAvailObjectInProject(requestParam, userRoles));
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/04/1", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getAvailPackagesInProject(@RequestBody RequestParam requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);				
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		/** Get userRoles */
		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		
		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase.setData(vt070002Service.getAvailObjectInProject(requestParam, userRoles));
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/04/2", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getAvailContracts(@RequestBody RequestParam requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);				
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		/** Get userRoles */
		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		
		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase.setData(vt070002Service.getAvailObjectInProject(requestParam, userRoles));
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/04/3", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getAvailConstructionsInContract(@RequestBody RequestParam requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);				
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		/** Get userRoles */
		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		
		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase.setData(vt070002Service.getAvailObjectInProject(requestParam, userRoles));
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getProjectObjectInFolder(@RequestBody RequestParam requestParam, Principal principal) {
		System.out.println("++++++++++++++++++++++++++++++++++++Get tree ok 1++++++++++++++++++++++++++++++++++");
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase.setData(vt070002Service.getProjectObjectInFolder(requestParam, userRoles));
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}
	//05->10
	@RequestMapping(value = "/10", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase searchListTinBox(@RequestBody TinBoxDetail requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase= vt070002Service.searchListTinBox(requestParam, userRoles);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}
	
	@RequestMapping(value = "/10/1", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getTinBoxInType1Warehouse(@RequestBody TinBoxDetail requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase= vt070002Service.getTinBoxInType1Warehouse(requestParam, userRoles);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}
	
	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findTypeWarehouseDetail(@RequestBody WarehouseDetail requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase= vt070002Service.findTypeWarehouseDetail(requestParam, userRoles);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}
	
	@RequestMapping(value = "/06/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getType1Warehouses(@RequestBody WarehouseDetail requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase= vt070002Service.getType1Warehouses(requestParam, userRoles);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}
	
	@RequestMapping(value = "/07", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase updateTinboxByInformation(@RequestBody TinBoxDetail requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase= vt070002Service.updateTinboxByInformation(requestParam, userRoles);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}


	//05->08
	@RequestMapping(value = "/08", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getProjectDocsByWarehouse(@RequestBody RequestParam requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase = vt070002Service.getProjectDocOfWarehouse(requestParam, userRoles);
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	//06->09
	@RequestMapping(value = "/09", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> exportExcelListDocument(@RequestBody RequestParam condition,
			Principal principal) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource = null;
		if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			String loginUserName = (String) oauth.getPrincipal();
			condition.setCreateUser(loginUserName);
			Collection<GrantedAuthority> listRole = oauth.getAuthorities();
			File file = vt070002Service.createExcelOutputExcel(condition, listRole);
			headers.add("Content-Disposition", "attachment; filename=\"FileName\"");
			try {
				resource = new InputStreamResource(new FileInputStream(file));
				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.parseMediaType(MEDIATYPE)).body(resource);

			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
			}
		}
		Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT70000"));
		File file = resourcex.getFile();
		resource = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok().headers(headers).contentLength(0)
				.contentType(MediaType.parseMediaType(MEDIATYPE)).body(resource);
	}

	@RequestMapping(value = "/11", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getDocumentReport(@RequestBody RequestParam requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			int warehouseId = Integer.parseInt(requestParam.getWarehouseId());
			int pageSize = requestParam.getPageSize();
			int pageNumber = requestParam.getPageNumber();
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase = vt070002Service.getDocumentReport(warehouseId, pageSize, pageNumber, userRoles);
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}

	@RequestMapping(value = "/12", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> exportExcel(@RequestBody RequestParam condition,
			Principal principal) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		InputStreamResource resource = null;
		if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			String loginUserName = (String) oauth.getPrincipal();
			condition.setCreateUser(loginUserName);
			Collection<GrantedAuthority> listRole = oauth.getAuthorities();
			File file = null;
			headers.add("Content-Disposition", "attachment; filename=\"FileName\"");
			try {
				int warehouseId = Integer.parseInt(condition.getWarehouseId());
				file = vt070002Service.exportExcel(warehouseId, listRole);
				resource = new InputStreamResource(new FileInputStream(file));
				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.parseMediaType(MEDIATYPE)).body(resource);

			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
			}
		}
		Resource resourcex = new ClassPathResource(linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT70000"));
		File file = resourcex.getFile();
		resource = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok().headers(headers).contentLength(0)
				.contentType(MediaType.parseMediaType(MEDIATYPE)).body(resource);
	}
	
	@RequestMapping(value = "/13", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase countTinBoxAndOccupiedSlot(@RequestBody RequestParam requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
			try {
				int warehouseId = Integer.parseInt(requestParam.getWarehouseId());
				entityBase = vt070002Service.countTinBoxAndOccupiedSlot(warehouseId, userRoles);
				entityBase.setStatus(Constant.SUCCESS);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return entityBase;
	}
	
	@RequestMapping(value = "/14", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getFolderByQRCode(@RequestBody RequestParam requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		/** Get userName */
		String userName = (String) oauth.getPrincipal();
		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();

		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase.setData(vt070002Service.getFolderByQRCode(requestParam, userName, userRoles));
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}
	
	@RequestMapping(value = "/15", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getProjectTreeInFolder(@RequestBody RequestParam requestParam, Principal principal) {
		System.out.println("++++++++++++++++++++++++++++++++++++Get tree ok 2++++++++++++++++++++++++++++++++++");
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase.setData(vt070002Service.getProjectTreeInFolder(requestParam, userRoles));
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}
	
	@RequestMapping(value = "/16", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase searchDocumentsByKeyword(@RequestBody SearchDocumentRequest requestParam, Principal principal) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.ERROR, null);
		
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		oauth.getPrincipal();
		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		
		try {
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				List<SearchDocumentResponse> respData;
				String keyword = requestParam.getKeyword();
				int pageNumber = requestParam.getPageNumber();
				int pageSize = requestParam.getPageSize();
				
				respData = vt070002Service.searchDocumentsByKeyword(keyword, userRoles, pageNumber, pageSize);
				response.setData(respData);
				response.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setData(e.getMessage());
		}
		
		return response;
	}
	
	@RequestMapping(value = "/88/1", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase searchFolderByNameOrQrCode(@RequestBody SearchFolderRequest req, Principal principal) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		
		if(isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
			try {
				String qrCode = req.getQrCode();
				int pageNumber = req.getPageNumber();
				int pageSize = req.getPageSize();
				List<SearchFolderResponse> respData;
				respData = folderService.searchFolderByNameOrQrCode(qrCode, pageNumber, pageSize, userRoles);
				response.setData(respData);
				response.setStatus(Constant.SUCCESS);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				response.setData(e.getMessage());
			}
		}
		return response;
	}
	
	@RequestMapping(value = "/88/2", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getProjectsInFolder(@RequestBody FolderDetail req, Principal principal) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		if(isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
			try {
				long folderId = req.getFolderId();
				List<ProjectDetail> respData;
				respData = folderService.getProjectsInFolder(folderId, userRoles);
				response.setData(respData);
				response.setStatus(Constant.SUCCESS);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return response;
	}
	
	@RequestMapping(value = "/88/3", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getProjectsNotInFolder(@RequestBody FolderDetail req, Principal principal) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		if(isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
			try {
				long folderId = req.getFolderId();
				List<ProjectDetail> respData;
				respData = folderService.getProjectsNotInFolder(folderId, userRoles);
				response.setData(respData);
				response.setStatus(Constant.SUCCESS);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return response;
	}
	
	@RequestMapping(value = "/88/4", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase getProjectsInTinBox(@RequestBody TinBoxDetail req, Principal principal) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.ERROR, null);
		/** Initialization OAuth2Authentication object */
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		if(isValidated(configProperty, req.getSecurityUsername(), req.getSecurityPassword())) {
			try {
				long tinBoxId = req.getTinBoxId();
				List<ProjectDetail> respData;
				respData = vt070002Service.getProjectsInTinBox(tinBoxId, userRoles);
				response.setData(respData);
				response.setStatus(Constant.SUCCESS);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return response;
	}
	
	@RequestMapping(value = "/11/1", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase searchDocument(@RequestBody RequestParam requestParam, Principal principal) {
		ResponseEntityBase entityBase = new ResponseEntityBase(Constant.ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;

		Collection<GrantedAuthority> userRoles = (Collection<GrantedAuthority>) oauth.getAuthorities();
		try {
			String keyword = requestParam.getName();
			int pageSize = requestParam.getPageSize();
			int pageNumber = requestParam.getPageNumber();
			if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
				entityBase = vt070002Service.searchDocument(keyword, pageSize, pageNumber, userRoles);
				entityBase.setStatus(Constant.SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return entityBase;
	}
	
}
