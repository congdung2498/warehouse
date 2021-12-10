package com.viettel.vtnet360.vt05.vt050001.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class CalculationUnit extends BaseEntity {
	private String calUnitId;
	private String calUnit;
	

	public String getCalUnitId() {
		return calUnitId;
	}

	public void setCalUnitId(String calUnitId) {
		this.calUnitId = calUnitId;
	}

	public String getCalUnit() {
		return calUnit;
	}

	public void setCalUnit(String calUnit) {
		this.calUnit = calUnit;
	}

}
