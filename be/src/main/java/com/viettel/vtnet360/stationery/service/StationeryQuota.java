package com.viettel.vtnet360.stationery.service;

public class StationeryQuota {
  
  private int     unitId;
  private int     quantity;
  private double  quota;
  
  
  public int getUnitId() { return unitId; }
  public void setUnitId(int unitId) { this.unitId = unitId; }
  
  public int getQuantity() { return quantity; }
  public void setQuantity(int quantity) { this.quantity = quantity; }
  
  public double getQuota() { return quota; }
  public void setQuota(double quota) { this.quota = quota; }
}
