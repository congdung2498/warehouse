package com.viettel.vtnet360.vt03.vt030008.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt03.vt030008.entity.VT030008DriveCarInfo;
import com.viettel.vtnet360.vt03.vt030008.entity.VT030008RequestMatchCar;
import com.viettel.vtnet360.vt03.vt030008.entity.VT030008ResponseCars;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;

/**
 * interface VT030008DAO
 * 
 * @author CuongHD
 *
 */
@Repository
public interface VT030008DAO {

	/**
	 * Find All FreeDriving
	 * 
	 * @param VT030008RequestMatchCar obj
	 * @return List<VT030008RequestMatchCar>
	 */
	public List<VT030008RequestMatchCar> findFreeDriving(VT030008RequestMatchCar obj);
	
	/**
	 * Find All Car
	 * 
	 * @param VT030008ResponseCars obj
	 * @return List<VT030008ResponseCars>
	 */
	public List<VT030008ResponseCars> findCarMatchDriver(VT030008ResponseCars obj);
	
	/**
	 * Get status of request
	 * 
	 * @param String bookCarId
	 * @return int
	 */
	public int getStatus(String bookCarId);
	
	/**
	 * get UserDriver of request
	 * 
	 * @param String bookCarId
	 * @return String
	 */
	public String getUserDriver(String bookCarId);
	
	/**
	 * Update PROCESS_STATUS in DRIVE_CAR
	 * 
	 * @param VT030008DriveCarInfo obj
	 * @return int
	 */
	public int updateStateDriveCar(VT030008DriveCarInfo obj);
	
	/**
	 * update carId, userDriver, userAssigner in CAR_BOOKING table
	 * 
	 * @param VT030008DriveCarInfo obj
	 * @return int 
	 */
	public int updateCarBooking(VT030008DriveCarInfo obj);
	
	
	/**
	 * update carId, userDriver, userAssigner in CAR_BOOKING table
	 * 
	 * @param VT030008DriveCarInfo obj
	 * @return int 
	 */
	public int updateCarBookingMerge(VT030008DriveCarInfo obj);
	
	/**
	 * Update STATUS, PROCESS_STATUS in CARS table
	 * 
	 * @param VT030008DriveCarInfo obj
	 * @return int
	 */
	public int updateStateCar(VT030008DriveCarInfo obj);
	
	/**
	 * Update STATUS, PROCESS_STATUS in DRIVE table
	 * 
	 * @param VT030008DriveCarInfo obj
	 * @return int 
	 */
	public int updateStateDriver(VT030008DriveCarInfo obj);
	
	/**
	 * Cancel matching car
	 * 
	 * @param VT030008DriveCarInfo obj
	 * @return int
	 */
	public int cancelMatching(VT030008DriveCarInfo obj);
	
	/**
	 * get timeEnd of request
	 * 
	 * @param String id
	 * @return Long
	 */
	public Long getDateEndOfReqest(String id);
	
	/**
	 * check driver is active?
	 * 
	 * @param String driverId
	 * @return int 
	 */
	public int checkDriverIsActive(String driverId);
	
	/**
	 * check Car is active?
	 * 
	 * @param String carId
	 * @return int 
	 */
	public int checkCarIsActive(String carId);
	
	
	public VT030014ListCondition findCarBookingById(String carBookingId);
}
