package com.viettel.vtnet360.vt03.vt030002.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.driving.dto.SearchData;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030002.entity.Cars;
import com.viettel.vtnet360.vt03.vt030002.entity.Seat;
import com.viettel.vtnet360.vt03.vt030002.entity.Type;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;

/**
 * @author SonVSH 12/09/2018
 * 
 */
@Repository
@Transactional
public interface VT030002DAO {
	/**
	 * Select all car in list cars
	 * 
	 * @return List<Cars>
	 */
	public List<Cars> selectallCars();

	/**
	 * Get all car in list cars
	 * 
	 * @return List<Cars>
	 */
	public List<Cars> getallCars();

	/**
	 * Search car with some conditions
	 * 
	 * @param car
	 * @return List<Cars>
	 */
	public List<Cars> searchCar(Cars car);

	/**
	 * Select all seats of car
	 * 
	 * @return List<Seat>
	 */
	public List<VT030014ListCondition> getCarSeat();

	/**
	 * Select all types of car
	 * 
	 * @return List<Type>
	 */
	public List<VT030014ListCondition> getCarType();
	
	public List<Type> getCarType2();
	
	public List<Seat> getCarSeat2();

	/**
	 * Select all squad to set up car
	 * 
	 * @return List<VT030000EntityDriveSquad>
	 */
	public List<VT030000EntityDriveSquad> getSquad();

	/**
	 * Add new cars
	 * 
	 * @param car
	 * @return Integer
	 */
	public int insertCars(Cars car);

	/**
	 * Update information of cars
	 * 
	 * @param car
	 * @return Integer
	 */
	public int updateCars(Cars car);

	/**
	 * Check status active of car
	 * 
	 * @param license
	 * @return Integer
	 */
	public int checkActiveCar(String license);

	/**
	 * Delete cars from list car
	 * 
	 * @param car
	 * @return Integer
	 */
	public int deleteCar(Cars car);

	public int getStatus(String carid);

	public int checkDeleted(Cars car);

	public int getProcessStatus(String carId);
	
	public List<VT030014ListCondition> getCarTypeById(SearchData searchData);
	
	public List<VT030014ListCondition> getCarSeatById(SearchData searchData);
}
