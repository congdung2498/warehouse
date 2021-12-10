package com.viettel.vtnet360.vt02.vt020011.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.InputValidate;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020011.dao.VT020011DAO;
import com.viettel.vtnet360.vt02.vt020011.entity.VT020011DayOffSettingEntity;

/**
 * Class serviceImpl for screen VT020011 : setting day off
 * 
 * @author DuyNK 05/09/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT020011ServiceImpl implements VT020011Service {

	private final Logger logger = Logger.getLogger(this.getClass());
	private InputValidate validate = new InputValidate();

	@Autowired
	private VT020011DAO vt020011Dao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase getDayOff(VT020011DayOffSettingEntity dayOff) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT020011DayOffSettingEntity> list = null;
		try {
			list = vt020011Dao.findDayOff(dayOff);
			resp.setData(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase countTotalDayOff(VT020011DayOffSettingEntity dayOff) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		int totalRecord = 0;
		try {
			totalRecord = vt020011Dao.countTotalDayOff(dayOff);
			resp.setData(totalRecord);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional
	public ResponseEntityBase insertDayOff(VT020011DayOffSettingEntity dayOff) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		// validate input
		if (StringUtils.isBlank(dayOff.getDescription())) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			return resp;
		}
		// convert dayOff to Date
		dayOff.setDayOff(validate.validateDate(dayOff.getDayOff()));
		// check exist in database
		try {
			int checkDayOff = vt020011Dao.checkDayOffExist(dayOff);
			if (checkDayOff > Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
				return resp;
			}
			// insert to database
			int i = vt020011Dao.insertDayOff(dayOff);
			if (i == Constant.SUCCESS) {
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			} else {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			}
			// delete record lunchDate in this dayOff
			vt020011Dao.deleteLunchDateInDayOff(dayOff.getDayOff());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional
	public ResponseEntityBase updateDayOff(VT020011DayOffSettingEntity dayOff) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		// validate input
		if (StringUtils.isBlank(dayOff.getDescription())) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			return resp;
		}
		// convert dayOff to Date
		dayOff.setDayOff(validate.validateDate(dayOff.getDayOff()));
		// check exist in database
		try {
			int checkDayOff = vt020011Dao.checkDayOffExist(dayOff);
			if (checkDayOff > Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
				return resp;
			}

			// update to database
			int i = vt020011Dao.updateDayOff(dayOff);
			if (i == Constant.SUCCESS) {
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			} else {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			}
			// delete record lunchDate in this dayOff
			vt020011Dao.deleteLunchDateInDayOff(dayOff.getDayOff());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional
	public ResponseEntityBase copyDayOff(List<VT020011DayOffSettingEntity> listDayOff) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			int statusDelete = vt020011Dao.deleteDayOff(listDayOff.get(0));
			if (statusDelete >= 0) {
				int status = vt020011Dao.insertCopyDayOff(listDayOff);
				if (status > 0) {
					resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				} else {
					resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findAllYears() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<String> years = vt020011Dao.findAllYears();
			resp.setData(years);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
}
