package com.viettel.vtnet360.vt03.vt030013.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class RequestSearch extends BaseEntity {
	private String   driverSquadId;
	private String   driverSquadName;
	private String   driverUserName;
	private String   driverFullName;
	private int[]    driverStatus;
	private String   carType;
	private String   seat;
	private String   licensePlate;
	private int[]    carStatus;
	private int      pageSize;
	private int      pageNumber;
	private int      pageRecord;
	private boolean  freeTime;
	private boolean  carLead;
	private String   squadOfLead;


	public boolean isCarLead() { return carLead; }
	public void setCarLead(boolean carLead) { this.carLead = carLead; }

	public String getSquadOfLead() { return squadOfLead; }
	public void setSquadOfLead(String squadOfLead) { this.squadOfLead = squadOfLead; }

	public boolean isFreeTime() { return freeTime; }
	public void setFreeTime(boolean freeTime) { this.freeTime = freeTime; }

	public String getDriverSquadId() { return driverSquadId; }
	public void setDriverSquadId(String driverSquadId) { this.driverSquadId = driverSquadId; }

	public String getDriverUserName() { return driverUserName; }
	public void setDriverUserName(String driverUserName) { this.driverUserName = driverUserName; }

	public int[] getDriverStatus() { return driverStatus; }
	public void setDriverStatus(int[] driverStatus) { this.driverStatus = driverStatus; }

	public String getCarType() { return carType; }
	public void setCarType(String carType) { this.carType = carType; }

	public String getSeat() { return seat; }
	public void setSeat(String seat) { this.seat = seat; }

	public String getLicensePlate() { return licensePlate; }
	public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

	public int[] getCarStatus() { return carStatus; }
	public void setCarStatus(int[] carStatus) { this.carStatus = carStatus; }

	public int getPageSize() { return pageSize; }
	public void setPageSize(int pageSize) { this.pageSize = pageSize; }

	public int getPageNumber() { return pageNumber * this.pageSize; }
	public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }

  public int getPageRecord() { return pageRecord; }
  public void setPageRecord(int pageRecord) { this.pageRecord = pageRecord; }

  public String getDriverSquadName() { return driverSquadName; }
  public void setDriverSquadName(String driverSquadName) { this.driverSquadName = driverSquadName; }

  public String getDriverFullName() { return driverFullName; }
  public void setDriverFullName(String driverFullName) { this.driverFullName = driverFullName; }
}
