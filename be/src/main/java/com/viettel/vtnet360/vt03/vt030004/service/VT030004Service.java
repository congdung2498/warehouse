package com.viettel.vtnet360.vt03.vt030004.service;

import java.util.List;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030004.entity.DriveCar;

/**
 * Class service for screen VT030003 : matched car
 * 
 * @author SonVSH 17/09/2018
 */
public interface VT030004Service {
	/**
	 * Match cars for drivers
	 * 
	 * @param listCarDrive
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase insertCarDrive(List<DriveCar> driveCar);

	/**
	 * Get ID of car
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase getCarId(DriveCar driveCar);

	/**
	 * Find cars had been matched for current driver
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findMatchedCar(DriveCar driveCar);

	/**
	 * Delete matched car of current driver
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase deleteMatchedCar(DriveCar driveCar);

	/**
	 * Find cars had been matched for current driver
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findSuggestCar(DriveCar driveCar);
}
