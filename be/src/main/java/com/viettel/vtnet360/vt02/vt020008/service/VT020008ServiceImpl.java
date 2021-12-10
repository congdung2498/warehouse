package com.viettel.vtnet360.vt02.vt020008.service;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.InputValidate;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020008.dao.VT020008DAO;
import com.viettel.vtnet360.vt02.vt020008.entity.VT020008ParamRequest;

/**
 * Class serviceImpl for screen VT020008 : rating lunch
 * 
 * @author DuyNK 19/09/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT020008ServiceImpl implements VT020008Service {
	private final Logger logger = Logger.getLogger(this.getClass());
	private InputValidate validate = new InputValidate();

	@Autowired
	private VT020008DAO vt020008DAO;

	@Override
	@Transactional
	public ResponseEntityBase updateRating(VT020008ParamRequest param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		param.setHasBooking(Constant.HAS_BOOKING_DEFAULT);
		param.setQuantity(Constant.QUANTITY_NONE);
		// get system date
		Calendar today = Calendar.getInstance();
		// get lunchDate
		Calendar lunchDate = Calendar.getInstance();
		lunchDate.setTime(param.getLunchDate());
		// check if lunchDate = today && 13h <= today <= 15h => rating
		if (today.get(Calendar.HOUR_OF_DAY) < Constant.TIME_LIMIT_RATING_FROM || today.get(Calendar.HOUR_OF_DAY) >= Constant.TIME_LIMIT_RATING_TO) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			return resp;
		}
		today.setTime(validate.validateDate(today.getTime()));
		param.setLunchDate(today.getTime());
		try {
			// check lunch date existed on db 1:existed => update, 0:not existed => no
			// update
			int checkLunchDate = vt020008DAO.checkLunchExisted(param);
			if (checkLunchDate > Constant.NONE_EXIST_RECORD) {
				if (param.getRating() < Constant.RATING_MIN || param.getRating() > Constant.RATING_MAX) {
					param.setRating(Constant.RATING_MIN);
				}
				// update rating to db
				int update = vt020008DAO.updateRating(param);
				if (update == Constant.SUCCESS) {
					resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				} else {
					resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				}
			} else {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
}
