package com.viettel.vtnet360.vt00.vt000000.service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ExcelOutputServiceImpl implements ExcelOutputService{
	private static final Logger LOGGER = Logger.getLogger(ExcelOutputServiceImpl.class);
	
	/**
	 * Create excel file
	 */
    @Override
    public File createExcelOutputExcel() {
		List<Book> listBook = getListBook();
		String excelFilePath = "D:/Demo.xlsx";
		try {
			File file = writeExcel(listBook, excelFilePath, null);
			return file;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;

    }

	/**
	 * Create content excel file
	 * @param listBook
	 * @param excelFilePath
	 * @param sheetName
	 * @throws IOException
	 */
	public File writeExcel(List<Book> listBook, String excelFilePath, String sheetName) throws IOException {
		Workbook workbook = getWorkbook(excelFilePath);
		Sheet sheet = null;
		
		// Create sheet name
		if (StringUtils.isEmpty(sheetName))
			sheet = workbook.createSheet();
		else 
			sheet = workbook.createSheet(sheetName);
		
		// Cell style
		createHeaderRow(sheet);
		
		int rowCount = 0;
		
		for (Book aBook : listBook) {
			Row row = sheet.createRow(++rowCount);
			writeBook(aBook, row);
		}
		File file = new File(excelFilePath);
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			workbook.write(outputStream);
		}
		return file;
	}
	
	/**
	 * Fill data into cell
	 * @param aBook
	 * @param row
	 */
	private void writeBook(Book aBook, Row row) {
	    Cell cell = row.createCell(1);
	    cell.setCellValue(aBook.getTitle());
	 
	    cell = row.createCell(2);
	    cell.setCellValue(aBook.getAuthor());
	 
	    cell = row.createCell(3);
	    cell.setCellValue(aBook.getPrice());
	}
	
	/**
	 * Fill value/style 
	 * @param sheet
	 */
	private void createHeaderRow(Sheet sheet) {
		 
	    CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
	    Font font = sheet.getWorkbook().createFont();
	    font.setBold(true);
	    font.setFontHeightInPoints((short) 16);
	    cellStyle.setFont(font);
	 
	    Row row = sheet.createRow(0);
	    Cell cellTitle = row.createCell(1);
	 
	    cellTitle.setCellStyle(cellStyle);
	    cellTitle.setCellValue("Title");
	 
	    Cell cellAuthor = row.createCell(2);
	    cellAuthor.setCellStyle(cellStyle);
	    cellAuthor.setCellValue("Author");
	 
	    Cell cellPrice = row.createCell(3);
	    cellPrice.setCellStyle(cellStyle);
	    cellPrice.setCellValue("Price");
	}
	
	/**
	 * Option type excel xlsx/xls
	 * @param excelFilePath
	 * @return
	 * @throws IOException
	 */
	private Workbook getWorkbook(String excelFilePath)
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

	// START HARD CODE
	private List<Book> getListBook() {
	    Book book1 = new Book("Head First Java", "Kathy Serria", 79);
	    Book book2 = new Book("Effective Java", "Joshua Bloch", 36);
	    Book book3 = new Book("Clean Code", "Robert Martin", 42);
	    Book book4 = new Book("Thinking in Java", "Bruce Eckel", 35);
	 
	    List<Book> listBook = Arrays.asList(book1, book2, book3, book4);
	 
	    return listBook;
	}
	
	public class Book {
		 private String title;
		    private String author;
		    private float price;
		 
		    public Book() {
		    }
		 
		    public Book(String title, String author, float price) {
		        this.title = title;
		        this.author = author;
		        this.price = price;
		    }

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getAuthor() {
				return author;
			}

			public void setAuthor(String author) {
				this.author = author;
			}

			public float getPrice() {
				return price;
			}

			public void setPrice(float price) {
				this.price = price;
			}
	}
	// END HARD CODE
}
