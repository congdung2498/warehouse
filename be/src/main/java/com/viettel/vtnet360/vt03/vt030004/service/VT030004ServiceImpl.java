package com.viettel.vtnet360.vt03.vt030004.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030002.entity.Cars;
import com.viettel.vtnet360.vt03.vt030004.dao.VT030004DAO;
import com.viettel.vtnet360.vt03.vt030004.entity.Car;
import com.viettel.vtnet360.vt03.vt030004.entity.DriveCar;

/**
 * Class serviceImpl for screen VT030003 : matched car
 * 
 * @author SonVSH 17/09/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030004ServiceImpl implements VT030004Service {

	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private VT030004DAO vt030004dao;

	/**
	 * Match cars for drivers
	 * 
	 * @param listCarDrive
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase insertCarDrive(List<DriveCar> listCarDrive) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		int checked = 0;
		vt030004dao.deleteMatchedCar(listCarDrive.get(0));

		List<Car> listCarId = vt030004dao.getCarId(listCarDrive.get(0));
		for (DriveCar dc : listCarDrive) {
			if (checked == 1)
				break;
			for (Car car : listCarId) {
				if (car.getCarId().equals(dc.getCarId())) {
					checked = 1;
					break;
				}
			}
		}

		if (checked == 1) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			resp.setData(null);
			return resp;
		} else {
			try {
				for (DriveCar dc : listCarDrive) {
					int i = vt030004dao.insertCarDrive(dc);
					if (i > 0) {
						resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
					} else {
						resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
					}
				}
			} catch (Exception e) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				resp.setData(null);
				logger.error(e.getMessage(), e);
				throw (e);
			}
		}
		return resp;
	}

	
	/**
	 * Get ID of car
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	@Override
	public ResponseEntityBase getCarId(DriveCar driveCar) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Car> listCarId = null;
		try {
			listCarId = vt030004dao.getCarId(driveCar);
			resp.setData(listCarId);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			resp.setData(null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	
	/**
	 * Find cars had been matched for current driver
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	@Override
	public ResponseEntityBase findMatchedCar(DriveCar driveCar) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<DriveCar> listMatchedCar = null;
		try {
			listMatchedCar = vt030004dao.findMatchedCar(driveCar);
			resp.setData(listMatchedCar);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			resp.setData(null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	/**
	 * Delete matched car of current driver
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	@Override
	public ResponseEntityBase deleteMatchedCar(DriveCar driveCar) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			int i = vt030004dao.deleteMatchedCar(driveCar);
			if (i >= 1) {
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				return resp;
			} else {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			resp.setData(null);
		}
		return resp;
	}
	
	/**
	 * Find suggest car in driver's squad
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	@Override
	public ResponseEntityBase findSuggestCar(DriveCar driveCar) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Cars> listSuggested = null;
		try {
			listSuggested = vt030004dao.findSuggestCar(driveCar);
			resp.setData(listSuggested);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			resp.setData(null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

}
