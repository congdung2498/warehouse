package com.viettel.vtnet360.vt02.vt020013.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

public class ReportByUnitParam extends BaseEntity {
  
  private int     unitId;
  private Date    lunchDate;
  private String  kitchenId;
  
  
  public int getUnitId() { return unitId; }
  public void setUnitId(int unitId) { this.unitId = unitId; }
  
  public Date getLunchDate() { return lunchDate; }
  public void setLunchDate(Date lunchDate) { this.lunchDate = lunchDate; }
  
  public String getKitchenId() { return kitchenId; }
  public void setKitchenId(String kitchenId) { this.kitchenId = kitchenId; }
}
