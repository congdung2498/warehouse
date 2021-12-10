package com.viettel.vtnet360.vt03.vt030000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * 
 * @author CuongHD
 *
 */
public class VT030000EntityPlace extends BaseEntity {
	
	/** QLDV_PLACE.PLACE_ID */
	private int placeId;
	
	/** QLDV_PLACE.PLACE_NAME */
	private String placeName;
	
	/** QLDV_PLACE.STATUS */
	private int status;
	
	public VT030000EntityPlace() {
		super();
	}
	public VT030000EntityPlace(int placeId, String placeName) {
		super();
		this.placeId = placeId;
		this.placeName = placeName;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	

}
