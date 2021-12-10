package com.viettel.vtnet360.vt05.vt050003.entity;

/**
 * @author DuyNK 05/10/2018
 */
public class VT050003InfoToCheckPlaceExist {

	/** QLDV_PLACE.PLACE_CODE */
	private int placeID;

	/** QLDV_PLACE.STATUS */
	private int status;

	public VT050003InfoToCheckPlaceExist() {

	}

	public VT050003InfoToCheckPlaceExist(int placeID, int status) {
		super();
		this.placeID = placeID;
		this.status = status;
	}

	public int getPlaceID() {
		return placeID;
	}

	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
