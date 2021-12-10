package com.viettel.vtnet360.vt04.vt040001.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * class entity place VT040001
 * 
 * @author ThangBT 04/10/2018
 *
 */
public class VT040001ListPlace extends BaseEntity {

	/** id of place */
	private String placeId;

	/** name of place */
	private String placeName;
	
	private String unitId;
	
	private String unitName;

	private String threeLevelUnit;
	
	private int isLimit;

	public VT040001ListPlace() {
		super();
	}

	public VT040001ListPlace(String placeId, String placeName, String threeLevelUnit) {
		super();
		this.placeId = placeId;
		this.placeName = placeName;
		this.threeLevelUnit = threeLevelUnit;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getPlaceId() {
		return placeId;
	}

	public String getThreeLevelUnit() {
		return threeLevelUnit;
	}

	public void setThreeLevelUnit(String threeLevelUnit) {
		this.threeLevelUnit = threeLevelUnit;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public int getIsLimit() {
		return isLimit;
	}

	public void setIsLimit(int isLimit) {
		this.isLimit = isLimit;
	}
	
}
