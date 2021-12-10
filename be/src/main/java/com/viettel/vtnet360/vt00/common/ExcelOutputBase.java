package com.viettel.vtnet360.vt00.common;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.viettel.vtnet360.vt00.vt000000.service.ExcelOutputServiceImpl;
import com.viettel.vtnet360.vt00.vt000000.service.ExcelOutputServiceImpl.Book;

/**
 * 
 * @author VinhNVQ 9/17/2018
 *
 */
public abstract class ExcelOutputBase {
	
	private static final Logger LOGGER = Logger.getLogger(ExcelOutputServiceImpl.class);

	/**
	 * Create content excel file
	 * @param listBook
	 * @param excelFilePath
	 * @param sheetName
	 * @throws IOException
	 * 
	 * @author VinhNVQ 9/17/2018
	 */
	public abstract File writeExcel(List<Book> listBook, String excelFilePath, String sheetName) throws IOException;
	
	/**
	 * Fill data into cell
	 * @param aBook
	 * @param row
	 * 
	 * @author VinhNVQ 9/17/2018
	 */
	public abstract void writeBook(Book aBook, Row row);
	/**
	 * Fill value/style 
	 * @param sheet
	 * 
	 * @author VinhNVQ 9/17/2018
	 */
	public abstract void createHeaderRow(Sheet sheet);
	
	/**
	 * Option type excel xlsx/xls
	 * @param excelFilePath
	 * @return
	 * @throws IOException
	 * 
	 * @author VinhNVQ 9/17/2018
	 */
	public Workbook getWorkbook(String excelFilePath)
	        throws IOException {
	    Workbook workbook = null;
	 
	    if (excelFilePath.endsWith("xlsx")) {
	        workbook = new XSSFWorkbook();
	    } else if (excelFilePath.endsWith("xls")) {
	        workbook = new HSSFWorkbook();
	    } else {
			LOGGER.error(new IllegalArgumentException("The specified file is not Excel file"));
	        throw new IllegalArgumentException("The specified file is not Excel file");
	    }
	    return workbook;
	}
}
