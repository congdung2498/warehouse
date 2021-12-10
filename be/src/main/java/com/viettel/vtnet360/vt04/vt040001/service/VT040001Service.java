package com.viettel.vtnet360.vt04.vt040001.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001InfoToFindEmployee;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001ListEmployee;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001ListPlace;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001ListService;

/**
 * interface of services VT040001
 * 
 * @author ThangBT 04/10/2018
 *
 */
public interface VT040001Service {

	/**
	 * find List Place
	 * 
	 * @param VT040001ListPlace
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListPlace(VT040001ListPlace placeInfo) throws Exception;

	/**
	 * find List Service
	 * 
	 * @param VT040001ListService
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListService(VT040001ListService serviceInfo) throws Exception;

	/**
	 * find List Unit
	 * 
	 * @param VT040001ListPlace
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListUnit(VT040001ListPlace unitInfo) throws Exception;

	/**
	 * find List Receiver
	 * 
	 * @param VT040001ListEmployee
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListReceiver(VT040001ListEmployee empInfo) throws Exception;

	/**
	 * find List Receiver For Sussgest
	 * 
	 * @param VT040001ListEmployee
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListReceiverForSussgest(VT040001InfoToFindEmployee empInfo, Collection<GrantedAuthority> roleList) throws Exception;

	/**
	 * find List Receiver Of Service
	 * 
	 * @param VT040001ListEmployee
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListReceiverOfService(VT040001ListEmployee empInfo) throws Exception;

	/**
	 * find List Menu Service
	 * 
	 * @param VT040001ListService
	 * @param                     Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListMenuService(VT040001ListService serviceInfo,
			Collection<GrantedAuthority> listRole) throws Exception;

	/**
	 * count Total Menu Service
	 * 
	 * @param VT040001ListService
	 * @param                     Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase countTotalMenuService(VT040001ListService serviceInfo,
			Collection<GrantedAuthority> listRole) throws Exception;

	/**
	 * insert Service And Receiver
	 * 
	 * @param VT040001ListService
	 * @param                     Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase insertServiceAndReceiver(VT040001ListService serviceInfo,
			Collection<GrantedAuthority> listRole) throws Exception;

	/**
	 * update Service And Receiver
	 * 
	 * @param VT040001ListService
	 * @param                     Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase updateServiceAndReceiver(VT040001ListService serviceInfo,
			Collection<GrantedAuthority> listRole) throws Exception;

	/**
	 * delete Service And Receiver
	 * 
	 * @param VT040001ListService
	 * @param                     Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase deleteServiceAndReceiver(VT040001ListService serviceInfo,
			Collection<GrantedAuthority> listRole) throws Exception;
	
	/**
	 * 
	 * @param placeInfo
	 * @return
	 */
	public ResponseEntityBase getListPlace(VT040001ListPlace placeInfo);
	
	
	/**
	 * 
	 * @param serviceInfo
	 * @return
	 * @throws Exception
	 */
	public ResponseEntityBase getListService(VT040001ListService serviceInfo) throws Exception;
	
	/**
	 * 
	 * @param unitInfo
	 * @return
	 * @throws Exception
	 */
	public ResponseEntityBase getListUnit(VT040001ListPlace unitInfo) throws Exception;
	
	/**
	 * 
	 * @param empInfo
	 * @return
	 */
	public ResponseEntityBase getListReceiver(VT040001ListEmployee empInfo);
	
	/**
	 * check duplicate service name and place
	 * 
	 * @param serviceInfo
	 * @return numOfRows as row of duplicate couple service name and place id
	 */
	public int checkServiceNameAndPlace(VT040001ListService serviceInfo);
	
	public ResponseEntityBase getPlaceByName(VT040001ListPlace placeInfo);
	/**
	 * 
	 * @param serviceInfo
	 * @return
	 * @throws Exception
	 */
	public ResponseEntityBase getServiceByName(VT040001ListService serviceInfo) throws Exception;
	
	/**
	 * 
	 * @param unitInfo
	 * @return
	 * @throws Exception
	 */
	public ResponseEntityBase getUnitByName(VT040001ListPlace unitInfo) throws Exception;
	
	/**
	 * find List Receiver
	 * 
	 * @param VT040001ListEmployee
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findReceiverByUserName(VT040001ListEmployee empInfo) throws Exception;
}
