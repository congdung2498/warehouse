package com.viettel.vtnet360.vt03.vt030003.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.vt03.vt030004.entity.DriveCar;

public class DriveInsert extends BaseEntity {

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
	private List<DriveCar> selectedLicensePlate ;
	private List<String> listIdCar;
	private String idCar ;
	private String loginRole;
	private String userLogin;
	private int deleteFlag;
	private int startRow;
	private int rowSize;
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
	public List<DriveCar> getSelectedLicensePlate() {
		return selectedLicensePlate;
	}
	public void setSelectedLicensePlate(List<DriveCar> selectedLicensePlate) {
		this.selectedLicensePlate = selectedLicensePlate;
	}
	
	public List<String> getListIdCar() {
    return listIdCar;
  }
  public void setListIdCar(List<String> listIdCar) {
    this.listIdCar = listIdCar;
  }
  
  public String getIdCar() {
    return idCar;
  }
  public void setIdCar(String idCar) {
    this.idCar = idCar;
  }
  public String getLoginRole() {
    return loginRole;
  }
  public void setLoginRole(String loginRole) {
    this.loginRole = loginRole;
  }
  public String getUserLogin() {
    return userLogin;
  }
  public void setUserLogin(String userLogin) {
    this.userLogin = userLogin;
  }
  public int getDeleteFlag() {
    return deleteFlag;
  }
  public void setDeleteFlag(int deleteFlag) {
    this.deleteFlag = deleteFlag;
  }
  public int getStartRow() {
    return startRow;
  }
  public void setStartRow(int startRow) {
    this.startRow = startRow;
  }
  public int getRowSize() {
    return rowSize;
  }
  public void setRowSize(int rowSize) {
    this.rowSize = rowSize;
  }
  public DriveInsert(String driveId, String squadId, String squadName, int status, int processStatus, String fullName,
			String userName, String employeePhone, String employeeId, String oldValue, String createUser,
			String createDate, String updateUser, String updateDate, List<DriveCar> selectedLicensePlate) {
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
  
	public DriveInsert(String driveId) {
    super();
    this.driveId = driveId;
  }
	
  public DriveInsert(String squadId, String userName) {
    super();
    this.squadId = squadId;
    this.userName = userName;
  }
  public DriveInsert() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
