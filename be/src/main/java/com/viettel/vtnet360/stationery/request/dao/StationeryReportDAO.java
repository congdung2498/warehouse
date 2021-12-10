package com.viettel.vtnet360.stationery.request.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.issuesService.entity.IssuesStationeryApprovedStatus;
import com.viettel.vtnet360.stationery.entity.CaculationUnitTemplacte;
import com.viettel.vtnet360.stationery.entity.CanceIssuesStationey;
import com.viettel.vtnet360.stationery.entity.DataResponseRatting;
import com.viettel.vtnet360.stationery.entity.DetailsEmployeeInfo;
import com.viettel.vtnet360.stationery.entity.InfoEmployee;
import com.viettel.vtnet360.stationery.entity.InfoToUpdateStorageHcdvRequest;
import com.viettel.vtnet360.stationery.entity.IssuesStationeryItemsToInsert;
import com.viettel.vtnet360.stationery.entity.MSystemCodeEntity;
import com.viettel.vtnet360.stationery.entity.PlaceEmployee;
import com.viettel.vtnet360.stationery.entity.PlaceNameAllResponseTemplate;
import com.viettel.vtnet360.stationery.entity.PlaceNameResponseTemplate;
import com.viettel.vtnet360.stationery.entity.ProcessIssuesStationery;
import com.viettel.vtnet360.stationery.entity.RequestParamEdit;
import com.viettel.vtnet360.stationery.entity.ResultConfigName;
import com.viettel.vtnet360.stationery.entity.SearchData;
import com.viettel.vtnet360.stationery.entity.SearchRequestParamPagging;
import com.viettel.vtnet360.stationery.entity.StationeryEmployee;
import com.viettel.vtnet360.stationery.entity.StationeryNameAllResponseTemplate;
import com.viettel.vtnet360.stationery.entity.StationeryNameResponseTemplate;
import com.viettel.vtnet360.stationery.entity.StationeryParam;
import com.viettel.vtnet360.stationery.entity.StatusById;
import com.viettel.vtnet360.stationery.entity.UpdateAllList;
import com.viettel.vtnet360.stationery.entity.UpdateIssuesStationery;
import com.viettel.vtnet360.stationery.entity.VoteHCDV;
import com.viettel.vtnet360.vt05.vt050003.entity.IssuesStationeryItems;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004DataResponse;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004DataResponseRequest;
import com.viettel.vtnet360.vt05.vt050012.entity.VT050012DataResponseQuota;
import com.viettel.vtnet360.vt05.vt050015.entity.ReportStationery;

@Repository
public interface StationeryReportDAO {
  
  public void insertMasterClass(ResultConfigName config);
  
  public int checkExistMasterClass(String masterCode);
  
  public String getRoleByUser(String username);

	public List<MSystemCodeEntity> findMSystemCodeByCodeName(MSystemCodeEntity mSystemCodeEntity);

	public List<StationeryEmployee> searchStationeryByEmployee(ReportStationery rpStationery);

	public List<IssuesStationeryItems> getDetailsIssuesStationeryItems(SearchData searchData);

	public int countStationeryByEmployee(ReportStationery reportStationery);

	public List<StationeryEmployee> getIssuesStationeryItemsById(SearchData searchData);

	public int cancelIssuesStationeryItems(CanceIssuesStationey canceIssuesStationey);

	public int cancelIssuesStationery(CanceIssuesStationey canceIssuesStationey);

	public int deleteIssuesStationeryItems(RequestParamEdit requestParamEdit);
	
	public void approvedeleteIssuesStationeryItems(RequestParamEdit requestParamEdit);
	
	public void updateIssueStationery(RequestParamEdit requestParamEdit);

	public void insertIssuesStationeryItems(IssuesStationeryItemsToInsert issuesStationeryItemsToInsert);
	
	public void saveIssuesStationeryItem(StationeryParam requestParam);

	// start phe duyet
	public int updateLDApproved(UpdateIssuesStationery updateIssuesStationery);

	public int updateIssuesStationeryItems(UpdateIssuesStationery updateIssuesStationery);
	// end phe duye

	// start phe duyet List
		public int updateLDApprovedList(UpdateAllList updateIssuesStationery);

		public int updateIssuesStationeryItemsList(UpdateAllList updateIssuesStationery);
		// end phe duyet List
	
	// start tu choi
	public int updateRefuseIssuesStationeryItems(UpdateIssuesStationery updateIssuesStationery);

	public int updateRefuseLD(UpdateIssuesStationery updateIssuesStationery);

	public int updateRefuseIssuesStationery(UpdateIssuesStationery updateIssuesStationery);
	// end tu choi

	public List<StatusById> findStatusInIssuesStaioneryItems(UpdateIssuesStationery updateIssuesStationery);

	public List<Integer> findStatusProcessIssuesStationery(String issuesStationeryID);

	// start xav hhan tiep phat
	public int confirmReceiptIssuesApprove(ProcessIssuesStationery processIssuesStationery);

	public int confirmReceiptIssuesItems(ProcessIssuesStationery processIssuesStationery);
	// end xac nhan cap nhat

	// start Hoãn thực hiện
	public int postponedImplementationApprove(ProcessIssuesStationery processIssuesStationery);

	public int postponedImplementationIssuesItems(ProcessIssuesStationery processIssuesStationery);

