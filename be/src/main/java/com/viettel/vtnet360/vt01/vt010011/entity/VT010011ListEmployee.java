package com.viettel.vtnet360.vt01.vt010011.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class entity employee VT010011
 * 
 * @author ThangBT 07/09/2018
 *
 */
public class VT010011ListEmployee extends BaseEntity {

	/** column common */
	private String result;

	/** user name of employee */
	private String userName;

	/** list of unit id */
	private String[] listUnit;

	public VT010011ListEmployee() {
		super();
	}

	public VT010011ListEmployee(String result, String userName, String[] listUnit) {
		super();
		this.result = result;
		this.userName = userName;
		this.listUnit = listUnit;
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

	public String[] getlistUnit() {
		return listUnit;
	}

	public void setlistUnit(String[] listUnit) {
		this.listUnit = listUnit;
	}
}
