package com.viettel.vtnet360.vt03.vt030010.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.driving.dto.SearchData;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030010.dao.VT030010DAO;
import com.viettel.vtnet360.vt03.vt030010.entity.BookingCarByEmployees;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030010ServiceImpl implements VT030010Service {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private VT030010DAO vt030010dao;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase getListBCbyEmployee() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<BookingCarByEmployees> listBCByEmployee = null;
		try {
			listBCByEmployee = vt030010dao.getListBCbyEmployee();
			resp.setData(listBCByEmployee);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase getJourney() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT030014ListCondition> Journey = null;
		try {
			Journey = vt030010dao.getJourney();
			resp.setData(Journey);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getJourneyById(SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT030014ListCondition> Journey = new ArrayList<>();
		try {
			Journey = vt030010dao.getJourneyById(searchData);
			resp.setData(Journey);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	


}	
