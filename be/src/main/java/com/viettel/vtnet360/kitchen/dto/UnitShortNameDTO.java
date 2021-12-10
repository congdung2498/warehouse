package com.viettel.vtnet360.kitchen.dto;

public class UnitShortNameDTO {

	private int unitId;
	private String kitchenID;
	private String shortName;

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	
	public UnitShortNameDTO(int unitId, String kitchenID, String shortName) {
		super();
		this.unitId = unitId;
		this.kitchenID = kitchenID;
		this.shortName = shortName;
	}

	public UnitShortNameDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
