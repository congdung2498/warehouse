package com.viettel.vtnet360.checking.service.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;


public class Checking extends BaseEntity {
  
  private String    inOutRegisterId;
  private String    employeeUserName;
  private String    destination;
  private String    reasonRegistion;
  private String    reasonDetail;
  private Date      startTimeByPlan;
  private Date      endTimeByPlan;
  private Date      startTimeByActual;
  private Date      endTimeByActual;
  private String    approverFullName;
  private String    approverUserName;
  private int       status;
  private int       logToRollbackStatus;
  private Date      logToRollbackStartTimeByPlan;
  private Date      logToRollbackEndTimeByPlan;
  private String    guardOutUsername;
  private String    guardInUsername;
  private String    reasonOfApprover;
  private String    reasonOfGuard;
  private String    inOutEmployeeList;
  private int       isLate;
  
  public int        empId;
  public String     empName;
  public String     empPhone;
  public String     empCode;
  public String     empEmail;
  public String     empTitle;
  public String     unitName;
  public String     detailUnit;

  
  public String getInOutRegisterId() { return inOutRegisterId; }
  public void setInOutRegisterId(String inOutRegisterId) { this.inOutRegisterId = inOutRegisterId; }
  
  public String getEmployeeUserName() { return employeeUserName; }
  public void setEmployeeUserName(String employeeUserName) { this.employeeUserName = employeeUserName; }
  
  public String getDestination() { return destination; }
  public void setDestination(String destination) { this.destination = destination; }
  
  public String getReasonRegistion() { return reasonRegistion; }
  public void setReasonRegistion(String reasonRegistion) { this.reasonRegistion = reasonRegistion; }
  
  public String getReasonDetail() { return reasonDetail; }
  public void setReasonDetail(String reasonDetail) { this.reasonDetail = reasonDetail; }
  
  public Date getStartTimeByPlan() { return startTimeByPlan; }
  public void setStartTimeByPlan(Date startTimeByPlan) { this.startTimeByPlan = startTimeByPlan; }
  
  public Date getEndTimeByPlan() { return endTimeByPlan; }
  public void setEndTimeByPlan(Date endTimeByPlan) { this.endTimeByPlan = endTimeByPlan; }
  
  public Date getStartTimeByActual() { return startTimeByActual; }
  public void setStartTimeByActual(Date startTimeByActual) { this.startTimeByActual = startTimeByActual; }
  
  public Date getEndTimeByActual() { return endTimeByActual; }
  public void setEndTimeByActual(Date endTimeByActual) { this.endTimeByActual = endTimeByActual; }
  
  public String getApproverFullName() { return approverFullName; }
  public void setApproverFullName(String approverFullName) { this.approverFullName = approverFullName; }
  
  public String getApproverUserName() { return approverUserName; }
  public void setApproverUserName(String approverUserName) { this.approverUserName = approverUserName; }
  
  public int getStatus() { return status; }
  public void setStatus(int status) { this.status = status; }
  
  public int getLogToRollbackStatus() { return logToRollbackStatus; }
  public void setLogToRollbackStatus(int logToRollbackStatus) { this.logToRollbackStatus = logToRollbackStatus; }
  
  public Date getLogToRollbackStartTimeByPlan() { return logToRollbackStartTimeByPlan; }
  public void setLogToRollbackStartTimeByPlan(Date startTime) { this.logToRollbackStartTimeByPlan = startTime; }
  
  public Date getLogToRollbackEndTimeByPlan() { return logToRollbackEndTimeByPlan; }
  public void setLogToRollbackEndTimeByPlan(Date endTime) { this.logToRollbackEndTimeByPlan = endTime; }
  
  public String getGuardOutUsername() { return guardOutUsername; }
  public void setGuardOutUsername(String guardOutUsername) { this.guardOutUsername = guardOutUsername; }
  
  public String getGuardInUsername() { return guardInUsername; }
  public void setGuardInUsername(String guardInUsername) { this.guardInUsername = guardInUsername; }
  
  public String getReasonOfApprover() { return reasonOfApprover; }
  public void setReasonOfApprover(String reasonOfApprover) { this.reasonOfApprover = reasonOfApprover; }
  
  public String getReasonOfGuard() { return reasonOfGuard; }
  public void setReasonOfGuard(String reasonOfGuard) { this.reasonOfGuard = reasonOfGuard; }
  
  public String getInOutEmployeeList() { return inOutEmployeeList; }
  public void setInOutEmployeeList(String inOutEmployeeList) { this.inOutEmployeeList = inOutEmployeeList; }
  
  public int getIsLate() { return isLate; }
  public void setIsLate(int isLate) { this.isLate = isLate; }
  
  public int getEmpId() { return empId;}
  public void setEmpId(int empId) {this.empId = empId; }
  
  public String getEmpName() { return empName; }
  public void setEmpName(String empName) { this.empName = empName; }
  
  public String getEmpPhone() { return empPhone; }
  public void setEmpPhone(String empPhone) { this.empPhone = empPhone; }
  
  public String getEmpCode() { return empCode; }
  public void setEmpCode(String empCode) { this.empCode = empCode; }
  
  public String getEmpEmail() { return empEmail; }
  public void setEmpEmail(String empEmail) { this.empEmail = empEmail; }
  
  public String getUnitName() { return unitName; }
  public void setUnitName(String unitName) { this.unitName = unitName; }
  
  public String getEmpTitle() { return empTitle; }
  public void setEmpTitle(String empTitle) { this.empTitle = empTitle; }
  
  public String getDetailUnit() { return detailUnit; }
  public void setDetailUnit(String detailUnit) { this.detailUnit = detailUnit; }
}
