package com.viettel.vtnet360.stationery.service;

public class IssueStationery {
  
  private String  issueStationeryId;
  private int     status;
  
  public IssueStationery() { }
  
  public IssueStationery(String issueStationeryId, int status) {
    this.issueStationeryId = issueStationeryId;
    this.status = status;
  }
  
  public String getIssueStationeryId() { return issueStationeryId; }
  public void setIssueStationeryId(String issueStationeryId) { this.issueStationeryId = issueStationeryId; }
  
  public int getStatus() { return status; }
  public void setStatus(int status) { this.status = status; }
}
