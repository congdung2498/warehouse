package com.viettel.vtnet360.stationery.entity;

public class PlaceNameAllResponseTemplate {

	private String placeName ;
	private String description;
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public PlaceNameAllResponseTemplate(String placeName, String description) {
		super();
		this.placeName = placeName;
		this.description = description;
	}
	public PlaceNameAllResponseTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
