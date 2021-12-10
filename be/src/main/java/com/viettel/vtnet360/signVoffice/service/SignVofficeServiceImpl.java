package com.viettel.vtnet360.signVoffice.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetVisibility;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.common.convertPdf.AddComment;
import com.viettel.vtnet360.common.convertPdf.ConvertExcelToPdf;
import com.viettel.vtnet360.signVoffice.entity.SignVoffice;
import com.viettel.vtnet360.signVoffice.entity.SignVofficeEntity;
import com.viettel.vtnet360.signVoffice.entity.SignVofficeSearch;
import com.viettel.vtnet360.signVoffice.request.dao.SignVofficeDao;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040010.dao.VT040010DAO;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010Condition;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010IssueService;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010Stationery;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010StationeryExcel;

@Service
public class SignVofficeServiceImpl implements SignVofficeService{
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SignVofficeDao signVofficeDao;
	
	@Autowired
	private VT040010DAO vt040010dao;
	
	@Autowired
	Properties linkTemplateExcel;
	
	@Autowired
	Properties messages;
	
	
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListSignVoffice(SignVofficeSearch signVofficeSearch,Collection<GrantedAuthority> listRole,String userName) throws Exception{
		logger.info("**************** Start get list  SignVoffice ****************");

		SignVoffice signVoffice = new SignVoffice();
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, signVoffice );
		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))||listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP))
					||listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))){
				
			} else if(listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV))||listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))){
				signVofficeSearch.setIsManager(1);
				signVofficeSearch.setUnitId(signVofficeDao.findUnitByUserName(userName));
			}
			int totalSiginVoffice= signVofficeDao.totalSignVoffice(signVofficeSearch);
			List<SignVofficeEntity> listSignVoffice = signVofficeDao.findListSignVoffice(signVofficeSearch);
			
			for(SignVofficeEntity entity : listSignVoffice) {
			  if(entity.getType() == 2) {
			    List<SignVofficeEntity> services = signVofficeDao.findIssueServiceBySignVofficeId(entity.getSignVofficeId());
	        entity.setDetailPdfs(services);
			  } else if(entity.getType() == 1) {
			    List<SignVofficeEntity> services = new ArrayList<>();
			    SignVofficeEntity detail = new SignVofficeEntity();
			    detail.setType(2);
			    detail.setSignVofficeId(entity.getSignVofficeId());
			    detail.setContent(entity.getContent() + ".xlsx");
			    services.add(detail);
          entity.setDetailPdfs(services);
			  }
			}
			
			signVoffice.setListSignVoffice(listSignVoffice);
			signVoffice.setTotalSiginVoffice(totalSiginVoffice);
			reb.setData(signVoffice);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list registration SignVoffice - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list registration SignVoffice - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	@Transactional
	public ResponseEntityBase insertSignVoffice(SignVofficeEntity signVofficeEntity,Principal principal) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		signVofficeEntity.setRequestUserName(userName);
		try {
			
			signVofficeDao.insertSignVoffice(signVofficeEntity);
			if(signVofficeEntity.getSignVofficeId()>0) {
				int updateIssuedServiceReport = signVofficeDao.updateIssuedServiceReport(signVofficeEntity);
				if(updateIssuedServiceReport>0) {
					reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				}
			}
			
			logger.info("**************** End get list registration SignVoffice - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list registration SignVoffice - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}
	
	@Override
	public ResponseEntityBase exportStationeryBySyntheticSignVoffice(VT040010Condition condition, Principal principal) throws Exception {
		logger.info("**************** Start get list  SignVoffice ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null );
		String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_EXPORT_REPORT_SYNTHETIC");
		
		try {
			setUpCondition(condition,principal);
			List<VT040010StationeryExcel> listStationeryExcel = signVofficeDao.findIssueServiceByDetailSignVoffice(condition);
			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			Map<String, Integer> rangeStationery = new HashMap<>();
			createSummarySheet(listStationeryExcel, rangeStationery,excelFilePath , condition);
			System.out.println(ConvertExcelToPdf.convertToPDF(new File(convertUrl()+"/documentVO/"+condition.getSyntheticSignVofficeId().toString()+".xlsx").getParent(), convertUrl()+"/documentVO/"+condition.getSyntheticSignVofficeId()+".xlsx"));
			String in = new String(convertUrl()+"/documentVO/"+condition.getSyntheticSignVofficeId().toString()+".pdf");
			String out = new String(convertUrl()+"/documentVO/"+condition.getSyntheticSignVofficeId().toString()+".out.pdf");
			List<String> patterns = new ArrayList<>();
			patterns.add("kysvc1");
			
			AddComment.addCommentSignature(in, out, patterns,(String) oauth.getPrincipal(),linkTemplateExcel.getProperty("PDF_FILE_TEMPLATE_VOFFICE"));
			reb.setData(listStationeryExcel);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			logger.info("**************** End get list registration SignVoffice - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list registration SignVoffice - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}
	
	public void createSummarySheet(List<VT040010StationeryExcel> stationeryExcel, Map<String, Integer> rangeStationery,
			String excelFilePath, VT040010Condition condition) throws IOException{
		// list stationery name for each unit id
		
		File outFile = null;
		File file = null;

		Date date = new Date();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		try {
			Resource resource = new ClassPathResource(excelFilePath);
			file = resource.getFile();
			outFile = new File(convertUrl()+"/documentVO/"+condition.getSyntheticSignVofficeId()+".xlsx");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		try (FileInputStream inputStream = new FileInputStream(file)) {
			try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
				Sheet sheet = workbook.getSheetAt(0);
			
		Map<String, Map<String, VT040010Stationery>> mapUnit = new HashMap<>();
		// list name of unit
		Map<String, String> mapUnitName = new HashMap<>();

		for (VT040010StationeryExcel item : stationeryExcel) {
			// collect stationery name by unit id
			if (mapUnit.containsKey(item.getUnitId())) {
				Map<String, VT040010Stationery> mapStationeryNames = mapUnit.get(item.getUnitId());

				if (!mapStationeryNames.containsKey(item.getStationeryId())) {
					VT040010Stationery sta = new VT040010Stationery(item.getStationeryName(), null,
							item.getStationeryQuan(), item.getStationeryUnitCal());
					mapStationeryNames.put(item.getStationeryId(), sta);
				}else{
					VT040010Stationery sta = mapStationeryNames.get(item.getStationeryId());
					sta.setStationeryQuan(sta.getStationeryQuan()+item.getStationeryQuan());
				}

				mapUnit.put(item.getUnitId(), mapStationeryNames);
			} else {
				Map<String, VT040010Stationery> mapStationeryNames = new HashMap<>();
				VT040010Stationery sta = new VT040010Stationery(item.getStationeryName(), null,
						item.getStationeryQuan(), item.getStationeryUnitCal());
				mapStationeryNames.put(item.getStationeryId(), sta);
				mapUnit.put(item.getUnitId(), mapStationeryNames);

				// put unit name to mapUnitName
				mapUnitName.put(item.getUnitId(), item.getDetailUnit());
			}
		}
		CellStyle cellStyle = workbook.createCellStyle();
		CellStyle cellCvp = sheet.getWorkbook().createCellStyle();
		CellStyle cellSign = sheet.getWorkbook().createCellStyle();
		Font font1 = workbook.createFont();

		cellStyle.setFont(font1);
		cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		cellStyle.setFillPattern(FillPatternType.NO_FILL);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);

		Font fontSign = workbook.createFont();
		fontSign.setFontName("Arial");
		fontSign.setFontHeightInPoints((short)16);
		fontSign.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		cellSign.setAlignment(HorizontalAlignment.CENTER);
		cellSign.setFont(fontSign);
		
		Font fontCvp = workbook.createFont();
		fontCvp.setFontHeightInPoints((short)14);
		fontCvp.setBold(true);
		cellCvp.setFont(fontCvp);
		cellCvp.setAlignment(HorizontalAlignment.CENTER);
		
		int rowCount = 3;
		int numericalOrderUnit = 1;
		int numbericalOrderStationery = 1;
		Map<String, VT040010Stationery> mapStationeryName = null;
		Row row = null;
		String formula = "";

		// style for each unit name row
		CellStyle combinedUnit = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();

		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		combinedUnit.cloneStyleFrom(cellStyle);
		combinedUnit.setFont(font);
		combinedUnit.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		combinedUnit.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// style for No.
		CellStyle combinedNo = sheet.getWorkbook().createCellStyle();
		combinedNo.cloneStyleFrom(cellStyle);
		combinedNo.setAlignment(HorizontalAlignment.CENTER);

		// style for No. of unit name row
		CellStyle combinedNoUnitName = sheet.getWorkbook().createCellStyle();
		combinedNoUnitName.cloneStyleFrom(combinedUnit);
		combinedNoUnitName.setAlignment(HorizontalAlignment.CENTER);
		
		//row = sheet.createRow(0);
		Cell cell = sheet.getRow(0).getCell(0);
		
		if(cell == null) {
			row = sheet.createRow(0);
		} else {
			
		
		
		cell.setCellValue("BẢNG TỔNG HỢP THAY THẾ VẬT TƯ THIẾT BỊ VĂN PHÒNG");

		cell = sheet.getRow(1).getCell(0);
		cell.setCellValue(createTitleDate(condition));
		//Cell cell;

		for (String unitId : mapUnit.keySet()) {
			mapStationeryName = mapUnit.get(unitId);
			numbericalOrderStationery = 1;
			row = sheet.createRow(rowCount++);
			sheet.addMergedRegion(new CellRangeAddress((rowCount - 1), (rowCount - 1), 1, 4));
			RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress((rowCount - 1), (rowCount - 1), 1, 4),
					sheet);
			formula = "ROMAN(" + numericalOrderUnit++ + ", 4)";

			cell = row.createCell(0);
			cell.setCellStyle(combinedNoUnitName);
			cell.setCellType(CellType.FORMULA);
			cell.setCellFormula(formula);

			cell = row.createCell(1);
			cell.setCellStyle(combinedUnit);
			cell.setCellValue(mapUnitName.get(unitId));

			for (VT040010Stationery stationery : mapStationeryName.values()) {
				row = sheet.createRow(rowCount++);

				// No.
				cell = row.createCell(0);
				cell.setCellStyle(combinedNo);
				cell.setCellValue(numbericalOrderStationery++);

				// stationery name
				cell = row.createCell(1);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(stationery.getStationeryName());

				// stationery unit calculation
				cell = row.createCell(2);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(stationery.getStationeryUnitCal());

				// stationery quantity
			/*	formula = "SUMIF('" + unitId + "'!D3:D" + rangeStationery.get(unitId) + ",\""
						+ stationery.getStationeryName() + "\",'" + unitId + "'!F3:F" + rangeStationery.get(unitId)
						+ ")";
				cell = row.createCell(3);
				cell.setCellStyle(cellStyle);
				//cell.setCellValue(stationery.getStationeryQuan());
				cell.setCellType(CellType.FORMULA);
				cell.setCellFormula(formula);*/
				// stationery unit calculation
				cell = row.createCell(3);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(stationery.getStationeryQuan());

				cell = row.createCell(4);
				cell.setCellStyle(cellStyle);
			}
			
		}
		row = sheet.createRow(++rowCount);
		Cell cell1 = row.createCell(3);
		cell1 = row.createCell(3);
		cell1.setCellStyle(cellCvp);
		cell1.setCellValue("LÃNH ĐẠO ĐƠN VỊ");
		//sheet.addMergedRegion(new CellRangeAddress(rowCount,rowCount,3,4));
		
		row = sheet.createRow(++rowCount);
		Cell cellSign1 = row.createCell(3);
		cellSign1 = row.createCell(3);
		//cellStyle.set(BorderStyle.NONE);
		cellSign1.setCellStyle(cellSign);
		cellSign1.setCellValue("<(kysvc1)>");
		try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
			workbook.write(outputStream);
		}
			}
	}
		}
	}
	
	public String createTitleDate(VT040010Condition condition) {
		String title = "";
		try {
			if (condition.getStartDate() != null) {
				Date startDate = condition.getStartDate();
				Calendar cal = GregorianCalendar.getInstance();
				cal.setTime(startDate);
				title += "Từ ngày " + cal.get(Calendar.DAY_OF_MONTH) + " tháng " + (cal.get(Calendar.MONTH) + 1)
						+ " năm " + cal.get(Calendar.YEAR) + " ";
			}

			if (condition.getEndDate() != null) {
				Date endDate = condition.getEndDate();
				Calendar cal = GregorianCalendar.getInstance();
				cal.setTime(endDate);
				title += "Đến ngày " + cal.get(Calendar.DAY_OF_MONTH) + " tháng " + (cal.get(Calendar.MONTH) + 1)
						+ " năm " + cal.get(Calendar.YEAR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return title;
	}
	
	 public String RomanNumerals(int Int) {
		    LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<String, Integer>();
		    roman_numerals.put("M", 1000);
		    roman_numerals.put("CM", 900);
		    roman_numerals.put("D", 500);
		    roman_numerals.put("CD", 400);
		    roman_numerals.put("C", 100);
		    roman_numerals.put("XC", 90);
		    roman_numerals.put("L", 50);
		    roman_numerals.put("XL", 40);
		    roman_numerals.put("X", 10);
		    roman_numerals.put("IX", 9);
		    roman_numerals.put("V", 5);
		    roman_numerals.put("IV", 4);
		    roman_numerals.put("I", 1);
		    String res = "";
		    for(Map.Entry<String, Integer> entry : roman_numerals.entrySet()){
		      int matches = Int/entry.getValue();
		      res += repeat(entry.getKey(), matches);
		      Int = Int % entry.getValue();
		    }
		    return res;
		  }
		  public String repeat(String s, int n) {
		    if(s == null) {
		        return null;
		    }
		    final StringBuilder sb = new StringBuilder();
		    for(int i = 0; i < n; i++) {
		        sb.append(s);
		    }
		    return sb.toString();
		  }

		@Override
		@Transactional
		public ResponseEntityBase updateSignVoffice(SignVofficeEntity signVofficeEntity) throws Exception {
			ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			try {
				
				int count =signVofficeDao.updateSignVoffice(signVofficeEntity);
					if(count>0){
						reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
					}
				
				logger.info("**************** End get list registration SignVoffice - OK ****************");
			} catch (Exception e) {
				logger.info("**************** End get list registration SignVoffice - Fail ****************");
				logger.error(e.getMessage(), e);
			}

			return reb;
		}

		@Override
		@Transactional
		public ResponseEntityBase findIssueServiceByDetailSignVoffice(VT040010Condition condition, Principal principal) throws Exception {
			ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null );
			//String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_VT040010_VTTB");
			String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_EXPORT_REPORT_DETAIL");
			System.out.println("EXCEL_FILE_PATH_EXPORT_REPORT_DETAIL"+excelFilePath);
			try {
				setUpCondition(condition,principal);
				List<VT040010StationeryExcel> listStationeryExcel = signVofficeDao.findIssueServiceByDetailSignVoffice(condition);
				OAuth2Authentication oauth = (OAuth2Authentication) principal;
			
				writeExcelReportDetail(listStationeryExcel, excelFilePath, condition, null);
				
				System.out.println(ConvertExcelToPdf.convertToPDF(new File(convertUrl()+"/documentVO/"+condition.getDetailSignVofficeId()+".xlsx").getParent(), convertUrl()+"/documentVO/"+condition.getDetailSignVofficeId()+".xlsx"));
				String in = new String(convertUrl()+"/documentVO/"+condition.getDetailSignVofficeId()+".pdf");
				String out = new String(convertUrl()+"/documentVO/"+condition.getDetailSignVofficeId()+".out.pdf");
				List<String> patterns = new ArrayList<>();
				patterns.add("gnvohdk360_1");
				AddComment.addCommentSignature(in, out, patterns,(String) oauth.getPrincipal(),linkTemplateExcel.getProperty("PDF_FILE_TEMPLATE_VOFFICE"));
				reb.setData(listStationeryExcel);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

				logger.info("**************** End get list registration SignVoffice - OK ****************");
			} catch (Exception e) {
				logger.info("**************** End get list registration SignVoffice - Fail ****************");
				logger.error(e.getMessage(), e);
			}

			return reb;
		}
		public void setUpCondition(VT040010Condition condition ,Principal principal) {
			try {
				OAuth2Authentication oauth = (OAuth2Authentication) principal;
				String loginUserName = (String) oauth.getPrincipal();
				condition.setLoginUserName(loginUserName);
				if (oauth.getAuthorities().contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
					condition.setLoginRole(Constant.PMQT_ROLE_ADMIN);
				} else if (oauth.getAuthorities().contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))
						|| oauth.getAuthorities().contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP))) {
					condition.setLoginRole(Constant.PMQT_ROLE_MANAGER);
				}
				condition.setRowSize(6);
				condition.setPlaceName("S009");
				if (condition.getListUnitId() == null || condition.getListUnitId().length == 0) {
					if (condition.getLoginRole().equals(Constant.PMQT_ROLE_ADMIN)) {
						condition.setListUnitId(vt040010dao.findListUnit());
					} else if (condition.getLoginRole().equals(Constant.PMQT_ROLE_MANAGER)) {
						condition.setListUnitId(vt040010dao.findUnitManager(condition.getLoginUserName()));
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		public File writeExcelReportDetail(List<VT040010StationeryExcel> stationeryExcel, String excelFilePath,
				VT040010Condition condition, String path) throws IOException {

			File outFile = null;
			File file = null;

			Date date = new Date();
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(date);
			try {
				Resource resource = new ClassPathResource(excelFilePath);
				file = resource.getFile();
				if(path == null) {
				  outFile = new File(convertUrl()+"/documentVO/"+condition.getDetailSignVofficeId()+".xlsx");
				} else {
				  outFile = new File(path);
				}
				//outFile = new File(filePath.split("templateExcel")[0] + "saveExcel\\VT04\\VTTB\\" + fileName);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}

			try (FileInputStream inputStream = new FileInputStream(file)) {
				try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
					// SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(inputStream),
					// 100)
					Map<String, Integer> rangeStationery = new HashMap<>();
					Map<String, Map<String, VT040010IssueService>> mapUnit = handleData(stationeryExcel);
					int rowCount = 2;
					int i = 1;
					int lengthListStationery = 2; // start with row index = 2
					boolean isNewIssue = true;
					CreationHelper createHelper = workbook.getCreationHelper();
					CellStyle cellStyle = workbook.createCellStyle();
					CellStyle combined = workbook.createCellStyle();
					CellStyle combinedNo = workbook.createCellStyle();
					CellStyle combinedNote = workbook.createCellStyle();
					CellStyle cellSign = workbook.createCellStyle();
					CellStyle cellCvp = workbook.createCellStyle();
					Font font = workbook.createFont();

					
					Font fontSign = workbook.createFont();
					fontSign.setFontName("Times New Roman");
					fontSign.setFontHeightInPoints((short)13);
					fontSign.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
					cellSign.setAlignment(HorizontalAlignment.CENTER);
					cellSign.setFont(fontSign);
					
					Font fontCvp = workbook.createFont();
					fontCvp.setFontHeightInPoints((short)14);
					fontCvp.setBold(true);
					cellCvp.setFont(fontCvp);
					cellCvp.setAlignment(HorizontalAlignment.CENTER);
					
					font.setFontName("Times New Roman");
					font.setFontHeightInPoints((short) 12);
					cellStyle.setFont(font);
					cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
					cellStyle.setFillPattern(FillPatternType.NO_FILL);
					cellStyle.setBorderTop(BorderStyle.THIN);
					cellStyle.setBorderRight(BorderStyle.THIN);
					cellStyle.setBorderBottom(BorderStyle.THIN);
					cellStyle.setBorderLeft(BorderStyle.THIN);

					// style for column No.
					combinedNo.cloneStyleFrom(cellStyle);
					combinedNo.setAlignment(HorizontalAlignment.CENTER);

					// style for column date
					combined.cloneStyleFrom(cellStyle);
					combined.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));

					// style for column Note
					combinedNote.cloneStyleFrom(cellStyle);
					combinedNote.setWrapText(true);
					SimpleDateFormat dt1 = new SimpleDateFormat("MM/yyyy");
					String dateIt = "";
					if(condition.getStartDate()!= null){
						dateIt =dt1.format(condition.getStartDate());
					}else if (condition.getEndDate()!= null) {
						dateIt =dt1.format(condition.getEndDate());
					}
					// write excel
					// create sheet
					for (String unitId : mapUnit.keySet()) {
						Sheet sheet = workbook.cloneSheet(0);
						sheet.setFitToPage(true);
						sheet.getPrintSetup().setLandscape(true);
						sheet.getPrintSetup().setFitWidth((short) 1);
					    sheet.getPrintSetup().setFitHeight((short) 0);
					    sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
						// try to fix hidden sheet tabs
						workbook.setSheetHidden(i, false);
						workbook.setSheetVisibility(i, SheetVisibility.VISIBLE);
						
						workbook.setSheetName(i++, String.valueOf(unitId));
						
						Map<String, VT040010IssueService> mapIss = mapUnit.get(unitId);
						rowCount = 2;
						lengthListStationery = 2;

						// set title
						Cell cell = sheet.getRow(0).getCell(0);
						cell.setCellValue("Dịch vụ IT tháng "+dateIt+" của "+mapIss.entrySet().iterator().next().getValue().getUnitName());

						// fill data
						for (String issId : mapIss.keySet()) {
							VT040010IssueService itemIss = mapIss.get(issId);
							isNewIssue = true;
							lengthListStationery += itemIss.getStationeries().size();

							for (VT040010Stationery sta : itemIss.getStationeries()) {
								writeBook(itemIss, sta, sheet, rowCount++, cellStyle, combinedNo, combined, combinedNote,
										isNewIssue);
								isNewIssue = false;
							}
							
							
						}
						
						Row row = sheet.createRow(rowCount++);
						row = sheet.createRow(rowCount++);
						Cell cellDv = row.createCell(6);
						cellDv = row.createCell(6);
						cellDv.setCellStyle(cellCvp);
						cellDv.setCellValue("LÃNH ĐẠO ĐƠN VỊ");
					//	sheet.addMergedRegion(new CellRangeAddress(rowNum-1,rowNum-1,3,4));
						
						row = sheet.createRow(rowCount++);
						Cell cellSign1 = row.createCell(6);
						cellSign1 = row.createCell(6);
						//cellStyle.set(BorderStyle.NONE);
						cellSign1.setCellStyle(cellSign);
						cellSign1.setCellValue("<(gnvohdk360_1)>");

						rangeStationery.put(unitId, lengthListStationery);
					}

					// write summary sheet
					//createSummarySheet(stationeryExcel, rangeStationery, workbook.getSheetAt(0), cellStyle, condition);
					workbook.removeSheetAt(0);
					workbook.setForceFormulaRecalculation(true);

					try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
						workbook.write(outputStream);
					}
				}
			}

			return outFile;
		}
		
		public Map<String, Map<String, VT040010IssueService>> handleData(List<VT040010StationeryExcel> stationeryExcel) {
			// list sheet unit
			Map<String, Map<String, VT040010IssueService>> mapUnit = new HashMap<>();

			for (VT040010StationeryExcel item : stationeryExcel) {
				// collect issue service by unit id
				if (mapUnit.containsKey(item.getUnitId())) {
					Map<String, VT040010IssueService> mapIssueService = mapUnit.get(item.getUnitId());

					if (mapIssueService.containsKey(item.getIssueServiceId())) {
						VT040010Stationery sta = new VT040010Stationery(item.getStationeryName(), null,
								item.getStationeryQuan(), item.getStationeryUnitCal());
						mapIssueService.get(item.getIssueServiceId()).addStationery(sta);
					} else {
						
//						VT040010IssueService iss = new VT040010IssueService(item.getIssueServiceId(), item.getUnitName(),
//								item.getServiceName(), item.getTimeStartPlan(), item.getEmpName(), item.getNote());
						
						VT040010IssueService iss = new VT040010IssueService();

						iss.setIssueServiceId(item.getIssueServiceId());
						iss.setUnitName(item.getUnitName());
						iss.setServiceName(item.getServiceName());
						iss.setTimeStartPlan(item.getTimeStartPlan());
						iss.setReciverName(item.getReciverName());
						iss.setEmpName(item.getEmpName());
						iss.setNote(item.getNote());
						iss.setStatus(item.getStatus());
						
						VT040010Stationery sta = new VT040010Stationery(item.getStationeryName(), null,
								item.getStationeryQuan(), item.getStationeryUnitCal());
						
						iss.addStationery(sta);
						mapIssueService.put(item.getIssueServiceId(), iss);
					}

					mapUnit.put(item.getUnitId(), mapIssueService);
				} else {
					Map<String, VT040010IssueService> mapIssueService = new HashMap<>();
					
//					VT040010IssueService iss = new VT040010IssueService(item.getIssueServiceId(), item.getUnitName(),
//							item.getServiceName(), item.getTimeStartPlan(), item.getEmpName(), item.getNote());
//					
					
					VT040010IssueService iss = new VT040010IssueService();

					iss.setIssueServiceId(item.getIssueServiceId());
					iss.setUnitName(item.getUnitName());
					iss.setServiceName(item.getServiceName());
					iss.setTimeStartPlan(item.getTimeStartPlan());
					iss.setReciverName(item.getReciverName());
					iss.setEmpName(item.getEmpName());
					iss.setNote(item.getNote());
					iss.setStatus(item.getStatus());
					
					VT040010Stationery sta = new VT040010Stationery(item.getStationeryName(), null,
							item.getStationeryQuan(), item.getStationeryUnitCal());
					iss.addStationery(sta);

					mapIssueService.put(item.getIssueServiceId(), iss);
					mapUnit.put(item.getUnitId(), mapIssueService);
				}
			}

			return mapUnit;
		}
		
		private void writeBook(VT040010IssueService itemIss, VT040010Stationery stationeries, Sheet sheet, int rowCount,
				CellStyle cellStyle, CellStyle combinedNo, CellStyle combined, CellStyle combinedNote, boolean isNewIssue) {

			Row row = sheet.createRow(rowCount);

			// No.
			Cell cell = row.createCell(0);
			cell.setCellStyle(combinedNo);
			cell.setCellValue((double) rowCount - 1);

			// issue service id
			cell = row.createCell(1);
			cell.setCellStyle(cellStyle);
			if (isNewIssue) {
				cell.setCellValue(itemIss.getIssueServiceId());
			}

			// service name
			cell = row.createCell(2);
			cell.setCellStyle(cellStyle);
			if (isNewIssue) {
				cell.setCellValue(itemIss.getServiceName());
			}

			// name of stationery
			cell = row.createCell(3);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(stationeries.getStationeryName());

			// stationery unit calculation
			cell = row.createCell(4);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(stationeries.getStationeryUnitCal());

			// end time plan
			cell = row.createCell(5);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(stationeries.getStationeryQuan());
			
			// start time to do service
			cell = row.createCell(6);
			cell.setCellStyle(combined);
			if (isNewIssue) {
				cell.setCellValue(itemIss.getTimeStartPlan());
			}
			
			// nguoi nhan name
			cell = row.createCell(7);
			cell.setCellStyle(cellStyle);
			if (isNewIssue) {
				cell.setCellValue(itemIss.getReciverName());
			}

			// requester name
			cell = row.createCell(8);
			cell.setCellStyle(cellStyle);
			if (isNewIssue) {
				cell.setCellValue(itemIss.getEmpName());
			}
		
		}
		
		@Override
		public ResponseEntityBase findSignVofficeById(Long signVofficeId) {
			ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			try {
				
				SignVofficeEntity signVofficeEntity =signVofficeDao.findSignVofficeById(signVofficeId);
					if(signVofficeEntity != null){
						reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
						reb.setData(signVofficeEntity);
					}
				
				logger.info("**************** End get list registration SignVoffice - OK ****************");
			} catch (Exception e) {
				logger.info("**************** End get list registration SignVoffice - Fail ****************");
				logger.error(e.getMessage(), e);
			}

			return reb;
		}
		
		private String convertUrl(){
		    int lastRegex = System.getProperty("catalina.home").lastIndexOf(File.separator);
		    String url=System.getProperty("catalina.home").substring(0, lastRegex);
		    
		    File theDir = new File(url+"/documentVO");
				 if (!theDir.exists()) {
					 theDir.getName();
				     try{
				         theDir.mkdir();
				     } 
				     catch(SecurityException se){
				     }        
		
				 }
		    return url;
		}

}
