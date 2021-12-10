package com.viettel.vtnet360.vt05.vt050015.entity;

public class FullfillStationery {
	private String rpStationeryId;
	private String stationeryName;
	private int totalRequest;
	private int totalFullfill;
	private String calUnit;
	private String fullName;
	private String lastUser;
	private int status;

	public FullfillStationery() {

	}

	public FullfillStationery(String rpStationeryId, String stationeryName, int totalRequest, int totalFullfill,
			String calUnit, String fullName, String lastUser, int status) {
		super();
		this.rpStationeryId = rpStationeryId;
		this.stationeryName = stationeryName;
		this.totalRequest = totalRequest;
		this.totalFullfill = totalFullfill;
		this.calUnit = calUnit;
		this.fullName = fullName;
		this.lastUser = lastUser;
		this.status = status;
	}

	public String getRpStationeryId() {
		return rpStationeryId;
	}

	public void setRpStationeryId(String rpStationeryId) {
		this.rpStationeryId = rpStationeryId;
	}

	public String getStationeryName() {
		return stationeryName;
	}

	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}

	public int getTotalRequest() {
		return totalRequest;
	}

	public void setTotalRequest(int totalRequest) {
		this.totalRequest = totalRequest;
	}

	public int getTotalFullfill() {
		return totalFullfill;
	}

	public void setTotalFullfill(int totalFullfill) {
		this.totalFullfill = totalFullfill;
	}

	public String getCalUnit() {
		return calUnit;
	}

	public void setCalUnit(String calUnit) {
		this.calUnit = calUnit;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLastUser() {
		return lastUser;
	}

	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
