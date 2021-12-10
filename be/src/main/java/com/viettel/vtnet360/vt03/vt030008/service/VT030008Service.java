package com.viettel.vtnet360.vt03.vt030008.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030008.entity.VT030008DriveCarInfo;
import com.viettel.vtnet360.vt03.vt030008.entity.VT030008RequestMatchCar;
import com.viettel.vtnet360.vt03.vt030008.entity.VT030008ResponseCars;

/**
 * 
 * @author CuongHD
 *
 */
public interface VT030008Service {

	/**
	 * Find Driver freedom
	 * 
	 * @param VT030008RequestMatchCar obj
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase findFreeDriving(VT030008RequestMatchCar obj) throws Exception;
	
	/**
	 * Take the car not working corresponding to the driver
	 * 
	 * @param VT030008ResponseCars obj
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase findCarMatchDriver(VT030008ResponseCars obj) throws Exception;
	
	/**
	 * Matching car with it's Own
	 * 
	 * @param VT030008DriveCarInfo obj
	 * @param Collection<GrantedAuthority> roleList
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase matchCarAndDrive(VT030008DriveCarInfo obj, Collection<GrantedAuthority> roleList) throws Exception;
	
	/**
	 * 
	 * @param VT030008DriveCarInfo obj
	 * @param Collection<GrantedAuthority> roleList
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase cancelMatching(VT030008DriveCarInfo obj, Collection<GrantedAuthority> roleList) throws Exception;
}
