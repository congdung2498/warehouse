package com.viettel.vtnet360.vt02.vt020010.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Unit;
import com.viettel.vtnet360.vt02.vt020000.service.VT020000Service;
import com.viettel.vtnet360.vt02.vt020010.dao.VT020010DAO;
import com.viettel.vtnet360.vt02.vt020010.entity.SearchingUnit;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010DataResponse;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010InfoToFindUnit;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010ReportDetail;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010RequestParam;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010Unit;

/**
 * Class serviceImpl for screen VT020010 : bao cao suat an theo bep
 * 
 * @author DuyNK 14/09/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT020010ServiceImpl implements VT020010Service {

  private final Logger logger = Logger.getLogger(this.getClass());

  @Autowired
  private VT020010DAO vt020010DAO;
  
  @Autowired
  private VT020000Service vt020000Service; 


  @Override
  @Transactional(readOnly = true)
  public ResponseEntityBase getReportForMobile(VT020010RequestParam param, Collection<GrantedAuthority> roleList) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<VT020010DataResponse> dataResponse = new ArrayList<>();
    try {
      if (!roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN)) && 
          (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER)) || 
              roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV)))) {
        if(param.getUnitId() > 0) {
          VT020000Unit unit = vt020000Service.getUnitParent(String.valueOf(param.getUnitId()));
          if(unit != null) param.setUnitId(unit.getUnitId().intValue());
        }
      }
      //vt020000Service.getUnitParent(unitID)
      // find all Info LunchDate in a month(get month from client)
      param.setHasBooking(Constant.HAS_BOOKING_DEFAULT);
      param.setQuantity(Constant.QUANTITY_NONE);
      List<VT020010ReportDetail> listReportItem = vt020010DAO.findReportItem(param);
      // group ReportItem by lunchDate
      Map<Date, List<VT020010ReportDetail>> groupByLunchDate = listReportItem.stream()
          .sorted((o1, o2) -> o2.getLunchDate().compareTo(o1.getLunchDate())).collect(Collectors
              .groupingBy(VT020010ReportDetail::getLunchDate, LinkedHashMap::new, Collectors.toList()));
      Set<Date> set = groupByLunchDate.keySet();

      //list unitName
      List<String> listUnitName = new ArrayList<>();
      //      listUnitName.add(Constant.UNIT_NAME_BAN); 
      listUnitName.add(Constant.UNIT_NAME_KHOI);    
      // get all unit by unitId PARENT_UNIT_ID that UNIT_NAME not like 'khoi%'
      List<VT020010Unit> listUnitNotLikeKhoi = 
          vt020010DAO.findAllUnitNotLikeKhoi(new VT020010InfoToFindUnit(Constant.UNIT_PARENT_ID, param.getKitchenID(), listUnitName));
      // get all unit by unitId PARENT_UNIT_ID that UNIT_NAME like 'khoi%'
      List<Integer> listUnitLikeKhoi = 
          vt020010DAO.findAllUnitLikeKhoi(new VT020010InfoToFindUnit(Constant.UNIT_PARENT_ID, param.getKitchenID(), listUnitName));
      // get all unit by unitId PARENT_UNIT_ID that Unit Parent has UNIT_NAME like 'khoi%'
      List<VT020010Unit> listUnitChildOfKhoi = vt020010DAO.findAllUnitChildOfKhoi(new SearchingUnit(param.getKitchenID(), listUnitLikeKhoi));

      // list all id need for synthesis report 
      List<VT020010Unit> listUnit = new ArrayList<>();
      for (int i = 0; i < listUnitNotLikeKhoi.size(); i++) {
        listUnit.add(listUnitNotLikeKhoi.get(i));
      }
      for (int i = 0; i < listUnitChildOfKhoi.size(); i++) {
        listUnit.add(listUnitChildOfKhoi.get(i));
      }

      //set report for each day
      for (Date key : set) {
        List<VT020010ReportDetail> listReport = groupByLunchDate.get(key);
        //find total for each unit
        //for all unit
        for (int i = 0; i < listUnit.size(); i++) {
          int total = 0;
          //for lunchDate in a day
          for (int j = listReport.size() - 1; j >= 0; j--) {
            //if this employee has unitPath contains unitID in list => find total 
            if(listReport.get(j).getUnitPath() != null) {
              if(listReport.get(j).getUnitPath().contains(String.valueOf(listUnit.get(i).getUnitID()))) {
                total += listReport.get(j).getTotal();
                listReport.remove(j);
              } 
            }
          }
          // create report for this unit in this lunchDate if total > 0
          if(total > 0) {
            VT020010Unit vt020010Unit  = new VT020010Unit();
            vt020010Unit.setKitchenId(param.getKitchenID());
            vt020010Unit.setUnitID(listUnit.get(i).getUnitID());
            vt020010Unit.setPath(listUnit.get(i).getPath());

            String unitName = "";
            if(isKhoiCoQuanUnit(vt020010Unit, listUnitChildOfKhoi)) {
              unitName = vt020010DAO.getUnitShortNameKhoi(vt020010Unit);
            } else if(isTrungTamUnit(vt020010Unit, listUnitNotLikeKhoi)) {
              unitName = vt020010DAO.getUnitShortName(vt020010Unit);
            } else {
              unitName = vt020010DAO.getUnitShortNameKhoi(vt020010Unit);
            }

            VT020010DataResponse reportOneDay = new VT020010DataResponse();
            reportOneDay.setLunchDate(key);
            reportOneDay.setTotal(total);
            reportOneDay.setUnitName(unitName);
            reportOneDay.setUnitId(listUnit.get(i).getUnitID());
            dataResponse.add(reportOneDay);
          }
        }
        
        if(listReport.size() > 0) {
          int total = 0;
          for (int j = listReport.size() - 1; j >= 0; j--) {
            if(listReport.get(j).getUnitPath() == null) {
                total += listReport.get(j).getTotal();
            }
          }

          if(total > 0) {
            VT020010DataResponse reportOneDay = new VT020010DataResponse();
            reportOneDay.setLunchDate(key);
            reportOneDay.setTotal(total);
            reportOneDay.setUnitName("Khác(không có đơn vị)");
            reportOneDay.setUnitId(0);
            dataResponse.add(reportOneDay);
          }
        }
      }
      
      resp.setData(dataResponse);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return resp;
  }


  @Override
  @Transactional(readOnly = true)
  public ResponseEntityBase getReport(VT020010RequestParam param, Collection<GrantedAuthority> roleList) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    List<VT020010DataResponse> dataResponse = new ArrayList<>();
    try {
      // find all Info LunchDate in a month(get month from client)
      param.setHasBooking(Constant.HAS_BOOKING_DEFAULT);
      param.setQuantity(Constant.QUANTITY_NONE);
      List<VT020010ReportDetail> listReportItem = vt020010DAO.findReportItem(param);
      // group ReportItem by lunchDate
      Map<Date, List<VT020010ReportDetail>> groupByLunchDate = listReportItem.stream()
          .sorted((o1, o2) -> o2.getLunchDate().compareTo(o1.getLunchDate())).collect(Collectors
              .groupingBy(VT020010ReportDetail::getLunchDate, LinkedHashMap::new, Collectors.toList()));
      Set<Date> set = groupByLunchDate.keySet();

      //list unitName
      List<String> listUnitName = new ArrayList<>();
      //			listUnitName.add(Constant.UNIT_NAME_BAN);	
      listUnitName.add(Constant.UNIT_NAME_KHOI);		
      // get all unit by unitId PARENT_UNIT_ID that UNIT_NAME not like 'khoi%'
      List<VT020010Unit> listUnitNotLikeKhoi = 
          vt020010DAO.findAllUnitNotLikeKhoi(new VT020010InfoToFindUnit(Constant.UNIT_PARENT_ID, param.getKitchenID(), listUnitName));
      // get all unit by unitId PARENT_UNIT_ID that UNIT_NAME like 'khoi%'
      List<Integer> listUnitLikeKhoi = 
          vt020010DAO.findAllUnitLikeKhoi(new VT020010InfoToFindUnit(Constant.UNIT_PARENT_ID, param.getKitchenID(), listUnitName));
      // get all unit by unitId PARENT_UNIT_ID that Unit Parent has UNIT_NAME like 'khoi%'
      List<VT020010Unit> listUnitChildOfKhoi = vt020010DAO.findAllUnitChildOfKhoi(new SearchingUnit(param.getKitchenID(), listUnitLikeKhoi));

      // list all id need for synthesis report 
      List<VT020010Unit> listUnit = new ArrayList<>();
      for (int i = 0; i < listUnitNotLikeKhoi.size(); i++) {
        listUnit.add(listUnitNotLikeKhoi.get(i));
      }
      for (int i = 0; i < listUnitChildOfKhoi.size(); i++) {
        listUnit.add(listUnitChildOfKhoi.get(i));
      }

      //set report for each day
      for (Date key : set) {
        List<VT020010ReportDetail> listReport = groupByLunchDate.get(key);
        //find total for each unit
        //for all unit
        for (int i = 0; i < listUnit.size(); i++) {
          int total = 0;
          //for lunchDate in a day
          for (int j = listReport.size() - 1; j >= 0; j--) {
            //if this employee has unitPath contains unitID in list => find total 
            if(listReport.get(j).getUnitPath() != null) {
              if(listReport.get(j).getUnitPath().contains(String.valueOf(listUnit.get(i).getUnitID()))) {
                total += listReport.get(j).getTotal();
                listReport.remove(j);
              }
            }
          }
          // create report for this unit in this lunchDate if total > 0
          if(total > 0) {
            VT020010Unit vt020010Unit  = new VT020010Unit();
            vt020010Unit.setKitchenId(param.getKitchenID());
            vt020010Unit.setUnitID(listUnit.get(i).getUnitID());
            vt020010Unit.setPath(listUnit.get(i).getPath());

            String unitName = "";
            if(isKhoiCoQuanUnit(vt020010Unit, listUnitChildOfKhoi)) {
              unitName = vt020010DAO.getUnitShortNameKhoi(vt020010Unit);
            } else if(isTrungTamUnit(vt020010Unit, listUnitNotLikeKhoi)) {
              unitName = vt020010DAO.getUnitShortName(vt020010Unit);
            } else {
              unitName = vt020010DAO.getUnitShortNameKhoi(vt020010Unit);
            }

            VT020010DataResponse reportOneDay = new VT020010DataResponse();
            reportOneDay.setLunchDate(key);
            reportOneDay.setTotal(total);
            reportOneDay.setUnitName(unitName);
            reportOneDay.setUnitId(listUnit.get(i).getUnitID());
            dataResponse.add(reportOneDay);
          }
        }
      }

      if(listReportItem.size() > 0) {
        for (Date key : set) {
          List<VT020010ReportDetail> listReport = groupByLunchDate.get(key);
          int total = 0;
          for (int j = listReport.size() - 1; j >= 0; j--) {
            if(key.getTime() == listReportItem.get(j).getLunchDate().getTime()) {
              total += listReportItem.get(j).getTotal();
              listReportItem.remove(j);
            }
          }

          if(total > 0) {
            VT020010DataResponse reportOneDay = new VT020010DataResponse();
            reportOneDay.setLunchDate(key);
            reportOneDay.setTotal(total);
            reportOneDay.setUnitName("Khác(không có đơn vị)");
            reportOneDay.setUnitId(0);
            dataResponse.add(reportOneDay);
          }
        }
      }

      resp.setData(dataResponse);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return resp;
  }

  @Override
  public ResponseEntityBase reportByUnit(VT020010RequestParam param) {
    List<VT020010DataResponse> dataResponse = vt020010DAO.reportByUnit(param);
    return new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, dataResponse);
  }

  private boolean isKhoiCoQuanUnit(VT020010Unit currtenUnit, List<VT020010Unit> units) {
    for(VT020010Unit unit : units) {
      if(currtenUnit.getUnitID() == unit.getUnitID()) return true;
    }
    return false;
  }

  private boolean isTrungTamUnit(VT020010Unit currtenUnit, List<VT020010Unit> units) {
    for(VT020010Unit unit : units) {
      if(currtenUnit.getUnitID() == unit.getUnitID()) return true;
    }
    return false;
  }
}
