package com.viettel.vtnet360.stationery.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

public class RequestParam extends BaseEntity {
	/** ISSUES_STATIONERY.ISSUE_LOCATION */
	private int placeID;

	/**
	 * list STATIONERY_ITEMS.STATIONERY_ID and quantity to insert request stationery
	 */
	private List<StationeryParam> listStationery;

	/** ISSUES_STATIONERY.NOTE */
	private String note;

	public int getPlaceID() {
		return placeID;
	}

	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}

	public List<StationeryParam> getListStationery() {
		return listStationery;
	}

	public void setListStationery(List<StationeryParam> listStationery) {
		this.listStationery = listStationery;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public RequestParam(int placeID, List<StationeryParam> listStationery, String note) {
		super();
		this.placeID = placeID;
		this.listStationery = listStationery;
		this.note = note;
	}

	public RequestParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
