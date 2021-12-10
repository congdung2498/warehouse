package com.viettel.vtnet360.vt05.vt050003.entity;

import java.util.List;

import com.viettel.vtnet360.vt05.vt050000.entity.VT050000IssuesStationeryItems;

/**
 * @author DuyNK 05/10/2018
 */
public class VT050003IssuesStationeryItemsToInsert extends VT050000IssuesStationeryItems {

	/**
	 * list STATIONERY_ITEMS.STATIONERY_ID and quantity to insert request stationery
	 */
	private List<VT050003StationeryParam> listStationery;

	public VT050003IssuesStationeryItemsToInsert() {

	}

	public VT050003IssuesStationeryItemsToInsert(String issuesStationeryItemID, String issuesStationeryID,
			String stationeryID, int totalRequest, int totalFulFill, String issuesStationeryApprovedID,
			String issuesStationeryRegistry, int status, List<VT050003StationeryParam> listStationery) {
		super(issuesStationeryItemID, issuesStationeryID, stationeryID, totalRequest, totalFulFill,
				issuesStationeryApprovedID, issuesStationeryRegistry, status);
		this.listStationery = listStationery;
	}

	public List<VT050003StationeryParam> getListStationery() {
		return listStationery;
	}

	public void setListStationery(List<VT050003StationeryParam> listStationery) {
		this.listStationery = listStationery;
	}

}
