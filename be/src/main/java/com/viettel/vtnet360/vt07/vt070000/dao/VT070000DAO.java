package com.viettel.vtnet360.vt07.vt070000.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt07.vt070000.entity.DocumentDetails;
import com.viettel.vtnet360.vt07.vt070000.entity.RackDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.RequestParam;
import com.viettel.vtnet360.vt07.vt070000.entity.SlotDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxDetailsRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxSearchRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.UpdateStatusRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseRackSlot;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseRequestParam;
import com.viettel.vtnet360.vt07.vt070000.entity.projecttree.ProjectTree;
import com.viettel.vtnet360.vt07.vt070000.entity.search.ConstructionDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.ContractDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.DocumentDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.DocumentSearchRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.search.FolderDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.OfficialDispatchDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.PackageDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.PaymentSummaryDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.ProjectDetailSearch;
import com.viettel.vtnet360.vt07.vt070000.entity.search.VoucherDetailSearch;
import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;

@Repository
public interface VT070000DAO {

	public List<RackDetail> getListRackDetail(RequestParam rackRequestParam);
	
	public List<WarehouseRequestParam> getListWareHouseDetail(WarehouseRequestParam warehouseDetail);
	
	public int getTotalRecord(WarehouseRequestParam warehouseRequestParam);
	
	public List<WarehouseRequestParam> insertUpdate(WarehouseRequestParam warehouseDetail);
	
	public int isDelete(String warehouseId);
	
	public int checkExistsWarehouse(String warehouseId);
	
	public int updateWarehouse(WarehouseRequestParam warehouseDetail);
	
	public int insertWarehouse(WarehouseRequestParam warehouseDetail);
	
	public int insertRack(RackDetail rackDetail);
	
	public int insertSlot(SlotDetail slotDetail);
	
	public int deleteWarehouse(WarehouseRequestParam warehouseDetail);
	
	public int deleteRackAfterChangeWarehouse(String warehouseId);
	
	public int deleteRackWithRowColumn(@Param("warehouseId") long warehouseId, @Param("rowNum") int rowNum,@Param("colNum") int columnNum);
	
	public int deleteSlotWithRowColumn(@Param("warehouseId") long warehouseId, @Param("rowNum") int rowNum,@Param("colNum") int columnNum);
	
	public int deleteSlotHeightWithRackId(@Param("rackId") long rackId, @Param("heightNum") int heightNum);
	
	public int deleteSlotAfterChangeWarehouse(String warehouseId);
	
	public List<SlotDetail> getListSlotWithRowColumn(@Param("warehouseId") long warehouseId, 
			@Param("oldRowNum") int oldRowNum, @Param("rowNum") int rowNum,
			@Param("oldColumnNum") int oldColumnNum, @Param("columnNum") int columnNum);
	
	public RackDetail getRackWithRowColumnOfWarehouse(@Param("warehouseId")String warehouseId, @Param("rowNum") int rowNum, @Param("columnNum") int columnNum);
	
	public WarehouseRequestParam getWarehouseDetailById(String warehouseId);
	
	public List<RackDetail> getListRackDetailByWarehouseId(String warehouseId);
	
	public int countSlotUse(long rackId);
	
	public List<WarehouseRackSlot> getListSlotUseInWarehouse(WarehouseRequestParam warehouseRequestParam);
	
	public List<WarehouseRackSlot> getFullListSlotUseInWarehouse(WarehouseRequestParam warehouseRequestParam);
	
	public List<WarehouseRequestParam> getAllListWarehouseDetail(WarehouseRequestParam warehouseDetail);
	
	public int getTotalRecordWarehouseSlot(String warehouseId);
	
	public int countSlotActive(String warehouseId);
	public List<SlotDetail> getListSlotDetailByRackId(long rackId);
	public List<RackDetail> getListRackDetailByWarehouseId(long warehouseId);
	public List<WarehouseDetail> getListWarehouse(RequestParam rackRequestParam);
	public List<SlotDetail> getListSlotDetailByQrCode(String qrCode);
	public List<RackDetail> getListRackDetailByQrCode(String qrCode);
	public List<WarehouseDetail> getListWarehouseDetailByQrCode(String qrCode);
	public int getNumberOfWarehouse(RequestParam rackRequestParam);
	public int updateRackDetail(RackDetail rackDetail);
	public int updateSlotDetail(SlotDetail slotDetail);
	
