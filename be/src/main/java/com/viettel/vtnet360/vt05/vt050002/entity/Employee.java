package com.viettel.vtnet360.vt05.vt050002.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

public class Employee extends BaseEntity {
	private String employeeId;
	private String unitId;
	private String fullName;
	private String userName;
	private String selectUserName;
	private String employeePhone;
	List<String> listRole;
	private String role;
	private String unit;
	private List<Integer> listUnit;

	public Employee() {
	}

	public Employee(String employeeId, String fullName, String userName, String employeePhone, List<String> listRole,
			String role, String unit, String unitId) {
		super();
		this.employeeId = employeeId;
		this.fullName = fullName;
		this.userName = userName;
		this.employeePhone = employeePhone;
		this.listRole = listRole;
		this.role = role;
		this.unit = unit;
		this.unitId = unitId;
	}

	public List<Integer> getListUnit() {
		return listUnit;
	}

	public void setListUnit(List<Integer> listUnit) {
		this.listUnit = listUnit;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public String getEmployeePhone() {
		return employeePhone;
	}

	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}

	public List<String> getListRole() {
		return listRole;
	}

	public void setListRole(List<String> listRole) {
		this.listRole = listRole;
	}

	public String getSelectUserName() {
		return selectUserName;
	}

	public void setSelectUserName(String selectUserName) {
		this.selectUserName = selectUserName;
	}
}
