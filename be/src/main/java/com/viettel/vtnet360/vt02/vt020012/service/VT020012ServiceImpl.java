package com.viettel.vtnet360.vt02.vt020012.service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.vtnet360.vt02.vt020012.dao.VT020012DAO;
import com.viettel.vtnet360.vt02.vt020012.entity.VT020012KitchenReport;
import com.viettel.vtnet360.vt02.vt020012.entity.VT020012RequestParam;

/**
 * Class implement from VT020000Service with business rule
 * 
 * @author VinhNVQ 9/10/2018
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class VT020012ServiceImpl implements VT020012Service {
	
	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/** Data Access Object */
	@Autowired
	VT020012DAO vt020012dao;
	
	/** Service convert object to excel file */
	@Autowired
	VT020012ExcelOutputService VT020012ExcelOutPutService;
	
	/**
	 * @author VinhNVQ 9/10/2018
	 * 
	 * @return List<VT020012KitchenReport>
	 */
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<VT020012KitchenReport> kitchenList(VT020012RequestParam param, String userName, Collection<GrantedAuthority> authority) throws Exception {

		/** Initialization List of KitchenReport object for contain result */
		List<VT020012KitchenReport> kitchenList = null;

		try {
			kitchenList = vt020012dao.getReportForKitchen(param);
		} catch (Exception e) {
			logger.info("******************** Crash from database when get report by unit ********************");
			logger.error(e.getMessage(), e);
			throw e;
		}

		return kitchenList;
	}

	@Override
	public File kitchenReport(VT020012RequestParam param, String userName,
			Collection<GrantedAuthority> authority) throws Exception {
		/** Initialization List of KitchenReport object for contain result */
		List<VT020012KitchenReport> kitchenList = null;
		File ExcelFile = null;
		try {
			kitchenList = vt020012dao.getReportForKitchen(param);
		} catch (Exception e) {
			logger.info("******************** Crash from database when get report by unit ********************");
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		try {
			DateFormat formatter = new SimpleDateFormat("MM/yyyy");
			ExcelFile = VT020012ExcelOutPutService.createExcelOutputExcel(kitchenList, formatter.format(param.getMonth()));
		} catch (Exception e) {
			logger.info("******************** Crash from object to database convert when get report by unit ********************");
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ExcelFile;
	}

}
