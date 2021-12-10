package com.viettel.vtnet360.vt03.vt030000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class Employee extends BaseEntity {
  /** QLDV_EMPLOYEE.QLDV_EMPLOYEE */
  private int employeeId;

  /** QLDV_EMPLOYEE.USER_NAME */
  private String userName;

  /** QLDV_EMPLOYEE.CODE*/
  private String employeeCode;

  /** QLDV_EMPLOYEE.FULL_NAME*/
  private String fullName;

  /** QLDV_EMPLOYEE.PHONE_NUMBER*/
  private String employeePhone;

  /** QLDV_EMPLOYEE.EMAIL*/
  private String employeeEmail;

  /** QLDV_EMPLOYEE.UNIT_ID*/
  private int unitId;

  /** QLDV_UNIT.UNIT_NAME*/
  private String unitName;

  /** QLDV_EMPLOYEE.TITLE*/
  private String title;

  /** QLDV_EMPLOYEE.LEVEL_EMPLOYEE*/
  private String levelEmployee;

  /** QLDV_EMPLOYEE.ROLE*/
  private String role;

  /** QLDV_EMPLOYEE.PLACE_ID*/
  private int placeId;

  /** QLDV_EMPLOYEE.COMMENTS*/
  private String comment;

  /** QLDV_EMPLOYEE.STATUS*/
  private int status;
  
  private String phoneNumber;


  public Employee() { }

  public Employee(int employeeId, String userName, String employeeCode, String full_name, String employeePhone,
      String employeeEmail, int unitId, String unitName, String title, String levelEmployee, String role,
      int placeId, String comment, int status) {
    super();
    this.employeeId = employeeId;
    this.userName = userName;
    this.employeeCode = employeeCode;
    this.fullName = full_name;
    this.employeePhone = employeePhone;
    this.employeeEmail = employeeEmail;
    this.unitId = unitId;
    this.unitName = unitName;
    this.title = title;
    this.levelEmployee = levelEmployee;
    this.role = role;
    this.placeId = placeId;
    this.comment = comment;
    this.status = status;
  }

  public String getPhoneNumber() {
	return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}

public int getEmployeeId() { return employeeId; }
  public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName; }

  public String getEmployeeCode() { return employeeCode; }
  public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

  public String getFullName() { return fullName; }
  public void setFullName(String fullName) { this.fullName = fullName; }

  public String getEmployeePhone() { return employeePhone; }
  public void setEmployeePhone(String employeePhone) { this.employeePhone = employeePhone; }

  public String getEmployeeEmail() { return employeeEmail; }
  public void setEmployeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; }

  public int getUnitId() { return unitId; }
  public void setUnitId(int unitId) { this.unitId = unitId; }

  public String getUnitName() { return unitName; }
  public void setUnitName(String unitName) { this.unitName = unitName; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public String getLevelEmployee() { return levelEmployee; }
  public void setLevelEmployee(String levelEmployee) { this.levelEmployee = levelEmployee; }

  public String getRole() { return role; }
  public void setRole(String role) { this.role = role; }

  public int getPlaceId() { return placeId; }
  public void setPlaceId(int placeId) { this.placeId = placeId; }

  public String getComment() { return comment; }
  public void setComment(String comment) { this.comment = comment; }

  public int getStatus() { return status; }
  public void setStatus(int status) { this.status = status; }
}
