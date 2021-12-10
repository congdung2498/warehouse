package com.viettel.vtnet360.vt05.vt050018.entity;

import java.util.List;

import com.viettel.vtnet360.vt05.vt050018.entity.VT050018InfoOfEmployeeRequest;

/**
 * @author DuyNK
 *
 */
public class VT050018InfoToUpdateIssuesStationeryItem {

	/** use for update ISSUES_STATIONERY_ITEMS.TOTAL_FULFILL */
	private List<VT050018InfoOfEmployeeRequest> listInfo;

	/** ISSUES_STATIONERY_ITEMS.STATUS */
	private int status;

	/**
	 * use for update ISSUES_STATIONERY_ITEMS.UPDATE_USER when hcdv confirm received
	 * stationery from VPTCT
	 */
	private String hcdvUserName;

	/** ISSUES_STATIONERY_ITEMS.STATUS */
	private int statusNow;
	
	public VT050018InfoToUpdateIssuesStationeryItem() {

	}

	public VT050018InfoToUpdateIssuesStationeryItem(List<VT050018InfoOfEmployeeRequest> listInfo, int status,
			String hcdvUserName, int statusNow) {
		super();
		this.listInfo = listInfo;
		this.status = status;
		this.hcdvUserName = hcdvUserName;
		this.statusNow = statusNow;
	}

	public List<VT050018InfoOfEmployeeRequest> getListInfo() {
		return listInfo;
	}

	public void setListInfo(List<VT050018InfoOfEmployeeRequest> listInfo) {
		this.listInfo = listInfo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHcdvUserName() {
		return hcdvUserName;
	}

	public void setHcdvUserName(String hcdvUserName) {
		this.hcdvUserName = hcdvUserName;
	}

	public int getStatusNow() {
		return statusNow;
	}

	public void setStatusNow(int statusNow) {
		this.statusNow = statusNow;
	}
}
