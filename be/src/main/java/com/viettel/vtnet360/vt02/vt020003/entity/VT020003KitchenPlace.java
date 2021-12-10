package com.viettel.vtnet360.vt02.vt020003.entity;

/** 
 * @author DuyNK 09/08/2018
 */
public class VT020003KitchenPlace {

	/** QLDV_PLACE.PLACE_ID */
	private int placeID;
	
	/** QLDV_PLACE.PLACE_NAME */
	private String placeName;
	
	public VT020003KitchenPlace() {

	}

	public VT020003KitchenPlace(int placeID, String placeName) {
		super();
		this.placeID = placeID;
		this.placeName = placeName;
	}

	public int getPlaceID() {
		return placeID;
	}

	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

}
