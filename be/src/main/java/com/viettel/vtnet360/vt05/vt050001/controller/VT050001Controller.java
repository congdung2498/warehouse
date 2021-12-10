package com.viettel.vtnet360.vt05.vt050001.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.stationery.service.ComonParam;
import com.viettel.vtnet360.stationery.service.StationeryWrapper;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050001.entity.CalculationUnit;
import com.viettel.vtnet360.vt05.vt050001.entity.LimitSpend;
import com.viettel.vtnet360.vt05.vt050001.entity.Stationery;
import com.viettel.vtnet360.vt05.vt050001.service.VT050001Service;
import com.viettel.vtnet360.vt05.vt050003.entity.StationeryItemWrapper;

@RestController
@RequestMapping("/com/viettel/vtnet360/vt05/vt050001")
public class VT050001Controller extends BaseController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private VT050001Service vt050001Service;
	
	@Autowired
  private Properties configProperty;

	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getAllStationery(@RequestBody BaseEntity info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt050001Service.getAllStationery();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getCalculationUnit(@RequestBody BaseEntity info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt050001Service.getCalulationUnit();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findCalculationUnit(@RequestBody CalculationUnit calUnit) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, calUnit.getSecurityUsername(), calUnit.getSecurityPassword())) {
		    resp = vt050001Service.findCalulationUnit(calUnit);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchStationery(@RequestBody Stationery stationery) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, stationery.getSecurityUsername(), stationery.getSecurityPassword())) {
		    resp = vt050001Service.searchStationery(stationery);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/searchCountStationery", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchCountStationery(@RequestBody Stationery stationery) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, stationery.getSecurityUsername(), stationery.getSecurityPassword())) {
		    resp = vt050001Service.searchCountStationery(stationery);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> insertStationery( @RequestBody StationeryItemWrapper param, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		Stationery stationery = param.getStationery();
		byte[] bytes = null;
		if(param.getImage() != null) {
		  Decoder decoder = Base64.getMimeDecoder();
	    bytes = decoder.decode(param.getImage().split(",")[1]);
		}
		
		try {
		  if(isValidated(configProperty, stationery.getSecurityUsername(), stationery.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      stationery.setUserName(userName);
	      stationery.setCreateDate(dateFormat.format(date));
	      resp = vt050001Service.insertStationery(stationery, bytes);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateStationery(@RequestBody StationeryItemWrapper param, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
		  Stationery stationery = param.getStationery();
		  if(isValidated(configProperty, stationery.getSecurityUsername(), stationery.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      
	      byte[] bytes = null;
	      if(param.getImage() != null) {
	        Decoder decoder = Base64.getMimeDecoder();
	        bytes = decoder.decode(param.getImage().split(",")[1]);
	      }
	      
	      stationery.setUserName(userName);
	      stationery.setUpdateDate(dateFormat.format(date));
	      resp = vt050001Service.updateStationery(stationery, bytes);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/07", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> deleteStationery(@RequestBody Stationery stationery, Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
		  if(isValidated(configProperty, stationery.getSecurityUsername(), stationery.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      stationery.setUpdateUser(userName);
	      stationery.setUpdateDate(dateFormat.format(date));
	      resp = vt050001Service.deleteStationery(stationery);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteSelectStationery", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> deleteSelectStationery(@RequestBody StationeryWrapper stationery,
			Principal principal) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
		  OAuth2Authentication oauth = (OAuth2Authentication) principal;
		  userName = (String) oauth.getPrincipal();
		  for(int i =0 ; i< stationery.getStationerys().size() ; i++){
		    stationery.getStationerys().get(i).setUpdateUser(userName);
		    stationery.getStationerys().get(i).setUpdateDate(dateFormat.format(date));
		    resp = vt050001Service.deleteStationery(stationery.getStationerys().get(i));
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/08", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getLimit(@RequestBody BaseEntity info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt050001Service.getLimit();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/09", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateLimit(@RequestBody LimitSpend limit) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, limit.getSecurityUsername(), limit.getSecurityPassword())) {
		    resp = vt050001Service.updateLimit(limit);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get-stationery-item-image", method = RequestMethod.POST, produces  = { MediaType.IMAGE_PNG_VALUE })
	public byte[] getStationeryItemImage(@RequestBody ComonParam param) {
	  byte[] bytes = null;

	  try {
	    if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
	      String directory = new File("").getAbsoluteFile().getParent() + File.separator + Constant.STATIONERY_IMAGE_PATH;
	      String subPath = directory + File.separator + param.getSearch() + ".png";
	      File file = new File(subPath);
	      if (file.exists()) {
	        Path path = file.toPath();
	        bytes = Files.readAllBytes(path);
	      }
	    }
	  } catch (Exception e) {
	    logger.error(e.getMessage(), e);
	  }

	  return bytes;
	}
}
