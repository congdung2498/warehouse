package com.viettel.vtnet360.vt04.vt040001.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt04.vt040001.entity.VT040001InfoToFindEmployee;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001ListEmployee;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001ListPlace;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001ListService;

/**
 * interface dao get data from database VT040001
 * 
 * @author ThangBT 04/10/2018
 *
 */

@Repository
public interface VT040001DAO {

	/**
	 * get list of places when type character
	 * 
	 * @param placeInfo
	 * @return List<VT040001ListPlace>
	 */
	public List<VT040001ListPlace> findListPlace(VT040001ListPlace placeInfo);

	/**
	 * get list of services when type character
	 * 
	 * @param serviceInfo
	 * @return List<VT040001ListService>
	 */
	public List<VT040001ListService> findListService(VT040001ListService serviceInfo);

	/**
	 * get list units when type character, reused class VT040001ListPlace
	 * 
	 * @param unitInfo
	 * @return List<VT040001ListPlace>
	 */
	public List<VT040001ListPlace> findListUnit(VT040001ListPlace unitInfo);

	/**
	 * get list of receiver when type character
	 * 
	 * @param empInfo
	 * @return List<VT040001ListEmployee>
	 */
	public List<VT040001ListEmployee> findListReceiver(VT040001ListEmployee empInfo);

	/**
	 * get list of receiver when type character
	 * 
	 * @param empInfo
	 * @return List<VT040001ListEmployee>
	 */
	public List<VT040001ListEmployee> findListReceiverForSussgest(VT040001InfoToFindEmployee empInfo);

	/**
	 * count total receiver
	 * 
	 * @return VT040001ListEmployee
	 */
	public List<VT040001ListEmployee> findListReceiverOfService(VT040001ListEmployee empInfo);

	/**
	 * get list menu service by condition
	 * 
	 * @param serviceInfo
	 * @return List<VT040001ListService>
	 */
	public List<VT040001ListService> findListSearchMenuService(VT040001ListService serviceInfo);

	/**
	 * count total menu service by condition
	 * 
	 * @param serviceInfo
	 * @return numOfRows in VT040001ListService
	 */
	public int countTotalMenuService(VT040001ListService serviceInfo);

	/**
	 * insert service
	 * 
	 * @param serviceInfo
	 * @return status insert service
	 */
	public int insertService(VT040001ListService serviceInfo);

	/**
	 * insert receiver
	 * 
	 * @param serviceInfo
	 * @return status insert receiver
	 */
	public int insertReceiver(VT040001ListService serviceInfo);

	/**
	 * check duplicate service name and place
	 * 
	 * @param serviceInfo
	 * @return numOfRows as row of duplicate couple service name and place id
	 */
	public int checkServiceNameAndPlace(VT040001ListService serviceInfo);

	/**
	 * check Delete Flag Service
	 * 
	 * @param serviceInfo
	 * @return int
	 */
	public int checkDeleteFlagService(VT040001ListService serviceInfo);

	/**
	 * update service
	 * 
	 * @param serviceInfo
	 * @return status update service
	 */
	public int updateService(VT040001ListService serviceInfo);

	/**
	 * get created user and created date
	 * 
	 * @param serviceInfo
	 * @return placeName for createdUsr and unitName for createdDate
	 */
	public VT040001ListService findCreatedUser(VT040001ListService serviceInfo);

	/**
	 * delete receiver by service id
	 * 
	 * @param serviceInfo
	 * @return status delete receiver
	 */
	public int deleteServiceReceiver(VT040001ListService serviceInfo);

	/**
	 * update receiver using inserting
	 * 
	 * @param serviceInfo
	 * @return status insert new receiver
	 */
	public int updateReceiverByInsert(VT040001ListService serviceInfo);

	/**
	 * check service is requesting or not
	 * 
	 * @param serviceInfo
	 * @return numOfRows as row of services are requesting
	 */
	public int checkServiceRequesting(VT040001ListService serviceInfo);

	/**
	 * delete service
	 * 
	 * @param serviceInfo
	 * @return status delete service
	 */
	public int deleteService(VT040001ListService serviceInfo);

	/**
	 * check if service name and place id exited or not to deleting
	 * 
	 * @param serviceInfo
	 * @return numOfRows as row of existed couple service name and place id
	 */
	public int checkServiceNameAndPlaceForDeleting(VT040001ListService serviceInfo);
	
	/**
	 * 
	 * @param placeInfo
	 * @return
	 */
	public List<VT040001ListPlace> getListPlace(VT040001ListPlace placeInfo);

	
	/**
	 * 
	 * @param serviceInfo
	 * @return
	 */
	public List<VT040001ListService> getListService(VT040001ListService serviceInfo);
	
	
	/**
	 * 
	 * @param unitInfo
	 * @return
	 */
	public List<VT040001ListPlace> getListUnit(VT040001ListPlace unitInfo);
	
	/**
	 * 
	 * @param empInfo
	 * @return
	 */
	public List<VT040001ListEmployee> getListReceiver(VT040001ListEmployee empInfo);
	
	public List<VT040001ListPlace> getPlaceByName(VT040001ListPlace placeInfo);
	
	/**
	 * 
	 * @param serviceInfo
	 * @return
	 */
	public List<VT040001ListService> getServiceByName(VT040001ListService serviceInfo);
	
	/**
	 * 
	 * @param unitInfo
	 * @return
	 */
	public List<VT040001ListPlace> getUnitByName(VT040001ListPlace unitInfo);
	
	/**
	 * get list of receiver when type character
	 * 
	 * @param empInfo
	 * @return List<VT040001ListEmployee>
	 */
	public List<VT040001ListEmployee> findReceiverByUserName(VT040001ListEmployee empInfo);
}
