package com.viettel.vtnet360.vt03.vt030004.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

public class DriveCar extends BaseEntity {
	/** DRIVE_CAR.CAR_ID */
	private String carId;

	/** DRIVE_CAR.DRIVE_SQUAD_ID */
	private String squadId;

	private String squadName;

	/** DRIVE_CAR.EMPLOYEE_USER_NAME */
	private String userName;

	private String licensePlate;

	/** DRIVE_CAR.PROCESS_STATUS */
	private int processStatus;

	/** DRIVE_CAR.CREATE_USER */
	private String createUser;

	/** DRIVE_CAR.CREATE_DATE */
	private String createDate;

	/** DRIVE_CAR.UPDATE_USER */
	private String updateUser;

	/** DRIVE_CAR.UPDATE_DATE */
	private String updateDate;

	public DriveCar() {
	}

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

	public String getSquadName() {
		return squadName;
	}

	public void setSquadName(String squadName) {
		this.squadName = squadName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public int getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
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

	public DriveCar(String carId, String squadId, String squadName, String userName, String licensePlate,
			int processStatus, String createUser, String createDate, String updateUser, String updateDate) {
		super();
		this.carId = carId;
		this.squadId = squadId;
		this.squadName = squadName;
		this.userName = userName;
		this.licensePlate = licensePlate;
		this.processStatus = processStatus;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
	}

	

}
