package com.viettel.vtnet360.vt02.vt020010.entity;

import java.util.List;

public class SearchingUnit {
  
  private String kitchenId;
  private List<Integer> parentIds;
  
  public SearchingUnit(String kitchenId, List<Integer> parentIds) {
    this.kitchenId = kitchenId;
    this.parentIds = parentIds;
  }
  
  
  public String getKitchenId() { return kitchenId; }
  public void setKitchenId(String kitchenId) { this.kitchenId = kitchenId; }
  
  public List<Integer> getParentIds() { return parentIds; }
  public void setParentIds(List<Integer> parentIds) { this.parentIds = parentIds; }
}
