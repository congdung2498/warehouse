package com.viettel.vtnet360.vt04.vt040002.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt04.vt040002.entity.VT040002Stationery;
import com.viettel.vtnet360.vt04.vt040002.entity.VT040002SystemCode;

/**
 * interface dao get data from database VT040002
 * 
 * @author ThangBT 18/10/2018
 *
 */

@Repository
public interface VT040002DAO {

	/**
	 * get stationery name and id by character input
	 * 
	 * @param stationeryName
	 * @return list of stationery id and name
	 */
	public List<VT040002Stationery> findListStationery(VT040002Stationery stationeryInfo);

	/**
	 * get type and unit calculation of stationery from M_SYSTEM_CODE table
	 * 
	 * @param masterClass
	 * @return type and unit calculation of stationery
	 */
	public List<VT040002SystemCode> findListStationeryTypeOrUnit(String masterClass);

	/**
	 * get list report stationery
	 * 
	 * @param stationeryInfo
	 * @return list stationery
	 */
	public List<VT040002Stationery> findStationeryReport(VT040002Stationery stationeryInfo);

	/**
	 * get total record of list stationery
	 * 
	 * @param stationeryInfo
	 * @return number of record
	 */
	public int countTotalRecord(VT040002Stationery stationeryInfo);

	/**
	 * check exist stationery or not to update or delete, > 0 is existed, otherwise
	 * not existed
	 * 
	 * @param stationeryInfo
	 * @return number
	 */
	public int checkExistStationery(VT040002Stationery stationeryInfo);

	/**
	 * check duplicate stationery name, <= 0 is not duplicate, otherwise duplicate
	 * 
	 * @param stationeryInfo
	 * @return number
	 */
	public int checkDuplicateName(VT040002Stationery stationeryInfo);
	
	
	/**
	 * check Deleted Status
	 * 
	 * @param stationeryInfo
	 * @return int
	 */
	public int checkDeletedStatus(VT040002Stationery stationeryInfo);

	/**
	 * insert stationery, > 0 is OK
	 * 
	 * @param stationeryInfo
	 * @return status after inserting
	 */
	public int insertStationery(VT040002Stationery stationeryInfo);

	/**
	 * check stationeries are used in requesting or not
	 * 
	 * @param stationeryInfo
	 * @return number
	 */
	public int checkStationeryRequesting(VT040002Stationery stationeryInfo);

	/**
	 * update stationery, > 0 is OK
	 * 
	 * @param stationeryInfo
	 * @return status after updating
	 */
	public int updateStationery(VT040002Stationery stationeryInfo);

	/**
	 * delete stationery, > 0 is OK
	 * 
	 * @param stationeryInfo
	 * @return status after deleting
	 */
	public int deleteStationery(VT040002Stationery stationeryInfo);
	
	public List<Integer> getStationeryProcess(String stationeryId);
}
