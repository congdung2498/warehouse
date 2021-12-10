package com.viettel.vtnet360.issuesService.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class EmployeeEntity extends BaseEntity {
  private String userNameEmployee;
  private String fullNameEmployee;
  private String role;
  private Long status;
  private String pathUnit;

  public EmployeeEntity() {
    super();
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

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  public String getPathUnit() {
    return pathUnit;
  }

  public void setPathUnit(String pathUnit) {
    this.pathUnit = pathUnit;
  }

}
