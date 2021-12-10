package com.viettel.vtnet360.stationery.service;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelUtil {
  
  public static void createDropdown(String[] data, XSSFSheet sheet, Workbook workbook) throws Exception {
    CellRangeAddressList addressList = new CellRangeAddressList(4, 50, 1, 1);
    
    XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
    XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint)
    dvHelper.createExplicitListConstraint(data);
    XSSFDataValidation validation = (XSSFDataValidation)dvHelper.createValidation(
    dvConstraint, addressList);
    validation.setShowErrorBox(true);
    sheet.addValidationData(validation);
  }
}
