package com.viettel.vtnet360.vt03.vt030013.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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
import com.viettel.vtnet360.vt03.vt030002.entity.Seat;
import com.viettel.vtnet360.vt03.vt030002.entity.Type;
import com.viettel.vtnet360.vt03.vt030013.dao.VT030013DAO;
import com.viettel.vtnet360.vt03.vt030013.entity.CarDriverManage;
import com.viettel.vtnet360.vt03.vt030013.entity.CarPlate;
import com.viettel.vtnet360.vt03.vt030013.entity.Driver;
import com.viettel.vtnet360.vt03.vt030013.entity.FreeCar;
import com.viettel.vtnet360.vt03.vt030013.entity.MegaUnit;
import com.viettel.vtnet360.vt03.vt030013.entity.RequestSearch;
import com.viettel.vtnet360.vt03.vt030013.entity.Unit;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030013ServiceImpl implements VT030013Service {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private final static String[] DRIVER_STATUS = {"Lái xe mới", "", "", "", "Đã xếp xe", "Bắt đầu đi", "Đến nơi", "Về vị trí"};
	private final static String[] CAR_STATUS = {"Xe mới", "", "", "", "Đã xếp xe", "Bắt đầu đi", "Đến nơi", "Về vị trí"};
	
	
	@Autowired
	private VT030013DAO vt030013dao;
	@Autowired
	Properties linkTemplateExcel;

	public List<Integer> listChosenUnit = new ArrayList<>();

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase getCarDriverManage(RequestSearch rqSearch) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<CarDriverManage> listCarDriveManage = null;
		List<Seat> seats = null;
		List<Type> types = null;

		try {
			if (rqSearch.getDriverStatus() == null) {
				rqSearch.setFreeTime(false);
			} else if (checkContainFreeStatus(rqSearch.getDriverStatus()) != 0) {
				rqSearch.setFreeTime(true);
			}
			listCarDriveManage = vt030013dao.getCarDriverManage(rqSearch);
			seats = vt030013dao.getCarSeat();
			types = vt030013dao.getCarType();
			for (CarDriverManage carDriverManage : listCarDriveManage) {
				for (Seat seat : seats) {
					if (seat.getSeatId().equals(carDriverManage.getSeat())) {
						carDriverManage.setSeat(seat.getSeat());
						break;
					}
				}
			}

			for (CarDriverManage carDriverManage : listCarDriveManage) {
				for (Type type : types) {
					if (type.getTypeId().equals(carDriverManage.getType())) {
						carDriverManage.setType(type.getType());
						break;
					}
				}
			}

			resp.setData(listCarDriveManage);

		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}

		return resp;
	}

	public int checkContainFreeStatus(int[] driverStatus) {
		int output = -1;
		for (int i = 0; i < driverStatus.length; i++) {
			if (driverStatus[i] == 4)
				output = 0;
		}
		return output;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase getLocationList() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Unit> listUnit = null;
		listChosenUnit = new ArrayList<>();
		Map<Integer, MegaUnit> mapData = new HashMap<Integer, MegaUnit>();
		List<MegaUnit> listTreeNode = new ArrayList<>();
		try {
			listUnit = vt030013dao.getLocationList();
			for (Unit unit : listUnit) {
				MegaUnit child = new MegaUnit();
				if (mapData.containsKey(unit.getUnitId())) {
					child = mapData.get(unit.getUnitId());
					mapData.get(unit.getUnitId()).setParentId(unit.getParentId());
					mapData.get(unit.getUnitId()).setUnitName(unit.getUnitName());
				} else {
					mapData.put(unit.getUnitId(), child);
				}
				child.setUnitId(unit.getUnitId());
				child.setUnitName(unit.getUnitName());
				child.setParentId(unit.getParentId());

				if (mapData.containsKey(unit.getParentId())) {
					mapData.get(unit.getParentId()).addChildrenItem(child);
				} else {
					MegaUnit parent = new MegaUnit();
					mapData.put(unit.getParentId(), parent);
					parent.setParentId(-2);
					parent.addChildrenItem(child);
				}
			}
			listTreeNode.add(mapData.get(234841));
			MegaUnit tempUnit = mapData.get(234841);
			listChosenUnit.add(234841);
			repeatShowUnit(tempUnit.getChildrenItems());
			resp.setData(listChosenUnit);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	// de quy
	public void repeatShowUnit(List<MegaUnit> listTreeNode) {
		for (MegaUnit megaUnit : listTreeNode) {
			listChosenUnit.add(megaUnit.getUnitId());
			repeatShowUnit(megaUnit.getChildrenItems());
		}
	}

	@Override
	public ResponseEntityBase getLicensePlate(CarPlate plate, Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<CarPlate> listPlate = null;

		try {
			if (roleList.isEmpty()) {
				return resp;
			}
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_DOIXE))) {
				plate.setRole(Constant.PMQT_ROLE_MANAGER_DOIXE);
			}
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))) {
				plate.setRole(Constant.PMQT_ROLE_MANAGER);
			}
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				plate.setRole(Constant.PMQT_ROLE_ADMIN);
			}
			listPlate = vt030013dao.getLicensePlate(plate);
			resp.setData(listPlate);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getSquadIdByQLDX(String userName) {
		return null;
	}

	@Override
	public ResponseEntityBase getDriverInSquad(Driver driver, Collection<GrantedAuthority> roleList) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.ERROR, null);
		List<Driver> data = new ArrayList<>();
		try {
			if (roleList.isEmpty()) {
				return response;
			}
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_DOIXE))) {
				driver.setRole(Constant.PMQT_ROLE_MANAGER_DOIXE);
			}
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))) {
				driver.setRole(Constant.PMQT_ROLE_MANAGER);
			}
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				driver.setRole(Constant.PMQT_ROLE_ADMIN);
			}
			data = vt030013dao.getDriverInSquad(driver);
			response = new ResponseEntityBase(Constant.SUCCESS, data);
		} catch (Exception e) {
			logger.error(e);
		}
		return response;
	}

	@Override
	public File createExcel(CarDriverManage carDriverManage, String loginUserName) throws Exception {
		File file = null;
		List<CarDriverManage> list = new ArrayList<>();

		String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT050015");
		try {
			list = vt030013dao.reportCarDriverManage(carDriverManage);
			file = writeExcel(list, excelFilePath, carDriverManage);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return file;
	}

	public File writeExcel(List<CarDriverManage> listCarDriverManage, String excelFilePath, CarDriverManage condition)
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
				for (CarDriverManage item : listCarDriverManage) {
					writeBook(item, sheet, ++rowCount, cellStyle, combined, combinedNote);

				}

				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				}
			}
		}

		return outFile;
	}

	private void writeBook(CarDriverManage carDriverManage, Sheet sheet, int rowNum, CellStyle cellStyle,
			CellStyle combined, CellStyle combinedNote) {

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
		cell.setCellValue(carDriverManage.getPlace());

		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(carDriverManage.getSquadName());

		// unit name
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(carDriverManage.getDriver());

		// user receiver
		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(carDriverManage.getPhoneNumber());

		// phone
		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(carDriverManage.getUnit());

		// totalRequest
		cell = row.createCell(6);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(DRIVER_STATUS[carDriverManage.getDriverStatus()]);

		// totalRequest
		cell = row.createCell(7);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(carDriverManage.getType());

		// totalRequest
		cell = row.createCell(8);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(carDriverManage.getSeat());

		// totalRequest
		cell = row.createCell(9);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(carDriverManage.getLicensePlate());

		// totalRequest
		cell = row.createCell(10);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(CAR_STATUS[carDriverManage.getCarStatus()]);

	}

	@Override
	public ResponseEntityBase getCarDriverManageCount(RequestSearch rqSearch) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		int i = vt030013dao.getCarDriverManageCount(rqSearch);
		resp.setData(i);
		return resp;
	}

	@Override
  @Transactional(readOnly = true)
  public ResponseEntityBase getListFreeCar(RequestSearch rqSearch) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    if(rqSearch.getPageNumber() > 0) {
      rqSearch.setPageRecord(rqSearch.getPageNumber() / rqSearch.getPageSize());
    }
    
    List<CarDriverManage> listFreeCar = null;
    try {
      FreeCar freeCar= new FreeCar();
      int total = vt030013dao.totalFreeCar(rqSearch);
      listFreeCar = vt030013dao.getListFreeCar(rqSearch);
      freeCar.setListFreeCar(listFreeCar);
      freeCar.setTotal(total);
      resp.setData(freeCar);
    } catch (Exception e) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      logger.error(e.getMessage(), e);
      throw (e);
    }

    return resp;
  }
}
