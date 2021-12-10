package com.viettel.vtnet360.vt05.vt050015.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.viettel.vtnet360.stationery.request.dao.StationeryDAO;
import com.viettel.vtnet360.stationery.service.StationeryService;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt05.vt050000.dao.VT050000DAO;
import com.viettel.vtnet360.vt05.vt050015.dao.VT050015DAO;
import com.viettel.vtnet360.vt05.vt050015.entity.ReportStationery;
import com.viettel.vtnet360.vt05.vt050015.entity.VT050015StationeryExcel;

/**
 * 
 * @author VinhNVQ
 *
 */
@Service
public class VT050015ExcelVPPServiceImpl implements VT050015ExcelVPPService {

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	Properties linkTemplateExcel;

	@Autowired
	VT050015DAO VT050015DAO;

	@Autowired
	  private VT050000DAO vt050000DAO;
	@Autowired
	private StationeryService stationeryService;
	
	@Autowired
	private StationeryDAO stationeryDao;
	
	@Override
	public File createExcelVPP(ReportStationery reportStationery, Principal principal,
			OAuth2Authentication authentication ,Collection<GrantedAuthority> roleList) throws Exception {
		
		
		List<Integer> statusParam = reportStationery.getListStatus();
		List<Integer> status = new ArrayList<>();
		if (statusParam == null || statusParam.isEmpty() 
				|| (!statusParam.contains(Constant.STATIONERY_APPROVED_EXECUTING) &&  !statusParam.contains(Constant.STATIONERY_APPROVED_FINISH) && !statusParam.contains(Constant.STATIONERY_APPROVED_COMPLETE))
				|| (statusParam.contains(Constant.STATIONERY_APPROVED_EXECUTING) && statusParam.contains(Constant.STATIONERY_APPROVED_FINISH) && statusParam.contains(Constant.STATIONERY_APPROVED_COMPLETE))) {
			status.add(Constant.STATIONERY_APPROVED_EXECUTING);
			status.add(Constant.STATIONERY_APPROVED_FINISH);
			status.add(Constant.STATIONERY_APPROVED_COMPLETE);
		} else if (statusParam.contains(Constant.STATIONERY_APPROVED_EXECUTING) && !statusParam.contains(Constant.STATIONERY_APPROVED_FINISH)&& !statusParam.contains(Constant.STATIONERY_APPROVED_COMPLETE)){
			status.add(Constant.STATIONERY_APPROVED_EXECUTING);
		} else if (!statusParam.contains(Constant.STATIONERY_APPROVED_EXECUTING) && statusParam.contains(Constant.STATIONERY_APPROVED_FINISH) && !statusParam.contains(Constant.STATIONERY_APPROVED_COMPLETE)){
			status.add(Constant.STATIONERY_APPROVED_FINISH);
		}else if (!statusParam.contains(Constant.STATIONERY_APPROVED_EXECUTING) && !statusParam.contains(Constant.STATIONERY_APPROVED_FINISH) && statusParam.contains(Constant.STATIONERY_APPROVED_COMPLETE)){
			status.add(Constant.STATIONERY_APPROVED_COMPLETE);
		}
		// set status
		reportStationery.setListStatus(status);
		File file = null;
		List<VT050015StationeryExcel> stationeryExcel = new ArrayList<>();
		String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT050015_VPP");

		try {
		  if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN)) 
		      || roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))) {
        reportStationery.setAdmin(true);
        reportStationery.setQL(true);
        reportStationery.setVPTCT(false);
        reportStationery.setHCDV(false);
      stationeryExcel = VT050015DAO.findListStationeryExcelVPP(reportStationery);
      }
		  
		  else if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))) {
		    reportStationery.setAdmin(false);
		    reportStationery.setQL(false);
        reportStationery.setVPTCT(true);
        reportStationery.setHCDV(false);
        stationeryExcel = VT050015DAO.findListStationeryExcelVPP(reportStationery);
      }
		  
		  else if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV))) {
		    reportStationery.setQL(false);
        reportStationery.setAdmin(false);
				reportStationery.setVPTCT(false);
				reportStationery.setHCDV(true);
				stationeryExcel = VT050015DAO.findListStationeryExcelVPP(reportStationery);
			}
		  
			file = writeExcel(stationeryExcel, excelFilePath, reportStationery);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return file;
	}

	/**
	 * @param stationeryExcel
	 * @param excelFilePath
	 * @return excel
	 * @throws IOException
	 */
	public File writeExcel(List<VT050015StationeryExcel> stationeryExcel, String excelFilePath,
			ReportStationery reportStationery) throws IOException {

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
				Map<String, Map<String, List<VT050015StationeryExcel>>> mapUnit = handleData(stationeryExcel);
				int rowCount = 2;
				int i = 2;
				int lengthListStationery = 2; // start with row index = 2
				boolean isNewIssue = true;
				CreationHelper createHelper = workbook.getCreationHelper();
				CellStyle cellStyle = workbook.createCellStyle();
				CellStyle cellStyleNumber = workbook.createCellStyle();
				CellStyle cellStyleNumberFooter = workbook.createCellStyle();
				CellStyle cellStyleNumberFooter1 = workbook.createCellStyle();
				CellStyle combined = workbook.createCellStyle();
				CellStyle combinedNo = workbook.createCellStyle();
				CellStyle combinedNote = workbook.createCellStyle();
				CellStyle combinedColor = workbook.createCellStyle();
				CellStyle combinedUnit = workbook.createCellStyle();
				Font font = workbook.createFont();
				
				Font fontColor = workbook.createFont();
				
				fontColor.setFontName("Times New Roman");
				fontColor.setFontHeightInPoints((short) 14);
				fontColor.setColor(IndexedColors.RED.getIndex());
				fontColor.setBold(true);
				
				font.setFontName("Times New Roman");
				font.setFontHeightInPoints((short) 12);
				cellStyle.setFont(font);
				cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
				cellStyle.setFillPattern(FillPatternType.NO_FILL);
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);

				cellStyleNumber.setFont(font);
				cellStyleNumber.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
				cellStyleNumber.setFillPattern(FillPatternType.NO_FILL);
				cellStyleNumber.setBorderTop(BorderStyle.THIN);
				cellStyleNumber.setBorderRight(BorderStyle.THIN);
				cellStyleNumber.setBorderBottom(BorderStyle.THIN);
				cellStyleNumber.setBorderLeft(BorderStyle.THIN);
				cellStyleNumber.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
				
				
				cellStyleNumberFooter.setFont(font);
				cellStyleNumberFooter.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleNumberFooter.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cellStyleNumberFooter.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
				
				cellStyleNumberFooter1.cloneStyleFrom(cellStyle);
				cellStyleNumberFooter1.setFont(font);
				cellStyleNumberFooter1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleNumberFooter1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cellStyleNumberFooter1.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
				
				combinedColor.setFont(fontColor);
				combinedColor.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
				combinedColor.setFillPattern(FillPatternType.NO_FILL);
				combinedColor.setBorderTop(BorderStyle.THIN);
				combinedColor.setBorderRight(BorderStyle.THIN);
				combinedColor.setBorderBottom(BorderStyle.THIN);
				combinedColor.setBorderLeft(BorderStyle.THIN);
				
				
				font.setFontName("Times New Roman");
				font.setFontHeightInPoints((short) 12);
				combinedUnit.cloneStyleFrom(cellStyle);
				combinedUnit.setFont(font);
				combinedUnit.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				combinedUnit.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				
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
				int countSL_VPP_YEUCAU = 0;
				int countSL_VPP_DAP_UNG = 0;
				double countTONG_TIEN_DAP_UNG= 0.00;
				for (String unitId : mapUnit.keySet()) {
					
					// create sheet
					 countSL_VPP_YEUCAU = 0;
					 countSL_VPP_DAP_UNG = 0;
					 countTONG_TIEN_DAP_UNG= 0.00;
					
					Sheet sheet = workbook.cloneSheet(1);
					
					// try to fix hidden sheet tabs
					workbook.setSheetHidden(i, false);
					workbook.setSheetVisibility(i, SheetVisibility.VISIBLE);
					
					workbook.setSheetName(i++, String.valueOf(unitId));
					Map<String, List<VT050015StationeryExcel>> mapIss = mapUnit.get(unitId);
					rowCount = 2;
					lengthListStationery = 2;

					// set title
					Cell cell = sheet.getRow(0).getCell(0);
					cell.setCellValue(((VT050015StationeryExcel) mapIss.entrySet().iterator().next().getValue().get(0))
							.getUnitName());
					
					
//					double limit = 0;
//				    StationeryStaff stationeryStaff = stationeryDao.getStationeryStaffByUser(username);
//				    if(stationeryStaff == null) return getLimitSpendingNV(username);
//				    
//				    List<StationeryQuota> quotas = stationeryDao.getStationeryQuotas(stationeryStaff.getUnitId());
//				    for(StationeryQuota quota : quotas) {
//				      double temp = quota.getQuantity() * quota.getQuota();
//				      if(quota.getUnitId() == stationeryStaff.getUnitId()) {
//				        limit = temp;
//				        break;
//				      } else {
//				        if(limit < temp) limit = temp;
//				      }
//				    }
					
					
					double	spendingLimit = stationeryService.getLimitSpendingNVByUnit(unitId);
					// fill data
					for (String issId : mapIss.keySet()) {
						List<VT050015StationeryExcel> itemIss = mapIss.get(issId);
						isNewIssue = true;
						lengthListStationery += itemIss.size();
						
						for (VT050015StationeryExcel sta : itemIss) {
							writeBook(sta, sheet, rowCount++, cellStyle, combinedNo, combined, combinedNote,
									isNewIssue,cellStyleNumber,cellStyleNumberFooter,spendingLimit );
							isNewIssue = false;
							
							//TODO
							
							countSL_VPP_YEUCAU = countSL_VPP_YEUCAU + sta.getTotalRequest();
							countSL_VPP_DAP_UNG = countSL_VPP_DAP_UNG + sta.getTotalFulfill();
							countTONG_TIEN_DAP_UNG = countTONG_TIEN_DAP_UNG + sta.getTotalFulfillMoney();
						}
					}
//					writeBookUnitFooter(sheet, rowCount++, cellStyle, combinedUnit, combined, combinedNote, isNewIssue,
//							countSL_VPP_YEUCAU,
//							countSL_VPP_DAP_UNG,
//							countTONG_TIEN_DAP_UNG,spendingLimit,cellStyleNumber,cellStyleNumberFooter1 );
					rangeStationery.put(unitId, lengthListStationery);
				}

				// write summary sheet
				createSummarySheet(stationeryExcel, rangeStationery, workbook.getSheetAt(0), cellStyle, combinedNo,cellStyleNumber,
						reportStationery);
				workbook.removeSheetAt(1);
				workbook.setForceFormulaRecalculation(true);

				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				}
			}
		}

		return outFile;
	}

	private void writeBookUnitFooter(Sheet sheet, int rowCount, CellStyle cellStyle,
			CellStyle combinedUnit, CellStyle combined, CellStyle combinedNote, boolean isNewIssue, int countSL_VPP_YEUCAU,int countSL_VPP_DAP_UNG,
			double countTONG_TIEN_DAP_UNG,double spendingLimit,CellStyle cellStyleNumber,CellStyle cellStyleNumberFooter1 ) {
		Row row = sheet.createRow(rowCount);
		
		// No.
		Cell cell = row.createCell(0);
		cell.setCellStyle(combinedUnit);

		// issue service id
		cell = row.createCell(1);
		cell.setCellStyle(combinedUnit);
			cell.setCellValue("Tổng cộng");

		// start time to do service
		cell = row.createCell(2);
		cell.setCellStyle(combinedUnit);
		cell.setCellValue("");
		
		
		cell = row.createCell(3);
		cell.setCellStyle(combinedUnit);
		cell.setCellValue("");
		
		cell = row.createCell(4);
		cell.setCellStyle(combinedUnit);
		cell.setCellValue("");
		// start time to do service
		cell = row.createCell(5);
		cell.setCellStyle(combinedUnit);
		cell.setCellValue(countSL_VPP_YEUCAU);

		// requester name
		cell = row.createCell(6);
		cell.setCellStyle(combinedUnit);
		cell.setCellValue(countSL_VPP_DAP_UNG);
		// note
		cell = row.createCell(7);
		cell.setCellStyle(combinedUnit);
		cell.setCellValue("");
		cell = row.createCell(8);
		cell.setCellStyle(combinedUnit);
		cell.setCellValue("");
		cell = row.createCell(9);
		cell.setCellStyle(combinedUnit);
		cell.setCellValue("");
		cell = row.createCell(10);
		cell.setCellStyle(combinedUnit);
		cell.setCellValue("");
		cell = row.createCell(11);
		cell.setCellStyle(combinedUnit);
		cell.setCellValue("");
		cell = row.createCell(12);
		cell.setCellStyle(combinedUnit);
		// note
		cell = row.createCell(13);
		cell.setCellStyle(cellStyleNumberFooter1);
		cell.setCellValue(spendingLimit);
		// note
		cell = row.createCell(14);
		cell.setCellStyle(cellStyleNumberFooter1);
		cell.setCellValue(countTONG_TIEN_DAP_UNG);
		
	}
	
	/**
	 * handle original list to appropriate list to write excel
	 * 
	 * @param stationeryExcel
	 * @return map
	 */
	public Map<String, Map<String, List<VT050015StationeryExcel>>> handleData(
			List<VT050015StationeryExcel> stationeryExcel) {
		// list sheet unit
		Map<String, Map<String, List<VT050015StationeryExcel>>> mapUnit = stationeryExcel.stream()
				.collect(Collectors.groupingBy(VT050015StationeryExcel::getUnitId, Collectors
						.groupingBy(VT050015StationeryExcel::getRequestID, LinkedHashMap::new, Collectors.toList())));
		return mapUnit;
	}

	/**
	 * @param mapUnit2
	 * @param stationeryExcel
	 * @param rangeStationery
	 * @param sheet
	 * @param cellStyle
	 * @throws JsonProcessingException 
	 */
	public void createSummarySheet(List<VT050015StationeryExcel> stationeryExcel, Map<String, Integer> rangeStationery,
			Sheet sheet, CellStyle cellStyle,CellStyle combinedNoo,CellStyle cellStyleNumber, ReportStationery condition) throws JsonProcessingException {
		// list name of unit
	  Map<String, Map<String, List<VT050015StationeryExcel>>> mapUnitName = new HashMap<>();
	  try {
	    List<VT050015StationeryExcel> clone = new ArrayList<>(stationeryExcel);
	    for (VT050015StationeryExcel s : clone) {
	      if (s.getUnitId() == null || s.getStationeryID() == null) {
	        stationeryExcel.remove(s);
	      }
	    }
	    mapUnitName = stationeryExcel.stream()
				.collect(Collectors.groupingBy(VT050015StationeryExcel::getUnitId, Collectors.groupingBy(
						VT050015StationeryExcel::getStationeryID, LinkedHashMap::new, Collectors.toList())));
	  } catch (Exception e) {
	    logger.error(e.getMessage(), e);
	  }
		CellStyle combinedUnit = sheet.getWorkbook().createCellStyle();
		StringBuilder sb = new StringBuilder("BẢNG TỔNG HỢP VPP   " +"<");
		
		List<String> lstUnitName = new ArrayList<>();
		Cell cell = sheet.getRow(0).getCell(0);
		for (String unitId : mapUnitName.keySet()) {
			Map<String, List<VT050015StationeryExcel>> mapIss = mapUnitName.get(unitId);
			for (String issId : mapIss.keySet()) {
				List<VT050015StationeryExcel> itemIss = mapIss.get(issId);
				for (VT050015StationeryExcel sta : itemIss) {
					if (!lstUnitName.contains(sta.getUnitName())){
						lstUnitName.add(sta.getUnitName());
					}
					 
				}
			}
		}
		for(int i =0 ; i< lstUnitName.size(); i++){
			sb.append(lstUnitName.get(i));
			if(i< lstUnitName.size()-1){
				sb.append(" , ");
			}
		}
		sb.append(">");
		cell.setCellStyle(combinedNoo);
		cell.setCellValue(sb.toString());
		
		int rowCount = 3;
		int numericalOrderUnit = 1;
		int numbericalOrderStationery = 1;
		Map<String, List<VT050015StationeryExcel>> mapStationeryName = null;
		Row row = null;
		String formula = "";

		// style for each unit name row
		
		Font font = sheet.getWorkbook().createFont();

		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short) 12);
		combinedUnit.cloneStyleFrom(cellStyle);
		combinedUnit.setFont(font);
		combinedUnit.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		combinedUnit.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		CellStyle combinedUnitFooter = sheet.getWorkbook().createCellStyle();
		Font fontFooter = sheet.getWorkbook().createFont();
		fontFooter.setFontName("Times New Roman");
		fontFooter.setFontHeightInPoints((short) 12);
		combinedUnitFooter.cloneStyleFrom(cellStyle);
		combinedUnitFooter.setFont(fontFooter);
		combinedUnitFooter.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		combinedUnitFooter.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		CellStyle combinedUnitTotalFooter = sheet.getWorkbook().createCellStyle();
		Font fontTotalFooter = sheet.getWorkbook().createFont();
		fontTotalFooter.setFontName("Times New Roman");
		fontTotalFooter.setFontHeightInPoints((short) 12);
		combinedUnitTotalFooter.cloneStyleFrom(cellStyle);
		combinedUnitTotalFooter.setFont(fontTotalFooter);
		combinedUnitTotalFooter.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		combinedUnitTotalFooter.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		
		CellStyle cellStyleNumberFooter = sheet.getWorkbook().createCellStyle();
		Font fontTotalNumberFooter = sheet.getWorkbook().createFont();
		fontTotalNumberFooter.setFontName("Times New Roman");
		fontTotalNumberFooter.setFontHeightInPoints((short) 12);
		cellStyleNumberFooter.cloneStyleFrom(cellStyle);
		cellStyleNumberFooter.setFont(fontTotalFooter);
		cellStyleNumberFooter.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyleNumberFooter.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyleNumberFooter.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
		
		// style for No.
		CellStyle combinedNo = sheet.getWorkbook().createCellStyle();
		combinedNo.cloneStyleFrom(cellStyle);
		combinedNo.setAlignment(HorizontalAlignment.CENTER);

		// style for No. of unit name row
		CellStyle combinedNoUnitName = sheet.getWorkbook().createCellStyle();
		combinedNoUnitName.cloneStyleFrom(combinedUnit);
		combinedNoUnitName.setAlignment(HorizontalAlignment.CENTER);

