package com.viettel.vtnet360.stationery.request.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.viettel.vtnet360.stationery.entity.CheckVote;
import com.viettel.vtnet360.stationery.entity.RequestParamEdit;
import com.viettel.vtnet360.stationery.entity.StationeryEmployee;
import com.viettel.vtnet360.stationery.entity.StationeryNameAllResponseTemplate;
import com.viettel.vtnet360.stationery.service.ApprovingInfo;
import com.viettel.vtnet360.stationery.service.GettingItemsByStationeryIds;
import com.viettel.vtnet360.stationery.service.ItemModel;
import com.viettel.vtnet360.stationery.service.StationeryItem;
import com.viettel.vtnet360.stationery.service.StationeryQuota;
import com.viettel.vtnet360.stationery.service.StationeryStaff;
import com.viettel.vtnet360.stationery.service.StorageRequestItem;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Unit;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003RequestParam;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003StationeryParam;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004ParamToInsert;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013DataGetAll;

public interface StationeryDAO {
  
  public double calTotalByItems(RequestParamEdit requestParam);
  
  public int checkValidStationeryItems(VT050004ParamToInsert param);
  
  public int checkValidStationeryReport(String issueStationeryId);
  
  public int checkVoteHCDVValidStationeryReport(String issueStationeryId);
  
  public int checkVoteVPTCTValidStationeryReport(String issueStationeryId);
  
  public int checkValidStationery(String issueStationeryId);
  
  public String[] getHCDVByUnitId(int unitId);
  
  public double getHCDVRequest(@Param("usernames") String[] usernames);
  
  public List<StationeryItem> getStationeryItems();
  
  public List<StationeryNameAllResponseTemplate> getStationeryIdByName(String name);
  
  public List<Integer> getPlaceIdByName(String name);
  
  public List<VT020000Unit> getUnitInfo(int unitId);
  
  public List<Integer> getPlaceIdByHCDV(String username);
  
  public int getItemApproved(String issueStationeryId);
  
  public List<StationeryQuota> getStationeryQuotas(int unitId);
  
  public StationeryStaff getStationeryStaffByUser(String username);
  
  public StationeryStaff getStationeryStaffVPTCTByUser(String username);
  
  public VT050013DataGetAll getApproveById(String approveId);
  
  public List<ItemModel> getItemsByApproveId(String approveId);
  
  public List<StorageRequestItem> getStorageItemsByApproveId(String approveId);
  
  public List<ApprovingInfo> getApprovingInfo(String issueApproveId);
  
  public List<ApprovingInfo> getApprovingInfoVPP(String issueApproveId);
  
  public List<StationeryEmployee> getItemsByStationeryIds(GettingItemsByStationeryIds stationeryIds);
  
  public List<VT050003StationeryParam> getStationerys(VT050003RequestParam param);
  
  public double getHCDVLimit(StationeryStaff condition);
  
  public List<ItemModel> getItemsProcessByApproveId(String approveId);
  
  public VT050013DataGetAll getItemsByApproveIdDetails(String approveId);
  
  public List<ItemModel> getItemsProcessByApproveIdDetails(String approveId);
  
  public List<StorageRequestItem> getStorageItemsByApproveIdDetails(String approveId);
  
  public StationeryStaff getStationeryStaffByUserByUnitID(String unitID);
  
}
