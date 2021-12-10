package com.viettel.vtnet360.kitchen.dto;

import java.util.Date;

public class LunchVoteMessage {
  
  private String  phone;
  private int     employeeId;
  private String  userName;
  private String  deviceId;
  private String  lunchId;
  private Date    lunchDate;
  
  
  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }
  
  public int getEmployeeId() { return employeeId; }
  public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
  
  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName; }
  
  public String getDeviceId() { return deviceId; }
  public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
  
  public String getLunchId() { return lunchId; }
  public void setLunchId(String lunchId) { this.lunchId = lunchId; }
  
  public Date getLunchDate() { return lunchDate; }
  public void setLunchDate(Date lunchDate) { this.lunchDate = lunchDate; }
}
