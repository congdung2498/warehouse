package com.viettel.vtnet360.vt00.common;

/**
 * @author DuyNK
 *
 */
public class UnitAcronym {

	private String unitName;
	
	private String acronym;
	
	public UnitAcronym() {
	
	}

	public UnitAcronym(String unitName, String acronym) {
		super();
		this.unitName = unitName;
		this.acronym = acronym;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
}
