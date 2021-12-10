package com.viettel.vtnet360.stationery.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

public class InfoEmployee extends BaseEntity {
	
	private String fullName;
	private int    status;
	private String unitName;
	private String phoneNumber;
	private String email;
	private int    placeId;
	private String userName;
	private List<DetailsEmployeeInfo> listData;
	
	
	public String getFullName() { return fullName; }
	public void setFullName(String fullName) { this.fullName = fullName; }
	
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }
	
	public String getUnitName() { return unitName; }
	public void setUnitName(String unitName) { this.unitName = unitName; }
	
	public String getPhoneNumber() { return phoneNumber; }
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public List<DetailsEmployeeInfo> getListData() { return listData; }
	public void setListData(List<DetailsEmployeeInfo> listData) { this.listData = listData; }
	
	public int getPlaceId() { return placeId; }
	public void setPlaceId(int placeId) { this.placeId = placeId; }
	
  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName; }
}	
