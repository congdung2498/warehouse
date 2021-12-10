package com.viettel.vtnet360.vt05.vt050000.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoToFindListEmployeeOfUnit;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoToFindStationery;

/**
 * @author DuyNK 04/10/2018
 */
public interface VT050000Service {

	/**
	 * find list stationery by stationeryName
	 * 
	 * @param info
	 * @return ResponseEntityBase(status, List<VT050000StationeryInfo>)
	 */
	public ResponseEntityBase findStationeryByName(VT050000InfoToFindStationery info);

	/**
	 * find remaining spending limit of this user logged on in this month
	 * 
	 * @param userName
	 * @param roleList
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findRemainingSpendingLimit(String userName, Collection<GrantedAuthority> roleList);

	/**
	 * find remaining spending limit of this user logged on in this month
	 * 
	 * @param userName
	 * @param roleList
	 * @return Double
	 */
	public double caculRemainingSpendingLimit(String userName, Collection<GrantedAuthority> roleList);

	/**
	 * change status in ISSUES_STATIONERY when reject or give items
	 * 
	 * @param issuesStationeryID
	 * @param userName
	 */
	public void changeStatusIssuesStationery(String issuesStationeryID, String userName);
	
	/**
	 * find list employee of unit of hcdv and unitChild
	 * 
	 * @param info
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListEmployeeOfUnit(VT050000InfoToFindListEmployeeOfUnit info, Collection<GrantedAuthority> roleList);

	public void changeStatusIssuesStationeryPending(String issuesStationeryID, String userName);
}
