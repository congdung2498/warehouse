package com.viettel.vtnet360.stationery.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.vtnet360.stationery.entity.CheckVote;
import com.viettel.vtnet360.stationery.entity.StationeryEmployee;
import com.viettel.vtnet360.stationery.entity.StationeryNameAllResponseTemplate;
import com.viettel.vtnet360.stationery.request.dao.StationeryDAO;
import com.viettel.vtnet360.stationery.request.dao.StationeryReportDAO;
import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntitySmsVsNotify;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Unit;
import com.viettel.vtnet360.vt05.vt050000.dao.VT050000DAO;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000AdditionalInfoToSendNotify;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000IssuesStationery;
import com.viettel.vtnet360.vt05.vt050000.service.VT050000Service;
import com.viettel.vtnet360.vt05.vt050003.dao.VT050003DAO;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003InfoToFindHcdv;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003IssuesStationeryItemsToInsertEmployee;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003RequestParam;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003RequestParamEmployee;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003StationeryParam;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013DataGetAll;

@Service
public class StationeryService {
	private final Logger logger = Logger.getLogger(this.getClass());

	public static final String MEDIATYPE_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String HEADER_CACHE_CONTROL = "must-revalidate, post-check=0, pre-check=0";
	private static final String NOT_FOUND = "notfound";

	@Autowired
	private Properties messages;

	@Autowired
	private StationeryDAO stationeryDao;

	@Autowired
  private StationeryReportDAO stationeryReportDAO;
	
	@Autowired
	private VT050000Service vt050000Service;

	@Autowired
	private VT050003DAO vt050003DAO;

	@Autowired
	private Notification notification;

	@Autowired
	private Properties notifyMessage;

	@Autowired
	private VT050000DAO vt050000DAO;

	@Autowired
	private Sms sms;
	
	@Autowired
  private Properties linkTemplateExcel;
	
	
	@Transactional
	public int validStationery(String issueStationeryId) {
	  return stationeryDao.checkValidStationery(issueStationeryId);
	}
	@Transactional
	public int validStationeryReport(String issueStationeryId) {
	  return stationeryDao.checkValidStationeryReport(issueStationeryId);
	}
	@Transactional
	public int checkVoteHCDVValidStationeryReport(String issueStationeryId) {
	  return stationeryDao.checkVoteHCDVValidStationeryReport(issueStationeryId);
	}
	@Transactional
	public int checkVoteVPTCTValidStationeryReport(String issueStationeryId) {
	  return stationeryDao.checkVoteVPTCTValidStationeryReport(issueStationeryId);
	}
	@Transactional
	public StationeryStaff getStationeryStaffByUser(String username) {
	  return stationeryDao.getStationeryStaffByUser(username);
	}
	
	@Transactional
	public List<StationeryItem> getStationeryItems() {
	  return stationeryDao.getStationeryItems();
	}
	
	@Transactional
	public List<VT020000Unit> getUnitHCDV(String username) {
	  StationeryStaff stationeryStaff = stationeryDao.getStationeryStaffByUser(username);
	  if(stationeryStaff == null) return null;
	  return stationeryDao.getUnitInfo(stationeryStaff.getUnitId());
	}
	
	@Transactional
	public List<VT020000Unit> getUnitHCDVNoStaff(String username) {
	  int unitId = stationeryReportDAO.getUnitIdByUser(username);
	  if(unitId == 0) return null;
	  return stationeryDao.getUnitInfo(unitId);
	}
	
	@Transactional
	public List<VT020000Unit> getUnitVPTCT(String username) {
	  StationeryStaff stationeryStaff = stationeryDao.getStationeryStaffVPTCTByUser(username);
	  if(stationeryStaff == null) return null;
	  return stationeryDao.getUnitInfo(stationeryStaff.getUnitId());
	}
	
	@Transactional
	public ResponseEntityBase getLimitSpendingHCDV(String username) {
	  ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, 0);
    
    double limit = 0;
    StationeryStaff stationeryStaff = stationeryDao.getStationeryStaffByUser(username);
    if(stationeryStaff == null) return getLimitSpendingNV(username);
    
