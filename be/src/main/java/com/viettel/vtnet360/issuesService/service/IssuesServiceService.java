package com.viettel.vtnet360.issuesService.service;

import java.io.File;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.viettel.vtnet360.issuesService.entity.EmployeeEntity;
import com.viettel.vtnet360.issuesService.entity.IssuesServiceEntity;
import com.viettel.vtnet360.issuesService.entity.IssuesServiceSearch;
import com.viettel.vtnet360.issuesService.entity.MSystemCodeEntity;
import com.viettel.vtnet360.issuesService.entity.PlaceEntity;
import com.viettel.vtnet360.issuesService.entity.ServiceEntity;
import com.viettel.vtnet360.issuesService.entity.UnitEntity;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityRqFind;
import com.viettel.vtnet360.vt04.vt040002.entity.VT040002HistoryService;
import com.viettel.vtnet360.vt04.vt040002.entity.VT040002Stationery;
import com.viettel.vtnet360.vt04.vt040004.entity.VT040004EntityRq;
import com.viettel.vtnet360.vt04.vt040007.entity.VT040007EntityRqExecutive;

public interface IssuesServiceService {

/*  public ResponseEntityBase getRequestModel(VT050012RequestParam param);*/

  /* public ResponseEntityBase findUserInfoByUserName(String userName); */

  public ResponseEntityBase findEmployeeByFullName(EmployeeEntity employee);

  public ResponseEntityBase insertIssuesService(IssuesServiceEntity requestParam, Principal principal);

  public ResponseEntityBase findEmployeeByMulti(EmployeeEntity employee);

  public ResponseEntityBase findListPlace(PlaceEntity place);

  public ResponseEntityBase findListService(ServiceEntity service);

  public ResponseEntityBase findListUnit(UnitEntity unit);

  public ResponseEntityBase findListIssuesServiceBrowser(IssuesServiceSearch issuesServiceSearch);

  public ResponseEntityBase updateApprovedIssesService(IssuesServiceSearch issuesServiceSearch);

  public ResponseEntityBase findIssuesServiceById(IssuesServiceEntity issuesService);

  public ResponseEntityBase findListIssuesServiceApprovedRequest(IssuesServiceSearch issuesServiceSearch);

  public ResponseEntityBase searchFullNameInRole(String searchDTO);

  public ResponseEntityBase updateIssuedService(IssuesServiceEntity requestParam, Principal principal,
      OAuth2Authentication authentication);
  
  public ResponseEntityBase executiveService(VT040007EntityRqExecutive requestParam, Principal principal)
			throws Exception;


  public ResponseEntityBase searchPlaceIsRole(String searchDTO);

  public ResponseEntityBase searchUnitName(String searchDTO);

  public ResponseEntityBase totalIssuedService(VT040000EntityRqFind requestParam, Principal principal,
      OAuth2Authentication authentication);

  public ResponseEntityBase totalIssuesedServiceForApprove(VT040004EntityRq requestParam, Principal principal,
      OAuth2Authentication authentication);

  public ResponseEntityBase findListIssuesService(IssuesServiceSearch issuesServiceSearch,
      Collection<GrantedAuthority> listRole) throws Exception;
  
  public ResponseEntityBase findListIssuesServiceHistoryByIdService(IssuesServiceSearch issuesServiceSearch ) throws Exception;
  
  	/*
 	 * insert to ISSUES_STATIONERY_ITEMS when user request stationery
 	 * 
 	 * @param issuesStationeryItemsToInsert
 	 */
 /* public ResponseEntityBase insertIssuesStationery(RequestParam requestParam, String userName, Collection<GrantedAuthority> roleList);*/
  /**
	 * send sms and notify for HCDV
	 * 
	 * @param userName
	 * @param placeID
	 * @param idRecord
	 * @param statusRecord
	 * @throws Exception
	 */
	public void sendSmsAndNotify(String userName, int placeID, String idRecord, int statusRecord) throws Exception;
	
	public ResponseEntityBase findListIssuesServiceHistoryForMobile(VT040002HistoryService issuesServiceSearch ) throws Exception;
	
/*	public ResponseEntityBase searchReportStationery(ReportStationery rpStationery);*/
	public File createExcelOutputExcel(VT040002Stationery stationeryInfo, Collection<GrantedAuthority> listRole)
			throws Exception;
	
	public ResponseEntityBase findMSystemCodeByCodeName(MSystemCodeEntity mSystemCodeEntity) throws Exception;
	
	public List<UnitEntity> getUnitListByID(String userName, Collection<GrantedAuthority> authority, String string) throws Exception;

}
