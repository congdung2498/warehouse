package com.viettel.vtnet360.kitchen.dto;

import com.viettel.vtnet360.common.security.BaseEntity;

public class ImageConfig extends BaseEntity {
  
  private String dishId;

  
  public String getDishId() { return dishId; }
  public void setDishId(String dishId) { this.dishId = dishId; }
}
