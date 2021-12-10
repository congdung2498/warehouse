package com.viettel.vtnet360.vt05.vt050004.entity;

import java.util.List;

/**
 * @author DuyNK
 *
 */
public class VT050004InfoToFindTotalMoneyUsedOfUnit {

	/** ISSUES_STATIONERY_APPROVED.HCDV_USERNAME */
	private List<String> listHcdvUserName;
	
	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private List<Integer> listStatus;
	
	public VT050004InfoToFindTotalMoneyUsedOfUnit() {
	
	}

	public VT050004InfoToFindTotalMoneyUsedOfUnit(List<String> listHcdvUserName, List<Integer> listStatus) {
		super();
		this.listHcdvUserName = listHcdvUserName;
		this.listStatus = listStatus;
	}

	public List<String> getListHcdvUserName() {
		return listHcdvUserName;
	}

	public void setListHcdvUserName(List<String> listHcdvUserName) {
		this.listHcdvUserName = listHcdvUserName;
	}

	public List<Integer> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<Integer> listStatus) {
		this.listStatus = listStatus;
	}
}
