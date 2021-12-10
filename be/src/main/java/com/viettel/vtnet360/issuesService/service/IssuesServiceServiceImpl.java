package com.viettel.vtnet360.issuesService.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.common.util.CalculateDate;
import com.viettel.vtnet360.issuesService.entity.EmployeeEntity;
import com.viettel.vtnet360.issuesService.entity.IssuesService;
import com.viettel.vtnet360.issuesService.entity.IssuesServiceEntity;
import com.viettel.vtnet360.issuesService.entity.IssuesServiceSearch;
import com.viettel.vtnet360.issuesService.entity.MSystemCodeEntity;
import com.viettel.vtnet360.issuesService.entity.PlaceEntity;
import com.viettel.vtnet360.issuesService.entity.PlaceResult;
import com.viettel.vtnet360.issuesService.entity.ServiceEntity;
import com.viettel.vtnet360.issuesService.entity.UnitEntity;
import com.viettel.vtnet360.issuesService.entity.UnitResult;
import com.viettel.vtnet360.issuesService.request.dao.IssuesServiceDAO;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt00.common.TokenAdditionInfo;
import com.viettel.vtnet360.vt02.vt020006.dao.VT020006DAO;
import com.viettel.vtnet360.vt04.vt040000.dao.VT040000DAO;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityAdditionalInfo;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityRqFind;
import com.viettel.vtnet360.vt04.vt040002.dao.VT040002DAO;
import com.viettel.vtnet360.vt04.vt040002.entity.VT040002HistoryService;
import com.viettel.vtnet360.vt04.vt040002.entity.VT040002Stationery;
import com.viettel.vtnet360.vt04.vt040003.dao.VT040003DAO;
import com.viettel.vtnet360.vt04.vt040004.entity.VT040004EntityRq;
import com.viettel.vtnet360.vt04.vt040007.dao.VT040007DAO;
import com.viettel.vtnet360.vt04.vt040007.entity.VT040007EntityDataSt;
import com.viettel.vtnet360.vt04.vt040007.entity.VT040007EntityRqExecutive;
import com.viettel.vtnet360.vt05.vt050000.dao.VT050000DAO;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000AdditionalInfoToSendNotify;
import com.viettel.vtnet360.vt05.vt050000.service.VT050000Service;
import com.viettel.vtnet360.vt05.vt050003.dao.VT050003DAO;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003InfoToFindHcdv;
import com.viettel.vtnet360.vt05.vt050012.dao.VT050012DAO;

@Service
public class IssuesServiceServiceImpl implements IssuesServiceService {

	private final Logger logger = Logger.getLogger(this.getClass());
	private final static String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	
	@Autowired
	private VT040002DAO vt040002dao;
	
	@Autowired
	private VT050000DAO vt050000DAO;
	
	@Autowired
	private VT050000Service vt050000Service;
	
	@Autowired
	IssuesServiceDAO issuesServiceDAO;

	@Autowired
	private VT050012DAO vt050012Dao;
	
	@Autowired
	private VT050003DAO vt050003DAO;
	
	@Autowired
	private VT020006DAO vt020006DAO;
	
/*	@Autowired
	private StationeryReportDAO stationeryReportDAO;*/
	
	@Autowired
	private AuthorizationServerTokenServices tokenServices;

	@Autowired
	Notification notification;

	@Autowired
	Sms sms;

	@Autowired
	VT040003DAO vt040003DAO;

	@Autowired
	VT040000DAO vt040000DAO;
	
	@Autowired
	VT040007DAO vt040007DAO;

	@Autowired
	Properties notifyMessage;

	@Autowired
	Properties smsMessage;
	
	@Autowired
	private Properties linkTemplateExcel;

