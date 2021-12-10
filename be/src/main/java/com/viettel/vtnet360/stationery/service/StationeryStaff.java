package com.viettel.vtnet360.stationery.service;

import java.util.List;

public class StationeryStaff {
  
  private int           unitId;
  private String        stationeryStaffId;
  private List<Integer> placeIds;
  
  
  public int getUnitId() { return unitId; }
  public void setUnitId(int unitId) { this.unitId = unitId; }
  
  public String getStationeryStaffId() { return stationeryStaffId; }
  public void setStationeryStaffId(String stationeryStaffId) { this.stationeryStaffId = stationeryStaffId; }
  
  public List<Integer> getPlaceIds() { return placeIds; }
  public void setPlaceIds(List<Integer> placeIds) { this.placeIds = placeIds; }
}
