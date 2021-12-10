package com.viettel.vtnet360.vt03.vt030003.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class Drive extends BaseEntity {
	private String driveId;
	private String squadId;
	private String squadName;
	private int status;
	private int processStatus;
	private String fullName;
	private String userName;
	private String employeePhone;
	private String employeeId;
	private String oldValue;
	private String createUser;
	private String createDate;
	private String updateUser;
	private String updateDate;
	private String selectedLicensePlate ;
	private String idCar ;
	public Drive() {
	}
	public String getDriveId() {
		return driveId;
	}
	public void setDriveId(String driveId) {
		this.driveId = driveId;
	}
	public String getSquadId() {
		return squadId;
	}
	public void setSquadId(String squadId) {
		this.squadId = squadId;
	}
	public String getSquadName() {
		return squadName;
	}
	public void setSquadName(String squadName) {
		this.squadName = squadName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmployeePhone() {
		return employeePhone;
	}
	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
	public String getSelectedLicensePlate() {
		return selectedLicensePlate;
	}
	public void setSelectedLicensePlate(String selectedLicensePlate) {
		this.selectedLicensePlate = selectedLicensePlate;
	}
	
	public String getIdCar() {
    return idCar;
  }
  public void setIdCar(String idCar) {
    this.idCar = idCar;
  }
  public Drive(String driveId, String squadId, String squadName, int status, int processStatus, String fullName,
			String userName, String employeePhone, String employeeId, String oldValue, String createUser,
			String createDate, String updateUser, String updateDate, String selectedLicensePlate) {
		super();
		this.driveId = driveId;
		this.squadId = squadId;
		this.squadName = squadName;
		this.status = status;
		this.processStatus = processStatus;
		this.fullName = fullName;
		this.userName = userName;
		this.employeePhone = employeePhone;
		this.employeeId = employeeId;
		this.oldValue = oldValue;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
		this.selectedLicensePlate = selectedLicensePlate;
	}
  public Drive(String driveId) {
    super();
    this.driveId = driveId;
  }
  public Drive(String squadId, String userName) {
    super();
    this.squadId = squadId;
    this.userName = userName;
  }

	

	

}
