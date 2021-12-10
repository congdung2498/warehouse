package com.viettel.vtnet360.stationery.entity;

import java.util.List;

import com.viettel.vtnet360.stationery.service.ItemModel;

public class InfoToUpdateStorageHcdvRequest {

	private String issuesStationeryApproveID;

	/** list stationery info of this request */
	private List<ItemModel> listStationery;

	/** STORAGE_HCDV_REQUEST.UPDATE_USER */
	private String vptctUserName;

	public String getIssuesStationeryApproveID() {
		return issuesStationeryApproveID;
	}

	public void setIssuesStationeryApproveID(String issuesStationeryApproveID) {
		this.issuesStationeryApproveID = issuesStationeryApproveID;
	}

	public String getVptctUserName() {
		return vptctUserName;
	}

	public void setVptctUserName(String vptctUserName) {
		this.vptctUserName = vptctUserName;
	}

	public List<ItemModel> getListStationery() {
		return listStationery;
	}

	public void setListStationery(List<ItemModel> listStationery) {
		this.listStationery = listStationery;
	}

	
	
}
