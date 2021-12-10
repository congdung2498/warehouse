package com.viettel.vtnet360.vt05.vt050002.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050002.entity.Employee;
import com.viettel.vtnet360.vt05.vt050002.entity.Place;
import com.viettel.vtnet360.vt05.vt050002.entity.Receiver;
import com.viettel.vtnet360.vt05.vt050002.entity.Unit;
import com.viettel.vtnet360.vt05.vt050002.service.VT050002ServiceImpl;

@RestController
@RequestMapping("/com/viettel/vtnet360/vt05/vt050002")
public class VT050002Controller extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private VT050002ServiceImpl vt050002ServiceImpl;
	
	@Autowired
  private Properties configProperty;

	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getReceiver(@RequestBody BaseEntity info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt050002ServiceImpl.getReceiver();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getRole(@RequestBody BaseEntity info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt050002ServiceImpl.getRole();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findEmployee(@RequestBody Employee employee) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, employee.getSecurityUsername(), employee.getSecurityPassword())) {
		    resp = vt050002ServiceImpl.findEmployee(employee);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findUnit(@RequestBody Unit unit) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, unit.getSecurityUsername(), unit.getSecurityPassword())) {
		    resp = vt050002ServiceImpl.findUnit(unit);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> findPlace(@RequestBody Place place) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, place.getSecurityUsername(), place.getSecurityPassword())) {
		    resp = vt050002ServiceImpl.findPlace(place);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchReceiver(@RequestBody Receiver receiver) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, receiver.getSecurityUsername(), receiver.getSecurityPassword())) {
		    resp = vt050002ServiceImpl.searchReceiver(receiver);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/07", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> insertReceiver(@RequestBody Receiver receiver, Principal principal) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
		  if(isValidated(configProperty, receiver.getSecurityUsername(), receiver.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      receiver.setCreateUser(userName);
	      receiver.setCreateDate(dateFormat.format(date));
	      resp = vt050002ServiceImpl.insertReceiver(receiver);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/08", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateReceiver(@RequestBody Receiver receiver, Principal principal) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
		  if(isValidated(configProperty, receiver.getSecurityUsername(), receiver.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      receiver.setUpdateUser(userName);
	      receiver.setUpdateDate(dateFormat.format(date));
	      resp = vt050002ServiceImpl.updateReceiver(receiver);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/09", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> deleteReceiver(@RequestBody Receiver receiver, Principal principal) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		String userName = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
		  if(isValidated(configProperty, receiver.getSecurityUsername(), receiver.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      userName = (String) oauth.getPrincipal();
	      receiver.setUpdateUser(userName);
	      receiver.setUpdateDate(dateFormat.format(date));
	      resp = vt050002ServiceImpl.deleteReceiver(receiver);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/find-place-by-hcdv", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> findPlaceByHCDV(@RequestBody Place place, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, place.getSecurityUsername(), place.getSecurityPassword())) {
        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        String userName = (String) oauth.getPrincipal();
        place.setUsername(userName);
        List<Place> places = vt050002ServiceImpl.findPlaceByHCDV(place);
        resp.setData(places);
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
	
	@RequestMapping(value = "/find-place-by-vptct", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	  public ResponseEntity<ResponseEntityBase> findPlaceByVPTCT(@RequestBody Place place, Principal principal) {
	    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	    try {
	      if(isValidated(configProperty, place.getSecurityUsername(), place.getSecurityPassword())) {
	        OAuth2Authentication oauth = (OAuth2Authentication) principal;
	        String userName = (String) oauth.getPrincipal();
	        place.setUsername(userName);
	        List<Place> places = vt050002ServiceImpl.findPlaceByVPTCT(place);
	        resp.setData(places);
	        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
	      }
	    } catch (Exception e) {
	      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	      logger.error(e.getMessage(), e);
	      throw (e);
	    }
	    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	  }
	
	
}
