package com.viettel.vtnet360.checking.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.checking.service.CheckingModel;
import com.viettel.vtnet360.checking.service.CheckingSearching;
import com.viettel.vtnet360.checking.service.CheckingService;
import com.viettel.vtnet360.checking.service.Condition;
import com.viettel.vtnet360.checking.service.entity.Checking;
import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRpMgAp;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqMgAp;

@RestController
@RequestMapping("/com/viettel/vtnet360/checking")
public class CheckingController extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  
  @Autowired
  private CheckingService service;
  
  @Autowired
  private Properties configProperty;
  
  
  @PostMapping(value = "/get-checking-model", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseEntityBase> getCheckingModel(@RequestBody CheckingSearching searchingModel, 
            Principal principal, OAuth2Authentication authentication) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, searchingModel.getSecurityUsername(), searchingModel.getSecurityPassword())) {
        CheckingModel model = service.findListInOut(searchingModel.getConfig(), principal, authentication);
        response.setData(model);
        response.setStatus(Constant.SUCCESS);
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return new ResponseEntity<ResponseEntityBase>(response, HttpStatus.OK);
  }
  
  @PostMapping(value = "/approve-by-manager", consumes = MediaType.ALL_VALUE) 
  public ResponseEntity<ResponseEntityBase> approveByManager(@RequestBody VT010000EntityRqMgAp requestParam, Principal principal) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
        VT010000EntityRpMgAp approveObject = service.approveByManager(requestParam, principal);
        response.setStatus(approveObject.getStatus());
        response.setData(approveObject.getNames());
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return new ResponseEntity<ResponseEntityBase>(response, HttpStatus.OK);
  }
  
  @PostMapping(value = "/update-status-approve", consumes = MediaType.ALL_VALUE) 
  public ResponseEntity<ResponseEntityBase> updateStatusApproveChecking(@RequestBody VT010000EntityRqMgAp requestParam, Principal principal) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
        VT010000EntityRpMgAp approveObject = service.updateManagerApprove(requestParam, principal);
        response.setStatus(approveObject.getStatus());
        response.setData(approveObject.getNames());
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return new ResponseEntity<ResponseEntityBase>(response, HttpStatus.OK);
  }
  
  @PostMapping(value = "/get-selecting-model", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getSelectingModel(@RequestBody BaseEntity requestParam) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
        response.setData(service.getSelectingModel());
        response.setStatus(Constant.SUCCESS);
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return new ResponseEntity<ResponseEntityBase>(response, HttpStatus.OK);
  }
  
  @PostMapping(value = "/employee-autocomplete", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> employeeAutocomplete(@RequestBody Condition search) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, search.getSecurityUsername(), search.getSecurityPassword())) {
        response.setData(service.employeeAutocomplete(search.getSearch()));
        response.setStatus(Constant.SUCCESS);
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return new ResponseEntity<ResponseEntityBase>(response, HttpStatus.OK);
  }
  
  @PostMapping(value = "/manager-employee-autocomplete", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> managerEmployeeAutocomplete(@RequestBody Condition search) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, search.getSecurityUsername(), search.getSecurityPassword())) {
        response.setData(service.managerEmployeeAutocomplete(search.getSearch()));
        response.setStatus(Constant.SUCCESS);
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return new ResponseEntity<ResponseEntityBase>(response, HttpStatus.OK);
  }
  
  @PostMapping(value = "/get-with-employee", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getWithEmployees(@RequestBody Condition search) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, search.getSecurityUsername(), search.getSecurityPassword())) {
        response.setData(service.getWithEmployees(search.getUserNames()));
        response.setStatus(Constant.SUCCESS);
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return new ResponseEntity<ResponseEntityBase>(response, HttpStatus.OK);
  }
  
  @PostMapping(value = "/get-checking-model-web", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseEntityBase> getCheckingModelWeb(@RequestBody CheckingSearching searchingModel, Principal principal) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, searchingModel.getSecurityUsername(), searchingModel.getSecurityPassword())) {
        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        String userName = (String) oauth.getPrincipal();
        Collection<GrantedAuthority> listRole = oauth.getAuthorities();
        CheckingModel model = service.getCheckingModel(searchingModel, userName, listRole);
        response.setData(model);
        response.setStatus(Constant.SUCCESS);
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return new ResponseEntity<ResponseEntityBase>(response, HttpStatus.OK);
  }
  
  @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseEntityBase> createChecking(@RequestBody Checking requestParam, Principal principal) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
        response.setData("The checking is inserted!");
        response.setStatus(service.createChecking(requestParam, principal).getStatus());
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return new ResponseEntity<ResponseEntityBase>(response, HttpStatus.OK);
  }
  
  @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseEntityBase> updateChecking(@RequestBody Checking requestParam, Principal principal) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
        response.setData("The checking is updated!");
        response.setStatus(service.updateChecking(requestParam, principal).getStatus());
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return new ResponseEntity<ResponseEntityBase>(response, HttpStatus.OK);
  }
  
  @PostMapping(value = "/update-more-time", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseEntityBase> updateMoreTimeChecking(@RequestBody Checking requestParam, Principal principal) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, requestParam.getSecurityUsername(), requestParam.getSecurityPassword())) {
        response.setData("The checking is updated!");
        response.setStatus(service.updateMoreTimeChecking(requestParam, principal).getStatus());
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return new ResponseEntity<ResponseEntityBase>(response, HttpStatus.OK);
  }
  
  @PostMapping(value = "/get-checking-model-by-barcode", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getCheckingByBarcode(@RequestBody Condition barcode ) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, barcode.getSecurityUsername(), barcode.getSecurityPassword())) {
        response.setData(service.getCheckingByBarcode(barcode.getBarcode()));
        response.setStatus(Constant.SUCCESS);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      response.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);
    }
    return new ResponseEntity<ResponseEntityBase>(response, HttpStatus.OK);
  }
}
