package com.viettel.vtnet360.vt04.vt040002.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040002.entity.VT040002Stationery;

/**
 * interface of service VT040002
 * 
 * @author ThangBT 18/10/2018
 *
 */
public interface VT040002Service {

	/**
	 * find List Stationery
	 * 
	 * @param VT040002Stationery
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListStationery(VT040002Stationery stationeryInfo) throws Exception;

	/**
	 * find List Stationery Type Or Unit
	 * 
	 * @param String
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListStationeryTypeOrUnit(String masterClass) throws Exception;

	/**
	 * find Stationery Report
	 * 
	 * @param VT040002Stationery
	 * @param                    Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findStationeryReport(VT040002Stationery stationeryInfo,
			Collection<GrantedAuthority> listRole) throws Exception;

	/**
	 * count Total Record
	 * 
	 * @param VT040002Stationery
	 * @param Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase countTotalRecord(VT040002Stationery stationeryInfo, Collection<GrantedAuthority> listRole)
			throws Exception;

	/**
	 * insert Stationery
	 * 
	 * @param VT040002Stationery
	 * @param Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase insertStationery(VT040002Stationery stationeryInfo, Collection<GrantedAuthority> listRole)
			throws Exception;

	/**
	 * update Stationery
	 * 
	 * @param VT040002Stationery
	 * @param  Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase updateStationery(VT040002Stationery stationeryInfo, Collection<GrantedAuthority> listRole)
			throws Exception;

	/**
	 * delete Stationery
	 * 
	 * @param VT040002Stationery
	 * @param Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase deleteStationery(VT040002Stationery stationeryInfo, Collection<GrantedAuthority> listRole)
			throws Exception;
	
	/**
	 * select Stationery
	 * 
	 * @param VT040002Stationery
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase checkDuplicateName(VT040002Stationery stationeryInfo)
			throws Exception;
}
