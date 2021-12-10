package com.viettel.vtnet360.vt05.vt050011.entity;

import java.util.List;

/**
 * @author DuyNK
 *
 */
public class VT050011InfoToUpdateStorageHcdvRequest {

	/** STORAGE_HCDV_REQUEST.ISSUES_STATIONERY_APPROVED_ID */
	private String issuesStationeryApproveID;

	/** list stationery info of this request */
	private List<VT050011Stationery> listStationery;

	/** STORAGE_HCDV_REQUEST.UPDATE_USER */
	private String vptctUserName;
	
	public VT050011InfoToUpdateStorageHcdvRequest() {

	}

	public VT050011InfoToUpdateStorageHcdvRequest(String issuesStationeryApproveID,
			List<VT050011Stationery> listStationery, String vptctUserName) {
		super();
		this.issuesStationeryApproveID = issuesStationeryApproveID;
		this.listStationery = listStationery;
		this.vptctUserName = vptctUserName;
	}

	public String getIssuesStationeryApproveID() {
		return issuesStationeryApproveID;
	}

	public void setIssuesStationeryApproveID(String issuesStationeryApproveID) {
		this.issuesStationeryApproveID = issuesStationeryApproveID;
	}

	public List<VT050011Stationery> getListStationery() {
		return listStationery;
	}

	public void setListStationery(List<VT050011Stationery> listStationery) {
		this.listStationery = listStationery;
	}

	public String getVptctUserName() {
		return vptctUserName;
	}

	public void setVptctUserName(String vptctUserName) {
		this.vptctUserName = vptctUserName;
	}
}
