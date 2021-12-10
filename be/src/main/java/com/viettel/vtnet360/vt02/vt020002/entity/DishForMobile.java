package com.viettel.vtnet360.vt02.vt020002.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viettel.vtnet360.common.security.BaseEntity;

public class DishForMobile extends BaseEntity {
  
  private Integer action;
  private VT020002Dish data;
  private String image;
  
  
  public Integer getAction() { return action; }
  public void setAction(Integer action) { this.action = action; }
  
  public VT020002Dish getData() { return data; }
  public void setData(VT020002Dish data) { this.data = data; }
  
  @JsonProperty
  public String getImage() { return image; }
  public void setImage(String image) { this.image = image; }
  
  static public class Image {
    private String name;
    private String data;
    
    
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public String getData() {
      return data;
    }
    public void setData(String data) {
      this.data = data;
    }
  }
}


