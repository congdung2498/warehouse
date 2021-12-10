package com.viettel.vtnet360.vt03.vt030002.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class Cars extends BaseEntity {
	/** CARS.CAR_ID */
	private String carId;

	/** DRIVES_SQUAD.DRIVE_SQUAD_ID */
	private String squadId;

	/** DRIVES_SQUAD.SQUAD_NAME */
	private String squadName;

	/** CARS.LICENSE_PLATE */
	private String licensePlate;

	/** CARS.TYPE */
	private String type;

	/** CARS.SEAT */
	private String seat;

	/** CARS.STATUS */
	private int status;

	/** CARS.PROCESS_STATUS */
	private int processStatus;

	private String userAssign;
	private int pageSize;
	private int pageNumber;

	public Cars() {
	}

	public Cars(String carID, String squadID, String squadName, String licensePlate, String type, String seat,
			int status, int processStatus, int pageSize, int pageNumber) {
		super();
		this.carId = carID;
		this.squadId = squadID;
		this.squadName = squadName;
		this.licensePlate = licensePlate;
		this.type = type;
		this.seat = seat;
		this.status = status;
		this.processStatus = processStatus;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
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

	public String getUserAssign() {
		return userAssign;
	}

	public void setUserAssign(String userAssign) {
		this.userAssign = userAssign;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
