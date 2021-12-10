package com.viettel.vtnet360.otp;

import java.security.MessageDigest;
import java.security.Principal;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
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

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;

@RestController
@RequestMapping("/com/viettel/vtnet360/otp")
public class OTPController {
  private final Logger logger = Logger.getLogger(this.getClass());
  
  @Autowired
  private OTPService service;
  
  
  @RequestMapping(value = "/sending", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> sendOTP(Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      OAuth2Authentication oauth = (OAuth2Authentication) principal;
      String userName = (String) oauth.getPrincipal();
      //resp.setData(service.sendOTP(userName));
      service.sendOTP(userName);
      resp.setData("");
      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
    } catch (Exception e) {
      logger.error(e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/valid", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> validOTP(@RequestBody OTP otp, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      OAuth2Authentication oauth = (OAuth2Authentication) principal;
      String userName = (String) oauth.getPrincipal();
      otp.setUsername(userName);
      int status = service.isValidOTP(otp);
      resp.setStatus(1);      
      String data = "";
      MessageDigest md = MessageDigest.getInstance("MD5");
      
      if(status == 1) {
    	  data = otp.getOtp() + userName + "11" + userName;
      }
      else if(status == 2){
    	  resp.setStatus(2);
    	  data = otp.getOtp() + userName + "22"+ userName;
      }
      else {
    	  resp.setStatus(3);
    	  data = otp.getOtp() + userName + "33" + userName;
      }
      
      md.update(data.getBytes());
      byte[] digest = md.digest();
      String myHash = DatatypeConverter
        .printHexBinary(digest).toUpperCase();
      
      resp.setData(myHash);
    } catch (Exception e) {
      logger.error(e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/capcha", method = RequestMethod.POST, consumes = { MediaType.ALL_VALUE })
  public byte[] getDishImage(Principal principal) {
    byte[] bytes = null;
    try {
      OAuth2Authentication oauth = (OAuth2Authentication) principal;
      String userName = (String) oauth.getPrincipal();
      bytes = service.getCaptchaImage(userName).getImage();
    } catch (Exception e) {
      logger.error(e);
    }
    return bytes;
  }
  
  @RequestMapping(value = "/capcha-valid", method = RequestMethod.POST, consumes = { MediaType.ALL_VALUE })
  public ResponseEntity<ResponseEntityBase> validCapcha(@RequestBody OTP otp, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      OAuth2Authentication oauth = (OAuth2Authentication) principal;
      String userName = (String) oauth.getPrincipal();
      otp.setUsername(userName);
      int status = service.isValidCapcha(otp);
      resp.setStatus(status);
      if(status != 1) {
        byte[] bytes = service.getCaptchaImage(userName).getImage();
        resp.setData(bytes);
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }
}
