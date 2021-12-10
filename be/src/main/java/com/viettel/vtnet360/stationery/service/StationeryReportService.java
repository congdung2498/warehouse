package com.viettel.vtnet360.stationery.service;

import java.io.File;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.stationery.entity.CanceIssuesStationey;
import com.viettel.vtnet360.stationery.entity.MSystemCodeEntity;
import com.viettel.vtnet360.stationery.entity.PlaceEmployeeParam;
import com.viettel.vtnet360.stationery.entity.ProcessIssuesStationery;
import com.viettel.vtnet360.stationery.entity.RequestParam;
import com.viettel.vtnet360.stationery.entity.RequestParamEdit;
import com.viettel.vtnet360.stationery.entity.ResultConfigName;
import com.viettel.vtnet360.stationery.entity.SearchData;
import com.viettel.vtnet360.stationery.entity.SearchRequestParamPagging;
import com.viettel.vtnet360.stationery.entity.StationeryEmployeeParam;
import com.viettel.vtnet360.stationery.entity.StationeryParam;
import com.viettel.vtnet360.stationery.entity.UpdateAllList;
import com.viettel.vtnet360.stationery.entity.UpdateIssuesStationery;
import com.viettel.vtnet360.stationery.entity.VoteHCDV;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040002.entity.VT040002Stationery;
import com.viettel.vtnet360.vt05.vt050006.entity.VT050006RequestParam;
import com.viettel.vtnet360.vt05.vt050015.entity.ReportStationery;

public interface StationeryReportService {
  
  public ResponseEntityBase insertIssuesStationery(RequestParam requestParam, String userName,
      Collection<GrantedAuthority> roleList);
  
  public ResponseEntityBase getSpendingLimit(String userName);

	public ResponseEntityBase findMSystemCodeByCodeName(MSystemCodeEntity mSystemCodeEntity) throws Exception;

	public ResponseEntityBase searchStationeryByEmployee(ReportStationery rpStationery, String loginUsername, Collection<GrantedAuthority> listRole);

	public ResponseEntityBase getDetailsIssuesStationeryItems(SearchData searchData);

	public ResponseEntityBase updateApproveIssuesStationery(UpdateIssuesStationery updateIssuesStationery);

	public ResponseEntityBase updateRefuseIssuesStationery(UpdateIssuesStationery updateIssuesStationery);

	public ResponseEntityBase countStationeryByEmployee(ReportStationery rpStationery, String loginUsername, Collection<GrantedAuthority> listRole);

	public ResponseEntityBase getIssuesStationeryItemsById(SearchData searchData);

	public ResponseEntityBase updateCancelIssuesStationery(CanceIssuesStationey canceIssuesStationey);

	public ResponseEntityBase editIssuesStationery(RequestParamEdit requestParam, String userName,
			Collection<GrantedAuthority> roleList);

	// xac nhan tiep nhậm
	public ResponseEntityBase confirmReceiptIssuesStationery(ProcessIssuesStationery processIssuesStationery);

	// hoan thuc hien
	public ResponseEntityBase postponedImplementationIssuesItems(ProcessIssuesStationery processIssuesStationery);

	// khong thuc hien duoc
	public ResponseEntityBase notPossibleApprove(ProcessIssuesStationery processIssuesStationery);

	// hoan thanh
	public ResponseEntityBase isCompleteIssusStationery(ProcessIssuesStationery processIssuesStationery);
	
	// xac nhan cap phat
	public ResponseEntityBase confirmationPendingApprove(ProcessIssuesStationery processIssuesStationery);
	
	// tu choi cap phat
	public ResponseEntityBase refuseConfirmationPendingApprove(ProcessIssuesStationery processIssuesStationery);
	
	public ResponseEntityBase findDataToRatting(SearchRequestParamPagging param);
	
	public ResponseEntityBase findDataProcessToRatting(SearchRequestParamPagging param);
	
	public ResponseEntityBase findDataToProcess(SearchRequestParamPagging param);
	
	public ResponseEntityBase findDataToStationeryEmployee(StationeryEmployeeParam param);
	
	public ResponseEntityBase searchPlaceEmployeeById(PlaceEmployeeParam param);
	
	public ResponseEntityBase voteHcdv(VoteHCDV voteHCDV);
	
	public ResponseEntityBase voteVptct(VoteHCDV voteHCDV);
	
	public ResponseEntityBase countInfoRequestHcdvDetails(VT050006RequestParam param);
	
//	public ResponseEntityBase getQuotaLimit(StationeryQuotaParam param);
	
	public ResponseEntityBase requestByUserName(StationeryParam param);
	
//	public ResponseEntityBase getQuotaLimit(StationeryParam param);
	
	public ResponseEntityBase requestByUnitId(StationeryParam param);
	
	public ResponseEntityBase findSpendingLimit(int unitId);
	
	public ResponseEntityBase findSpendingLimitString(String unitId);
	
	public File createExcel(ReportStationery rpStationery, String loginUserName) throws Exception;
	
	public File createExcelEmployee(ReportStationery rpStationery, String loginUserName, Collection<GrantedAuthority> listRole) throws Exception;
	
	public ResponseEntityBase updateApproveIssuesStationeryAllList(UpdateAllList updateIssuesStationery);
	
	public ResponseEntityBase getMasterClass();
	
	public ResponseEntityBase getMasterClassWeb();
	//start master class
	public ResponseEntityBase getMasterClassByCode(ResultConfigName resultConfigName );
	
	public ResponseEntityBase editTypeParam(ResultConfigName resultConfigName);
	
	public ResponseEntityBase getCountMasterClassByCode(ResultConfigName resultConfigName );
	//end master class
	//start param web conffig
	public ResponseEntityBase getParamWebConfigByCode(ResultConfigName resultConfigName );
	
	public ResponseEntityBase getCountParamWebConfigByCode(ResultConfigName resultConfigName);
	
	public ResponseEntityBase insertMasterCodeWeb(ResultConfigName resultConfigName);
	
	public ResponseEntityBase editWebParam(ResultConfigName resultConfigName);
	//end param web conffig
	
	public ResponseEntityBase getCodeValueByMasterClass(ResultConfigName resultConfigName);
	
	
	public double findSpendingLimitQuota(String username,Collection<GrantedAuthority> roleList);
	//start ParamProcessConfig
	public ResponseEntityBase getParamProcessConfigByCode(ResultConfigName resultConfigName );
	
	public ResponseEntityBase getCountParamProcessConfigByCode(ResultConfigName resultConfigName );
	//end ParamProcessConfig

	public ResponseEntityBase editProcessParam(ResultConfigName resultConfigName);
	
	public ResponseEntityBase getAllProcessConfig();
	
	public ResponseEntityBase findDataToRattingDetails(SearchRequestParamPagging param);
	
	public ResponseEntityBase checkHcdvInStaff(String userName);
	
	public ResponseEntityBase checkVPCTCTInStaff(String userName);
}
