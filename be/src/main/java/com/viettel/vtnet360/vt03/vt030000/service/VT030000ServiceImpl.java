package com.viettel.vtnet360.vt03.vt030000.service;

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
import com.viettel.vtnet360.vt03.vt030000.dao.VT030000DAO;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000NotifySmsInfo;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000ResponseBookCarRequest;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;

/**
 * Class business of VT030000
 * 
 * @author ThangBT 11/09/2018
 *
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030000ServiceImpl implements VT030000Service {

	@Autowired
	private VT030000DAO vt030000dao;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * Find All Resquest pendding Corresponding role of user
	 * 
	 * @param VT030000ResponseBookCarRequest
	 *            obj
	 * @return ResponseEntityBase
	 * @throws Exception
	 * @author CuongHD
	 */
	@Override
	@Transactional(readOnly=true)
	public ResponseEntityBase findBookCarRequest(VT030000ResponseBookCarRequest obj) throws Exception {
		logger.info("********************* START EXECUTE APIVT030000_02 ***********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		VT030000ResponseBookCarRequest data = null;
		try {
			obj.setMasterClassRoute(Constant.MASTER_CLASS_ROUTE_CAR);
			obj.setMasterClassSeat(Constant.MASTER_CLASS_SEAT_CAR);
			obj.setMasterClassType(Constant.MASTER_CLASS_TYPE_CAR);
			data = vt030000dao.findBookCarRequest(obj);
			if(data == null) {
				return new ResponseEntityBase(Constant.DRIVER_NOT_EXISTED, null);
			}
			String unitName  = vt030000dao.selectUnitLevel(data.getUnitId());
			data.setUnitName(unitName);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("********************* START EXECUTE APIVT030000_02 ***********************");
		return response;
	}

	/**
	 * find all squad
	 * 
	 * @return List<VT030000EntityDriveSquad>
	 * @param VT030000EntityDriveSquad squadInfo
	 * @throws Exception
	 * @author ThangBT
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findAllSquad(VT030000EntityDriveSquad squadInfo) throws Exception {

		logger.info("**************** Start get list of drive squad ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT030014ListCondition> listDS = new ArrayList<>();

		try {
			squadInfo.setPageNumber(squadInfo.getPageNumber() * squadInfo.getPageSize());
			listDS = vt030000dao.findAllSquad(squadInfo);
			reb.setData(listDS);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list of drive squad - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list of drive squad - Fail ****************");

			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * find info od car route to send notify and send SMS
	 * 
	 * @param VT030000NotifySmsInfo obj
	 * @return VT030000NotifySmsInfo
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public VT030000NotifySmsInfo findInfoNotifySms(VT030000NotifySmsInfo obj) throws Exception {
		VT030000NotifySmsInfo data = null;
		try {
			obj.setMasterClassRoute(Constant.MASTER_CLASS_ROUTE_CAR);
			obj.setMasterClassSeat(Constant.MASTER_CLASS_SEAT_CAR);
			obj.setMasterClassType(Constant.MASTER_CLASS_TYPE_CAR);
			data = vt030000dao.findInfoNotifySms(obj);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return data;
	}

	/**
	 * Update all Bookcar's request timeout
	 * 
	 * @return int 
	 * @throws Exception
	 */
	@Override
	@Transactional
	public int updateStatusRequestTimeOut() throws Exception {
		logger.info("**************** SCHEDULE MODULE 3 START ****************");
		try {	
			vt030000dao.updateStatusDriveCarTimeOut();
			vt030000dao.updateStatusDriverTimeOut();
			vt030000dao.updateStatusCarTimeOut();
			vt030000dao.updateStatusRequestTimeOut();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("**************** SCHEDULE MODULE 3 END ****************");
		return 0;
	}

	@Override
	public ResponseEntityBase findAllSquadById(SearchData searchData) throws Exception {
		logger.info("**************** Start get list of findAllSquadById ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT030014ListCondition> listDS = new ArrayList<>();

		try {
			
			listDS = vt030000dao.findAllSquadById(searchData);
			reb.setData(listDS);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list of findAllSquadById - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list of findAllSquadById - Fail ****************");

			logger.error(e.getMessage(), e);
		}

		return reb;
	}

}
