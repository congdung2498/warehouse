package com.viettel.vtnet360.vt03.vt030013.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class CarPlate extends BaseEntity {
	private String carId;
	private String licensePlate;
	private String role;
	private String userName;
	private int pageNumber;
	private int pageSize;
	
	public CarPlate() {
		// TODO Auto-generated constructor stub
	}

	public CarPlate(String carId, String licensePlate, int pageNumber, int pageSize) {
		super();
		this.carId = carId;
		this.licensePlate = licensePlate;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}	
