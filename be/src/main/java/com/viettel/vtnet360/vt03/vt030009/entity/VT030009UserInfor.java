package com.viettel.vtnet360.vt03.vt030009.entity;

public class VT030009UserInfor {
	private String userFullName;
	private String phoneNumber;
	private String unitName;
	public VT030009UserInfor(String userFullName, String phoneNumber, String unitName) {
		super();
		this.userFullName = userFullName;
		this.phoneNumber = phoneNumber;
		this.unitName = unitName;
	}
	public VT030009UserInfor() {
		super();
	}
	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	
	
	
}
