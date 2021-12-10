package com.viettel.vtnet360.vt03.vt030005.service;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityBookCar;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityBookCarResult;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityPlace;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000ResponseBookCarRequest;

/**
 * interface VT030005Service
 * 
 * @author CuongHD
 *
 */
public interface VT030005Service {
	/**
	 * Find place
	 * 
	 * @param VT030000EntityPlace obj
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findPlaceStart(VT030000EntityPlace obj) throws Exception;
	
	/**
	 * Insert new BookCar's Request
	 * 
	 * @param VT030000EntityBookCar obj
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase insertRequest(VT030000ResponseBookCarRequest obj) throws Exception;
	
	/**
	 * Insert new BookCar's Request
	 * 
	 * @param VT030000EntityBookCar obj
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase insertRequestResult(VT030000EntityBookCarResult obj) throws Exception;

}
