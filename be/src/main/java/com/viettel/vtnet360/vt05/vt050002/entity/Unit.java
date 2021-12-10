package com.viettel.vtnet360.vt05.vt050002.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class Unit extends BaseEntity {
	private String unitId;
	private String unitName;
	private String twoLevelUnit;

	public Unit() {
	}

	public Unit(String unitId, String unitName, String twoLevelUnit) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
		this.twoLevelUnit = twoLevelUnit;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getTwoLevelUnit() {
		return twoLevelUnit;
	}

	public void setTwoLevelUnit(String twoLevelUnit) {
		this.twoLevelUnit = twoLevelUnit;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

}
