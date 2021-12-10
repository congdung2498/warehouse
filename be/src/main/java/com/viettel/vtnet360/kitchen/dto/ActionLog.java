package com.viettel.vtnet360.kitchen.dto;

import java.util.Date;

public class ActionLog {
  
  private int     logActionId;
  private String  appCode;
  private String  url;
  private String  className;
  private String  actionMethod;
  private String  actionType;
  private String  requestId;
  private Date    startTime;
  private Date    endTime;
  private double  duratioin;
  private String  username;
  private String  ip;
  private double  errorCode;
  private double  result;
  private String  content;
  private Date    createdDate;
  
  
  public int getLogActionId() { return logActionId; }
  public void setLogActionId(int logActionId) { this.logActionId = logActionId; }
  
  public String getAppCode() { return appCode; }
  public void setAppCode(String appCode) { this.appCode = appCode; }
  
  public String getUrl() { return url; }
  public void setUrl(String url) { this.url = url; }
  
  public String getClassName() { return className; }
  public void setClassName(String className) { this.className = className; }
  
  public String getActionMethod() { return actionMethod; }
  public void setActionMethod(String actionMethod) { this.actionMethod = actionMethod; }
  
  public String getActionType() { return actionType; }
  public void setActionType(String actionType) { this.actionType = actionType; }
  
  public String getRequestId() { return requestId; }
  public void setRequestId(String requestId) { this.requestId = requestId; }
  
  public Date getStartTime() { return startTime; }
  public void setStartTime(Date startTime) { this.startTime = startTime; }
  
  public Date getEndTime() { return endTime; }
  public void setEndTime(Date endTime) { this.endTime = endTime; }
  
  public double getDuratioin() { return duratioin; }
  public void setDuratioin(double duratioin) { this.duratioin = duratioin; }
  
  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }
  
  public String getIp() { return ip; }
  public void setIp(String ip) { this.ip = ip; }
  
  public double getErrorCode() { return errorCode; }
  public void setErrorCode(double errorCode) { this.errorCode = errorCode; }
  
  public double getResult() { return result; }
  public void setResult(double result) { this.result = result; }
  
  public String getContent() { return content; }
  public void setContent(String content) { this.content = content; }
  
  public Date getCreatedDate() { return createdDate; }
  public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
}
