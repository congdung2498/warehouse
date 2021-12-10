package com.viettel.vtnet360.driving.entity;

import java.util.Date;

public class DrivesSquad {

	private String squadId ;
	
	private String squadName;
	
	private int placeId;
	
	private String employeeUserName ;
	
	private int status;
	
	private int deleteFlag ;
	
	private String createUser ;
	
	private Date createDate ;
	
	private String updateUser ;
	
	private Date updateDate ;

	

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

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public String getEmployeeUserName() {
		return employeeUserName;
	}

	public void setEmployeeUserName(String employeeUserName) {
		this.employeeUserName = employeeUserName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	

	public DrivesSquad(String squadId, String squadName, int placeId, String employeeUserName, int status,
			int deleteFlag, String createUser, Date createDate, String updateUser, Date updateDate) {
		super();
		this.squadId = squadId;
		this.squadName = squadName;
		this.placeId = placeId;
		this.employeeUserName = employeeUserName;
		this.status = status;
		this.deleteFlag = deleteFlag;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
	}

	public DrivesSquad() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
