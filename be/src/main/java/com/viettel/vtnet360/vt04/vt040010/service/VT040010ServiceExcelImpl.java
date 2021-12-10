package com.viettel.vtnet360.vt04.vt040010.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetVisibility;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityMapStatus;
import com.viettel.vtnet360.vt04.vt040010.dao.VT040010DAO;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010Condition;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010IssueService;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010Stationery;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010StationeryExcel;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT040010ServiceExcelImpl implements VT040010ServiceExportExcel {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT040010DAO vt040010dao;

	@Autowired
	Properties linkTemplateExcel;

	@Override
	@Transactional(readOnly = true)
	public File createExcelVTTB(VT040010Condition condition) throws Exception {

		File file = null;
		List<VT040010StationeryExcel> stationeryExcel = new ArrayList<>();
		String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT040010_VTTB");

		try {
			setUpCondition(condition);
			stationeryExcel = vt040010dao.findListStationeryExcel(condition);

			file = writeExcel(stationeryExcel, excelFilePath, condition);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return file;
	}

	public void setUpCondition(VT040010Condition condition) {
		try {
			condition.setRowSize(6);
			
			if (!arrayContainStatusValid(condition.getStatus())) {
				int[] status = { Constant.ISSUED_SERVICE_STATUS_COMPLETE, Constant.ISSUED_SERVICE_STATUS_RECEIVE,
						Constant.ISSUED_SERVICE_STATUS_REJECT };
				condition.setStatus(status);
			}
			
			condition.setPlaceName("S009");
			if (condition.getListUnitId() == null || condition.getListUnitId().length == 0) {
				if (condition.getLoginRole().equals(Constant.PMQT_ROLE_ADMIN)) {
					condition.setListUnitId(vt040010dao.findListUnit());
				} else if (condition.getLoginRole().equals(Constant.PMQT_ROLE_MANAGER)) {
					condition.setListUnitId(vt040010dao.findUnitManager(condition.getLoginUserName()));
				}
			}

			if (condition.getStartDate() != null) {
				Date startDate = condition.getStartDate();
				Calendar cal = GregorianCalendar.getInstance();
				cal.setTime(startDate);
				String newDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
						+ cal.get(Calendar.DAY_OF_MONTH) + " 00:00:00";
				condition.setStartDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse(newDate));
			}

			if (condition.getEndDate() != null) {
				Date endDate = condition.getEndDate();
				Calendar cal = GregorianCalendar.getInstance();
				cal.setTime(endDate);
				String newDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
						+ cal.get(Calendar.DAY_OF_MONTH) + " 23:59:59";
				condition.setEndDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse(newDate));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private Boolean arrayContainStatusValid(int[] status) {
		if(status==null)return false;
		

		for (int temp : status) {
			if (temp == Constant.ISSUED_SERVICE_STATUS_COMPLETE || temp == Constant.ISSUED_SERVICE_STATUS_RECEIVE || temp == Constant.ISSUED_SERVICE_STATUS_REJECT) {
				return true;
			}
		}
		
		return false;

	}

	/**
	 * @param stationeryExcel
	 * @param excelFilePath
	 * @return excel
	 * @throws IOException
	 */
	public File writeExcel(List<VT040010StationeryExcel> stationeryExcel, String excelFilePath,
			VT040010Condition condition) throws IOException {

		File outFile = null;
		File file = null;

		Date date = new Date();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		String fileName = cal.get(Calendar.YEAR) + "_" + (cal.get(Calendar.MONTH) + 1) + "_"
				+ cal.get(Calendar.DAY_OF_MONTH) + "_" + cal.get(Calendar.HOUR_OF_DAY) + "h" + cal.get(Calendar.MINUTE)
				+ "m" + cal.get(Calendar.SECOND) + "s" + cal.get(Calendar.MILLISECOND) + "ms.xlsx";
		try {
			Resource resource = new ClassPathResource(excelFilePath);
			file = resource.getFile();
			String filePath = file.getAbsolutePath();
			outFile = new File(filePath.split("templateExcel")[0] + "saveExcel\\VT04\\VTTB\\" + fileName);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		try (FileInputStream inputStream = new FileInputStream(file)) {
			try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
				// SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(inputStream),
				// 100)
				Map<String, Integer> rangeStationery = new HashMap<>();
				Map<String, Map<String, VT040010IssueService>> mapUnit = handleData(stationeryExcel);
				int rowCount = 2;
				int i = 2;
				int lengthListStationery = 2; // start with row index = 2
				boolean isNewIssue = true;
				CreationHelper createHelper = workbook.getCreationHelper();
				CellStyle cellStyle = workbook.createCellStyle();
				CellStyle combined = workbook.createCellStyle();
				CellStyle combinedNo = workbook.createCellStyle();
				CellStyle combinedNote = workbook.createCellStyle();
				Font font = workbook.createFont();

				font.setFontName("Times New Roman");
				font.setFontHeightInPoints((short) 12);
				cellStyle.setFont(font);
				cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
				cellStyle.setFillPattern(FillPatternType.NO_FILL);
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);

				// style for column No.
				combinedNo.cloneStyleFrom(cellStyle);
				combinedNo.setAlignment(HorizontalAlignment.CENTER);

				// style for column date
				combined.cloneStyleFrom(cellStyle);
				combined.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));

				// style for column Note
				combinedNote.cloneStyleFrom(cellStyle);
				combinedNote.setWrapText(true);

				// write excel
				// create sheet
				for (String unitId : mapUnit.keySet()) {
					Sheet sheet = workbook.cloneSheet(1);
					
					// try to fix hidden sheet tabs
					workbook.setSheetHidden(i, false);
					workbook.setSheetVisibility(i, SheetVisibility.VISIBLE);
					
					workbook.setSheetName(i++, String.valueOf(unitId));
					
					Map<String, VT040010IssueService> mapIss = mapUnit.get(unitId);
					rowCount = 2;
					lengthListStationery = 2;

					// set title
					Cell cell = sheet.getRow(0).getCell(0);
					cell.setCellValue(mapIss.entrySet().iterator().next().getValue().getUnitName());

					// fill data
					for (String issId : mapIss.keySet()) {
						VT040010IssueService itemIss = mapIss.get(issId);
						isNewIssue = true;
						lengthListStationery += itemIss.getStationeries().size();

						for (VT040010Stationery sta : itemIss.getStationeries()) {
							writeBook(itemIss, sta, sheet, rowCount++, cellStyle, combinedNo, combined, combinedNote,
									isNewIssue);
							isNewIssue = false;
						}
					}

					rangeStationery.put(unitId, lengthListStationery);
				}

				// write summary sheet
				createSummarySheet(stationeryExcel, rangeStationery, workbook.getSheetAt(0), cellStyle, condition);
				workbook.removeSheetAt(1);
				workbook.setForceFormulaRecalculation(true);

				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				}
			}
		}

		return outFile;
	}

	/**
	 * handle original list to appropriate list to write excel
	 * 
	 * @param stationeryExcel
	 * @return map
	 */
	public Map<String, Map<String, VT040010IssueService>> handleData(List<VT040010StationeryExcel> stationeryExcel) {
		// list sheet unit
		Map<String, Map<String, VT040010IssueService>> mapUnit = new HashMap<>();

		for (VT040010StationeryExcel item : stationeryExcel) {
			// collect issue service by unit id
			if (mapUnit.containsKey(item.getUnitId())) {
				Map<String, VT040010IssueService> mapIssueService = mapUnit.get(item.getUnitId());

				if (mapIssueService.containsKey(item.getIssueServiceId())) {
					VT040010Stationery sta = new VT040010Stationery(item.getStationeryName(), null,
							item.getStationeryQuan(), item.getStationeryUnitCal());
					mapIssueService.get(item.getIssueServiceId()).addStationery(sta);
				} else {
					
//					VT040010IssueService iss = new VT040010IssueService(item.getIssueServiceId(), item.getUnitName(),
//							item.getServiceName(), item.getTimeStartPlan(), item.getEmpName(), item.getNote());
					
					VT040010IssueService iss = new VT040010IssueService();

					iss.setIssueServiceId(item.getIssueServiceId());
					iss.setUnitName(item.getUnitName());
					iss.setServiceName(item.getServiceName());
					iss.setTimeStartPlan(item.getTimeStartPlan());
					iss.setReciverName(item.getReciverName());
					iss.setEmpName(item.getEmpName());
					iss.setNote(item.getNote());
					iss.setStatus(item.getStatus());
					
					VT040010Stationery sta = new VT040010Stationery(item.getStationeryName(), null,
							item.getStationeryQuan(), item.getStationeryUnitCal());
					
					iss.addStationery(sta);
					mapIssueService.put(item.getIssueServiceId(), iss);
				}

				mapUnit.put(item.getUnitId(), mapIssueService);
			} else {
				Map<String, VT040010IssueService> mapIssueService = new HashMap<>();
				
//				VT040010IssueService iss = new VT040010IssueService(item.getIssueServiceId(), item.getUnitName(),
//						item.getServiceName(), item.getTimeStartPlan(), item.getEmpName(), item.getNote());
//				
				
				VT040010IssueService iss = new VT040010IssueService();

				iss.setIssueServiceId(item.getIssueServiceId());
				iss.setUnitName(item.getUnitName());
				iss.setServiceName(item.getServiceName());
				iss.setTimeStartPlan(item.getTimeStartPlan());
				iss.setReciverName(item.getReciverName());
				iss.setEmpName(item.getEmpName());
				iss.setNote(item.getNote());
				iss.setStatus(item.getStatus());
				
				VT040010Stationery sta = new VT040010Stationery(item.getStationeryName(), null,
						item.getStationeryQuan(), item.getStationeryUnitCal());
				iss.addStationery(sta);

				mapIssueService.put(item.getIssueServiceId(), iss);
				mapUnit.put(item.getUnitId(), mapIssueService);
			}
		}

		return mapUnit;
	}

	/**
	 * @param stationeryExcel
	 * @param rangeStationery
	 * @param sheet
	 * @param cellStyle
	 */
	public void createSummarySheet(List<VT040010StationeryExcel> stationeryExcel, Map<String, Integer> rangeStationery,
			Sheet sheet, CellStyle cellStyle, VT040010Condition condition) {
		// list stationery name for each unit id
		Map<String, Map<String, VT040010Stationery>> mapUnit = new HashMap<>();
		// list name of unit
		Map<String, String> mapUnitName = new HashMap<>();

		for (VT040010StationeryExcel item : stationeryExcel) {
			// collect stationery name by unit id
			if (mapUnit.containsKey(item.getUnitId())) {
				Map<String, VT040010Stationery> mapStationeryNames = mapUnit.get(item.getUnitId());

				if (!mapStationeryNames.containsKey(item.getStationeryId())) {
					VT040010Stationery sta = new VT040010Stationery(item.getStationeryName(), null,
							item.getStationeryQuan(), item.getStationeryUnitCal());
					mapStationeryNames.put(item.getStationeryId(), sta);
				}

				mapUnit.put(item.getUnitId(), mapStationeryNames);
			} else {
				Map<String, VT040010Stationery> mapStationeryNames = new HashMap<>();
				VT040010Stationery sta = new VT040010Stationery(item.getStationeryName(), null,
						item.getStationeryQuan(), item.getStationeryUnitCal());
				mapStationeryNames.put(item.getStationeryId(), sta);
				mapUnit.put(item.getUnitId(), mapStationeryNames);

				// put unit name to mapUnitName
				mapUnitName.put(item.getUnitId(), item.getDetailUnit());
			}
		}

		int rowCount = 3;
		int numericalOrderUnit = 1;
		int numbericalOrderStationery = 1;
		Map<String, VT040010Stationery> mapStationeryName = null;
		Row row = null;
		String formula = "";

		// style for each unit name row
		CellStyle combinedUnit = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();

		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		combinedUnit.cloneStyleFrom(cellStyle);
		combinedUnit.setFont(font);
		combinedUnit.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		combinedUnit.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// style for No.
		CellStyle combinedNo = sheet.getWorkbook().createCellStyle();
		combinedNo.cloneStyleFrom(cellStyle);
		combinedNo.setAlignment(HorizontalAlignment.CENTER);

		// style for No. of unit name row
		CellStyle combinedNoUnitName = sheet.getWorkbook().createCellStyle();
		combinedNoUnitName.cloneStyleFrom(combinedUnit);
		combinedNoUnitName.setAlignment(HorizontalAlignment.CENTER);

		Cell cell = sheet.getRow(0).getCell(0);
		cell.setCellValue("BẢNG TỔNG HỢP THAY THẾ VẬT TƯ THIẾT BỊ VĂN PHÒNG");

		cell = sheet.getRow(1).getCell(0);
		cell.setCellValue(createTitleDate(condition));

		for (String unitId : mapUnit.keySet()) {
			mapStationeryName = mapUnit.get(unitId);
			numbericalOrderStationery = 1;
			row = sheet.createRow(rowCount++);
			sheet.addMergedRegion(new CellRangeAddress((rowCount - 1), (rowCount - 1), 1, 4));
			RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress((rowCount - 1), (rowCount - 1), 1, 4),
					sheet);
			formula = "ROMAN(" + numericalOrderUnit++ + ", 4)";

			cell = row.createCell(0);
			cell.setCellStyle(combinedNoUnitName);
			cell.setCellType(CellType.FORMULA);
			cell.setCellFormula(formula);

			cell = row.createCell(1);
			cell.setCellStyle(combinedUnit);
			cell.setCellValue(mapUnitName.get(unitId));

			for (VT040010Stationery stationery : mapStationeryName.values()) {
				row = sheet.createRow(rowCount++);

				// No.
				cell = row.createCell(0);
				cell.setCellStyle(combinedNo);
				cell.setCellValue(numbericalOrderStationery++);

				// stationery name
				cell = row.createCell(1);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(stationery.getStationeryName());

				// stationery unit calculation
				cell = row.createCell(2);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(stationery.getStationeryUnitCal());

				// stationery quantity
				formula = "SUMIF('" + unitId + "'!D3:D" + rangeStationery.get(unitId) + ",\""
						+ stationery.getStationeryName() + "\",'" + unitId + "'!F3:F" + rangeStationery.get(unitId)
						+ ")";
				cell = row.createCell(3);
				cell.setCellStyle(cellStyle);
				//cell.setCellValue(stationery.getStationeryQuan());
				cell.setCellType(CellType.FORMULA);
				cell.setCellFormula(formula);

				cell = row.createCell(4);
				cell.setCellStyle(cellStyle);
			}
		}
	}

	/**
	 * fill data to cell
	 * 
	 * @param stationeryExcel
	 * @param sheet
	 * @param rowNum
	 */
	private void writeBook(VT040010IssueService itemIss, VT040010Stationery stationeries, Sheet sheet, int rowCount,
			CellStyle cellStyle, CellStyle combinedNo, CellStyle combined, CellStyle combinedNote, boolean isNewIssue) {

		Row row = sheet.createRow(rowCount);

		// No.
		Cell cell = row.createCell(0);
		cell.setCellStyle(combinedNo);
		cell.setCellValue((double) rowCount - 1);

		// issue service id
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);
		if (isNewIssue) {
			cell.setCellValue(itemIss.getIssueServiceId());
		}

		// service name
		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		if (isNewIssue) {
			cell.setCellValue(itemIss.getServiceName());
		}

		// name of stationery
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(stationeries.getStationeryName());

		// stationery unit calculation
		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(stationeries.getStationeryUnitCal());

		// end time plan
		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(stationeries.getStationeryQuan());
		
		// start time to do service
		cell = row.createCell(6);
		cell.setCellStyle(combined);
		if (isNewIssue) {
			cell.setCellValue(itemIss.getTimeStartPlan());
		}
		
		// nguoi nhan name
		cell = row.createCell(7);
		cell.setCellStyle(cellStyle);
		if (isNewIssue) {
			cell.setCellValue(itemIss.getReciverName());
		}

		// requester name
		cell = row.createCell(8);
		cell.setCellStyle(cellStyle);
		if (isNewIssue) {
			cell.setCellValue(itemIss.getEmpName());
		}
		
		// status
		cell = row.createCell(9);
		cell.setCellStyle(cellStyle);
		if (VT000000EntityMapStatus.STATUS_ISSUE_SERVICE.containsKey(itemIss.getStatus())) {
			cell.setCellValue(VT000000EntityMapStatus.STATUS_ISSUE_SERVICE.get(itemIss.getStatus()));
		} else {
			cell.setCellValue(itemIss.getStatus());
		}

		// note
		cell = row.createCell(10);
		cell.setCellStyle(combinedNote);
		if (isNewIssue) {
			cell.setCellValue(itemIss.getNote());
		}


	}

	public String createTitleDate(VT040010Condition condition) {
		String title = "";
		try {
			if (condition.getStartDate() != null) {
				Date startDate = condition.getStartDate();
				Calendar cal = GregorianCalendar.getInstance();
				cal.setTime(startDate);
				title += "Từ ngày " + cal.get(Calendar.DAY_OF_MONTH) + " tháng " + (cal.get(Calendar.MONTH) + 1)
						+ " năm " + cal.get(Calendar.YEAR) + " ";
			}

			if (condition.getEndDate() != null) {
				Date endDate = condition.getEndDate();
				Calendar cal = GregorianCalendar.getInstance();
				cal.setTime(endDate);
				title += "Đến ngày " + cal.get(Calendar.DAY_OF_MONTH) + " tháng " + (cal.get(Calendar.MONTH) + 1)
						+ " năm " + cal.get(Calendar.YEAR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return title;
	}
}
