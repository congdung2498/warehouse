package com.viettel.vtnet360.vt07.vt070002.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ConstructionDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ConstructionDocDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ContractDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ContractDocDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.DocumentReportRecord;
import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.PackageDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.PackageDocDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ProjectDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ProjectDocDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ProjectInFoderDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.RequestParam;
import com.viettel.vtnet360.vt07.vt070002.entity.SearchDocumentResponse;
import com.viettel.vtnet360.vt07.vt070002.entity.SearchFolderResponse;
import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;


@Repository
public interface VT070002DAO {
	
	List<TinBoxDetail> searchTinBox(RequestParam requestParam);
	List<TinBoxDetail> getListTinBox(RequestParam requestParam);
	List<TinBoxDetail> getListTinBoxByQrCode(RequestParam requestParam);
	int getNumberOfTinBox(RequestParam requestParam);
	List<FolderDetail> getListFolderByTinBoxId(RequestParam requestParam);
	int getNumberOfFolderByTinBoxId(RequestParam requestParam);
	List<ProjectDetail> getListProjectByFolderId(RequestParam requestParam);
	int getNumberOfProjectByFolderId(RequestParam requestParam);
	public int insertTinBox(TinBoxDetail tinBoxDetail);
	public int insertFolder(FolderDetail folder);	
	public int updateProjectDocDetail(ProjectDocDetail projectDocDetail);	
	public int updateContractDocDetail(ContractDocDetail contractDocDetail);
	public int updateConstructionDocDetail(ConstructionDocDetail constructionDocDetail);
	public int updatePackageDocDetail(PackageDocDetail packageDocDetail);
	public int insertProjectInFoderDetail(ProjectInFoderDetail projectInFoderDetail);
	
	
	public int updateTinBox(TinBoxDetail tinBoxDetail);
	public int updateFolder(FolderDetail folder);
	List<ProjectDetail> checkProjectInFolder(RequestParam folderId);
	public int removeProjectInFoderDetail(ProjectInFoderDetail projectInFoderDetail);
	
	List<ProjectDetail> getProjects(RequestParam requestParam);
	List<PackageDetail> getPackages(RequestParam requestParam);
	List<ContractDetail> getContracts(RequestParam requestParam);
	List<ConstructionDetail> getConstructions(RequestParam requestParam);
	
	public int updateProjectOfContract(ContractDetail contract);
	
	
	List<ProjectDocDetail> getAvailProjectDocs(long projectId);
	List<PackageDocDetail> getAvailPackageDocs(long packageId);
	List<ContractDocDetail> getAvailContractDocs(long contractId);
	List<ConstructionDocDetail> getAvailConstructionDocs(long constructionId);
	
	List<ProjectDetail> getProjectById(RequestParam requestParam);
	
	List<ProjectDocDetail> getProjectDocInFolder(RequestParam requestParam);//
	List<PackageDetail>  getProjectPackagesInFolder(RequestParam requestParam);//
	List<PackageDocDetail> getPackageDocInFolder(RequestParam packageDocRequestParam);//
	List<ContractDetail> getPackageContractsInFolder(RequestParam requestParam);//
	List<ContractDocDetail> getContractDocInFolder(RequestParam contractDocRequestParam);
	List<ConstructionDetail> getProjectConstructionsInFolder(RequestParam contractRequestParam);
	List<ConstructionDocDetail> getConstructionDocInFolder(RequestParam constructionDocRequestParam);
	
	public int removeDocsProjectInFolder(RequestParam updateParam);
	public int removeDocsPackageInFolder(RequestParam updateParam);
	public int removeDocsContractInFolder(RequestParam updateParam);
	public int removeDocsConstructionInFolder(RequestParam updateParam);
	
	List<DocumentReportRecord> getAllProjectDocumentWithWarehouseId(RequestParam requestParam);
	List<DocumentReportRecord> getProjectDocumentWithWarehouseId(RequestParam requestParam);
	int countProjectDocumentWithWarehouseId(RequestParam requestParam);
	int countProjectTinboxWithWarehouseId(RequestParam requestParam);
	int countProjectFolderWithWarehouseId(RequestParam requestParam);
	List<TinBoxDetail> searchListTinBox(TinBoxDetail object);
	List<TinBoxDetail> getTinBoxInType1Warehouse(TinBoxDetail object);
	List<WarehouseDetail> findTypeWarehouseDetail(WarehouseDetail object);
	List<WarehouseDetail> getType1Warehouses(WarehouseDetail object);
	public int updateTinboxByInformation(TinBoxDetail object);
	public int getTotalRecordTinBox(TinBoxDetail object);
	TinBoxDetail getTinBoxById(long tinBoxId);
	FolderDetail getFolderById(long folderId);
	