	public List<RackDetail> findRack(RackDetail object);	
	public int updatePrintTimeRack(List<Integer> list);	
	
	public int getNumberOfMainWarehouse(RequestParam rackRequestParam);
	public List<WarehouseDetail> getListMainWarehouseDetail(RequestParam rackRequestParam);
	
	//picasso
	//for search document
	public List<WarehouseDetail> getAllActiveWarehouse();
	
	public List<Long> searchTinBoxByName(String searchPattern);	
	public List<Long> searchFolderByName(String searchPattern);	
	public List<Long> searchProjectByName(String searchPattern);
	public List<Long> searchPackageByName(String searchPattern);
	public List<Long> searchContractByName(String searchPattern);
	public List<Long> searchConstructionByName(String searchPattern);
	
	public List<Long> searchFolderByProjectDoc(String searchPattern);
	public List<Long> searchFolderByPackageDoc(String searchPattern);
	public List<Long> searchFolderByContractDoc(String searchPattern);
	public List<Long> searchFolderByConstructionDoc(String searchPattern);
	
	public List<Long> searchTinBoxByFolderName(String searchPattern);
	public List<Long> searchProjectByPackageName(String searchPattern);
	
	//return all contract ids where construction inside contains the search keyword
	public List<Long> searchContractByConstruction(String searchPattern);	
	
	public List<Long> searchProjectByContractIdList(List<Long> contractIdList);
	public List<Long> searchProjectByPackageIdList(List<Long> packageIdList);
	public List<Long> searchFolderByContractIdList(List<Long> contractIdList);
	public List<Long> searchFolderByConstructionIdList(List<Long> constructionIdList);
	public List<Long> searchFolderByPackageIdList(List<Long> packageIdList); 
	
	//return all project ids where CONTRACT or PROJECT in that project or PROJECT itself has the keyword in name
	public List<Long> searchProjectByKeyword(String searchPattern);
	
	public List<Long> searchFolderByProjectIdList(List<Long> projectIdList);
	
	//return all folder ids where name of document (4 types) contains the search keyword - contract_doc, contrucstion_doc, project_doc, package_doc 
	public List<Long> searchFolderContainsKeyword(String searchPattern);
	
	//return all tin box ids which those folders are in
	public List<Long> searchTinBoxByFolderIdList(List<Long> folderIdList);
	
	public List<TinBoxDetail> getAllTinBox(long warehouseId);
	
	//return tin box list in a warehouse with id in a specific list
	public List<TinBoxDetail> getTinBox(@Param("tinBoxIdList") List<Long> tinBoxIdList, @Param("warehouseId") long warehouseId);
	
	public String getWarehouseNameById(long warehouseId);
	public SlotDetail getSlotById(long slotId);
	public RackDetail getRackById(long rackId);
	public long getRackIdBySlotId(long slotId);
	
	// for tin box details screen
	public TinBoxDetail getTinBoxById(long tinBoxId);	
	public List<FolderDetail> getFolderInTinBox(long tinBoxId);	
	public List<DocumentDetails> getAllDocumentInFolder(FolderDetail folder);
	
	public long getProjectIdByDoc(long docId);
	public long getPackageIdByDoc(long docId);
	public long getContractIdByDoc(long docId);
	public long getConstructionIdByDoc(long docId);
	
	public String getProjectName(long id);
	public String getPackageName(long id);
	public String getContractName(long id);
	public String getConstructionName(long id);
	
	public String getFolderQrCode(long folderId);
	public String getProjectNameByPackageDoc(long docId);
	public String getProjectNameByContractDoc(long docId);
	public String getProjectNameByContractDocV2(long docId);
	public String getProjectNameByConstructionDoc(long docId);
	public String getProjectNameByConstructionDocV2(long docId);
	
	public List<DocumentDetails> getAllDocumentInFolderV2(FolderDetail folder);
	public String getProjectNameByContractDocV3(long docId);
	public String getProjectNameByConstructionDocV3(long docId);
	public String getContractNameByConstructionId(long constructionId);
	
	public List<FolderDetail> getFolderByQRCode(String qrCode);	
	public List<FolderDetail> getFolderByQRCodeGroup(String qrCode);	
	
	// for importing data from excel
	public int logImportDataAPICall(@Param("createUser") String createUser, @Param("jsonData") String jsonData);
	
