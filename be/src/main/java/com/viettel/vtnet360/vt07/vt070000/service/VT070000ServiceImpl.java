package com.viettel.vtnet360.vt07.vt070000.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;
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
import com.viettel.vtnet360.vt07.vt070000.entity.RequestParam;
import com.viettel.vtnet360.vt07.vt070000.entity.SlotDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.UpdateStatusRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDiagramParam;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseRackSlot;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseRequestParam;
import com.viettel.vtnet360.vt07.vt070000.entity.projecttree.ContractTree;
import com.viettel.vtnet360.vt07.vt070000.entity.projecttree.PackageTree;
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
import com.viettel.vtnet360.vt07.vt070001.dao.VT070001DAO;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT070000ServiceImpl implements VT070000Service {

	@Autowired
	VT070000DAO vt070000dao;
	@Autowired
	VT070001DAO vt070001dao;
	@Autowired
	Properties linkTemplateExcel;
	@Autowired
	Properties headerTitle;
	@Autowired
	Properties warehouseMessage;

	private final Logger logger = Logger.getLogger(this.getClass());

	private int getUnit(Collection<GrantedAuthority> userRoles) {
		for (GrantedAuthority temp : userRoles) {
			// if user had role VIEW ALL TINBOX
			if (Constant.PMQT_ROLE_WAREHOUSE_GROUP.equalsIgnoreCase(temp.getAuthority())) {
				return 1;
			}
		}
		return 0;
	}

	private List<WarehouseDetail> preGetListWarehouseDetail(RequestParam requestParam, int unit) {

		List<WarehouseDetail> results = unit == 1 ? vt070000dao.getListWarehouseGroup(requestParam)
				: vt070000dao.getListWarehouse(requestParam);
		if (results != null && requestParam.getLevel() > 0) {
			results.forEach(warehouse -> {
				List<RackDetail> racks = unit == 1
						? vt070000dao.getListRackDetailByWarehouseIdGroup(warehouse.getWarehouseId())
						: vt070000dao.getListRackDetailByWarehouseId(warehouse.getWarehouseId());
				if (racks == null) {
					racks = new ArrayList<>();
				}
				warehouse.setRacks(racks);
				if (requestParam.getLevel() > 1) {
					racks.forEach(rack -> {
						List<SlotDetail> slots = unit == 1
								? vt070000dao.getListSlotDetailByRackIdGroup(rack.getRackId())
								: vt070000dao.getListSlotDetailByRackId(rack.getRackId());
						if (slots == null) {
							slots = new ArrayList<>();
						}
						rack.setSlots(slots);
					});
				}
			});
		}
		return results;
	}

	@Override
	public Object getListWarehouseDetail(RequestParam requestParam, Collection<GrantedAuthority> roleList) {
		int unit = getUnit(roleList);
		requestParam.setPageNo(requestParam.getPageNo() * 20);
		if (requestParam.getWarehouseName() != null && !"".equals(requestParam.getWarehouseName())) {
			requestParam.setWarehouseName(requestParam.getWarehouseName().trim());
		}
		List<WarehouseDetail> results = preGetListWarehouseDetail(requestParam, unit);
		return new Object() {
			@SuppressWarnings("unused")
			public List<WarehouseDetail> warehouses = results != null ? results : new ArrayList<>();
			@SuppressWarnings("unused")
			public int total = unit == 1 ? vt070000dao.getNumberOfWarehouseGroup(requestParam)
					: vt070000dao.getNumberOfWarehouse(requestParam);
		};
	}

	@Override
	public Object updateWarehouseDetail(WarehouseDetail warehouseDetail, String updateUser,
			Collection<GrantedAuthority> roleList) {
		List<RackDetail> racks = warehouseDetail.getRacks();
		int unit = getUnit(roleList);
		if (racks != null) {
			racks.forEach(rack -> {
				List<SlotDetail> slots = rack.getSlots();
				if (rack.isChanged()) {
					rack.setUpdateUser(updateUser);
					List<SlotDetail> currentSlotsOffRack = unit == 1
							? vt070000dao.getListSlotDetailByRackIdGroup(rack.getRackId())
							: vt070000dao.getListSlotDetailByRackId(rack.getRackId());

					if (currentSlotsOffRack != null && (rack.getType() != 0 && rack.getType() != 2)) {
						int busySlot = 0;
						for (int i = 0; i < currentSlotsOffRack.size(); i++) {
							if (currentSlotsOffRack.get(i).getStatus() != 0) {
								busySlot = busySlot + 1;
								rack.setError(MessageFormat.format(warehouseMessage.getProperty("0700009"),
										currentSlotsOffRack.get(i).getQrCode()));
								// rack.setError("0700009#Can not change type of rack (Slot " +
								// currentSlotsOffRack.get(i).getQrCode() + " is in used)");
							}
						}
						if (busySlot > 0)
							return;

						for (int i = 0; i < currentSlotsOffRack.size(); i++) {
							currentSlotsOffRack.get(i).setQrCode(null);
							currentSlotsOffRack.get(i).setUpdateUser(updateUser);
							if (unit == 1) {
								vt070000dao.updateSlotDetailGroup(currentSlotsOffRack.get(i));
							} else {
								vt070000dao.updateSlotDetail(currentSlotsOffRack.get(i));
							}
						}
						rack.setUpdatedNum(unit == 1 ? vt070000dao.updateRackDetailGroup(rack)
								: vt070000dao.updateRackDetail(rack));
						return;
					}
					rack.setUpdatedNum(
							unit == 1 ? vt070000dao.updateRackDetailGroup(rack) : vt070000dao.updateRackDetail(rack));
				}

				if (slots != null) {
					slots.forEach(slot -> {
						if (slot.isChanged()) {
							slot.setUpdateUser(updateUser);

							SlotDetail currentSlot = unit == 1 ? vt070000dao.getSlotByIdGroup(slot.getSlotId())
									: vt070000dao.getSlotById(slot.getSlotId());
							VT070001EntityBarcodeDetail barCodeDetail = new VT070001EntityBarcodeDetail();
							barCodeDetail.setCode(slot.getQrCode());
							List<VT070001EntityBarcodeDetail> barCodes = unit == 1
									? vt070001dao.checkBarcodeIsAvailGroup(barCodeDetail)
									: vt070001dao.checkBarcodeIsAvail(barCodeDetail);
							if (barCodes != null && barCodes.size() == 1) {
								slot.setUpdatedNum(unit == 1 ? vt070000dao.updateSlotDetailGroup(slot)
										: vt070000dao.updateSlotDetail(slot));
								barCodeDetail = barCodes.get(0);
								barCodeDetail.setStatus(1);
								barCodeDetail.setUpdateUser(updateUser);
								if (unit == 1) {
									vt070001dao.updateStatusBarcodeDetailGroup(barCodeDetail);
								} else {
									vt070001dao.updateStatusBarcodeDetail(barCodeDetail);
								}
							} else {
								if (slot.getQrCode() != null && !"".equals(slot.getQrCode())) {
									if (currentSlot != null && currentSlot.getQrCode() != null
											&& !currentSlot.getQrCode().equals(slot.getQrCode())) {
										slot.setError(MessageFormat.format(warehouseMessage.getProperty("0700001"),
												slot.getQrCode()));
										// slot.setError("0700001#QR Code " + slot.getQrCode() + " was used or not
										// exist");
									}

									if (currentSlot != null && currentSlot.getQrCode() == null) {
										slot.setError(MessageFormat.format(warehouseMessage.getProperty("0700001"),
												slot.getQrCode()));
										// slot.setError("0700001#QR Code " + slot.getQrCode() + " was used or not
										// exist");
									}
								} else if (currentSlot != null && currentSlot.getQrCode() != null
										&& (slot.getQrCode() == null || "".equals(slot.getQrCode().trim()))) {
									slot.setError(MessageFormat.format(warehouseMessage.getProperty("0700008"),
											currentSlot.getQrCode()));
									// slot.setError("0700008#QR Code " + currentSlot.getQrCode() + " can not
									// delete");
									slot.setQrCode(currentSlot.getQrCode());
								}
							}
						}
					});
				}
			});
		}
		return warehouseDetail;
	}

	@Override
	public Object getLocationByQrCode(RequestParam requestParam, Collection<GrantedAuthority> roleList) {
		int unit = getUnit(roleList);
		requestParam.setPageNo(requestParam.getPageNo() * 20);
		List<WarehouseDetail> results = unit == 1
				? vt070000dao.getListWarehouseDetailByQrCodeGroup(requestParam.getQrCode())
				: vt070000dao.getListWarehouseDetailByQrCode(requestParam.getQrCode());
		long warehouseId = 0;
		if (results != null && !results.isEmpty()) {

			results.forEach(warehouse -> {
				List<RackDetail> racks = unit == 1
						? vt070000dao.getListRackDetailByQrCodeGroup(requestParam.getQrCode())
						: vt070000dao.getListRackDetailByQrCode(requestParam.getQrCode());
				warehouse.setRacks(racks != null ? racks : new ArrayList<RackDetail>());

				racks.forEach(rack -> {
					List<SlotDetail> slots = unit == 1
							? vt070000dao.getListSlotDetailByQrCodeGroup(requestParam.getQrCode())
							: vt070000dao.getListSlotDetailByQrCode(requestParam.getQrCode());
					rack.setSlots(slots != null ? slots : new ArrayList<SlotDetail>());
				});
			});
			warehouseId = results.get(0).getWarehouseId();
		}

		final List<WarehouseDetail> warehouses = preGetListWarehouseDetail(new RequestParam(warehouseId, 1), unit);
		return new Object() {
			@SuppressWarnings("unused")
			public RackDetail position = results != null && !results.isEmpty() ? results.get(0).getRacks().get(0)
					: null;
			@SuppressWarnings("unused")
			public WarehouseDetail warehouse = warehouses != null && !warehouses.isEmpty() ? warehouses.get(0) : null;
		};
	}

	@Override
	public ResponseEntityBase getWarehouseDetail(WarehouseRequestParam warehouseDetail,
			Collection<GrantedAuthority> roleList) {
		int unit = getUnit(roleList);
		ResponseEntityBase response = null;
		List<WarehouseRequestParam> data = new ArrayList<>();
		try {
			int totalRecords = unit == 1 ? vt070000dao.getTotalRecordGroup(warehouseDetail)
					: vt070000dao.getTotalRecord(warehouseDetail);
			data = unit == 1 ? vt070000dao.getListWareHouseDetailGroup(warehouseDetail)
					: vt070000dao.getListWareHouseDetail(warehouseDetail);
			if (!data.isEmpty()) {
				if (warehouseDetail.getPageNumber() == 0 && data.size() < warehouseDetail.getPageSize()) {
					totalRecords = data.size();
				}
				data.get(0).setTotalRecords(totalRecords);
			}
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
		}
		return response;
	}

	public String validateXss(String checkXss) {
		/*
		 * checkXss = checkXss.replace("&","&amp;"); checkXss =
		 * checkXss.replace("\"","&quot;"); checkXss = checkXss.replace("'","&#x27;");
		 * checkXss = checkXss.replace("/","&#x2F;"); checkXss =
		 * checkXss.replace(">","&gt;"); checkXss = checkXss.replace("<","&lt;");
		 */
		return checkXss;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public ResponseEntityBase insertUpdateWarehouse(WarehouseRequestParam object,
			Collection<GrantedAuthority> roleList) {
		int unit = getUnit(roleList);
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			String checkXss = object.getName();
			if (checkXss != null) {
				checkXss = validateXss(checkXss);
				if (checkXss.length() > 255) {
					checkXss = checkXss.substring(0, 255);
				}
				object.setName(checkXss);
			}
			checkXss = object.getAddress();
			if (checkXss != null) {
				checkXss = validateXss(checkXss);
				if (checkXss.length() > 255) {
					checkXss = checkXss.substring(0, 255);
				}
				object.setAddress(checkXss);
			}
			if (roleList.isEmpty()) {
				return response;
			}
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE))) {
				/** If not contain squadId */
				if (object.getWarehouseId() != null) {

					int isDelete = unit == 1 ? vt070000dao.isDeleteGroup(object.getWarehouseId())
							: vt070000dao.isDelete(object.getWarehouseId());
					if (isDelete == 1) {
						return new ResponseEntityBase(Constant.ERROR_UPDATE, null);
					}
					int rowEffectUpdateWarehouse = 0;
					WarehouseRequestParam warehouseRequestParam = unit == 1
							? vt070000dao.getWarehouseDetailByIdGroup(object.getWarehouseId())
							: vt070000dao.getWarehouseDetailById(object.getWarehouseId());
					if (object.getRowNum() != null || object.getColumnNum() != null
							|| !object.getRowNum().equalsIgnoreCase(warehouseRequestParam.getRowNum())
							|| !object.getColumnNum().equalsIgnoreCase(warehouseRequestParam.getColumnNum())
							|| !object.getHeightNum().equalsIgnoreCase(warehouseRequestParam.getHeightNum())) {

						if (Integer.parseInt(object.getRowNum()) >= Integer.parseInt(warehouseRequestParam.getRowNum())
								&& Integer.parseInt(object.getColumnNum()) >= Integer
										.parseInt(warehouseRequestParam.getColumnNum())) {
							rowEffectUpdateWarehouse = unit == 1 ? vt070000dao.updateWarehouseGroup(object)
									: vt070000dao.updateWarehouse(object);
							if (rowEffectUpdateWarehouse > 0) {
								addRackWithCondition(object, warehouseRequestParam, unit);
							}
						} else {
							int rowEffectCheckSlotUse = checkSlotActive(Long.parseLong(object.getWarehouseId()),
									Integer.parseInt(warehouseRequestParam.getRowNum()),
									Integer.parseInt(object.getRowNum()),
									Integer.parseInt(warehouseRequestParam.getColumnNum()),
									Integer.parseInt(object.getColumnNum()), unit);
							if (rowEffectCheckSlotUse == 0) {
								rowEffectUpdateWarehouse = unit == 1 ? vt070000dao.updateWarehouseGroup(object)
										: vt070000dao.updateWarehouse(object);
								if (rowEffectUpdateWarehouse > 0) {
									addRackWithCondition(object, warehouseRequestParam, unit);
								}
							} else {
								return new ResponseEntityBase(5, null);
							}
						}

					} else {
						rowEffectUpdateWarehouse = unit == 1 ? vt070000dao.updateWarehouseGroup(object)
								: vt070000dao.updateWarehouse(object);
					}
					if (rowEffectUpdateWarehouse > 0) {
						return new ResponseEntityBase(Constant.REQUEST_ACTION_UPDATE, null);
					} else {
						return new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
					}

				} else {
					if (unit == 1) {
						if (vt070000dao.checkExistsWarehouseGroup(object.getWarehouseId()) > 0) {
							return new ResponseEntityBase(Constant.STATUS_DUPLICATE_WAREHOUSE, null);
						}
					} else {
						if (vt070000dao.checkExistsWarehouse(object.getWarehouseId()) > 0) {
							return new ResponseEntityBase(Constant.STATUS_DUPLICATE_WAREHOUSE, null);
						}
					}

					object.setName(object.getName().trim());
					int rowEffectInsertWarehouse = unit == 1 ? vt070000dao.insertWarehouseGroup(object)
							: vt070000dao.insertWarehouse(object);

					if (rowEffectInsertWarehouse > 0) {
						addRack(Long.parseLong(object.getWarehouseId()), Integer.parseInt(object.getRowNum()),
								Integer.parseInt(object.getColumnNum()), Integer.parseInt(object.getHeightNum()),
								object.getCreateUser(), unit);
						return new ResponseEntityBase(Constant.REQUEST_ACTION_INSERT, null);
					} else {
						return new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
					}
				}
			}

		} catch (Exception e) {
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			response.setData(null);
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	public int addRackWithCondition(WarehouseRequestParam object, WarehouseRequestParam warehouseRequestParam, int unit)
			throws Exception {
		int addOrDeleteRackRowColumn = 0;
		if (Integer.parseInt(object.getHeightNum()) > Integer.parseInt(warehouseRequestParam.getHeightNum())) {
			int rowNum = Integer.parseInt(object.getRowNum());
			int columnNum = Integer.parseInt(object.getColumnNum());
			int heightNum = Integer.parseInt(object.getHeightNum());
			int oldHeightNum = Integer.parseInt(warehouseRequestParam.getHeightNum());
			for (int i = 1; i <= rowNum; i++) {
				for (int j = 1; j <= columnNum; j++) {
					RackDetail rackDetail = unit == 1
							? vt070000dao.getRackWithRowColumnOfWarehouseGroup(object.getWarehouseId(), i, j)
							: vt070000dao.getRackWithRowColumnOfWarehouse(object.getWarehouseId(), i, j);
					if (rackDetail != null) {
						addSlotFromOldToNew(rackDetail.getRackId(), oldHeightNum, heightNum, object.getCreateUser(),
								unit);
					} else {
						break;
					}
				}
			}
		} else if (Integer.parseInt(object.getHeightNum()) < Integer.parseInt(warehouseRequestParam.getHeightNum())) {
			int rowNum = Integer.parseInt(object.getRowNum());
			int columnNum = Integer.parseInt(object.getColumnNum());
			int oldHeightNum = Integer.parseInt(warehouseRequestParam.getHeightNum());
			int heightNum = Integer.parseInt(object.getHeightNum());
			for (int i = 1; i <= rowNum; i++) {
				for (int j = 1; j <= columnNum; j++) {
					for (int k = heightNum + 1; k <= oldHeightNum; k++) {
						RackDetail rackDetail = unit == 1
								? vt070000dao.getRackWithRowColumnOfWarehouseGroup(object.getWarehouseId(), i, j)
								: vt070000dao.getRackWithRowColumnOfWarehouse(object.getWarehouseId(), i, j);
						if (rackDetail != null) {
							deleteSlotHeightWithRowColumn(rackDetail.getRackId(), oldHeightNum, heightNum,
									warehouseRequestParam.getCreateUser(), unit);
						} else {
							break;
						}
					}
				}
			}
		}
		if (Integer.parseInt(object.getRowNum()) > Integer.parseInt(warehouseRequestParam.getRowNum())) {
			if (Integer.parseInt(object.getColumnNum()) > Integer.parseInt(warehouseRequestParam.getColumnNum())) {
				// add row + column
				addOrDeleteRackRowColumn = addRackWithRowColumn(Long.parseLong(object.getWarehouseId()),
						Integer.parseInt(warehouseRequestParam.getRowNum()), Integer.parseInt(object.getRowNum()),
						Integer.parseInt(warehouseRequestParam.getColumnNum()), Integer.parseInt(object.getColumnNum()),
						Integer.parseInt(warehouseRequestParam.getHeightNum()), Integer.parseInt(object.getHeightNum()),
						object.getCreateUser(), unit);
			} else if (Integer.parseInt(object.getColumnNum()) < Integer
					.parseInt(warehouseRequestParam.getColumnNum())) {
				// add row delete column
				addOrDeleteRackRowColumn = addRackWithRowDeleteColumn(Long.parseLong(object.getWarehouseId()),
						Integer.parseInt(warehouseRequestParam.getRowNum()), Integer.parseInt(object.getRowNum()),
						Integer.parseInt(warehouseRequestParam.getColumnNum()), Integer.parseInt(object.getColumnNum()),
						Integer.parseInt(object.getHeightNum()), object.getCreateUser(), unit);
			} else {
				// add row only
				addOrDeleteRackRowColumn = addRackWithRowDeleteColumn(Long.parseLong(object.getWarehouseId()),
						Integer.parseInt(warehouseRequestParam.getRowNum()), Integer.parseInt(object.getRowNum()),
						Integer.parseInt(warehouseRequestParam.getColumnNum()), Integer.parseInt(object.getColumnNum()),
						Integer.parseInt(object.getHeightNum()), object.getCreateUser(), unit);
			}
		} else if (Integer.parseInt(object.getRowNum()) < Integer.parseInt(warehouseRequestParam.getRowNum())) {
			if (Integer.parseInt(object.getColumnNum()) > Integer.parseInt(warehouseRequestParam.getColumnNum())) {
				// delete row add column
				addOrDeleteRackRowColumn = addRackWithColumnDeleteRow(Long.parseLong(object.getWarehouseId()),
						Integer.parseInt(warehouseRequestParam.getRowNum()), Integer.parseInt(object.getRowNum()),
						Integer.parseInt(warehouseRequestParam.getColumnNum()), Integer.parseInt(object.getColumnNum()),
						Integer.parseInt(object.getHeightNum()), object.getCreateUser(), unit);
			} else {
				// delete row, column
				addOrDeleteRackRowColumn = deleteRackWithRowColumn(Long.parseLong(object.getWarehouseId()),
						Integer.parseInt(warehouseRequestParam.getRowNum()), Integer.parseInt(object.getRowNum()),
						Integer.parseInt(warehouseRequestParam.getColumnNum()), Integer.parseInt(object.getColumnNum()),
						object.getCreateUser(), unit);
			}
		} else {
			if (Integer.parseInt(object.getColumnNum()) > Integer.parseInt(warehouseRequestParam.getColumnNum())) {
				// add column only
				addOrDeleteRackRowColumn = addRackWithColumnDeleteRow(Long.parseLong(object.getWarehouseId()),
						Integer.parseInt(warehouseRequestParam.getRowNum()), Integer.parseInt(object.getRowNum()),
						Integer.parseInt(warehouseRequestParam.getColumnNum()), Integer.parseInt(object.getColumnNum()),
						Integer.parseInt(object.getHeightNum()), object.getCreateUser(), unit);
			} else if (Integer.parseInt(object.getColumnNum()) < Integer
					.parseInt(warehouseRequestParam.getColumnNum())) {
				// delte column only
				addOrDeleteRackRowColumn = deleteRackWithRowColumn(Long.parseLong(object.getWarehouseId()),
						Integer.parseInt(warehouseRequestParam.getRowNum()), Integer.parseInt(object.getRowNum()),
						Integer.parseInt(warehouseRequestParam.getColumnNum()), Integer.parseInt(object.getColumnNum()),
						object.getCreateUser(), unit);
			}
		}

		return addOrDeleteRackRowColumn;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "transactionManager")
	public int checkSlotActive(long warehouseId, int oldRowNum, int rowNum, int oldColumnNum, int columnNum, int unit) {
		try {
			List<SlotDetail> lstSlot = unit == 1
					? vt070000dao.getListSlotWithRowColumnGroup(warehouseId, oldRowNum, rowNum, oldColumnNum, columnNum)
					: vt070000dao.getListSlotWithRowColumn(warehouseId, oldRowNum, rowNum, oldColumnNum, columnNum);
			if (lstSlot == null || lstSlot.isEmpty()) {
				return 0;
			} else {
				return 1;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "transactionManager")
	public int addRackWithRowColumn(long warehouseId, int oldRowNum, int rowNum, int oldColumnNum, int columnNum,
			int oldHeight, int height, String addUser, int unit) {
		int rowEffectInsertRack = 0;
		try {
			for (int i = 1; i <= oldRowNum; i++) {
				for (int j = oldColumnNum + 1; j <= columnNum; j++) {
					RackDetail rackDetail = new RackDetail(i, warehouseId, j, -1, false);
					rackDetail.setCreateUser(addUser);
					rackDetail.setUpdateUser(addUser);
					rowEffectInsertRack = unit == 1 ? vt070000dao.insertRackGroup(rackDetail)
							: vt070000dao.insertRack(rackDetail);
					if (rowEffectInsertRack > 0) {
						addSlot(rackDetail.getRackId(), height, addUser, unit);
					}
				}
			}
			for (int i = oldRowNum + 1; i <= rowNum; i++) {
				for (int j = 1; j <= columnNum; j++) {
					RackDetail rackDetail = new RackDetail(i, warehouseId, j, -1, false);
					rackDetail.setCreateUser(addUser);
					rackDetail.setUpdateUser(addUser);
					rowEffectInsertRack = unit == 1 ? vt070000dao.insertRackGroup(rackDetail)
							: vt070000dao.insertRack(rackDetail);
					if (rowEffectInsertRack > 0) {
						addSlot(rackDetail.getRackId(), height, addUser, unit);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
		return rowEffectInsertRack;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "transactionManager")
	public int addRackWithRowDeleteColumn(long warehouseId, int oldRowNum, int rowNum, int oldColumnNum, int columnNum,
			int height, String addUser, int unit) {
		int rowEffectInsertRack = 0;
		try {
			for (int i = oldRowNum + 1; i <= rowNum; i++) {
				for (int j = 1; j <= columnNum; j++) {
					RackDetail columnRackDetail = new RackDetail(i, warehouseId, j, -1, false);
					columnRackDetail.setCreateUser(addUser);
					columnRackDetail.setUpdateUser(addUser);
					rowEffectInsertRack = unit == 1 ? vt070000dao.insertRackGroup(columnRackDetail)
							: vt070000dao.insertRack(columnRackDetail);
					if (rowEffectInsertRack > 0) {
						addSlot(columnRackDetail.getRackId(), height, addUser, unit);
					}
				}
			}
			for (int i = 1; i <= oldRowNum; i++) {
				deleteRackWithRowColumn(warehouseId, oldRowNum, rowNum, oldColumnNum, columnNum, addUser, unit);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
		return rowEffectInsertRack;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "transactionManager")
	public int addRackWithColumnDeleteRow(long warehouseId, int oldRowNum, int rowNum, int oldColumnNum, int columnNum,
			int height, String addUser, int unit) {
		int rowEffectInsertRack = 0;
		try {
			for (int i = 1; i <= rowNum; i++) {
				for (int j = oldColumnNum + 1; j <= columnNum; j++) {
					RackDetail rowRackDetail = new RackDetail(i, warehouseId, j, -1, false);
					rowRackDetail.setCreateUser(addUser);
					rowRackDetail.setUpdateUser(addUser);
					rowEffectInsertRack = unit == 1 ? vt070000dao.insertRackGroup(rowRackDetail)
							: vt070000dao.insertRack(rowRackDetail);
					if (rowEffectInsertRack > 0) {
						addSlot(rowRackDetail.getRackId(), height, addUser, unit);
					}
				}
			}
			for (int i = 1; i <= oldRowNum; i++) {
				deleteRackWithRowColumn(warehouseId, oldRowNum, rowNum, oldColumnNum, columnNum, addUser, unit);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
		return rowEffectInsertRack;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "transactionManager")
	public int deleteRackWithRowColumn(long warehouseId, int oldRowNum, int rowNum, int oldColumnNum, int columnNum,
			String addUser, int unit) {
		int rowEffectDeleteRack = 0;
		try {
			if (oldColumnNum > columnNum) {
				for (int i = columnNum + 1; i <= oldColumnNum; i++) {
					rowEffectDeleteRack = unit == 1 ? vt070000dao.deleteRackWithRowColumnGroup(warehouseId, -1, i)
							: vt070000dao.deleteRackWithRowColumn(warehouseId, -1, i);
					if (unit == 1) {
						vt070000dao.deleteSlotWithRowColumnGroup(warehouseId, -1, i);
					} else {
						vt070000dao.deleteSlotWithRowColumn(warehouseId, -1, i);
					}
				}
			}
			if (oldRowNum > rowNum) {
				for (int i = rowNum + 1; i <= oldRowNum; i++) {
					rowEffectDeleteRack = unit == 1 ? vt070000dao.deleteRackWithRowColumnGroup(warehouseId, i, -1)
							: vt070000dao.deleteRackWithRowColumn(warehouseId, i, -1);
					if (unit == 1) {
						vt070000dao.deleteSlotWithRowColumnGroup(warehouseId, i, -1);
					} else {
						vt070000dao.deleteSlotWithRowColumn(warehouseId, i, -1);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
		return rowEffectDeleteRack;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "transactionManager")
	public int deleteSlotHeightWithRowColumn(long rackId, int oldHeightNum, int heightNum, String addUser, int unit) {
		int rowEffectDeleteSlot = 0;
		try {
			for (int i = heightNum + 1; i <= oldHeightNum; i++) {
				rowEffectDeleteSlot = unit == 1 ? vt070000dao.deleteSlotHeightWithRackIdGroup(rackId, i)
						: vt070000dao.deleteSlotHeightWithRackId(rackId, i);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
		return rowEffectDeleteSlot;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "transactionManager")
	public int addRack(long warehouseId, int rowNum, int columnNum, int height, String addUser, int unit) {
		int rowEffectInsertRack = 0;
		for (int i = 1; i <= rowNum; i++) {
			for (int j = 1; j <= columnNum; j++) {
				RackDetail rackDetail = new RackDetail(i, warehouseId, j, -1, false);
				rackDetail.setCreateUser(addUser);
				rackDetail.setUpdateUser(addUser);
				rowEffectInsertRack = unit == 1 ? vt070000dao.insertRackGroup(rackDetail)
						: vt070000dao.insertRack(rackDetail);
				if (rowEffectInsertRack > 0) {
					addSlot(rackDetail.getRackId(), height, addUser, unit);
				}
			}
		}
		return rowEffectInsertRack;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "transactionManager")
	public int addSlot(long rackId, int height, String addUser, int unit) {
		int rowEffectInsertSlot = 0;
		for (int i = 1; i <= height; i++) {
			SlotDetail slotDetail = new SlotDetail(rackId, i, null, 0, false);
			slotDetail.setCreateUser(addUser);
			slotDetail.setUpdateUser(addUser);
			rowEffectInsertSlot = unit == 1 ? vt070000dao.insertSlotGroup(slotDetail)
					: vt070000dao.insertSlot(slotDetail);
		}
		return rowEffectInsertSlot;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "transactionManager")
	public int addRemainSlot(long rackId, int oldHeight, int height, String addUser, int unit) {
		int rowEffectInsertSlot = 0;
		for (int i = oldHeight + 1; i <= height; i++) {
			SlotDetail slotDetail = new SlotDetail(rackId, i, null, 0, false);
			slotDetail.setCreateUser(addUser);
			slotDetail.setUpdateUser(addUser);
			rowEffectInsertSlot = unit == 1 ? vt070000dao.insertSlotGroup(slotDetail)
					: vt070000dao.insertSlot(slotDetail);
		}
		return rowEffectInsertSlot;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "transactionManager")
	public int addSlotFromOldToNew(long rackId, int oldHeight, int height, String addUser, int unit) {
		int rowEffectInsertSlot = 0;
		for (int i = oldHeight + 1; i <= height; i++) {
			SlotDetail slotDetail = new SlotDetail(rackId, i, null, 0, false);
			slotDetail.setCreateUser(addUser);
			slotDetail.setUpdateUser(addUser);
			rowEffectInsertSlot = unit == 1 ? vt070000dao.insertSlotGroup(slotDetail)
					: vt070000dao.insertSlot(slotDetail);
		}
		return rowEffectInsertSlot;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public ResponseEntityBase deleteWarehouse(WarehouseRequestParam object, Collection<GrantedAuthority> roleList) {
		int unit = getUnit(roleList);
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			if (roleList.isEmpty()) {
				return response;
			}
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE))) {
				/** If not contain squadId */
				if (object.getWarehouseId() != null) {
					int isDelete = unit == 1 ? vt070000dao.isDeleteGroup(object.getWarehouseId())
							: vt070000dao.isDelete(object.getWarehouseId());
					if (isDelete == 1) {
						return new ResponseEntityBase(Constant.ERROR_UPDATE, null);
					}
					object.setDelFlag(true);
					WarehouseRequestParam warehouseRequestParam = unit == 1
							? vt070000dao.getWarehouseDetailByIdGroup(object.getWarehouseId())
							: vt070000dao.getWarehouseDetailById(object.getWarehouseId());
					int rowEffectCheckSlotUse = checkSlotActive(Long.parseLong(object.getWarehouseId()),
							Integer.parseInt(warehouseRequestParam.getRowNum()), 0,
							Integer.parseInt(warehouseRequestParam.getColumnNum()), 0, unit);
					if (rowEffectCheckSlotUse == 0) {
						int rowEffect = unit == 1 ? vt070000dao.deleteWarehouseGroup(object)
								: vt070000dao.deleteWarehouse(object);
						if (rowEffect > 0) {
							if (unit == 1) {
								vt070000dao.deleteRackAfterChangeWarehouseGroup(object.getWarehouseId());
								vt070000dao.deleteSlotAfterChangeWarehouseGroup(object.getWarehouseId());
							} else {
								vt070000dao.deleteRackAfterChangeWarehouse(object.getWarehouseId());
								vt070000dao.deleteSlotAfterChangeWarehouse(object.getWarehouseId());
							}
							return new ResponseEntityBase(Constant.REQUEST_ACTION_DELETE, null);
						}
						return new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
					}
					return new ResponseEntityBase(5, null);
				}
				return new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			}

		} catch (Exception e) {
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			response.setData(null);
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	private int compareUtil(RackDetail a, RackDetail b) {
		if (a.getRow() == b.getRow()) {
			if (a.getColumn() < b.getColumn()) {
				return -1;
			} else {
				return 1;
			}
		} else if (a.getRow() > b.getRow()) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public ResponseEntityBase getDiagram(String warehouseId, Collection<GrantedAuthority> roleList) {

		int unit = getUnit(roleList);
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<List<WarehouseDiagramParam>> warehouseDiagrams = new ArrayList<>();
		try {
			if (warehouseId == null || warehouseId.isEmpty()) {
				response.setStatus(Constant.RESPONSE_STATUS_ERROR);
				response.setData(null);
				return response;
			}
			if (roleList.isEmpty()) {
				return response;
			}
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE))) {
				/** If not contain squadId */

				WarehouseRequestParam warehouseDetail = unit == 1 ? vt070000dao.getWarehouseDetailByIdGroup(warehouseId)
						: vt070000dao.getWarehouseDetailById(warehouseId);

				List<RackDetail> listRackDetail = unit == 1
						? vt070000dao.getListRackDetailByWarehouseIdGroup(Long.parseLong(warehouseId))
						: vt070000dao.getListRackDetailByWarehouseId(Long.parseLong(warehouseId));
				List<WarehouseDiagramParam> lstColumn = new ArrayList<>();
				int countOfCol = 0;
				// fix Draw Right to Left
				Collections.sort(listRackDetail, new Comparator<RackDetail>() {
					public int compare(RackDetail a, RackDetail b) {
						return compareUtil(a, b);
					}
				});
				for (RackDetail rackDetail : listRackDetail) {
					int count = unit == 1 ? vt070000dao.countSlotUseGroup(rackDetail.getRackId())
							: vt070000dao.countSlotUse(rackDetail.getRackId());
					WarehouseDiagramParam warehouseDiagramParam = new WarehouseDiagramParam(rackDetail.getType(),
							rackDetail.getRow(), rackDetail.getColumn(), count + "/" + warehouseDetail.getHeightNum());
					lstColumn.add(warehouseDiagramParam);
					countOfCol++;
					if (countOfCol == Integer.parseInt(warehouseDetail.getColumnNum())) {
						warehouseDiagrams.add(lstColumn);
						lstColumn = new ArrayList<>();
						countOfCol = 0;
					}
				}
			}
			response.setData(warehouseDiagrams);
		} catch (Exception e) {
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			response.setData(null);
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public ResponseEntityBase getListSlotOfWarehouse(WarehouseRequestParam warehouseRequestParam,
			Collection<GrantedAuthority> roleList) {
		int unit = getUnit(roleList);
		ResponseEntityBase response = null;
		List<WarehouseRackSlot> data = new ArrayList<>();
		try {
			int totalRecords = unit == 1
					? vt070000dao.getTotalRecordWarehouseSlotGroup(warehouseRequestParam.getWarehouseId())
					: vt070000dao.getTotalRecordWarehouseSlot(warehouseRequestParam.getWarehouseId());
			data = unit == 1 ? vt070000dao.getListSlotUseInWarehouseGroup(warehouseRequestParam)
					: vt070000dao.getListSlotUseInWarehouse(warehouseRequestParam);
			int countActive = unit == 1 ? vt070000dao.countSlotActiveGroup(warehouseRequestParam.getWarehouseId())
					: vt070000dao.countSlotActive(warehouseRequestParam.getWarehouseId());
			data.get(0).setTotalActive(countActive);
			if (!data.isEmpty()) {
				if (warehouseRequestParam.getPageNumber() == 0 && data.size() < warehouseRequestParam.getPageSize()) {
					totalRecords = data.size();
				}
				data.get(0).setTotalRecords(totalRecords);
			}
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
		}
		return response;
	}

	@Override
	public ResponseEntityBase getAllListWarehouseDetail(WarehouseRequestParam warehouseRequestParam,
			Collection<GrantedAuthority> roleList) {

		ResponseEntityBase response = null;
		List<WarehouseRequestParam> data = new ArrayList<>();
		try {
			data = getUnit(roleList) == 1 ? vt070000dao.getAllListWarehouseDetailGroup(warehouseRequestParam)
					: vt070000dao.getAllListWarehouseDetail(warehouseRequestParam);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
		}
		return response;
	}

	@Override
	public File createExcelOutputExcel(WarehouseRequestParam warehouseRequestParam,
			Collection<GrantedAuthority> listRole) {
		int unit = getUnit(listRole);
		File file = null;
		String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT70000");
		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE))
					|| (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE_REPORT)))) {
				List<WarehouseRackSlot> warehouseRackSlot = unit == 1
						? vt070000dao.getFullListSlotUseInWarehouseGroup(warehouseRequestParam)
						: vt070000dao.getFullListSlotUseInWarehouse(warehouseRequestParam);
				if (warehouseRackSlot == null || warehouseRackSlot.isEmpty()) {
					warehouseRackSlot = new ArrayList<>();
					warehouseRackSlot.add(new WarehouseRackSlot());
					warehouseRackSlot.get(0).setCreateUser(warehouseRequestParam.getCreateUser());
				} else {
					int countActive = unit == 1
							? vt070000dao.countSlotActiveGroup(warehouseRequestParam.getWarehouseId())
							: vt070000dao.countSlotActive(warehouseRequestParam.getWarehouseId());
					warehouseRackSlot.get(0).setTotalActive(countActive);
					warehouseRackSlot.get(0).setCreateUser(warehouseRequestParam.getCreateUser());
				}
				file = writeExcel(warehouseRackSlot, excelFilePath);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return file;
	}

	public File writeExcel(List<WarehouseRackSlot> warehouseRackSlots, String excelFilePath) throws IOException {

		File outFile = null;
		File file = null;

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String fileName = warehouseRackSlots.get(0).getCreateUser() + "_" + cal.get(Calendar.YEAR) + "_"
				+ (cal.get(Calendar.MONTH) + 1) + "_" + cal.get(Calendar.DAY_OF_MONTH) + "_"
				+ cal.get(Calendar.HOUR_OF_DAY) + "h" + cal.get(Calendar.MINUTE) + "m" + cal.get(Calendar.SECOND) + "s"
				+ cal.get(Calendar.MILLISECOND) + "ms.xlsx";
		try {
			Resource resource = new ClassPathResource(excelFilePath);
			file = resource.getFile();
			String filePath = file.getAbsolutePath();
			outFile = new File(filePath.split("templateExcel")[0] + "saveExcel\\VT07\\" + fileName);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		try (FileInputStream inputStream = new FileInputStream(file)) {
			try (SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(inputStream), 100)) {
				SXSSFSheet sheet = workbook.getSheetAt(0);
				CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
				CellStyle cellStyleHeader = sheet.getWorkbook().createCellStyle();
				CellStyle cellStyleInfo = sheet.getWorkbook().createCellStyle();

				Font fontContent = sheet.getWorkbook().createFont();
				fontContent.setFontName("Times New Roman");
				fontContent.setFontHeightInPoints((short) 11);

				cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
				cellStyle.setFillPattern(FillPatternType.NO_FILL);
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);
				cellStyle.setFont(fontContent);
				cellStyleInfo.setFont(fontContent);

				Font fontHeader = sheet.getWorkbook().createFont();
				fontHeader.setBold(true);
				fontHeader.setFontName("Times New Roman");
				fontHeader.setFontHeightInPoints((short) 11);
				cellStyleHeader.setFont(fontHeader);
				cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);
				cellStyleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
				// Print header
				int rowHeader = 1;
				writeBookHeader(warehouseRackSlots, sheet, rowHeader, cellStyleHeader, cellStyleInfo);
				if (warehouseRackSlots.get(0).getSlotId() != null && !warehouseRackSlots.get(0).getSlotId().isEmpty()) {
					// number header of row in excel template
					int rowCount = 2;
					// fill data
					int rowNumber = 1;
					for (WarehouseRackSlot item : warehouseRackSlots) {
						writeBook(item, sheet, ++rowCount, cellStyle, rowNumber++);
					}
				}
				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				}
			}
		}

		return outFile;
	}

	private void writeBook(WarehouseRackSlot warehouseRackSlot, Sheet sheet, int rowNum, CellStyle cellStyle,
			int rowNumber) {
		Row row = sheet.createRow(rowNum);

		// No.
		Cell cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(rowNumber);

		// Warehouse Name
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(warehouseRackSlot.getWarehouseName());

		// Row
		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(warehouseRackSlot.getRow());

		// Column
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(warehouseRackSlot.getColumn());

		// Position
		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(warehouseRackSlot.getPosition());

		// Qr Code
		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(warehouseRackSlot.getQrCode());

		// Status
		cell = row.createCell(6);
		cell.setCellStyle(cellStyle);
		if (Integer.parseInt(warehouseRackSlot.getStatus()) == 0) {
			cell.setCellValue(headerTitle.getProperty("NOT_USED"));
		} else {
			cell.setCellValue(headerTitle.getProperty("USED"));
		}

	}

	private void writeBookHeader(List<WarehouseRackSlot> warehouseRackSlots, Sheet sheet, int rowNum,
			CellStyle cellStyleHeader, CellStyle cellStyleInfo) {
		Row row = sheet.createRow(rowNum);

		Cell cell = row.createCell(2);
		cell.setCellStyle(cellStyleInfo);
		if (warehouseRackSlots.get(0).getSlotId() == null || warehouseRackSlots.get(0).getSlotId().isEmpty()) {
			cell.setCellValue(headerTitle.getProperty("TOTAL_RACK"));
		} else {
			cell.setCellValue(
					MessageFormat.format(headerTitle.getProperty("TOTAL_RACK_PARAM"), warehouseRackSlots.size()));
		}

		cell = row.createCell(4);
		cell.setCellStyle(cellStyleInfo);
		if (warehouseRackSlots.get(0).getSlotId() == null || warehouseRackSlots.get(0).getSlotId().isEmpty()) {
			cell.setCellValue(headerTitle.getProperty("USED0"));
		} else {
			cell.setCellValue(MessageFormat.format(headerTitle.getProperty("USED_PARAM"),
					warehouseRackSlots.get(0).getTotalActive()));
		}

		cell = row.createCell(6);
		cell.setCellStyle(cellStyleInfo);

		if (warehouseRackSlots.get(0).getSlotId() == null || warehouseRackSlots.get(0).getSlotId().isEmpty()) {
			cell.setCellValue(headerTitle.getProperty("NOT_USED0"));
		} else {
			cell.setCellValue(MessageFormat.format(headerTitle.getProperty("NOT_USED_PARAM"),
					(warehouseRackSlots.size() - warehouseRackSlots.get(0).getTotalActive())));
		}

		Row rowHeader = sheet.createRow(++rowNum);

		Cell cellHeader = rowHeader.createCell(0);
		cellHeader.setCellStyle(cellStyleHeader);
		cellHeader.setCellValue("STT");

		cellHeader = rowHeader.createCell(1);
		cellHeader.setCellStyle(cellStyleHeader);
		cellHeader.setCellValue("Kho");

		cellHeader = rowHeader.createCell(2);
		cellHeader.setCellStyle(cellStyleHeader);
		cellHeader.setCellValue(headerTitle.getProperty("ROW"));

		cellHeader = rowHeader.createCell(3);
		cellHeader.setCellStyle(cellStyleHeader);
		cellHeader.setCellValue(headerTitle.getProperty("COLUMN"));

		cellHeader = rowHeader.createCell(4);
		cellHeader.setCellStyle(cellStyleHeader);
		cellHeader.setCellValue(headerTitle.getProperty("RACK"));

		cellHeader = rowHeader.createCell(5);
		cellHeader.setCellStyle(cellStyleHeader);
		cellHeader.setCellValue("QR Code");

		cellHeader = rowHeader.createCell(6);
		cellHeader.setCellStyle(cellStyleHeader);
		cellHeader.setCellValue(headerTitle.getProperty("CUR_STATUS"));

	}

	@Override
	public ResponseEntityBase findRack(RackDetail object, Collection<GrantedAuthority> listRole) {
		ResponseEntityBase response = null;
		List<RackDetail> data = new ArrayList<>();
		try {
			data = getUnit(listRole) == 1 ? vt070000dao.findRackGroup(object) : vt070000dao.findRack(object);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, data);
		}
		return response;
	}

	@Override
	public ResponseEntityBase updatePrintTimeRack(RackDetail listRack, Collection<GrantedAuthority> listRole) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			int rowEffect = 0;
			List<Integer> arrInt = new ArrayList<>();
			String[] arr = listRack.getWarehouseName().split(",");
			for (int i = 0; i < arr.length; i++) {
				arrInt.add(Integer.parseInt(arr[i]));
			}

			rowEffect += getUnit(listRole) == 1 ? vt070000dao.updatePrintTimeRackGroup(arrInt)
					: vt070000dao.updatePrintTimeRack(arrInt);

			if (rowEffect > 0) {
				return new ResponseEntityBase(Constant.REQUEST_ACTION_INSERT, null);
			} else {
				return new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public Object getListMainWarehouseDetail(RequestParam requestParam, Collection<GrantedAuthority> listRole) {
		requestParam.setPageNo(requestParam.getPageNo() * 20);
		if (requestParam.getWarehouseName() != null && !"".equals(requestParam.getWarehouseName())) {
			requestParam.setWarehouseName(requestParam.getWarehouseName().trim());
		}
		List<WarehouseDetail> results = preGetListMainWarehouseDetail(requestParam, getUnit(listRole));
		return new Object() {
			@SuppressWarnings("unused")
			public List<WarehouseDetail> warehouses = results != null ? results : new ArrayList<>();
			@SuppressWarnings("unused")
			public int total = getUnit(listRole) == 1 ? vt070000dao.getNumberOfMainWarehouseGroup(requestParam)
					: vt070000dao.getNumberOfMainWarehouse(requestParam);
		};
	}

	private List<WarehouseDetail> preGetListMainWarehouseDetail(RequestParam requestParam, int unit) {
		List<WarehouseDetail> results = unit == 1 ? vt070000dao.getListMainWarehouseDetailGroup(requestParam)
				: vt070000dao.getListMainWarehouseDetail(requestParam);
		if (results != null && requestParam.getLevel() > 0) {
			results.forEach(warehouse -> {
				List<RackDetail> racks = unit == 1
						? vt070000dao.getListRackDetailByWarehouseIdGroup(warehouse.getWarehouseId())
						: vt070000dao.getListRackDetailByWarehouseId(warehouse.getWarehouseId());
				warehouse.setRacks(racks != null ? racks : new ArrayList<RackDetail>());
				if (requestParam.getLevel() > 1) {
					racks.forEach(rack -> {
						List<SlotDetail> slots = unit == 1
								? vt070000dao.getListSlotDetailByRackIdGroup(rack.getRackId())
								: vt070000dao.getListSlotDetailByRackId(rack.getRackId());
						rack.setSlots(slots != null ? slots : new ArrayList<SlotDetail>());
					});
				}
			});
		}
		return results;
	}

	/**
	 * Cập nhật trạng thái đồng bộ / không đồng bộ cho các bảng table tương ứng theo
	 * search type
	 */
	@Override
	public ResponseEntityBase updateStatusBySearchType(UpdateStatusRequest updateSttRequest) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			vt070000dao.updateStatusBySearchType(updateSttRequest);
			if (updateSttRequest.getSearchType() == 3) {
				updateStatusPackageByProjectId(updateSttRequest.getId(), updateSttRequest.getIsSynchrony());
			}
			if (updateSttRequest.getSearchType() == 4) {
				updateStatusContractByPackageId(updateSttRequest.getId(), updateSttRequest.getIsSynchrony());
			}
			if (updateSttRequest.getSearchType() == 5) {
				updateStatusConstructionByContractId(updateSttRequest.getId(), updateSttRequest.getIsSynchrony());
			}
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	private void updateStatusPackageByProjectId(int projectId,int isSynchrony) {
		ProjectTree projectTree = vt070000dao.getProjectTree(projectId);
		if (CollectionUtils.isNotEmpty(projectTree.getPackages())) {
			String packageIdList = projectTree.getPackages().stream().map(e -> String.valueOf(e.getPackageId())).collect(Collectors.joining(","));
			String contractIdList = projectTree.getPackages().stream().filter(f->CollectionUtils.isNotEmpty(f.getContracts())).map(e -> e.getContracts().stream().filter(f->CollectionUtils.isNotEmpty(f.getConstructions())).map(c -> String.valueOf(c.getContractId())).collect(Collectors.joining(","))).collect(Collectors.joining(","));
		    String constructionIdList = projectTree.getPackages().stream().filter(f->CollectionUtils.isNotEmpty(f.getContracts())).map(p -> p.getContracts().stream().filter(f->CollectionUtils.isNotEmpty(f.getConstructions())).map(e -> e.getConstructions().stream().map(c -> String.valueOf(c.getConstructionId())).collect(Collectors.joining(","))).collect(Collectors.joining(","))).collect(Collectors.joining(","));

			if (StringUtils.isNotEmpty(packageIdList)) {
				vt070000dao.updateStatusPackageById(packageIdList, isSynchrony);
			}
			if (StringUtils.isNotEmpty(contractIdList)) {
				vt070000dao.updateStatusContractById(contractIdList, isSynchrony);
			}
			if (StringUtils.isNotEmpty(constructionIdList)) {
				vt070000dao.updateStatusConstructionById(constructionIdList, isSynchrony);
			}
		}
	}
	
	private void updateStatusContractByPackageId(int packageId, int isSynchrony) {
		PackageTree packageTree = vt070000dao.getPackageTree(packageId);
		if (CollectionUtils.isNotEmpty(packageTree.getContracts())) {
			String contractIdList = packageTree.getContracts().stream().filter(f->CollectionUtils.isNotEmpty(f.getConstructions())).map(e -> String.valueOf(e.getContractId())).collect(Collectors.joining(","));
			String constructionIdList = packageTree.getContracts().stream().filter(f->CollectionUtils.isNotEmpty(f.getConstructions())).map(e -> e.getConstructions().stream().map(c -> String.valueOf(c.getConstructionId())).collect(Collectors.joining(","))).collect(Collectors.joining(","));
			
			if (StringUtils.isNotEmpty(contractIdList)) {
				vt070000dao.updateStatusContractById(contractIdList, isSynchrony);
			}
			if (StringUtils.isNotEmpty(constructionIdList)) {
				vt070000dao.updateStatusConstructionById(constructionIdList, isSynchrony);
			}
		}
	}
	
	private void updateStatusConstructionByContractId(int contractId, int isSynchrony) {
		ContractTree contractTree = vt070000dao.getContractTree(contractId);
		if (CollectionUtils.isNotEmpty(contractTree.getConstructions())) {
			String constructionIdList = contractTree.getConstructions().stream().map(e -> String.valueOf(e.getConstructionId())).collect(Collectors.joining(","));
			if (StringUtils.isNotEmpty(constructionIdList)) {
				vt070000dao.updateStatusConstructionById(constructionIdList, isSynchrony);
			}
		}
	}
	
	@Override
	public List<PaymentSummaryDetailSearch> getPaymentSummaryByDocName(DocumentSearchRequest documentSearchRequest,
			Collection<GrantedAuthority> roleList) {
		return vt070000dao.getPaymentSummaryByDocName(documentSearchRequest);
	}

	@Override
	public List<VoucherDetailSearch> getVoucherDocByDocNameAndType(DocumentSearchRequest documentSearchRequest,
			Collection<GrantedAuthority> roleList) {
		return vt070000dao.getVoucherByDocNameAndType(documentSearchRequest, 6);
	}

	@Override
	public List<VoucherDetailSearch> getVoucherByDocNameAndType(DocumentSearchRequest documentSearchRequest,
			Collection<GrantedAuthority> roleList) {
		return vt070000dao.getVoucherByDocNameAndType(documentSearchRequest, 4);
	}

	@Override
	public List<OfficialDispatchDetailSearch> getOfficialDispatchByIncomingDocName(
			DocumentSearchRequest documentSearchRequest, Collection<GrantedAuthority> roleList) {
		return vt070000dao.getOfficialDispatchByIncomingDocName(documentSearchRequest);
	}

	@Override
	public List<OfficialDispatchDetailSearch> getOfficialDispatchBytravelsDocName(
			DocumentSearchRequest documentSearchRequest, Collection<GrantedAuthority> roleList) {
		return vt070000dao.getOfficialDispatchBytravelsDocName(documentSearchRequest);
	}

	@Override
	public List<ContractDetailSearch> getContractByDocName(DocumentSearchRequest documentSearchRequest,
			Collection<GrantedAuthority> roleList) {
		return vt070000dao.getContractByDocName(documentSearchRequest);
	}

	@Override
	public List<PackageDetailSearch> getPackageByDocName(DocumentSearchRequest documentSearchRequest,
			Collection<GrantedAuthority> roleList) {
		return vt070000dao.getPackageByDocName(documentSearchRequest);
	}

	@Override
	public List<ProjectDetailSearch> getProjectByDocName(DocumentSearchRequest documentSearchRequest,
			Collection<GrantedAuthority> roleList) {
		return vt070000dao.getProjectByDocName(documentSearchRequest);
	}

	@Override
	public List<FolderDetailSearch> getFolderByName(DocumentSearchRequest documentSearchRequest,
			Collection<GrantedAuthority> roleList) {
		return vt070000dao.getFolderByDocName(documentSearchRequest);
	}

	@Override
	public List<TinBoxDetail> getTinBoxByName(DocumentSearchRequest documentSearchRequest,
			Collection<GrantedAuthority> roleList) {
		return vt070000dao.getTinBoxByName(documentSearchRequest);
	}

	@Override
	public List<ConstructionDetailSearch> getConstructionByDocName(DocumentSearchRequest documentSearchRequest,
			Collection<GrantedAuthority> roleList) {
		return vt070000dao.getConstructionByDocName(documentSearchRequest);
	}

	private final int[] TYPE_VALUES = { 1, 2, 3, 4 };

	@Override
	public List<DocumentDetailSearch> getDocDetailByIdAndType(long projectId, long type, long folderId) throws Exception {
		boolean contains = IntStream.of(TYPE_VALUES).anyMatch(x -> x == type);
		if (!contains) {
			throw new Exception("invalid type");
		}
		return vt070000dao.getDocDetailByIdAndType(projectId, type,folderId);
	}
}
