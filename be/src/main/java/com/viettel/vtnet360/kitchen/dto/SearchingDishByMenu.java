package com.viettel.vtnet360.kitchen.dto;

import java.util.Date;

public class SearchingDishByMenu {
  
  private Date dateOfMenu;
  private String kitchenId;
  
  public SearchingDishByMenu(Date dateOfMenu, String kitchenid) {
    this.dateOfMenu = dateOfMenu;
    this.kitchenId = kitchenid;
  }
  
  
  public Date getDateOfMenu() { return dateOfMenu; }
  public void setDateOfMenu(Date dateOfMenu) { this.dateOfMenu = dateOfMenu; }
  
  public String getKitchenId() { return kitchenId; }
  public void setKitchenId(String kitchenid) { this.kitchenId = kitchenid; }
}
