package com.viettel.vtnet360.checking.service;

import java.util.List;

import com.viettel.vtnet360.checking.service.entity.Checking;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Unit;

public class CheckingModel {
  
  private List<VT020000Unit>      units;
  private List<Checking>          checkings;
  private int                     totalRecords;
  
  public CheckingModel() { }
  
  public CheckingModel(List<VT020000Unit> units, List<Checking> checkings, int totalRecords) {
    this.units = units;
    this.checkings = checkings;
    this.totalRecords = totalRecords;
  }

  
  public List<VT020000Unit> getUnits() { return units; }
  public void setUnits(List<VT020000Unit> units) { this.units = units; }

  public List<Checking> getCheckings() { return checkings; }
  public void setCheckings(List<Checking> checkings) { this.checkings = checkings; }

  public int getTotalRecords() { return totalRecords; }
  public void setTotalRecords(int totalRecords) { this.totalRecords = totalRecords; }
}
