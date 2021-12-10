package com.viettel.vtnet360.vt04.vt040010.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
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
import org.apache.poi.ss.usermodel.CellType;
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

import com.viettel.vtnet360.common.util.CalculateDate;
import com.viettel.vtnet360.issuesService.entity.IssuesService;
import com.viettel.vtnet360.issuesService.entity.IssuesServiceEntity;
import com.viettel.vtnet360.issuesService.entity.IssuesServiceSearch;
import com.viettel.vtnet360.issuesService.request.dao.IssuesServiceDAO;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityMapStatus;
import com.viettel.vtnet360.vt02.vt020006.dao.VT020006DAO;
import com.viettel.vtnet360.vt04.vt040010.dao.VT040010DAO;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010Condition;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010ReportService;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010Stationery;

/**
 * class to do business VT040010
 * 
 * @author ThangBT 17/09/2018
 *
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT040010ServiceImpl implements VT040010Service {

  private final Logger logger = Logger.getLogger(this.getClass());

  @Autowired
  private VT040010DAO vt040010dao;

  @Autowired
  Properties linkTemplateExcel;

  @Autowired
  Properties defaultContent;

  @Autowired
  IssuesServiceDAO issuesServiceDAO;
  
  @Autowired
  VT020006DAO vt020006DAO;
  /**
   * validate conditions
   * 
   * @param condition
   * @param listRole
   * @return boolean
   * @throws Exception
   */
  public boolean setUpCondition(VT040010Condition condition, Collection<GrantedAuthority> listRole) throws Exception {
    boolean isRole = false;
    try {
      if (listRole != null && !listRole.isEmpty()) {
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

        if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
            || listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))
            || listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP))
            || listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV))
            || listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))

            )
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

  @Override
  @Transactional(readOnly = true)
  public ResponseEntityBase findListReportService(VT040010Condition condition, Collection<GrantedAuthority> listRole)
      throws Exception {

    logger.info("**************** Start get list registration service ****************");

    List<VT040010ReportService> listRegistration = new ArrayList<>();
    ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, listRegistration);

    try {
      if (setUpCondition(condition, listRole)) {
        if (condition.getReceiverName() != null) {
          String patternReciver = condition.getReceiverName();
          condition.setReceiverName(patternReciver.trim());
        }

        if (condition.getServiceName() != null) {
          String patternServiceName = condition.getServiceName();
          condition.setServiceName(patternServiceName.trim());
        }

        if (condition.getPlaceName() != null) {
          String patternPlaceName = condition.getPlaceName();
          condition.setPlaceName(patternPlaceName.trim());

        }

        listRegistration = vt040010dao.findListReportService(condition);
        reb.setData(listRegistration);
        reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

        logger.info("**************** End get list registration service - OK ****************");
      }
    } catch (Exception e) {
      logger.info("**************** End get list registration service - Fail ****************");
      logger.error(e.getMessage(), e);
    }

    return reb;
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntityBase countTotalRecord(VT040010Condition condition, Collection<GrantedAuthority> listRole)
      throws Exception {
    logger.info("**************** Start count total record list registration service ****************");

    VT040010Condition totalRecord = null;
    ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, totalRecord);

    try {
      if (setUpCondition(condition, listRole)) {
        if (condition.getReceiverName() != null) {
          String patternReciver = condition.getReceiverName();
          condition.setReceiverName(patternReciver.trim());
        }

        if (condition.getServiceName() != null) {
          String patternServiceName = condition.getServiceName();
          condition.setServiceName(patternServiceName.trim());
        }
        if (condition.getPlaceName() != null) {
          String patternPlaceName = condition.getPlaceName();
          condition.setPlaceName(patternPlaceName.trim());

        }

        totalRecord = vt040010dao.countTotalRecord(condition);
        reb.setData(totalRecord);
        reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

        logger.info("**************** End count total record list registration service - OK ****************");
      }
    } catch (Exception e) {
      logger.info("**************** End count total record list registration service - Fail ****************");
      logger.error(e.getMessage(), e);
    }

    return reb;
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntityBase findListStationery(VT040010Condition conStat) throws Exception {

    logger.info("**************** Start get list stationery ****************");

    List<VT040010Stationery> listStationery = new ArrayList<>();
    ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, listStationery);

    try {
      conStat.setPlaceId("S008");
      conStat.setPlaceName("S009");
      listStationery = vt040010dao.findListStationery(conStat);
      reb.setData(listStationery);
      reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

      logger.info("**************** End get list stationery - OK ****************");
    } catch (Exception e) {
      logger.info("**************** End get list stationery - Fail ****************");
      logger.error(e.getMessage(), e);
    }

    return reb;
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntityBase countTotalRecordStationery(VT040010Condition conStat) throws Exception {

    logger.info("**************** Start count total record stationery ****************");

    int totalRecord = -1;
    ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, totalRecord);

    try {
      conStat.setPlaceId("S008");
      conStat.setPlaceName("S009");
      totalRecord = vt040010dao.countTotalRecordStationery(conStat);
      reb.setData(totalRecord);
      reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

      logger.info("**************** End count total record stationery - OK ****************");
    } catch (Exception e) {
      logger.info("**************** End count total record stationery - Fail ****************");
      logger.error(e.getMessage(), e);
    }

    return reb;
  }

  @Override
  @Transactional(readOnly = true)
  public File createExcel(IssuesServiceSearch issuesServiceSearch, Collection<GrantedAuthority> listRole) throws Exception {

    File file = null;
    List<VT040010ReportService> reportServices = new ArrayList<>();
    IssuesService issuesService = new IssuesService(); 

    String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT040010");
    try {
      if (setUpIssuesServiceSearch(issuesServiceSearch, listRole)) {
        if (issuesServiceSearch.getReceiverName() != null) {
          String patternReciver = issuesServiceSearch.getReceiverName();
          issuesServiceSearch.setReceiverName(patternReciver.trim());
        }

        if (issuesServiceSearch.getServiceName() != null) {
          String patternServiceName = issuesServiceSearch.getServiceName();
          issuesServiceSearch.setServiceName(patternServiceName.trim());
        }

        if (issuesServiceSearch.getPlaceName() != null) {
          String patternPlaceName = issuesServiceSearch.getPlaceName();
          issuesServiceSearch.setPlaceName(patternPlaceName.trim());
        }
        issuesService.setListIssuesService(issuesServiceDAO.findListIssuesService(issuesServiceSearch));
      }
      file = writeExcel(issuesService.getListIssuesService(), excelFilePath, issuesServiceSearch);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    return file;
  }
  public boolean setUpIssuesServiceSearch(IssuesServiceSearch issuesServiceSearch, Collection<GrantedAuthority> listRole) throws Exception {
    boolean isRole = false;
    try {
      if (listRole != null && !listRole.isEmpty()) {
        if (issuesServiceSearch.getStartDate() != null) {
          Date startDate = issuesServiceSearch.getStartDate();
          Calendar cal = GregorianCalendar.getInstance();
          cal.setTime(startDate);
          String newDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
              + cal.get(Calendar.DAY_OF_MONTH) + " 00:00:00";
          issuesServiceSearch.setStartDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse(newDate));
        }

        if (issuesServiceSearch.getEndDate() != null) {
          Date endDate = issuesServiceSearch.getEndDate();
          Calendar cal = GregorianCalendar.getInstance();
          cal.setTime(endDate);
          String newDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
              + cal.get(Calendar.DAY_OF_MONTH) + " 23:59:59";
          issuesServiceSearch.setEndDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse(newDate));
        }

        if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN)))
          issuesServiceSearch.setLoginRole(Constant.PMQT_ROLE_ADMIN);
        else if(listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))){
          issuesServiceSearch.setLoginRole(Constant.PMQT_ROLE_STAFF);
        }else if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV))){
          issuesServiceSearch.setLoginRole(Constant.PMQT_ROLE_STAFF_HC_DV);
          issuesServiceSearch.setUnitId(issuesServiceDAO.findUserInfoByUserName(issuesServiceSearch.getLoginUserName()).getUnitId());
        }else if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))) {
          issuesServiceSearch.setLoginRole(Constant.PMQT_ROLE_MANAGER);
          issuesServiceSearch.setUnitId(issuesServiceDAO.findUserInfoByUserName(issuesServiceSearch.getLoginUserName()).getUnitId());
        }else if(listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP))){
          issuesServiceSearch.setLoginRole(Constant.PMQT_ROLE_MANAGER_CVP);
          issuesServiceSearch.setUnitId(issuesServiceDAO.findUserInfoByUserName(issuesServiceSearch.getLoginUserName()).getUnitId());
        }else {
          issuesServiceSearch.setLoginRole(null);
        }


        isRole = true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    return isRole;
  }

  public File writeExcel(List<IssuesServiceEntity> listIssuesService, String excelFilePath,
      IssuesServiceSearch condition) throws IOException {

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
      outFile = new File(filePath.split("templateExcel")[0] + "saveExcel\\VT04\\" + fileName);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }

    try (FileInputStream inputStream = new FileInputStream(file)) {
      try (SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(inputStream), 100)) {
        SXSSFSheet sheet = workbook.getSheetAt(0);

        int rowCount = 1;
        CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();

        cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFillPattern(FillPatternType.NO_FILL);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        // style for No.
        CellStyle combinedNo = sheet.getWorkbook().createCellStyle();
        combinedNo.cloneStyleFrom(cellStyle);
        combinedNo.setAlignment(HorizontalAlignment.CENTER);

        // style for date
        CellStyle combinedDate = sheet.getWorkbook().createCellStyle();
        combinedDate.cloneStyleFrom(cellStyle);
        combinedDate.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));

        // style for note and feedback
        CellStyle combinedNote = sheet.getWorkbook().createCellStyle();
        combinedNote.cloneStyleFrom(cellStyle);
        combinedNote.setWrapText(true);

        // fill data
        for (IssuesServiceEntity item : listIssuesService) {
          writeBook(item, sheet, ++rowCount, combinedNo, cellStyle, combinedDate, combinedNote);
        }
        // set average rating
        setFooter(sheet);

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
   * @param reportServices
   * @param sheet
   * @param rowNum
   */
  private void writeBook(IssuesServiceEntity reportServices, Sheet sheet, int rowNum, CellStyle combinedNo,
      CellStyle cellStyle, CellStyle combined, CellStyle combinedNote) {
	  List<Date> offDays = vt020006DAO.findDayOff(Constant.STATUS_ACTIVE);
    Row row = sheet.createRow(rowNum);

    // No.
    Cell cell = row.createCell(0);
    cell.setCellStyle(combinedNo);
    cell.setCellValue((double) rowNum - 1);

    // issue service id
    cell = row.createCell(1);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(reportServices.getIssueServiceId());

    // service name
    cell = row.createCell(2);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(reportServices.getServiceName());

    // time plan
    cell = row.createCell(3);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(reportServices.getFullFillTime());

    // start time plan
    cell = row.createCell(4);
    cell.setCellStyle(combined);
    cell.setCellValue(reportServices.getStartDatePlan());

    // end time plan
    cell = row.createCell(5);
    cell.setCellStyle(combined);
    cell.setCellValue(reportServices.getEndDatePlan());

    // time real
    cell = row.createCell(6);
    cell.setCellStyle(cellStyle);
    
    double rangeHours = 0;
    if(reportServices.getEndDateReal() != null && reportServices.getStartDatePlan() != null) {
      long range = CalculateDate.getTotalHour(reportServices.getStartDatePlan(), reportServices.getEndDateReal(), offDays);
    		  
      rangeHours = (double) range / (double) (1000 * 60 * 60);
      DecimalFormat df = new DecimalFormat("#.##");
      rangeHours = Double.parseDouble(df.format(rangeHours));
    }
    cell.setCellValue(Math.round(rangeHours) == 0 ? "":String.valueOf(rangeHours));

/*    // start time real
    cell = row.createCell(7);
    cell.setCellStyle(combined);
    cell.setCellValue(reportServices.getStartDateReal());*/

    // end time real
    cell = row.createCell(7);
    cell.setCellStyle(combined);
    cell.setCellValue(reportServices.getEndDateReal());

    // unit name
    cell = row.createCell(8);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(reportServices.getDetailUnit());

    // name of requesting employee
    cell = row.createCell(9);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(reportServices.getEmpName());

    // name of reciver employee
    cell = row.createCell(10);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(reportServices.getReceiName());

    // phone number of requesting employee
    cell = row.createCell(11);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(reportServices.getEmpPhone());

    // status of issue service
    cell = row.createCell(12);
    cell.setCellStyle(cellStyle);
    if (VT000000EntityMapStatus.STATUS_ISSUE_SERVICE.containsKey(reportServices.getStatus())) {
      cell.setCellValue(VT000000EntityMapStatus.STATUS_ISSUE_SERVICE.get(reportServices.getStatus()));
    } else {
      cell.setCellValue(reportServices.getStatus());
    }

    // status complete
    cell = row.createCell(13);
    cell.setCellStyle(cellStyle);
    if (reportServices.getStatus() == 6 || reportServices.getStatus() == 8 || reportServices.getStatus() == 9)
    	/*if(reportServices.getEndDateReal() != null && reportServices.getStartDatePlan() != null) {
    	      long range = CalculateDate.getTotalHour(reportServices.getStartDatePlan(), reportServices.getEndDateReal(), offDays);
    	    		  
    	      rangeHours = (double) range / (double) (1000 * 60 * 60);
    	      DecimalFormat df = new DecimalFormat("#.##");
    	      rangeHours = Double.parseDouble(df.format(rangeHours));
    	    }*/
    /* cell.setCellValue(completeStatus(reportServices.getFullFillTime(), reportServices.getTimeRealBysecond(),
          reportServices.getEndDateReal(), rangeHours));*/
      cell.setCellValue(completeStatus(reportServices.getFullFillTime(), rangeHours));
    // REASON_REFUSE
    cell = row.createCell(14);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(reportServices.getReasonRefuse());

    // REASON
    cell = row.createCell(15);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(reportServices.getReason());

    // PostponeReason
    cell = row.createCell(16);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(reportServices.getPostponeReason());

    // POSTPONE_TO_TIME
    cell = row.createCell(17);
    cell.setCellStyle(combined);
    cell.setCellValue(reportServices.getPostponeToTime());

    // USER_REASON
    cell = row.createCell(18);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(reportServices.getUserReason());

    // note
    cell = row.createCell(19);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(reportServices.getNote());

    // rating
    cell = row.createCell(20);
    cell.setCellStyle(cellStyle);
    if (reportServices.getRating() != 0) {
      cell.setCellValue(reportServices.getRating());
    } else {
      cell.setCellValue("");
    }
    // feedback
    cell = row.createCell(21);
    cell.setCellStyle(combinedNote);
    cell.setCellValue(reportServices.getFeedback());
    
    // rating
    cell = row.createCell(22);
    cell.setCellStyle(cellStyle);
    if (reportServices.getRating() != 0) {
      cell.setCellValue(reportServices.getRattingToUser());
    } else {
        cell.setCellValue("");
      }
 // feedback
    cell = row.createCell(23);
    cell.setCellStyle(combinedNote);
    cell.setCellValue(reportServices.getFeedBackToUser());

    // create date
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    cell = row.createCell(24);
    cell.setCellStyle(combined);
    cell.setCellValue(dateFormat.format(reportServices.getCreateDate()));
    
        // start time real
    cell = row.createCell(25);
    cell.setCellStyle(combined);
    cell.setCellValue(reportServices.getStartDateReal());

    double realTimeTotalHours = 0;
    if(reportServices.getStatus()==1){
    	 offDays = issuesServiceDAO.findDayOff(reportServices.getStartDatePlan());
    	realTimeTotalHours=(Double)(CalculateDate.getTotalHour(reportServices.getStartDatePlan() , new Date(), offDays)*1.0/(60*60*1000));
	}else if (reportServices.getStatus()==0 || reportServices.getStatus()==2) {
		realTimeTotalHours = 0;
	}else if(reportServices.getStatus()==3){
		if(reportServices.getPostponeToTime()==null||reportServices.getTimeResume().getTime()<reportServices.getPostponeToTime().getTime()){
			offDays = issuesServiceDAO.findDayOff(reportServices.getTimeResume());
			realTimeTotalHours=(Double)(CalculateDate.getTotalHour(reportServices.getTimeResume() , new Date(), offDays)*1.0/(60*60*1000))+(Double)(reportServices.getTimeRealBysecond()*1.0/(60*60*1000));
		}else{
			offDays = issuesServiceDAO.findDayOff(reportServices.getPostponeToTime());
			realTimeTotalHours=(Double)(CalculateDate.getTotalHour(reportServices.getPostponeToTime() , new Date(), offDays)*1.0/(60*60*1000))+(Double)(reportServices.getTimeRealBysecond()*1.0/(60*60*1000));
		}
	}else{
		realTimeTotalHours=(Double)(reportServices.getTimeRealBysecond()*1.0/(60*60*1000));
	}
    DecimalFormat df = new DecimalFormat("#.##");
    realTimeTotalHours = Double.parseDouble(df.format(realTimeTotalHours));
    cell = row.createCell(26);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(realTimeTotalHours);
  }

  /**
   * convert status complete field
   * 
   * @param value1
   * @param value2
   * @param valueDate
   * @return String // value1(timePlan) value2(timeReal)
   */
  private String completeStatus(int fullFillTime, Double rangeHours) {

    String statusCompleteOnTime = defaultContent.getProperty("STATUS_COMPLETE_ON_TIME");
    String StatusCompleteOutOfDate = defaultContent.getProperty("STATUS_COMPLETE_OUT_OF_DATE");

    /*if (valueDate == null)
      return StatusCompleteOutOfDate;
    else {*/
      return ((fullFillTime ) >= rangeHours) ? statusCompleteOnTime : StatusCompleteOutOfDate;
    //}

  }

  /**
   * set up footer 'average rating'
   * 
   * @param sheet
   */
  private void setFooter(Sheet sheet) {

    // get last index of row
    int numOfRows = sheet.getLastRowNum();

    if ((numOfRows > 0) || (sheet.getPhysicalNumberOfRows() > 0)) {
      numOfRows++;

      // rating average
      CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
      Font font = sheet.getWorkbook().createFont();

      font.setBold(true);
      font.setFontHeightInPoints((short) 16);
      cellStyle.setFont(font);
      cellStyle.setAlignment(HorizontalAlignment.RIGHT);
      cellStyle.setBorderTop(BorderStyle.MEDIUM);
      cellStyle.setBorderRight(BorderStyle.MEDIUM);
      cellStyle.setBorderBottom(BorderStyle.MEDIUM);
      cellStyle.setBorderLeft(BorderStyle.MEDIUM);

      Row row = sheet.createRow(numOfRows);
      Cell cell = row.createCell(0);

      cell.setCellStyle(cellStyle);
      cell.setCellValue("Đánh giá trung bình");
      sheet.addMergedRegion(new CellRangeAddress(numOfRows, numOfRows, 0, 15));

      cell = row.createCell(16);
      cell.setCellStyle(cellStyle);
      String formula = "IFERROR(ROUND(AVERAGEIF(Q3:Q" + numOfRows + ", \">0\"), 2), \"\")";
      cell.setCellType(CellType.FORMULA);
      cell.setCellFormula(formula);
      sheet.getWorkbook().setForceFormulaRecalculation(true);

      // average rating border
      String cellAddr = "$A$" + (numOfRows + 1) + ":$P$" + (numOfRows + 1);
      setRegionBorderWith(CellRangeAddress.valueOf(cellAddr), sheet);

      cell = row.createCell(17);
      cell.setCellStyle(cellStyle);

      sheet.addMergedRegion(new CellRangeAddress(numOfRows, numOfRows, 17, 18));
      cell = row.createCell(18);
      cell.setCellStyle(cellStyle);
    }
  }

  /**
   * set border to region cell
   * 
   * @param region
   * @param sheet
   */
  private void setRegionBorderWith(CellRangeAddress region, Sheet sheet) {
    RegionUtil.setBorderBottom(BorderStyle.MEDIUM, region, sheet);
    RegionUtil.setBorderLeft(BorderStyle.MEDIUM, region, sheet);
    RegionUtil.setBorderRight(BorderStyle.MEDIUM, region, sheet);
    RegionUtil.setBorderTop(BorderStyle.MEDIUM, region, sheet);
  }
}
