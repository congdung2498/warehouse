package com.viettel.vtnet360.vt05.vt050004.entity;

import java.util.List;

/**
 * @author DuyNK
 *
 */
public class VT050004InfoToInsertStorageHcdvRequest {

	/** STORAGE_HCDV_REQUEST.ISSUES_STATIONERY_APPROVED_ID */
	private String issuesStationeryApproveID;
	
	/** STORAGE_HCDV_REQUEST.CREATE_USER */
	private String hcdvUserName;
	
	/** list info stationery that HCDV request */
	
	private List<VT050004Stationery> listStationery;
	public VT050004InfoToInsertStorageHcdvRequest() {
	
	}

	public VT050004InfoToInsertStorageHcdvRequest(String issuesStationeryApproveID, String hcdvUserName,
			List<VT050004Stationery> listStationery) {
		super();
		this.issuesStationeryApproveID = issuesStationeryApproveID;
		this.hcdvUserName = hcdvUserName;
		this.listStationery = listStationery;
	}

	public String getIssuesStationeryApproveID() {
		return issuesStationeryApproveID;
	}

	public void setIssuesStationeryApproveID(String issuesStationeryApproveID) {
		this.issuesStationeryApproveID = issuesStationeryApproveID;
	}

	public String getHcdvUserName() {
		return hcdvUserName;
	}

	public void setHcdvUserName(String hcdvUserName) {
		this.hcdvUserName = hcdvUserName;
	}

	public List<VT050004Stationery> getListStationery() {
		return listStationery;
	}

	public void setListStationery(List<VT050004Stationery> listStationery) {
		this.listStationery = listStationery;
	}
}
