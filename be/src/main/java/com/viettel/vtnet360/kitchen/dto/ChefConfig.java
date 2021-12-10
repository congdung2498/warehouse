package com.viettel.vtnet360.kitchen.dto;

public class ChefConfig {
  
  private String username;
  private String kitchenId;
  
  public ChefConfig(String username) {
    this.username = username;
  }
  
  public ChefConfig(String username ,String kitchenId) {
    this.username = username;
    this.kitchenId = kitchenId;
  }
  
  
  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }

  public String getKitchenId() { return kitchenId; }
  public void setKitchenId(String kitchenId) { this.kitchenId = kitchenId; }
}