	ContractDetail getRelationProjectContract(ContractDetail contract);
	int insertRelationProjectContract(ContractDetail contract);
	
	ConstructionDetail getRelationProjectConstruction(ConstructionDetail construction);
	int insertRelationProjectConstruction(ConstructionDetail construction);
	
	ConstructionDetail getConstructionInFolder(ConstructionDetail construction);
	int remoceRelationProjectConstruction(ConstructionDetail construction);
	
	Long getContractIdFromDocId(long contractDocId);	
	Long getConstructionIdFromDocId(long constructionDocId);
	Long getContractIdFromConstructionId(long constructionId);

	WarehouseDetail countThingsInWarehouse(int warehouseId);
	List<DocumentReportRecord> getDocumentInWarehouse(@Param("warehouseId") int warehouseId, @Param("pageSize") int pageSize, @Param("offset") int pageNumber);
	List<SearchFolderResponse> searchFolderByNameOrQrCode(@Param("keyword") String keyword, @Param("offset") int offset, @Param("pageSize") int pageSize, @Param("unit") int unit);
	int searchFolderByNameOrQrCodeTotalRecord(@Param("keyword") String keyword, @Param("unit") int unit);
	List<ProjectDetail> getProjectsInFolder(long folderId);
	List<ProjectDetail> getProjectsNotInFolder(@Param("folderId") long folderId, @Param("unit") int unit);
	
	int countTotalTinBox(int warehouseId);
	int countOccupiedSlot(int warehouseId);
	
	List<FolderDetail> getListFolderByQrCode(RequestParam requestParam);
	List<PackageDetail>  getProjectPackagesInFolderSuper(RequestParam requestParam);//
	List<ContractDetail> getProjectContractsInFolderSuper(RequestParam requestParam);//
	List<ConstructionDetail> getProjectConstructionsInFolderSuper(RequestParam contractRequestParam);
	List<DocumentReportRecord> searchDocument(@Param("keyword") String keyword, @Param("pageSize") int pageSize, @Param("offset") int pageNumber, @Param("unit") int unit);
	
	List<ProjectDetail> getProjectsInTinBox(@Param("tinBoxId") long tinBoxId, @Param("unit") int unit);
	List<SearchDocumentResponse> searchDocumentsByKeyword(@Param("keyword") String keyword, @Param("unit") int unit, @Param("pageSize") int pageSize, @Param("offset") int offset);
	
	// Group region
	
