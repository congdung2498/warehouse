package com.viettel.vtnet360.vt05.vt050013.entity;

import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003StationeryParam;

/**
 * @author DuyNK
 *
 */
public class VT050013InfoToEditRequest extends BaseEntity {

	/** ISSUES_STATIONERY.ISSUES_STATIONERY_ID */
	private String requestID;

	/** ISSUES_STATIONERY.ISSUE_LOCATION */
	private int placeID;

	/**
	 * list STATIONERY_ITEMS.STATIONERY_ID and quantity to insert request stationery
	 */
	private List<VT050003StationeryParam> listStationery;

	/** ISSUES_STATIONERY.NOTE */
	private String note;

	/**
	 * true=delete list item(listStationery)
	 * false=edit request
	 */
	private boolean deleteFlag;
	
	public VT050013InfoToEditRequest() {

	}

	public VT050013InfoToEditRequest(String requestID, int placeID, List<VT050003StationeryParam> listStationery,
			String note, boolean deleteFlag) {
		super();
		this.requestID = requestID;
		this.placeID = placeID;
		this.listStationery = listStationery;
		this.note = note;
		this.deleteFlag = deleteFlag;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public int getPlaceID() {
		return placeID;
	}

	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}

	public List<VT050003StationeryParam> getListStationery() {
		return listStationery;
	}

	public void setListStationery(List<VT050003StationeryParam> listStationery) {
		this.listStationery = listStationery;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
