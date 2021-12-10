package com.viettel.vtnet360.stationery.service;

public class ApprovingInfo {
  
  private String approvedId;
  private String hcdvUsername;
  private String vptctUsername;
  private String hcdvMessage;
  private String jobCode;
  
  public ApprovingInfo() { }
  
  public ApprovingInfo(String approvedId, String jobCode) {
    this.approvedId = approvedId;
    this.jobCode = jobCode;
  }
  
  public String getApprovedId() { return approvedId; }
  public void setApprovedId(String approvedId) { this.approvedId = approvedId; }
  
  public String getHcdvUsername() { return hcdvUsername; }
  public void setHcdvUsername(String hcdvUsername) { this.hcdvUsername = hcdvUsername; }
  
  public String getVptctUsername() { return vptctUsername; }
  public void setVptctUsername(String vptctUsername) { this.vptctUsername = vptctUsername; }
  
  public String getHcdvMessage() { return hcdvMessage; }
  public void setHcdvMessage(String hcdvMessage) { this.hcdvMessage = hcdvMessage; }
  
  public String getJobCode() { return jobCode; }
  public void setJobCode(String jobCode) { this.jobCode = jobCode; }
}
