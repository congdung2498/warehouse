package com.viettel.vtnet360.driving.dto;

import com.viettel.vtnet360.common.security.BaseEntity;

public class SearchCarBooking extends BaseEntity {
  
  private String carBookingId;

  
  public String getCarBookingId() { return carBookingId; }
  public void setCarBookingId(String carBookingId) { this.carBookingId = carBookingId; }
}
