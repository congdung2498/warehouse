package com.viettel.vtnet360.driving.entity;

public class Manager {
	private String carBookingId;
	private String employeeId;
	private String fullName;
    private String userName;
    
    
	public String getCarBookingId() {
		return carBookingId;
	}
	public void setCarBookingId(String carBookingId) {
		this.carBookingId = carBookingId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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
	
	public Manager(String carBookingId, String employeeId, String fullName, String userName) {
		super();
		this.carBookingId = carBookingId;
		this.employeeId = employeeId;
		this.fullName = fullName;
		this.userName = userName;
	}
	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}
  
    
    
	
}
