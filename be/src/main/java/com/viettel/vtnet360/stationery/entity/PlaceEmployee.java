package com.viettel.vtnet360.stationery.entity;

public class PlaceEmployee {

	private int placeId;
	private String placeName;
	private String lstPlaceId;
	
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
	public String getLstPlaceId() {
		return lstPlaceId;
	}
	public void setLstPlaceId(String lstPlaceId) {
		this.lstPlaceId = lstPlaceId;
	}
	 
	public PlaceEmployee(int placeId, String placeName, String lstPlaceId) {
		super();
		this.placeId = placeId;
		this.placeName = placeName;
		this.lstPlaceId = lstPlaceId;
	}
	public PlaceEmployee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
