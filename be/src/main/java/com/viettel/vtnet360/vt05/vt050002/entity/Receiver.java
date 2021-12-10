package com.viettel.vtnet360.vt05.vt050002.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

public class Receiver extends BaseEntity {
	private String receiverId;
	private String employeeId;
	private int placeId;
	private int unitId;
	private String place;
	private String unit;
	private String userName;
	private String savedUsername;
	private String fullName;
	private String employeePhone;
	private String roleId;
	private String roleName;
	private String jobCode;
	private String threeLevelUnit;
	private String updateDate;
	private String createDate;
	private String createUser;
	private String updateUser;
	private List<Place> lstPlace;
	private String placeNames;
	private String placeIds;
	private List<Long> lstPlaceId;
	private List<String> listUnit;
	private String path;

	public Receiver() {
	}

	

	public Receiver(String receiverId, String employeeId, int placeId, int unitId, String place, String unit,
			String userName, String fullName, String employeePhone, String roleId, String roleName, String jobCode,
			String threeLevelUnit, String updateDate, String createDate, String createUser, String updateUser,
			List<Place> lstPlace, String placeNames, String placeIds, List<Long> lstPlaceId, List<String> listUnit,
			String path) {
		super();
		this.receiverId = receiverId;
		this.employeeId = employeeId;
		this.placeId = placeId;
		this.unitId = unitId;
		this.place = place;
		this.unit = unit;
		this.userName = userName;
		this.fullName = fullName;
		this.employeePhone = employeePhone;
		this.roleId = roleId;
		this.roleName = roleName;
		this.jobCode = jobCode;
		this.threeLevelUnit = threeLevelUnit;
		this.updateDate = updateDate;
		this.createDate = createDate;
		this.createUser = createUser;
		this.updateUser = updateUser;
		this.lstPlace = lstPlace;
		this.placeNames = placeNames;
		this.placeIds = placeIds;
		this.lstPlaceId = lstPlaceId;
		this.listUnit = listUnit;
		this.path = path;
	}



	public String getSavedUsername() {
    return savedUsername;
  }

  public void setSavedUsername(String savedUsername) {
    this.savedUsername = savedUsername;
  }

  public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}



	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getJobCode() {
		return jobCode;
	}

	public String getThreeLevelUnit() {
		return threeLevelUnit;
	}

	public void setThreeLevelUnit(String threeLevelUnit) {
		this.threeLevelUnit = threeLevelUnit;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmployeePhone() {
		return employeePhone;
	}

	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Place> getLstPlace() {
		return lstPlace;
	}

	public void setLstPlace(List<Place> lstPlace) {
		this.lstPlace = lstPlace;
	}

	public String getPlaceNames() {
		return placeNames;
	}

	public void setPlaceNames(String placeNames) {
		this.placeNames = placeNames;
	}

	public String getPlaceIds() {
		return placeIds;
	}

	public void setPlaceIds(String placeIds) {
		this.placeIds = placeIds;
	}

	public List<Long> getLstPlaceId() {
		return lstPlaceId;
	}

	public void setLstPlaceId(List<Long> lstPlaceId) {
		this.lstPlaceId = lstPlaceId;
	}

	public List<String> getListUnit() {
		return listUnit;
	}

	public void setListUnit(List<String> listUnit) {
		this.listUnit = listUnit;
	}

}
