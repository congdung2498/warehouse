package com.viettel.vtnet360.vt04.vt040002.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040002.dao.VT040002DAO;
import com.viettel.vtnet360.vt04.vt040002.entity.VT040002Stationery;
import com.viettel.vtnet360.vt04.vt040002.entity.VT040002SystemCode;

/**
 * class to do business VT040002
 * 
 * @author ThangBT 18/10/2018
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT040002ServiceImpl implements VT040002Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	private static final String END_INSERT_STATIONERY_FAIL = "**************** End processing insert stationery - Fail ****************";

	private static final String END_UPDATE_STATIONERY_FAIL = "**************** End processing update stationery - Fail ****************";

	private static final String END_DELETE_STATIONERY_FAIL = "**************** End processing delete stationery - Fail ****************";

	@Autowired
	private VT040002DAO vt040002dao;

	/**
	 * find List Stationery
	 * 
	 * @param VT040002Stationery
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListStationery(VT040002Stationery stationeryInfo) throws Exception {

		logger.info("**************** Start get list stationeries ****************");

		List<VT040002Stationery> stationeries = new ArrayList<>();
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, stationeries);

		try {
			String pattern = stationeryInfo.getStationeryName();
			stationeryInfo.setStationeryName(pattern.trim());

			stationeries = vt040002dao.findListStationery(stationeryInfo);
			reb.setData(stationeries);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list stationeries - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list stationeries - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * find List Stationery Type Or Unit
	 * 
	 * @param String
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListStationeryTypeOrUnit(String masterClass) throws Exception {

		logger.info("**************** Start get list system code of stationery ****************");

		List<VT040002SystemCode> systemCodes = new ArrayList<>();
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, systemCodes);

		try {
			systemCodes = vt040002dao.findListStationeryTypeOrUnit(masterClass);
			reb.setData(systemCodes);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list system code of stationery - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list system code of stationery - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * find Stationery Report
	 * 
	 * @param VT040002Stationery
	 * @param Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findStationeryReport(VT040002Stationery stationeryInfo,
			Collection<GrantedAuthority> listRole) {

		logger.info("**************** Start get list report stationeries ****************");

		List<VT040002Stationery> stationeries = new ArrayList<>();
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, stationeries);

		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {
				stationeries = vt040002dao.findStationeryReport(stationeryInfo);
				reb.setData(stationeries);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

				logger.info("**************** End get list report stationeries - OK ****************");
			}
		} catch (Exception e) {
			logger.info("**************** End get list report stationeries - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * count Total Record
	 * 
	 * @param VT040002Stationery
	 * @param Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase countTotalRecord(VT040002Stationery stationeryInfo, Collection<GrantedAuthority> listRole)
			throws Exception {

		logger.info("**************** Start count total record of list report stationeries ****************");

		int totalRecord = -1;
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, totalRecord);

		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {
				totalRecord = vt040002dao.countTotalRecord(stationeryInfo);
				reb.setData(totalRecord);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

				logger.info("*************** End count total record of list report stationeries - OK ***************");
			}
		} catch (Exception e) {
			logger.info("*************** End count total record of list report stationeries - Fail ***************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * insert Stationery
	 * 
	 * @param VT040002Stationery
	 * @param Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase insertStationery(VT040002Stationery stationeryInfo, Collection<GrantedAuthority> listRole)
			throws Exception {

		logger.info("**************** Start processing insert stationery ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {
				logger.info("**************** Check duplicate stationery name ****************");

				int isDuplicate = vt040002dao.checkDuplicateName(stationeryInfo);

				if (isDuplicate <= 0) {
					logger.info("**************** Insert stationery ****************");

					int statusInsert = vt040002dao.insertStationery(stationeryInfo);

					if (statusInsert > 0) {
						reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

						logger.info("**************** End processing insert stationery - OK ****************");
					} else {
						logger.info(END_INSERT_STATIONERY_FAIL);
					}
				} else {
					reb.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);

					logger.info("**************** Duplicate stationery name ****************");
					logger.info(END_INSERT_STATIONERY_FAIL);
				}
			}
		} catch (Exception e) {
			logger.info(END_INSERT_STATIONERY_FAIL);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	public ResponseEntityBase handleUpdateStationery(ResponseEntityBase reb, VT040002Stationery stationeryInfo)
			throws Exception {
		logger.info("**************** Check duplicate stationery name ****************");

		List<Integer> listStationeryProcess = new ArrayList<>();
		if (stationeryInfo.getStatus().equals("0")) {
			listStationeryProcess = vt040002dao.getStationeryProcess(stationeryInfo.getStationeryId());
		}
		if (listStationeryProcess.size() > 0) {
			for (int element : listStationeryProcess) {
				if (element == 6) {
					reb.setStatus(Constant.RESPONSE_STATUS_NO_ROLE);
					return reb;
				}
			}
		}
		int isDuplicate = vt040002dao.checkDuplicateName(stationeryInfo);

		if (isDuplicate <= 0) {
			logger.info("**************** Update stationery ****************");

			int statusUpdate = vt040002dao.updateStationery(stationeryInfo);

			if (statusUpdate > 0) {
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

				logger.info("**************** End processing update stationery - OK ****************");
			} else {
				logger.info(END_UPDATE_STATIONERY_FAIL);
			}
		} else {
			reb.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);

			logger.info("**************** Duplicate stationery name ****************");
			logger.info(END_UPDATE_STATIONERY_FAIL);
		}

		return reb;
	}

	/**
	 * update Stationery
	 * 
	 * @param VT040002Stationery
	 * @param Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase updateStationery(VT040002Stationery stationeryInfo, Collection<GrantedAuthority> listRole)
			throws Exception {

		logger.info("**************** Start processing update stationery ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {

				// check status delete
				int satusDelete = vt040002dao.checkDeletedStatus(stationeryInfo);
				logger.info("****************satusDelete:" + satusDelete);

				if (satusDelete == Constant.DELETE_FLAG) {
					reb.setStatus(Constant.RESPONSE_STATIONERY_DELETED);
					return reb;
				}
				logger.info("**************** Check existed stationery ****************");

				int isExisted = vt040002dao.checkExistStationery(stationeryInfo);

				if (isExisted > 0) {
					reb = handleUpdateStationery(reb, stationeryInfo);

				} else {
					reb.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);

					logger.info("**************** Stationery is not existed ****************");
					logger.info(END_UPDATE_STATIONERY_FAIL);
				}
			}
		} catch (Exception e) {
			logger.info(END_UPDATE_STATIONERY_FAIL);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	public ResponseEntityBase handleDeleteStationery(ResponseEntityBase reb, VT040002Stationery stationeryInfo)
			throws Exception {
		logger.info("**************** Delete stationery ****************");
		List<Integer> listStationeryProcess = new ArrayList<>();
		listStationeryProcess = vt040002dao.getStationeryProcess(stationeryInfo.getStationeryId());
		if (listStationeryProcess.size() > 0) {
			for (int element : listStationeryProcess) {
				if (element == 6) {
					reb.setStatus(Constant.RESPONSE_STATUS_NO_ROLE);
					return reb;
				}
			}
		}
		int statusDelete = vt040002dao.deleteStationery(stationeryInfo);

		if (statusDelete > 0) {
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End processing delete stationery - OK ****************");
		} else {
			logger.info(END_DELETE_STATIONERY_FAIL);
		}

		return reb;
	}

	/**
	 * delete Stationery
	 * 
	 * @param VT040002Stationery
	 * @param Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase deleteStationery(VT040002Stationery stationeryInfo, Collection<GrantedAuthority> listRole)
			throws Exception {

		logger.info("**************** Start processing update stationery ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {
				logger.info("**************** Check existed stationery ****************");

				int isExisted = vt040002dao.checkExistStationery(stationeryInfo);

				if (isExisted > 0) {
					reb = handleDeleteStationery(reb, stationeryInfo);
				} else {
					reb.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);

					logger.info("**************** Stationery is not existed ****************");
					logger.info(END_DELETE_STATIONERY_FAIL);
				}
			}
		} catch (Exception e) {
			logger.info(END_DELETE_STATIONERY_FAIL);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}
	
	@Override
	@Transactional
	public ResponseEntityBase checkDuplicateName(VT040002Stationery stationeryInfo)
			throws Exception {

		logger.info("**************** Start processing insert stationery ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			int isDuplicate = vt040002dao.checkDuplicateName(stationeryInfo);
				reb.setData(isDuplicate);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			
		} catch (Exception e) {
			logger.info(END_INSERT_STATIONERY_FAIL);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

}
