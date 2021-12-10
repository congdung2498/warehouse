package com.viettel.vtnet360.vt03.vt030006.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt03.vt030006.entity.VT030006UpdateBookCar;

/**
 * 
 * @author CuongHD
 *
 */
@Repository
@Transactional
public interface VT030006DAO {
	/**
	 * do approve and do refuse
	 * 
	 * @param VT030006UpdateBookCar obj
	 * @return integer
	 */
	public int updateRequest(VT030006UpdateBookCar obj);
	
	/**
	 * find all manager have permission to approve or refuse 
	 * 
	 * @param String id
	 * @return VT030006UpdateBookCar
	 */
	public VT030006UpdateBookCar findAllLevelManager(String id);

	/**
	 * Find Next Approver
	 * 
	 * @param String id
	 * @return String
	 */
	public String findNextApprover(String id);
	
	/**
	 * Find user request
	 * 
	 * @param String id
	 * @return String
	 */
	public String findUserRequest(String id);
	
	/**
	 * Find manager car's squad
	 * 
	 * @param String id
	 * @return String
	 */
	public String findManagerCarSquad(String id);
	
	/**
	 * get timeEnd of record
	 * 
	 * @param String id
	 * @return long
	 */
	public Long getDateEndOfReqest(String id);
}
