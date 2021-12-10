package com.viettel.vtnet360.vt05.vt050010.entity;

public class VT050010DataPlaceResponse {

	private int placeId;

	private String placeName ;
	
	
  public String getPlaceName() {
    return placeName;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

  public int getPlaceId() {
    return placeId;
  }

  public void setPlaceId(int placeId) {
    this.placeId = placeId;
  }

  public VT050010DataPlaceResponse(int placeId, String placeName) {
    super();
    this.placeId = placeId;
    this.placeName = placeName;
  }

  public VT050010DataPlaceResponse() {
    super();
    // TODO Auto-generated constructor stub
  }

	
	
	
}
