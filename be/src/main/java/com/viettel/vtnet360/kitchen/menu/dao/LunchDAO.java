package com.viettel.vtnet360.kitchen.menu.dao;

import java.util.List;

import com.viettel.vtnet360.kitchen.dto.ActionLog;
import com.viettel.vtnet360.kitchen.dto.GettingLunch;
import com.viettel.vtnet360.kitchen.dto.LunchDTO;
import com.viettel.vtnet360.kitchen.dto.MenuSetting;
import com.viettel.vtnet360.kitchen.dto.LunchVoteMessage;
import com.viettel.vtnet360.kitchen.dto.SearchingLunchModel;
import com.viettel.vtnet360.kitchen.dto.SearchingMenuByDate;
import com.viettel.vtnet360.kitchen.dto.UpdatingAllLunch;
import com.viettel.vtnet360.kitchen.service.LunchUser;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006ParamRequest;

public interface LunchDAO {
  
  public List<LunchUser> getRemindLunchUsers();
  
  public double getLunchPercent();
  
  public void updateUnitLunch();
  
  public int getUnitIdByUsername(String username);
  
  public List<LunchVoteMessage> getListLunchVoteMessage();
  
  public String getParentUnitId(String unitId);
  
  public List<MenuSetting> getMenuSettingByDate(SearchingMenuByDate searching);
  
  public List<LunchDTO> getLunchByDay(SearchingLunchModel param);
  
  public int getTotalLunchByDay(SearchingLunchModel param);
  
  public List<LunchDTO> getLunchByPeriod(SearchingLunchModel param);
  
  public int getTotalLunchByPeriod(SearchingLunchModel param);
  
  public void updateAllLunch(UpdatingAllLunch config);
  
  public void deleteAllLunch(UpdatingAllLunch config);
  
  public LunchDTO getLunchById(String lunchId);
  
  public LunchDTO getLunchByIdForMobile(GettingLunch lunch);
  
  public LunchDTO getLunchByUser(VT020006ParamRequest param);
  
  public void updateVoting(LunchDTO lunch);
  
  public void deleteByMonth(VT020006ParamRequest param);
  
  public void deleteByPeriod(VT020006ParamRequest param);
  
  public void updateSyncLunch();
  
  public void createActionLog(ActionLog action);
  
  public String checkUserExist(String username);
}
