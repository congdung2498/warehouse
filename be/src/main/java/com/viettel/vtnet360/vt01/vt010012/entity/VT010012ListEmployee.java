package com.viettel.vtnet360.vt01.vt010012.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * class entity employee VT010012
 * 
 * @author ThangBT 10/11/2018
 *
 */
public class VT010012ListEmployee extends BaseEntity {

	/** column common */
	private String result;

	/** user name of employee */
	private String userName;

	public VT010012ListEmployee() {
		super();
	}

	public VT010012ListEmployee(String result, String userName) {
		super();
		this.result = result;
		this.userName = userName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
