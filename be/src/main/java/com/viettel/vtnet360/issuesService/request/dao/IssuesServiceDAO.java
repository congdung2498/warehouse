package com.viettel.vtnet360.issuesService.request.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.issuesService.entity.EmployeeEntity;
import com.viettel.vtnet360.issuesService.entity.IssuesServiceEntity;
import com.viettel.vtnet360.issuesService.entity.IssuesServiceSearch;
import com.viettel.vtnet360.issuesService.entity.MSystemCodeEntity;
import com.viettel.vtnet360.issuesService.entity.PlaceEntity;
import com.viettel.vtnet360.issuesService.entity.PlaceResult;
import com.viettel.vtnet360.issuesService.entity.ServiceEntity;
import com.viettel.vtnet360.issuesService.entity.UnitEntity;
import com.viettel.vtnet360.issuesService.entity.UnitResult;
import com.viettel.vtnet360.issuesService.entity.UserInfoEntity;
import com.viettel.vtnet360.vt04.vt040002.entity.VT040002HistoryService;

@Repository
public interface IssuesServiceDAO {

  public UserInfoEntity findUserInfoByUserName(String userName);

  public List<EmployeeEntity> findEmployeeByFullName(EmployeeEntity employee);

  public int insertIssuesService(Map<String, Object> data);

  public List<EmployeeEntity> findEmployeeByMulti(EmployeeEntity employee);

  public List<PlaceEntity> findListPlace(PlaceEntity place);

  public List<ServiceEntity> findListService(ServiceEntity service);

  public List<UnitEntity> findListUnit(UnitEntity unit);

  public List<IssuesServiceEntity> findListIssuesServiceBrowser(IssuesServiceSearch issuesServiceSearch);

  public int updateApprovedIssesService(IssuesServiceEntity issuesService);

  public List<String> searchFullNameInRole(String searchDTO);

  public List<PlaceResult> searchPlaceIsRole(String searchDTO);

  public List<UnitResult> searchUnitName(String searchDTO);
  
  public IssuesServiceEntity findIssuesServiceById(IssuesServiceEntity issuesService);

  public List<IssuesServiceEntity> findListIssuesServiceApprovedRequest(IssuesServiceSearch issuesServiceSearch);

  public void updateIssuedService(Map<String, Object> data);
  
  public void executiveService(Map<String, Object> data);
  
  public int totalIssuedService (Map<String, Object> dataPram);
  
  public int totalIssuesedServiceForApprove(Map<String, Object> data);
  
  public int totalIssuseService(IssuesServiceSearch issuesServiceSearch);
  
  public List<IssuesServiceEntity> findListIssuesService(IssuesServiceSearch issuesServiceSearch);
  
  public List<IssuesServiceEntity> checkExitsIssuesService(String userName);
  
  public List<IssuesServiceEntity> findListIssuesServiceHistoryByIdService(IssuesServiceSearch issuesServiceSearch);
  /*
	 * insert to ISSUES_STATIONERY_ITEMS when user request stationery
	 * 
	 * @param issuesStationeryItemsToInsert
	 */
	/*public void insertIssuesStationeryItems(IssuesStationeryItemsToInsert issuesStationeryItemsToInsert);*/
	public List<IssuesServiceEntity> findListIssuesServiceHistoryForMobile(VT040002HistoryService issuesServiceSearch);
	
	public List<Date> findDayOff(Date date);
	
	public List<MSystemCodeEntity> findMSystemCodeByCodeName(MSystemCodeEntity mSystemCodeEntity);
	
	public List<UnitEntity> getUnitListByID(String query);
}
