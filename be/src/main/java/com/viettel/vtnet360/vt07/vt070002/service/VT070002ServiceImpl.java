package com.viettel.vtnet360.vt07.vt070002.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070000.dao.VT070000DAO;
import com.viettel.vtnet360.vt07.vt070000.entity.RackDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.SlotDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseRequestParam;
import com.viettel.vtnet360.vt07.vt070000.service.TinBoxSearchService;
import com.viettel.vtnet360.vt07.vt070001.dao.VT070001DAO;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeDetail;
import com.viettel.vtnet360.vt07.vt070002.dao.VT070002DAO;
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
import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;
import com.viettel.vtnet360.vt07.vt070005.dao.VT070005DAO;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT070002ServiceImpl implements VT070002Service {

	@Autowired
	VT070002DAO vt070002dao;
	@Autowired
	VT070000DAO vt070000dao;
	@Autowired
	VT070001DAO vt070001dao;
	@Autowired
	VT070005DAO vt070005dao;
	@Autowired
	Properties linkTemplateExcel;
	@Autowired
	Properties headerTitle;

	@Autowired
	TinBoxSearchService tinBoxSearchService;
	
	@Autowired
	Properties warehouseMessage;

	private final Logger logger = Logger.getLogger(this.getClass());
	
	private int[] typeSearch = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

	private void validateTypeSearch(RequestParam requestParam) {

		boolean contains = IntStream.of(typeSearch).anyMatch(x -> x == requestParam.getTypeSearch());

		if (!contains) {
			requestParam.setTypeSearch(0);
		}
	}

	private void addTinboxRoles(List<TinBoxDetail> tinBoxes, String userName, Collection<GrantedAuthority> userRoles) {
		tinBoxes.forEach(box -> {
			if(box.getCreateUser().equals(userName))
			{
				box.getRoles().add("edit");
				box.getRoles().add("delete");
			}
			else {
				for (GrantedAuthority temp : userRoles) {
			        // if user had role VIEW ALL TINBOX
			        if (Constant.PMQT_ROLE_TINBOX_UPDATE.equalsIgnoreCase(temp.getAuthority())) {
			        	box.getRoles().add("edit");
			        }
			        if (Constant.PMQT_ROLE_TINBOX_DELETE.equalsIgnoreCase(temp.getAuthority())) {
			        	box.getRoles().add("delete");
			        }
				}
			}
		});
		
	}
	
	private void setViewRight(RequestParam requestParam, String userName, Collection<GrantedAuthority> userRoles)
	{
		requestParam.setQueryUser(userName);
		requestParam.setViewAll(false);
		for (GrantedAuthority temp : userRoles) {
	        // if user had role VIEW ALL TINBOX
	        if (Constant.PMQT_ROLE_TINBOX_VIEW_ALL.equalsIgnoreCase(temp.getAuthority())) {
	        	requestParam.setViewAll(true);
	        } else if (Constant.PMQT_ROLE_ADMIN.equalsIgnoreCase(temp.getAuthority())) {
	        	requestParam.setViewAll(true);
	        }
		}
	}

	@Override
	public Object getListTinBox(RequestParam requestParam, String userName, Collection<GrantedAuthority> userRoles) {
        if(!StringUtils.isEmpty(requestParam.getName())) {
        	requestParam.setName(replaceLikeStmtParam(requestParam.getName()));
        }
		requestParam.setPageNo(requestParam.getPageNo() * 20);
//		if(requestParam.getName() != null && !"".equals(requestParam.getName())) {
//			requestParam.setName(requestParam.getName().replace(" ", "%"));
//		}

		List<TinBoxDetail> results;
		setViewRight(requestParam, userName, userRoles);
		int unit = getUnit(userRoles);
		requestParam.setUnit(unit);
		if(requestParam.getQrCode() != null && !requestParam.getQrCode().isEmpty())
		{
			results = unit == 1 ? vt070002dao.getListTinBoxByQrCodeGroup(requestParam) : vt070002dao.getListTinBoxByQrCode(requestParam);
			addTinboxRoles(results, userName, userRoles);
			return new Object() {
				@SuppressWarnings("unused")
				public List<TinBoxDetail> tinBoxs = results != null ? results : new ArrayList<>();
				@SuppressWarnings("unused")
				public int total = results.size();
			};
		}
		else
		{
			if(requestParam.getTinBoxId() == 0)
			{
				validateTypeSearch(requestParam);
				results = unit == 1 ? vt070002dao.searchTinBoxGroup(requestParam) : vt070002dao.searchTinBox(requestParam);
			}
			else
			{
				results = unit == 1 ? vt070002dao.getListTinBoxGroup(requestParam) : vt070002dao.getListTinBox(requestParam);
				if(requestParam.getTinBoxId() > 0 && results != null)
				{
					results.forEach(tinBox -> {
						// List<FolderDetail> folders = vt070002dao.getListFolderByTinBoxId(requestParam);
						// tinBox.setFolders(folders);
						// tinBox.setTotalFolder(vt070002dao.getNumberOfFolderByTinBoxId(requestParam));

						if(requestParam.getFolderId() > 0)
						{
							int pageNo = requestParam.getPageNo();
							requestParam.setPageNo(0);
							List<FolderDetail> folders = unit == 1 ? vt070002dao.getListFolderByTinBoxIdGroup(requestParam) : vt070002dao.getListFolderByTinBoxId(requestParam);
							tinBox.setFolders(folders);
							tinBox.setTotalFolder(unit == 1 ? vt070002dao.getNumberOfFolderByTinBoxIdGroup(requestParam) : vt070002dao.getNumberOfFolderByTinBoxId(requestParam));
							requestParam.setPageNo(pageNo);
							folders.forEach(folder -> {
								List<ProjectDetail> projects = unit == 1 ? vt070002dao.getListProjectByFolderIdGroup(requestParam) : vt070002dao.getListProjectByFolderId(requestParam);
								projects.stream().forEach(p->p.setType(3));
								folder.setProjects(projects);
								folder.setTotalProject(unit == 1? vt070002dao.getNumberOfProjectByFolderIdGroup(requestParam) : vt070002dao.getNumberOfProjectByFolderId(requestParam));
						
								FolderDetail folderDetail = vt070005dao.getVoucherInFolderById(folder.getFolderId());
								folder.setPaymmentSummarys(folderDetail.getPaymmentSummarys());
								folder.setVouchers(folderDetail.getVouchers());
							});
						}
						else {
							List<FolderDetail> folders = unit == 1 ? vt070002dao.getListFolderByTinBoxIdGroup(requestParam) : vt070002dao.getListFolderByTinBoxId(requestParam);
							tinBox.setFolders(folders);
							tinBox.setTotalFolder(unit == 1 ? vt070002dao.getNumberOfFolderByTinBoxIdGroup(requestParam) : vt070002dao.getNumberOfFolderByTinBoxId(requestParam));
						}

					});
				}	
			}
			if(results != null)
				addTinboxRoles(results, userName, userRoles);
			if (results != null) {
				setTypeByTinBox(results, requestParam.getTypeSearch());
			}
		}
		return new Object() {
			@SuppressWarnings("unused")
			public List<TinBoxDetail> tinBoxs = results != null ? results : new ArrayList<>();
			@SuppressWarnings("unused")
			public int total = unit == 1 ? vt070002dao.getNumberOfTinBoxGroup(requestParam) : vt070002dao.getNumberOfTinBox(requestParam);
		};
	}

	private void setTypeByTinBox(List<TinBoxDetail> tinBoxList, int typeSearch) {
		for (TinBoxDetail tinBoxDetail : tinBoxList) {
			tinBoxDetail.setType(String.valueOf(typeSearch));
		}
	}

	@Override
	public ResponseEntityBase searchListTinBox(TinBoxDetail object, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<TinBoxDetail> data = new ArrayList<>();
		try {
			String checkXss =  object.getName();
			if(checkXss!=null) {
				checkXss = validateXss(checkXss);
				object.setName(checkXss);
			}
			checkXss =  object.getMngUser();
			if(checkXss!=null) {
				checkXss = validateXss(checkXss);
				object.setMngUser(checkXss);
			}
			data = unit == 1 ? vt070002dao.searchListTinBoxGroup(object) : vt070002dao.searchListTinBox(object);

			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			logger.info(e.getMessage());
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
		}
		return response;
	}

	private void saveRelationProjectContract(ContractDetail contract, int unit) {
		ContractDetail relationPC = unit == 1 ? vt070002dao.getRelationProjectContractGroup(contract)
				: vt070002dao.getRelationProjectContract(contract);		
		if(relationPC == null && contract.getContractId() != 0)
		{
			if(unit == 1) {
				vt070002dao.insertRelationProjectContractGroup(contract);
			}
			else {
				vt070002dao.insertRelationProjectContract(contract);
			}
		}
	}

	private void saveRelationProjectConstruction(ConstructionDetail construction, int unit) {
		ConstructionDetail relationPCst = unit == 1 ? vt070002dao.getRelationProjectConstructionGroup(construction)
				: vt070002dao.getRelationProjectConstruction(construction);
		if(relationPCst == null && construction.getConstructionId() != 0)
		{
			if(unit == 1)
			{
				vt070002dao.insertRelationProjectConstructionGroup(construction);
			}
			else {
				vt070002dao.insertRelationProjectConstruction(construction);
			}
		}
	}

	private void removeRelationProjectConstruction(ConstructionDetail construction, int unit) {
		ConstructionDetail constructionInFolder = unit == 1 ? vt070002dao.getConstructionInFolderGroup(construction) 
				: vt070002dao.getConstructionInFolder(construction);
		if(constructionInFolder == null)
		{
			if(unit ==1) {
				vt070002dao.remoceRelationProjectConstructionGroup(construction);
			}
			else {
				vt070002dao.remoceRelationProjectConstruction(construction);
			}			
		}
	}

	private long getContractIdFromDocId(long contractDocId, int unit) {
		Long result = unit == 1 ? vt070002dao.getContractIdFromDocIdGroup(contractDocId)
				: vt070002dao.getContractIdFromDocId(contractDocId);
		return result == null ? 0 : result;
	}

	private long getConstructionIdFromDocId(long constructionDocId, int unit) {
		Long result = unit == 1? vt070002dao.getConstructionIdFromDocIdGroup(constructionDocId)
				: vt070002dao.getConstructionIdFromDocId(constructionDocId);
		return result == null ? 0 : result;
	}

	private long getContractIdFromConstructionId(long constructionId, int unit) {
		Long result = unit == 1? vt070002dao.getContractIdFromConstructionIdGroup(constructionId)
				: vt070002dao.getContractIdFromConstructionId(constructionId);
		return result == null ? 0 : result;
	}

	private TinBoxDetail checkAvailNewSlot(TinBoxDetail tinBoxDetail, int unit) {
		SlotDetail slot = unit ==1? vt070000dao.getSlotByIdGroup(tinBoxDetail.getSlotId()) 
				: vt070000dao.getSlotById(tinBoxDetail.getSlotId());
		if(slot != null && slot.getStatus() == 1)
		{
			tinBoxDetail.setError(MessageFormat.format(warehouseMessage.getProperty("0700002"), slot.getQrCode()));
			//tinBoxDetail.setError("0700002#Slot " + slot.getQrCode() + " was used");
			return tinBoxDetail;
		}
		else if (slot != null && slot.getStatus() == 0){
			// Check slot in warehouse
			RackDetail rackDetail = unit == 1? vt070000dao.getRackByIdGroup(slot.getRackId())
					: vt070000dao.getRackById(slot.getRackId());
			if(rackDetail == null)
			{
				tinBoxDetail.setError(MessageFormat.format(warehouseMessage.getProperty("0700004"), slot.getQrCode()));
				//tinBoxDetail.setError("0700004#Slot " + slot.getQrCode() + " was not correct");
				return tinBoxDetail;
			}
			if(rackDetail.getWarehouseId() != tinBoxDetail.getWarehouseId())
			{
				tinBoxDetail.setError(MessageFormat.format(warehouseMessage.getProperty("0700005"), slot.getQrCode()));
				//tinBoxDetail.setError("0700005#Slot " + slot.getQrCode() + " was not in warehouse");
				return tinBoxDetail;
			}
			tinBoxDetail.setWarehouseId(rackDetail.getWarehouseId());
			// Update new slot
			slot.setStatus(1);
			if(unit == 1) {
				vt070000dao.updateSlotDetailGroup(slot);
			}
			else {
				vt070000dao.updateSlotDetail(slot);
			}
			
		}
		else {
			tinBoxDetail.setError(MessageFormat.format(warehouseMessage.getProperty("0700003"), tinBoxDetail.getSlotQrCode()));
			//tinBoxDetail.setError("0700003#Slot " + tinBoxDetail.getSlotQrCode() + " was not exist");
			return tinBoxDetail;
		}
		return null;
	}
	
	private void updateProjectInFolder(FolderDetail folder, long folderId, String userName, int unit) {
		List<ProjectDetail> projects = folder.getProjects();
		if(projects != null && folder.getAction() != Constant.DEL_ACTION) {
			
			projects.forEach(project -> {
				if(project.getDocs() != null) {
					project.getDocs().forEach(doc -> {
						if(doc.getAction() == Constant.ADD_ACTION) {
							doc.setFolderId(folderId);
							if(unit == 1) {
								vt070002dao.updateProjectDocDetailGroup(doc);
							}
							else {
								vt070002dao.updateProjectDocDetail(doc);
							}		
						}
						if(doc.getAction() == Constant.REMOVE_ACTION) {
							doc.setFolderId(0);
							if(unit == 1) {
								vt070002dao.updateProjectDocDetailGroup(doc);
							}
							else {
								vt070002dao.updateProjectDocDetail(doc);
							}
						}						
					});
				}

				// Add contract
				if(project.getContracts() != null) {
					project.getContracts().forEach(contract -> {
						// Add doc of contract
						if(contract.getDocs() != null) {
							contract.getDocs().forEach(doc -> {
								contract.setContractId(getContractIdFromDocId(doc.getContractDocId(), unit));
								if(doc.getAction() == Constant.ADD_ACTION) {
									doc.setFolderId(folderId);
									if(unit == 1) {
										vt070002dao.updateContractDocDetailGroup(doc);
									}
									else {
										vt070002dao.updateContractDocDetail(doc);
									}
								}
								if(doc.getAction() == Constant.REMOVE_ACTION) {
									doc.setFolderId(0);
									if(unit == 1) {
										vt070002dao.updateContractDocDetailGroup(doc);
									}
									else {
										vt070002dao.updateContractDocDetail(doc);
									}
								}										
							});
						}

						// Add construction
						if(contract.getConstructions() != null) {
							contract.getConstructions().forEach(construction -> {
								// Add doc of construction
								if(construction.getDocs() != null) {
									construction.getDocs().forEach(doc -> {
										if(doc.getAction() == Constant.ADD_ACTION) {
											doc.setFolderId(folderId);
											if(unit == 1) {
												vt070002dao.updateConstructionDocDetailGroup(doc);
											}
											else {
												vt070002dao.updateConstructionDocDetail(doc);
											}										
											// Add relation project - construction
											long constructionId = getConstructionIdFromDocId(doc.getConstructionDocId(), unit);
											contract.setContractId(getContractIdFromConstructionId(constructionId, unit));
											construction.setConstructionId(constructionId);
											construction.setProjectId(project.getProjectId());
											construction.setUpdateUser(userName);
											saveRelationProjectConstruction(construction, unit);													
										}
										if(doc.getAction() == Constant.REMOVE_ACTION) {
											doc.setFolderId(0);
											if(unit == 1) {
												vt070002dao.updateConstructionDocDetailGroup(doc);
											}
											else {
												vt070002dao.updateConstructionDocDetail(doc);
											}
											// Remove relation project - construction
											long constructionId = getConstructionIdFromDocId(doc.getConstructionDocId(), unit);
											construction.setConstructionId(constructionId);
											construction.setProjectId(project.getProjectId());
											construction.setFolderId(folderId);
											construction.setUpdateUser(userName);
											removeRelationProjectConstruction(construction, unit);
										}
									});
								}		
							});
						}

						// Update contract -> no need
						contract.setProjectId(project.getProjectId());
						contract.setUpdateUser(userName);
						saveRelationProjectContract(contract, unit);
					});
				}

				// Add package
				if(project.getPackages() != null) {
					project.getPackages().forEach(packagz -> {
						// Add doc of package
						if(packagz.getDocs() != null) {
							packagz.getDocs().forEach(doc -> {
								if(doc.getAction() == Constant.ADD_ACTION) {
									doc.setFolderId(folderId);
									if(unit == 1) {
										vt070002dao.updatePackageDocDetailGroup(doc);
									}
									else {
										vt070002dao.updatePackageDocDetail(doc);
									}
								}
								if(doc.getAction() == Constant.REMOVE_ACTION) {
									doc.setFolderId(0);
									if(unit == 1) {
										vt070002dao.updatePackageDocDetailGroup(doc);
									}
									else {
										vt070002dao.updatePackageDocDetail(doc);
									}
								}
							});
						}
					});
				}
				
				//How to check have any doc of project in folder

				RequestParam requestParam = new RequestParam();
				requestParam.setProjectId(project.getProjectId());
				requestParam.setFolderId(folderId);
				List<ProjectDetail> pInF = unit == 1 ? vt070002dao.checkProjectInFolderGroup(requestParam) 
						: vt070002dao.checkProjectInFolder(requestParam);
				int numPInF = unit == 1 ? vt070002dao.getNumberOfProjectByFolderIdGroup(requestParam)
						: vt070002dao.getNumberOfProjectByFolderId(requestParam);

				// Vua them moi
				if(pInF != null && !pInF.isEmpty() && numPInF == 0) {
					ProjectInFoderDetail pifDetail = new ProjectInFoderDetail();
					pifDetail.setFolderId(folderId);
					pifDetail.setProjectId(project.getProjectId());
					pifDetail.setDelFlag(0);
					pifDetail.setCreateUser(userName);
					if(unit == 1) {
						vt070002dao.insertProjectInFoderDetailGroup(pifDetail);
					}
					else {
						vt070002dao.insertProjectInFoderDetail(pifDetail);
					}
				}
				// Bo het
				if(((pInF == null) || (pInF != null && pInF.isEmpty())) && numPInF > 0) {
					ProjectInFoderDetail pifDetail = new ProjectInFoderDetail();
					pifDetail.setFolderId(folderId);
					pifDetail.setProjectId(project.getProjectId());
					pifDetail.setDelFlag(1);
					pifDetail.setUpdateUser(userName);
					if(unit == 1) {
						vt070002dao.removeProjectInFoderDetailGroup(pifDetail);
					}
					else {
						vt070002dao.removeProjectInFoderDetail(pifDetail);
					}
				}
			});

		}
	}
	@Override
	@Transactional
	public Object createTinBox(TinBoxDetail tinBoxDetail, String userName, Collection<GrantedAuthority> userRoles) {
		// Validate tinbox barcode
		// Validate position barcode
		int unit = getUnit(userRoles);
		VT070001EntityBarcodeDetail barCodeDetail = new VT070001EntityBarcodeDetail();
		barCodeDetail.setCode(tinBoxDetail.getQrCode());
		List<VT070001EntityBarcodeDetail> barCodes = unit == 1 ? vt070001dao.checkBarcodeIsAvailGroup(barCodeDetail)
				: vt070001dao.checkBarcodeIsAvail(barCodeDetail);
		if(barCodes == null || barCodes.size() != 1)
		{
			tinBoxDetail.setError(MessageFormat.format(warehouseMessage.getProperty("0700001"), tinBoxDetail.getQrCode()));
			//tinBoxDetail.setError("0700001#QR Code " + tinBoxDetail.getQrCode() + " was used or not exist");
			return tinBoxDetail;
		}

		tinBoxDetail.setCreateUser(userName);
		tinBoxDetail.setUnit(unit);
		//Check warehouseType
		WarehouseRequestParam warehouse = unit == 1? vt070000dao.getWarehouseDetailByIdGroup(tinBoxDetail.getWarehouseId() + "")
				: vt070000dao.getWarehouseDetailById(tinBoxDetail.getWarehouseId() + "");
		if(warehouse != null && "0".equals(warehouse.getType())) {
			tinBoxDetail.setSlotQrCode(null);
			tinBoxDetail.setSlotId(0);
			tinBoxDetail.setWarehouseName(warehouse.getName());
		}
		else if(warehouse != null && "1".equals(warehouse.getType()))
		{
			tinBoxDetail.setWarehouseName(warehouse.getName());
			// Check avail new slot
			TinBoxDetail tinBoxDetailCheck = checkAvailNewSlot(tinBoxDetail, unit);
			if(tinBoxDetailCheck != null)
			{
				return tinBoxDetailCheck;
			}
		}
		barCodeDetail = barCodes.get(0);
		barCodeDetail.setStatus(1);
		if(unit == 1) {
			vt070001dao.updateStatusBarcodeDetailGroup(barCodeDetail);
		}
		else {
			vt070001dao.updateStatusBarcodeDetail(barCodeDetail);
		}
		if(tinBoxDetail.getName() != null) {
			tinBoxDetail.setName(validateXss(tinBoxDetail.getName()));
		}
		if(unit == 1) {
			vt070002dao.insertTinBoxGroup(tinBoxDetail);
		}
		else {
			vt070002dao.insertTinBox(tinBoxDetail);
		}
		long tinBoxId = tinBoxDetail.getTinBoxId();
		if(tinBoxDetail.getFolders() != null) {
			// Add folder
			tinBoxDetail.getFolders().forEach(folder -> {

				if(folder.getFolderId() != 0) {
					folder.setTinBoxId(tinBoxId);
					if(unit == 1) {
						vt070002dao.updateFolderGroup(folder);
					}
					else {
						vt070002dao.updateFolder(folder);
					}
					updateProjectInFolder(folder, folder.getFolderId(), userName, unit);
					return;
				}
				// Check barcode is available
				VT070001EntityBarcodeDetail folderBarCodeDetail = new VT070001EntityBarcodeDetail();
				folderBarCodeDetail.setCode(folder.getQrCode());
				List<VT070001EntityBarcodeDetail> folderBarCodes = unit == 1 ? vt070001dao.checkBarcodeIsAvailGroup(folderBarCodeDetail)
						: vt070001dao.checkBarcodeIsAvail(folderBarCodeDetail);
				if(folderBarCodes != null && folderBarCodes.size() == 1)
				{
					folderBarCodeDetail = folderBarCodes.get(0);
					folderBarCodeDetail.setStatus(1);
					if(unit == 1) {
						vt070001dao.updateStatusBarcodeDetailGroup(folderBarCodeDetail);
					}
					else {
						vt070001dao.updateStatusBarcodeDetail(folderBarCodeDetail);
					}
					folder.setTinBoxId(tinBoxId);
					folder.setCreateUser(userName);
					folder.setName(validateXss(folder.getName()));
					folder.setUnit(unit);
					if(unit == 1) {
						vt070002dao.insertFolderGroup(folder);
					}
					else {
						vt070002dao.insertFolder(folder);
					}
					long folderId = folder.getFolderId();
					// Add project
					List<ProjectDetail> projects = folder.getProjects();
					if(projects != null) {
						projects.forEach(project -> {
							// Add doc of project
							if(project.getDocs() != null) {
								project.getDocs().forEach(doc -> {
									doc.setFolderId(folderId);
									if(doc.getAction() == Constant.ADD_ACTION)
									{
										if(unit ==1) {
											vt070002dao.updateProjectDocDetailGroup(doc);
										}
										else {
											vt070002dao.updateProjectDocDetail(doc);
										}
									}
								});
							}

							// Add contract
							if(project.getContracts() != null) {
								project.getContracts().forEach(contract -> {
									// Add doc of contract
									if(contract.getDocs() != null) {
										contract.getDocs().forEach(doc -> {
											contract.setContractId(getContractIdFromDocId(doc.getContractDocId(), unit));
											doc.setFolderId(folderId);
											if(doc.getAction() == Constant.ADD_ACTION)
											{
												if(unit == 1) {
													vt070002dao.updateContractDocDetailGroup(doc);
												}
												else {
													vt070002dao.updateContractDocDetail(doc);
												}
											}
										});
									}

									// Add construction
									if(contract.getConstructions() != null) {
										contract.getConstructions().forEach(construction -> {
											// Add doc of construction
											contract.setContractId(construction.getContractId());
											if(construction.getDocs() != null) {
												construction.getDocs().forEach(doc -> {
													doc.setFolderId(folderId);
													if(doc.getAction() == Constant.ADD_ACTION)
													{
														if(unit == 1) {
															vt070002dao.updateConstructionDocDetailGroup(doc);
														}
														else {
															vt070002dao.updateConstructionDocDetail(doc);
														}
													}
													long constructionId = getConstructionIdFromDocId(doc.getConstructionDocId(), unit);
													contract.setContractId(getContractIdFromConstructionId(constructionId, unit));
													construction.setConstructionId(constructionId);
												});

												// Add relation construction - project
												if(construction.getDocs().size() > 0) {
													construction.setProjectId(project.getProjectId());
													construction.setUpdateUser(userName);
													saveRelationProjectConstruction(construction, unit);
												}
											}		
										});
									}

									// Update contract
									contract.setProjectId(project.getProjectId());
									contract.setUpdateUser(userName);
									saveRelationProjectContract(contract, unit);
								});
							}

							// Add package
							if(project.getPackages() != null) {
								project.getPackages().forEach(packagz -> {
									// Add doc of package
									if(packagz.getDocs() != null) {
										packagz.getDocs().forEach(doc -> {
											doc.setFolderId(folderId);
											if(doc.getAction() == Constant.ADD_ACTION)
											{
												if(unit == 1) {
													vt070002dao.updatePackageDocDetailGroup(doc);
												}
												else {
													vt070002dao.updatePackageDocDetail(doc);
												}
											}
										});
									}
								});
							}

							//How to check have any doc of project in folder
							RequestParam requestParam = new RequestParam();
							requestParam.setProjectId(project.getProjectId());
							requestParam.setFolderId(folderId);
							List<ProjectDetail> pInF = unit == 1? vt070002dao.checkProjectInFolderGroup(requestParam)
									: vt070002dao.checkProjectInFolder(requestParam);
							if(pInF != null && !pInF.isEmpty()) {
								ProjectInFoderDetail pifDetail = new ProjectInFoderDetail();
								pifDetail.setFolderId(folderId);
								pifDetail.setProjectId(project.getProjectId());
								pifDetail.setDelFlag(0);
								pifDetail.setCreateUser(userName);
								if(unit ==1) {
									vt070002dao.insertProjectInFoderDetailGroup(pifDetail);
								}
								else {
									vt070002dao.insertProjectInFoderDetail(pifDetail);
								}
							}
						});
					}
				}
				else
				{
					folder.setError(MessageFormat.format(warehouseMessage.getProperty("0700001"), folder.getQrCode()));
					//folder.setError("0700001#QR Code " + folder.getQrCode() + " was used or not exist");
					return;
				}
			});
		}
		RequestParam request = new RequestParam();
		request.setQrCode(tinBoxDetail.getQrCode());
		List<TinBoxDetail> lisTinbox = unit == 1? vt070002dao.getListTinBoxByQrCodeGroup(request)
				: vt070002dao.getListTinBoxByQrCode(request);
		if(lisTinbox != null && !lisTinbox.isEmpty())
			tinBoxDetail = lisTinbox.get(0);
		return tinBoxDetail;
	}

	private boolean updateBarcodeStatus(FolderDetail folder, int unit){
		// Check tin box if change qrCode
		VT070001EntityBarcodeDetail folderBarCodeDetail = new VT070001EntityBarcodeDetail();
		folderBarCodeDetail.setCode(folder.getQrCode());
		List<VT070001EntityBarcodeDetail> folderBarCodes = unit ==1? vt070001dao.checkBarcodeIsAvailGroup(folderBarCodeDetail)
				: vt070001dao.checkBarcodeIsAvail(folderBarCodeDetail);
		if(folderBarCodes != null && folderBarCodes.size() == 1)
		{
			folderBarCodeDetail = folderBarCodes.get(0);
			folderBarCodeDetail.setStatus(1);
			if(unit == 1) {
				vt070001dao.updateStatusBarcodeDetailGroup(folderBarCodeDetail);
			}
			else {
				vt070001dao.updateStatusBarcodeDetail(folderBarCodeDetail);
			}
		}
		else
		{
			folder.setError(MessageFormat.format(warehouseMessage.getProperty("0700001"), folder.getQrCode()));
			//folder.setError("0700001#QR Code " + folder.getQrCode() + " was used or not exist");
			return false;
		}
		return true;
	}
	
	private boolean checkCanDeleteTinBox(TinBoxDetail tinBoxDetail) {
		for(FolderDetail folder : tinBoxDetail.getFolders()) {
			if(folder.getAction() != Constant.DEL_ACTION)
			{
				return false;
			}
		}
		return true;
	}
	@Override
	@Transactional
	public Object updateTinBox(TinBoxDetail tinBoxDetail, String userName, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		TinBoxDetail currentTinBox = unit ==1? vt070002dao.getTinBoxByIdGroup(tinBoxDetail.getTinBoxId())
				: vt070002dao.getTinBoxById(tinBoxDetail.getTinBoxId());
		if(currentTinBox != null) {
			tinBoxDetail.setCreateUser(currentTinBox.getCreateUser());
		}
		if(tinBoxDetail.getAction() == Constant.EDIT_ACTION ||
				tinBoxDetail.getAction() == Constant.DEL_ACTION) {
			tinBoxDetail.setDelFlag(tinBoxDetail.getAction() == Constant.DEL_ACTION ? 1 : 0);
			tinBoxDetail.setUpdateUser(userName);
			if(tinBoxDetail.getAction() == Constant.EDIT_ACTION) {

				if(currentTinBox != null && (currentTinBox.getQrCode() == null
						|| (currentTinBox.getQrCode() != null && !currentTinBox.getQrCode().equals(tinBoxDetail.getQrCode())))) {
					// Check tin box if change qrCode
					VT070001EntityBarcodeDetail barCodeDetail = new VT070001EntityBarcodeDetail();
					barCodeDetail.setCode(tinBoxDetail.getQrCode());
					List<VT070001EntityBarcodeDetail> barCodes = unit == 1 ? vt070001dao.checkBarcodeIsAvailGroup(barCodeDetail)
							: vt070001dao.checkBarcodeIsAvail(barCodeDetail);
					if(barCodes != null && barCodes.size() == 1)
					{
						barCodeDetail = barCodes.get(0);
						barCodeDetail.setStatus(1);
						if(unit ==1) {
							vt070001dao.updateStatusBarcodeDetailGroup(barCodeDetail);
						}
						else {
							vt070001dao.updateStatusBarcodeDetail(barCodeDetail);
						}
					}
					else
					{
						tinBoxDetail.setError(MessageFormat.format(warehouseMessage.getProperty("0700001"), tinBoxDetail.getQrCode()));
						//tinBoxDetail.setError("0700001#QR Code " + tinBoxDetail.getQrCode() + " was used or not exist");
						return tinBoxDetail;
					}
				}
				// Check warehouseType
				WarehouseRequestParam warehouse = unit == 1? vt070000dao.getWarehouseDetailByIdGroup(tinBoxDetail.getWarehouseId() + "")
						: vt070000dao.getWarehouseDetailById(tinBoxDetail.getWarehouseId() + "");
				if(warehouse != null && "0".equals(warehouse.getType())) {
					tinBoxDetail.setWarehouseName(warehouse.getName());
					// Free pre slot
					if(currentTinBox != null)
					{
						SlotDetail slot = unit == 1? vt070000dao.getSlotByIdGroup(currentTinBox.getSlotId())
								: vt070000dao.getSlotById(currentTinBox.getSlotId());
						if(slot != null)
						{
							slot.setStatus(0);
							if(unit ==1) {
								vt070000dao.updateSlotDetailGroup(slot);
							}
							else {
								vt070000dao.updateSlotDetail(slot);
							}							
						}
					}
					// Empty slot
					tinBoxDetail.setSlotQrCode(null);
					tinBoxDetail.setSlotId(0);
				}
				else if(warehouse != null && "1".equals(warehouse.getType())) {
					tinBoxDetail.setWarehouseName(warehouse.getName());
					if(currentTinBox != null && ((currentTinBox.getSlotId() == 0) || (currentTinBox.getSlotId() != 0 
							&& currentTinBox.getSlotId() != tinBoxDetail.getSlotId()))) {
						// Check avail new slot
						TinBoxDetail tinBoxDetailCheck = checkAvailNewSlot(tinBoxDetail, unit);
						if(tinBoxDetailCheck != null)
						{
							return tinBoxDetailCheck;
						}
						SlotDetail slot = unit == 1? vt070000dao.getSlotByIdGroup(currentTinBox.getSlotId())
								: vt070000dao.getSlotById(currentTinBox.getSlotId());
						if(slot != null)
						{
							slot.setStatus(0);
							if(unit ==1) {
								vt070000dao.updateSlotDetailGroup(slot);
							}
							else {
								vt070000dao.updateSlotDetail(slot);
							}
						}
					}
				}
			}
			else
			{
				// Check empty tinbox
				if(!checkCanDeleteTinBox(tinBoxDetail))
				{
					tinBoxDetail.setError(MessageFormat.format(warehouseMessage.getProperty("0700006"), tinBoxDetail.getQrCode()));
					//tinBoxDetail.setError("0700006#Tinbox " + tinBoxDetail.getQrCode() + " can not delete");
					return tinBoxDetail;
				}
				// Check user right
				if(!(tinBoxDetail.getRoles().contains("delete")))
				{
					if(currentTinBox != null && currentTinBox.getCreateUser() != null && !currentTinBox.getCreateUser().equals(userName))
					{
						tinBoxDetail.setError(MessageFormat.format(warehouseMessage.getProperty("0700011"), tinBoxDetail.getQrCode()));
						//tinBoxDetail.setError("0700006#Tinbox " + tinBoxDetail.getQrCode() + " can not delete");
						return tinBoxDetail;
					}
				}
				// Free slot
				SlotDetail slot = unit == 1 ? vt070000dao.getSlotByIdGroup(tinBoxDetail.getSlotId())
						: vt070000dao.getSlotById(tinBoxDetail.getSlotId());
				if(slot != null) {
					slot.setStatus(0);
					slot.setUpdateUser(userName);
					if(unit ==1) {
						vt070000dao.updateSlotDetailGroup(slot);	
					}
					else {
						vt070000dao.updateSlotDetail(slot);
					}
				}
			}
			if(tinBoxDetail != null && tinBoxDetail.getName() != null) {
				tinBoxDetail.setName(validateXss(tinBoxDetail.getName()));
			}
			if(unit == 1) {
				vt070002dao.updateTinBoxGroup(tinBoxDetail);
			}
			else {
				vt070002dao.updateTinBox(tinBoxDetail);
			}
		}
		long tinBoxId = tinBoxDetail != null ? tinBoxDetail.getTinBoxId() : 0;
		if(tinBoxDetail != null && tinBoxDetail.getFolders() != null) {
			// Add folder			
			tinBoxDetail.getFolders().forEach(folder -> {
				FolderDetail currentFolder = unit == 1? vt070002dao.getFolderByIdGroup(folder.getFolderId())
						: vt070002dao.getFolderById(folder.getFolderId());
				if(folder.getAction() == Constant.EDIT_ACTION) {
					if(currentFolder != null && (currentFolder.getQrCode() == null
							|| (currentFolder.getQrCode() != null && !currentFolder.getQrCode().equals(folder.getQrCode())))) {
						// Check tin box if change qrCode
						if(!updateBarcodeStatus(folder, unit))
							return;
					}
					folder.setUpdateUser(userName);
					folder.setName(validateXss(folder.getName()));
					//Phase 2
					folder.setTinBoxId(tinBoxId);
					if(unit ==1) {
						vt070002dao.updateFolderGroup(folder);					
					}
					else {
						vt070002dao.updateFolder(folder);
					}
				}
				if(folder.getAction() == Constant.DEL_ACTION) {
					if(tinBoxDetail.getRoles().contains("delete") || ((currentFolder != null && currentFolder.getCreateUser() != null && currentFolder.getCreateUser().equals(userName))))
					{
						folder.setUpdateUser(userName);
						folder.setDelFlag(1);
						RequestParam updateParam = new RequestParam();
						updateParam.setFolderId(folder.getFolderId());
						
						updateParam.setUpdateUser(userName);
						folder.setName(validateXss(folder.getName()));
						if(unit == 1) {
							vt070002dao.updateFolderGroup(folder);
							vt070002dao.removeDocsProjectInFolderGroup(updateParam);
							vt070002dao.removeDocsPackageInFolderGroup(updateParam);
							vt070002dao.removeDocsContractInFolderGroup(updateParam);
							vt070002dao.removeDocsConstructionInFolderGroup(updateParam);
						}
						else {
							vt070002dao.updateFolder(folder);
							vt070002dao.removeDocsProjectInFolder(updateParam);
							vt070002dao.removeDocsPackageInFolder(updateParam);
							vt070002dao.removeDocsContractInFolder(updateParam);
							vt070002dao.removeDocsConstructionInFolder(updateParam);	
						}
					}
					else
					{
						folder.setError(MessageFormat.format(warehouseMessage.getProperty("0700007"), folder.getQrCode()));
						
						//folder.setError("0700007#Folder " + folder.getQrCode() + " can not delete");
					}
				}
				if(folder.getAction() == Constant.CREATE_ACTION) {
					// Check qrCode
					if(!updateBarcodeStatus(folder, unit))
						return;
					folder.setTinBoxId(tinBoxId);
					folder.setCreateUser(userName);
					folder.setName(validateXss(folder.getName()));
					folder.setUnit(unit);
					if(unit ==1) {
						vt070002dao.insertFolderGroup(folder);
					}
					else {
						vt070002dao.insertFolder(folder);
					}
				}				
				long folderId = folder.getFolderId();

				// Add project
				
				updateProjectInFolder(folder, folderId, userName, unit);

			});
		}

		return tinBoxDetail;
	}


	@Override
	public Object getAvailObjectInProject(RequestParam requestParam, Collection<GrantedAuthority> userRoles) {
		requestParam.setPageNo(requestParam.getPageNo() * 20);
		if(requestParam.getName() != null && !"".equals(requestParam.getName())) {
			requestParam.setName(requestParam.getName().replace(" ", "%"));
		}
		int unit = getUnit(userRoles);
		requestParam.setUnit(unit);
		
		List<ProjectDetail> projects = unit ==1? vt070002dao.getProjectsGroup(requestParam)
				: vt070002dao.getProjects(requestParam);
		if(projects != null && !projects.isEmpty()) {
			projects.forEach(project -> {
				List<ProjectDocDetail> projectDocs = unit ==1? vt070002dao.getAvailProjectDocsGroup(project.getProjectId())
						: vt070002dao.getAvailProjectDocs(project.getProjectId());
				project.setDocs(projectDocs);				
				if(requestParam.getObjectType() == 1) {
					List<PackageDetail> packages = unit ==1? vt070002dao.getPackagesGroup(requestParam)
							: vt070002dao.getPackages(requestParam);
					project.setPackages(packages);
					if(packages != null && !packages.isEmpty()) {
						packages.forEach(packagz -> {
							List<PackageDocDetail> packageDocs = unit ==1? vt070002dao.getAvailPackageDocsGroup(packagz.getPackageId())
									: vt070002dao.getAvailPackageDocs(packagz.getPackageId());
							packagz.setDocs(packageDocs);
						});
					}
				}
				if(requestParam.getObjectType() >= 2) {
					List<ContractDetail> contracts = unit ==1? vt070002dao.getContractsGroup(requestParam)
							: vt070002dao.getContracts(requestParam);
					project.setContracts(contracts);
					if(contracts != null && !contracts.isEmpty()) {
						contracts.forEach(contract -> {
							List<ContractDocDetail> contractDocs = unit ==1? vt070002dao.getAvailContractDocsGroup(contract.getContractId())
									: vt070002dao.getAvailContractDocs(contract.getContractId());
							contract.setDocs(contractDocs);
							//Return all construction
							if(requestParam.getObjectType() == 3) {
								List<ConstructionDetail> constructions = unit ==1? vt070002dao.getConstructionsGroup(requestParam)
										: vt070002dao.getConstructions(requestParam);
								contract.setConstructions(constructions);
								if(constructions != null && !constructions.isEmpty()) {
									constructions.forEach(construction -> {
										List<ConstructionDocDetail> constructionDocs = unit == 1? vt070002dao.getAvailConstructionDocsGroup(construction.getConstructionId())
												: vt070002dao.getAvailConstructionDocs(construction.getConstructionId());
										construction.setDocs(constructionDocs);
									});
								}
							}
						});
					}
				}

			});
		}
		return projects;
	}

	@Override
	public Object getProjectObjectInFolder(RequestParam requestParam, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		requestParam.setPageNo(requestParam.getPageNo() * 20);
		List<ProjectDetail> projects = unit == 1? vt070002dao.getProjectByIdGroup(requestParam) 
				: vt070002dao.getProjectById(requestParam);
		if(projects != null && !projects.isEmpty()) {
			projects.forEach(project -> {
				List<ProjectDocDetail> docs = unit == 1? vt070002dao.getProjectDocInFolderGroup(requestParam)
						: vt070002dao.getProjectDocInFolder(requestParam);
				project.setDocs(docs);

				List<PackageDetail> packages = unit == 1? vt070002dao.getProjectPackagesInFolderGroup(requestParam)
						: vt070002dao.getProjectPackagesInFolder(requestParam);
				if(packages != null && !packages.isEmpty()) {
					packages.forEach(packagz -> {
						RequestParam packageDocRequestParam = new RequestParam();
						packageDocRequestParam.setFolderId(requestParam.getFolderId());
						packageDocRequestParam.setPackageId(packagz.getPackageId());
						List<PackageDocDetail> packageDocs = unit == 1? vt070002dao.getPackageDocInFolderGroup(packageDocRequestParam)
								: vt070002dao.getPackageDocInFolder(packageDocRequestParam);
						packagz.setDocs(packageDocs);
						
						
						RequestParam contractParam = new RequestParam();
						contractParam.setFolderId(requestParam.getFolderId());
						contractParam.setPackageId(packagz.getPackageId());
						
						List<ContractDetail> contracts = unit == 1? vt070002dao.getPackageContractsInFolderGroup(contractParam)
								: vt070002dao.getPackageContractsInFolder(contractParam);
						if(contracts != null && !contracts.isEmpty()) {
							contracts.forEach(contract -> {
								RequestParam contractRequestParam = new RequestParam();
								contractRequestParam.setFolderId(requestParam.getFolderId());
								contractRequestParam.setContractId(contract.getContractId());
								contractRequestParam.setProjectId(project.getProjectId());
								List<ContractDocDetail> contractDocs = unit ==1? vt070002dao.getContractDocInFolderGroup(contractRequestParam)
										: vt070002dao.getContractDocInFolder(contractRequestParam);
								contract.setDocs(contractDocs);

								List<ConstructionDetail> constructions = unit ==1 ? vt070002dao.getProjectConstructionsInFolderGroup(contractRequestParam)
										: vt070002dao.getProjectConstructionsInFolder(contractRequestParam);
								getConstructionDocs(constructions, requestParam, unit);
								contract.setConstructions(constructions);
							});
						}
						packagz.setContracts(contracts);
						
					});
				}
				project.setPackages(packages);
			});
		}
		return projects;
	}
	
	private void getConstructionDocs(List<ConstructionDetail> constructions, RequestParam requestParam, int unit) {
		if(constructions != null && !constructions.isEmpty()) {
			constructions.forEach(construction -> {
				RequestParam constructionDocRequestParam = new RequestParam();
				constructionDocRequestParam.setFolderId(requestParam.getFolderId());
				constructionDocRequestParam.setConstructionId(construction.getConstructionId());
				List<ConstructionDocDetail> constructionDocs = unit == 1? vt070002dao.getConstructionDocInFolderGroup(constructionDocRequestParam)
						: vt070002dao.getConstructionDocInFolder(constructionDocRequestParam);
				construction.setDocs(constructionDocs);
			});							
		}
	}

	@Override
	public ResponseEntityBase findTypeWarehouseDetail(WarehouseDetail object, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<WarehouseDetail> data = new ArrayList<>();
		try {

			data = unit ==1? vt070002dao.findTypeWarehouseDetailGroup(object)
					: vt070002dao.findTypeWarehouseDetail(object);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			logger.info(e.getMessage());
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
		}
		return response;
	}
	public String validateXss(String checkXss) {
		/*checkXss = checkXss.replace("&","&amp;");
		checkXss = checkXss.replace("\"","&quot;");
		checkXss = checkXss.replace("'","&#x27;");
		checkXss = checkXss.replace("/","&#x2F;");
		checkXss = checkXss.replace(">","&gt;");
		checkXss = checkXss.replace("<","&lt;");*/
		return checkXss;
	}
	@Override
	public ResponseEntityBase updateTinboxByInformation(TinBoxDetail object, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			String checkXss =  object.getName();
			if(checkXss!=null) {
				checkXss = validateXss(checkXss);
				if(checkXss.length()>255) {
					checkXss = checkXss.substring(0, 255);
				}
				object.setName(checkXss);
			}
			checkXss =  object.getMngUser();
			if(checkXss!=null) {
				checkXss = validateXss(checkXss);
				if(checkXss.length()>50) {
					checkXss = checkXss.substring(0, 50);
				}
				object.setMngUser(checkXss);
			}
			int rowEffect = unit ==1? vt070002dao.updateTinboxByInformationGroup(object) 
					: vt070002dao.updateTinboxByInformation(object);

			if (rowEffect > 0) {
				return new ResponseEntityBase(Constant.REQUEST_ACTION_INSERT, null);
			} else {
				return new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public ResponseEntityBase getProjectDocOfWarehouse(RequestParam requestParam, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		ResponseEntityBase response = null;
		List<DocumentReportRecord> data = new ArrayList<>();
		List<DocumentReportRecord> dataTemp = new ArrayList<>();
		try {
			int totalRecords = unit ==1? vt070002dao.countProjectDocumentWithWarehouseIdGroup(requestParam)
					: vt070002dao.countProjectDocumentWithWarehouseId(requestParam);
			
			dataTemp = unit ==1? vt070002dao.getProjectDocumentWithWarehouseIdGroup(requestParam)
					: vt070002dao.getProjectDocumentWithWarehouseId(requestParam);
			if(dataTemp != null) {
				for(DocumentReportRecord temp : dataTemp) {
					if((temp.getPackageDoc() != null &&
							temp.getContractDoc() != null)
							) {
						DocumentReportRecord temp1 = new DocumentReportRecord();
						temp1.setProjectId(temp.getProjectId());
						temp1.setProjectName(temp.getProjectName());
						temp1.setProjectDoc(temp.getProjectDoc());
						temp1.setPackageName(temp.getPackageName());
						temp1.setPackageDoc(temp.getPackageDoc());
						temp1.setTinBoxQRCode(temp.getTinBoxQRCode());
						temp1.setTinBoxName(temp.getTinBoxName());
						temp1.setFolderQRCode(temp.getFolderQRCode());
						temp1.setFolderName(temp.getFolderName());
						temp1.setWarehouseName(temp.getWarehouseName());
						temp1.setPosition(temp.getPosition());
						temp1.setPositionQRCode(temp.getPositionQRCode());
						DocumentReportRecord temp2 = new DocumentReportRecord();
						temp2.setProjectId(temp.getProjectId());
						temp2.setProjectName(temp.getProjectName());
						temp2.setProjectDoc(temp.getProjectDoc());
						temp2.setContractName(temp.getContractName());
						temp2.setContractDoc(temp.getContractDoc());
						if(temp.getConstructionDoc() != null) {
							temp2.setConstructionName(temp.getConstructionName());
							temp2.setConstructionDoc(temp.getConstructionDoc());
						}
						temp2.setTinBoxQRCode(temp.getTinBoxQRCode());
						temp2.setTinBoxName(temp.getTinBoxName());
						temp2.setFolderQRCode(temp.getFolderQRCode());
						temp2.setFolderName(temp.getFolderName());
						temp2.setWarehouseName(temp.getWarehouseName());
						temp2.setPosition(temp.getPosition());
						temp2.setPositionQRCode(temp.getPositionQRCode());
						totalRecords++;
					} else {
						data.add(temp);

					}
				}
			}
			int totalTinbox = unit == 1? vt070002dao.countProjectTinboxWithWarehouseIdGroup(requestParam)
					: vt070002dao.countProjectTinboxWithWarehouseId(requestParam);
			int totalFolder = unit == 1? vt070002dao.countProjectFolderWithWarehouseIdGroup(requestParam)
					: vt070002dao.countProjectFolderWithWarehouseId(requestParam);
			if (!data.isEmpty()) {
				if (requestParam.getPageNumber() == 0 && data.size() < requestParam.getPageSize()) {
					totalRecords = data.size();
				}
				data.get(0).setTotalRecords(totalRecords);
				data.get(0).setTotalTinbox(totalTinbox);
				data.get(0).setTotalFolder(totalFolder);
			}
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch(Exception e) {
			logger.info(e.getMessage());
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
		}
		return response;
	}

	@Override
	public ResponseEntityBase getDocumentReport(int warehouseId, int pageSize, int pageNumber, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		ResponseEntityBase response = null;
		Map<String, Object> data = new HashMap<>();
		int offset = (pageNumber-1)*pageSize;
		try {
			WarehouseDetail warehouse = unit == 1? vt070002dao.countThingsInWarehouseGroup(warehouseId)
					: vt070002dao.countThingsInWarehouse(warehouseId);
			int totalTinBox = warehouse.getTotalTinBox();
			int totalFolder = warehouse.getTotalFolder();
			int totalDocument = warehouse.getTotalRecords();
			data.put("totalTinbox",totalTinBox);
			data.put("totalFolder", totalFolder);
			data.put("totalDocument", totalDocument);
			data.put("returnRecords", unit == 1? vt070002dao.getDocumentInWarehouseGroup(warehouseId, pageSize, offset)
					: vt070002dao.getDocumentInWarehouse(warehouseId, pageSize, offset));

			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		}
		catch (Exception e) {
			logger.info(e.getMessage());
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
		}
		return response;
	}

	@Override
	public File exportExcel(int warehouseId, Collection<GrantedAuthority> listRole) {
		int unit = getUnit(listRole);
		File file = null;
		String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT70002");
		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE))
					|| (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE_REPORT)))) {

				// no paging, get all the records
				int pageSize = -1;
				int offset = -1;

				List<DocumentReportRecord> records = unit ==1? vt070002dao.getDocumentInWarehouseGroup(warehouseId, pageSize, offset)
						: vt070002dao.getDocumentInWarehouse(warehouseId, pageSize, offset);
//				long totalFolder = vt070002dao.countFolderInWarehouse(warehouseId);
//				long totalTinBox = vt070002dao.countTinBoxInWarehouse(warehouseId);
				WarehouseDetail warehouse = unit ==1? vt070002dao.countThingsInWarehouseGroup(warehouseId)
						: vt070002dao.countThingsInWarehouse(warehouseId);
				int totalTinBox = warehouse.getTotalTinBox();
				int totalFolder = warehouse.getTotalFolder();
				file = writeExcel(records, totalFolder, totalTinBox, excelFilePath);
			}
		}
		catch (Exception e) {
			logger.info(e.getMessage());
		}
		return file;
	}

	@Override
	public File createExcelOutputExcel(RequestParam requestParam,
			Collection<GrantedAuthority> listRole) {
		int unit = getUnit(listRole);
		File file = null;
		
		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE))
					|| (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE_REPORT)))) {
				List<DocumentReportRecord> listDocumentReport = new ArrayList<>();
				List<DocumentReportRecord> listDocumentReportTemp = unit ==1? vt070002dao.getAllProjectDocumentWithWarehouseIdGroup(requestParam)
						: vt070002dao.getAllProjectDocumentWithWarehouseId(requestParam);
				if (listDocumentReportTemp == null || listDocumentReportTemp.isEmpty()) {
					listDocumentReport = new ArrayList<>();
					listDocumentReport.add(new DocumentReportRecord());
					listDocumentReport.get(0).setCreateUser(requestParam.getCreateUser());
				} else {
					for(DocumentReportRecord temp : listDocumentReportTemp) {
						if((temp.getPackageDoc() != null &&
								temp.getContractDoc() != null)
								) {
							DocumentReportRecord temp3 = new DocumentReportRecord();
							temp3.setProjectId(temp.getProjectId());
							temp3.setProjectName(temp.getProjectName());
							temp3.setProjectDoc(temp.getProjectDoc());
							temp3.setPackageName(temp.getPackageName());
							temp3.setPackageDoc(temp.getPackageDoc());
							temp3.setTinBoxQRCode(temp.getTinBoxQRCode());
							temp3.setTinBoxName(temp.getTinBoxName());
							temp3.setFolderQRCode(temp.getFolderQRCode());
							temp3.setFolderName(temp.getFolderName());
							temp3.setWarehouseName(temp.getWarehouseName());
							temp3.setPosition(temp.getPosition());
							temp3.setPositionQRCode(temp.getPositionQRCode());
							listDocumentReport.add(temp3);
							DocumentReportRecord temp4 = new DocumentReportRecord();
							temp4.setProjectId(temp.getProjectId());
							temp4.setProjectName(temp.getProjectName());
							temp4.setProjectDoc(temp.getProjectDoc());
							temp4.setContractName(temp.getContractName());
							temp4.setContractDoc(temp.getContractDoc());
							if(temp.getConstructionDoc() != null) {
								temp4.setConstructionName(temp.getConstructionName());
								temp4.setConstructionDoc(temp.getConstructionDoc());
							}
							temp4.setTinBoxQRCode(temp.getTinBoxQRCode());
							temp4.setTinBoxName(temp.getTinBoxName());
							temp4.setFolderQRCode(temp.getFolderQRCode());
							temp4.setFolderName(temp.getFolderName());
							temp4.setWarehouseName(temp.getWarehouseName());
							temp4.setPosition(temp.getPosition());
							temp4.setPositionQRCode(temp.getPositionQRCode());
							listDocumentReport.add(temp4);
						} else {
							listDocumentReport.add(temp);

						}
					}
					listDocumentReport.get(0).setCreateUser(requestParam.getCreateUser());
					int totalTinbox = unit == 1? vt070002dao.countProjectTinboxWithWarehouseIdGroup(requestParam)
							: vt070002dao.countProjectTinboxWithWarehouseId(requestParam);
					int totalFolder = unit ==1? vt070002dao.countProjectFolderWithWarehouseIdGroup(requestParam)
							: vt070002dao.countProjectFolderWithWarehouseId(requestParam);
					listDocumentReport.get(0).setTotalTinbox(totalTinbox);
					listDocumentReport.get(0).setTotalFolder(totalFolder);
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}

		return file;
	}

	public File writeExcel(List<DocumentReportRecord> records, long totalFolder, long totalTinBox, String excelFilePath) throws IOException {

		File outFile = null;
		File file = null;

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String fileName = cal.get(Calendar.YEAR) + "_" + (cal.get(Calendar.MONTH) + 1) + "_" + cal.get(Calendar.DAY_OF_MONTH) + "_"
				+ cal.get(Calendar.HOUR_OF_DAY) + "h" + cal.get(Calendar.MINUTE) + "m" + cal.get(Calendar.SECOND) + ".xlsx";
		try {
			Resource resource2 = new ClassPathResource(excelFilePath);
			file = resource2.getFile();
			String filePath = file.getAbsolutePath();
			outFile = new File(filePath.split("templateExcel")[0] + "saveExcel\\VT07\\" + fileName);
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		}

		try (FileInputStream inputStream = new FileInputStream(file)) {
			try (SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(inputStream), 100)) {
				SXSSFSheet sheet = workbook.getSheetAt(0);

				CellStyle cellStyle2 = sheet.getWorkbook().createCellStyle();
				CellStyle cellStyleHeader = sheet.getWorkbook().createCellStyle();
				CellStyle cellStyleInfo = sheet.getWorkbook().createCellStyle();

				Font fontContent = sheet.getWorkbook().createFont();
				fontContent.setFontName("Times New Roman");
				fontContent.setFontHeightInPoints((short) 11);

				cellStyle2.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
				cellStyle2.setFillPattern(FillPatternType.NO_FILL);
				cellStyle2.setBorderTop(BorderStyle.THIN);
				cellStyle2.setBorderRight(BorderStyle.THIN);
				cellStyle2.setBorderBottom(BorderStyle.THIN);
				cellStyle2.setBorderLeft(BorderStyle.THIN);
				cellStyle2.setFont(fontContent);
				cellStyleInfo.setFont(fontContent);

				Font fontHeader2 = sheet.getWorkbook().createFont();
				fontHeader2.setBold(true);
				fontHeader2.setFontName("Times New Roman");
				fontHeader2.setFontHeightInPoints((short) 11);
				cellStyleHeader.setFont(fontHeader2);
				cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);
				cellStyleHeader.setVerticalAlignment(VerticalAlignment.CENTER);

				int rowCount = 2;
				int rowNumber = 1;
				long totalDocument = records.size();

				writeBookHeader(totalDocument, totalFolder, totalTinBox,  sheet,  rowNumber, cellStyleHeader,  cellStyleInfo);

				for (DocumentReportRecord record : records) {
					writeBook(record, sheet, ++rowCount, cellStyle2, rowNumber++);
				}



				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				}
			}
		}

		return outFile;
	}

	private void writeBook(DocumentReportRecord record, Sheet sheet, int rowNum, CellStyle cellStyle,
			int rowNumber) {
		Row row = sheet.createRow(rowNum);

		// No.
		Cell cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(rowNumber);

		// Warehouse Name
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(record.getProjectName());

		// Row
		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(record.getPackageName());

		// Column
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(record.getContractName());

		// Position
		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(record.getConstructionName());

		// Qr Code
		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(record.getDocumentName());

		// Status
		cell = row.createCell(6);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(record.getTinBoxQRCode());

		// Status
		cell = row.createCell(7);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(record.getTinBoxName());

		// Status
		cell = row.createCell(8);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(record.getFolderQRCode());

		// Status
		cell = row.createCell(9);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(record.getFolderName());

		// Status
		cell = row.createCell(10);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(record.getWarehouseName());


		// Status
		cell = row.createCell(11);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(record.getPosition());


		// Status
		cell = row.createCell(12);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(record.getPositionQRCode());

	}

	private void writeBookHeader(long totalDocument, long totalFolder, long totalTinBox, Sheet sheet, int rowNum,
			CellStyle cellStyleHeader, CellStyle cellStyleInfo) {
		Row row = sheet.createRow(rowNum);

		Cell cell = row.createCell(5);
		cell.setCellStyle(cellStyleInfo);
		cell.setCellValue(MessageFormat.format(headerTitle.getProperty("TOTAL_DOC"), totalDocument));


		cell = row.createCell(7);
		cell.setCellStyle(cellStyleInfo);
		cell.setCellValue(MessageFormat.format(headerTitle.getProperty("TOTAL_TINBOX"), totalTinBox));


		cell = row.createCell(9);
		cell.setCellStyle(cellStyleInfo);
		cell.setCellValue(MessageFormat.format(headerTitle.getProperty("TOTAL_FOLDER"), totalFolder));		

		Row rowHeader = sheet.createRow(++rowNum);

		/*Cell cellHeader2 = rowHeader.createCell(0);
		cellHeader2.setCellStyle(cellStyleHeader);
		cellHeader2.setCellValue("STT");*/
		createCell(rowHeader, 0, cellStyleHeader, "STT");
		/*cellHeader2 = rowHeader.createCell(1);
		cellHeader2.setCellStyle(cellStyleHeader);
		cellHeader2.setCellValue(headerTitle.getProperty("PROJECT"));*/
		createCell(rowHeader, 1, cellStyleHeader, headerTitle.getProperty("PROJECT"));
		/*cellHeader2 = rowHeader.createCell(2);
		cellHeader2.setCellStyle(cellStyleHeader);
		cellHeader2.setCellValue(headerTitle.getProperty("PACKAGE"));*/
		createCell(rowHeader, 2, cellStyleHeader, headerTitle.getProperty("PACKAGE"));
		/*cellHeader2 = rowHeader.createCell(3);
		cellHeader2.setCellStyle(cellStyleHeader);
		cellHeader2.setCellValue(headerTitle.getProperty("CONTRACT"));*/
		createCell(rowHeader, 3, cellStyleHeader, headerTitle.getProperty("CONTRACT"));
		/*cellHeader2 = rowHeader.createCell(4);
		cellHeader2.setCellStyle(cellStyleHeader);
		cellHeader2.setCellValue(headerTitle.getProperty("CONSTRUCTION"));*/
		createCell(rowHeader, 4, cellStyleHeader, headerTitle.getProperty("CONSTRUCTION"));
		/*cellHeader2 = rowHeader.createCell(5);
		cellHeader2.setCellStyle(cellStyleHeader);
		cellHeader2.setCellValue(headerTitle.getProperty("DOC"));*/
		createCell(rowHeader, 5, cellStyleHeader, headerTitle.getProperty("DOC"));
		/*cellHeader2 = rowHeader.createCell(6);
		cellHeader2.setCellStyle(cellStyleHeader);
		cellHeader2.setCellValue(headerTitle.getProperty("TINBOX_CODE"));*/
		createCell(rowHeader, 6, cellStyleHeader, headerTitle.getProperty("TINBOX_CODE"));
		/*cellHeader2 = rowHeader.createCell(7);
		cellHeader2.setCellStyle(cellStyleHeader);
		cellHeader2.setCellValue(headerTitle.getProperty("TINBOX_NAME"));*/
		createCell(rowHeader, 7, cellStyleHeader, headerTitle.getProperty("TINBOX_NAME"));
		/*cellHeader2 = rowHeader.createCell(8);
		cellHeader2.setCellStyle(cellStyleHeader);
		cellHeader2.setCellValue(headerTitle.getProperty("FOLDER_CODE"));*/
		createCell(rowHeader, 8, cellStyleHeader, headerTitle.getProperty("FOLDER_CODE"));
		/*cellHeader2 = rowHeader.createCell(9);
		cellHeader2.setCellStyle(cellStyleHeader);
		cellHeader2.setCellValue(headerTitle.getProperty("FOLDER_NAME"));*/
		createCell(rowHeader, 9, cellStyleHeader, headerTitle.getProperty("FOLDER_NAME"));
		/*cellHeader2 = rowHeader.createCell(10);
		cellHeader2.setCellStyle(cellStyleHeader);
		cellHeader2.setCellValue("Kho");*/
		createCell(rowHeader, 10, cellStyleHeader, "Kho");
		/*cellHeader2 = rowHeader.createCell(11);
		cellHeader2.setCellStyle(cellStyleHeader);
		cellHeader2.setCellValue(headerTitle.getProperty("RACK"));*/
		createCell(rowHeader, 11, cellStyleHeader, headerTitle.getProperty("RACK"));
		/*cellHeader2 = rowHeader.createCell(12);
		cellHeader2.setCellStyle(cellStyleHeader);
		cellHeader2.setCellValue(headerTitle.getProperty("RACK_CODE"));*/
		createCell(rowHeader, 12, cellStyleHeader, headerTitle.getProperty("RACK_CODE"));
	}
	
	private void createCell(Row rowHeader, int index, CellStyle cellStyleHeader, String name) {
		Cell cellHeader2 = rowHeader.createCell(index);
		cellHeader2.setCellStyle(cellStyleHeader);
		cellHeader2.setCellValue(name);
	}
	
	@Override
	public ResponseEntityBase countTinBoxAndOccupiedSlot(int warehouseId, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		ResponseEntityBase response = null;
		Map<String, Object> data = new HashMap<>();
		try {
			data.put("totalTinBox", unit == 1? vt070002dao.countTotalTinBoxGroup(warehouseId)
					: vt070002dao.countTotalTinBox(warehouseId));
			data.put("occupiedSlot", unit ==1? vt070002dao.countOccupiedSlotGroup(warehouseId)
					: vt070002dao.countOccupiedSlot(warehouseId));

			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		}
		catch (Exception e) {
			logger.info(e.getMessage(), e);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
		}
		return response;
	}
	
	@Override
	public Object getFolderByQRCode(RequestParam requestParam, String userName, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		requestParam.setUnit(unit);
		List<FolderDetail> results = unit ==1? vt070002dao.getListFolderByQrCodeGroup(requestParam)
				: vt070002dao.getListFolderByQrCode(requestParam);
		if(results.isEmpty()) {
			FolderDetail emptyFolder = new FolderDetail();
			emptyFolder.setError(MessageFormat.format(warehouseMessage.getProperty("0700010"), requestParam.getQrCode()));
			return new Object() {
				@SuppressWarnings("unused")
				public FolderDetail folder = emptyFolder;
			};	
		}
		return new Object() {
			@SuppressWarnings("unused")
			public FolderDetail folder = results.get(0);
		};
	}
	
	@Override
	public Object getProjectTreeInFolder(RequestParam requestParam, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		if(unit != 1)
		{
			return getProjectObjectInFolder(requestParam, userRoles);
		}
		requestParam.setPageNo(requestParam.getPageNo() * 20);
		List<ProjectDetail> projects = unit ==1? vt070002dao.getProjectByIdGroup(requestParam)
				: vt070002dao.getProjectById(requestParam);
		if(projects != null && !projects.isEmpty()) {
			projects.forEach(project -> {
				List<ProjectDocDetail> docs = unit ==1? vt070002dao.getProjectDocInFolderGroup(requestParam)
						: vt070002dao.getProjectDocInFolder(requestParam);
				project.setDocs(docs);

				List<PackageDetail> packages = unit ==1? vt070002dao.getProjectPackagesInFolderSuperGroup(requestParam)
						: vt070002dao.getProjectPackagesInFolderSuper(requestParam);
				if(packages != null && !packages.isEmpty()) {
					packages.forEach(packagz -> {
						RequestParam packageDocRequestParam = new RequestParam();
						packageDocRequestParam.setFolderId(requestParam.getFolderId());
						packageDocRequestParam.setPackageId(packagz.getPackageId());
						List<PackageDocDetail> packageDocs = unit ==1? vt070002dao.getPackageDocInFolderGroup(packageDocRequestParam)
								: vt070002dao.getPackageDocInFolder(packageDocRequestParam);
						packagz.setDocs(packageDocs);
					});
				}

				List<ContractDetail> contracts = unit ==1? vt070002dao.getProjectContractsInFolderSuperGroup(requestParam)
						: vt070002dao.getProjectContractsInFolderSuper(requestParam);
				if(contracts != null && !contracts.isEmpty()) {
					contracts.forEach(contract -> {
						RequestParam contractRequestParam = new RequestParam();
						contractRequestParam.setFolderId(requestParam.getFolderId());
						contractRequestParam.setContractId(contract.getContractId());
						contractRequestParam.setProjectId(project.getProjectId());
						List<ContractDocDetail> contractDocs = unit ==1? vt070002dao.getContractDocInFolderGroup(contractRequestParam)
								: vt070002dao.getContractDocInFolder(contractRequestParam);
						contract.setDocs(contractDocs);

						List<ConstructionDetail> constructions = unit ==1? vt070002dao.getProjectConstructionsInFolderSuperGroup(contractRequestParam)
								: vt070002dao.getProjectConstructionsInFolderSuper(contractRequestParam);
						getConstructionDocs(constructions, requestParam, unit);
						contract.setConstructions(constructions);
					});
				}

				project.setContracts(contracts);
				project.setPackages(packages);
			});
		}
		return projects;
	}
	
	@Override
	public ResponseEntityBase getType1Warehouses(WarehouseDetail object, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<WarehouseDetail> data = new ArrayList<>();
		try {

			data = unit ==1? vt070002dao.getType1WarehousesGroup(object)
					: vt070002dao.getType1Warehouses(object);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			logger.info(e.getMessage());
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
		}
		return response;
	}
	
	@Override
	public ResponseEntityBase getTinBoxInType1Warehouse(TinBoxDetail object, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<TinBoxDetail> data = new ArrayList<>();
		try {
			String checkXss =  object.getName();
			if(checkXss!=null) {
				checkXss = validateXss(checkXss);
				object.setName(checkXss);
			}
			checkXss =  object.getMngUser();
			if(checkXss!=null) {
				checkXss = validateXss(checkXss);
				object.setMngUser(checkXss);
			}
			data = unit ==1? vt070002dao.getTinBoxInType1WarehouseGroup(object)
					: vt070002dao.getTinBoxInType1Warehouse(object);

			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			logger.info(e.getMessage());
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
		}
		return response;
	}
	
	private int getUnit(Collection<GrantedAuthority> userRoles) {
		for (GrantedAuthority temp : userRoles) {
	        if (Constant.PMQT_ROLE_WAREHOUSE_GROUP.equalsIgnoreCase(temp.getAuthority())) {
	        	return 1;
	        }
		}
		return 0;
	}
	
	@Override
	public ResponseEntityBase searchDocument(String keyword, int pageSize, int pageNumber, Collection<GrantedAuthority> userRoles) {
		ResponseEntityBase response = null;
		Map<String, Object> data = new HashMap<>();
		int offset = (pageNumber-1)*pageSize;
		try {
			int unit = getUnit(userRoles);
			data.put("returnRecords", unit == 1? vt070002dao.searchDocumentGroup(keyword, pageSize, offset, unit)
					: vt070002dao.searchDocument(keyword, pageSize, offset, unit));

			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		}
		catch (Exception e) {
			logger.info(e.getMessage());
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
		}
		return response;
	}

	@Override
	public List<ProjectDetail> getProjectsInTinBox(long tinBoxId, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);
		List<ProjectDetail> result = unit == 1? vt070002dao.getProjectsInTinBoxGroup(tinBoxId, getUnit(userRoles))
				: vt070002dao.getProjectsInTinBox(tinBoxId, getUnit(userRoles));
		return result;
	}

	@Override
	public List<SearchDocumentResponse> searchDocumentsByKeyword(String keyword, Collection<GrantedAuthority> userRoles, int pageNumber, int pageSize) {
		int unit = getUnit(userRoles);
		List<SearchDocumentResponse> result = new ArrayList<SearchDocumentResponse>();
		
		if(pageNumber < 0 || pageSize < 0) return result;
		int offset = pageSize*(pageNumber - 1);
		
		result = unit == 1? vt070002dao.searchDocumentsByKeywordGroup(keyword, getUnit(userRoles), pageSize, offset)
				: vt070002dao.searchDocumentsByKeyword(keyword, getUnit(userRoles), pageSize, offset);
		return result;
	}
	
	public static String replaceLikeStmtParam(String input) {
		// Reason why,
	    // In mariadb, "\\" is escape word. so, have to replace from "/" to "\\/" and "_" to "\\_"
		input = StringUtils.replace(input, "/", "\\/");
		input = StringUtils.replace(input, "_", "\\_");
		return input;
	}

	public boolean validateRole(RequestParam requestParam, Collection<GrantedAuthority> userRoles) {
		if (requestParam.getTypeSearch() == 7 || requestParam.getTypeSearch() == 8 ) {
			return isOfficialDispatch(userRoles);
		}
		return true;
	}

	private boolean isOfficialDispatch(Collection<GrantedAuthority> userRoles) {
		if (checkRoleExits("PMQT_ADMIN", userRoles) || checkRoleExits("WAREHOUSE_VT", userRoles)) {
			return true;
		}
		return false;
	}

	private boolean checkRoleExits(String role, Collection<GrantedAuthority> userRoles) {
		return userRoles.stream().anyMatch(r -> r.getAuthority().equals(role));
	}
}
