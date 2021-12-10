package com.viettel.vtnet360.kitchen.service;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.vt02.vt020011.entity.VT020011DayOffSettingEntity;

public class DayOffSettingWrapper extends BaseEntity {
  
  private List<VT020011DayOffSettingEntity> listDayOff;

  public List<VT020011DayOffSettingEntity> getListDayOff() { return listDayOff; }
  public void setListDayOff(List<VT020011DayOffSettingEntity> listDayOff) { this.listDayOff = listDayOff; }
}
