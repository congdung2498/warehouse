package com.viettel.vtnet360.vt05.vt050003.entity;

import java.util.List;

import com.viettel.vtnet360.vt05.vt050000.entity.VT050000IssuesStationeryItems;

/**
 * @author DuyNK 05/10/2018
 */
public class VT050003IssuesStationeryItemsToInsertForm extends VT050000IssuesStationeryItems {

	/**
	 * list STATIONERY_ITEMS.STATIONERY_ID and quantity to insert request stationery
	 */
	private List<VT050003StationeryParamForm> listStationery;

	public VT050003IssuesStationeryItemsToInsertForm() {

	}

	public VT050003IssuesStationeryItemsToInsertForm(String issuesStationeryItemID, String issuesStationeryID,
			String stationeryID, int totalRequest, int totalFulFill, String issuesStationeryApprovedID,
			String issuesStationeryRegistry, int status, List<VT050003StationeryParamForm> listStationery) {
		super(issuesStationeryItemID, issuesStationeryID, stationeryID, totalRequest, totalFulFill,
				issuesStationeryApprovedID, issuesStationeryRegistry, status);
		this.listStationery = listStationery;
	}

	public List<VT050003StationeryParamForm> getListStationery() {
		return listStationery;
	}

	public void setListStationery(List<VT050003StationeryParamForm> listStationery) {
		this.listStationery = listStationery;
	}

	

}
