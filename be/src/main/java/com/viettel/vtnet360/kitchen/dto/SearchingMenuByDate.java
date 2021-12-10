package com.viettel.vtnet360.kitchen.dto;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

public class SearchingMenuByDate extends BaseEntity {
  
  private String kitchenId;
  private Date   date;
  
  public SearchingMenuByDate() { }
  
  public SearchingMenuByDate(String kitchenId, Date date) {
    this.kitchenId = kitchenId;
    this.date = date;
  }
  
  
  public String getKitchenId() { return kitchenId; }
  public void setKitchenId(String kitchenId) { this.kitchenId = kitchenId; }
  
  public Date getDate() { return date; }
  public void setDate(Date date) { this.date = date; }
}
