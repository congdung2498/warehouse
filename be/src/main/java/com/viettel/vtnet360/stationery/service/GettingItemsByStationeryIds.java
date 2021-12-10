package com.viettel.vtnet360.stationery.service;

import com.viettel.vtnet360.common.security.BaseEntity;

public class GettingItemsByStationeryIds extends BaseEntity {
  
  private String[] stationeryIds;

  
  public String[] getStationeryIds() { return stationeryIds; }
  public void setStationeryIds(String[] stationeryIds) { this.stationeryIds = stationeryIds; }
}
