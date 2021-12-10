package com.viettel.vtnet360.stationery.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class StationeryParam extends BaseEntity {

  /** STATIONERY_ITEMS.STATIONERY_ID */
  private String stationeryId;

  /** quantity of stationery request */
  private int quantity;

  private String stationeryName;

  private String calculationUnit;

  private Double unitPrice;

  private String issuesStationeryItemId;
  
  private String userName;
  

  public String getStationeryId() { return stationeryId; }
  public void setStationeryId(String stationeryId) { this.stationeryId = stationeryId; }

  public int getQuantity() { return quantity; }
  public void setQuantity(int quantity) { this.quantity = quantity; }

  public String getStationeryName() { return stationeryName; }
  public void setStationeryName(String stationeryName) { this.stationeryName = stationeryName; }

  public String getCalculationUnit() { return calculationUnit; }
  public void setCalculationUnit(String calculationUnit) { this.calculationUnit = calculationUnit; }

  public Double getUnitPrice() { return unitPrice; }
  public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }

  public String getIssuesStationeryItemId() { return issuesStationeryItemId; }
  public void setIssuesStationeryItemId(String itemId) { this.issuesStationeryItemId = itemId; }
  
  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName; }
}