	public int postponedImplementationIssuesItemsHistory(ProcessIssuesStationery processIssuesStationery);

	// end Hoãn thực hiện
	// start không thực hiện được
	public int notPossibleApprove(ProcessIssuesStationery processIssuesStationery);

	public int notPossibleIssuesStationeryItems(ProcessIssuesStationery processIssuesStationery);

	public int notPossibleIssusStationery1(VT050004DataResponse processIssuesStationery);

	// end không thực hiện được
	// start dang xu li,da hoan thanh , 1 phan , tu choi , tu chối xác
	// nhận,không thuc hiên được

	public int notPossibleIssusStationery3(VT050004DataResponse processIssuesStationery);

	public int notPossibleIssusStationery2(VT050004DataResponse processIssuesStationery);

	public int notPossibleIssusStationery4(VT050004DataResponse processIssuesStationery);

	public int notPossibleIssusStationery7(VT050004DataResponse processIssuesStationery);

	public int notPossibleIssusStationery6(VT050004DataResponse processIssuesStationery);

	// end dang xu li,da hoan thanh , 1 phan , tu choi , tu chối xác nhận,không

	// start hoan thanh
	public int isCompleteIssusStationeryApprove(ProcessIssuesStationery processIssuesStationery);

	public int isCompleteIssusStationeryIssuesStationeryItem(ProcessIssuesStationery processIssuesStationery);

	public void isCompleteStorageHcvpRequest(InfoToUpdateStorageHcdvRequest info);

	// end hoan thanh
	// start xac nhan cap phat
	public int confirmationPendingApprove(ProcessIssuesStationery processIssuesStationery);

	public int confirmationPendingIssuesStationeryItems(VT050004DataResponseRequest processIssuesStationery);

	// end xac nhan cap phat
	// start tu choi xac nhan cap phat
	public int refuseConfirmationPendingApprove(ProcessIssuesStationery processIssuesStationery);

	public int refuseConfirmationPendingIssuesItems(ProcessIssuesStationery processIssuesStationery);
	// end tu choi xac nhan cap phat
	// start ratting
	public DataResponseRatting findDataToRatting(String issuesStationeryApproveID);
	// end ratting
	
	
	public InfoEmployee findInfoRequestEmployee(String issuesStationeryApproveID);
	
	public List<DetailsEmployeeInfo> findDetailsRequestEmployee(String issuesStationeryApproveID);
	
	public PlaceEmployee searchPlaceEmployeeById(int placeId);
	
	public DataResponseRatting findInfoRequestHcdv(String issuesStationeryApproveID);
	
	public List<VT050004DataResponse> findInfoRequestHcdvDetails(SearchRequestParamPagging param);
	
	public int voteHcdv(VoteHCDV voteHCDV);
	
	public int voteVptct(VoteHCDV voteHCDV);
	
	public int countInfoRequestHcdvDetails(String issuesStationeryApproveID);
	
	public VT050012DataResponseQuota getQuotaLimit(List<String> lisstUnitId);
	
	public double requestByUserName(String createUser);

	public double requestByUnitId(int unitId);
	
	public double findSpendingLimit(int unitId);
	
	public double findSpendingLimitString(String unitId);
	
	public double requestQuotaByUnitId(int unitId);
	
	public int getEndStationeryLimit();
	
	public int getUnitIdByUser(String userName);
	
	public List<IssuesStationeryApprovedStatus> getStatusApprovedByUserName(String hcdvUserName);
	
	public List<StationeryNameResponseTemplate> getStaioneryNameTemplate();
	
	public List<StationeryNameAllResponseTemplate> getStaioneryAllNameTemplate();
	
	public List<PlaceNameResponseTemplate> getPlaceNameTemplate();
	
	public List<PlaceNameAllResponseTemplate> getPlaceNameAllTemplate();
	
	public List<CaculationUnitTemplacte> getCucalationUnitTemplate();
	
	public List<ResultConfigName> getMasterClass();
	
	public List<ResultConfigName> getMasterClassWeb();
	
	public List<ResultConfigName> getMasterClassByCode(ResultConfigName configName);
	
	public int editTypeParam(ResultConfigName resultConfigName);
	
	public List<ResultConfigName> getParamWebConfigByCode(ResultConfigName configName);
	
	public int insertMasterCodeWeb(ResultConfigName resultConfigName);
	
	public int editWebParam(ResultConfigName resultConfigName);
	
	public List<ResultConfigName> getCodeValueByMasterClass(ResultConfigName configName);
	
	public List<ResultConfigName> getCountMasterClassByCode(ResultConfigName configName);
	
	public List<ResultConfigName> getParamProcessConfigByCode(ResultConfigName configName);
	
	public List<ResultConfigName> getCountParamWebConfigByCode(ResultConfigName configName);
	
	public List<ResultConfigName> getCountParamProcessConfigByCode(ResultConfigName configName);
	
	public int editProcessParam(ResultConfigName resultConfigName);
	
	public List<ResultConfigName> getAllProcessConfig();
	
	public String getUnitPathByUserName(String username);
	
	public String getUnitPathByUnitID(String unitID);
	
	public int checkExitsInParamweb(ResultConfigName resultConfigName);
	
	public List<StationeryEmployee> searchExportStationeryByEmployee(ReportStationery rpStationery);
}
