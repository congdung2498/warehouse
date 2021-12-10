package com.viettel.vtnet360.vt03.vt030014.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
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
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
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

import com.viettel.vtnet360.driving.request.dao.BookingDAO;
import com.viettel.vtnet360.driving.request.dao.DrivingDAO;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityMapStatus;
import com.viettel.vtnet360.vt03.vt030014.dao.VT030014DAO;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListEmployee;

/**
 * class to do business VT030014
 * 
 * @author ThangBT 07/09/2018
 *
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030014ServiceImpl implements VT030014Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT030014DAO vt030014dao;
	
	@Autowired
	private DrivingDAO   drivingDao;
	
	@Autowired
  private BookingDAO bookingDAO;

	@Autowired
	Properties linkTemplateExcel;

	/**
	 * get list booker by condition
	 * 
	 * @param empInfo
	 * @return List<VT030014ListEmployee>
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListEmployee(VT030014ListEmployee empInfo) throws Exception {

		logger.info("**************** Start get list employees ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		List<VT030014ListEmployee> employees = new ArrayList<>();

		try {
			employees = vt030014dao.findListEmployee(empInfo);
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
	 * get list of employee's phone number
	 * 
	 * @param empInfo
	 * @return List<VT030014ListEmployee>
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListEmployeePhone(VT030014ListEmployee empInfo) throws Exception {

		logger.info("**************** Start get list employee's phone number ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		List<VT030014ListEmployee> empPhone = new ArrayList<>();

		try {
			empPhone = vt030014dao.findListEmployeePhone(empInfo);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			reb.setData(empPhone);

			logger.info("**************** End get list employee's phone number - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list employee's phone number - Fail ****************");

			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get list of driver
	 * 
	 * @param driverInfo
	 * @return List<VT030014ListDriver>
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListDriver(VT030014ListEmployee driverInfo) throws Exception {

		logger.info("**************** Start get list driver ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		List<VT030014ListEmployee> drivers = new ArrayList<>();

		try {
			drivers = vt030014dao.findListDriver(driverInfo);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			reb.setData(drivers);

			logger.info("**************** End get list driver - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list driver - Fail ****************");

			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * validation conditions
	 * 
	 * @param condition
	 * @param listRole
	 * @return boolean
	 * @throws Exception
	 */
	public boolean setUpCondition(VT030014ListCondition condition, Collection<GrantedAuthority> listRole)
			throws Exception {

		boolean isRole = false;
		try {
			if (listRole != null && !listRole.isEmpty()) {
				if (condition.getStartDate() != null) {
					Date dateStart = condition.getStartDate();
					Calendar cal = GregorianCalendar.getInstance();
					cal.setTime(dateStart);
					String newDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DAY_OF_MONTH) + " 00:00:00";
					condition.setStartDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse(newDate));
				}

				if (condition.getEndDate() != null) {
					Date dateEnd = condition.getEndDate();
					Calendar cal = GregorianCalendar.getInstance();
					cal.setTime(dateEnd);
					String newDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DAY_OF_MONTH) + " 23:59:59";
					condition.setEndDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse(newDate));
				}

				if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
						|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))
						|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP))
						|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV))
						|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_DOIXE)))
					condition.setLoginRole(null);
				else
					condition.setLoginRole(Constant.PMQT_ROLE_EMPLOYYEE);

				isRole = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return isRole;
	}

	
	
	
	/**
	 * get list of trips
	 * 
	 * @param condition
	 * @param listRole
	 * @return List<VT030014ListTrip>
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListTrip(VT030014ListCondition condition, Collection<GrantedAuthority> listRole)
			throws Exception {

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		List<VT030014ListCondition> trips = new ArrayList<>();

		try {
			logger.info("**************** Start get list car booking ****************");

			if (setUpCondition(condition, listRole)) {
				trips = vt030014dao.findListTrip(condition);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				reb.setData(trips);

				logger.info("**************** End get list car booking - OK ****************");
			}
		} catch (Exception e) {
			logger.info("**************** End get list car booking - Fail ****************");

			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * count total records VT030014
	 * 
	 * @param condition
	 * @param listRole
	 * @return number of records
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase countTotalRecord(VT030014ListCondition condition, Collection<GrantedAuthority> listRole)
			throws Exception {

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		
		VT030014ListCondition cons = null;
		
		if (!listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
        && !listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))
        && !listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP))
        && listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_DOIXE))) {
      int countSquadByUsername = bookingDAO.countSquadByUsername(condition.getLoginUserName());
      if(countSquadByUsername == 0) {
        cons = new VT030014ListCondition();
        cons.setNumOfRows(0);
        cons.setAverageRating("0");
        reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        reb.setData(cons);
        return reb;
      }
    }

		try {
			logger.info("**************** Start count records list car booking ****************");

			if (setUpCondition(condition, listRole)) {
			  if (!listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
            && (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))
            || listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP)))) {
          condition.setLoginRole(Constant.PMQT_ROLE_MANAGER);
        }
				cons = vt030014dao.countTotalRecord(condition);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				reb.setData(cons);

				logger.info("**************** End count records list car booking - OK ****************");
			}
		} catch (Exception e) {
			logger.info("**************** End count records list car booking - Fail ****************");

			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get report excel file
	 * 
	 * @param condition-
	 * @param listRole
	 * @return excel file
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public File createExcelOutputExcel(VT030014ListCondition condition, Collection<GrantedAuthority> listRole)
			throws Exception {

		File file = null;
		List<VT030014ListCondition> listTrip = new ArrayList<>();
		String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT030014");
		try {
			if (setUpCondition(condition, listRole)) {
			  if (!listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
            && (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))
            || listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP)))) {
          condition.setLoginRole(Constant.PMQT_ROLE_MANAGER);
        }
				listTrip = drivingDao.findAllListTrip(condition);
			}

			file = writeExcel(listTrip, excelFilePath, condition);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return file;
	}

	/**
	 * get template excel file, write new file VT030014
	 * 
	 * @param listTrip
	 * @param excelFilePath
	 * @param condition
	 * @return file excel
	 * @throws IOException
	 */
	public File writeExcel(List<VT030014ListCondition> listTrip, String excelFilePath, VT030014ListCondition condition)
			throws IOException {

		File outFile = null;
		File file = null;

		Date date = new Date();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		String fileName = condition.getLoginUserName() + "_" + cal.get(Calendar.YEAR) + "_"
				+ (cal.get(Calendar.MONTH) + 1) + "_" + cal.get(Calendar.DAY_OF_MONTH) + "_"
				+ cal.get(Calendar.HOUR_OF_DAY) + "h" + cal.get(Calendar.MINUTE) + "m" + cal.get(Calendar.SECOND) + "s"
				+ cal.get(Calendar.MILLISECOND) + "ms.xlsx";
		try {
			Resource resource = new ClassPathResource(excelFilePath);
			file = resource.getFile();
			String filePath = file.getAbsolutePath();
			outFile = new File(filePath.split("templateExcel")[0] + "saveExcel\\VT03\\" + fileName);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		try (FileInputStream inputStream = new FileInputStream(file)) {
			try (SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(inputStream), 100)) {
				SXSSFSheet sheet = workbook.getSheetAt(0);

				// number header of row in excel template
				int rowCount = 2;

				CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
				CellStyle combined = sheet.getWorkbook().createCellStyle();
				CellStyle cellStyle = sheet.getWorkbook().createCellStyle();

				cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
				cellStyle.setFillPattern(FillPatternType.NO_FILL);
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);

				combined.cloneStyleFrom(cellStyle);
				combined.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));

				// fill data
				int rowNumber = 1;
				int countUserRating = 0, countDriverRating = 0;
				int userRatingTotal = 0, driverRatingTotal = 0;
				for (VT030014ListCondition item : listTrip) {
				  if(item.getRating() != 0) {
				    countUserRating++;
				    userRatingTotal = userRatingTotal + item.getRating();
				  }
				  if (item.getDriverRating() != null && !item.getDriverRating().equals("0")) {
				    countDriverRating++;
				    driverRatingTotal = driverRatingTotal + Integer.parseInt(item.getDriverRating());
          }
					writeBook(item, sheet, ++rowCount, cellStyle, combined, rowNumber++);
				}

				// set average rating
				
	      DecimalFormat df = new DecimalFormat("#.##");
				double userAvg = 0, driverAvg = 0;
				
				if(countUserRating > 0) {
				  userAvg = (double) userRatingTotal / (double) countUserRating;
				  userAvg = Double.parseDouble(df.format(userAvg));
				}
				if(countDriverRating > 0) {
				  driverAvg = (double) driverRatingTotal / (double) countDriverRating;
				  driverAvg = Double.parseDouble(df.format(driverAvg));
				}
				
				setFooter(sheet, userAvg, driverAvg);

				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				}
			}
		}

		return outFile;
	}

	/**
	 * fill data to cell
	 * 
	 * @param listTrip
	 * @param sheet
	 * @param rowNum
	 */
	private void writeBook(VT030014ListCondition itemTrip, Sheet sheet, int rowNum, CellStyle cellStyle,
			CellStyle combined, int rowNumber) {
		Row row = sheet.createRow(rowNum);

		// No.
		Cell cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(rowNumber);
		

		// unit name
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemTrip.getDetailUnit());

		// booker name
		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemTrip.getEmpName());

		// phone number of booker
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemTrip.getEmpPhone());

		// team car name
		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemTrip.getSquadName());

		// driver name
		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemTrip.getDriverName());

		// car type
		cell = row.createCell(6);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemTrip.getCarType());
		
		//TODO : add new column: realCarType
		// realCarType
    cell = row.createCell(7);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(itemTrip.getRealyCarType());

		// car license plate
		cell = row.createCell(8);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemTrip.getLicensePlate());

		// time start going
		cell = row.createCell(9);
		cell.setCellStyle(combined);
		cell.setCellValue(itemTrip.getDateStart());

		// time end going
		cell = row.createCell(10);
		cell.setCellStyle(combined);
		cell.setCellValue(itemTrip.getDateEnd());
		
		// time readyStart
		cell = row.createCell(11);
		cell.setCellStyle(combined);
		cell.setCellValue(itemTrip.getTimeReadyStart());
		
		// time readyEnd
		cell = row.createCell(12);
		cell.setCellStyle(combined);
		cell.setCellValue(itemTrip.getTimeReadyEnd());
		
		// time actually booked
		cell = row.createCell(13);
    cell.setCellStyle(combined);
    cell.setCellValue(itemTrip.getBookedActualyTime());
    
		// number passenger
		cell = row.createCell(14);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemTrip.getNumOfPassenger());

		// status trip
		cell = row.createCell(15);
		cell.setCellStyle(cellStyle);
		if (VT000000EntityMapStatus.STATUS_CAR_BOOKING.containsKey(itemTrip.getStatusTrips())) {
			cell.setCellValue(VT000000EntityMapStatus.STATUS_CAR_BOOKING.get(itemTrip.getStatusTrips()));
		} else {
			cell.setCellValue(itemTrip.getStatusTrips());
		}

		// user rating
		cell = row.createCell(16);
		cell.setCellStyle(cellStyle);
		if (itemTrip.getRating() != 0) {
			cell.setCellValue(itemTrip.getRating());
		} else {
			cell.setCellValue("");
		}

		// user feedback
		cell = row.createCell(17);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemTrip.getFeedback());
		
		// driver rating
    cell = row.createCell(18);
    cell.setCellStyle(cellStyle);
    if (itemTrip.getDriverRating() == null || itemTrip.getDriverRating().equals("0")) {
      cell.setCellValue("");
    } else {
      cell.setCellValue(itemTrip.getDriverRating());
    }

    // driver feedback
    cell = row.createCell(19);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(itemTrip.getDriverFeedback());

		// reasonrefuse
		cell = row.createCell(20);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemTrip.getReasonAssigner());

		// getReasonAssigner
		cell = row.createCell(21);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemTrip.getReasonRefuse());

	}

	/**
	 * set up footer 'average rating'
	 * 
	 * @param sheet
	 */
	private void setFooter(Sheet sheet, double userAvg, double driverAvg) {

		// get last index of row
		int numOfRows = sheet.getLastRowNum() + 1;

		// set text and style rating average
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();

		font.setBold(true);
		font.setFontHeightInPoints((short) 20);

		cellStyle.setFont(font);
		cellStyle.setAlignment(HorizontalAlignment.RIGHT);
		cellStyle.setBorderTop(BorderStyle.MEDIUM);
		cellStyle.setBorderRight(BorderStyle.MEDIUM);
		cellStyle.setBorderBottom(BorderStyle.MEDIUM);
		cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		if(numOfRows == 1) {
			numOfRows = 5;
		}
		Row row = sheet.createRow(numOfRows);

		Cell cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("Đánh giá trung bình");
		sheet.addMergedRegion(new CellRangeAddress(numOfRows, numOfRows, 0, 15));

		// 'average rating' cell
		cell = row.createCell(16);
		cell.setCellValue(userAvg);
		cell.setCellStyle(cellStyle);
		
		// 'average rating' border
		String cellAddr = "$A$" + (numOfRows + 1) + ":$L$" + (numOfRows + 1);
		setRegionBorder(CellRangeAddress.valueOf(cellAddr), sheet);

		cell = row.createCell(17);
		cell.setCellStyle(cellStyle);
		sheet.getWorkbook().setForceFormulaRecalculation(true);
		
		String cellAddr15 = "$A$" + (numOfRows + 1) + ":$L$" + (numOfRows + 1);
		setRegionBorderType(CellRangeAddress.valueOf(cellAddr15), sheet);
		
		cell = row.createCell(18);
    cell.setCellValue(driverAvg);
    cell.setCellStyle(cellStyle);
		sheet.getWorkbook().setForceFormulaRecalculation(true);
		
		String cellAddr16 = "$A$" + (numOfRows + 1) + ":$L$" + (numOfRows + 1);
		setRegionBorderType(CellRangeAddress.valueOf(cellAddr16), sheet);
		
		
		cell = row.createCell(19);
		cell.setCellStyle(cellStyle);
		sheet.getWorkbook().setForceFormulaRecalculation(true);
		
		String cellAddr17 = "$A$" + (numOfRows + 1) + ":$L$" + (numOfRows + 1);
		setRegionBorderType(CellRangeAddress.valueOf(cellAddr17), sheet);
		
		cell = row.createCell(20);
		cell.setCellStyle(cellStyle);
		sheet.getWorkbook().setForceFormulaRecalculation(true);
		
		String cellAddr18 = "$A$" + (numOfRows + 1) + ":$L$" + (numOfRows + 1);
		setRegionBorderType(CellRangeAddress.valueOf(cellAddr18), sheet);
		
		cell = row.createCell(21);
		cell.setCellStyle(cellStyle);
		sheet.getWorkbook().setForceFormulaRecalculation(true);
		
		String cellAddr19 = "$A$" + (numOfRows + 1) + ":$L$" + (numOfRows + 1);
		setRegionBorderType(CellRangeAddress.valueOf(cellAddr19), sheet);
	}

	/**
	 * set border to region cell
	 * 
	 * @param region
	 * @param sheet
	 */
	private void setRegionBorder(CellRangeAddress region, Sheet sheet) {
		RegionUtil.setBorderBottom(BorderStyle.MEDIUM, region, sheet);
		RegionUtil.setBorderLeft(BorderStyle.MEDIUM, region, sheet);
		RegionUtil.setBorderRight(BorderStyle.MEDIUM, region, sheet);
		RegionUtil.setBorderTop(BorderStyle.MEDIUM, region, sheet);
	}
	private void setRegionBorderType(CellRangeAddress region, Sheet sheet) {
		RegionUtil.setBorderBottom(BorderStyle.MEDIUM, region, sheet);
		RegionUtil.setBorderTop(BorderStyle.MEDIUM, region, sheet);
	}
}
