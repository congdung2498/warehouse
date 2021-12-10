package com.viettel.vtnet360.vt03.vt030003.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030000.entity.Employee;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030002.entity.Cars;
import com.viettel.vtnet360.vt03.vt030003.dao.VT030003DAO;
import com.viettel.vtnet360.vt03.vt030003.entity.Drive;
import com.viettel.vtnet360.vt03.vt030003.entity.DriveInsert;
import com.viettel.vtnet360.vt03.vt030003.entity.ListDrive;
import com.viettel.vtnet360.vt03.vt030004.entity.DriveCar;

/**
 * Class serviceImpl for screen VT030003 : add list driver
 * 
 * @author SonVSH 17/09/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030003ServiceImpl implements VT030003Service {
  private final Logger logger = Logger.getLogger(this.getClass());
  @Autowired
  private VT030003DAO vt030003dao;

  /**
   * Select all drivers in list driver
   * 
   * @return ResponseEntityBase
   */
  @Override
  @Transactional(readOnly = true)
  public ResponseEntityBase getAllDriver() {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<Employee> listdriver = null;
    try {
      listdriver = vt030003dao.getAllDriver();
      resp.setData(listdriver);
    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      logger.error(e.getMessage(), e);
      throw (e);
    }

    return resp;

  }

  /**
   * Find employee with role is driver
   * 
   * @param driver
   * @return ResponseEntityBase
   */
  @Override
  public ResponseEntityBase findDriver(Employee driver) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<Employee> listdriver = null;
    try {
      listdriver = vt030003dao.findDriver(driver);
      resp.setData(listdriver);
    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      logger.error(e.getMessage(), e);
      throw (e);
    }

    return resp;
  }

  /**
   * Select drive list drive
   * 
   * @return ResponseEntityBase
   */
  @Override
  public ResponseEntityBase getListDrive() {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<DriveInsert> listdrive = null;
    try {
      listdrive = vt030003dao.getListDrive();
      resp.setData(listdrive);
    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return resp;
  }

  /**
   * Select all drive squad in list squad
   * 
   * @return ResponseEntityBase
   */
  @Override
  public ResponseEntityBase getSquad() {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<VT030000EntityDriveSquad> listsquad = null;
    try {
      listsquad = vt030003dao.getSquad();
      resp.setData(listsquad);
    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return resp;
  }

  /**
   * Add new drive
   * 
   * @param drive
   * @return ResponseEntityBase
   */
  @Override
  public ResponseEntityBase insertDrive(DriveInsert drive) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<DriveInsert> listdrive = null;
    int duplicate = 0;
    DriveInsert driveDetail = vt030003dao.detailDrive(drive);
    if (driveDetail != null && driveDetail.getDeleteFlag() == 0) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      resp.setData(null);
    } else if (driveDetail != null && driveDetail.getDeleteFlag() == 1) {
      drive.setDeleteFlag(0);
      drive.setDriveId(driveDetail.getDriveId());
      try {
        if (drive.getSelectedLicensePlate().size() != 0) {
          vt030003dao.deleteMatchedCar(new Drive(drive.getSquadId(), drive.getUserName()));
          vt030003dao.insertDriveCar(drive);
        }
        int i = vt030003dao.updateDeleteFlag(drive);
        if (i == 1) {
          resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        } else {
          resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
        }
      } catch (Exception e) {
        resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
        resp.setData(null);
        logger.error(e.getMessage(), e);
        throw (e);
      }
      
    } else if (driveDetail == null) {

      try {
        if (drive.getSelectedLicensePlate().size() != 0) {
          vt030003dao.insertDriveCar(drive);
        }
        int i = vt030003dao.insertDrive(drive);
        if (i == 1) {
          resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        } else {
          resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
        }
      } catch (Exception e) {
        resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
        resp.setData(null);
        logger.error(e.getMessage(), e);
        throw (e);
      }
    }
    return resp;
  }

  /**
   * Update drive
   * 
   * @param drive
   * @return ResponseEntityBase
   */
  @Override
  public ResponseEntityBase updateDrive(DriveInsert drive) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    int processStatus = -1;
    processStatus = vt030003dao.getProcessStatus(drive.getDriveId());
    if (processStatus != 7 && processStatus != 0) {
      resp.setStatus(Constant.STATUS_IS_RUNING_CAR);
      return resp;
    }
    // listdrive = vt030003dao.getListDrive();
    int status = vt030003dao.checkDeleted(new Drive(drive.getDriveId()));
    if (status == 1) {
      resp.setStatus(Constant.ERROR_UPDATE);
      return resp;
    }

    DriveInsert driveInsert = vt030003dao.findDriveById(drive);
    if (driveInsert == null) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      return resp;
    } else {
      if (driveInsert.getUserName().equals(drive.getUserName())
          && driveInsert.getSquadId().equals(drive.getSquadId())) {
        try {
          int i = vt030003dao.updateDrive(drive);
          if (i == 1) {
            vt030003dao.deleteMatchedCar(new Drive(drive.getSquadId(), drive.getUserName()));
            if (drive.getSelectedLicensePlate().size() != 0) {
              vt030003dao.insertDriveCar(drive);
            }
            resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
            return resp;
          } else {
            resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
            return resp;
          }
        } catch (Exception e) {
          resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
          resp.setData(null);
        }
      } else {
        DriveInsert driveDetail = vt030003dao.detailDrive(drive);
        if (driveDetail != null) {
          resp.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
          return resp;
        } else {
          try {
            int i = vt030003dao.updateDrive(drive);
            if (i == 1) {
              vt030003dao.deleteMatchedCar(new Drive(driveInsert.getSquadId(), driveInsert.getUserName()));
              if (drive.getSelectedLicensePlate().size() != 0) {
                vt030003dao.insertDriveCar(drive);
              }
              resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
              return resp;
            } else {
              resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
              return resp;
            }
          } catch (Exception e) {
            resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
            resp.setData(null);
          }
        }
      }
    }

    /*
     * for (DriveInsert item : listdrive) { if
     * (item.getUserName().equals(drive.getOldValue())) { if
     * (drive.getOldValue().equals(drive.getUserName())) checked = 0; else
     * checked = 1; break; } } if (checked == 1) { for (DriveInsert item :
     * listdrive) { if (item.getUserName().equals(drive.getUserName())) {
     * duplicate = 1; break; } } } if (duplicate == 1) {
     * resp.setStatus(Constant.RESPONSE_STATUS_ERROR); resp.setData(null); }
     * else { try { int i = vt030003dao.updateDrive(drive); if (i == 1) {
     * resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS); return resp; } else {
     * resp.setStatus(Constant.RESPONSE_STATUS_ERROR); return resp; } } catch
     * (Exception e) { resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
     * resp.setData(null); } }
     */
    return resp;
  }

  /**
   * Delete drive
   * 
   * @param drive
   * @return ResponseEntityBase
   */
  @Override
  public ResponseEntityBase deleteDrive(Drive drive) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    int status = vt030003dao.checkDeleted(drive);
    if (status == 1) {
      resp.setStatus(Constant.ERROR_UPDATE);
      return resp;
    }
    try {
      if (vt030003dao.checkActiveDrive(drive.getDriveId()) == 0) {
        int i = vt030003dao.deleteDrive(drive);
        if (i == 1) {
          vt030003dao.deleteMatchedCar(drive);
          resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
          return resp;
        } else {
          resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
          return resp;
        }
      } else {
        resp = new ResponseEntityBase(Constant.STATUS_IS_RUNING_DRIVE, null);
      }

    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      resp.setData(null);
    }
    return resp;
  }

  /**
   * Find drive squad in list squad
   * 
   * @param squad
   * @return ResponseEntityBase
   */
  @Override
  public ResponseEntityBase findSquad(VT030000EntityDriveSquad squad, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<VT030000EntityDriveSquad> listsquad = null;
    OAuth2Authentication oauth = (OAuth2Authentication) principal;
    String userName = (String) oauth.getPrincipal();
    squad.setUserName(userName);
    Collection<GrantedAuthority> listRole = oauth.getAuthorities();
    if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
      squad.setLoginRole(Constant.PMQT_ROLE_ADMIN);
    } else if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_DOIXE))) {
      squad.setLoginRole(Constant.PMQT_ROLE_MANAGER_DOIXE);
    }
    try {
      listsquad = vt030003dao.findSquad(squad);
      resp.setData(listsquad);
    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return resp;
  }

  /**
   * Select drive with some conditions
   * 
   * @param drive
   * @return ResponseEntityBase
   */
  @Override
  public ResponseEntityBase searchData(DriveInsert driveInsert, Principal principal) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<Drive> listDrive = null;
    List<String> listIdCar = null;
    try {
      OAuth2Authentication oauth = (OAuth2Authentication) principal;
      String userName = (String) oauth.getPrincipal();
      driveInsert.setUserLogin(userName);
      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
      if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
        driveInsert.setLoginRole(Constant.PMQT_ROLE_ADMIN);
      } else if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_DOIXE))) {
        driveInsert.setLoginRole(Constant.PMQT_ROLE_MANAGER_DOIXE);
      }
      if (driveInsert.getSelectedLicensePlate() != null && driveInsert.getSelectedLicensePlate().size() > 0) {
        listIdCar = new ArrayList<>();
        for (DriveCar driveCar : driveInsert.getSelectedLicensePlate()) {
          if (driveCar != null) {
            listIdCar.add(driveCar.getCarId());
          }
        }
      }
      if (listIdCar != null && listIdCar.size() > 0) {
        driveInsert.setListIdCar(listIdCar);
      }

      // listDrive = vt030003dao.selectsss(driveInsert);
      listDrive = vt030003dao.searchData(driveInsert);
      int totalDrive = vt030003dao.totalDrive(driveInsert);
      ListDrive listDrives = new ListDrive();
      listDrives.setListDrive(listDrive);
      listDrives.setTotalDrive(totalDrive);
      resp.setData(listDrives);
    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      logger.error(e.getMessage(), e);
    }
    return resp;
  }

  @Override
  public ResponseEntityBase findDrive(DriveInsert drive) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    try {
      List<DriveCar> listCar = null;
      DriveInsert listDrive = vt030003dao.detailDrive(drive);
      if (listDrive.getIdCar() != null) {
        String[] listIDs = listDrive.getIdCar().split(",");
        List<String> listIdCar = Arrays.asList(listIDs);
        ;
        drive.setListIdCar(listIdCar);
        listCar = vt030003dao.findListCar(drive);
      }
      listDrive.setSelectedLicensePlate(listCar);
      resp.setData(listDrive);
    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      resp.setData(null);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return resp;
  }

}