	/*@Transactional(readOnly = true)
	public ResponseEntityBase getRequestModel(VT050012RequestParam param) {
		logger.info("==============Get request model start =====================");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// validate listStatus
			if (param.getListStatus() == null || param.getListStatus().isEmpty()) {
				List<Integer> listStatus = new ArrayList<>();
				listStatus.add(Constant.ISSUES_STATIONERY_PROCESSING);
				listStatus.add(Constant.ISSUES_STATIONERY_COMPLETE_SECTION);
				listStatus.add(Constant.ISSUES_STATIONERY_COMPLETE);
				listStatus.add(Constant.ISSUES_STATIONERY_REJECT);
				listStatus.add(Constant.ISSUES_STATIONERY_CANCEL);
				param.setListStatus(listStatus);
			}
			List<VT050012DataResponse> requests = vt050012Dao.findData(param);
			int totalRecords = vt050012Dao.countRequest(param);
			resp.setData(new ResquestModel(requests, totalRecords));
			logger.info("==============Get request model end =====================");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("==============Get request model fail=====================");
		}
		return resp;
	}*/

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findEmployeeByFullName(EmployeeEntity employee) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<EmployeeEntity> listEmployee = issuesServiceDAO.findEmployeeByFullName(employee);
			resp.setData(listEmployee);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	@Transactional
	public ResponseEntityBase insertIssuesService(IssuesServiceEntity requestParam, Principal principal) {
		// get time now
		Date dateNow = new Date(System.currentTimeMillis());

		// use token
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();

		// create Map Oject cotain param
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("returnedId", null);
		data.put("userName", userName);
		data.put("requestParam", requestParam);
		data.put("dateNow", dateNow);

		// set reponse default
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			
			List<Date> offDays = vt020006DAO.findDayOff(Constant.STATUS_ACTIVE);
			requestParam.setTimeFinishPlan(CalculateDate.calculateFinishTime(requestParam.getFullFillTime(), offDays));
			requestParam.setTimeStartPlan(CalculateDate.calculateStartTime(offDays));
			
			List<Map<String, Object>> validService = vt040003DAO.isServiceValid(data);

			if (validService.isEmpty()) {
				response.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return response;
			} else if(issuesServiceDAO.checkExitsIssuesService(userName).size()>0){
				response.setStatus(Constant.RESPONSE_EXIST_SERVICE_NOT_VALID);
				return response;
			} else{
				issuesServiceDAO.insertIssuesService(data);
				response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				String returnId = (String) data.get("issuesServiceId");

				// try sms and notify
				trysendSmsNotify(returnId, requestParam, userName);
			}

			// end try send notifi and sms
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		// set reponse
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findEmployeeByMulti(EmployeeEntity employee) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<EmployeeEntity> listEmployee = issuesServiceDAO.findEmployeeByMulti(employee);
			resp.setData(listEmployee);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListPlace(PlaceEntity place) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<PlaceEntity> listPlace = issuesServiceDAO.findListPlace(place);
			resp.setData(listPlace);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListService(ServiceEntity service) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<ServiceEntity> listService = issuesServiceDAO.findListService(service);
			resp.setData(listService);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findListIssuesServiceBrowser(IssuesServiceSearch issuesServiceSearch) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT_PATTERN);
			if (issuesServiceSearch.getFromDate() != null && issuesServiceSearch.getFromDate().trim().length() != 0)
				issuesServiceSearch.setFromDateConvert(df.parse(issuesServiceSearch.getFromDate()));
			if (issuesServiceSearch.getToDate() != null && issuesServiceSearch.getToDate().trim().length() != 0)
				issuesServiceSearch.setToDateConvert(df.parse(issuesServiceSearch.getToDate()));
			List<IssuesServiceEntity> listIssuesService = issuesServiceDAO
					.findListIssuesServiceBrowser(issuesServiceSearch);
			resp.setData(listIssuesService);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase updateApprovedIssesService(IssuesServiceSearch issuesServiceSearch) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<IssuesServiceEntity> listIssuesService = issuesServiceSearch.getListIdIssuesService();
			for (IssuesServiceEntity IssuesService : listIssuesService) {
				IssuesService.setUserNameUser(issuesServiceSearch.getUserNameUser());
				issuesServiceDAO.updateApprovedIssesService(IssuesService);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findIssuesServiceById(IssuesServiceEntity issuesService) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			resp.setData(issuesServiceDAO.findIssuesServiceById(issuesService));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findListUnit(UnitEntity unit) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<UnitEntity> listUnit = issuesServiceDAO.findListUnit(unit);
			resp.setData(listUnit);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findListIssuesServiceApprovedRequest(IssuesServiceSearch issuesServiceSearch) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT_PATTERN);
			if (issuesServiceSearch.getFromDate() != null && issuesServiceSearch.getFromDate().trim().length() != 0)
				issuesServiceSearch.setFromDateConvert(df.parse(issuesServiceSearch.getFromDate()));
			if (issuesServiceSearch.getToDate() != null && issuesServiceSearch.getToDate().trim().length() != 0)
				issuesServiceSearch.setToDateConvert(df.parse(issuesServiceSearch.getToDate()));
			List<IssuesServiceEntity> listIssuesService = issuesServiceDAO
					.findListIssuesServiceApprovedRequest(issuesServiceSearch);
			resp.setData(listIssuesService);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase updateIssuedService(IssuesServiceEntity requestParam, Principal principal,
			OAuth2Authentication authentication) {
		// take value from token
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();

		// timeNow
		Date timeNow = new Date(System.currentTimeMillis());
		IssuesServiceEntity issuesService = issuesServiceDAO.findIssuesServiceById(requestParam);
		Double realTimeTotal = (double) (new Date().getTime() - issuesService.getTimeResume().getTime());
		requestParam.setRealTimeTotal(issuesService.getRealTimeTotal() + (realTimeTotal / 1000));
		// create Map Object cotain param
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("userName", userName);
		data.put("timeNow", timeNow);
		data.put("requestParam", requestParam);

		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			issuesServiceDAO.updateIssuedService(data);
			response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return response;
	}
	
