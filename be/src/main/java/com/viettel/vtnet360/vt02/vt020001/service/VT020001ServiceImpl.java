package com.viettel.vtnet360.vt02.vt020001.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020001.dao.VT020001DAO;
import com.viettel.vtnet360.vt02.vt020001.entity.VT020001Place;

/**
 * Class serviceImpl for screen VT020001 : setting place
 * 
 * @author DuyNK 09/08/2018
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT020001ServiceImpl implements VT020001Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT020001DAO vt020001DAO;

	/**
	 * Select all place from QLDV_PLACE
	 * 
	 * @param
	 * @return ResponseEntityBase(status,List<VT020001Place>)
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListPlace(VT020001Place placeInfo) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT020001Place> listPlace = null;
		try {
			listPlace = vt020001DAO.findPlace(placeInfo);
			resp.setData(listPlace);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase counTotalRecord(VT020001Place placeInfo) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			int totalRecord = vt020001DAO.countTotalRecord(placeInfo);
			resp.setData(totalRecord);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	/**
	 * Insert new place to QLDV_PLACE
	 * 
	 * @param VT020001Place
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase insertPlace(VT020001Place placeInfo) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		// validate input
		if (placeInfo == null || StringUtils.isBlank(placeInfo.getPlaceName())) {
			return resp;
		}
		try {
			int checkPlace = vt020001DAO.checkPlaceExist(placeInfo);
			if (checkPlace > 0) {
				resp.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
				return resp;
			}

			// insert to db
			int insert = vt020001DAO.insertPlace(placeInfo);
			if (insert > 0) {
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	/**
	 * update place to QLDV_PLACE
	 * 
	 * @param VT020001Place
	 * @return ResponseEntityBase
	 */
	@Override
	public ResponseEntityBase updatePlace(VT020001Place placeInfo) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		// validate input
		if (placeInfo == null || StringUtils.isBlank(placeInfo.getPlaceName())) {
			return resp;
		}
		try {
			//check condition before update place ( this place is deleted before or not)
			int checkCondition = vt020001DAO.checkConditionBeforeUpdate(placeInfo.getPlaceCode());
			if(checkCondition == Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.ERROR_PLACE_DELETED_BEFORE_UPDATE);
				return resp;
			}
			
			// check place name existed in db
			int checkPlace = vt020001DAO.checkPlaceUpdate(placeInfo);
			if (checkPlace > 0) {
				resp.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
				return resp;
			}

			// update to db
			int update = vt020001DAO.updatePlace(placeInfo);
			if (update > 0) {
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	/**
	 * Delete place in QLDV_PLACE
	 * 
	 * @param VT020001Place
	 * @return ResponseEntityBase
	 */
	@Override
	public ResponseEntityBase deletePlace(VT020001Place placeInfo) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		// validate input
		if (placeInfo == null) {
			return resp;
		}
		try {
			//check condition before update place ( this place is deleted before or not)
			int checkCondition = vt020001DAO.checkConditionBeforeUpdate(placeInfo.getPlaceCode());
			if(checkCondition == Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.ERROR_PLACE_DELETED_BEFORE_UPDATE);
				return resp;
			}
			
			vt020001DAO.deletePlace(placeInfo);
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}
}
