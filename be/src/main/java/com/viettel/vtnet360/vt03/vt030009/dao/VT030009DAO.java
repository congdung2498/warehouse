package com.viettel.vtnet360.vt03.vt030009.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt03.vt030007.entity.VT030007InforDetailMergeCar;
import com.viettel.vtnet360.vt03.vt030009.entity.VT030009RequestGetUser;
import com.viettel.vtnet360.vt03.vt030009.entity.VT030009ResponseCarRoute;
import com.viettel.vtnet360.vt03.vt030009.entity.VT030009UpdateProcessCarRoute;
import com.viettel.vtnet360.vt03.vt030009.entity.VT030009UserInfor;

/**
 * interface VT030009DAO
 * 
 * @author CuongHD
 *
 */
@Repository
public interface VT030009DAO {
	/**
	 * Update process for car's route
	 * 
	 * @param VT030009UpdateProcessCarRoute obj
	 * @return int
	 */
	public int updateCarRoute(VT030009UpdateProcessCarRoute obj);
	
	/**
	 * Get information of route
	 * 
	 * @param VT030009UpdateProcessCarRoute obj
	 * @return VT030009ResponseCarRoute
	 */
	public VT030009ResponseCarRoute findCarRoute(VT030009UpdateProcessCarRoute obj);
	
	/**
	 * Tim kiem tat ca yeu cau da duoc xep xe, den vi tri, hoan thanh
	 * 
	 * @param VT030009ResponseCarRoute obj
	 * @return List<VT030009ResponseCarRoute>
	 */
	public List<VT030009ResponseCarRoute> getListRequest(VT030009ResponseCarRoute obj);
	
	/**
	 * Tim kiem tat ca yeu cau da ghep xe
	 * 
	 * @param VT030009ResponseCarRoute obj
	 * @return List<VT030009ResponseCarRoute>
	 */
	public List<VT030009ResponseCarRoute> getListRequestMergeCar(VT030009ResponseCarRoute obj);
	
	/**
	 * Update STATUS, PROCESS_STATUS in CARS table
	 * 
	 * @param VT030009UpdateProcessCarRoute obj
	 * @return int
	 */
	public int updateStateCar(VT030009UpdateProcessCarRoute obj);
	
	/**
	 * Update STATUS, PROCESS_STATUS in DRIVE table
	 * 
	 * @param VT030009UpdateProcessCarRoute obj
	 * @return int 
	 */
	public int updateStateDriver(VT030009UpdateProcessCarRoute obj);
	
	/**
	 * Update PROCESS_STATUS in DRIVE_CAR
	 * 
	 * @param VT030009UpdateProcessCarRoute obj
	 * @return integer
	 */
	public int updateStateDriveCar(VT030009UpdateProcessCarRoute obj);
	
	/**
	 * find username of PMQT_QL_Doi_xe
	 * 
	 * @param String usessrName
	 * @return String
	 */
	public String findManager(String usessrName);
	
	public String findEmployee(String bookingId);
	
	public List<VT030009UserInfor> getUserInfor(VT030009RequestGetUser requestGetUser);
	
	public VT030007InforDetailMergeCar getInforDetailMergeCar(VT030009RequestGetUser requestGetUser);
	
	public List<VT030009UpdateProcessCarRoute> getInforUpdateProcessCarRoute(VT030009ResponseCarRoute carRoute);
}
