package com.viettel.vtnet360.vt03.vt030003.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.viettel.vtnet360.vt03.vt030000.entity.Employee;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030003.entity.Drive;
import com.viettel.vtnet360.vt03.vt030003.entity.DriveInsert;
import com.viettel.vtnet360.vt03.vt030003.service.VT030003ServiceImpl;
import com.viettel.vtnet360.vt03.vt030004.entity.DriveCar;

/**
 * Class controller for screen VT030003 : add list driver
 * 
 * @author SonVSH 17/09/2018
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt03/vt030003")
public class VT030003Controller extends BaseController {
  private final Logger logger = Logger.getLogger(this.getClass());
  @Autowired
  private VT030003ServiceImpl vt030003ServiceImpl;
  
  @Autowired
  private Properties configProperty;

  /**
   * Select all drivers in list driver
   * 
   * @return ResponseEntityBase
   */
  @RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getAllDriver(@RequestBody BaseEntity info) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
        resp = vt030003ServiceImpl.getAllDriver();
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
      throw (e);
    }

    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }

  /**
   * Select drive list drive
   * 
   * @return ResponseEntityBase
   */
  @RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getListDrive(@RequestBody BaseEntity info) {

    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
        resp = vt030003ServiceImpl.getListDrive();
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
      throw (e);
    }

    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }

  /**
   * Select all drive squad in list squad
   * 
   * @return ResponseEntityBase
   */
  @RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> getListSquad(@RequestBody BaseEntity info) {

    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
        resp = vt030003ServiceImpl.getSquad();
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
      throw (e);
    }

    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }

  /**
   * Select drive with some conditions
   * 
   * * @param drive
   * 
   * @return ResponseEntityBase
   */
  @RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> searchDrives(@RequestBody DriveInsert driveInsert, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, driveInsert.getSecurityUsername(), driveInsert.getSecurityPassword())) {
        resp = vt030003ServiceImpl.searchData(driveInsert,principal);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }

  /**
   * Add new drive
   * 
   * @param drive
   * @return ResponseEntityBase
   * @throws Exception
   */
  @RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> insertDrive(@RequestBody DriveInsert drive, Principal principal)
      throws Exception {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    String userName = null;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    try {
      if(isValidated(configProperty, drive.getSecurityUsername(), drive.getSecurityPassword())) {
        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        userName = (String) oauth.getPrincipal();
        drive.setCreateUser(userName);
        drive.setCreateDate(dateFormat.format(date));

        List<DriveCar> driveCars = new ArrayList<>();
        if (drive.getSelectedLicensePlate() != null) {
          for (int i = 0; i < drive.getSelectedLicensePlate().size(); i++) {
            if (drive.getSelectedLicensePlate().get(i) != null) {
              DriveCar car = new DriveCar();
              car.setCarId(drive.getSelectedLicensePlate().get(i).getCarId());
              driveCars.add(car);
            }

          }
        }
        drive.setSelectedLicensePlate(driveCars);
        resp = vt030003ServiceImpl.insertDrive(drive);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }

  /**
   * Update drive
   * 
   * * @param drive
   * 
   * @return ResponseEntityBase
   */
  @RequestMapping(value = "/06", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> updateDrive(@RequestBody DriveInsert drive, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    String userName = null;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    try {
      if(isValidated(configProperty, drive.getSecurityUsername(), drive.getSecurityPassword())) {
        List<DriveCar> driveCars = new ArrayList<>();
        if (drive.getSelectedLicensePlate() != null) {
          for (int i = 0; i < drive.getSelectedLicensePlate().size(); i++) {
            if (drive.getSelectedLicensePlate().get(i) != null) {
              DriveCar car = new DriveCar();
              /*
               * long millisecond =
               * Long.parseLong(drive.getSelectedLicensePlate().get(i).
               * getCreateDate()); String startDate = dateFormat.format(new
               * Date(millisecond)).toString(); car.setCreateDate(startDate);
               */
              car.setCarId(drive.getSelectedLicensePlate().get(i).getCarId());
              /*
               * car.setCreateUser(drive.getSelectedLicensePlate().get(i).
               * getCreateUser());
               * car.setLicensePlate(drive.getSelectedLicensePlate().get(i).
               * getLicensePlate());
               * car.setProcessStatus(drive.getSelectedLicensePlate().get(i).
               * getProcessStatus());
               * car.setSquadId(drive.getSelectedLicensePlate().get(i).getSquadId(
               * )); car.setSquadName(drive.getSelectedLicensePlate().get(i).
               * getSquadName());
               * car.setUserName(drive.getSelectedLicensePlate().get(i).
               * getUserName()); car.setUpdateDate(null); car.setUpdateUser(null);
               */
              driveCars.add(car);
            }

          }
        }
        drive.setSelectedLicensePlate(driveCars);
        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        userName = (String) oauth.getPrincipal();
        drive.setUpdateUser(userName);
        drive.setUpdateDate(dateFormat.format(date));
        drive.setCreateUser(userName);
        drive.setCreateDate(dateFormat.format(date));
        resp = vt030003ServiceImpl.updateDrive(drive);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }

  /**
   * Delete drive
   * 
   * @param drive
   * @return ResponseEntityBase
   */
  @RequestMapping(value = "/07", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> deleteDrive(@RequestBody Drive drive, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    String userName = null;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    try {
      if(isValidated(configProperty, drive.getSecurityUsername(), drive.getSecurityPassword())) {
        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        userName = (String) oauth.getPrincipal();
        drive.setUpdateUser(userName);
        drive.setUpdateDate(dateFormat.format(date));
        resp = vt030003ServiceImpl.deleteDrive(drive);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }

  /**
   * Find drive squad in list squad
   * 
   * @param squad
   * @return ResponseEntityBase
   */
  @RequestMapping(value = "/08", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> findSquad(@RequestBody VT030000EntityDriveSquad squad, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, squad.getSecurityUsername(), squad.getSecurityPassword())) {
        resp = vt030003ServiceImpl.findSquad(squad, principal);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }

  /**
   * Find employee with role is driver
   * 
   * @param driver
   * @return ResponseEntityBase
   */
  @RequestMapping(value = "/09", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> findDriver(@RequestBody Employee driver) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, driver.getSecurityUsername(), driver.getSecurityPassword())) {
        resp = vt030003ServiceImpl.findDriver(driver);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }

  @RequestMapping(value = "/find-drive", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> findDrive(@RequestBody DriveInsert drive) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, drive.getSecurityUsername(), drive.getSecurityPassword())) {
        resp = vt030003ServiceImpl.findDrive(drive);
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }

}
