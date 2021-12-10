package com.viettel.vtnet360.vt03.vt030013.entity;

public class Unit {
	private int unitId;
	private String unitName;
	private int parentId;

	public Unit() {
	}

	public Unit(int unitId, String unitName, int parentId) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
		this.parentId = parentId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

}
