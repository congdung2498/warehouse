package com.viettel.vtnet360.kitchen.controller;

import java.security.Principal;
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

import com.viettel.vtnet360.checking.service.Condition;
import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.kitchen.dto.GettingLunch;
import com.viettel.vtnet360.kitchen.dto.LunchDTO;
import com.viettel.vtnet360.kitchen.dto.MenuSetting;
import com.viettel.vtnet360.kitchen.dto.SearchingLunchModel;
import com.viettel.vtnet360.kitchen.dto.SearchingMenuByDate;
import com.viettel.vtnet360.kitchen.dto.UpdatingAllLunch;
import com.viettel.vtnet360.kitchen.service.LunchService;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000RequestParam;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006ParamRequest;

@RestController
@RequestMapping("/com/viettel/vtnet360/kitchen/lunch")
public class LunchController extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  
  
  @Autowired
  private LunchService service;
  
  @Autowired
  private Properties configProperty;
  
  
  @RequestMapping(value = "/get-lunch-by-id-mobile", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getLunchByIdForMobile(@RequestBody GettingLunch lunch) {
    logger.info("*********** Get lunch by id start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, lunch.getSecurityUsername(), lunch.getSecurityPassword())) {
        resp.setData(service.getLunchByIdForMobile(lunch));
        resp.setStatus(Constant.SUCCESS);
        logger.info("*********** Get lunch by id  end - OK***********");
      }
    } catch (Exception e) {
      logger.info("*********** Get lunch by id  - FAIL***********");
      logger.error(e);
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/get-parent-unit", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getExistedLunchMessage(@RequestBody Condition condition) {
    logger.info("*********** Get parent unit id start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
        resp.setData(service.getParentUnitId(condition.getSearch()));
        resp.setStatus(Constant.SUCCESS);
        logger.info("*********** Get parent unit id  end - OK***********");
      }
    } catch (Exception e) {
      logger.info("*********** Get parent unit id  - FAIL***********");
      logger.error(e);
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/get-existed-lunch-msg", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getExistedLunchMessage(@RequestBody VT020006ParamRequest param) {
    logger.info("*********** Get lunch model by period start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
        resp.setData(service.getExistedLunchMessage(param));
        resp.setStatus(Constant.SUCCESS);
        logger.info("*********** Get lunch model by period end - OK***********");
      }
    } catch (Exception e) {
      logger.info("*********** Get lunch model by period - FAIL***********");
      logger.error(e);
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/get-lunch-model-by-period", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getLunchModelByPeriod(@RequestBody SearchingLunchModel searchingConfig) {
    logger.info("*********** Get lunch model by period start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, searchingConfig.getSecurityUsername(), searchingConfig.getSecurityPassword())) {
        resp.setData(service.getLunchModelByPeriod(searchingConfig));
        resp.setStatus(Constant.SUCCESS);
        logger.info("*********** Get lunch model by period end - OK***********");
      }
    } catch (Exception e) {
      logger.info("*********** Get lunch model by period - FAIL***********");
      logger.error(e);
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/get-lunch-model-by-day", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getLunchModelByDay(@RequestBody SearchingLunchModel searchingConfig) {
    logger.info("*********** Get lunch model by day start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, searchingConfig.getSecurityUsername(), searchingConfig.getSecurityPassword())) {
        resp.setData(service.getLunchModelByDay(searchingConfig));
        resp.setStatus(Constant.SUCCESS);
        logger.info("*********** Get lunch model by day end - OK***********");
      }
    } catch (Exception e) {
      logger.info("*********** Get lunch model by day - FAIL***********");
      logger.error(e);
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/get-menu-by-date", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getMenuSettingByDate(@RequestBody SearchingMenuByDate searching) {
    logger.info("*********** Get menu setting by date start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, searching.getSecurityUsername(), searching.getSecurityPassword())) {
        List<MenuSetting> menuSettings = service.getMenuSettingByDate(searching);
        resp.setData(menuSettings);
        resp.setStatus(Constant.SUCCESS);
        logger.info("*********** Get menu setting by date end - OK***********");
      }
    } catch (Exception e) {
      logger.info("*********** Get menu setting by date - FAIL***********");
      logger.error(e);
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/update-all-lunch", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> updateAllLunch(@RequestBody UpdatingAllLunch config) {
    logger.info("*********** Update all lunch start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, config.getSecurityUsername(), config.getSecurityPassword())) {
        service.updateAllLunch(config);
        resp.setData("All lunch is updated!");
        resp.setStatus(Constant.SUCCESS);
        logger.info("*********** Update all lunch end - OK***********");
      }
    } catch (Exception e) {
      logger.info("*********** Update all lunch - FAIL***********");
      logger.error(e);
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/update-voting", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> updateVoting(@RequestBody LunchDTO lunch) {
    logger.info("*********** Update voting lunch start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, lunch.getSecurityUsername(), lunch.getSecurityPassword())) {
        service.updateVoting(lunch);
        resp.setData("The voting of lunch is updated!");
        resp.setStatus(Constant.SUCCESS);
        logger.info("*********** Update voting lunch end - OK***********");
      }
    } catch (Exception e) {
      logger.info("*********** Update voting lunch - FAIL***********");
      logger.error(e);
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/create-lunch-by-day", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> createLunchByDay(
      @RequestBody VT000000RequestParam<VT020006ParamRequest> param, Principal principal) {
    logger.info("***********  setting lunch by day start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      VT020006ParamRequest paramData = param.getData();
      if (paramData == null) {
        paramData = new VT020006ParamRequest();
      }
      if(isValidated(configProperty, paramData.getSecurityUsername(), paramData.getSecurityPassword())) {
        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        String userName = (String) oauth.getPrincipal();
        paramData.setUserName(userName);
        resp = service.createLunchByDay(paramData);
        logger.info("*********** setting lunch by day end - OK ***********");
      }
    } catch (Exception e) {
      logger.info("*********** setting lunch by day : FAILED***********");
      logger.error(e.getMessage());
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/create-lunch-by-period", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> createLunchByPeriodic( @RequestBody VT020006ParamRequest param, Principal principal) {
    logger.info("***********  setting lunch by period start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
        resp = service.createLunchByPeriodic(param);
        logger.info("*********** setting lunch by period end - OK ***********");
      }
    } catch (Exception e) {
      logger.info("*********** setting lunch by period : FAILED***********");
      logger.error(e.getMessage());
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/delete-lunch-by-period", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> deleteLunchByPeriodic(@RequestBody VT020006ParamRequest param, Principal principal) {
    logger.info("***********  setting lunch by period start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, param.getSecurityUsername(), param.getSecurityPassword())) {
        resp = service.deleteLunchByPeriodic(param);
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        logger.info("*********** setting lunch by period end - OK ***********");
      }
    } catch (Exception e) {
      logger.info("*********** setting lunch by period : FAILED***********");
      logger.error(e.getMessage());
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  //TODO: for phongld2
  @RequestMapping(value = "/create-lunch-by-day-mocha", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> createLunchByDayMocha(@RequestBody VT020006ParamRequest param) {
    logger.info("***********  setting lunch by day start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      resp = service.createLunchByDay(param);
      logger.info("*********** setting lunch by day end - OK ***********");
    } catch (Exception e) {
      logger.info("*********** setting lunch by day : FAILED***********");
      logger.error(e.getMessage());
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/delete-lunch-mocha", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> deleteLunchMocha(@RequestBody UpdatingAllLunch config) {
    logger.info("*********** Update all lunch start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      service.deleteLunch(config);
      resp.setData("All lunch is updated!");
      resp.setStatus(Constant.SUCCESS);
      logger.info("*********** Update all lunch end - OK***********");
    } catch (Exception e) {
      logger.info("*********** Update all lunch - FAIL***********");
      logger.error(e);
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/create-lunch-by-period-mocha", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> createLunchByPeriodicMocha(@RequestBody VT020006ParamRequest param) {
    logger.info("***********  setting lunch by period start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      resp = service.createLunchByPeriodic(param);
      logger.info("*********** setting lunch by period end - OK ***********");
    } catch (Exception e) {
      logger.info("*********** setting lunch by period : FAILED***********");
      logger.error(e.getMessage());
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
}