//		Cell cell = sheet.getRow(0).getCell(0);
//		cell.setCellValue("BẢNG TỔNG HỢP CẤP PHÁT VĂN PHÒNG PHẨM");

		cell = sheet.getRow(1).getCell(0);
		cell.setCellValue(createTitleDate(condition));
		
		int countSL_VPP_TONGYEUCAU = 0;
		int countSL_VPP_TONGDAP_UNG = 0;
		double countTONG_TIEN_DAP_UNGTOTAL= 0.00;
		double spendLimitTotal = 0.00;
		
		
		int countTotalRequest = 0;
		int countTotalFullFill = 0;
		double countTotalMoneyFullfill = 0.00;
		
		for (String unitId : mapUnitName.keySet()) {
			mapStationeryName = mapUnitName.get(unitId);
			numbericalOrderStationery = 1;
			row = sheet.createRow(rowCount++);
			sheet.addMergedRegion(new CellRangeAddress((rowCount - 1), (rowCount - 1), 1, 7));
			RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress((rowCount - 1), (rowCount - 1), 1, 7),
					sheet);
			formula = "ROMAN(" + numericalOrderUnit++ + ", 4)";

			cell = row.createCell(0);
			cell.setCellStyle(combinedNoUnitName);
			cell.setCellType(CellType.FORMULA);
			cell.setCellFormula(formula);

			cell = row.createCell(1);
			cell.setCellStyle(combinedUnit);
			cell.setCellValue(mapUnitName.get(unitId).get(mapUnitName.get(unitId).keySet().iterator().next()).get(0)
					.getDetailUnit());
			
			int countSL_VPP_YEUCAU = 0;
			int countSL_VPP_DAP_UNG = 0;
			double countTONG_TIEN_DAP_UNG= 0.00;
			
			double	spendingLimit = stationeryService.getLimitSpendingNVByUnit(unitId);
			for (List<VT050015StationeryExcel> stationery : mapStationeryName.values()) {
				row = sheet.createRow(rowCount++);
				
				// No.
				cell = row.createCell(0);
				cell.setCellStyle(combinedNo);
				cell.setCellValue(numbericalOrderStationery++);

				// stationery name
				cell = row.createCell(1);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(stationery.get(0).getStationeryName());

				// stationery unit calculation
				cell = row.createCell(2);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(stationery.get(0).getCalculatorUnit());

				// stationery quantity
				formula = "SUMIF('" + unitId + "'!D3:D" + rangeStationery.get(unitId) + ",\""
						+ stationery.get(0).getStationeryName() + "\",'" + unitId + "'!F3:F"
						+ rangeStationery.get(unitId) + ")";

				cell = row.createCell(3);
				cell.setCellStyle(cellStyle);
				cell.setCellType(CellType.FORMULA);
				cell.setCellFormula(formula);

				// stationery quantity
				formula = "SUMIF('" + unitId + "'!D3:D" + rangeStationery.get(unitId) + ",\""
						+ stationery.get(0).getStationeryName() + "\",'" + unitId + "'!G3:G"
						+ rangeStationery.get(unitId) + ")";

				cell = row.createCell(4);
				cell.setCellStyle(cellStyle);
				cell.setCellType(CellType.FORMULA);
				cell.setCellFormula(formula);
				
				cell = row.createCell(5);
				cell.setCellStyle(cellStyleNumber);
				cell.setCellValue(stationery.get(0).getUnitPrice());
				
				cell = row.createCell(6);
				cell.setCellStyle(cellStyle);
				
				formula = "E" + rowCount + "*F" + rowCount;
				
				cell = row.createCell(7);
				cell.setCellStyle(cellStyleNumber);
				cell.setCellType(CellType.FORMULA);
				cell.setCellFormula(formula);
				
				for (VT050015StationeryExcel item : stationery) {
					countSL_VPP_YEUCAU = countSL_VPP_YEUCAU + item.getTotalRequest();
					countSL_VPP_DAP_UNG = countSL_VPP_DAP_UNG + item.getTotalFulfill();
					countTONG_TIEN_DAP_UNG = countTONG_TIEN_DAP_UNG + item.getTotalFulfillMoney();
					
					countTotalRequest = countTotalRequest + item.getTotalRequest();
					countTotalFullFill = countTotalFullFill + item.getTotalFulfill();
					countTotalMoneyFullfill = countTotalMoneyFullfill + item.getTotalFulfillMoney();
				}
				System.out.println("count SL VPP YC " + countSL_VPP_YEUCAU);
			}
			
			System.out.println("total request " + countSL_VPP_YEUCAU);
			writeBookUnitFooterSummary(sheet, rowCount++, combinedUnitFooter, combinedNo, countSL_VPP_YEUCAU, countSL_VPP_DAP_UNG,
					countTONG_TIEN_DAP_UNG, spendingLimit, cellStyleNumber, cellStyleNumberFooter);
			
			countSL_VPP_YEUCAU = 0;
			countSL_VPP_DAP_UNG = 0;
			countTONG_TIEN_DAP_UNG = 0;
			
			countSL_VPP_TONGYEUCAU = countSL_VPP_TONGYEUCAU + countTotalRequest;
			countSL_VPP_TONGDAP_UNG =  countSL_VPP_TONGDAP_UNG + countTotalFullFill;
			countTONG_TIEN_DAP_UNGTOTAL = countTONG_TIEN_DAP_UNGTOTAL + countTotalMoneyFullfill;
			spendLimitTotal  = spendLimitTotal + spendingLimit;
		}
		
		writeBookUnitTotalFooterSummary(sheet, rowCount++, combinedUnitTotalFooter, combinedNo,countTotalRequest , 
				countTotalFullFill, countTotalMoneyFullfill,spendLimitTotal,cellStyleNumber,cellStyleNumberFooter );
		
		
		
	}

	private void writeBookUnitTotalFooterSummary(Sheet sheet, int rowCount, CellStyle combinedUnitTotalFooter,
			CellStyle combinedNo, int countSL_VPP_TONGYEUCAU,int countSL_VPP_TONGDAP_UNG, double countTONG_TIEN_DAP_UNGTOTAL,double spendLimitTotal,
			CellStyle cellStyleNumber,CellStyle cellStyleNumberFooter ) {
		Row row = sheet.createRow(rowCount);

		// No.
		Cell cell = row.createCell(0);
		cell.setCellStyle(combinedUnitTotalFooter);

		// issue service id
		cell = row.createCell(1);
		cell.setCellStyle(combinedUnitTotalFooter);
		cell.setCellValue("Tổng cộng TCT");

		// start time to do service
		cell = row.createCell(3);
		cell.setCellStyle(combinedUnitTotalFooter);
		cell.setCellValue(countSL_VPP_TONGYEUCAU);
		// start time to do service
		cell = row.createCell(2);
		cell.setCellStyle(combinedUnitTotalFooter);
		//TODO: sum this column
		cell.setCellValue("");

		// requester name
		cell = row.createCell(4);
		cell.setCellStyle(combinedUnitTotalFooter);
		cell.setCellValue(countSL_VPP_TONGDAP_UNG);
		// note
		cell = row.createCell(5);
		cell.setCellStyle(combinedUnitTotalFooter);
		cell.setCellValue("");
		// note
		cell = row.createCell(6);
		cell.setCellStyle(cellStyleNumberFooter);
		cell.setCellValue(spendLimitTotal);
		
		// note
		cell = row.createCell(7);
		cell.setCellStyle(cellStyleNumberFooter);
		cell.setCellValue(countTONG_TIEN_DAP_UNGTOTAL);
		
	}
	
	private void writeBookUnitFooterSummary(Sheet sheet, int rowCount, CellStyle combinedUnitFooter,
			CellStyle combinedNo, int countSL_VPP_YEUCAU,int countSL_VPP_DAP_UNG, double countTONG_TIEN_DAP_UNG,
			double spendingLimit,CellStyle cellStyleNumber, CellStyle cellStyleNumberFooter) {
		Row row = sheet.createRow(rowCount);

		// No.
		Cell cell = row.createCell(0);
		cell.setCellStyle(combinedUnitFooter);

		// issue service id
		cell = row.createCell(1);
		cell.setCellStyle(combinedUnitFooter);
		cell.setCellValue("Tổng cộng");

		// start time to do service
		cell = row.createCell(2);
		cell.setCellStyle(combinedUnitFooter);
		//TODO: sum this column
		cell.setCellValue("");
		
		// start time to do service
		cell = row.createCell(3);
		cell.setCellValue(countSL_VPP_YEUCAU);
		System.out.println("cell value " + cell.getNumericCellValue());
		

		// requester name
		cell = row.createCell(4);
		cell.setCellStyle(combinedUnitFooter);
		cell.setCellValue(countSL_VPP_DAP_UNG);
		// note
		cell = row.createCell(5);
		cell.setCellStyle(combinedUnitFooter);
		cell.setCellValue("");
		// note
		System.out.println("spendingLimit " + spendingLimit);
		cell = row.createCell(6);
		cell.setCellStyle(cellStyleNumber);
		cell.setCellValue(spendingLimit);
		System.out.println("cell value " + cell.getNumericCellValue());
		
		
		// note
		cell = row.createCell(7);
		cell.setCellValue(countTONG_TIEN_DAP_UNG);
		cell.setCellStyle(cellStyleNumber);
	}
	
	/**
	 * fill data to cell
	 * 
	 * @param stationeryExcel
	 * @param sheet
	 * @param rowNum
	 */
	private void writeBook(VT050015StationeryExcel itemIss, Sheet sheet, int rowCount, CellStyle cellStyle,
			CellStyle combinedNo, CellStyle combined, CellStyle combinedNote, boolean isNewIssue,CellStyle cellStyleNumber,CellStyle cellStyleNumberFooterBook , double spendingLimit) {

		Row row = sheet.createRow(rowCount);

		// No.
		Cell cell = row.createCell(0);
		cell.setCellStyle(combinedNo);
		cell.setCellValue( rowCount - 1);

		// issue service id
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);
		if (isNewIssue) {
			cell.setCellValue(itemIss.getRequestID());
		}

		// start time to do service
		cell = row.createCell(2);
		cell.setCellStyle(combined);
		if (isNewIssue) {
			cell.setCellValue(itemIss.getPlaceName());
		}
		// start time to do service
		cell = row.createCell(3);
		cell.setCellStyle(combined);
		cell.setCellValue(itemIss.getStationeryName());

		// requester name
		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemIss.getCalculatorUnit());

		// note
		cell = row.createCell(5);
		cell.setCellStyle(combinedNote);
		cell.setCellValue(itemIss.getTotalRequest());

		// note
		cell = row.createCell(6);
		cell.setCellStyle(combinedNote);
		cell.setCellValue(itemIss.getTotalFulfill());

		// note
				cell = row.createCell(7);
				cell.setCellStyle(combinedNote);
				if (isNewIssue) {
					SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					cell.setCellValue(itemIss.getDateExecute() != null ? df.format(itemIss.getDateExecute()) : "");
				}

				// note
				cell = row.createCell(8);
				cell.setCellStyle(combinedNote);
				if (isNewIssue) {
					cell.setCellValue(itemIss.getUserReceive());
				}

				// note
				cell = row.createCell(9);
				cell.setCellStyle(combinedNote);
				if (isNewIssue) {
					cell.setCellValue(itemIss.getUserExecute());
				}

				// note
				cell = row.createCell(10);
				cell.setCellStyle(combinedNote);
				if (isNewIssue) {
//					status.add(Constant.STATIONERY_APPROVED_WAIT_MANAGER_APPROVE);
//					status.add(Constant.STATIONERY_APPROVED_MANAGER_APPROVE);
//					status.add(Constant.STATIONERY_APPROVED_MANAGER_REJECT);
//					status.add(Constant.STATIONERY_APPROVED_RECEIVE_REQUEST);
//					status.add(Constant.STATIONERY_APPROVED_PAUSE);
//					status.add(Constant.STATIONERY_APPROVED_REJECT);
//					status.add(Constant.STATIONERY_APPROVED_EXECUTING);
//					status.add(Constant.STATIONERY_APPROVED_FINISH);

					if (itemIss.getStatus() == Constant.STATIONERY_APPROVED_WAIT_MANAGER_APPROVE) {
						cell.setCellValue("Chờ LĐ phê duyệt");
					}
					if (itemIss.getStatus() == Constant.STATIONERY_APPROVED_MANAGER_APPROVE) {
						cell.setCellValue("Lãnh đạo phê duyệt");
					}
					if (itemIss.getStatus() == Constant.STATIONERY_APPROVED_MANAGER_REJECT) {
						cell.setCellValue("Lãnh đạo từ chối");
					}
					if (itemIss.getStatus() == Constant.STATIONERY_APPROVED_RECEIVE_REQUEST) {
						cell.setCellValue("VP TCT tiếp nhận");
					}
					if (itemIss.getStatus() == Constant.STATIONERY_APPROVED_PAUSE) {
						cell.setCellValue("VP TCT hoãn thực hiện");
					}
					if(itemIss.getStatus() == Constant.STATIONERY_APPROVED_REJECT) {
						cell.setCellValue("VP TCT không thực hiện được");
					}
					if(itemIss.getStatus() == Constant.STATIONERY_APPROVED_EXECUTING) {
						cell.setCellValue("VP TCT cấp phát cho HCĐV");
					}
					if(itemIss.getStatus() == Constant.STATIONERY_APPROVED_FINISH) {
						cell.setCellValue("HCĐV xác nhận VPP");
					}if(itemIss.getStatus() == Constant.STATIONERY_APPROVED_COMPLETE) {
						cell.setCellValue("Từ chối xác nhận");
					}
					
				}

				// note
				cell = row.createCell(11);
				cell.setCellStyle(combinedNote);
				if (isNewIssue) {
					cell.setCellValue(itemIss.getNote());
				}
		
		
		
		// note
		cell = row.createCell(12);
		cell.setCellStyle(cellStyleNumber);
		cell.setCellValue(itemIss.getUnitPrice());

		// note
		cell = row.createCell(13);
		cell.setCellStyle(cellStyleNumber);
		cell.setCellValue(spendingLimit);
		// note
		cell = row.createCell(14);
		cell.setCellStyle(cellStyleNumber);
		cell.setCellValue(itemIss.getTotalFulfillMoney());
		
	
	}

	public String createTitleDate(ReportStationery condition) {
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
