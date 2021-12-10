package com.viettel.vtnet360.vt03.vt030002.service;

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
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030002.dao.VT030002DAO;
import com.viettel.vtnet360.vt03.vt030002.entity.Cars;
import com.viettel.vtnet360.vt03.vt030002.entity.Seat;
import com.viettel.vtnet360.vt03.vt030002.entity.Type;
import com.viettel.vtnet360.vt03.vt030002.service.VT030002Service;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;

/**
 * Class service for screen VT030002 : add list car
 * 
 * @author SonVSH 17/09/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030002ServiceImpl implements VT030002Service {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private VT030002DAO vt030002dao;

	/**
	 * Select all car in list cars
	 * 
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase selectallCars() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Cars> listcar = null;
		List<Type> listType = null;
		List<Seat> listSeat = null;
		Cars tempCar = new Cars();
		try {
			listcar = vt030002dao.selectallCars();
			listSeat = vt030002dao.getCarSeat2();
			listType = vt030002dao.getCarType2();
			for (Cars car : listcar) {
				tempCar = car;
				for (Type type : listType) {
					if (tempCar.getType().equals(type.getTypeId() + "")) {
						tempCar.setType(type.getType());
						break;
					}
				}
				car.setType(tempCar.getType());
			}

			for (Cars car : listcar) {
				tempCar = car;
				for (Seat seat : listSeat) {
					if (tempCar.getSeat().equals(seat.getSeatId() + "")) {
						tempCar.setSeat(seat.getSeat());
						break;
					}
				}

				car.setSeat(tempCar.getSeat());
			}
			resp.setData(listcar);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	/**
	 * Add new cars
	 * 
	 * @param car
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase insertCars(Cars car) {
		logger.info("************************* INSERT ***********************");
		List<Type> listType = null;
		List<Seat> listSeat = null;
		List<Cars> listCar = null;
		int checked = 0;
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		listCar = vt030002dao.selectallCars();
		for (Cars item : listCar) {
			if (item.getLicensePlate().equals(car.getLicensePlate())) {
				checked = 1;
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				resp.setData(null);
				break;
			}
		}
		if (checked == 0) {
			try {
				listType = vt030002dao.getCarType2();
				listSeat = vt030002dao.getCarSeat2();
				for (Type item : listType) {
					if (item.getType().equals(car.getType())) {
						car.setType(item.getTypeId());
					}
				}

				for (Seat item : listSeat) {
					if (item.getSeat().equals(car.getSeat())) {
						car.setSeat(item.getSeatId());
					}
				}

				int i = vt030002dao.insertCars(car);
				if (i == 1) {
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
		}
		logger.info("************************* INSERT ***********************");
		return resp;
	}

	/**
	 * Select all squad to set up car
	 * 
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase getSquad() {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT030000EntityDriveSquad> listsquad = null;
		try {
			listsquad = vt030002dao.getSquad();
			resp.setData(listsquad);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	/**
	 * Select all seats of car
	 * 
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase getCarSeat() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Seat> listCarSeat = null;
		try {
			listCarSeat = vt030002dao.getCarSeat2();
			resp.setData(listCarSeat);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	/**
	 * Select all types of car
	 * 
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase getCarType() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT030014ListCondition> listCarType = null;
		try {
			listCarType = vt030002dao.getCarType();
			resp.setData(listCarType);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}

		return resp;
	}
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase getCarType2() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Type> listCarType = null;
		try {
			listCarType = vt030002dao.getCarType2();
			resp.setData(listCarType);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}

		return resp;
	}

	/**
	 * Update information of cars
	 * 
	 * @param car
	 * @return ResponseEntityBase
	 */
	@Override
	public ResponseEntityBase updateCars(Cars car) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		int processStatus = -1;
		processStatus = vt030002dao.getProcessStatus(car.getCarId());
		if (car.getStatus() == 0) {
			if (processStatus != 7 && processStatus != 0) {
				resp.setStatus(Constant.STATUS_IS_RUNING_CAR);
				return resp;
			}
		}
		List<Cars> listCar = null;
		int checked = 0;
		listCar = vt030002dao.getallCars();
		int status = vt030002dao.checkDeleted(car);
		if (status == 1) {
			resp.setStatus(Constant.ERROR_UPDATE);
			return resp;
		}

		for (Cars item : listCar) {
			if (item.getLicensePlate().equals(car.getLicensePlate()) && !item.getCarId().equals(car.getCarId())) {
				checked++;
			}
		}

		if (checked == 0) {
			try {
				int i = vt030002dao.updateCars(car);
				if (i == 1) {
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
		} else {
			resp.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);
			resp.setData(null);
		}
		return resp;
	}

	/**
	 * Search car with some conditions
	 * 
	 * @param car
	 * @return ResponseEntityBase
	 */
	@Override
	public ResponseEntityBase searchCar(Cars car) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Cars> listcar = null;
		List<Type> listType = null;
		List<Seat> listSeat = null;
		Cars tempCar = new Cars();
		try {
			listcar = vt030002dao.searchCar(car);
			listSeat = vt030002dao.getCarSeat2();
			listType = vt030002dao.getCarType2();
			for (Cars item : listcar) {
				tempCar = item;
				for (Type type : listType) {
					if (tempCar.getType().equals(type.getTypeId() + "")) {
						tempCar.setType(type.getType());
						break;
					}
				}

				item.setType(tempCar.getType());
			}

			for (Cars item : listcar) {
				tempCar = item;
				for (Seat seat : listSeat) {
					if (tempCar.getSeat().equals(seat.getSeatId() + "")) {
						tempCar.setSeat(seat.getSeat());
						break;
					}
				}

				item.setSeat(tempCar.getSeat());
			}
			resp.setData(listcar);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	/**
	 * Delete cars from list car
	 * 
	 * @param car
	 * @return ResponseEntityBase
	 */
	@Override
	public ResponseEntityBase deleteCar(Cars car) throws Exception {
		System.out.println("DELELE");
		logger.info("******************* Delete *****************************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		System.out.println("CHECKKKACTIVEEEDR = " + vt030002dao.checkActiveCar(car.getLicensePlate()));
		System.out.println("CHUAAA VAO DELETE ROI");
		int status = vt030002dao.checkDeleted(car);
		if (status == 1) {
			response.setStatus(Constant.ERROR_UPDATE);
			return response;
		}

		try {
			if (vt030002dao.checkActiveCar(car.getLicensePlate()) == 0) {
				System.out.println("VAO DELETE ROI");
				if (vt030002dao.deleteCar(car) > 0) {
					response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
				} else {
					response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
				}
			} else {
				response = new ResponseEntityBase(Constant.STATUS_IS_RUNING_CAR, null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			System.out.println("EXCEPTION");
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("******************* Delete *****************************");
		return response;
	}

	@Override
	public ResponseEntityBase getCarTypeById(SearchData searchData) {
		logger.info("**************** Start get list of findAllSquadById ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT030014ListCondition> listDS = new ArrayList<>();

		try {
			
			listDS = vt030002dao.getCarTypeById(searchData);
			reb.setData(listDS);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list of findAllSquadById - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list of findAllSquadById - Fail ****************");

			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	public ResponseEntityBase getCarSeatById(SearchData searchData) {
		logger.info("**************** Start get list of getCarSeatById ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT030014ListCondition> listDS = new ArrayList<>();

		try {
			
			listDS = vt030002dao.getCarSeatById(searchData);
			reb.setData(listDS);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list of getCarSeatById - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list of getCarSeatById - Fail ****************");

			logger.error(e.getMessage(), e);
		}

		return reb;
	}

}
