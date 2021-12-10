package com.viettel.vtnet360.vt02.vt020013.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.vtnet360.vt02.vt020013.dao.VT020013DAO;
import com.viettel.vtnet360.vt02.vt020013.entity.VT020013KitchenReport;

class Tuple {
	public Tuple(Double priceEach2, String unitName2) {
		this.priceEach = priceEach2;
		this.unitName = unitName2;
	}
	Double priceEach;
	String unitName;
	public Double getPriceEach() {
		return priceEach;
	}
	public void setPriceEach(Double priceEach) {
		this.priceEach = priceEach;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}

@Service
public class VT020013ExcelOutputServiceImpl implements VT020013ExcelOutputService {
	
	private static final Logger logger = Logger.getLogger(VT020013ExcelOutputServiceImpl.class);
	
	/** Data Access Object */
	@Autowired
	VT020013DAO vt020013dao;
	
	/**
	 * Create excel file
	 */
	@Override
    public File createExcelOutputExcel(List<VT020013KitchenReport> kitchenList, String month) throws Exception {
		File file = null;
		String excelFilePath = "/var/templateExcel/VT020013.xlsx";
		file = writeExcel(kitchenList, excelFilePath, month);
		
		return file;
    }

	/**
	 * Create content excel file
	 * @param listBook
	 * @param excelFilePath
	 * @param sheetName
	 * @throws IOException
	 */
	public File writeExcel(List<VT020013KitchenReport> kitchenList, String excelFilePath, String month) throws IOException {
		
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
		
		@SuppressWarnings("unchecked")
		Map<String, Map<String, Map<Double, List<VT020013KitchenReport>>>> kitchenGroupByNameAndPrice = kitchenList.stream()
				  .collect(Collectors.groupingBy(VT020013KitchenReport::getIDString, Collectors.groupingBy(VT020013KitchenReport::getCodeandPrice, Collectors.groupingBy(VT020013KitchenReport::getPriceEach))))
				  .entrySet().stream().sorted(Map.Entry.comparingByValue((oldValue, newValue) ->  
				  	((List<VT020013KitchenReport>)oldValue.get(oldValue.keySet().toArray()[0]).values().toArray()[0])
					.get(0).getFullName().compareTo(
					((List<VT020013KitchenReport>)newValue.get(newValue.keySet().toArray()[0]).values().toArray()[0])
					.get(0).getFullName())))
				  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
							(oldValue, newValue) -> oldValue, LinkedHashMap::new));
		
		
    
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
				int startRow = 2;
				Row row = sheet.getRow(0);
				DataFormat format = workbook.createDataFormat();
				for (Entry<String, Map<String, Map<Double, List<VT020013KitchenReport>>>> listVt020013KitchenReport : kitchenGroupByNameAndPrice.entrySet()) {
					Map<String, Map<Double, List<VT020013KitchenReport>>> sorted = listVt020013KitchenReport.getValue().entrySet().stream().sorted(Map.Entry.comparingByValue((oldValue, newValue) ->  
					oldValue.get(oldValue.keySet().toArray()[0]).get(0).getFullName().compareTo(newValue.get(newValue.keySet().toArray()[0]).get(0).getFullName()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
							(oldValue, newValue) -> oldValue, LinkedHashMap::new));
					for (Entry<String, Map<Double, List<VT020013KitchenReport>>>  kitchenReport : sorted.entrySet()) {
						Map<Double, List<VT020013KitchenReport>> sorted2 = kitchenReport.getValue().entrySet().stream().sorted(Map.Entry.comparingByValue((oldValue, newValue) -> 
						oldValue.get(0).getFullName().compareTo(newValue.get(0).getFullName()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
								(oldValue, newValue) -> oldValue, LinkedHashMap::new));
						
						for (Entry<Double, List<VT020013KitchenReport>>  kitchen : sorted2.entrySet()) {
						  
						int rows=sheet.getLastRowNum();
						sheet.shiftRows(++startRow,rows,1);
						row = sheet.createRow(startRow);
						
						// STT
						Cell cell = row.createCell(0);
					    cell.setCellValue(startRow - 2);
					    CellStyle cellStyle2 = sheet.getWorkbook().createCellStyle();
					    cellStyle2.cloneStyleFrom(cellStyle);
					    cellStyle2.setAlignment(HorizontalAlignment.CENTER);
					    cell.setCellStyle(cellStyle2);
						
					    // userID
					    cell = row.createCell(1);
					    cell.setCellValue(kitchen.getValue().get(0).getCode());
					    cell.setCellStyle(cellStyle);
					    
					    // userName
					    cell = row.createCell(2);
					    cell.setCellValue(kitchen.getValue().get(0).getFullName());
					    CellStyle cellStyle3 = sheet.getWorkbook().createCellStyle();
					    cellStyle3.cloneStyleFrom(cellStyle);
					    cellStyle3.setWrapText(true);;
					    cell.setCellStyle(cellStyle3);
					    
						// unit name
					    cell = row.createCell(3);
					    cell.setCellValue(kitchen.getValue().get(0).getDetailUnit());
					    cell.setCellStyle(cellStyle3);
					    
					    // kitchen name
					    cell = row.createCell(4);
					    cell.setCellValue(kitchen.getValue().get(0).getKitchenName());
					    cell.setCellStyle(cellStyle3);
					    
					    CellStyle cellStyle4 = sheet.getWorkbook().createCellStyle();
					    cellStyle4.cloneStyleFrom(cellStyle);
					    cellStyle4.setWrapText(true);
					    cellStyle4.setDataFormat(format.getFormat("#,###,###,###,###,##0"));
					    cell.setCellStyle(cellStyle4);
					    for (int i = 5; i < 36; i++) {
					    	cell = row.createCell(i);
					    	cell.setCellStyle(cellStyle4);
						}
					    
					    // month
					    for (VT020013KitchenReport day : kitchen.getValue()) {
					    	cell = row.createCell(day.getResultDay() + 4);
							cell.setCellValue(Integer.parseInt(day.getTotalMeal().toPlainString()));
							cell.setCellStyle(cellStyle4);
						}
					    
					    cell = row.createCell(36);
					    cell.setCellFormula("SUM(F" + (startRow + 1) + ":AJ" + (startRow + 1) + ")");
					    cell.setCellStyle(cellStyle4);
					    
					    cell = row.createCell(37);
					    cell.setCellValue(kitchen.getKey().doubleValue());
					    cell.setCellStyle(cellStyle4);
					    
					    cell = row.createCell(38);
					    cell.setCellFormula("AK" + (startRow + 1) + "*" + "AL" + (startRow + 1));
					    cell.setCellStyle(cellStyle4);
						}
					}
				}
				row = sheet.getRow(++startRow);
				CellStyle cellStyle4 = sheet.getWorkbook().createCellStyle();
			    cellStyle4.cloneStyleFrom(cellStyle);
			    cellStyle4.setWrapText(true);
			    cellStyle4.setDataFormat(format.getFormat("#,###,###,###,###,##0"));
			    
				Cell cell = row.getCell(36);
				cell.setCellFormula("SUM(AK4:AK" + startRow + ")");
				cell.setCellStyle(cellStyle4);
				
				cell = row.getCell(38);
				cell.setCellFormula("SUM(AM4:AM" + startRow + ")");
				cell.setCellStyle(cellStyle4);
			    
				row = sheet.getRow(1);
				cell = row.getCell(5);
				cell.setCellValue("Tháng " + month);
				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				} catch(Exception ex) {
		      logger.error(ex.getMessage(), ex);
		    }
			} catch(Exception ex) {
	      logger.error(ex.getMessage(), ex);
	    }
		} catch(Exception ex) {
		  logger.error(ex.getMessage(), ex);
		}
		return outFile;
	}
}
