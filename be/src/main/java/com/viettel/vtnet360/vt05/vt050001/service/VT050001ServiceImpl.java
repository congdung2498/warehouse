package com.viettel.vtnet360.vt05.vt050001.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050001.dao.VT050001DAO;
import com.viettel.vtnet360.vt05.vt050001.entity.CalculationUnit;
import com.viettel.vtnet360.vt05.vt050001.entity.LimitSpend;
import com.viettel.vtnet360.vt05.vt050001.entity.Stationery;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT050001ServiceImpl implements VT050001Service {
  private final Logger logger = Logger.getLogger(this.getClass());
  @Autowired
  private VT050001DAO vt050001DAO;

  @Override
  public ResponseEntityBase getAllStationery() {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<Stationery> listStationery = null;
    try {
      listStationery = vt050001DAO.getAllStationery();
      if(listStationery != null && listStationery.size() > 0) {
        for(Stationery stationery : listStationery) {
          if(isCheckImage(stationery.getStationeryId())) {
            stationery.setImage(1);
          }
        }
      }
      resp.setData(listStationery);
    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return resp;
  }

  @Override
  public ResponseEntityBase getCalulationUnit() {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<CalculationUnit> listCalUnit = null;
    try {
      listCalUnit = vt050001DAO.getCalulationUnit();
      resp.setData(listCalUnit);
    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return resp;
  }

  @Override
  public ResponseEntityBase findCalulationUnit(CalculationUnit calUnit) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<CalculationUnit> listCalUnit = null;
    try {
      listCalUnit = vt050001DAO.findCalulationUnit(calUnit);
      resp.setData(listCalUnit);
    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return resp;
  }

  @Override
  public ResponseEntityBase searchStationery(Stationery stationery) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<Stationery> listStationery = null;
    try {
      listStationery = vt050001DAO.searchStationery(stationery);
      if(listStationery != null && listStationery.size() > 0) {
        for(Stationery stationery1 : listStationery) {
          if(isCheckImage(stationery1.getStationeryId())) {
            stationery1.setImage(1);
          }
        }
      }
      resp.setData(listStationery);
    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return resp;
  }

  @Override
  public ResponseEntityBase insertStationery(Stationery stationery, byte[] bytes) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    int duplicate = 0;

    List<Stationery> listStationery = vt050001DAO.getAllStationery();
    for (Stationery item : listStationery) {
      if ((item.getStationeryName().toLowerCase()).equals(stationery.getStationeryName().toLowerCase())) {
        duplicate = 1;
        break;
      }
    }
    
    if (duplicate == 1) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      resp.setData(null);
    } else {
      try {
        int i = vt050001DAO.insertStationery(stationery);
        saveImage(bytes, stationery.getStationeryId());
        
        if (i == 1) {
          resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
          return resp;
        } else {
          resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
          return resp;
        }
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
        resp.setData(null);
      }
    }
    return resp;
  }

  @Override
  public ResponseEntityBase updateStationery(Stationery stationery, byte[] bytes) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<Stationery> listStationery = vt050001DAO.getAllStationery();
    int checked = 0;
    int status = vt050001DAO.checkDeleted(stationery);
    if (status == 1) {
      resp.setStatus(Constant.ERROR_UPDATE);
      return resp;
    }
    for (Stationery item : listStationery) {
      if ((item.getStationeryName().toLowerCase()).equals(stationery.getStationeryName().toLowerCase())
          && !item.getStationeryId().equals(stationery.getStationeryId())) {
        checked++;
      }
    }

    if (checked == 0) {
      try {
        int i = vt050001DAO.updateStationery(stationery);
        saveImage(bytes, stationery.getStationeryId());
        if (i == 1) {
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
      resp.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);
      resp.setData(null);
    }
    return resp;
  }

  @Override
  public ResponseEntityBase deleteStationery(Stationery stationery) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    int status = vt050001DAO.checkDeleted(stationery);
    if (status == 1) {
      response.setStatus(Constant.ERROR_UPDATE);
      return response;
    }
    List<Integer> listTemp = new ArrayList<>();
    listTemp.add(Constant.STATIONERY_WAIT_MANAGER_APPROVE);
    listTemp.add(Constant.STATIONERY_MANAGER_APPROVE);
    listTemp.add(Constant.STATIONERY_RECEIVE_REQUEST);
    listTemp.add(Constant.STATIONERY_EXECUTING);
    listTemp.add(Constant.STATIONERY_PAUSE);
    listTemp.add(Constant.STATIONERY_CREATE);
    stationery.setListStatus(listTemp);
    try {


      if (vt050001DAO.deleteStationery(stationery) > 0) {
        response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

      } else {
        response = new ResponseEntityBase(Constant.STATUS_IS_RUNING_CAR, null);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    return response;
  }

  @Override
  public ResponseEntityBase getLimit() {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    LimitSpend limit = null;
    try {
      limit = vt050001DAO.getLimit(Constant.STATIONERY_SPENDING_LIMIT_CLASS);
      resp.setData(limit);
    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return resp;
  }

  @Override
  public ResponseEntityBase updateLimit(LimitSpend limit) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    try {
      int i = vt050001DAO.updateLimit(limit);
      if (i == 1) {
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
    return null;
  }

  @Override
  public ResponseEntityBase deleteSelectStationery(List<Stationery> stationery) {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    for(int i = 0 ; i < stationery.size() ; i ++){
      int status = vt050001DAO.checkDeleted(stationery.get(i));
      if (status == 1) {
        response.setStatus(Constant.ERROR_UPDATE);
        return response;
      }
      List<Integer> listTemp = new ArrayList<>();
      listTemp.add(Constant.STATIONERY_WAIT_MANAGER_APPROVE);
      listTemp.add(Constant.STATIONERY_MANAGER_APPROVE);
      listTemp.add(Constant.STATIONERY_RECEIVE_REQUEST);
      listTemp.add(Constant.STATIONERY_EXECUTING);
      listTemp.add(Constant.STATIONERY_PAUSE);
      listTemp.add(Constant.STATIONERY_CREATE);
      stationery.get(i).setListStatus(listTemp);
      try {
        if (vt050001DAO.deleteStationery(stationery.get(i)) > 0) {
          response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

        } else {
          response = new ResponseEntityBase(Constant.STATUS_IS_RUNING_CAR, null);
        }

      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      }
    }
    return response;
  }

  @Override
  public ResponseEntityBase searchCountStationery(Stationery stationery) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<Stationery> listStationery = null;
    try {
      listStationery = vt050001DAO.searchCountStationery(stationery);
      resp.setData(listStationery);
    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      logger.error(e.getMessage(), e);
    }
    return resp;
  }

  public void saveImage(byte[] data, String stationerItemId) throws IOException {
    if (data != null) {
      String newName = stationerItemId + "." + "png";

      String directory = new File("").getAbsoluteFile().getParent() + File.separator + Constant.STATIONERY_IMAGE_PATH;
      File directoryFile = new File(directory);
      if(!directoryFile.exists()) {
        directoryFile.mkdirs();
      }

      String path = directory + File.separator + newName;
      
      BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
      File imageToSave = new File(path);
      if (imageToSave.exists()) {
        imageToSave.delete();
      } 
      
      ImageIO.write(image, "png", imageToSave);
    }
  }
  
  private boolean isCheckImage(String stationeryId) {
    try {
      if (stationeryId != null) {
        String newName = stationeryId + "." + "png";
        String directory = new File("").getAbsoluteFile().getParent() + File.separator + Constant.STATIONERY_IMAGE_PATH;
        String path = directory + File.separator + newName;
        File file  = new File(path);
        if(file.exists()) return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return false;
  }
}
