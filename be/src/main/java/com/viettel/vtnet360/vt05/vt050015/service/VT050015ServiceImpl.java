package com.viettel.vtnet360.vt05.vt050015.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections4.map.HashedMap;
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
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.stationery.service.StationeryReportService;
import com.viettel.vtnet360.stationery.service.StationeryService;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020000.dao.VT020000DAO;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Unit;
import com.viettel.vtnet360.vt05.vt050002.dao.VT050002DAO;
import com.viettel.vtnet360.vt05.vt050002.entity.Employee;
import com.viettel.vtnet360.vt05.vt050002.entity.Place;
import com.viettel.vtnet360.vt05.vt050002.entity.Receiver;
import com.viettel.vtnet360.vt05.vt050010.dao.VT050010DAO;
import com.viettel.vtnet360.vt05.vt050010.entity.VT050010DataResponse;
import com.viettel.vtnet360.vt05.vt050010.entity.VT050010InfoToFindRequestInfo;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013DataGetAll;
import com.viettel.vtnet360.vt05.vt050015.dao.VT050015DAO;
import com.viettel.vtnet360.vt05.vt050015.entity.FullfillStationery;
import com.viettel.vtnet360.vt05.vt050015.entity.ReportStationery;
import com.viettel.vtnet360.vt05.vt050015.entity.ReportStationeryAll;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT050015ServiceImpl implements VT050015Service {
	private final ArrayList<String> listStatus = new ArrayList<>();
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private VT050015DAO vt050015DAO;
	
	@Autowired
	private VT020000DAO vt020000DAO;

	@Autowired
	private VT050010DAO vt050010DAO;
	
	@Autowired
	Properties linkTemplateExcel;
	
	@Autowired
	  private VT050002DAO vt050002DAO;

	@Autowired
	private StationeryService stationeryService;

	public void getListStatus() {
		listStatus.add("Chờ LĐ phê duyệt");
		listStatus.add("Lãnh đạo phê duyệt");
		listStatus.add("Lãnh đạo từ chối");
		listStatus.add("VP TCT tiếp nhận");
		listStatus.add("VP TCT hoãn thực hiện");
		listStatus.add("VP TCT Không thực hiện được");
		listStatus.add("VP TCT cấp phát cho HCĐV");
		listStatus.add("HCĐV xác nhận VPP");
		listStatus.add("Từ chối xác nhận");
	}

	@Override
	public ResponseEntityBase getEmployee(Employee employee) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Employee> listEmployee = null;
		List<String> listRole = new ArrayList<>();

		listRole.add(Constant.PMQT_ROLE_STAFF_HCVP_VPP);
		employee.setUnitId(Constant.PMQT_UNIT_STAFF_HCVP_TCT);
		try {
			listEmployee = vt050015DAO.getEmployee(employee);
			resp.setData(listEmployee);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getReportStationery() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<ReportStationery> listReportStationery = null;
		try {
			listReportStationery = vt050015DAO.getReportStationery();
			resp.setData(listReportStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getFullfillStationery(ReportStationery rpStationery) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<FullfillStationery> listFullfillStationery = null;
		try {
			listFullfillStationery = vt050015DAO.getFullfillStationery(rpStationery);
			resp.setData(listFullfillStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getLastUser(String userName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		String out = "";
		try {
			out = vt050015DAO.getLastUser(userName);

			resp.setData(out);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchReportStationery(ReportStationery rpStationery,Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<ReportStationery> listReportStationery = null;
		try {
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))
			          && !roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				rpStationery.setQL(false);
				rpStationery.setVPTCT(true);
				rpStationery.setHCDV(false);
				int checkConfigInStaff = vt050010DAO.checkVptctInStaffConfig(rpStationery.getUserNameVPTCT());
				 if (checkConfigInStaff == Constant.NONE_EXIST_RECORD) {
			          resp.setStatus(Constant.ERROR_VPTCT_NO_CONFIG_IN_STATIONERY_STAFF);
			          return resp;
			        }
				 listReportStationery = vt050015DAO.searchReportStationery(rpStationery);
				 resp.setData(listReportStationery);
				 		
			} else if(roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV)) 
          && !roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))){
				rpStationery.setQL(false);
				rpStationery.setHCDV(true);
				rpStationery.setVPTCT(false);
				listReportStationery = vt050015DAO.searchReportStationery(rpStationery);
				resp.setData(listReportStationery);
				
			}
			else if(roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN)) || roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER)) ){
				rpStationery.setHCDV(false);
				rpStationery.setQL(false);
				rpStationery.setVPTCT(false);
				rpStationery.setAdmin(true);
			listReportStationery = vt050015DAO.searchReportStationery(rpStationery);
			resp.setData(listReportStationery);
			}
			
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public File createExcel(ReportStationery rpStationery, String username,Collection<GrantedAuthority> roleList) throws Exception {
		File file = null;
		List<ReportStationeryAll> reportStationery = new ArrayList<>();

		String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT050015");
		try {
		  if(roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN)) || roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER)) ){
        rpStationery.setHCDV(false);
        rpStationery.setVPTCT(false);
        rpStationery.setAdmin(true);
        reportStationery = vt050015DAO.searchReportStationeryAll(rpStationery);
        file = writeExcel(reportStationery, excelFilePath, rpStationery);
      }
		  else if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))
			          && !roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				rpStationery.setVPTCT(true);
				rpStationery.setHCDV(false);
				 reportStationery = vt050015DAO.searchReportStationeryAll(rpStationery);
					file = writeExcel(reportStationery, excelFilePath, rpStationery);
			} 
		  else if(roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV))){
				rpStationery.setHCDV(true);
				rpStationery.setVPTCT(false);
				reportStationery = vt050015DAO.searchReportStationeryAll(rpStationery);
				file = writeExcel(reportStationery, excelFilePath, rpStationery);
			} 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return file;
	}

	public File writeExcel(List<ReportStationeryAll> reportStationery, String excelFilePath, ReportStationery condition)
			throws IOException {

		File outFile = null;
		File file = null;

		Date date = new Date();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		String fileName = condition.getUserName() + "_" + cal.get(Calendar.YEAR) + "_" + (cal.get(Calendar.MONTH) + 1)
				+ "_" + cal.get(Calendar.DAY_OF_MONTH) + "_" + cal.get(Calendar.HOUR_OF_DAY) + "h"
				+ cal.get(Calendar.MINUTE) + "m" + cal.get(Calendar.SECOND) + "s" + cal.get(Calendar.MILLISECOND)
				+ "ms.xlsx";
		try {
			Resource resource = new ClassPathResource(excelFilePath);
			file = resource.getFile();
			String filePath = file.getAbsolutePath();
			outFile = new File(filePath.split("templateExcel")[0] + "saveExcel\\VT05\\" + fileName);
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
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

				// style for date
				CellStyle combined = sheet.getWorkbook().createCellStyle();
				combined.cloneStyleFrom(cellStyle);
				combined.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));

				// style for note and feedback
				CellStyle combinedNote = sheet.getWorkbook().createCellStyle();
				combinedNote.cloneStyleFrom(cellStyle);
				combinedNote.setWrapText(true);

				// fill data
				for (ReportStationeryAll item : reportStationery) {
					writeBook(item, sheet, ++rowCount, cellStyle, combined, combinedNote);

				}

				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				}
			}
		}

		return outFile;
	}

	private void writeBook(ReportStationeryAll reportStationery, Sheet sheet, int rowNum, CellStyle cellStyle,
			CellStyle combined, CellStyle combinedNote) {
		getListStatus();
		Row row = sheet.createRow(rowNum);

		// No.
		Cell cell = row.createCell(0);
		CellStyle cellStyle2 = sheet.getWorkbook().createCellStyle();
		cellStyle2.cloneStyleFrom(cellStyle);
		cellStyle2.setAlignment(HorizontalAlignment.CENTER);
		cell.setCellStyle(cellStyle2);
		cell.setCellValue((double) rowNum - 1);

		// issue id
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getRpStationeryId());

		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getPlaceName());

		// unit name
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getThreeLevelUnit());

		// user receiver
		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getHcvpFullName());

		// phone
		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getEmployeePhone());

		// totalRequest
		cell = row.createCell(6);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getTotalRequest());

		cell = row.createCell(7); 
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getTotalFulFill());
		
		// status
		cell = row.createCell(8);
		cell.setCellStyle(cellStyle);
		String status = listStatus.get(reportStationery.getStatus());
		cell.setCellValue(status);

		// note
		
		cell = row.createCell(9);
		CellStyle cellStyle3 = sheet.getWorkbook().createCellStyle();
		cellStyle3.cloneStyleFrom(cellStyle);
		cellStyle3.setWrapText(true);
		cell.setCellStyle(cellStyle3);
		cell.setCellValue(reportStationery.getMessage());
		
		
		
		// note
				cell = row.createCell(10);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(reportStationery.getStationeryName());
				
				cell = row.createCell(11);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(reportStationery.getCalculationUnit());

				cell = row.createCell(12);
				cell.setCellStyle(cellStyle);
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String date = df.format(reportStationery.getUpdateDate());
				cell.setCellValue(date);
				
				cell = row.createCell(13);	
				cell.setCellStyle(cellStyle);
				cell.setCellValue(reportStationery.getAcceptFullName());
				

				cell = row.createCell(14);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(reportStationery.getNote());
				
				cell = row.createCell(15);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(reportStationery.getRatingToVptct());
				
				cell = row.createCell(16);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(reportStationery.getRatingToUser());
				
	}

	private void setFooter(Sheet sheet) {

		// get last index of row
		int numOfRows = sheet.getLastRowNum();

		if ((numOfRows > 0) || (sheet.getPhysicalNumberOfRows() > 0)) {
			numOfRows++;

			// rating average
			CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
			Font font = sheet.getWorkbook().createFont();

			font.setBold(true);
			font.setFontHeightInPoints((short) 13);
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
			sheet.addMergedRegion(new CellRangeAddress(numOfRows, numOfRows, 0, 6));

			cell = row.createCell(7);
			cell.setCellStyle(cellStyle);
			String formula = "IFERROR(ROUND(AVERAGEIF(H3:H" + numOfRows + ", \">0\"), 2), \"\")";
			cell.setCellType(CellType.FORMULA);
			cell.setCellFormula(formula);
			sheet.getWorkbook().setForceFormulaRecalculation(true);

			// average rating border
			String cellAddr = "$A$" + (numOfRows + 1) + ":$H$" + (numOfRows + 1);
			setRegionBorderWith(CellRangeAddress.valueOf(cellAddr), sheet);

			cell = row.createCell(8);
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

	@Override
	public ResponseEntityBase getLoginInfo(String userName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		Receiver receiver = null;
		Map<String, Object> output = new HashedMap<>();

		try {
			receiver = vt050015DAO.getLoginInfo(userName);
			if (receiver != null) {
				List<VT020000Unit> unit = new ArrayList<>();
				if (receiver.getUnitId() != 0) {
				  ObjectMapper mapper = new ObjectMapper();
				  System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(receiver));
				  VT020000Unit units = vt020000DAO.findUnitInfoByUnitID(String.valueOf(receiver.getUnitId()));
				  
				  System.out.println("UNIT " + units.getUnitId());
					unit.add(vt020000DAO.findUnitInfoByUnitID(String.valueOf(receiver.getUnitId())));
				} else {
					unit = vt020000DAO.getLocationListByID("");
				}
				output.put("receiver", receiver);
				output.put("unit", unit);
			}
			resp.setData(output);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase countReportStationery(ReportStationery rpStationery,Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))
			          && !roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				rpStationery.setVPTCT(true);
				rpStationery.setHCDV(false);
				rpStationery.setAdmin(false);
//				int checkConfigInStaff = vt050010DAO.checkVptctInStaffConfig(rpStationery.getUserName());
//				 if (checkConfigInStaff == Constant.NONE_EXIST_RECORD) {
//			          resp.setStatus(Constant.ERROR_VPTCT_NO_CONFIG_IN_STATIONERY_STAFF);
//			          return resp;
//			        }
				 int count = vt050015DAO.countReportStationery(rpStationery);
				 resp.setData(count);
			
			} else if(roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV)) 
          && !roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))){
				rpStationery.setHCDV(true);
				rpStationery.setVPTCT(false);
				rpStationery.setAdmin(false);
				int count = vt050015DAO.countReportStationery(rpStationery);
				resp.setData(count);
			}
			else if(roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))|| roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))){
				rpStationery.setHCDV(false);
				rpStationery.setVPTCT(false);
				rpStationery.setAdmin(true);
				int count = vt050015DAO.countReportStationery(rpStationery);
				resp.setData(count);
			}
			
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	
	@Override
	public ResponseEntityBase getHcdv(Employee employee) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Employee> listEmployee = null;
		List<String> listRole = new ArrayList<>();

		listRole.add(Constant.PMQT_ROLE_STAFF_HCVP_VPP);
		employee.setUnitId(Constant.PMQT_UNIT_STAFF_HCVP_TCT);
		try {
			listEmployee = vt050015DAO.getHcdv(employee);
			resp.setData(listEmployee);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getEmployeeTCT(Employee employee) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Employee> listEmployee = null;
		List<String> listRole = new ArrayList<>();

		listRole.add(Constant.PMQT_ROLE_STAFF_HCVP_VPP);
		employee.setUnitId(Constant.PMQT_UNIT_STAFF_HCVP_TCT);
		try {
			listEmployee = vt050015DAO.getEmployeeTCT(employee);
			resp.setData(listEmployee);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
}
