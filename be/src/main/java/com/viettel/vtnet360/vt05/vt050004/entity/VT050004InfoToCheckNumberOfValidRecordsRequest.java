package com.viettel.vtnet360.vt05.vt050004.entity;

import java.util.List;

/**
 * @author DuyNK
 *
 */
public class VT050004InfoToCheckNumberOfValidRecordsRequest {

	/** ISSUES_STATIONERY_ITEMS.STATUS */
	private int status;
	
	/** list ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ITEM_ID */
	private List<String> listRequestID;
	
	public VT050004InfoToCheckNumberOfValidRecordsRequest() {
	
	}

	public VT050004InfoToCheckNumberOfValidRecordsRequest(int status, List<String> listRequestID) {
		super();
		this.status = status;
		this.listRequestID = listRequestID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<String> getListRequestID() {
		return listRequestID;
	}

	public void setListRequestID(List<String> listRequestID) {
		this.listRequestID = listRequestID;
	}
}
