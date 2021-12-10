package com.viettel.vtnet360.kitchen.dto;

import java.util.List;

public class LunchModel {

  private List<LunchDTO> lunches;
  private int            totalRecords;
  
  public LunchModel(List<LunchDTO> lunches, int totalRecords) {
    this.lunches = lunches;
    this.totalRecords = totalRecords;
  }
  
  public List<LunchDTO> getLunches() { return lunches; }
  public void setLunches(List<LunchDTO> lunches) { this.lunches = lunches; }
  
  public int getTotalRecords() { return totalRecords; }
  public void setTotalRecords(int totalRecords) { this.totalRecords = totalRecords; }
}
