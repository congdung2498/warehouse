package com.viettel.vtnet360.vt05.vt050003.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK 05/10/2018
 */
public class VT050003RequestParamForm extends BaseEntity {

	/** ISSUES_STATIONERY.ISSUE_LOCATION */
	private int placeID;

	/**
	 * list STATIONERY_ITEMS.STATIONERY_ID and quantity to insert request stationery
	 */
	private List<VT050003StationeryParamForm> listStationery;

	/** ISSUES_STATIONERY.NOTE */
	private String note;

	public VT050003RequestParamForm() {

	}

	public VT050003RequestParamForm(int placeID, List<VT050003StationeryParamForm> listStationery, String note) {
		super();
		this.placeID = placeID;
		this.listStationery = listStationery;
		this.note = note;
	}

	public int getPlaceID() {
		return placeID;
	}

	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}

	

	public List<VT050003StationeryParamForm> getListStationery() {
		return listStationery;
	}

	public void setListStationery(List<VT050003StationeryParamForm> listStationery) {
		this.listStationery = listStationery;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