	// get Id will return -1 if not found anything
	public long getFolderId(@Param("folderQrCode") String folderQrCode, @Param("tinBoxQrCode") String tinBoxQrCode, @Param("unit") int unit, @Param("folderType") int type);
	public long getFolderIdByQrAndTypeAndUnit(@Param("folderQrCode") String folderQrCode, @Param("folderType") int type, @Param("unit") int unit);
	public long getFolderIdByQrAndUnit(@Param("folderQrCode") String folderQrCode, @Param("unit") int unit);

	public long getProjectId(String projectName);	
	
	public int insertProjectDoc(@Param("projectDoc") String projectDoc, @Param("folderId") long folderId, @Param("projectId") long projectId, @Param("levelBaoMatProject") String levelBaoMatProject);
	public long getValidPackageId(@Param("packageName") String packageName, @Param("projectId") long projectId);
	public int insertPackageDoc(@Param("packageDoc") String packageDoc, @Param("folderId") long folderId, @Param("packageId") long packageId, @Param("levelBaoMatPackage") String levelBaoMatPackage);
	public long getValidContractId(@Param("contractName") String contractName, @Param("packageId") long packageId);
	public int insertContractDoc(@Param("contractDoc") String contractDoc, @Param("folderId") long folderId, @Param("contractId") long contractId,@Param("levelBaoMatContract") String levelBaoMatContract);
	public long getValidConstructionId(@Param("constructionName") String constructionName, @Param("contractId") long contractId);
	public int insertConstructionDoc(@Param("constructionDoc") String constructionDoc, @Param("folderId") long folderId, @Param("constructionId") long constructionId, @Param("levelBaoMatConstruction") String levelBaoMatConstruction);
	
	public long checkExistPrjDoc(@Param("projectId") long projectId, @Param("projectDoc") String projectDoc);
	public long checkExistPkgDoc(@Param("packageId") long packageId, @Param("packageDoc") String packageDoc);
	public long checkExistCtDoc(@Param("contractId") long contractId, @Param("contractDoc") String contractDoc);
	public long checkExistConsDoc(@Param("constructionId") long constructionId, @Param("constructionDoc") String constructionDoc);
	
	public long getFolderIdOfProjectDoc(@Param("prjDocExistedId") long prjDocExistedId);
	public int bindPrjDocToFolder(@Param("prjDocExistedId") long prjDocExistedId, @Param("folderId") long folderId);
	
	public long getFolderIdOfPkgDoc(@Param("pkgDocExistedId") long pkgDocExistedId);
	public int bindPkgDocToFolder(@Param("pkgDocExistedId") long pkgDocExistedId, @Param("folderId") long folderId);
	
	public long getFolderIdOfCtDoc(@Param("ctDocExistedId") long ctDocExistedId);
	public int bindCtDocToFolder(@Param("ctDocExistedId") long ctDocExistedId, @Param("folderId") long folderId);
	
	public long getFolderIdOfConsDoc(@Param("consDocExistedId") long consDocExistedId);
	public int bindConsDocToFolder(@Param("consDocExistedId") long consDocExistedId, @Param("folderId") long folderId);
	
	public int createProject(@Param("projectName") String projectName, @Param("unit") int unit, @Param("rowIndex") int rowIndex);
	public int createPackage(@Param("packageName") String packageName, @Param("projectId") long projectId, @Param("rowIndex") int rowIndex);
	public int createContract(@Param("contractName") String contractName, @Param("projectId") long projectId,  @Param("unit") int unit, @Param("rowIndex") int rowIndex,@Param("packageId") long packageId);
	public int createConstruction(@Param("constructionName") String constructionName, @Param("contractId") long contractId);
	public int bindProjectToFolder(@Param("folderId") long folderId, @Param("projectId") long projectId, @Param("createUser") String createUser);
	
	public int checkTinBoxCreateUser(@Param("tinBoxId") long tinBoxId, @Param("createUser") String createUser);
	//end picasso
	
	
	public List<TinBoxDetail> searchTinBox(TinBoxSearchRequest requestParam);	
	public List<DocumentDetails> getDocumentInTinBox(TinBoxDetailsRequest tinBoxRq);
	public long getTinBoxId(@Param("tinBoxQrCode") String tinBoxQrCode, @Param("unit") int unit);
	
	
	// Group region
	