	List<TinBoxDetail> searchTinBoxGroup(RequestParam requestParam);
	List<TinBoxDetail> getListTinBoxGroup(RequestParam requestParam);
	List<TinBoxDetail> getListTinBoxByQrCodeGroup(RequestParam requestParam);
	int getNumberOfTinBoxGroup(RequestParam requestParam);
	List<FolderDetail> getListFolderByTinBoxIdGroup(RequestParam requestParam);
	int getNumberOfFolderByTinBoxIdGroup(RequestParam requestParam);
	List<ProjectDetail> getListProjectByFolderIdGroup(RequestParam requestParam);
	int getNumberOfProjectByFolderIdGroup(RequestParam requestParam);
	public int insertTinBoxGroup(TinBoxDetail tinBoxDetail);
	public int insertFolderGroup(FolderDetail folder);	
	public int updateProjectDocDetailGroup(ProjectDocDetail projectDocDetail);	
	public int updateContractDocDetailGroup(ContractDocDetail contractDocDetail);
	public int updateConstructionDocDetailGroup(ConstructionDocDetail constructionDocDetail);
	public int updatePackageDocDetailGroup(PackageDocDetail packageDocDetail);
	public int insertProjectInFoderDetailGroup(ProjectInFoderDetail projectInFoderDetail);
	public int updateTinBoxGroup(TinBoxDetail tinBoxDetail);
	public int updateFolderGroup(FolderDetail folder);
	List<ProjectDetail> checkProjectInFolderGroup(RequestParam folderId);
	public int removeProjectInFoderDetailGroup(ProjectInFoderDetail projectInFoderDetail);	
	List<ProjectDetail> getProjectsGroup(RequestParam requestParam);
	List<PackageDetail> getPackagesGroup(RequestParam requestParam);
	List<ContractDetail> getContractsGroup(RequestParam requestParam);
	List<ConstructionDetail> getConstructionsGroup(RequestParam requestParam);	
	public int updateProjectOfContractGroup(ContractDetail contract);
	List<ProjectDocDetail> getAvailProjectDocsGroup(long projectId);
	List<PackageDocDetail> getAvailPackageDocsGroup(long packageId);
	List<ContractDocDetail> getAvailContractDocsGroup(long contractId);
	List<ConstructionDocDetail> getAvailConstructionDocsGroup(long constructionId);	
	List<ProjectDetail> getProjectByIdGroup(RequestParam requestParam);	
	List<ProjectDocDetail> getProjectDocInFolderGroup(RequestParam requestParam);//
	List<PackageDetail>  getProjectPackagesInFolderGroup(RequestParam requestParam);//
	List<PackageDocDetail> getPackageDocInFolderGroup(RequestParam packageDocRequestParam);//
	List<ContractDetail> getPackageContractsInFolderGroup(RequestParam requestParam);//
	List<ContractDocDetail> getContractDocInFolderGroup(RequestParam contractDocRequestParam);
	List<ConstructionDetail> getProjectConstructionsInFolderGroup(RequestParam contractRequestParam);
	List<ConstructionDocDetail> getConstructionDocInFolderGroup(RequestParam constructionDocRequestParam);	
	public int removeDocsProjectInFolderGroup(RequestParam updateParam);
	public int removeDocsPackageInFolderGroup(RequestParam updateParam);
	public int removeDocsContractInFolderGroup(RequestParam updateParam);
	public int removeDocsConstructionInFolderGroup(RequestParam updateParam);	
	List<DocumentReportRecord> getAllProjectDocumentWithWarehouseIdGroup(RequestParam requestParam);
	List<DocumentReportRecord> getProjectDocumentWithWarehouseIdGroup(RequestParam requestParam);
	int countProjectDocumentWithWarehouseIdGroup(RequestParam requestParam);
	int countProjectTinboxWithWarehouseIdGroup(RequestParam requestParam);
	int countProjectFolderWithWarehouseIdGroup(RequestParam requestParam);
	List<TinBoxDetail> searchListTinBoxGroup(TinBoxDetail object);
	List<TinBoxDetail> getTinBoxInType1WarehouseGroup(TinBoxDetail object);
	List<WarehouseDetail> findTypeWarehouseDetailGroup(WarehouseDetail object);
	List<WarehouseDetail> getType1WarehousesGroup(WarehouseDetail object);
	public int updateTinboxByInformationGroup(TinBoxDetail object);
	public int getTotalRecordTinBoxGroup(TinBoxDetail object);
	TinBoxDetail getTinBoxByIdGroup(long tinBoxId);
	FolderDetail getFolderByIdGroup(long folderId);	
	ContractDetail getRelationProjectContractGroup(ContractDetail contract);
	int insertRelationProjectContractGroup(ContractDetail contract);	
	ConstructionDetail getRelationProjectConstructionGroup(ConstructionDetail construction);
	int insertRelationProjectConstructionGroup(ConstructionDetail construction);	
	ConstructionDetail getConstructionInFolderGroup(ConstructionDetail construction);
	int remoceRelationProjectConstructionGroup(ConstructionDetail construction);	
	Long getContractIdFromDocIdGroup(long contractDocId);	
	Long getConstructionIdFromDocIdGroup(long constructionDocId);
	Long getContractIdFromConstructionIdGroup(long constructionId);
	WarehouseDetail countThingsInWarehouseGroup(int warehouseId);
	List<DocumentReportRecord> getDocumentInWarehouseGroup(@Param("warehouseId") int warehouseId, @Param("pageSize") int pageSize, @Param("offset") int pageNumber);
	List<SearchFolderResponse> searchFolderByNameOrQrCodeGroup(@Param("keyword") String keyword, @Param("offset") int offset, @Param("pageSize") int pageSize, @Param("unit") int unit);
	int searchFolderByNameOrQrCodeTotalRecordGroup(@Param("keyword") String keyword, @Param("unit") int unit);
	List<ProjectDetail> getProjectsInFolderGroup(long folderId);
	List<ProjectDetail> getProjectsNotInFolderGroup(@Param("folderId") long folderId, @Param("unit") int unit);
	int countTotalTinBoxGroup(int warehouseId);
	int countOccupiedSlotGroup(int warehouseId);	
	List<FolderDetail> getListFolderByQrCodeGroup(RequestParam requestParam);
	List<PackageDetail>  getProjectPackagesInFolderSuperGroup(RequestParam requestParam);//
	List<ContractDetail> getProjectContractsInFolderSuperGroup(RequestParam requestParam);//
	List<ConstructionDetail> getProjectConstructionsInFolderSuperGroup(RequestParam contractRequestParam);
	List<DocumentReportRecord> searchDocumentGroup(@Param("keyword") String keyword, @Param("pageSize") int pageSize, @Param("offset") int pageNumber, @Param("unit") int unit);	
	List<ProjectDetail> getProjectsInTinBoxGroup(@Param("tinBoxId") long tinBoxId, @Param("unit") int unit);
	List<SearchDocumentResponse> searchDocumentsByKeywordGroup(@Param("keyword") String keyword, @Param("unit") int unit, @Param("pageSize") int pageSize, @Param("offset") int offset);
}
