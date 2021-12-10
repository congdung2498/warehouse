package com.viettel.vtnet360.driving.controller;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.driving.service.BookingService;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;

@RestController
@RequestMapping("/com/viettel/vtnet360/driving")
public class BookingController extends BaseController {
  private final static Logger logger = Logger.getLogger(BookingController.class);
  
  @Autowired
  private BookingService service;
  
  @Autowired
  private Properties configProperty;
  
  @RequestMapping(value = "/get-car-booking-model", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getCarBookingModel(@RequestBody BaseEntity info) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
        resp.setData(service.getCarBookingModel());
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
}
