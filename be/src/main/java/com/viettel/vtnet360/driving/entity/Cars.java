package com.viettel.vtnet360.driving.entity;

import java.util.Date;

public class Cars {

	private String carId;
	private String squadId;
	private String licensePlate;
	private String type;
	private String seat;
	private int status;
	private int processStatus;
	private int deleteFlag;
	private String createUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	
	public String getSquadId() {
		return squadId;
	}

	public void setSquadId(String squadId) {
		this.squadId = squadId;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
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

	public Cars(String carId, String squadId, String licensePlate, String type, String seat, int status,
			int processStatus, int deleteFlag, String createUser, Date createDate, String updateUser, Date updateDate) {
		super();
		this.carId = carId;
		this.squadId = squadId;
		this.licensePlate = licensePlate;
		this.type = type;
		this.seat = seat;
		this.status = status;
		this.processStatus = processStatus;
		this.deleteFlag = deleteFlag;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
	}

	public Cars() {
		super();
		// TODO Auto-generated constructor stub
	}

}
