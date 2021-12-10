package com.viettel.vtnet360.driving.entity;

public class ResultCarName {
	
	private String carId;
	private String licensePlate ;
	private String codeName ;
	private String employeeUserName;
	
	
	public String getEmployeeUserName() {
		return employeeUserName;
	}
	public void setEmployeeUserName(String employeeUserName) {
		this.employeeUserName = employeeUserName;
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
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	public ResultCarName(String carId, String licensePlate, String codeName, String employeeUserName) {
		super();
		this.carId = carId;
		this.licensePlate = licensePlate;
		this.codeName = codeName;
		this.employeeUserName = employeeUserName;
	}
	public ResultCarName() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
