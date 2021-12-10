package com.viettel.vtnet360.vt03.vt030011.dao;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt03.vt030011.entity.VT030011CancelRequest;

/**
 * interface VT030011DAO
 * 
 * @author CuongHD
 *
 */
@Repository
public interface VT030011DAO {
	/**
	 * do cancel request
	 * 
	 * @param VT030011CancelRequest obj
	 * @return int
	 */
	public int updateRequest(VT030011CancelRequest obj);
	
	/**
	 * get status of request
	 * 
	 * @param VT030011CancelRequest obj
	 * @return int
	 */
	public int getStatusRequest(VT030011CancelRequest obj);
	
	/**
	 * Update status for Drive
	 * 
	 * @param VT030011CancelRequest obj
	 * @return int 
	 */
	public int updateStatusDriveAfterCancel(VT030011CancelRequest obj);
	
	/**
	 * Update status for Car
	 * 
	 * @param VT030011CancelRequest obj
	 * @return int 
	 */
	public int updateStatusCarAfterCancel(VT030011CancelRequest obj);
	
	/**
	 * Update status for DriveCar
	 * 
	 * @param VT030011CancelRequest obj
	 * @return int 
	 */
	public int updateStatusDriveCarAfterCancel(VT030011CancelRequest obj);
}
