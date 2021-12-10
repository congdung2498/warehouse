package com.viettel.vtnet360.issuesService.entity;

public class UserInfoEntity {
	private String userNameEmployee;
	private String fullNameEmployee;
	private String phoneNumberEmployee;
	private String emailEmployee;
	private Long unitId;
	private String nameUnit;

	public UserInfoEntity() {
		super();
	}

	public UserInfoEntity(String userNameEmployee, String fullNameEmployee, String phoneNumberEmployee,
			String emailEmployee, String nameUnit) {
		super();
		this.userNameEmployee = userNameEmployee;
		this.fullNameEmployee = fullNameEmployee;
		this.phoneNumberEmployee = phoneNumberEmployee;
		this.emailEmployee = emailEmployee;
		this.nameUnit = nameUnit;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getUserNameEmployee() {
		return userNameEmployee;
	}

	public void setUserNameEmployee(String userNameEmployee) {
		this.userNameEmployee = userNameEmployee;
	}

	public String getFullNameEmployee() {
		return fullNameEmployee;
	}

	public void setFullNameEmployee(String fullNameEmployee) {
		this.fullNameEmployee = fullNameEmployee;
	}

	public String getPhoneNumberEmployee() {
		return phoneNumberEmployee;
	}

	public void setPhoneNumberEmployee(String phoneNumberEmployee) {
		this.phoneNumberEmployee = phoneNumberEmployee;
	}

	public String getEmailEmployee() {
		return emailEmployee;
	}

	public void setEmailEmployee(String emailEmployee) {
		this.emailEmployee = emailEmployee;
	}

	public String getNameUnit() {
		return nameUnit;
	}

	public void setNameUnit(String nameUnit) {
		this.nameUnit = nameUnit;
	}

}
