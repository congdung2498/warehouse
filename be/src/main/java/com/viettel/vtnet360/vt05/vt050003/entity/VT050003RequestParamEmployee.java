package com.viettel.vtnet360.vt05.vt050003.entity;

import com.viettel.vtnet360.vt05.vt050000.entity.VT050000IssuesStationeryItems;

public class VT050003RequestParamEmployee extends VT050000IssuesStationeryItems {

	/** ISSUES_STATIONERY.ISSUE_LOCATION */
	private int placeID;

	/**
	 * list STATIONERY_ITEMS.STATIONERY_ID and quantity to insert request stationery
	 */
	private VT050003StationeryParam listStationery;

	/** ISSUES_STATIONERY.NOTE */
	private String note;

	public int getPlaceID() {
		return placeID;
	}

	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}

	public VT050003StationeryParam getListStationery() {
		return listStationery;
	}

	public void setListStationery(VT050003StationeryParam listStationery) {
		this.listStationery = listStationery;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public VT050003RequestParamEmployee(int placeID, VT050003StationeryParam listStationery, String note) {
		super();
		this.placeID = placeID;
		this.listStationery = listStationery;
		this.note = note;
	}

	public VT050003RequestParamEmployee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VT050003RequestParamEmployee(String issuesStationeryItemID, String issuesStationeryID, String stationeryID,
			int totalRequest, int totalFulFill, String issuesStationeryApprovedID, String issuesStationeryRegistryID,
			int status) {
		super(issuesStationeryItemID, issuesStationeryID, stationeryID, totalRequest, totalFulFill, issuesStationeryApprovedID,
				issuesStationeryRegistryID, status);
		// TODO Auto-generated constructor stub
	}

	
	
}
