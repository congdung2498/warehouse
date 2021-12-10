package com.viettel.vtnet360.vt07.vt070000.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt07.vt070000.dao.VT070000DAO;
import com.viettel.vtnet360.vt07.vt070000.entity.RackDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.SlotDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxDetailsRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxSearchRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.DocumentDetails;
import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxSearchResult;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;

@Service
public class TinBoxSearchServiceImpl implements TinBoxSearchService {

	@Autowired
	VT070000DAO vt070000dao;
	@Autowired
	Properties headerTitle;

	private int getUnit(Collection<GrantedAuthority> userRoles) {
		for (GrantedAuthority temp : userRoles) {
			// if user had role VIEW ALL TINBOX
			if (Constant.PMQT_ROLE_WAREHOUSE_GROUP.equalsIgnoreCase(temp.getAuthority())) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public List<WarehouseDetail> getAllActiveWarehouse(Collection<GrantedAuthority> userRoles) {
		return getUnit(userRoles) == 1 ? vt070000dao.getAllActiveWarehouseGroup() : vt070000dao.getAllActiveWarehouse();
	}

	@Override
	public List<DocumentDetails> getDocumentsInBox(long tinboxId, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		List<DocumentDetails> result = new ArrayList<>();

		List<FolderDetail> folders = unit == 1 ? vt070000dao.getFolderInTinBoxGroup(tinboxId)
				: vt070000dao.getFolderInTinBox(tinboxId);

		for (FolderDetail f : folders) {
			result.addAll(
					unit == 1 ? vt070000dao.getAllDocumentInFolderV2Group(f) : vt070000dao.getAllDocumentInFolderV2(f));
		}

		for (DocumentDetails record : result) {
			addMoreInfo4Document(record, unit);
		}

		return result;
	}

	public DocumentDetails addMoreInfo4Document(DocumentDetails record, int unit) {
		int docId = record.getDocumentId();
		String type = record.getType();
		String projectName = "";

		String folderQrCode = unit == 1 ? vt070000dao.getFolderQrCodeGroup(record.getFolderId())
				: vt070000dao.getFolderQrCode(record.getFolderId());
		record.setFolderQrCode(folderQrCode);

		if ("project".equals(type)) {
			long projectId = unit == 1 ? vt070000dao.getProjectIdByDocGroup(docId)
					: vt070000dao.getProjectIdByDoc(docId);
			projectName = unit == 1 ? vt070000dao.getProjectNameGroup(projectId)
					: vt070000dao.getProjectName(projectId);
		} else if ("package".equals(type)) {
			long packageId = unit == 1 ? vt070000dao.getPackageIdByDocGroup(docId)
					: vt070000dao.getPackageIdByDoc(docId);
			projectName = unit == 1 ? vt070000dao.getProjectNameByPackageDocGroup(docId)
					: vt070000dao.getProjectNameByPackageDoc(docId);
			String packageName = unit == 1 ? vt070000dao.getPackageNameGroup(packageId)
					: vt070000dao.getPackageName(packageId);
			record.setPackageName(packageName);
		} else if ("contract".equals(type)) {
			long contractId = unit == 1 ? vt070000dao.getContractIdByDocGroup(docId)
					: vt070000dao.getContractIdByDoc(docId);
			projectName = unit == 1 ? vt070000dao.getProjectNameByContractDocV3Group(docId)
					: vt070000dao.getProjectNameByContractDocV3(docId);
			String contractName = unit == 1 ? vt070000dao.getContractNameGroup(contractId)
					: vt070000dao.getContractName(contractId);
			record.setContractName(contractName);
		} else if ("construction".equals(type)) {
			long constructionId = unit == 1 ? vt070000dao.getConstructionIdByDocGroup(docId)
					: vt070000dao.getConstructionIdByDoc(docId);
			projectName = unit == 1 ? vt070000dao.getProjectNameByConstructionDocV3Group(docId)
					: vt070000dao.getProjectNameByConstructionDocV3(docId);
			String constructionName = unit == 1 ? vt070000dao.getConstructionNameGroup(constructionId)
					: vt070000dao.getConstructionName(constructionId);
			String contractName = unit == 1 ? vt070000dao.getContractNameByConstructionIdGroup(constructionId)
					: vt070000dao.getContractNameByConstructionId(constructionId);
			record.setContractName(contractName);
			record.setConstructionName(constructionName);
		}
		record.setProjectName(projectName);
		return record;
	}

	@Override
	public List<TinBoxSearchResult> getAllTinBoxInWarehouse(int warehouseId, Collection<GrantedAuthority> userRoles) {
		List<TinBoxDetail> tinBoxList = getUnit(userRoles) == 1 ? vt070000dao.getAllTinBoxGroup(warehouseId)
				: vt070000dao.getAllTinBox(warehouseId);
		return addMoreInfo4TinBox(tinBoxList, warehouseId, getUnit(userRoles));
	}

	@Override
	public List<TinBoxSearchResult> searchTinBox(int warehouseId, String keyword, boolean byTinBox, boolean byFolder,
			boolean byProject, boolean byPackage, boolean byContract, boolean byConstruction,
			Collection<GrantedAuthority> userRoles) {

		int unit = getUnit(userRoles);
		List<TinBoxSearchResult> searchResult = new ArrayList<>();

		if ("".equals(keyword)) {
			return searchResult;
		}

		if (warehouseId < 0) {
			warehouseId = 0;
		}

		List<Long> tinBoxIdList = processSearch(keyword, byTinBox, byFolder, byProject, byPackage, byContract,
				byConstruction, unit);

		// try to search with vietnamese char đ
		if (keyword.toLowerCase().contains("d")) {
			String enhancedKeyword = keyword.replace("d", headerTitle.getProperty("DD"));
			tinBoxIdList.addAll(processSearch(enhancedKeyword, byTinBox, byFolder, byProject, byPackage, byContract,
					byConstruction, unit));
		}

		if (!tinBoxIdList.isEmpty()) {
			tinBoxIdList = removeDuplicate(tinBoxIdList);
			List<TinBoxDetail> tinBoxList = getUnit(userRoles) == 1
					? vt070000dao.getTinBoxGroup(tinBoxIdList, warehouseId)
					: vt070000dao.getTinBox(tinBoxIdList, warehouseId);
			searchResult = addMoreInfo4TinBox(tinBoxList, warehouseId, unit);
		}

		return searchResult;
	}

	private List<Long> processSearch(String keyword, boolean byTinBox, boolean byFolder, boolean byProject,
			boolean byPackage, boolean byContract, boolean byConstruction, int unit) {

		List<Long> tinBoxIdList = new ArrayList<>();

		// create search pattern for sql query
		String searchPattern = "%" + keyword + "%";

		// if no condition is selected, process search by all conditions
		if (!byTinBox && !byFolder && !byProject && !byPackage && !byContract && !byConstruction) {
			byTinBox = byFolder = byProject = byPackage = byContract = byConstruction = true;
		}

		if (byTinBox) {
			tinBoxIdList.addAll(searchByTinBox(searchPattern, unit));
		}

		if (byFolder) {
			tinBoxIdList.addAll(searchByFolder(searchPattern, unit));
		}

		if (byProject) {
			tinBoxIdList.addAll(searchByProject(searchPattern, unit));
		}

		if (byPackage) {
			tinBoxIdList.addAll(searchByPackage(searchPattern, unit));
		}

		if (byContract) {
			tinBoxIdList.addAll(searchByContract(searchPattern, unit));
		}

		if (byConstruction) {
			tinBoxIdList.addAll(searchByConstruction(searchPattern, unit));
		}

		return tinBoxIdList;
	}

	private List<Long> searchByTinBox(String searchPattern, int unit) {
		return unit == 1 ? vt070000dao.searchTinBoxByNameGroup(searchPattern)
				: vt070000dao.searchTinBoxByName(searchPattern);
	}

	private List<Long> searchByFolder(String searchPattern, int unit) {
		return unit == 1 ? vt070000dao.searchTinBoxByFolderNameGroup(searchPattern)
				: vt070000dao.searchTinBoxByFolderName(searchPattern);
	}

	private List<Long> searchByProject(String searchPattern, int unit) {
		List<Long> folderIdList = new ArrayList<>();

		List<Long> projectIdList = unit == 1 ? vt070000dao.searchProjectByNameGroup(searchPattern)
				: vt070000dao.searchProjectByName(searchPattern);

		if (!projectIdList.isEmpty()) {
			folderIdList = unit == 1 ? vt070000dao.searchFolderByProjectIdListGroup(projectIdList)
					: vt070000dao.searchFolderByProjectIdList(projectIdList);
		}

		folderIdList.addAll(unit == 1 ? vt070000dao.searchFolderByProjectDocGroup(searchPattern)
				: vt070000dao.searchFolderByProjectDoc(searchPattern));

		return folderToTinBox(folderIdList, unit);
	}

	private List<Long> searchByPackage(String searchPattern, int unit) {
		List<Long> folderIdList = new ArrayList<>();

		List<Long> packageIdList = unit == 1 ? vt070000dao.searchPackageByNameGroup(searchPattern)
				: vt070000dao.searchPackageByName(searchPattern);

		if (!packageIdList.isEmpty()) {
			folderIdList = unit == 1 ? vt070000dao.searchFolderByPackageIdListGroup(packageIdList)
					: vt070000dao.searchFolderByPackageIdList(packageIdList);
		}

		folderIdList.addAll(unit == 1 ? vt070000dao.searchFolderByPackageDocGroup(searchPattern)
				: vt070000dao.searchFolderByPackageDoc(searchPattern));

		return folderToTinBox(folderIdList, unit);
	}

	private List<Long> searchByContract(String searchPattern, int unit) {
		List<Long> folderIdList = new ArrayList<>();

		List<Long> contractIdList = unit == 1 ? vt070000dao.searchContractByNameGroup(searchPattern)
				: vt070000dao.searchContractByName(searchPattern);

		if (!contractIdList.isEmpty()) {
			folderIdList = unit == 1 ? vt070000dao.searchFolderByContractIdListGroup(contractIdList)
					: vt070000dao.searchFolderByContractIdList(contractIdList);
		}

		folderIdList.addAll(unit == 1 ? vt070000dao.searchFolderByContractDocGroup(searchPattern)
				: vt070000dao.searchFolderByContractDoc(searchPattern));

		return folderToTinBox(folderIdList, unit);
	}

	private List<Long> searchByConstruction(String searchPattern, int unit) {
		List<Long> folderIdList = new ArrayList<>();

		List<Long> constructionIdList = unit == 1 ? vt070000dao.searchConstructionByNameGroup(searchPattern)
				: vt070000dao.searchConstructionByName(searchPattern);

		if (!constructionIdList.isEmpty()) {
			folderIdList = unit == 1 ? vt070000dao.searchFolderByConstructionIdListGroup(constructionIdList)
					: vt070000dao.searchFolderByConstructionIdList(constructionIdList);
		}

		folderIdList.addAll(unit == 1 ? vt070000dao.searchFolderByConstructionDocGroup(searchPattern)
				: vt070000dao.searchFolderByConstructionDoc(searchPattern));

		return folderToTinBox(folderIdList, unit);
	}

	private List<Long> folderToTinBox(List<Long> folderIdList, int unit) {

		List<Long> tinBoxIdList = new ArrayList<>();

		if (!folderIdList.isEmpty()) {
			List<Long> folderIdset = removeDuplicate(folderIdList);
			tinBoxIdList = unit == 1 ? vt070000dao.searchTinBoxByFolderIdListGroup(folderIdset)
					: vt070000dao.searchTinBoxByFolderIdList(folderIdset);
		}

		return tinBoxIdList;
	}

	private List<TinBoxSearchResult> addMoreInfo4TinBox(List<TinBoxDetail> tinBoxList, int warehouseId, int unit) {

		List<TinBoxSearchResult> searchResult = new ArrayList<>();

		if (warehouseId > 0) {

			String warehouseName = unit == 1 ? vt070000dao.getWarehouseNameByIdGroup(warehouseId)
					: vt070000dao.getWarehouseNameById(warehouseId);

			if (warehouseName != null) {

				for (TinBoxDetail box : tinBoxList) {
					TinBoxSearchResult rawResult = new TinBoxSearchResult(warehouseName, box);
					TinBoxSearchResult moreDetailResult = labelTinBoxLocation(rawResult, unit);
					searchResult.add(moreDetailResult);
				}

			}
		} else {
			for (TinBoxDetail box : tinBoxList) {

				String warehouseName = unit == 1 ? vt070000dao.getWarehouseNameByIdGroup(box.getWarehouseId())
						: vt070000dao.getWarehouseNameById(box.getWarehouseId());

				if (warehouseName != null) {
					TinBoxSearchResult rawResult = new TinBoxSearchResult(warehouseName, box);
					TinBoxSearchResult moreDetailResult = labelTinBoxLocation(rawResult, unit);
					searchResult.add(moreDetailResult);
				}
			}
		}
		return searchResult;
	}

	private TinBoxSearchResult labelTinBoxLocation(TinBoxSearchResult rawResult, int unit) {
		TinBoxSearchResult result = rawResult;
		String location = "";
		long slotId = 0;
		long rackId = 0;
		int row = 0;
		int column = 0;
		int height = 0;

		slotId = result.getTinBox().getSlotId();
		SlotDetail slot = unit == 1 ? vt070000dao.getSlotByIdGroup(slotId) : vt070000dao.getSlotById(slotId);

		if (slot != null) {
			height = slot.getHeight();
		}

		if (slot != null) {
			result.setSlotQrCode(slot.getQrCode());

			rackId = unit == 1 ? vt070000dao.getRackIdBySlotIdGroup(slotId) : vt070000dao.getRackIdBySlotId(slotId);
			RackDetail rack = unit == 1 ? vt070000dao.getRackByIdGroup(rackId) : vt070000dao.getRackById(rackId);

			if (rack != null) {
				row = rack.getRow();
				column = rack.getColumn();
			}
		}

		location = String.valueOf(row) + "/" + column + "/" + height;
		result.setTinBoxLocation(location);
		return result;
	}

	private List<Long> removeDuplicate(List<Long> list) {
		Set<Long> uniqueSet = new HashSet<>(list);
		return new ArrayList<>(uniqueSet);
	}

	@Override
	public List<TinBoxDetail> searchTinBox(TinBoxSearchRequest req, Collection<GrantedAuthority> userRoles) {
		return getUnit(userRoles) == 1 ? vt070000dao.searchTinBoxGroup(req) : vt070000dao.searchTinBox(req);
	}

	@Override
	public List<DocumentDetails> getDocumentsInBoxV2(TinBoxDetailsRequest tinBoxRq,
			Collection<GrantedAuthority> userRoles) {
		String role = StringUtils.EMPTY;
		if (checkRoleExits("WAREHOUSE_VT", userRoles)) {
			role = "WAREHOUSE_VT";
		}
		if (checkRoleExits("WAREHOUSE_TC", userRoles)) {
			role = "WAREHOUSE_TC";
		}
		if (checkRoleExits("WAREHOUSE_DA", userRoles)) {
			role = "WAREHOUSE_DA";
		}
		if (checkRoleExits("PMQT_ADMIN", userRoles)) {
			role = "PMQT_ADMIN";
		}
		tinBoxRq.setRole(role);
		return getUnit(userRoles) == 1 ? vt070000dao.getDocumentInTinBoxGroup(tinBoxRq.getId())
				: vt070000dao.getDocumentInTinBox(tinBoxRq);
	}

	private boolean checkRoleExits(String role, Collection<GrantedAuthority> userRoles) {
		return userRoles.stream().anyMatch(r -> r.getAuthority().equals(role));
	}

}
