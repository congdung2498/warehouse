package com.viettel.vtnet360.vt03.vt030005.dao;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityBookCar;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityBookCarResult;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityPlace;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000ResponseBookCarRequest;

/**
 * interface VT030005DAO
 * 
 * @author CuongHD
 *
 */
@Repository
public interface VT030005DAO {	
	/**
	 * Find place
	 * 
	 * @param VT030000EntityPlace obj
	 * @return List<VT030000EntityPlace
	 */
	public List<VT030000EntityPlace> findPlaceStart(VT030000EntityPlace obj);
	
	/**
	 * Insert new BookCar's Request
	 * 
	 * @param VT030005RespuestBookCarDELETE obj
	 * @return integer
	 */
	public int insertRequest(VT030000EntityBookCar obj);
	public int insertRequestResult(VT030000EntityBookCarResult obj);
	/**
	 * Check duplicate timeStart and timeEnd of the bookcar's request
	 * 
	 * @param VT030000EntityBookCar obj
	 * @return int
	 */
	public int limitRequestBookCar(VT030000ResponseBookCarRequest obj);
	
	public int limitRequestBookCarResult(VT030000EntityBookCarResult obj);
	
}
