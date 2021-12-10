package com.viettel.vtnet360.vt03.vt030000.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.driving.dto.SearchData;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000NotifySmsInfo;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000ResponseBookCarRequest;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;

/**
 * interface map to sqlMap
 * 
 * @author ThangBT 11/09/2018
 * @author CuongHD
 *
 */

@Repository
@Transactional
public interface VT030000DAO {

	/**
	 * Find All Resquest pendding Corresponding role of user
	 * 
	 * @author CuongHD
	 * @param VT030000ResponseBookCarRequest obj
	 * @return List<VT030000ResponseBookCarRequest>
	 */
	public VT030000ResponseBookCarRequest findBookCarRequest(VT030000ResponseBookCarRequest obj);

	/**
	 * get drive squad
	 *  
	 * @author ThangBT
	 * @return List<VT030000EntityDriveSquad>
	 * 
	 */
	public List<VT030014ListCondition> findAllSquad(VT030000EntityDriveSquad squadInfo);
	
	/**
	 * find info of route
	 * 
	 * @param String id
	 * @return VT030000NotifySmsInfo
	 */
	public VT030000NotifySmsInfo findInfoNotifySms(VT030000NotifySmsInfo obj);
	
	/**
	 * Update all Bookcar's request timeout
	 * 
	 * @return int
	 */
	public int updateStatusRequestTimeOut();
	
	/**
	 * Update status time out for car has matched
	 * 
	 * @return int 
	 */
	public int updateStatusCarTimeOut();
	
	/**
	 * 
	 * 
	 * @return int 
	 */
	public int updateStatusDriveCarTimeOut();
	
	/**
	 * Update status time out for Driver has matched
	 * 
	 * @return int
	 */
	public int updateStatusDriverTimeOut();
	
	/**
	 * 
	 * Select levels Unit 
	 * 
	 * @param int unitId
	 * @return String 
	 */
	public String selectUnitLevel(int unitId);
	
	public List<VT030014ListCondition> findAllSquadById(SearchData searchData);

}