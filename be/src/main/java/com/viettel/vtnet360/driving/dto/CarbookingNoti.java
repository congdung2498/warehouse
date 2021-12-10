package com.viettel.vtnet360.driving.dto;

import java.util.Date;

import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;

public class CarbookingNoti extends AdditionalInfoBase {
  
  private String  unitName;
  private String  fullName;
  private String  driverFullName;
  private Date    start;
  private Date    end;
  private Date    dateStart;
  private Date    dateEnd;
  private Date    timeStart;
  private Date    timeFinish;
  private Date    realStart;
  private Date    realEnd;
  private String  startPlace;
  private String  targetPlace;
  private int     ratting;
  
  
  public String getUnitName() { return unitName; }
  public void setUnitName(String unitName) { this.unitName = unitName; }
  
  public String getFullName() { return fullName; }
  public void setFullName(String fullName) { this.fullName = fullName; }
  
  public String getDriverFullName() { return driverFullName; }
  public void setDriverFullName(String driverFullName) { this.driverFullName = driverFullName; }
  
  public Date getStart() { return start; }
  public void setStart(Date start) { this.start = start; }
  
  public Date getEnd() { return end; }
  public void setEnd(Date end) { this.end = end; }
  
  public Date getDateStart() { return dateStart; }
  public void setDateStart(Date dateStart) { this.dateStart = dateStart; }
  
  public Date getDateEnd() { return dateEnd; }
  public void setDateEnd(Date dateEnd) { this.dateEnd = dateEnd; }
  
  public Date getTimeStart() { return timeStart; }
  public void setTimeStart(Date timeStart) { this.timeStart = timeStart; }
  
  public Date getTimeFinish() { return timeFinish; }
  public void setTimeFinish(Date timeFinish) { this.timeFinish = timeFinish; }
  
  public Date getRealStart() { return realStart; }
  public void setRealStart(Date realStart) { this.realStart = realStart; }
  
  public Date getRealEnd() { return realEnd; }
  public void setRealEnd(Date realEnd) { this.realEnd = realEnd; }
  
  public String getStartPlace() { return startPlace; }
  public void setStartPlace(String startPlace) { this.startPlace = startPlace; }
  
  public String getTargetPlace() { return targetPlace; }
  public void setTargetPlace(String targetPlace) { this.targetPlace = targetPlace; }
  
  public int getRatting() { return ratting; }
  public void setRatting(int ratting) { this.ratting = ratting; }
}
