package com.viettel.vtnet360.issuesService.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class PlaceEntity extends BaseEntity {
  /** id of place */
  private String placeId;

  /** name of place */
  private String placeName;

  /** status of place */
  private Long status;

  public PlaceEntity() {
    super();
  }

  public String getPlaceId() {
    return placeId;
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

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

}
