package com.viettel.vtnet360.stationery.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.stationery.entity.StationeryEmployee;
import com.viettel.vtnet360.stationery.service.GettingItemsByStationeryIds;
import com.viettel.vtnet360.stationery.service.StationeryItem;
import com.viettel.vtnet360.stationery.service.StationeryService;
import com.viettel.vtnet360.stationery.service.StationeryService.StationeryWrapper;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;

@Controller
@RequestMapping("/com/viettel/vtnet360/stationery")
public class StattioneryController extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  @Autowired
  private StationeryService     service;
  
  @Autowired
  private Properties configProperty;


  
  @RequestMapping(value = "/get-stationery-staff", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseEntityBase> getStationeryStaffByUser(@RequestBody BaseEntity info, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        String loginUserName = (String) oauth.getPrincipal();
        resp.setData(service.getStationeryStaffByUser(loginUserName));
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/get-stationery-items", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseEntityBase> getStationeryItems(@RequestBody BaseEntity info, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
        List<StationeryItem> stationeryItemss = service.getStationeryItems();
        resp.setData(stationeryItemss);
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HC_DV')")
  @RequestMapping(value = "/get-items-by-stationerys", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseEntityBase> getItemsByStationeryIds(@RequestBody GettingItemsByStationeryIds getting) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, getting.getSecurityUsername(), getting.getSecurityPassword())) {
        List<StationeryEmployee> stationerys = service.getItemsByStationeryIds(getting);
        resp.setData(stationerys);
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/get-unit-hcdv", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseEntityBase> getUnitHCDV(@RequestBody BaseEntity info, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, 0);
    try {
      if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        String loginUserName = (String) oauth.getPrincipal();
        resp.setData(service.getUnitHCDV(loginUserName));
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      } 
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/get-unit-hcdv-noStaff", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseEntityBase> getUnitHCDVNoStaff(@RequestBody BaseEntity info, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, 0);
    try {
      if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        String loginUserName = (String) oauth.getPrincipal();
        resp.setData(service.getUnitHCDVNoStaff(loginUserName));
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      } 
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/get-unit-vptct", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseEntityBase> getUnitVPTCT(@RequestBody BaseEntity info, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, 0);
    try {
      if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        String loginUserName = (String) oauth.getPrincipal();
        resp.setData(service.getUnitVPTCT(loginUserName));
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  //TODO [Thanh]: review and rewrite
  @PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_HC_DV') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_HCVP_VPP') ")
  @RequestMapping(value = "/uploadStationeryEmployee", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<byte[]> uploadStationeryEmployee(Principal principal,
      @RequestPart("file") MultipartFile uploadfile, HttpServletRequest httpServletRequest) {

    OAuth2Authentication oauth = (OAuth2Authentication) principal;
    Collection<GrantedAuthority> listRole = oauth.getAuthorities();
    String loginUserName = (String) oauth.getPrincipal();
    StationeryWrapper headersWrapper = service.uploadStationery(loginUserName, listRole, uploadfile);

    return new ResponseEntity<>(headersWrapper.getData(), headersWrapper.getHeaders(), HttpStatus.OK);
  }

  
}
