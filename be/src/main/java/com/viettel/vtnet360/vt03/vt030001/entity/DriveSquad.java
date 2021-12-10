package com.viettel.vtnet360.vt03.vt030001.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class DriveSquad extends BaseEntity {
	
	private String squadId;
	
	private String squadName;
	
	private int placeId;
	
	private String placeName;
	
	private String empUsername;
	
	private String empFullName;
	
	private int status;
	
	
	public DriveSquad() {
		// TODO Auto-generated constructor stub
	}


	public DriveSquad(String squadId, String squadName, int placeId, String placeName, String empUsername,
			String empFullName, int status) {
		super();
		this.squadId = squadId;
		this.squadName = squadName;
		this.placeId = placeId;
		this.placeName = placeName;
		this.empUsername = empUsername;
		this.empFullName = empFullName;
		this.status = status;
	}

	public String getSquadId() {
		return squadId;
	}

	public void setSquadId(String squadId) {
		this.squadId = squadId;
	}

	public String getSquadName() {
		return squadName;
	}

	public void setSquadName(String squadName) {
		this.squadName = squadName;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getEmpUsername() {
		return empUsername;
	}

	public void setEmpUsername(String empUsername) {
		this.empUsername = empUsername;
	}

	public String getEmpFullName() {
		return empFullName;
	}

	public void setEmpFullName(String empFullName) {
		this.empFullName = empFullName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ""+this.squadId+"-"+this.squadName+"-"+this.empFullName;
	}
}
