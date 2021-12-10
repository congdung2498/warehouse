package com.viettel.vtnet360.vt05.vt050002.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class Place extends BaseEntity {
	private String   placeId;
	private String   placeName;
	private String[] lstPlaceId;
	private String   username;


	public String getPlaceId() { return placeId; }
	public void setPlaceId(String placeId) { this.placeId = placeId; }

	public String getPlaceName() { return placeName; }
	public void setPlaceName(String placeName) { this.placeName = placeName; }

	public String[] getLstPlaceId() { return lstPlaceId; }
	public void setLstPlaceId(String[] lstPlaceId) { this.lstPlaceId = lstPlaceId; }
	
  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }
}
