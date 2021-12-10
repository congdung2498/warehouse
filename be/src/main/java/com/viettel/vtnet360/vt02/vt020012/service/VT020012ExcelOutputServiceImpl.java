package com.viettel.vtnet360.vt02.vt020012.service;

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
import java.util.stream.Stream;

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

import com.viettel.vtnet360.vt02.vt020012.dao.VT020012DAO;
import com.viettel.vtnet360.vt02.vt020012.entity.VT020012KitchenReport;

/**
 * 
 * @author VinhNVQ
 *
 */
@Service
public class VT020012ExcelOutputServiceImpl implements VT020012ExcelOutputService {
	
	private static final Logger logger = Logger.getLogger(VT020012ExcelOutputServiceImpl.class);
	
	/** Data Access Object */
	@Autowired
	VT020012DAO vt020012dao;
	
	/**
	 * Create excel file
	 */
	@Override
    public File createExcelOutputExcel(List<VT020012KitchenReport> kitchenList, String month) throws Exception {
		File file = null;
		String excelFilePath = "/var/templateExcel/VT020012.xlsx";
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
	@SuppressWarnings("unchecked")
	public File writeExcel(List<VT020012KitchenReport> kitchenList, String excelFilePath, String month) throws IOException {
		
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
		//Map<Long, Map<String, Map<Double, List<VT020012KitchenReport>>>>
		Stream<Entry<Long, Map<String, LinkedHashMap<Double, List<VT020012KitchenReport>>>>> kitchenGroupByNameAndPriceStream = kitchenList.stream()
				.collect(Collectors.groupingBy(VT020012KitchenReport::getUnitId, 
						Collectors.groupingBy(VT020012KitchenReport::getKitchenId, 
								Collectors.groupingBy(VT020012KitchenReport::getPriceEach, LinkedHashMap::new, 
										Collectors.toList()))))
				.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue((oldValue, newValue) -> 
					((List<VT020012KitchenReport>)oldValue.get(oldValue.keySet().toArray()[0]).values().toArray()[0])
						.get(0).getUnitName().compareTo(
					((List<VT020012KitchenReport>)newValue.get(newValue.keySet().toArray()[0]).values().toArray()[0])
						.get(0).getUnitName()) == 0 ? 
					((List<VT020012KitchenReport>)oldValue.get(oldValue.keySet().toArray()[0]).values().toArray()[0])
						.get(0).getKitchenName().compareTo(
					((List<VT020012KitchenReport>)newValue.get(newValue.keySet().toArray()[0]).values().toArray()[0])
						.get(0).getKitchenName()) 
					:((List<VT020012KitchenReport>)oldValue.get(oldValue.keySet().toArray()[0]).values().toArray()[0])
						.get(0).getUnitName().compareTo(
					((List<VT020012KitchenReport>)newValue.get(newValue.keySet().toArray()[0]).values().toArray()[0])
						.get(0).getUnitName())
				));
		
		Map<Long,Map<String,LinkedHashMap<Double,List<VT020012KitchenReport>>>> kitchenGroupByNameAndPrice = kitchenGroupByNameAndPriceStream
				.collect(Collectors
						.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		
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
				for (Entry<Long, Map<String, LinkedHashMap<Double, List<VT020012KitchenReport>>>> listVt020012KitchenPreReport : kitchenGroupByNameAndPrice.entrySet()) {
					//Entry<Long, Map<Double, List<VT020012KitchenReport>>> 
					for (Entry<String, LinkedHashMap<Double, List<VT020012KitchenReport>>> listVt020012KitchenReport : listVt020012KitchenPreReport.getValue().entrySet()) {
						Map<Double, List<VT020012KitchenReport>> sorted = listVt020012KitchenReport.getValue().entrySet().stream().sorted(Map.Entry.comparingByValue((oldValue, newValue) -> oldValue.get(0).getResultDay().compareTo(newValue.get(0).getResultDay()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
								(oldValue, newValue) -> oldValue, LinkedHashMap::new));
						for (Entry<Double, List<VT020012KitchenReport>>  kitchen : sorted.entrySet()) {
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
						    
						    VT020012KitchenReport report = (VT020012KitchenReport)kitchen.getValue().toArray()[0];
						    if(report.getDetailUnit() == null || report.getDetailUnit().length() == 0) report.setDetailUnit("Khác (chưa có đơn vị)");
							// unit name
						    cell = row.createCell(1);
						    cell.setCellValue(report.getDetailUnit());
						    CellStyle cellStyle3 = sheet.getWorkbook().createCellStyle();
						    cellStyle3.cloneStyleFrom(cellStyle);
						    cellStyle3.setWrapText(true);
						    cell.setCellStyle(cellStyle3);
						    
						    // kitchen name
						    cell = row.createCell(2);
						    cell.setCellValue(((VT020012KitchenReport)kitchen.getValue().toArray()[0]).getKitchenName());;
						    cell.setCellStyle(cellStyle3);
						    
						    CellStyle cellStyle4 = sheet.getWorkbook().createCellStyle();
						    cellStyle4.cloneStyleFrom(cellStyle);
						    cellStyle4.setWrapText(true);
						    cellStyle4.setDataFormat(format.getFormat("#,###,###,###,###,##0"));
						    cell.setCellStyle(cellStyle4);
						    for (int i = 3; i < 34; i++) {
						    	cell = row.createCell(i);
						    	cell.setCellStyle(cellStyle4);
							}
						    // month
						    for (VT020012KitchenReport day : kitchen.getValue()) {
						    	cell = row.getCell(day.getResultDay() + 2);
								cell.setCellValue(Integer.parseInt(day.getTotalMeal().toPlainString()));
								cell.setCellStyle(cellStyle4);
							}
						    
						    cell = row.createCell(34);
						    cell.setCellFormula("SUM(D" + (startRow + 1) + ":AH" + (startRow + 1) + ")");
					    	cell.setCellStyle(cellStyle4);
					    	
						    cell = row.createCell(35);
						    cell.setCellValue(kitchen.getKey().doubleValue());
						    cell.setCellStyle(cellStyle4);
						    
						    cell = row.createCell(36);
						    cell.setCellFormula("AI" + (startRow + 1) + "*" + "AJ" + (startRow + 1));
						    cell.setCellStyle(cellStyle4);
						}
					}
				}
				row = sheet.getRow(++startRow);
				Cell cell = row.getCell(34);
				cell.setCellFormula("SUM(AI4:AI" + startRow + ")");
				CellStyle cellStyle4 = sheet.getWorkbook().createCellStyle();
			    cellStyle4.cloneStyleFrom(cellStyle);
			    cellStyle4.setWrapText(true);
			    cellStyle4.setDataFormat(format.getFormat("#,###,###,###,###,##0"));
			    cell.setCellStyle(cellStyle4);
			    
				cell = row.getCell(36);
				cell.setCellFormula("SUM(AK4:AK" + startRow + ")");
				cell.setCellStyle(cellStyle4);

				// set month of result
				row = sheet.getRow(1);
				cell = row.getCell(3);
				cell.setCellValue("Tháng " + month);
				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				}
			}
		}
		return outFile;
	}
	
}
