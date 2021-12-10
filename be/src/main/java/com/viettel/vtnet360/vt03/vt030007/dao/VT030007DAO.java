package com.viettel.vtnet360.vt03.vt030007.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt03.vt030007.entity.VT030007EntityBookCarInfo;
import com.viettel.vtnet360.vt03.vt030007.entity.VT030007ResquestFindBookCar;

/**
 * 
 * @author CuongHD
 *
 */
@Transactional
public interface VT030007DAO {
	/**
	 * Get list bookcar's request
	 * 
	 * @param VT030007ResquestFindBookCar obj
	 * @return List<VT030007EntityBookCarInfo>
	 */
	public List<VT030007EntityBookCarInfo> findBookCarRequest(VT030007ResquestFindBookCar obj);
	
	public List<VT030007EntityBookCarInfo> findBookCarRequestOrder(VT030007ResquestFindBookCar obj);

	public List<VT030007EntityBookCarInfo> findBookCarList(VT030007ResquestFindBookCar obj);
	
	public List<VT030007EntityBookCarInfo> findBookCarListApprove(VT030007ResquestFindBookCar obj);
}
