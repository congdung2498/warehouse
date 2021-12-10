package com.viettel.vtnet360.stationery.entity;

import java.util.List;

public class RequestParamForm {

	/** ISSUES_STATIONERY.ISSUE_LOCATION */
	private int placeID;

	/**
	 * list STATIONERY_ITEMS.STATIONERY_ID and quantity to insert request stationery
	 */
	private List<StationeryParamForm> listStationery;

	/** ISSUES_STATIONERY.NOTE */
	private String note;

	public int getPlaceID() {
		return placeID;
	}

	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}

	public List<StationeryParamForm> getListStationery() {
		return listStationery;
	}

	public void setListStationery(List<StationeryParamForm> listStationery) {
		this.listStationery = listStationery;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public RequestParamForm(int placeID, List<StationeryParamForm> listStationery, String note) {
		super();
		this.placeID = placeID;
		this.listStationery = listStationery;
		this.note = note;
	}

	public RequestParamForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
