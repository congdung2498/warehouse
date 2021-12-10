package com.viettel.vtnet360.vt03.vt030013.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class CarDriverManage extends BaseEntity {
	private String squadId;
	private String userName;
	private String place;
	private String squadName;
	private String driver; // fullname
	private String phoneNumber;
	private String unit;
	private int    driverStatus;
	private String type;
	private String seat;
	private String licensePlate;
	private int    carStatus;


	public String getSquadId() {
		return squadId;
	}

	public void setSquadId(String squadId) {
		this.squadId = squadId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getSquadName() {
		return squadName;
	}

	public void setSquadName(String squadName) {
		this.squadName = squadName;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getDriverStatus() {
		return driverStatus;
	}

	public void setDriverStatus(int driverStatus) {
		this.driverStatus = driverStatus;
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

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public int getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(int carStatus) {
		this.carStatus = carStatus;
	}

}
