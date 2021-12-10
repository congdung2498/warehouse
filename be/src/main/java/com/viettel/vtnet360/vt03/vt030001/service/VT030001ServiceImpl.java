package com.viettel.vtnet360.vt03.vt030001.service;

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
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityEmployee;
import com.viettel.vtnet360.vt03.vt030001.dao.VT030001DAO;

/**
 * 
 * @author CuongHD 09/08/2018
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030001ServiceImpl implements VT030001Service {

	@Autowired
	private VT030001DAO vt030001dao;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * tim kiem truong ban xe theo vi tri va ten
	 * 
	 * @param int placeId
	 * @return ResponseEntityBase
	 */
	@Override
	public ResponseEntityBase findDriver(VT030000EntityEmployee obj) {
		logger.info("******************** START APIVT030001_02 ********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT030000EntityEmployee> data = new ArrayList<>();
		try {
			data = vt030001dao.findDriver(obj);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("******************** END APIVT030001_02 ********************");
		return response;
	}

	/**
	 * Tim kiem thong tin doi xe theo dieu kien
	 * 
	 * @param VT030000EntityDriveSquad object
	 * @return List<DriveSquad>
	 */
	@Override
	@Transactional
	public ResponseEntityBase findSquad(VT030000EntityDriveSquad object) throws Exception {
		logger.info("******************** START APIVT030001_03 ********************");
		ResponseEntityBase response = null;
		List<VT030000EntityDriveSquad> data = new ArrayList<>();
		try {

			int totalRecords = vt030001dao.getTotalRecord();
			data = vt030001dao.findSquad(object);
			if (!data.isEmpty()) {
				if (object.getPageNumber() == 0 && data.size() < object.getPageSize()) {
					totalRecords = data.size();
				}
				data.get(0).setTotalRecords(totalRecords);
			}
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
		}
		logger.info("******************** END APIVT030001_03 ********************");
		return response;

	}

	/**
	 * Insert or Update Squad to database
	 * 
	 * @param VT030000EntityDriveSquad object
	 * @return APIVT030001Message
	 */
	@Override
	@Transactional
	public ResponseEntityBase insertSquad(VT030000EntityDriveSquad object, Collection<GrantedAuthority> roleList)
			throws Exception {
		logger.info("******************** START APIVT030001_04 ********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			if (roleList.isEmpty()) {
				return response;
			}
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_DOIXE))) {
				/** If not contain squadId */
				if (object.getSquadId() != null && object.getSquadId().length() > 0) {
					if (vt030001dao.checkExistedManagerSquad(object.getUserName()) > 0 && !object.getUserName().trim()
							.equalsIgnoreCase(vt030001dao.selectUserManagerCarSquad(object.getSquadId()).trim())) {
						return new ResponseEntityBase(Constant.STATUS_DUPLICATE_MANAGER_CAR_SQUAD, null);
					}
					int status = vt030001dao.getStatus(object.getSquadId());
					int statusRequestSend = Integer.parseInt(object.getStatus());
					if (status > statusRequestSend) {
						if (vt030001dao.isExistActiveCar(object.getSquadId()) > 0) {
							return new ResponseEntityBase(Constant.STATUS_IS_RUNING_CAR, null);
						}
						if (vt030001dao.checkDuplicate1(object) > 0) {
							return new ResponseEntityBase(Constant.STATUS_DUPLICATE_CAR_SQUAD, null);
						}

						if (vt030001dao.update(object) > 0) {
							return new ResponseEntityBase(Constant.REQUEST_ACTION_UPDATE, null);
						} else {
							return new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
						}

					} else {
						int isDelete = vt030001dao.isDelete(object.getSquadId());
						if (isDelete == 1) {
							return new ResponseEntityBase(Constant.ERROR_UPDATE, null);
						}
						if (vt030001dao.checkDuplicate1(object) > 0) {
							return  new ResponseEntityBase(Constant.STATUS_DUPLICATE_CAR_SQUAD, null);
						}

						int rowEffect = vt030001dao.update(object);
						if (rowEffect > 0) {
							return new ResponseEntityBase(Constant.REQUEST_ACTION_UPDATE, null);
						} else {
							return new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
						}
					}

				} else {
					if (vt030001dao.checkDuplicate(object) > 0) {
						return new ResponseEntityBase(Constant.STATUS_DUPLICATE_CAR_SQUAD, null);
					}
					if (vt030001dao.checkExistedManagerSquad(object.getUserName()) > 0) {
						return new ResponseEntityBase(Constant.STATUS_DUPLICATE_MANAGER_CAR_SQUAD, null);
					}

					object.setSquadName(object.getSquadName().trim());
					int rowEffect = vt030001dao.insertDriveSquad(object);
					if (rowEffect > 0) {
						return new ResponseEntityBase(Constant.REQUEST_ACTION_INSERT, null);
					} else {
						return new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
					}
				}
			}

		} catch (

		Exception e) {
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			response.setData(null);
			logger.error(e.getMessage(), e);
		}
		logger.info("******************** END APIVT030001_04 ********************");
		return response;
	}

	/**
	 * Delete a driveSquad
	 * 
	 * @param VT030000EntityDriveSquad object
	 * @return APIVT030001Message
	 */
	@Override
	public ResponseEntityBase deleteSquad(VT030000EntityDriveSquad object, Collection<GrantedAuthority> roleList) throws Exception {
		logger.info("******************** START APIVT030001_05 ********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			if (!roleList.isEmpty()) {
				for (GrantedAuthority grantedAuthority : roleList) {
					if (Constant.PMQT_ROLE_ADMIN.equals(grantedAuthority.getAuthority()) || Constant.PMQT_ROLE_MANAGER_DOIXE.equals(grantedAuthority.getAuthority())) {
						int rowEffect = vt030001dao.isExistActiveCar(object.getSquadId());
						if (rowEffect == 0) {
							int isDelete = vt030001dao.isDelete(object.getSquadId());
							if (isDelete == 1) {
								return new ResponseEntityBase(Constant.ERROR_UPDATE, null);
							}
							rowEffect = vt030001dao.deleteSquad(object);
							if (rowEffect > 0) {
								response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
							} else {
								response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
							}
						} else {
							response = new ResponseEntityBase(Constant.STATUS_IS_RUNING_CAR_TEMP, null);
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			response.setData(null);
			logger.error(e.getMessage(), e);
		}
		logger.info("******************** END APIVT030001_05 ********************");
		return response;
	}

}
