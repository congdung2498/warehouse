package com.viettel.vtnet360.checking.service;

import com.viettel.vtnet360.common.security.BaseEntity;

public class Condition extends BaseEntity {
  
  private String barcode;
  private String search;
  private String[] userNames;

  
  public String getBarcode() { return barcode; }
  public void setBarcode(String barcode) { this.barcode = barcode; }
  
  public String getSearch() { return search; }
  public void setSearch(String search) { this.search = search; }
  
  public String[] getUserNames() { return userNames; }
  public void setUserNames(String[] userNames) { this.userNames = userNames; }
} 
