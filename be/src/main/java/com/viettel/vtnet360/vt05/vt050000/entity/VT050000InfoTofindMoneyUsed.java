package com.viettel.vtnet360.vt05.vt050000.entity;

import java.util.List;

/**
 * @author DuyNK
 * 04/10/2018
 */
public class VT050000InfoTofindMoneyUsed {

	/** ISSUES_STATIONERY.EMPLOYEE_USERNAME */
	private String userName;
	
	/** ISSUES_STATIONERY_ITEMS.STATUS that rejected */
	private List<Integer> listSatus;
	
	public VT050000InfoTofindMoneyUsed() {
	
	}

	public VT050000InfoTofindMoneyUsed(String userName, List<Integer> listSatus) {
		super();
		this.userName = userName;
		this.listSatus = listSatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Integer> getListSatus() {
		return listSatus;
	}

	public void setListSatus(List<Integer> listSatus) {
		this.listSatus = listSatus;
	}

}
