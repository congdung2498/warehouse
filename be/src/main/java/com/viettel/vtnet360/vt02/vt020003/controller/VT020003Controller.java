package com.viettel.vtnet360.vt02.vt020003.controller;

import java.io.File;
import java.io.FileInputStream;
import java.security.Principal;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000RequestParam;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003Condition;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003KitchenInfo;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003RequestQuery;
import com.viettel.vtnet360.vt02.vt020003.service.VT020003Service;

/**
 * Class controller for screen VT020003 : setting kitchen
 * 
 * @author DuyNK 10/09/2018
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt02/vt020003")
public class VT020003Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT020003Service vt020003Service;
	
	@Autowired
  private Properties configProperty;

	/**
	 * Select all list chef(QLDV_EMPLOYEE where `ROLE` = 'PMQT_Bep_truong')
	 * 
	 * @return ResponseEntityBase<List<VT020003KitchenPlace>>
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_Bep_truong')")
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020003FindListChef(@RequestBody VT020003RequestQuery query) {
		logger.info("*********** vt020003_01 get list chef start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, query.getSecurityUsername(), query.getSecurityPassword())) {
		    resp = vt020003Service.findListChef(query);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020003_01 get list chef end***********");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	/**
	 * Select all list chef(QLDV_EMPLOYEE where `ROLE` = 'PMQT_Bep_truong')
	 * 
	 * @return ResponseEntityBase<List<VT020003KitchenPlace>>
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_Bep_truong')")
	@RequestMapping(value = "/vt020003FindListChef2", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020003FindListChef2(@RequestBody VT020003RequestQuery query) {
		logger.info("*********** vt020003_01 get list chef start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, query.getSecurityUsername(), query.getSecurityPassword())) {
		    resp = vt020003Service.findListChef2(query);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020003_01 get list chef end***********");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	/**
	 * Select all kitchenInfo(KITCHEN_SETTING)
	 * 
	 * @return ResponseEntityBase<List<VT020003KitchenInfo>>
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_Bep_truong')")
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020003GetKitchenPlace(@RequestBody VT020003Condition condition) {
		logger.info("*********** vt020003_02 get list kitchen info start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    resp = vt020003Service.findListKitchenInfo(condition);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020003_02 get list kitchen info end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	/**
	 * count total record of kitchen
	 * 
	 * @author ThangBT
	 * @param condition
	 * @return number
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_Bep_truong')")
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020003CountTotalRecord(@RequestBody VT020003Condition condition) {
		logger.info("*********** vt020003_03 count total record start ***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    resp = vt020003Service.countTotalRecord(condition);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("*********** vt020003_03 count total record end***********");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	/**
	 * setting kitchen to KITCHEN_SETTING (insert delete update)
	 * 
	 * @param param
	 * @param principal
	 * @return ResponseEntityBase<VT020000ResponseStatus>
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_Bep_truong')")
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020003SettingKitchen(
			@RequestBody VT000000RequestParam<VT020003KitchenInfo> param, Principal principal) {
		logger.info("*********** vt020003_04 setting kitchen start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			// get userName
			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			String userName = (String) oauth.getPrincipal();
			VT020003KitchenInfo kitchenInfo = param.getData();
			if(isValidated(configProperty, kitchenInfo.getSecurityUsername(), kitchenInfo.getSecurityPassword())) {
			  if (param.getAction() == Constant.REQUEST_ACTION_INSERT) {
	        logger.info("*********** vt020003_04 insert kitchen start***********");
	        resp = vt020003Service.insertKitchen(kitchenInfo, userName);
	        logger.info("*********** vt020003_04 insert kitchen end***********");
	      } else if (param.getAction() == Constant.REQUEST_ACTION_UPDATE) {
	        logger.info("*********** vt020003_04 update kitchen start***********");
	        resp = vt020003Service.updateKitchen(kitchenInfo, userName);
	        logger.info("*********** vt020003_04 update kitchen end***********");
	      } else if (param.getAction() == Constant.REQUEST_ACTION_DELETE) {
	        logger.info("*********** vt020003_04 delete kitchen start***********");
	        resp = vt020003Service.deleteKitchen(kitchenInfo.getKitchenID(), userName);
	        logger.info("*********** vt020003_04 delete kitchen end***********");
	      } else {
	        resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	      }
			}
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020003_04 setting kitchen end***********");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	/**
	 * excel
	 * 
	 * @return ResponseEntity<Resource>
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_Bep_truong')")
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> getListReportByUnitExcel(@RequestBody VT020003Condition condition) throws Exception {

		/** Initialization response object */
		HttpHeaders headers = new HttpHeaders();
		File file = null;
		InputStreamResource resource = null;

		logger.info("*********** vt020003_03 get list kitchen info start***********");
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    file = vt020003Service.exportListKitchenInfo(condition);
	      resource = new InputStreamResource(new FileInputStream(file));
	      headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
	      return ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("*********** vt020003_03 get list kitchen info end***********");

		return ResponseEntity.ok().headers(headers).contentLength(0)
		        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

}
