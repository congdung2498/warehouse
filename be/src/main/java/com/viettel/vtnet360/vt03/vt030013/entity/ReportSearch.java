package com.viettel.vtnet360.vt03.vt030013.entity;

public class ReportSearch {
	private String driverSquadId;
	private String driverUserName;
	private int[] driverStatus;
	private String carType;
	private String seat;
	private String licensePlate;
	private int[] carStatus;
	private boolean freeTime;
	private boolean carLead;
	private String squadOfLead;
	public String getDriverSquadId() {
		return driverSquadId;
	}
	public void setDriverSquadId(String driverSquadId) {
		this.driverSquadId = driverSquadId;
	}
	public String getDriverUserName() {
		return driverUserName;
	}
	public void setDriverUserName(String driverUserName) {
		this.driverUserName = driverUserName;
	}
	public int[] getDriverStatus() {
		return driverStatus;
	}
	public void setDriverStatus(int[] driverStatus) {
		this.driverStatus = driverStatus;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public int[] getCarStatus() {
		return carStatus;
	}
	public void setCarStatus(int[] carStatus) {
		this.carStatus = carStatus;
	}
	public boolean isFreeTime() {
		return freeTime;
	}
	public void setFreeTime(boolean freeTime) {
		this.freeTime = freeTime;
	}
	public boolean isCarLead() {
		return carLead;
	}
	public void setCarLead(boolean carLead) {
		this.carLead = carLead;
	}
	public String getSquadOfLead() {
		return squadOfLead;
	}
	public void setSquadOfLead(String squadOfLead) {
		this.squadOfLead = squadOfLead;
	}
	public ReportSearch(String driverSquadId, String driverUserName, int[] driverStatus, String carType, String seat,
			String licensePlate, int[] carStatus, boolean freeTime, boolean carLead, String squadOfLead) {
		super();
		this.driverSquadId = driverSquadId;
		this.driverUserName = driverUserName;
		this.driverStatus = driverStatus;
		this.carType = carType;
		this.seat = seat;
		this.licensePlate = licensePlate;
		this.carStatus = carStatus;
		this.freeTime = freeTime;
		this.carLead = carLead;
		this.squadOfLead = squadOfLead;
	}
	public ReportSearch() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