	@Override
	@Transactional
	public ResponseEntityBase executiveService(VT040007EntityRqExecutive requestParam, Principal principal)
			throws Exception {
		   Date dateNow = new Date(System.currentTimeMillis());
		// take value from token
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();

		// timeNow
		Date timeNow = new Date(System.currentTimeMillis());
		
		// set reponse default
		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		Map<String, Object> dataInfo = vt040000DAO.infoIssuedService(requestParam.getIssuedServiceId());
		Long realTimeTotal= 0l;
		if(dataInfo.get("realTimeTotal")!= null){
			BigDecimal realTimeTotalBig= (BigDecimal) dataInfo.get("realTimeTotal");
			realTimeTotal=realTimeTotalBig.longValue();
		}
		Date timeStartActual =null;
		if(dataInfo.get("timeStartActual") != null){
			timeStartActual = (Date)dataInfo.get("timeStartActual");
		}
		Date timeStartPlan =null;
		if(dataInfo.get("timeStartPlan") != null){
			timeStartPlan = (Date)dataInfo.get("timeStartPlan");
		}
		
		Date timeResume =null;
		if(dataInfo.get("timeResume") != null){
			timeResume = (Date)dataInfo.get("timeResume");
		}
		Date postponeToTime =null;
		if(dataInfo.get("postponeToTime") != null){
			postponeToTime = (Date)dataInfo.get("postponeToTime");
		}
		int statusIssuedService = (int) dataInfo.get("status");
		if(requestParam.getFlagExecutive()==3 && requestParam.getStatusStart()==1){
			List<Date> offDays = issuesServiceDAO.findDayOff(timeStartPlan);
			requestParam.setRealTimeTotal(CalculateDate.getTotalHour(timeStartPlan, dateNow, offDays));
		}else if (requestParam.getFlagExecutive()==3&&(timeStartActual== null)) {
			requestParam.setStatusStart(1);
			requestParam.setRealTimeTotal(realTimeTotal);
		}else if (requestParam.getFlagExecutive()==4 ) {
			if(statusIssuedService == 1){
				List<Date> offDays = issuesServiceDAO.findDayOff(timeStartPlan);
				requestParam.setRealTimeTotal(CalculateDate.getTotalHour(timeStartPlan, dateNow, offDays));
			}else {
				if(postponeToTime == null||timeResume.getTime()<= postponeToTime.getTime()){
					List<Date> offDays = issuesServiceDAO.findDayOff(timeResume);
					requestParam.setRealTimeTotal(realTimeTotal+CalculateDate.getTotalHour(timeResume, dateNow, offDays));
				}else {
					List<Date> offDays = issuesServiceDAO.findDayOff(postponeToTime);
					requestParam.setRealTimeTotal(realTimeTotal+CalculateDate.getTotalHour(postponeToTime, dateNow, offDays));
				}
			}

		}else if(requestParam.getFlagExecutive()==5){
			if(realTimeTotal== 0L){
				List<Date> offDays = issuesServiceDAO.findDayOff(timeStartPlan);
				requestParam.setRealTimeTotal(CalculateDate.getTotalHour(timeStartPlan, dateNow, offDays));
			}else{
				if(statusIssuedService==4){
					requestParam.setRealTimeTotal(realTimeTotal);
				}else{
					List<Date> offDays = issuesServiceDAO.findDayOff(timeResume);
					requestParam.setRealTimeTotal(realTimeTotal+CalculateDate.getTotalHour(timeResume, dateNow, offDays));
				}
			}
		} else if (requestParam.getFlagExecutive()==6 || requestParam.getFlagExecutive()==7 ) {
			if(postponeToTime == null||timeResume.getTime()<= postponeToTime.getTime()){
				List<Date> offDays = issuesServiceDAO.findDayOff(timeResume);
				requestParam.setRealTimeTotal(realTimeTotal+CalculateDate.getTotalHour(timeResume, dateNow, offDays));
			}else {
				List<Date> offDays = issuesServiceDAO.findDayOff(postponeToTime);
				requestParam.setRealTimeTotal(realTimeTotal+CalculateDate.getTotalHour(postponeToTime, dateNow, offDays));
			}
		}
		// create Map Object contain param
		Map<String, Object> data = new HashMap<String, Object>();
		
		if(requestParam.getStatusStart()==1){
			data.put("statusStart", 0);	
		}else{
			data.put("statusStart", 1);
		}
		
		data.put("userName", userName);
		data.put("timeNow", timeNow);
		data.put("requestParam", requestParam);
		data.put("fullFillTime", dataInfo.get("fullFillTime"));

		if (checkStatusBeforeInvalid(statusIssuedService, requestParam.getFlagExecutive())) {

			reponse.setStatus(Constant.RESPONSE_ISSUED_SERVICE_CHANGED_STATUS);
			return reponse;
		}

		// try executiveService
		try {

			// save post pone
			if (Constant.FLAG_PENDING_EXECUTIVE_IS_SV == requestParam.getFlagExecutive()) {
				int checkExisted = -1;
				checkExisted = vt040007DAO.checkExisted(requestParam.getIssuedServiceId());
				if(checkExisted == 0)
					vt040007DAO.savePostPoneHistory(data);
				else {
					String resonPostponeExecutive = vt040007DAO.findIssuesServiceHistoryByIdIss(requestParam.getIssuedServiceId());
					resonPostponeExecutive = resonPostponeExecutive +" || ["+ userName+"]["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"][" +requestParam.getResonPostponeExecutive()+"]";
					data.put("resonPostponeExecutive", resonPostponeExecutive);
					vt040007DAO.updateHistory(data);
				}
					
			}

			// after sql data contain more statusStart
			issuesServiceDAO.executiveService(data);

			// put issuedServiceId into addInfomation json
			VT040000EntityAdditionalInfo addInfomation = new VT040000EntityAdditionalInfo();
			addInfomation.setId(requestParam.getIssuedServiceId());

			// Case get requestParam flag executive = FLAG_COMPLETE_IS_SV
			if (Constant.FLAG_COMPLETE_IS_SV == requestParam.getFlagExecutive()
					&& requestParam.getStationery() != null) {

				// we fill each other stationery user used into database
				for (VT040007EntityDataSt temp : requestParam.getStationery()) {
					data.put("stationeryId", temp.getStationeryId());
					data.put("stationeryQuantity", temp.getStationeryQuantity());
					data.put("unitPrice", temp.getUnitPrice());
					vt040007DAO.fillStationery(data);
				}
			}

			int statusStart = (int) data.get("statusStart");

			trySmsNotify(requestParam, addInfomation, statusStart, userName);

			// set reponse
			reponse.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return reponse;

	}

	private Boolean checkStatusBeforeInvalid(int statusIssuedService, int flagExcutive) {

		if (statusIssuedService == Constant.ISSUED_SERVICE_STATUS_WAITING_APROVE
				|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_REJECT_APROVED
				|| statusIssuedService == Constant.ISSUED_SERVICE_CANCEL
				|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_RECEIVE
				|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_REJECT
				|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_CANT_EXECUTE
				|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_COMPLETE
				) {

			return true;
		}

		if (statusIssuedService == Constant.ISSUED_SERVICE_STATUS_EXECUTING
				&& flagExcutive == Constant.ISSUED_SERVICE_STATUS_EXECUTING) {
			return true;
		}

		if (statusIssuedService == Constant.ISSUED_SERVICE_STATUS_PENDING_EXECUTE
				&& flagExcutive == Constant.ISSUED_SERVICE_STATUS_PENDING_EXECUTE) {
			return true;
		}

		return false;

	}
	private void trySmsNotify(VT040007EntityRqExecutive requestParam, VT040000EntityAdditionalInfo addInfomation,
			int statusStart, String userName) {

		/// kiênnen

		Thread notiThread = new Thread() {
			public void run() {
				try {

					// NOTIFLY AND SMS HANDLER
					// find name service by issuedServiceId
					// String serviceName;
					Map<String, Object> dataSmsNoti = vt040000DAO.infoIssuedService(requestParam.getIssuedServiceId());
					// serviceName = (String) dataSmsNoti.get("serviceName");
					String serviceId = (String) dataSmsNoti.get("serviceId");

					// set default property
					String messageNotify = "";
					String title = "";
					String messageSms = "";
					String typeSms = "";
					String roleEmp = Constant.PMQT_ROLE_EMPLOYYEE;

					// Case get requestParam flag executive = FLAG_COMPLETE_IS_SV
					if (Constant.FLAG_COMPLETE_IS_SV == requestParam.getFlagExecutive()) {

						// notify complete Issued Service
						// build message notification
						messageNotify = MessageFormat.format(smsMessage.getProperty("S32"), serviceId.toUpperCase());
						title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

						// build message Sms
						messageSms = MessageFormat.format(smsMessage.getProperty("S32"), serviceId.toUpperCase());
						typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_EXECUTIVE");

						// Case get requestParam flag executive = FLAG_EXECUTIVING_IS_SV
					} else if (Constant.FLAG_EXECUTIVING_IS_SV == requestParam.getFlagExecutive()) {

						// Case Issued Service status is status executive
						if (statusStart == 0) {

							// notifly continue executive Issued Service
							// build message notification
							messageNotify = MessageFormat.format(smsMessage.getProperty("S28"),
									serviceId.toUpperCase());
							title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

							// build message Sms
							messageSms = MessageFormat.format(smsMessage.getProperty("S28"), serviceId.toUpperCase());
							typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_EXECUTIVE");

							// Case Issued Service continue is start executive
						} else {

							// notifly start executive Issued Service
							// build message notification
							messageNotify = MessageFormat.format(smsMessage.getProperty("S30"),
									serviceId.toUpperCase());
							title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

							// build message Sms
							messageSms = MessageFormat.format(smsMessage.getProperty("S30"), serviceId.toUpperCase());
							typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_EXECUTIVE");
						}

						// Case get requestParam flag executive = flag pending executive Issued Service
					} else if (Constant.FLAG_PENDING_EXECUTIVE_IS_SV == requestParam.getFlagExecutive()) {

						// notify pending executive Issued Service
						// build message notification
						DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
						messageNotify = MessageFormat.format(smsMessage.getProperty("S29"), serviceId.toUpperCase(),
								dateformat.format(requestParam.getDatePostpone()),
								requestParam.getResonPostponeExecutive().toUpperCase());

						title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

						// build message Sms
						messageSms = MessageFormat.format(smsMessage.getProperty("S29"), serviceId.toUpperCase(),
								dateformat.format(requestParam.getDatePostpone()),
								requestParam.getResonPostponeExecutive().toUpperCase());
						typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_EXECUTIVE");

						// Case get requestParam flag executive = flag cant excutive Issued Service
					} else if (Constant.FLAG_CANT_EXECUTIVE_IS_SV == requestParam.getFlagExecutive()) {

						// notify cant excutive Issued Service
						// build message notification
						messageNotify = MessageFormat.format(smsMessage.getProperty("S31"), serviceId.toUpperCase());
						title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

						// build message Sms
						messageSms = MessageFormat.format(smsMessage.getProperty("S31"), serviceId.toUpperCase());
						typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_EXECUTIVE");
					}

					// send notification
					addInfomation.setRoleReceiver(roleEmp);
					notification.sendNotification((String) dataSmsNoti.get("empUserName"), addInfomation.toString(),
							messageNotify, title, Constant.TYPE_NOTIFY_MODUL04, userName,
							requestParam.getFlagExecutive());

					// send Sms
					sms.sendSms((int) dataSmsNoti.get("empId"), messageSms, Constant.STATUS_NEW_SMS,
							(String) dataSmsNoti.get("empPhone"), typeSms);

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					logger.info("****************************THREAD NOTI ERROR********:");

				}
			}

		};

		notiThread.start();

	}
	@Override
	public ResponseEntityBase searchFullNameInRole(String searchDTO) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<String> list = issuesServiceDAO.searchFullNameInRole(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchPlaceIsRole(String searchDTO) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<PlaceResult> list = issuesServiceDAO.searchPlaceIsRole(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;

	}

	@Override
	public ResponseEntityBase searchUnitName(String searchDTO) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<UnitResult> list = issuesServiceDAO.searchUnitName(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase totalIssuedService(VT040000EntityRqFind requestParam, Principal principal,
			OAuth2Authentication authentication) {
		/// take value from token
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();

		// get info from authentication
		Map<String, Object> additionalInfo = tokenServices.getAccessToken(authentication).getAdditionalInformation();
		TokenAdditionInfo tokenInfo = (TokenAdditionInfo) additionalInfo.get("userInfor");

		// build param used Map object contain param
		Map<String, Object> dataPram = new HashMap<String, Object>();
		dataPram.put("myUnitId", tokenInfo.getUnitId());
		dataPram.put("userName", userName);
		dataPram.put("requestParam", requestParam);

		// set role
		dataPram.put(Constant.PMQT_ROLE_ADMIN, null);
		dataPram.put(Constant.PMQT_ROLE_MANAGER, null);
		dataPram.put(Constant.PMQT_ROLE_EMPLOYYEE, null);
		dataPram.put(Constant.PMQT_ROLE_STAFF, null);
		dataPram.put(Constant.PMQT_ROLE_MANAGER_CVP, null);

		// check role user
		Collection<GrantedAuthority> arrayRoleToken = (Collection<GrantedAuthority>) oauth.getAuthorities();
		for (GrantedAuthority temp : arrayRoleToken) {

			// check and push role into Map Object param if user had role
			logger.info("USER ROLE ALL:" + temp.getAuthority());

			// if user had role PMQT_ROLE_ADMIN
			if (Constant.PMQT_ROLE_ADMIN.equalsIgnoreCase(temp.getAuthority())) {
				dataPram.put(Constant.PMQT_ROLE_ADMIN, Constant.PMQT_ROLE_ADMIN);

				// if user had role PMQT_ROLE_MANAGER
			} else if (Constant.PMQT_ROLE_MANAGER.equalsIgnoreCase(temp.getAuthority())) {
				dataPram.put(Constant.PMQT_ROLE_MANAGER, Constant.PMQT_ROLE_MANAGER);

				// if user had role PMQT_ROLE_EMPLOYYEE
			} else if (Constant.PMQT_ROLE_EMPLOYYEE.equalsIgnoreCase(temp.getAuthority())) {
				dataPram.put(Constant.PMQT_ROLE_EMPLOYYEE, Constant.PMQT_ROLE_EMPLOYYEE);

				// if user had role PMQT_ROLE_STAFF
			} else if (Constant.PMQT_ROLE_STAFF.equalsIgnoreCase(temp.getAuthority())) {
				dataPram.put(Constant.PMQT_ROLE_STAFF, Constant.PMQT_ROLE_STAFF);

				// if user had role PMQT_CVP
			} else if (Constant.PMQT_ROLE_MANAGER_CVP.equalsIgnoreCase(temp.getAuthority())) {
				dataPram.put(Constant.PMQT_ROLE_MANAGER_CVP, Constant.PMQT_ROLE_MANAGER_CVP);
			}

		}
		// log info
		logger.info("USER PMQT_ROLE_ADMIN:" + dataPram.get(Constant.PMQT_ROLE_ADMIN));
		logger.info("USER PMQT_ROLE_MANAGER:" + dataPram.get(Constant.PMQT_ROLE_MANAGER));
		logger.info("USER PMQT_ROLE_EMPLOYYEE:" + dataPram.get(Constant.PMQT_ROLE_EMPLOYYEE));
		logger.info("USER PMQT_ROLE_STAFF:" + dataPram.get(Constant.PMQT_ROLE_STAFF));
		int total = -1;
		// set reponse default
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			total = issuesServiceDAO.totalIssuedService(dataPram);
			response.setData(total);
			response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return response;
	}

	@Override
	public ResponseEntityBase totalIssuesedServiceForApprove(VT040004EntityRq requestParam, Principal principal,
			OAuth2Authentication authentication) {
		// get info User from Principal
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();

		// create MAP Object contain param
		Map<String, Object> dataPram = new HashMap<String, Object>();
		dataPram.put("requestParam", requestParam);
		dataPram.put("userName", userName);
		dataPram.put(Constant.PMQT_ROLE_ADMIN, null);

		// check role user admin
		Collection<GrantedAuthority> arrayRoleToken = (Collection<GrantedAuthority>) oauth.getAuthorities();
		for (GrantedAuthority temp : arrayRoleToken) {
			// if user had role PMQT_ROLE_ADMIN
			if (Constant.PMQT_ROLE_ADMIN.equalsIgnoreCase(temp.getAuthority())) {
				dataPram.put(Constant.PMQT_ROLE_ADMIN, Constant.PMQT_ROLE_ADMIN);
				break;
			}
		}

		// set reponse default
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		int totalIssuesServiceForApprove = -1;
		// try executive
		try {
			totalIssuesServiceForApprove = issuesServiceDAO.totalIssuesedServiceForApprove(dataPram);
			response.setData(totalIssuesServiceForApprove);
			response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return response;
	}

	private void trysendSmsNotify(String returnId, IssuesServiceEntity requestParam, String userName) {

		Thread notiThread = new Thread() {
			public void run() {
				try {

					// send sms and notify
					String userNameManager;
					int toUserid;
					String Phone;
					String roleToUserName = Constant.PMQT_ROLE_MANAGER;

					// find info by returnId
					Map<String, Object> data = vt040000DAO.infoIssuedService(returnId);
					//Object phoneData = data.get("qlttPhone");
					//if(phoneData != null) {
					  VT040000EntityAdditionalInfo addInfomation = new VT040000EntityAdditionalInfo();
					  addInfomation.setId(returnId);

	          // Case Issued Service have Approver QLTT
	          if(requestParam.getAppoverQltt() != null){
	        	  userNameManager = requestParam.getAppoverQltt();
		          toUserid = (int) data.get("qlttId");
		          Phone = (String) data.get("qlttPhone");
		          
		          // build message and send notification
		          addInfomation.setRoleReceiver(roleToUserName);
		          String messageNotify = MessageFormat.format(smsMessage.getProperty("S23"),
		              ((String) data.get("empFullName")), ((String) data.get("serviceId")).toUpperCase());
		          String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");
		          notification.sendNotification(userNameManager, addInfomation.toString(), messageNotify, title,
		              Constant.TYPE_NOTIFY_MODUL04, userName, Constant.WAITING_APROVE_IS_SV);

		          // build message and send Sms
		          String messageSms = MessageFormat.format(smsMessage.getProperty("S23"),
		              ((String) data.get("empFullName")), ((String) data.get("serviceId")).toUpperCase());
		          String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_APPROVE");
		          sms.sendSms(toUserid, messageSms, Constant.STATUS_NEW_SMS, Phone, typeSms);

	          }else if(requestParam.getAppoverLddv() != null){
	        	  userNameManager = requestParam.getAppoverLddv();
		          toUserid = (int) data.get("lddvId");
		          Phone = (String) data.get("lddvPhone");
		          
		          // build message and send notification
		          addInfomation.setRoleReceiver(roleToUserName);
		          String messageNotify = MessageFormat.format(smsMessage.getProperty("S23"),
		              ((String) data.get("empFullName")), ((String) data.get("serviceId")).toUpperCase());
		          String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");
		          notification.sendNotification(userNameManager, addInfomation.toString(), messageNotify, title,
		              Constant.TYPE_NOTIFY_MODUL04, userName, Constant.WAITING_APROVE_IS_SV);

		          // build message and send Sms
		          String messageSms = MessageFormat.format(smsMessage.getProperty("S23"),
		              ((String) data.get("empFullName")), ((String) data.get("serviceId")).toUpperCase());
		          String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_APPROVE");
		          sms.sendSms(toUserid, messageSms, Constant.STATUS_NEW_SMS, Phone, typeSms);
	          }else if(requestParam.getAppoverCvp() != null){
	        	  userNameManager = requestParam.getAppoverCvp();
		          toUserid = (int) data.get("cvpId");
		          Phone = (String) data.get("cvpPhone");
		          
		          // build message and send notification
		          addInfomation.setRoleReceiver(roleToUserName);
		          String messageNotify = MessageFormat.format(smsMessage.getProperty("S23"),
		              ((String) data.get("empFullName")), ((String) data.get("serviceId")).toUpperCase());
		          String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");
		          notification.sendNotification(userNameManager, addInfomation.toString(), messageNotify, title,
		              Constant.TYPE_NOTIFY_MODUL04, userName, Constant.WAITING_APROVE_IS_SV);

		          // build message and send Sms
		          String messageSms = MessageFormat.format(smsMessage.getProperty("S23"),
		              ((String) data.get("empFullName")), ((String) data.get("serviceId")).toUpperCase());
		          String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_APPROVE");
		          sms.sendSms(toUserid, messageSms, Constant.STATUS_NEW_SMS, Phone, typeSms);
	          }else {
	        	  roleToUserName = Constant.PMQT_ROLE_STAFF;
	        	  
	        	// find all receiver
					List<Map<String, Object>> mapResults = vt040000DAO.findServiceReceiver(requestParam.getServiceId());

					// for each receiver
					for (Map<String, Object> mapResult : mapResults) {

						// send for each other receiver
						// build message and send notification

						String messageNotifyRc = MessageFormat.format(smsMessage.getProperty("S26"),
								requestParam.getServiceId());
						String titleRc = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

						addInfomation.setRoleReceiver(roleToUserName);
						addInfomation.setEmpUserName(userName);
						notification.sendNotification((String) mapResult.get("reciveerName"),
								addInfomation.toString(), messageNotifyRc, titleRc,
								Constant.TYPE_NOTIFY_MODUL04, userName, Constant.WAITING_APROVE_IS_SV);

						// build message and send Sms
						String messageSmsRc = MessageFormat.format(smsMessage.getProperty("S26"),
								requestParam.getServiceId());
						String typeSmsCvp = smsMessage.getProperty("TYPE_SMS_MODUL04_APPROVE");
						sms.sendSms((int) mapResult.get("reciveerId"), messageSmsRc,
								Constant.STATUS_NEW_SMS, (String) mapResult.get("reciveerPhone"),
								typeSmsCvp);
					}
	          }
	          
	         
					//}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					logger.info("****************************THREAD NOTI ERROR********:");
				}
			}

		};

		notiThread.start();

	}

	public boolean setUpIssuesServiceSearch(IssuesServiceSearch issuesServiceSearch,
			Collection<GrantedAuthority> listRole) throws Exception {
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

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListIssuesService(IssuesServiceSearch issuesServiceSearch,
			Collection<GrantedAuthority> listRole) throws Exception {
		logger.info("**************** Start get list registration service ****************");
		IssuesService issuesService = new IssuesService();
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, issuesService);

		try {
			if(issuesServiceSearch.getListStatusDetail()!= null &&issuesServiceSearch.getListStatusDetail().length>0){
				List<String> list = Arrays.asList(issuesServiceSearch.getListStatusDetail());
				if(list.contains("0")){
					issuesServiceSearch.setIsStatusDetail(1);
				}
			}
			if(issuesServiceSearch.getListStatusSynthetic()!= null && issuesServiceSearch.getListStatusSynthetic().length>0){
				List<String> list = Arrays.asList(issuesServiceSearch.getListStatusSynthetic());
				if(list.contains("0")){
					issuesServiceSearch.setIsStatusSynthetic(1);
				}
			}
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
				issuesService.setTotalIssuesService(issuesServiceDAO.totalIssuseService(issuesServiceSearch));
				reb.setData(issuesService);
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
	public ResponseEntityBase findListIssuesServiceHistoryByIdService(IssuesServiceSearch issuesServiceSearch)
			throws Exception {
		logger.info("**************** Start get list registration service ****************");

		List<IssuesServiceEntity> listIssuesService = new ArrayList<>();
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, listIssuesService);
		try {

			listIssuesService = issuesServiceDAO.findListIssuesServiceHistoryByIdService(issuesServiceSearch);
			reb.setData(listIssuesService);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list registration service - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list registration service - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

/*	@Override
	public ResponseEntityBase insertIssuesStationery(RequestParam requestParam, String userName,
			Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// check validate input
			if (requestParam.getListStationery() == null || requestParam.getListStationery().isEmpty()
					|| requestParam.getPlaceID() <= 0) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}

			// check spending limit
			// get total price request
			List<StationeryParam> listStationery = requestParam.getListStationery();
			List<String> listStationeryID = new ArrayList<>();
			for (int i = 0; i < listStationery.size(); i++) {
				listStationeryID.add(listStationery.get(i).getStationeryId());
			}
			List<VT050000StationeryInfo> listPrice = vt050003DAO.findStationeryPrice(listStationeryID);

			double totalPrice = 0;
			for (int i = 0; i < listStationery.size(); i++) {
				totalPrice += listStationery.get(i).getQuantity() * listStationery.get(i).getUnitPrice();
//				for (int j = 0; j < listPrice.size(); j++) {
//					if (listStationery.get(i).getStationeryId().equals(listPrice.get(j).getStationeryID())) {
//						totalPrice += listStationery.get(i).getQuantity() * listPrice.get(j).getUnitPrice();
//					}
//				}
			}
			// get remaining spending limit
			double remainingSpendingLimit = vt050000Service.caculRemainingSpendingLimit(userName, roleList);
			
			// if total price request > Remaining spending limit => no insert
			if (totalPrice > remainingSpendingLimit) {
				resp.setStatus(Constant.STATIONERY_RESPONSE_SPENDING_LIMIT_EXCEED);
				return resp;
			}

			// check validate placeID
			VT050003InfoToCheckPlaceExist info = new VT050003InfoToCheckPlaceExist(requestParam.getPlaceID(),
					Constant.STATUS_ACTIVE);
			int checkPlaceID = vt050003DAO.checkPlaceExist(info);
			if (checkPlaceID != Constant.SUCCESS) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
			// insert to ISSUES_STATIONERY
			VT050000IssuesStationery issuesStationery = new VT050000IssuesStationery(null, userName,
					requestParam.getPlaceID(), requestParam.getNote(), null, Constant.ISSUES_STATIONERY_PROCESSING, 0,
					null);
			issuesStationery.setCreateUser(userName);
			vt050003DAO.insertIssuesStationery(issuesStationery);
			// get ISSUE_STATIONERY_ID in last ISSUES_STATIONERY of this user to insert to
			// ISSUES_STATIONERY_ITEMS
			String issuesStationeryID = vt050003DAO.findIssuesStationeryID(userName);
			// insert to ISSUES_STATIONERY_ITEMS
			IssuesStationeryItemsToInsert issuesStationeryItemsToInsert = new IssuesStationeryItemsToInsert();
			issuesStationeryItemsToInsert.setIssuesStationeryID(issuesStationeryID);
			issuesStationeryItemsToInsert.setStatus(Constant.STATIONERY_CREATE);
			issuesStationeryItemsToInsert.setCreateUser(userName);
			issuesStationeryItemsToInsert.setListStationery(requestParam.getListStationery());
			issuesServiceDAO.insertIssuesStationeryItems(issuesStationeryItemsToInsert);
			// set response to client
			int unitId =  stationeryReportDAO.getUnitIdByUser(userName);
			String path = stationeryReportDAO.getPathByUnitId(unitId);
			
			if(path != null || !path.contains("")){
			List<String> listUnitd = null;
			String[] parts = path.split("/");
			for(int i =0 ;i < parts.length ; i++){
				listUnitd.add(parts[i]);
				}
			VT050012DataResponseQuota responseQuota = new VT050012DataResponseQuota();
			responseQuota = stationeryReportDAO.getQuotaLimit(listUnitd);
			if(responseQuota == null){
				resp.setStatus(Constant.UNIT_NOT_EXIST_QUOTA);
				}
			}else{
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			// send sms and notify to HCDV
			}
			try {
				sendSmsAndNotify(userName, requestParam.getPlaceID(), issuesStationeryID, Constant.STATIONERY_CREATE);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}*/
	
	@Override
	public void sendSmsAndNotify(String userName, int placeID, String idRecord, int statusRecord) throws Exception {
		Thread notiThread = new Thread() {
			public void run() {
				try {
					VT050003InfoToFindHcdv info = new VT050003InfoToFindHcdv();
					info.setUserName(userName);
					info.setPlaceID(placeID);
					info.setJobCode(Constant.STATIONERY_HCDV_CODE);
					List<String> listHcdvUserName = vt050003DAO.findHcdvUserName(info);

					// find full name of employee request VPP
					String fullName = vt050000DAO.findFullNameByUserName(userName);
					for (String hcdvUserName : listHcdvUserName) {
						// send notify
						String toUserName = hcdvUserName;
						VT050000AdditionalInfoToSendNotify addInfo = new VT050000AdditionalInfoToSendNotify(idRecord,
								Constant.PMQT_ROLE_STAFF_HC_DV, Constant.PMQT_ROLE_EMPLOYYEE);
						String additionalInformation = addInfo.toString();
						String message = notifyMessage.getProperty("N35_1");
						message = MessageFormat.format(message, fullName);
						String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
						int type = Constant.TYPE_NOTIFY_MODUL05;
						String createUser = userName;
						int status = statusRecord;
						notification.sendNotification(toUserName, additionalInformation, message, title, type,
								createUser, status);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					logger.info("****************************THREAD NOTI ERROR********:");
				}
			}

		};
		notiThread.start();
	}

	@Override
	public ResponseEntityBase findListIssuesServiceHistoryForMobile(VT040002HistoryService issuesServiceSearch)
			throws Exception {
		logger.info("**************** Start get list history service mobile ****************");

		List<IssuesServiceEntity> listIssuesService = new ArrayList<>();
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, listIssuesService);
		try {

			listIssuesService = issuesServiceDAO.findListIssuesServiceHistoryForMobile(issuesServiceSearch);
			reb.setData(listIssuesService);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list registration service - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list registration service - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	public File createExcelOutputExcel(VT040002Stationery stationeryInfo, Collection<GrantedAuthority> listRole)
			throws Exception {

		File file = null;
		List<VT040002Stationery> stationeries = new ArrayList<>();
		String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_StationeryReport");

		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {
				stationeries = vt040002dao.findStationeryReport(stationeryInfo);

				file = writeExcel(stationeries, excelFilePath, stationeryInfo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return file;
	}
	public File writeExcel(List<VT040002Stationery> stationeries, String excelFilePath,
			VT040002Stationery stationeryInfo) throws IOException {

		File outFile = null;
		File file = null;

		Date date = new Date();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		String fileName = stationeryInfo.getLoginUserName() + "_" + cal.get(Calendar.YEAR) + "_"
				+ (cal.get(Calendar.MONTH) + 1) + "_" + cal.get(Calendar.DAY_OF_MONTH) + "_"
				+ cal.get(Calendar.HOUR_OF_DAY) + "h" + cal.get(Calendar.MINUTE) + "m" + cal.get(Calendar.SECOND) + "s"
				+ cal.get(Calendar.MILLISECOND) + "ms.xlsx";
		try {
			Resource resource = new ClassPathResource(excelFilePath);
			file = resource.getFile();
			String filePath = file.getAbsolutePath();
			outFile = new File(filePath.split("templateExcel")[0] + "saveExcel\\StationeryReport\\" + fileName);
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
				for (VT040002Stationery item : stationeries) {
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
	
	private void writeBook(VT040002Stationery itemStationery, Sheet sheet, int rowNum, CellStyle cellStyle,
			CellStyle combined1, CellStyle combined2) {

		Row row = sheet.createRow(rowNum);

		// No.
		Cell cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellValue((double) rowNum - 2);

		// phone number of employee
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemStationery.getStationeryName());

		// code of employee
		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemStationery.getStationeryType());

		// full name of employee
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemStationery.getStationeryUnitCal());

		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(itemStationery.getStationeryPrice());

		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		if (itemStationery.getStatus().equals("1")) {
			cell.setCellValue("Hoạt động");
		} else {
			cell.setCellValue("Không hoạt động");
		}

	}
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findMSystemCodeByCodeName(MSystemCodeEntity mSystemCodeEntity) throws Exception {

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			List<MSystemCodeEntity> SystemCodeEntity = issuesServiceDAO.findMSystemCodeByCodeName(mSystemCodeEntity);
			if (SystemCodeEntity != null) {
				reb.setData(SystemCodeEntity);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}
			logger.info("**************** End get list places - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list places - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	public List<UnitEntity> getUnitListByID(String userName, Collection<GrantedAuthority> authority, String query)
			throws Exception {
		try {
			/** Get list unit from database */
			return issuesServiceDAO.getUnitListByID(query);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
/*	@Override
	public ResponseEntityBase searchReportStationery(ReportStationery rpStationery) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
