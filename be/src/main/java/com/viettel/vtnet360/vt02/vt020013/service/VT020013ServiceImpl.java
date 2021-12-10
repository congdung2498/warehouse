package com.viettel.vtnet360.vt02.vt020013.service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt02.vt020013.dao.VT020013DAO;
import com.viettel.vtnet360.vt02.vt020013.entity.ReportByUnit;
import com.viettel.vtnet360.vt02.vt020013.entity.ReportByUnitParam;
import com.viettel.vtnet360.vt02.vt020013.entity.VT020013KitchenReport;
import com.viettel.vtnet360.vt02.vt020013.entity.VT020013RequestParam;

/**
 * Class implement from VT020000Service with business rule
 * 
 * @author VinhNVQ 9/10/2018
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class VT020013ServiceImpl implements VT020013Service {

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());

	/** Data Access Object */
	@Autowired
	VT020013DAO vt020013dao;

	/** Service convert object to excel file */
	@Autowired
	VT020013ExcelOutputService VT020013ExcelOutPutService;

	/**
	 * @author VinhNVQ 9/10/2018
	 * 
	 * @return List<VT020013KitchenReport>
	 */
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<VT020013KitchenReport> kitchenList(VT020013RequestParam param, String userName,
			Collection<GrantedAuthority> authority) throws Exception {

		/** Initialization List of KitchenReport object for contain result */
		List<VT020013KitchenReport> kitchenList = null;

		try {
		  if (authority.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_CHEF))) {
		    return vt020013dao.getReportForKitchen(param);
		  }
		  if(authority.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_EMPLOYYEE)) && 
		      !authority.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER)) &&  
		      !authority.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP)) && 
		      !authority.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV)) && 
		      !authority.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {
		    param.setUserName(userName);
		    param.setQuery(null);
		  }
			kitchenList = vt020013dao.getReportForKitchen(param);
		} catch (Exception e) {
			logger.info("******************** Crash from database when get report by unit ********************");
			logger.error(e.getMessage(), e);
			throw e;
		}

		return kitchenList;
	}
	
  @Transactional(readOnly = true)
  public List<ReportByUnit> kitchenListForMobile(ReportByUnitParam param, Collection<GrantedAuthority> authority) {
    List<ReportByUnit> kitchenList = null;
    try {
      if(param.getUnitId() == 0 || param.getUnitId() < 1) {
        kitchenList = vt020013dao.getReportForKitchenForMobileNoUnit(param);
      } else {
        kitchenList = vt020013dao.getReportForKitchenForMobile(param);
      }
    } catch (Exception e) {
      logger.info("******************** Crash from database when get report by unit ********************");
      logger.error(e.getMessage(), e);
      throw e;
    }
    return kitchenList;
  }

	@Override
	public File kitchenReport(VT020013RequestParam param, String userName, Collection<GrantedAuthority> authority)
			throws Exception {
		/** Initialization List of KitchenReport object for contain result */
		List<VT020013KitchenReport> kitchenList = null;
		File ExcelFile = null;
		try {
			kitchenList = vt020013dao.getReportForKitchen(param);
		} catch (Exception e) {
			logger.info("******************** Crash from database when get report by unit ********************");
			logger.error(e.getMessage(), e);
			throw e;
		}

		try {
			DateFormat formatter;
			formatter = new SimpleDateFormat("MM/yyyy");
			ExcelFile = VT020013ExcelOutPutService.createExcelOutputExcel(kitchenList,
					formatter.format(param.getMonth()));
		} catch (Exception e) {
			logger.info(
					"******************** Crash from object to database convert when get report by unit ********************");
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ExcelFile;
	}

	@Override
	public List<VT020013KitchenReport> getReportResponse(VT020013RequestParam param) throws Exception {
		List<VT020013KitchenReport> kitchenReports = new ArrayList<>();

		try {
			kitchenReports = vt020013dao.getReportResponse(param);
		} catch (Exception e) {
			throw e;
		}

		return kitchenReports;
	}

}
