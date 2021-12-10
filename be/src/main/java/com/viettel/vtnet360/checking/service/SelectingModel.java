package com.viettel.vtnet360.checking.service;

import java.util.List;

import com.viettel.vtnet360.checking.service.entity.SystemCode;

public class SelectingModel {
  
  private List<SystemCode> places;
  private List<SystemCode> reasons;
  
  
  public SelectingModel(List<SystemCode> places, List<SystemCode> reasons) {
    this.places = places;
    this.reasons = reasons;
  }
  public List<SystemCode> getPlaces() { return places; }
  public void setPlaces(List<SystemCode> places) { this.places = places; }
  
  public List<SystemCode> getReasons() { return reasons; }
  public void setReasons(List<SystemCode> reasons) { this.reasons = reasons; }
}
