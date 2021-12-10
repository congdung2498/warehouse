package com.viettel.vtnet360.stationery.service;

public class StorageRequestItem {
  
  private int totalFullFill;
  private String stationeryId;
  private double   totalMoneyFullfill;
  
   
	  public double getTotalMoneyFullfill() {
		return totalMoneyFullfill;
	}
	public void setTotalMoneyFullfill(double totalMoneyFullfill) {
		this.totalMoneyFullfill = totalMoneyFullfill;
	}
	public int getTotalFullFill() {
	    return totalFullFill;
	  }
  public void setTotalFullFill(int totalFullFill) {
    this.totalFullFill = totalFullFill;
  }
  public String getStationeryId() {
    return stationeryId;
  }
  public void setStationeryId(String stationeryId) {
    this.stationeryId = stationeryId;
  }
  
  
}
