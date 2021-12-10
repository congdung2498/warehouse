package com.viettel.vtnet360.vt05.vt050011.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050011RequestParamToSearch extends BaseEntity {

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_APPROVED_ID */
	private String requestID;

	/** ISSUES_STATIONERY_ITEMS.STATUS */
	private List<Integer> listStatus;
	
	public VT050011RequestParamToSearch() {

	}

	public VT050011RequestParamToSearch(String requestID, List<Integer> listStatus) {
		super();
		this.requestID = requestID;
		this.listStatus = listStatus;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public List<Integer> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<Integer> listStatus) {
		this.listStatus = listStatus;
	}
}
