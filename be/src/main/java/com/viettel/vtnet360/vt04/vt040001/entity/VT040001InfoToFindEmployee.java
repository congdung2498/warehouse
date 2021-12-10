package com.viettel.vtnet360.vt04.vt040001.entity;

/**
 * @author DuyNK
 *
 */
public class VT040001InfoToFindEmployee extends VT040001ListEmployee {

	/** if user logged on is HCĐV or QL or CVP => get employee by unit of them*/
	private boolean roleToGetUnit;
	
	/** if user logged on is HCĐV or QL or CVP => get userName for get unit*/
	private String userName;
	
	public VT040001InfoToFindEmployee() {
	
	}

	public VT040001InfoToFindEmployee(boolean roleToGetUnit, String userName) {
		super();
		this.roleToGetUnit = roleToGetUnit;
		this.userName = userName;
	}

	public boolean isRoleToGetUnit() {
		return roleToGetUnit;
	}

	public void setRoleToGetUnit(boolean roleToGetUnit) {
		this.roleToGetUnit = roleToGetUnit;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