    List<StationeryQuota> quotas = stationeryDao.getStationeryQuotas(stationeryStaff.getUnitId());
    for(StationeryQuota quota : quotas) {
      double temp = quota.getQuantity() * quota.getQuota();
      if(quota.getUnitId() == stationeryStaff.getUnitId()) {
        limit = temp;
        break;
      } else {
        if(limit < temp) limit = temp;
      }
    }
    
    resp.setData(limit);
    return resp;
	}
	
	@Transactional
  public ResponseEntityBase getLimitSpendingNV(String username) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, 0);
    
    double limit = 0;
    String path = stationeryReportDAO.getUnitPathByUserName(username);
    if(path == null) return resp;
    String[] unitIds = path.split("/");
    
    Collections.reverse(Arrays.asList(unitIds));
    for(String unit : unitIds) {
      if(unit != null && unit.length() > 0) limit = vt050000DAO.findSpendingLimit(Integer.parseInt(unit));
      if(limit > 0) {
        break;
      }
    }
    if(Math.round(limit) == 0 ) resp.setStatus(Constant.UNIT_NOT_EXIST_QUOTA);
    
    resp.setData(limit);
    return resp;
  }
	
	@Transactional
	  public double getLimitSpendingNVByUnit(String unitId) {
	    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, 0);
	    
	    double limit = 0.00;
	    String path = stationeryReportDAO.getUnitPathByUnitID(unitId);
	    if(path == null) return 0.00;
	    String[] unitIds = path.split("/");
	    
	    Collections.reverse(Arrays.asList(unitIds));
	    for(String unit : unitIds) {
	      if(unit != null && unit.length() > 0) limit = vt050000DAO.findSpendingLimitUnitExcel(Integer.parseInt(unit));
	      if(limit > 0) {
	        break;
	      }
	    }
	    if(Math.round(limit) == 0 ) resp.setStatus(Constant.UNIT_NOT_EXIST_QUOTA);
	    
	    return limit;
	  }

	@Transactional
	  public ResponseEntityBase getLimitSpendingNVByUnitID(String unitID) {
	    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, 0);
	    
	    double limit = 0;
	    String path = stationeryReportDAO.getUnitPathByUnitID(unitID);
	    if(path == null) return resp;
	    String[] unitIds = path.split("/");
	    
	    Collections.reverse(Arrays.asList(unitIds));
	    for(String unit : unitIds) {
	      if(unit != null && unit.length() > 0) limit = vt050000DAO.findSpendingLimit(Integer.parseInt(unit));
	      if(limit > 0) {
	        break;
	      }
	    }
	    if(Math.round(limit) == 0 ) resp.setStatus(Constant.UNIT_NOT_EXIST_QUOTA);
	    
	    resp.setData(limit);
	    return resp;
	  }
	
	@Transactional
	public ResponseEntityBase getLimitSpendingHCDVUnitdID(String unitID) {
	  ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, 0);
    
    double limit = 0;
    StationeryStaff stationeryStaff = stationeryDao.getStationeryStaffByUser(unitID);
    if(stationeryStaff == null) return getLimitSpendingNVByUnitID(unitID);
    
    List<StationeryQuota> quotas = stationeryDao.getStationeryQuotas(stationeryStaff.getUnitId());
    for(StationeryQuota quota : quotas) {
      double temp = quota.getQuantity() * quota.getQuota();
      if(quota.getUnitId() == stationeryStaff.getUnitId()) {
        limit = temp;
        break;
      } else {
        if(limit < temp) limit = temp;
      }
    }
    
    resp.setData(limit);
    return resp;
	}
	
	@Transactional
	public VT050013DataGetAll getApprovedInfoById(String approveId) {
		VT050013DataGetAll approveInfo = stationeryDao.getApproveById(approveId);
		List<ItemModel> items = stationeryDao.getItemsByApproveId(approveId);
		List<StorageRequestItem> storageItems = stationeryDao.getStorageItemsByApproveId(approveId);

		double fullFillTotal = 0;
		int fullFillQuantity = 0;

		double total = 0;
		int quantity = 0;

		for (ItemModel item : items) {
			total = total + item.getUnitPrice() * item.getQuantity();
			quantity = quantity + item.getQuantity();
			for (StorageRequestItem storageItem : storageItems) {
				if (item.getStationeryId().equals(storageItem.getStationeryId())) {
					fullFillTotal = fullFillTotal + (item.getUnitPrice() * storageItem.getTotalFullFill());
					fullFillQuantity = fullFillQuantity + storageItem.getTotalFullFill();
					item.setTotalFullfill(storageItem.getTotalFullFill());
					item.setTotalMoneyFullfill(item.getUnitPrice() * item.getTotalFullfill());
				}
			}
		}
		approveInfo.setCountTotalFullfill(fullFillQuantity);
		approveInfo.setCountTotalMoneyFullfill(fullFillTotal);
		approveInfo.setCountTotalMoneyRequest(total);
		approveInfo.setCountTotalRequest(quantity);
		approveInfo.setListRequest(items);
		return approveInfo;
	}
	
	
	@Transactional
	public VT050013DataGetAll getApprovedInfoByIdDetails(String approveId) {
		VT050013DataGetAll approveInfo = stationeryDao.getApproveById(approveId);
		List<ItemModel> items = stationeryDao.getItemsProcessByApproveIdDetails(approveId);
		List<StorageRequestItem> storageItems = stationeryDao.getStorageItemsByApproveIdDetails(approveId);

		double fullFillTotal = 0;
		int fullFillQuantity = 0;

		double total = 0;
		int quantity = 0;

		for (ItemModel item : items) {
			total = total + item.getUnitPrice() * item.getQuantity();
			quantity = quantity + item.getQuantity();
			for (StorageRequestItem storageItem : storageItems) {
				if (item.getStationeryId().equals(storageItem.getStationeryId())) {
					fullFillTotal = fullFillTotal + (item.getUnitPrice() * storageItem.getTotalFullFill());
					fullFillQuantity = fullFillQuantity + storageItem.getTotalFullFill();
					item.setTotalFullfill(storageItem.getTotalFullFill());
					item.setTotalMoneyFullfill(item.getUnitPrice() * item.getTotalFullfill());
				}
			}
		}
		approveInfo.setCountTotalFullfill(fullFillQuantity);
		approveInfo.setCountTotalMoneyFullfill(fullFillTotal);
		approveInfo.setCountTotalMoneyRequest(total);
		approveInfo.setCountTotalRequest(quantity);
		approveInfo.setListRequest(items);
		return approveInfo;
	}
	
	@Transactional
	public VT050013DataGetAll getApprovedProcessInfoById(String approveId) {
		VT050013DataGetAll approveInfo = stationeryDao.getApproveById(approveId);
		List<ItemModel> items = stationeryDao.getItemsProcessByApproveId(approveId);
		List<StorageRequestItem> storageItems = stationeryDao.getStorageItemsByApproveId(approveId);

		double fullFillTotal = 0;
		int fullFillQuantity = 0;

		double total = 0;
		int quantity = 0;

		for (ItemModel item : items) {
			total = total + item.getUnitPrice() * item.getQuantity();
			quantity = quantity + item.getQuantity();
			for (StorageRequestItem storageItem : storageItems) {
				if (item.getStationeryId().equals(storageItem.getStationeryId())) {
					fullFillTotal = fullFillTotal + (item.getUnitPrice() * storageItem.getTotalFullFill());
					fullFillQuantity = fullFillQuantity + storageItem.getTotalFullFill();
					item.setTotalFullfill(storageItem.getTotalFullFill());
					item.setTotalMoneyFullfill(item.getUnitPrice() * item.getTotalFullfill());
				}
			}
		}
		approveInfo.setCountTotalFullfill(fullFillQuantity);
		approveInfo.setCountTotalMoneyFullfill(fullFillTotal);
		approveInfo.setCountTotalMoneyRequest(total);
		approveInfo.setCountTotalRequest(quantity);
		approveInfo.setListRequest(items);
		return approveInfo;
	}
	
	@Transactional
	public VT050013DataGetAll getApprovedInfoProcessById(String approveId) {
		VT050013DataGetAll approveInfo = stationeryDao.getApproveById(approveId);
		List<ItemModel> items = stationeryDao.getItemsProcessByApproveId(approveId);
		List<StorageRequestItem> storageItems = stationeryDao.getStorageItemsByApproveId(approveId);

		double fullFillTotal = 0;
		int fullFillQuantity = 0;

		double total = 0;
		int quantity = 0;

		for (ItemModel item : items) {
			total = total + item.getUnitPrice() * item.getQuantity();
			quantity = quantity + item.getQuantity();
			for (StorageRequestItem storageItem : storageItems) {
				if (item.getStationeryId().equals(storageItem.getStationeryId())) {
					fullFillTotal = fullFillTotal + (item.getUnitPrice() * storageItem.getTotalFullFill());
					fullFillQuantity = fullFillQuantity + storageItem.getTotalFullFill();
					item.setTotalFullfill(storageItem.getTotalFullFill());
					item.setTotalMoneyFullfill(item.getUnitPrice() * item.getTotalFullfill());
				}
			}
		}
		approveInfo.setCountTotalFullfill(fullFillQuantity);
		approveInfo.setCountTotalMoneyFullfill(fullFillTotal);
		approveInfo.setCountTotalMoneyRequest(total);
		approveInfo.setCountTotalRequest(quantity);
		approveInfo.setListRequest(items);
		return approveInfo;
	}
	
	@Transactional
	public void sendNotiAndSms(VT000000EntitySmsVsNotify userInfo, String message, String title, String smsTitle,
			AdditionalInfoBase addInfo, int type) throws Exception {
		notification.sendNotification(userInfo.getToUserName(), addInfo.toString(), message, title, type,
				userInfo.getToUserName(), 0);
		sms.sendSms(userInfo.getToUserId(), message, Constant.STATUS_NEW_SMS, userInfo.getPhone(), smsTitle);
	}

	@Transactional
	public void storgeNotiAndSms(VT000000EntitySmsVsNotify userInfo, String message, String title, String smsTitle,
			AdditionalInfoBase addInfo) throws Exception {
		notification.storageNotification(userInfo.getToUserName(), null, addInfo.toString(), message, title,
				Constant.TYPE_NOTIFY_MODUL03, userInfo.getToUserName(), 0, Constant.MANAGER_APPROVED);
		sms.sendSms(userInfo.getToUserId(), message, Constant.STATUS_NEW_SMS, userInfo.getPhone(), smsTitle);
	}

	@Transactional(readOnly = true)
	public List<StationeryEmployee> getItemsByStationeryIds(GettingItemsByStationeryIds stationeryIds) {
		return stationeryDao.getItemsByStationeryIds(stationeryIds);
	}

	@Transactional
	public StationeryWrapper uploadStationery(String loginUserName, Collection<GrantedAuthority> roleList,
			MultipartFile uploadfile) {
		HttpHeaders headers = new HttpHeaders();

		double limit = vt050000Service.caculRemainingSpendingLimit(loginUserName, roleList);

		try {
			String extension = FilenameUtils.getExtension(uploadfile.getOriginalFilename());
			Workbook workbook;
			if ("xls".equals(extension)) {
				workbook = new HSSFWorkbook(uploadfile.getInputStream());
			} else {
				workbook = new XSSFWorkbook(uploadfile.getInputStream());
			}
			
			if(!validExcelTemplate(workbook)) {
			  headers.set("messageCode", "valid");
			  return new StationeryWrapper(headers, new byte[0]);
			}

			List<String> names = new ArrayList<>();
			Sheet worksheet = workbook.getSheetAt(0);
			int lastRowNum = worksheet.getLastRowNum();
			DataFormatter formatter = new DataFormatter();

			Row titleRow = worksheet.getRow(0);
			Cell resultTileCell = titleRow.getCell(3);
			if(resultTileCell == null) {
			  resultTileCell = titleRow.createCell(3);
			}
			resultTileCell.setCellValue("Kết quả import");
			setCellBorder(resultTileCell, workbook);
			
			Row placeRow = worksheet.getRow(1);
			Cell place = placeRow.getCell(1);
			
			Row noteRow = worksheet.getRow(2);
			Cell note = noteRow.getCell(1);
			Cell rightNote = noteRow.getCell(3);
			if(rightNote == null) {
			  rightNote = noteRow.createCell(3);
			}
			setCellBorder(rightNote, workbook);
			
			Row rowPlace = worksheet.getRow(1);
			Cell cellPlaceError = rowPlace.createCell(3);
			setCellBorder(cellPlaceError, workbook);
			
			Row columnTitleRow = worksheet.getRow(3);
			Cell columnTitleCell = columnTitleRow.createCell(3);
			if(columnTitleCell == null) columnTitleCell = columnTitleRow.createCell(3);
			setCellBorder(columnTitleCell, workbook);
			
			String noteName = formatter.formatCellValue(note);
			VT050003RequestParam param = new VT050003RequestParam();

			String placeName = formatter.formatCellValue(place).trim();

			CellStyle styleRed = workbook.createCellStyle();
      CellStyle styleBlack = workbook.createCellStyle();
      Font fontRed = workbook.createFont();
      fontRed.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
      Font fontBlack = workbook.createFont();
      fontBlack.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
      styleRed.setFont(fontRed);
      styleBlack.setFont(fontBlack);
      styleRed.setWrapText(true);

      if (StringUtils.isNotEmpty(placeName)) {
        List<Integer> placeIds = stationeryDao.getPlaceIdByName(placeName);
        if(placeIds.size() > 0) param.setPlaceID(placeIds.get(0));
      } else {
        cellPlaceError.setCellValue(messages.getProperty("import.stationeryEmployee.place.required"));
        cellPlaceError.setCellStyle(styleRed);
      }
      
      boolean isMaxLengthNote = false;
      if(noteName != null) 
    	  {
    	  noteName = noteName.trim();
      System.out.println("NOte size " + noteName.length()) ;
			param.setNote(noteName);
			if(noteName.length() > 255) {
			  isMaxLengthNote = true;
			  rightNote.setCellValue(messages.getProperty("import.stationery.note.length"));
			  rightNote.setCellStyle(styleRed);
			}
    	  }
			int startRow = 4;
			int startCellIndex;

			int rowSuccess = 0;
			List<VT050003StationeryParam> listParam = new ArrayList<>();

			for (int i = startRow; i <= lastRowNum; i++) {
				Row row = worksheet.getRow(i);
				if (row != null) {
					VT050003StationeryParam stationeryParam = new VT050003StationeryParam();
					startCellIndex = 1;

					Cell cellStationeryName = row.getCell(startCellIndex++);
					Cell cellQuantity = row.getCell(startCellIndex++);
					Cell cellError = row.createCell(startCellIndex);

					String stationeryName = formatter.formatCellValue(cellStationeryName);
					if (stationeryName != null)
						{
						stationeryName = stationeryName.trim();
						}
					String quantity = formatter.formatCellValue(cellQuantity);
					if (quantity != null)
						{
						quantity = quantity.trim();
						}
					if (StringUtils.isNotEmpty(stationeryName)) {
					  List<StationeryNameAllResponseTemplate> stationerys = stationeryDao.getStationeryIdByName(stationeryName);
					  if(stationerys.size() > 0) {
					    StationeryNameAllResponseTemplate stationery = stationerys.get(0);
					    stationeryDao.getStationeryIdByName(stationeryName);
					    stationeryParam.setStationeryName(stationery.getStationeryName());
	            stationeryParam.setStationeryID(stationery.getStationeryId());
					  } else {
					    stationeryParam.setStationeryName(NOT_FOUND);
	            stationeryParam.setStationeryID(NOT_FOUND);
					  }
					} else {
					  stationeryParam.setStationeryName(null);
            stationeryParam.setStationeryID(null);
					}
					try {
						if (StringUtils.isNotEmpty(quantity)) {
							stationeryParam.setQuantity(Integer.valueOf(quantity));
						}
					} catch (Exception e) {
					  logger.error(e);
					}

					List<String> listError = new ArrayList<>();

					if (!validateImportEmployee(param, stationeryParam.getStationeryName(), quantity, listError, names, isMaxLengthNote)) {
						headers.set("messageCode", "fail");
						StringBuilder errorAll = new StringBuilder();
						for (int j = 0; j < listError.size(); j++) {
						  String error = listError.get(j);
						  if(j > 0) errorAll.append("\n");
							errorAll.append(error);
						}
						cellError.setCellValue(errorAll.toString());
						cellError.setCellStyle(styleRed);
						continue;
					}
					
					cellError.setCellValue("Import OK");
					listParam.add(stationeryParam);
					param.setListStationery(listParam);

					rowSuccess++;
					VT050003RequestParamEmployee paramEmployee = new VT050003RequestParamEmployee();
					paramEmployee.setListStationery(stationeryParam);
					if (param.getNote() != null) {
						paramEmployee.setNote(param.getNote().trim());
					} else {
						paramEmployee.setNote("");
					}
					paramEmployee.setPlaceID(param.getPlaceID());
				}
			}

			if (listParam.size() > 0) {
				double total = 0;
				List<VT050003StationeryParam> stationerys = stationeryDao.getStationerys(param);
				for (VT050003StationeryParam stationery : stationerys) {
					for (VT050003StationeryParam temp : listParam) {
						if (stationery.getStationeryID().equals(temp.getStationeryID())) {
							total = total + stationery.getUnitPrice() * temp.getQuantity();
						}
					}
				}
				if (total > limit) {
					headers.set("messageCode", "limited");
					return new StationeryWrapper(headers, new byte[0]);
				}
			}

			if(listParam.size() > 0) {
				VT050000IssuesStationery issuesStationery = new VT050000IssuesStationery(null, loginUserName,
						param.getPlaceID(), param.getNote().trim(), null, Constant.ISSUES_STATIONERY_PROCESSING, 0, null);
				issuesStationery.setCreateUser(loginUserName);
				
				vt050003DAO.insertIssuesStationery(issuesStationery);
				String issuesStationeryID = vt050003DAO.findIssuesStationeryID(loginUserName);

				for (VT050003StationeryParam stationery : listParam) {
					VT050003RequestParamEmployee paramEmployee = new VT050003RequestParamEmployee();
					paramEmployee.setListStationery(stationery);
					if (param.getNote() != null) {
						paramEmployee.setNote(param.getNote());
					} else {
						paramEmployee.setNote("");
					}
					paramEmployee.setPlaceID(param.getPlaceID());
					createIssuesItemsImport(paramEmployee, loginUserName, issuesStationeryID);
				}

				try {
					sendSmsAndNotify(loginUserName, param.getPlaceID(), issuesStationeryID, Constant.STATIONERY_CREATE);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);
			byte[] barray = bos.toByteArray();
			InputStream is = new ByteArrayInputStream(barray);
			IOUtils.readFully(is, barray);

			headers.setContentType(MediaType.parseMediaType(MEDIATYPE_EXCEL));
			String filename = uploadfile.getName();
			List<String> customHeaders = new ArrayList<>();
			customHeaders.add(CONTENT_DISPOSITION);
			headers.setAccessControlExposeHeaders(customHeaders);
			headers.setContentDispositionFormData(filename, filename);
			headers.setCacheControl(HEADER_CACHE_CONTROL);
			
			if(isMaxLengthNote) {
			  headers.set("messageCode", "note");
			} else if (rowSuccess == 0) {
				headers.set("messageCode", "norow");
			} else if (rowSuccess > 0) {
			  headers.set("messageCode", "success");
			}

			return new StationeryWrapper(headers, barray);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		headers.set("messageCode", "error");
		return new StationeryWrapper(headers, new byte[0]);
	}
	
	private boolean validExcelTemplate(Workbook importWorkbook) throws IOException {
	  File fileTemp = getFileTemplate("EXCEL_FILE_PATH_IMPORT_TEMPLATE");
    Workbook defaultWorkbook = getWorkbook(fileTemp);
    Sheet defaultSheet = defaultWorkbook.getSheetAt(0);
    
    Sheet importSheet = importWorkbook.getSheetAt(0);
    if(importSheet == null) return false;
    Row defaultTilteRow = defaultSheet.getRow(0);
    Cell defaultTitleCell = defaultTilteRow.getCell(0);
    Row importTilteRow = importSheet.getRow(0);
    if(importTilteRow == null) return false;
    Cell importTitleCell = importTilteRow.getCell(0);
    if(!validateCell(defaultTitleCell, importTitleCell)) return false;
    
    Row defaultPlaceRow = defaultSheet.getRow(1);
    Row importPlaceRow = importSheet.getRow(1);
    if(importPlaceRow == null ) return false;
    
    Cell defaultPlaceCell = defaultPlaceRow.getCell(0);
    Cell importPlaceCell = importPlaceRow.getCell(0);
    if(!validateCell(defaultPlaceCell, importPlaceCell)) return false;
    
    Row defaultNoteRow = defaultSheet.getRow(2);
    Row importNoteRow = importSheet.getRow(2);
    if(importNoteRow == null) return false;
    Cell defaultNoteCell = defaultNoteRow.getCell(0);
    Cell importNoteCell = importNoteRow.getCell(0);
    if(!validateCell(defaultNoteCell, importNoteCell)) return false;
    
    Row defaultColTitleRow = defaultSheet.getRow(3);
    Row importColTitleRow = importSheet.getRow(3);
    if(importColTitleRow == null) return false;
    
    Cell defaultColTitle1Cell = defaultColTitleRow.getCell(0);
    Cell importColTitle1Cell = importColTitleRow.getCell(0);
    if(!validateCell(defaultColTitle1Cell, importColTitle1Cell)) return false;
    
    Cell defaultColTitle2Cell = defaultColTitleRow.getCell(1);
    Cell importColTitle2Cell = importColTitleRow.getCell(1);
    if(!validateCell(defaultColTitle2Cell, importColTitle2Cell)) return false;
    
    Cell defaultColTitle3Cell = defaultColTitleRow.getCell(2);
    Cell importColTitle3Cell = importColTitleRow.getCell(2);
    if(!validateCell(defaultColTitle3Cell, importColTitle3Cell)) return false;
    return true;
	}
	
	private boolean validateCell(Cell defaultCell, Cell importCell) {
	  if(defaultCell == null || importCell == null) return false;
	  String defaultValue = defaultCell.getStringCellValue().trim();
	  String importValue = importCell.getStringCellValue().trim();
	  if(!importValue.equals(defaultValue)) return false;
	  return true;
	}
	
	private boolean checkDuplicate(String name, List<String> names) {
	  if(names.contains(name)) return false;
	  names.add(name);
	  return true;
	}
	
	private File getFileTemplate(String path) {
    String excelFilePath = linkTemplateExcel.getProperty(path);
    File file = null;
    try {
      Resource resource = new ClassPathResource(excelFilePath);
      file = resource.getFile();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }

    return file;
  }

  private Workbook getWorkbook(File file) throws IOException {
    String extension = FilenameUtils.getExtension(file.getName());
    FileInputStream inputStream = new FileInputStream(file);
    Workbook wb;
    if ("xls".equals(extension)) {
      wb = new HSSFWorkbook(inputStream);
    } else {
      wb = new XSSFWorkbook(inputStream);
    }

    return wb;
  }

	private void createIssuesItemsImport(VT050003RequestParamEmployee requestParam, String userName,
			String issuesStationeryID) {
		// insert to ISSUES_STATIONERY_ITEMS
		VT050003IssuesStationeryItemsToInsertEmployee issuesStationeryItemsToInsert = new VT050003IssuesStationeryItemsToInsertEmployee();
		issuesStationeryItemsToInsert.setIssuesStationeryID(issuesStationeryID);
		issuesStationeryItemsToInsert.setStatus(Constant.STATIONERY_CREATE);
		issuesStationeryItemsToInsert.setCreateUser(userName);
		issuesStationeryItemsToInsert.setCalculationUnit(requestParam.getListStationery().getCalculationUnit());
		issuesStationeryItemsToInsert.setStationeryID(requestParam.getListStationery().getStationeryID());
		issuesStationeryItemsToInsert.setStationeryName(requestParam.getListStationery().getStationeryName());
		issuesStationeryItemsToInsert.setQuantity(requestParam.getListStationery().getQuantity());
		issuesStationeryItemsToInsert.setUnitPrice(requestParam.getListStationery().getUnitPrice());
		vt050003DAO.insertIssuesStationeryItemsEmployee(issuesStationeryItemsToInsert);
	}

	public void sendSmsAndNotify(String userName, int placeID, String idRecord, int statusRecord) throws Exception {
		Thread notiThread = new Thread() {
			public void run() {
				try {
					VT050003InfoToFindHcdv info = new VT050003InfoToFindHcdv();
					info.setUserName(userName);
					info.setPlaceID(placeID);
					info.setJobCode(Constant.STATIONERY_HCDV_CODE);
					List<String> listHcdvUserName = vt050003DAO.findHcdvUserName(info);

					// find full name of employee request VPP
					String fullName = vt050000DAO.findFullNameByUserName(userName);
					for (String hcdvUserName : listHcdvUserName) {
						// send notify
						String toUserName = hcdvUserName;
						VT050000AdditionalInfoToSendNotify addInfo = new VT050000AdditionalInfoToSendNotify(idRecord,
								Constant.PMQT_ROLE_STAFF_HC_DV, Constant.PMQT_ROLE_EMPLOYYEE);
						String additionalInformation = addInfo.toString();
						String message = notifyMessage.getProperty("N35_1");
						message = MessageFormat.format(message, fullName);
						String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
						int type = Constant.TYPE_NOTIFY_MODUL05;
						String createUser = userName;
						int status = statusRecord;
						notification.sendNotification(toUserName, additionalInformation, message, title, type,
								createUser, status);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					logger.info("****************************THREAD NOTI ERROR********:");
				}
			}

		};
		notiThread.start();
	}

	private void setCellBorder(Cell cell, Workbook workbook) {
	  CellStyle borderStyle = workbook.createCellStyle();
	  borderStyle.setWrapText(true);
	  borderStyle.setBorderRight(BorderStyle.THIN);
	  borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	  borderStyle.setBorderLeft(BorderStyle.THIN);
	  borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
    borderStyle.setBorderTop(BorderStyle.THIN);
    borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
    borderStyle.setBorderBottom(BorderStyle.THIN);
    borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    
    Font headerFont = workbook.createFont();
    headerFont.setBold(true);
    borderStyle.setFont(headerFont);
    cell.setCellStyle(borderStyle);
	}
	
	private boolean validateImportEmployee(VT050003RequestParam stationeryParam, String stationeryName, String quantity,
			List<String> listError, List<String> names, boolean isMaxLengthNote) {
		if(((stationeryName == null || "".equals(stationeryName)) && ((quantity == null || "".equals(quantity) || "0".equals(quantity))))){
			listError.add("");
		}
		else{
		if (stationeryName == null || "".equals(stationeryName)) {
			listError.add(messages.getProperty("import.stationery.stationeryName.required"));
		} else if(stationeryName.equals(NOT_FOUND)){
		  listError.add(messages.getProperty("import.stationery.stationeryName.notfound"));
		}
		if (quantity == null || "".equals(quantity) || "0".equals(quantity)) {
			listError.add(messages.getProperty("import.stationeryEmployee.quantity.required"));
		} else {
		  try {
        if (StringUtils.isNotEmpty(quantity)) {
          int checkQuantity = Integer.valueOf(quantity);
          if(checkQuantity < 0) {
        	  listError.add(messages.getProperty("import.stationeryEmployee.quantity.valid"));
          }
        } 
      } catch (Exception e) {
        listError.add(messages.getProperty("import.stationeryEmployee.quantity.valid"));
      }
		}
		if (stationeryParam.getPlaceID() == 0) {
			listError.add(messages.getProperty("import.stationeryEmployee.place.required"));
		}
		
		if(!checkDuplicate(stationeryName, names)) {
		  listError.add(messages.getProperty("import.stationery.stationeryName.duplicate"));
		}
		}
		if(isMaxLengthNote) return false;
		
		if (listError.size() != 0) {
			return false;
		}
		return true;
	}

	static public class StationeryWrapper {

		private HttpHeaders headers;
		private byte[] data;

		public StationeryWrapper(HttpHeaders headers, byte[] data) {
			this.headers = headers;
			this.data = data;
		}

		public HttpHeaders getHeaders() {
			return headers;
		}

		public void setHeaders(HttpHeaders headers) {
			this.headers = headers;
		}

		public byte[] getData() {
			return data;
		}

		public void setData(byte[] data) {
			this.data = data;
		}
	}
}