	public List<RackDetail> getListRackDetailGroup(RequestParam rackRequestParam);	
	public List<WarehouseRequestParam> getListWareHouseDetailGroup(WarehouseRequestParam warehouseDetail);	
	public int getTotalRecordGroup(WarehouseRequestParam warehouseRequestParam);	
	public List<WarehouseRequestParam> insertUpdateGroup(WarehouseRequestParam warehouseDetail);	
	public int isDeleteGroup(String warehouseId);
	public int checkExistsWarehouseGroup(String warehouseId);	
	public int updateWarehouseGroup(WarehouseRequestParam warehouseDetail);	
	public int insertWarehouseGroup(WarehouseRequestParam warehouseDetail);	
	public int insertRackGroup(RackDetail rackDetail);	
	public int insertSlotGroup(SlotDetail slotDetail);	
	public int deleteWarehouseGroup(WarehouseRequestParam warehouseDetail);	
	public int deleteRackAfterChangeWarehouseGroup(String warehouseId);	
	public int deleteRackWithRowColumnGroup(@Param("warehouseId") long warehouseId, @Param("rowNum") int rowNum,@Param("colNum") int columnNum);	
	public int deleteSlotWithRowColumnGroup(@Param("warehouseId") long warehouseId, @Param("rowNum") int rowNum,@Param("colNum") int columnNum);	
	public int deleteSlotHeightWithRackIdGroup(@Param("rackId") long rackId, @Param("heightNum") int heightNum);	
	public int deleteSlotAfterChangeWarehouseGroup(String warehouseId);	
	public List<SlotDetail> getListSlotWithRowColumnGroup(@Param("warehouseId") long warehouseId, 
			@Param("oldRowNum") int oldRowNum, @Param("rowNum") int rowNum,
			@Param("oldColumnNum") int oldColumnNum, @Param("columnNum") int columnNum);	
	public RackDetail getRackWithRowColumnOfWarehouseGroup(@Param("warehouseId")String warehouseId, @Param("rowNum") int rowNum, @Param("columnNum") int columnNum);
	public WarehouseRequestParam getWarehouseDetailByIdGroup(String warehouseId);	
	public List<RackDetail> getListRackDetailByWarehouseIdGroup(String warehouseId);	
	public int countSlotUseGroup(long rackId);
	public List<WarehouseRackSlot> getListSlotUseInWarehouseGroup(WarehouseRequestParam warehouseRequestParam);	
	public List<WarehouseRackSlot> getFullListSlotUseInWarehouseGroup(WarehouseRequestParam warehouseRequestParam);	
	public List<WarehouseRequestParam> getAllListWarehouseDetailGroup(WarehouseRequestParam warehouseDetail);	
	public int getTotalRecordWarehouseSlotGroup(String warehouseId);
	public int countSlotActiveGroup(String warehouseId);
	public List<SlotDetail> getListSlotDetailByRackIdGroup(long rackId);
	public List<RackDetail> getListRackDetailByWarehouseIdGroup(long warehouseId);
	public List<WarehouseDetail> getListWarehouseGroup(RequestParam rackRequestParam);
	public List<SlotDetail> getListSlotDetailByQrCodeGroup(String qrCode);
	public List<RackDetail> getListRackDetailByQrCodeGroup(String qrCode);
	public List<WarehouseDetail> getListWarehouseDetailByQrCodeGroup(String qrCode);
	public int getNumberOfWarehouseGroup(RequestParam rackRequestParam);
	public int updateRackDetailGroup(RackDetail rackDetail);
	public int updateSlotDetailGroup(SlotDetail slotDetail);	
	public List<RackDetail> findRackGroup(RackDetail object);	
	public int updatePrintTimeRackGroup(List<Integer> list);	
	public int getNumberOfMainWarehouseGroup(RequestParam rackRequestParam);
	public List<WarehouseDetail> getListMainWarehouseDetailGroup(RequestParam rackRequestParam);
	public List<WarehouseDetail> getAllActiveWarehouseGroup();	
	public List<Long> searchTinBoxByNameGroup(String searchPattern);	
	public List<Long> searchFolderByNameGroup(String searchPattern);	
	public List<Long> searchProjectByNameGroup(String searchPattern);
	public List<Long> searchPackageByNameGroup(String searchPattern);
	public List<Long> searchContractByNameGroup(String searchPattern);
	public List<Long> searchConstructionByNameGroup(String searchPattern);	
	public List<Long> searchFolderByProjectDocGroup(String searchPattern);
	public List<Long> searchFolderByPackageDocGroup(String searchPattern);
	public List<Long> searchFolderByContractDocGroup(String searchPattern);
	public List<Long> searchFolderByConstructionDocGroup(String searchPattern);	
	public List<Long> searchTinBoxByFolderNameGroup(String searchPattern);
	public List<Long> searchProjectByPackageNameGroup(String searchPattern);
	public List<Long> searchContractByConstructionGroup(String searchPattern);
	public List<Long> searchProjectByContractIdListGroup(List<Long> contractIdList);
	public List<Long> searchProjectByPackageIdListGroup(List<Long> packageIdList);
	public List<Long> searchFolderByContractIdListGroup(List<Long> contractIdList);
	public List<Long> searchFolderByConstructionIdListGroup(List<Long> constructionIdList);
	public List<Long> searchFolderByPackageIdListGroup(List<Long> packageIdList);
	public List<Long> searchProjectByKeywordGroup(String searchPattern);
	public List<Long> searchFolderByProjectIdListGroup(List<Long> projectIdList);
	public List<Long> searchFolderContainsKeywordGroup(String searchPattern);
	public List<Long> searchTinBoxByFolderIdListGroup(List<Long> folderIdList);
	public List<TinBoxDetail> getAllTinBoxGroup(long warehouseId);
	public List<TinBoxDetail> getTinBoxGroup(@Param("tinBoxIdList") List<Long> tinBoxIdList, @Param("warehouseId") long warehouseId);
	public String getWarehouseNameByIdGroup(long warehouseId);
	public SlotDetail getSlotByIdGroup(long slotId);
	public RackDetail getRackByIdGroup(long rackId);
	public long getRackIdBySlotIdGroup(long slotId);
	public TinBoxDetail getTinBoxByIdGroup(long tinBoxId);	
	public List<FolderDetail> getFolderInTinBoxGroup(long tinBoxId);	
	public List<DocumentDetails> getAllDocumentInFolderGroup(FolderDetail folder);
	public long getProjectIdByDocGroup(long docId);
	public long getPackageIdByDocGroup(long docId);
	public long getContractIdByDocGroup(long docId);
	public long getConstructionIdByDocGroup(long docId);
	public String getProjectNameGroup(long id);
	public String getPackageNameGroup(long id);
	public String getContractNameGroup(long id);
	public String getConstructionNameGroup(long id);
	public String getFolderQrCodeGroup(long folderId);
	public String getProjectNameByPackageDocGroup(long docId);
	public String getProjectNameByContractDocGroup(long docId);
	public String getProjectNameByContractDocV2Group(long docId);
	public String getProjectNameByConstructionDocGroup(long docId);
	public String getProjectNameByConstructionDocV2Group(long docId);
	public List<DocumentDetails> getAllDocumentInFolderV2Group(FolderDetail folder);
	public String getProjectNameByContractDocV3Group(long docId);
	public String getProjectNameByConstructionDocV3Group(long docId);
	public String getContractNameByConstructionIdGroup(long constructionId);
	public int logImportDataAPICallGroup(@Param("createUser") String createUser, @Param("jsonData") String jsonData);
	public long getFolderIdGroup(@Param("folderQrCode") String folderQrCode, @Param("tinBoxQrCode") String tinBoxQrCode, @Param("unit") int unit);
	public long getProjectIdGroup(String projectName);	
	public int insertProjectDocGroup(@Param("projectDoc") String projectDoc, @Param("folderId") long folderId, @Param("projectId") long projectId);
	public long getValidPackageIdGroup(@Param("packageName") String packageName, @Param("projectId") long projectId);
	public int insertPackageDocGroup(@Param("packageDoc") String packageDoc, @Param("folderId") long folderId, @Param("packageId") long packageId);
	public long getValidContractIdGroup(@Param("contractName") String contractName, @Param("packageId") long packageId);
	public int insertContractDocGroup(@Param("contractDoc") String contractDoc, @Param("folderId") long folderId, @Param("contractId") long contractId);
	public long getValidConstructionIdGroup(@Param("constructionName") String constructionName, @Param("contractId") long contractId);
	public int insertConstructionDocGroup(@Param("constructionDoc") String constructionDoc, @Param("folderId") long folderId, @Param("constructionId") long constructionId);
	public long checkExistPrjDocGroup(@Param("projectId") long projectId, @Param("projectDoc") String projectDoc);
	public long checkExistPkgDocGroup(@Param("packageId") long packageId, @Param("packageDoc") String packageDoc);
	public long checkExistCtDocGroup(@Param("contractId") long contractId, @Param("contractDoc") String contractDoc);
	public long checkExistConsDocGroup(@Param("constructionId") long constructionId, @Param("constructionDoc") String constructionDoc);
	public long getFolderIdOfProjectDocGroup(@Param("prjDocExistedId") long prjDocExistedId);
	public int bindPrjDocToFolderGroup(@Param("prjDocExistedId") long prjDocExistedId, @Param("folderId") long folderId);
	public long getFolderIdOfPkgDocGroup(@Param("pkgDocExistedId") long pkgDocExistedId);
	public int bindPkgDocToFolderGroup(@Param("pkgDocExistedId") long pkgDocExistedId, @Param("folderId") long folderId);
	public long getFolderIdOfCtDocGroup(@Param("ctDocExistedId") long ctDocExistedId);
	public int bindCtDocToFolderGroup(@Param("ctDocExistedId") long ctDocExistedId, @Param("folderId") long folderId);
	public long getFolderIdOfConsDocGroup(@Param("consDocExistedId") long consDocExistedId);
	public int bindConsDocToFolderGroup(@Param("consDocExistedId") long consDocExistedId, @Param("folderId") long folderId);
	public int createProjectGroup(@Param("projectName") String projectName, @Param("unit") int unit, @Param("rowIndex") int rowIndex);
	public int createPackageGroup(@Param("packageName") String packageName, @Param("projectId") long projectId, @Param("rowIndex") int rowIndex);
	public int createContractGroup(@Param("contractName") String contractName, @Param("projectId") long projectId,  @Param("unit") int unit, @Param("rowIndex") int rowIndex);
	public int createConstructionGroup(@Param("constructionName") String constructionName, @Param("contractId") long contractId);
	public int bindProjectToFolderGroup(@Param("folderId") long folderId, @Param("projectId") long projectId, @Param("createUser") String createUser);
	public int checkTinBoxCreateUserGroup(@Param("tinBoxId") long tinBoxId, @Param("createUser") String createUser);
	public List<TinBoxDetail> searchTinBoxGroup(TinBoxSearchRequest requestParam);	
	public List<DocumentDetails> getDocumentInTinBoxGroup(long tinBoxId);
	public long getTinBoxIdGroup(@Param("tinBoxQrCode") String tinBoxQrCode, @Param("unit") int unit);	
	
