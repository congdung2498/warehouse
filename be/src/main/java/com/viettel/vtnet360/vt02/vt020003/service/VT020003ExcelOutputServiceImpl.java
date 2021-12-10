package com.viettel.vtnet360.vt02.vt020003.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.viettel.vtnet360.vt02.vt020003.dao.VT020003DAO;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003KitchenInfo;

@Service
public class VT020003ExcelOutputServiceImpl implements VT020003ExcelOutputService {
	
	private static final Logger logger = Logger.getLogger(VT020003ExcelOutputServiceImpl.class);
	
	@Autowired
	VT020003DAO vt020013dao;
	
	
	@Override
	public File createExcelOutputExcel(List<VT020003KitchenInfo> kitchenList) throws Exception {
		File file = null;
		String excelFilePath = "/var/templateExcel/VT020003.xlsx";
		file = writeExcel(kitchenList, excelFilePath);
		
		return file;
	}
	
	public File writeExcel(List<VT020003KitchenInfo> kitchenList, String excelFilePath) throws IOException {
		
		File outFile = null;
		File file = null;

		Date date = new Date();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		String fileName = cal.get(Calendar.YEAR) + "_" + (cal.get(Calendar.MONTH) + 1)
				+ "_" + cal.get(Calendar.DAY_OF_MONTH) + "_" + cal.get(Calendar.HOUR_OF_DAY) + "h"
				+ cal.get(Calendar.MINUTE) + "m" + cal.get(Calendar.SECOND) + "s" + cal.get(Calendar.MILLISECOND)
				+ "ms.xlsx";
		try {
			Resource resource = new ClassPathResource(excelFilePath);
			file = resource.getFile();
			String filePath = file.getAbsolutePath();
			outFile = new File(filePath.split("templateExcel")[0] + "saveExcel\\VT02\\" + fileName);
			if(kitchenList.isEmpty()) return file;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		try (FileInputStream inputStream = new FileInputStream(file)) {
			try (Workbook workbook = new XSSFWorkbook(inputStream)) {
				Sheet sheet = workbook.getSheetAt(0);
				CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
				cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
				cellStyle.setFillPattern(FillPatternType.NO_FILL);
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				cellStyle.setBorderTop(BorderStyle.THIN);
				Font font = workbook.createFont();
				font.setFontName("Times New Roman");;
				font.setFontHeightInPoints((short) 12);
				cellStyle.setFont(font);
				int startRow = 1;
				Row row = sheet.getRow(0);
				row = sheet.createRow(startRow + 1);
				
				for (VT020003KitchenInfo kitchen : kitchenList) {
					int rows=sheet.getLastRowNum();
					sheet.shiftRows(++startRow,rows,1);
					row = sheet.createRow(startRow);
					
					// STT
					Cell cell = row.createCell(0);
				    cell.setCellValue(startRow - 1);
				    CellStyle cellStyle2 = sheet.getWorkbook().createCellStyle();
				    cellStyle2.cloneStyleFrom(cellStyle);
				    cellStyle2.setAlignment(HorizontalAlignment.CENTER);
				    cell.setCellStyle(cellStyle2);
				    
				    // name
				    cell = row.createCell(1);
				    cell.setCellValue(kitchen.getKitchenName());
				    cell.setCellStyle(cellStyle);
				    // location
				    cell = row.createCell(2);
				    cell.setCellValue(kitchen.getPlaceName());
				    cell.setCellStyle(cellStyle);
					// chef
				    cell = row.createCell(3);
				    cell.setCellValue(kitchen.getChefName());
				    cell.setCellStyle(cellStyle);
				    // note
				    cell = row.createCell(4);
				    cell.setCellValue(kitchen.getNote());
				    cell.setCellStyle(cellStyle);
					// status
				    cell = row.createCell(5);
				    cell.setCellValue(kitchen.getStatus() == 1 ? "Hoạt động":"Ngưng hoạt động");
				    cell.setCellStyle(cellStyle);
				}
			    
				/*cellNumber = 0;
				row = sheet.createRow(++rowCount);
				Cell cell = row.createCell(1);
			    cell.setCellValue(listVt020013KitchenReport.getValue().get(0).getUnitName());
			    for (VT020013KitchenReport day : listVt020013KitchenReport.getValue()) {
			    	cell = row.createCell(day.getResultDay());
					cell.setCellValue(day.getTotalMeal());
				}*/
				// set border table
				setRegionBorderWith(new CellRangeAddress(2, sheet.getLastRowNum() - 1, 0, 5), sheet);
				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				}
			}
		}
		return outFile;
	}
	
	private void setRegionBorderWith(CellRangeAddress region, Sheet sheet) {
		RegionUtil.setBorderBottom(BorderStyle.MEDIUM, region, sheet);
		RegionUtil.setBorderLeft(BorderStyle.MEDIUM, region, sheet);
		RegionUtil.setBorderRight(BorderStyle.MEDIUM, region, sheet);
		RegionUtil.setBorderTop(BorderStyle.MEDIUM, region, sheet);
	}
}
