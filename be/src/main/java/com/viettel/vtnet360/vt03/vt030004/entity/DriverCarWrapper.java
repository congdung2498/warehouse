package com.viettel.vtnet360.vt03.vt030004.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

public class DriverCarWrapper extends BaseEntity {
  
  private List<DriveCar> matches;

  public List<DriveCar> getMatches() { return matches; }
  public void setMatches(List<DriveCar> matches) { this.matches = matches; }
}
