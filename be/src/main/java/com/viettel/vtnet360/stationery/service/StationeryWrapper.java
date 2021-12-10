package com.viettel.vtnet360.stationery.service;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.vt05.vt050001.entity.Stationery;

public class StationeryWrapper extends BaseEntity {
  
  private List<Stationery> stationerys;

  public List<Stationery> getStationerys() { return stationerys; }
  public void setStationerys(List<Stationery> stationerys) { this.stationerys = stationerys; }
}
