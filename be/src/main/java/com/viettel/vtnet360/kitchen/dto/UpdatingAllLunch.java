package com.viettel.vtnet360.kitchen.dto;

import com.viettel.vtnet360.common.security.BaseEntity;

public class UpdatingAllLunch extends BaseEntity {
  
  private String[]  lunchIds;
  private int       hasBooking;
  private int       quantity;
  private String    loginUsername;
  
  
  public String[] getLunchIds() { return lunchIds; }
  public void setLunchIds(String[] lunchIds) { this.lunchIds = lunchIds; }
  
  public int getHasBooking() { return hasBooking; }
  public void setHasBooking(int hasBooking) { this.hasBooking = hasBooking; }
  
  public int getQuantity() { return quantity; }
  public void setQuantity(int quantity) { this.quantity = quantity; }
  
  public String getLoginUsername() { return loginUsername; }
  public void setLoginUsername(String loginUsername) { this.loginUsername = loginUsername; }
}
