package com.viettel.vtnet360.vt02.vt020006.entity;

public class VT020006LunchInforEachMonth {
	private String userName;
	private String fullName;
	private Integer month;
	private String phoneNumber;
	private Integer totalMeal;
	private Integer totalMoney;
	private Integer employeeId;
	
	public VT020006LunchInforEachMonth() {
		super();
	}
	
	
	public VT020006LunchInforEachMonth(String userName, String fullName, Integer month, String phoneNumber,
			Integer totalMeal, Integer totalMoney, Integer employeeId) {
		super();
		this.userName = userName;
		this.fullName = fullName;
		this.month = month;
		this.phoneNumber = phoneNumber;
		this.totalMeal = totalMeal;
		this.totalMoney = totalMoney;
		this.employeeId = employeeId;
	}


	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getTotalMeal() {
		return totalMeal;
	}
	public void setTotalMeal(Integer totalMeal) {
		this.totalMeal = totalMeal;
	}
	public Integer getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}


	public Integer getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	
	
}
