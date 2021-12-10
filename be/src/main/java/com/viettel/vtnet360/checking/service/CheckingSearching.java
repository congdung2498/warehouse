package com.viettel.vtnet360.checking.service;

import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.vt01.vt010011.entity.VT010011ListCondition;

public class CheckingSearching extends BaseEntity {

  private boolean               loadUnit;
  private String                unitId;
  private VT010011ListCondition config;
  
  
  public boolean isLoadUnit() { return loadUnit; }
  public void setLoadUnit(boolean loadUnit) { this.loadUnit = loadUnit; }
  
  public String getUnitId() { return unitId; }
  public void setUnitId(String unitId) { this.unitId = unitId; }

  public VT010011ListCondition getConfig() { return config; }
  public void setConfig(VT010011ListCondition config) { this.config = config; }
}
