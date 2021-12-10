package com.viettel.vtnet360.vt03.vt030005.entity;

/**
 * 
 * @author CuongHD
 *
 */
public class VT030005EntityPlaceUnit {

	/** QLDV_PLACE.PLACE_ID */
	private int placeId;
	
	/** QLDV_EMPLOYEE.UNIT_ID */
	private int unitId;
	
	/** QLDV_PLACE.PLACE_NAME */
	private String name;

	public VT030005EntityPlaceUnit() {
		super();
	}

	public VT030005EntityPlaceUnit(int placeId, int unitId, String name) {
		super();
		this.placeId = placeId;
		this.unitId = unitId;
		this.name = name;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
