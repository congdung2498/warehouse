package com.viettel.vtnet360.driving.dto;

public class DriverAndCarNotiInfo {
  
  private String driverFullName;
  private String driverPhone;
  private String carType;
  private String seat;
  private String licensePlate;
  
  
  public String getDriverFullName() { return driverFullName; }
  public void setDriverFullName(String driverFullName) { this.driverFullName = driverFullName; }
  
  public String getDriverPhone() { return driverPhone; }
  public void setDriverPhone(String driverPhone) { this.driverPhone = driverPhone; }
  
  public String getCarType() { return carType; }
  public void setCarType(String carType) { this.carType = carType; }
  
  public String getSeat() { return seat; }
  public void setSeat(String seat) { this.seat = seat; }
  
  public String getLicensePlate() { return licensePlate; }
  public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
}
