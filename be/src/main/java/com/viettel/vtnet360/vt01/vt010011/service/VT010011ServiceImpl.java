package com.viettel.vtnet360.vt01.vt010011.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityMapStatus;
import com.viettel.vtnet360.vt01.vt010000.service.VT010000Service;
import com.viettel.vtnet360.vt01.vt010011.dao.VT010011DAO;
import com.viettel.vtnet360.vt01.vt010011.entity.VT010011ListCondition;
import com.viettel.vtnet360.vt01.vt010011.entity.VT010011ListEmployee;
import com.viettel.vtnet360.vt01.vt010011.entity.VT010011ListInOut;

/**
 * class to do business VT010011
 * 
 * @author ThangBT 07/09/2018
 *
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT010011ServiceImpl implements VT010011Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT010011DAO vt010011Dao;

	@Autowired
	private VT010000Service vt010000Service;

	@Autowired
	Properties linkTemplateExcel;

	/**
	 * get list of employee
	 * 
	 * @param empInfo
	 * @return List<VT010011ListEmployee>
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListEmployee(VT010011ListEmployee empInfo) throws Exception {

		logger.info("**************** Start get list employees ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		List<VT010011ListEmployee> employees = new ArrayList<>();
		if (empInfo.getResult() != null) {
			String parttern = empInfo.getResult();
			empInfo.setResult(parttern.trim());
		}

		try {
			employees = vt010011Dao.findListEmployee(empInfo);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			reb.setData(employees);

			logger.info("**************** End get list employees - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list employees - Fail ****************");

			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * validate conditions
	 * 
	 * @param vlc
	 * @param listRole
	 * @return boolean
	 * @throws Exception
	 */
	public boolean setUpCondition(VT010011ListCondition vlc, Collection<GrantedAuthority> listRole) throws Exception {

		boolean isRole = false;
		try {
			if (listRole != null && !listRole.isEmpty()) {
				if (vlc.getStartDate() != null) {
					Date startDate = vlc.getStartDate();
					Calendar cal = GregorianCalendar.getInstance();
					cal.setTime(startDate);
					String newDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DAY_OF_MONTH) + " 00:00:00";
					vlc.setStartDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse(newDate));
				}

				if (vlc.getEndDate() != null) {
					Date endDate = vlc.getEndDate();
					Calendar cal = GregorianCalendar.getInstance();
					cal.setTime(endDate);
					String newDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DAY_OF_MONTH) + " 23:59:59";
					vlc.setEndDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse(newDate));
				}

				if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
						|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))
						|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP))
						|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_GUARD)))
					vlc.setLoginRole(null);
				else
					vlc.setLoginRole(Constant.PMQT_ROLE_EMPLOYYEE);

				isRole = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return isRole;
	}

	/**
	 * get list registration go out
	 * 
	 * @param vlc
	 * @param principal
	 * @return List<VT010011ListCondition>
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListInOut(VT010011ListCondition vlc, Collection<GrantedAuthority> listRole)
			throws Exception {

		List<VT010011ListInOut> listInOuts = new ArrayList<>();

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			logger.info("**************** Start get list in out ****************");
			if (vlc.getPersonInfo() != null) {
				String parttern = vlc.getPersonInfo();
				vlc.setPersonInfo(parttern.trim());
			}

			if (setUpCondition(vlc, listRole)) {
				listInOuts = vt010011Dao.findListInOut(vlc);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				reb.setData(listInOuts);

				logger.info("**************** End get list in out - OK ****************");
			}
		} catch (Exception e) {
			logger.info("**************** End get list in out - Fail ****************");

			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * count total record VT010011
	 * 
	 * @param vlc
	 * @param principal
	 * @return number of records
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase countTotalRecord(VT010011ListCondition vlc, Collection<GrantedAuthority> listRole)
			throws Exception {

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, -1);

		int totalRecord = -1;

		try {
			logger.info("**************** Start count records list in out ****************");

			if (setUpCondition(vlc, listRole)) {
				// check if request in-out is time out, update status
//				vt010000Service.autoUpdateRecordOutDateVer2();

				totalRecord = vt010011Dao.countTotalRecord(vlc);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

				reb.setData(totalRecord);

				logger.info("**************** End count records list in out - OK ****************");
			}
		} catch (Exception e) {
			logger.info("**************** End count records list in out - Fail ****************");

			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get excel file report
	 * 
	 * @param vlc
	 * @param listRole
	 * @return excel file
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public File createExcelOutputExcel(VT010011ListCondition vlc, Collection<GrantedAuthority> listRole)
			throws Exception {

		File file = null;
		List<VT010011ListInOut> listInOut = new ArrayList<>();
		String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT010011");

		try {
			if (setUpCondition(vlc, listRole)) {
				listInOut = vt010011Dao.findListInOut(vlc);
			}

			file = writeExcel(listInOut, excelFilePath, vlc);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return file;
	}

	/**
	 * get template excel file, write new file VT010011
	 * 
	 * @param listInOut
	 * @param excelFilePath
	 * @param vlc
	 * @return file excel
	 * @throws IOException
	 */
	public File writeExcel(List<VT010011ListInOut> listInOut, String excelFilePath, VT010011ListCondition vlc)
			throws IOException {

		File outFile = null;
		File file = null;

		Date date = new Date();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		String fileName = vlc.getLoginUserName() + "_" + cal.get(Calendar.YEAR) + "_" + (cal.get(Calendar.MONTH) + 1)
				+ "_" + cal.get(Calendar.DAY_OF_MONTH) + "_" + cal.get(Calendar.HOUR_OF_DAY) + "h"
				+ cal.get(Calendar.MINUTE) + "m" + cal.get(Calendar.SECOND) + "s" + cal.get(Calendar.MILLISECOND)
				+ "ms.xlsx";
		try {
			Resource resource = new ClassPathResource(excelFilePath);
			file = resource.getFile();
			String filePath = file.getAbsolutePath();
			outFile = new File(filePath.split("templateExcel")[0] + "saveExcel\\VT01\\" + fileName);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		try (FileInputStream inputStream = new FileInputStream(file)) {
			try (SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(inputStream), 100)) {
				SXSSFSheet sheet = workbook.getSheetAt(0);

				int rowCount = 2;

				// set style
				CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
				CellStyle combined1 = sheet.getWorkbook().createCellStyle();
				CellStyle combined2 = sheet.getWorkbook().createCellStyle();
				CellStyle cellStyle = sheet.getWorkbook().createCellStyle();

				cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
				cellStyle.setFillPattern(FillPatternType.NO_FILL);
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);

				combined1.cloneStyleFrom(cellStyle);
				combined1.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

				combined2.cloneStyleFrom(cellStyle);
				combined2.setDataFormat(createHelper.createDataFormat().getFormat("hh:mm"));

				// fill data
				for (VT010011ListInOut item : listInOut) {
					writeBook(item, sheet, ++rowCount, cellStyle, combined1, combined2);
				}

				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		return outFile;
	}

	/**
	 * fill data to cell
	 * 
	 * @param itemInOut
	 * @param sheet
	 * @param rowNum
	 */
	private void writeBook(VT010011ListInOut itemInOut, Sheet sheet, int rowNum, CellStyle cellStyle,
			CellStyle combined1, CellStyle combined2) {

		Row row = sheet.createRow(rowNum);

		// No.
		Cell cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellValue((double) rowNum - 2);

		// phone number of employee
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemInOut.getEmpPhone());

		// code of employee
		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemInOut.getEmpCode());

		// full name of employee
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemInOut.getEmpName());

		// email of employee
		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemInOut.getEmpEmail());

		// name of unit
		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemInOut.getDetailUnit());
		
		// get reason shoft
		cell = row.createCell(6);
		cell.setCellStyle(combined2);
		cell.setCellValue(itemInOut.getReason());
		
		// resonDetail
		cell = row.createCell(7);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemInOut.getResonDetail());
		
		// start date - only date
		cell = row.createCell(8);
		cell.setCellStyle(combined1);
		cell.setCellValue(itemInOut.getStartDate());
		
		// get destination
		cell = row.createCell(9);
		cell.setCellStyle(combined2);
		cell.setCellValue(itemInOut.getDestination());

		// start date - only time
		cell = row.createCell(10);
		cell.setCellStyle(combined2);
		cell.setCellValue(itemInOut.getStartDate());

		// end date - only time
		cell = row.createCell(11);
		cell.setCellStyle(combined2);
		cell.setCellValue(itemInOut.getEndDate());

		// start date actual
		cell = row.createCell(12);
		cell.setCellStyle(combined2);
		cell.setCellValue(itemInOut.getStartDateReal());

		// end date actual
		cell = row.createCell(13);
		cell.setCellStyle(combined2);
		cell.setCellValue(itemInOut.getEndDateReal());
		
		// get approverName
		cell = row.createCell(14);
		cell.setCellStyle(combined2);
		cell.setCellValue(itemInOut.getApproverName());
		
		// get Reson Approver
		cell = row.createCell(15);
		cell.setCellStyle(combined2);
		cell.setCellValue(itemInOut.getResonApprover());

		// get Reson Guard
		cell = row.createCell(16);
		cell.setCellStyle(combined2);
		cell.setCellValue(itemInOut.getResonGuard());

		// status
		cell = row.createCell(17);
		cell.setCellStyle(cellStyle);
		if (VT000000EntityMapStatus.STATUS_IN_OUT_MANAGEMENT.containsKey(itemInOut.getStatus())) {
			cell.setCellValue(VT000000EntityMapStatus.STATUS_IN_OUT_MANAGEMENT.get(itemInOut.getStatus()));
		} else {
			cell.setCellValue(itemInOut.getStatus());
		}
		
		cell = row.createCell(18);
	  cell.setCellStyle(cellStyle);
	  if(itemInOut.getIsLate() == 1) {
	    cell.setCellValue("Có");
	  }
	}
}
