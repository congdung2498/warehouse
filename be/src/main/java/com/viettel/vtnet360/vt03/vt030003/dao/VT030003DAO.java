package com.viettel.vtnet360.vt03.vt030003.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt03.vt030000.entity.Employee;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030002.entity.Cars;
import com.viettel.vtnet360.vt03.vt030003.entity.Drive;
import com.viettel.vtnet360.vt03.vt030003.entity.DriveInsert;
import com.viettel.vtnet360.vt03.vt030004.entity.DriveCar;

/**
 * @author SonVSH 12/09/2018
 * 
 */
@Repository
@Transactional
public interface VT030003DAO {

	/**
	 * Select all drivers in list driver
	 * 
	 * @return List<Employee>
	 */
	public List<Employee> getAllDriver();

	/**
	 * Find employee with role is driver
	 * 
	 * @param driver
	 * @return List<Employee>
	 */
	public List<Employee> findDriver(Employee driver);

	/**
	 * Select drive list drive
	 * 
	 * @return List<Employee>
	 */
	public List<DriveInsert> getListDrive();

	/**
	 * Select drive with some conditions
	 * 
	 * @param drive
	 * @return List<Drive>
	 */
	public List<Drive> searchData(DriveInsert driveInsert);
	
	public int totalDrive(DriveInsert driveInsert);

	public int updateDriverStatus(Drive drive);

	/**
	 * Select all drive squad in list squad
	 * 
	 * @return List<VT030000EntityDriveSquad>
	 */
	public List<VT030000EntityDriveSquad> getSquad();

	/**
	 * Find drive squad in list squad
	 * 
	 * @param squad
	 * @return List<VT030000EntityDriveSquad>
	 */
	public List<VT030000EntityDriveSquad> findSquad(VT030000EntityDriveSquad squad);

	/**
	 * Add new drive
	 * 
	 * @param drive
	 * @return Integer
	 */
	public int insertDrive(DriveInsert drive);

	/**
	 * Update drive
	 * 
	 * @param drive
	 * @return Integer
	 */
	public int updateDrive(DriveInsert drive);

	/**
	 * Delete drive
	 * 
	 * @param drive
	 * @return Integer
	 */
	public int deleteDrive(Drive drive);

	public int deleteMatchedCar(Drive drive);

	public int checkActiveDrive(String driveId);
	public int checkDeleted(Drive drive);
	
	public int getProcessStatus(String driveId);
	public int insertDriveCar(DriveInsert drive);
	public DriveInsert DriveInsert(DriveInsert driveInsert);

  public DriveInsert detailDrive(DriveInsert drive);

  public List<DriveCar> findListCar(DriveInsert drive);

  public DriveInsert findDriveById(DriveInsert drive);
  
  public int updateDeleteFlag(DriveInsert drive);
}
