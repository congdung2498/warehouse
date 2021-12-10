package com.viettel.vtnet360.driving.entity;

public class ResultDriveName {

	private String employeeUserName;
	private String fullName;
	private String driverId ;
	private String employeeId ;
	private String driveUser ;
	
	
	public String getDriveUser() {
		return driveUser;
	}

	public void setDriveUser(String driveUser) {
		this.driveUser = driveUser;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeUserName() {
		return employeeUserName;
	}

	public void setEmployeeUserName(String employeeUserName) {
		this.employeeUserName = employeeUserName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	


	public ResultDriveName(String employeeUserName, String fullName, String driverId, String employeeId,
			String driveUser) {
		super();
		this.employeeUserName = employeeUserName;
		this.fullName = fullName;
		this.driverId = driverId;
		this.employeeId = employeeId;
		this.driveUser = driveUser;
	}

	public ResultDriveName() {
		super();
		// TODO Auto-generated constructor stub
	}

}
