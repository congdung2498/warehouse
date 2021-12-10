package com.viettel.vtnet360.vt05.vt050003.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt05.vt050000.entity.VT050000IssuesStationery;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000StationeryInfo;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003InfoToCheckPlaceExist;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003InfoToFindHcdv;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003IssuesStationeryItemsToInsert;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003IssuesStationeryItemsToInsertEmployee;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003IssuesStationeryItemsToInsertForm;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003RequestParamEmployee;

/**
 * @author DuyNK 04/10/2018
 */
@Repository
public interface VT050003DAO {

	/**
	 * check validate placeID before insert request stationery
	 * 
	 * @param info
	 * @return Integer
	 */
	public int checkPlaceExist(VT050003InfoToCheckPlaceExist info);

	/**
	 * insert to ISSUES_STATIONERY when user request stationery
	 * 
	 * @param issuesStationery
	 */
	public void insertIssuesStationery(VT050000IssuesStationery issuesStationery);

	/**
	 * find ISSUE_STATIONERY_ID last in ISSUES_STATIONERY create by user logged on
	 * 
	 * @param employeeUserName
	 * @return String
	 */
	public String findIssuesStationeryID(String employeeUserName);

	/**
	 * insert to ISSUES_STATIONERY_ITEMS when user request stationery
	 * 
	 * @param issuesStationeryItemsToInsert
	 */
	public void insertIssuesStationeryItems(VT050003IssuesStationeryItemsToInsert issuesStationeryItemsToInsert);

	/**
	 * get list price to check spending limits
	 * 
	 * @param list
	 * @return List<VT050000StationeryInfo>
	 */
	public List<VT050000StationeryInfo> findStationeryPrice(List<String> list);

	/**
	 * find hcdv to send sms and notify
	 * 
	 * @param info
	 * @return List<String>
	 */
	public List<String> findHcdvUserName(VT050003InfoToFindHcdv info);
	
	/**
	 * insert to ISSUES_STATIONERY_ITEMS when user request stationery
	 * 
	 * @param issuesStationeryItemsToInsert
	 */
	public void insertIssuesStationeryItemsForm(VT050003IssuesStationeryItemsToInsertForm issuesStationeryItemsToInsert);
	
	public void insertIssuesStationeryItemsEmployee(VT050003IssuesStationeryItemsToInsertEmployee issuesStationeryItemsToInsert);
	
}
