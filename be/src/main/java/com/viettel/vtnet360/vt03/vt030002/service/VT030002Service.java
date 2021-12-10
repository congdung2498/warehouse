package com.viettel.vtnet360.vt03.vt030002.service;

import com.viettel.vtnet360.driving.dto.SearchData;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030002.entity.Cars;

/**
 * Class service for screen VT030002 : add list car
 * 
 * @author SonVSH 17/09/2018
 */

public interface VT030002Service {

	/**
	 * Select all car in list cars
	 * 
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase selectallCars();

	/**
	 * Search car with some conditions
	 * 
	 * @param car
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase searchCar(Cars car);

	/**
	 * Select all seats of car
	 * 
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase getCarSeat();

	/**
	 * Select all types of car
	 * 
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase getCarType();
	public ResponseEntityBase getCarType2();
	
	public ResponseEntityBase getCarTypeById(SearchData searchData);
	
	public ResponseEntityBase getCarSeatById(SearchData searchData);


	/**
	 * Select all squad to set up car
	 * 
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase getSquad();

	/**
	 * Add new cars
	 * 
	 * @param car
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase insertCars(Cars car);

	/**
	 * Update information of cars
	 * 
	 * @param car
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase updateCars(Cars car);

	/**
	 * Delete cars from list car
	 * 
	 * @param car
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase deleteCar(Cars car) throws Exception;
}
