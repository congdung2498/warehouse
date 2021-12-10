package com.viettel.vtnet360.vt03.vt030003.service;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030000.entity.Employee;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030003.entity.Drive;
import com.viettel.vtnet360.vt03.vt030003.entity.DriveInsert;

/**
 * Class service for screen VT030003 : add list driver
 * 
 * @author SonVSH 17/09/2018
 */
public interface VT030003Service {

	/**
	 * Select all drivers in list driver
	 * 
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase getAllDriver();

	/**
	 * Find employee with role is driver
	 * 
	 * @param driver
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findDriver(Employee driver);

	/**
	 * Select drive list drive
	 * 
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase getListDrive();

	/**
	 * Select all drive squad in list squad
	 * 
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase getSquad();

	/**
	 * Select drive with some conditions
	 * 
	 * @param drive
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase searchData(DriveInsert driveInsert,Principal principal);

	/**
	 * Find drive squad in list squad
	 * 
	 * @param squad
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findSquad(VT030000EntityDriveSquad squad,Principal principal);

	/**
	 * Add new drive
	 * 
	 * @param drive
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase insertDrive(DriveInsert drive);

	/**
	 * Update drive
	 * 
	 * @param drive
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase updateDrive(DriveInsert drive);

	/**
	 * Delete drive
	 * 
	 * @param drive
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase deleteDrive(Drive drive);
	/**
	 * find drive
	 * 
	 * @param drive
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findDrive(DriveInsert drive);

	
	
}
