package com.viettel.vtnet360.driving.dto;

import java.util.Date;

public class AddingTimeNotiInfo {
  
  private String  bookingId;
  private Date    dateStart;
  private Date    dateEnd;
  private int     status;
  private String  empName;
  private String  empPhone;
  private String  empUsername;
  private String  empUnitName;
  private String  appoverQltt;
  private String  squadLeadUsername;
  private String  driverUsername;
  private String  licensePlate;
  private String  qlttUsername;
  private String  qldvUsername;
  private String  cvpUsername;
  private String  routeType;
  private String  startPlace;
  private String  targetPlace;
  
  private String  carId;
  private String  carType;
  private String  seat;
  
  private int     flagQltt;
  private int     flagLddv;
  private int     flagCvp;
  
  
  public String getBookingId() { return bookingId; }
  public void setBookingId(String bookingId) { this.bookingId = bookingId; }
  
  public String getEmpUsername() { return empUsername; }
  public void setEmpUsername(String empUsername) { this.empUsername = empUsername; }
  
  public String getCarId() { return carId; }
  public void setCarId(String carId) { this.carId = carId; }
  
  public String getStartPlace() { return startPlace; }
  public void setStartPlace(String startPlace) { this.startPlace = startPlace; }
  
  public String getTargetPlace() { return targetPlace; }
  public void setTargetPlace(String targetPlace) { this.targetPlace = targetPlace; }
  
  public String getRouteType() { return routeType; }
  public void setRouteType(String routeType) { this.routeType = routeType; }
  
  public Date getDateStart() { return dateStart; }
  public void setDateStart(Date dateStart) { this.dateStart = dateStart; }
  
  public Date getDateEnd() { return dateEnd; }
  public void setDateEnd(Date dateEnd) { this.dateEnd = dateEnd; }
  
  public int getStatus() { return status; }
  public void setStatus(int status) { this.status = status; }
  
  public String getEmpName() { return empName; }
  public void setEmpName(String empName) { this.empName = empName; }
  
  public String getEmpPhone() { return empPhone; }
  public void setEmpPhone(String empPhone) { this.empPhone = empPhone; }
  
  public String getEmpUnitName() { return empUnitName; }
  public void setEmpUnitName(String empUnitName) { this.empUnitName = empUnitName; }
  
  public String getAppoverQltt() { return appoverQltt; }
  public void setAppoverQltt(String appoverQltt) { this.appoverQltt = appoverQltt; }
  
  public String getSquadLeadUsername() { return squadLeadUsername; }
  public void setSquadLeadUsername(String squadLeadUsername) { this.squadLeadUsername = squadLeadUsername; }
  
  public String getDriverUsername() { return driverUsername; }
  public void setDriverUsername(String driverUsername) { this.driverUsername = driverUsername; }
  
  public String getLicensePlate() { return licensePlate;}
  public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
  
  public String getQlttUsername() { return qlttUsername; }
  public void setQlttUsername(String qlttUsername) { this.qlttUsername = qlttUsername; }
  
  public String getQldvUsername() { return qldvUsername; }
  public void setQldvUsername(String qldvUsername) { this.qldvUsername = qldvUsername; }
  
  public String getCvpUsername() { return cvpUsername; }
  public void setCvpUsername(String cvpUsername) { this.cvpUsername = cvpUsername; }
  
  public String getCarType() { return carType; }
  public void setCarType(String carType) { this.carType = carType; }
  
  public String getSeat() { return seat; }
  public void setSeat(String seat) { this.seat = seat; }
  
  public int getFlagQltt() { return flagQltt; }
  public void setFlagQltt(int flagQltt) { this.flagQltt = flagQltt; }
  
  public int getFlagLddv() { return flagLddv; }
  public void setFlagLddv(int flagLddv) { this.flagLddv = flagLddv; }
  
  public int getFlagCvp() { return flagCvp; }
  public void setFlagCvp(int flagCvp) { this.flagCvp = flagCvp; }
}
