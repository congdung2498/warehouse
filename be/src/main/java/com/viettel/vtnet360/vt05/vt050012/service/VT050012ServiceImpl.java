package com.viettel.vtnet360.vt05.vt050012.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050012.dao.VT050012DAO;
import com.viettel.vtnet360.vt05.vt050012.entity.QuotaInsert;
import com.viettel.vtnet360.vt05.vt050012.entity.UpdateLimitDateDTO;
import com.viettel.vtnet360.vt05.vt050012.entity.VT050012DataResponse;
import com.viettel.vtnet360.vt05.vt050012.entity.VT050012DataResponseQuota;
import com.viettel.vtnet360.vt05.vt050012.entity.VT050012RequestParam;
import com.viettel.vtnet360.vt05.vt050012.entity.VT050012RequestParamQuota;

/**
 * @author DuyNK 04/10/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT050012ServiceImpl implements VT050012Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050012DAO vt050012DAO;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findData(VT050012RequestParam param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT050012DataResponse> dataResponse = null;
		try {
			param.setPageNumber(param.getPageNumber() * param.getPageSize());
			dataResponse = vt050012DAO.findData(param);
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findStationeryQuota(VT050012RequestParamQuota param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT050012DataResponseQuota> dataResponse = new ArrayList<>();
		try {
			dataResponse = vt050012DAO.findStationeryQuota(param);
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase countStationeryQuota(VT050012RequestParamQuota param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		int dataResponse = 0;
		try {
			dataResponse = vt050012DAO.countStationeryQuota(param);
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getLimitDateEnd() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		String dataResponse = vt050012DAO.getLimitDateEnd();
		resp.setData(dataResponse);
		return resp;
	}

	@Override
	public ResponseEntityBase updateLimitDate(UpdateLimitDateDTO updateLimitDateDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		int dataResponse = vt050012DAO.updateLimitDate(updateLimitDateDTO);
		resp.setData(dataResponse);
		return resp;
	}

	@Override
	public ResponseEntityBase insertQuota(QuotaInsert quotaInsert) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		if(vt050012DAO.isQuotaExist(quotaInsert.getUnitId()) > 0) {
		  resp.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
		  return resp;
		}
		int dataResponse = vt050012DAO.insertQuota(quotaInsert);
		resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		resp.setData(dataResponse);
		return resp;
	}

	@Override
	public ResponseEntityBase updateQuota(VT050012DataResponseQuota param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		if(vt050012DAO.isQuotaExist(param.getUnitId().intValue()) == 0){
			resp.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);
		}else{
			int dataResponse = vt050012DAO.updateQuota(param);
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			resp.setData(dataResponse);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase deleteQuota(VT050012DataResponseQuota param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		if(vt050012DAO.isQuotaExist(param.getUnitId().intValue()) == 0) {
      
      return resp;
    }
		int dataResponse = vt050012DAO.deleteQuota(param);
		resp.setData(dataResponse);
		return resp;
	}

}