package com.viettel.vtnet360.vt03.vt030004.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt03.vt030002.entity.Cars;
import com.viettel.vtnet360.vt03.vt030004.entity.Car;
import com.viettel.vtnet360.vt03.vt030004.entity.DriveCar;

/**
 * Class DAO for screen VT030003 : matched car
 * 
 * @author SonVSH 17/09/2018
 */
@Repository
@Transactional
public interface VT030004DAO {
	/**
	 * Match cars for drivers
	 * 
	 * @param listCarDrive
	 * @return ResponseEntityBase
	 */
	public int insertCarDrive(DriveCar driveCar);

	/**
	 * Get ID of car
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	public List<Car> getCarId(DriveCar driveCar);

	/**
	 * Find cars had been matched for current driver
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	public List<DriveCar> findMatchedCar(DriveCar driveCar);

	/**
	 * Delete matched car of current driver
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	public int deleteMatchedCar(DriveCar driveCar);

	/**
	 * Find cars had been matched for current driver
	 * 
	 * @param driveCar
	 * @return ResponseEntityBase
	 */
	public List<Cars> findSuggestCar(DriveCar driveCar);
}