	public int updateStatusBySearchType(UpdateStatusRequest updateSttRequest);
	
	public List<PaymentSummaryDetailSearch> getPaymentSummaryByDocName(DocumentSearchRequest documentSearchRequest);
	public List<VoucherDetailSearch> getVoucherByDocNameAndType(@Param("documentSearchRequest") DocumentSearchRequest documentSearchRequest, @Param("type") long type );
	public List<OfficialDispatchDetailSearch> getOfficialDispatchByIncomingDocName(DocumentSearchRequest documentSearchRequest);
	public List<OfficialDispatchDetailSearch> getOfficialDispatchBytravelsDocName(DocumentSearchRequest documentSearchRequest);
	public List<ContractDetailSearch> getContractByDocName(DocumentSearchRequest documentSearchRequest);
	public List<PackageDetailSearch> getPackageByDocName(DocumentSearchRequest documentSearchRequest);
	public List<ProjectDetailSearch> getProjectByDocName(DocumentSearchRequest documentSearchRequest);
	public List<FolderDetailSearch> getFolderByDocName(DocumentSearchRequest documentSearchRequest);
	public List<TinBoxDetail> getTinBoxByName(DocumentSearchRequest documentSearchRequest);
	public List<ConstructionDetailSearch> getConstructionByDocName(DocumentSearchRequest documentSearchRequest);
	
	public List<DocumentDetailSearch> getDocDetailByIdAndType(@Param("id") long id,@Param("type") long type,@Param("folderId") long folderId);
	
	public com.viettel.vtnet360.vt07.vt070000.entity.projecttree.ProjectTree getProjectTree(long projectId);

	public com.viettel.vtnet360.vt07.vt070000.entity.projecttree.PackageTree getPackageTree(long packageId);

	public com.viettel.vtnet360.vt07.vt070000.entity.projecttree.ContractTree getContractTree(long contractId);

	public int updateStatusPackageById(@Param("idList") String idList,@Param("isSynchrony") int isSynchrony);
	public int updateStatusContractById(@Param("idList") String idList,@Param("isSynchrony") int isSynchrony);
	public int updateStatusConstructionById(@Param("idList") String idList,@Param("isSynchrony") int isSynchrony);


}
