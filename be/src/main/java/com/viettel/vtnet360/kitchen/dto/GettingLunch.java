package com.viettel.vtnet360.kitchen.dto;

import com.viettel.vtnet360.common.security.BaseEntity;

public class GettingLunch extends BaseEntity {
  
  private String lunchId;

  
  public String getLunchId() { return lunchId; }
  public void setLunchId(String lunchId) { this.lunchId = lunchId; }
}
