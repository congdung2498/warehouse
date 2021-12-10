package com.viettel.vtnet360.stationery.service;

public class StationeryItem {
  
  private String stationeryId;
  private String stationeryName;
  private String unit;
  private double price;
  
  
  public String getStationeryId() { return stationeryId; }
  public void setStationeryId(String stationeryId) { this.stationeryId = stationeryId; }
  
  public String getStationeryName() { return stationeryName; }
  public void setStationeryName(String stationeryName) { this.stationeryName = stationeryName; }
  
  public String getUnit() { return unit; }
  public void setUnit(String unit) { this.unit = unit; }
  
  public double getPrice() { return price; }
  public void setPrice(double price) { this.price = price; }
}
