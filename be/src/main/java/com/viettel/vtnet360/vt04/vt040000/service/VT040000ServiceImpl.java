package com.viettel.vtnet360.vt04.vt040000.service;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.common.util.CalculateDate;
import com.viettel.vtnet360.issuesService.request.dao.IssuesServiceDAO;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt00.common.TokenAdditionInfo;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt02.vt020006.dao.VT020006DAO;
import com.viettel.vtnet360.vt04.vt040000.dao.VT040000DAO;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityAdditionalInfo;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityDataFindService;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityDataIssuedService;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityDataSt;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityDetailIssuedSv;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityRqApprove;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityRqFind;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityRqUpdate;

/**
 * Class interface VT040003Service
 * 
 * @author KienHT 27/09/2018
 * 
 */
@Service
public class VT040000ServiceImpl implements VT040000Service {

	@Autowired
	VT040000DAO vt040000DAO;

	@Autowired
	private AuthorizationServerTokenServices tokenServices;

	@Autowired
	Notification notification;

	@Autowired
	Sms sms;

	@Autowired
	VT000000DAO vt000000DAO;
	
	@Autowired
  private VT020006DAO vt020006DAO;

	@Autowired
	Properties notifyMessage;

	@Autowired
	Properties smsMessage;
	
	@Autowired
	IssuesServiceDAO issuesServiceDAO;

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * User find Service by request
	 * 
	 * @param VT040000EntityRqFind
	 * @param Principal
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findService(VT040000EntityRqFind requestParam, Principal principal) {

		// set reponse default
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {

			requestParam.setFromIndex(requestParam.getPageNumber() * requestParam.getPageSize());
			requestParam.setGetSize(requestParam.getPageSize());

			List<VT040000EntityDataFindService> data = vt040000DAO.findService(requestParam);
			response.setData(data);
			response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return response;
	}

	/**
	 * User find Issued Service by request
	 * 
	 * @param VT040000EntityRqFind
	 * @param Principal
	 * @param OAuth2Authentication
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findIssuedService(VT040000EntityRqFind requestParam, Principal principal,
			OAuth2Authentication authentication) {

		// take value from token
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
		dataPram.put("fromIndex", requestParam.getPageNumber() * requestParam.getPageSize());
		dataPram.put("getSize", requestParam.getPageSize());

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

		// set reponse default
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			double realTimeTotal = 0;
			List<VT040000EntityDataIssuedService> data = vt040000DAO.findIssuedService(dataPram);
			List<VT040000EntityDataIssuedService> lstDataDisplay = new ArrayList<>();
			for (VT040000EntityDataIssuedService dataIssuedService : data) {
				if (dataIssuedService.getStatus() == 1) {
					List<Date> offDays = issuesServiceDAO.findDayOff(dataIssuedService.getTimeStartPlan());
					if(dataIssuedService.getTimeStartPlan() != null) realTimeTotal = round((double) (CalculateDate.getTotalHour(dataIssuedService.getTimeStartPlan(), new Date(), offDays) * 1.0 / (60 * 60 * 1000)), 2);
					dataIssuedService.setTotalHourActual(realTimeTotal);
				} else if (dataIssuedService.getStatus() == 0 || dataIssuedService.getStatus() == 2) {
					dataIssuedService.setTotalHourActual(0);
				} else if (dataIssuedService.getStatus() == 3) {
					if (dataIssuedService.getPostponeToTime() == null
							|| dataIssuedService.getTimeResume().getTime() < dataIssuedService.getPostponeToTime().getTime()) {
						List<Date> offDays = issuesServiceDAO.findDayOff(dataIssuedService.getTimeResume());
						realTimeTotal = round((Double) (CalculateDate.getTotalHour(dataIssuedService.getTimeResume(), new Date(), offDays) * 1.0
										/ (60 * 60 * 1000)) + (dataIssuedService.getTotalHourActual() / 1000), 2);
						dataIssuedService.setTotalHourActual(realTimeTotal);
					} else {
						List<Date> offDays = issuesServiceDAO.findDayOff(dataIssuedService.getPostponeToTime());
						realTimeTotal = round((Double) (CalculateDate.getTotalHour(dataIssuedService.getPostponeToTime(), new Date(), offDays)
										* 1.0 / (60 * 60 * 1000)) + (dataIssuedService.getTotalHourActual() / 1000), 2);
						dataIssuedService.setTotalHourActual(realTimeTotal);
					}
				} else {
					dataIssuedService.setTotalHourActual(round(dataIssuedService.getTotalHourActual() / 1000, 2));
				}
			
				lstDataDisplay.add(dataIssuedService);
			}	
			
			response.setData(lstDataDisplay);
			response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return response;
	}

	/**
	 * find Detail Issued Service request
	 * 
	 * @param VT040000EntityRqFind
	 * @param Principal
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase finDatailService(VT040000EntityRqFind requestParam, Principal principal) {

		// set reponse default
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			
			double realTimeTotal;
			// find info Detail Issued Service
			VT040000EntityDetailIssuedSv data = vt040000DAO.finDatailService(requestParam);
			if(data.getStatus() == 1) {
				List<Date> offDays = issuesServiceDAO.findDayOff(data.getTimeStartPlan());
				realTimeTotal = round((double)(CalculateDate.getTotalHour(data.getTimeStartPlan() , new Date(), offDays) * 1.0/(60 * 60 * 1000)), 2);
				data.setRealTimeTotal(realTimeTotal);
			} else if (data.getStatus() == 0) {
				data.setRealTimeTotal(0);
			}else if(data.getStatus() == 3) {
				
				if(data.getPostponeToTime() == null||data.getTimeResume().getTime()<data.getPostponeToTime().getTime()){
					List<Date> offDays = issuesServiceDAO.findDayOff(data.getTimeResume());
					realTimeTotal = round((double)(CalculateDate.getTotalHour(data.getTimeResume() , new Date(), offDays) * 1.0/(60 * 60 * 1000))+(data.getTotalHourActual()/1000), 2);
					data.setRealTimeTotal(realTimeTotal);
				}else{
					List<Date> offDays = issuesServiceDAO.findDayOff(data.getPostponeToTime());
					realTimeTotal = round((double)(CalculateDate.getTotalHour(data.getPostponeToTime() , new Date(), offDays) * 1.0/(60 * 60 * 1000))+(data.getTotalHourActual()/1000), 2);
					data.setRealTimeTotal(realTimeTotal);
				}
			}else{
				data.setRealTimeTotal(round(data.getTotalHourActual()/1000, 2));
			}
			if(data.getReason()!= null){
				String [] arrReason=data.getReason().split("]", 3);
				data.setReason(arrReason[arrReason.length-1]);
			}
			
			requestParam.setMasterCode(Constant.M_SYSTEM_CODE_CALCULATION);
			// find stationery Detail for Issued Service
			VT040000EntityDataSt[] st = vt040000DAO.finStDatailService(requestParam);
			data.setStatonery(st);

			// set reponese
			response.setData(data);
			response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return response;
	}

	/**
	 * approve Issued Service
	 * 
	 * @param VT040000EntityRqFind
	 * @param Principal
	 * @param OAuth2Authentication
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase approveIssuedService(VT040000EntityRqApprove requestParam, Principal principal,
			OAuth2Authentication authentication) throws Exception {

		// take value from token
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		Boolean isAdmin = false;

		// get info from authentication
		Map<String, Object> additionalInfo = tokenServices.getAccessToken(authentication).getAdditionalInformation();
		TokenAdditionInfo tokenInfo = (TokenAdditionInfo) additionalInfo.get("userInfor");
		String myFullName = tokenInfo.getFullName();

		// timeNow
		Date timeNow = new Date(System.currentTimeMillis());

		// check role user admin
		Collection<GrantedAuthority> arrayRoleToken = (Collection<GrantedAuthority>) oauth.getAuthorities();
		for (GrantedAuthority temp : arrayRoleToken) {
			// if user had role PMQT_ROLE_ADMIN
			if (Constant.PMQT_ROLE_ADMIN.equalsIgnoreCase(temp.getAuthority())) {
				isAdmin = true;
				logger.info("APROVED BY ADMINNNNNNNN:");
				break;
			}
		}

		// set reponse default
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		// try appove for each Issued Service
		try {
			Map<String, Object> data;
			for (String onceIdIssuedSV : requestParam.getIssuedServiceId()) {

				// have fullFillTime and v.v into info object after executive
				data = vt040000DAO.infoIssuedService(onceIdIssuedSV);

				if ((int) data.get("status") != Constant.IN_OUT_REGISTER_WATING_APPROVED) {

					if (requestParam.getIssuedServiceId().length == 1) {
						logger.error("MODUL 4: approve once record have status don't valid ");
						response.setStatus(Constant.RESPONSE_ISSUED_SERVICE_CHANGED_STATUS);
						return response;
					} else {
						continue;
					}

				} else {

					// put param
					data.put("IdIssuedSV", onceIdIssuedSV);
					data.put("flag", requestParam.getFlagAppove());
					data.put("userName", userName);
					data.put("timeNow", timeNow);
					data.put("resonRefuse", requestParam.getResonRefuse());
					data.put("isAdmin", isAdmin);
					
					List<Date> offDays = vt020006DAO.findDayOff(Constant.STATUS_ACTIVE);
					data.put("finishDate", CalculateDate.calculateFinishTime((Integer) data.get("fullFillTime"), offDays));
					data.put("startDate", CalculateDate.calculateStartTime(offDays));
					
					CheckStartIssuedService(data, requestParam);
					// approve Issued Service
					
					vt040000DAO.approveIssuedService(data);

					logger.info("flagQltt:" + (Boolean) data.get("flagQltt") + "|| flagLddv:"
							+ (Boolean) data.get("flagLddv") + "|| flagCvp:" + (Boolean) data.get("flagCvp") + "||");

					Boolean flagQltt = (Boolean) data.get("flagQltt");
					Boolean flagLddv = (Boolean) data.get("flagLddv");
					Boolean flagCvp = (Boolean) data.get("flagCvp");

					String serviceName = (String) data.get("serviceName");

					String cvpUserName = null;
					int cvpId = 0;
					String cvpPhone = null;
					String cvpFullName = null;

					if ((String) data.get("cvpUserName") != null) {
						cvpUserName = (String) data.get("cvpUserName");
						cvpId = (int) data.get("cvpId");
						cvpPhone = (String) data.get("cvpPhone");
						cvpFullName = (String) data.get("cvpFullName");
					}

					String lddvUserName = null;
					int lddvId = 0;
					String lddvPhone = null;
					String lddvFullName = null;

					if ((String) data.get("lddvUserName") != null) {
						lddvUserName = (String) data.get("lddvUserName");
						lddvId = (int) data.get("lddvId");
						lddvPhone = (String) data.get("lddvPhone");
						lddvFullName = (String) data.get("lddvFullName");
					}

					String serviceId = (String) data.get("serviceId");
					String empUserName = (String) data.get("empUserName");
					String empFullName = (String) data.get("empFullName");

					String qlttFullName = (String) data.get("qlttFullName");

					int empId = (int) data.get("empId");
					String empPhone = (String) data.get("empPhone");
					int statusIssuedService = (int) data.get("status");

					// try send Notify and Sms

					sendNotify(flagQltt, flagLddv, flagCvp, serviceName, cvpUserName, cvpId, cvpPhone, lddvUserName,
							lddvId, lddvPhone, serviceId, empUserName, empId, empPhone, userName, requestParam,
							onceIdIssuedSV, lddvFullName, cvpFullName, empFullName, qlttFullName, statusIssuedService,
							isAdmin, myFullName);

					// end for each appove for each Issued Service
				}
			}

			// set reponse code
			response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			// end try appove for each Issued Service
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return response;
	}

	private void CheckStartIssuedService(Map<String, Object> data, VT040000EntityRqApprove requestParam) {
		Boolean flagQltt = (Boolean) data.get("flagQltt");
		Boolean flagLddv = (Boolean) data.get("flagLddv");
		Boolean flagCvp = (Boolean) data.get("flagCvp");

		int statusIssuedService = (int) data.get("status");

		if (statusIssuedService == 0) {
			if (requestParam.getFlagAppove() == 1) {
				if (flagQltt == false && flagLddv == true && flagCvp == true) {
					settingTime(data);
				} else if (flagQltt == true && flagLddv == false && flagCvp == true) {
					settingTime(data);
				} else if (flagQltt == true && flagLddv == false && flagCvp == false) {
					settingTime(data);
				}
			}
		}

	}

		
	
	private void settingTime(Map<String, Object> data) {
		int[] ar = new int[4];

		//int fullFilltime = (int) data.get("fullFillTime");
		int fullFilltime = 12;
		System.out.println("FULL FILL TIME:" + fullFilltime);

		Calendar timeNow = Calendar.getInstance();

		PhanTichSoH(ar, fullFilltime, timeNow);
		int soNgay = ar[0];

		int soNCongThem;
		System.out.println("==========xxxx==========.=============================");
		soNCongThem = timSoNgayPlus(soNgay, timeNow.getTime());
		System.out.println("HAHA:" + soNCongThem);
		System.out.println("===========================.=============================");
		int soHCongThem = ar[1];
		int soPCongThem = ar[2];
		Calendar timeNowToPlus = Calendar.getInstance();
		timeNowToPlus.setTime(timeNow.getTime());

		if (soNCongThem > 0 && ar[3] == 0) {
			timeNowToPlus.set(Calendar.HOUR_OF_DAY, 8);
			timeNowToPlus.set(Calendar.MINUTE, 0);
			timeNowToPlus.set(Calendar.SECOND, 0);
			timeNowToPlus.set(Calendar.MILLISECOND, 0);
		}

		data.put("soNCongThem", soNCongThem);
		System.out.print("soNCongThem : " + soNCongThem);
		data.put("soHCongThem", soHCongThem);
		
		System.out.print("soHCongThem : " + soHCongThem);
		
		data.put("soPCongThem", soPCongThem);
		System.out.print("soPCongThem : " + soPCongThem);
		data.put("timeNowToPlus", timeNowToPlus.getTime());
		
		System.out.println("timeNowToPlus:" + timeNowToPlus);
		
		data.put("timeNow", timeNow.getTime());

	}

	public int timSoNgayPlus(int soNgay, Date timeNow) {
		int soLanLap = 0;
		soNgay = TinhToanSoNgayHople(0, soNgay, timeNow, soLanLap);
		
		System.out.print("soNgay : " + soNgay); 

		return soNgay;

	}

	private int TinhToanSoNgayHople(int soNgayBoQua, int soNgayXet, Date timeNow, int soLanLap) {

		if (soLanLap == 0) {
			return soNgayXet;
		} else {
			return soNgayXet + 1;
		}
	}

	private int sqlTimKiem(int soNgayBoQua, int soNgayXet, Date timeNow) {

		Map<String, Object> dataPram = new HashMap<String, Object>();
		dataPram.put("soNgayBoQua", soNgayBoQua);
		dataPram.put("soNgayXet", soNgayXet);
		dataPram.put("timeNow", timeNow);

		int dem = 0;
		return dem;
	}

	private void PhanTichSoH(int[] ar, int h, Calendar timenow) {

		int soNDapung = 0;
		int soNcongthem = 0;
		int soHDapUng = 0;

		soNDapung = h / 8;
		soHDapUng = h % 8;

		tinhRasoHvsP(ar, timenow);
		System.out.println("thoi gian con lai Trong Ngay:");
		System.out.println(ar[1]);
		System.out.println(ar[2]);
		
		int soHconLaiTrongNgay = ar[1];
		int soPconLaiTrongNgay = ar[2];

		int soPcongThem = 0;
		int soHcongThem = 0;

		if (soHDapUng > soHconLaiTrongNgay) {
			soNDapung = soNDapung + 1;

			if (soPconLaiTrongNgay > 0) {
				soPcongThem = 60 - soPconLaiTrongNgay;
				soHDapUng = soHDapUng - 1;
			}

			soHDapUng = soHDapUng - soHconLaiTrongNgay;
		}

		System.out.println("Thoi gian Phai Cong:");
		soNcongthem = soNDapung;
		soHcongThem = soHDapUng;

		System.out.println(soNcongthem);
		System.out.println(soHcongThem);
		System.out.println(soPcongThem);
		ar[0] = soNcongthem;
		ar[1] = soHcongThem;
		ar[2] = soPcongThem;
		if(h % 8==0) {
			ar[3] = 1;
		}else {
			ar[3] = 0;
		}

	}

	private void tinhRasoHvsP(int[] ar, Calendar timenow) {

		Calendar startWorkHour = Calendar.getInstance();
		startWorkHour.set(Calendar.HOUR_OF_DAY, 8);
		startWorkHour.set(Calendar.MINUTE, 0);
		startWorkHour.set(Calendar.SECOND, 0);
		startWorkHour.set(Calendar.MILLISECOND, 0);

		Calendar starBreakkHour = Calendar.getInstance();
		starBreakkHour.set(Calendar.HOUR_OF_DAY, 12);
		starBreakkHour.set(Calendar.MINUTE, 0);
		starBreakkHour.set(Calendar.SECOND, 0);
		starBreakkHour.set(Calendar.MILLISECOND, 0);

		Calendar endBreakkHour = Calendar.getInstance();
		endBreakkHour.set(Calendar.HOUR_OF_DAY, 13);
		endBreakkHour.set(Calendar.MINUTE, 30);
		endBreakkHour.set(Calendar.SECOND, 0);
		endBreakkHour.set(Calendar.MILLISECOND, 0);

		Calendar endWorkHour = Calendar.getInstance();
		endWorkHour.set(Calendar.HOUR_OF_DAY, 17);
		endWorkHour.set(Calendar.MINUTE, 30);
		endWorkHour.set(Calendar.SECOND, 0);
		endWorkHour.set(Calendar.MILLISECOND, 0);

		// set time come on around (8h-12h or 13h30-17h30)
		timenow = validTimeNow(timenow, startWorkHour, starBreakkHour, endBreakkHour, endWorkHour);
		timenow.set(Calendar.SECOND, 0);
		timenow.set(Calendar.MILLISECOND, 0);

		// setting agian while timenow + day
		endWorkHour.setTime(timenow.getTime());
		endWorkHour.set(Calendar.HOUR_OF_DAY, 17);
		endWorkHour.set(Calendar.MINUTE, 30);
		endWorkHour.set(Calendar.SECOND, 0);
		endWorkHour.set(Calendar.MILLISECOND, 0);

		startWorkHour.setTime(timenow.getTime());
		startWorkHour.set(Calendar.HOUR_OF_DAY, 8);
		startWorkHour.set(Calendar.MINUTE, 0);
		startWorkHour.set(Calendar.SECOND, 0);
		startWorkHour.set(Calendar.MILLISECOND, 0);

		starBreakkHour.setTime(timenow.getTime());
		starBreakkHour.set(Calendar.HOUR_OF_DAY, 12);
		starBreakkHour.set(Calendar.MINUTE, 0);
		starBreakkHour.set(Calendar.SECOND, 0);
		starBreakkHour.set(Calendar.MILLISECOND, 0);

		// if 8h-12 then skip 1h30' (endWorkHour 17h30 => 16h00)
		if (timenow.after(startWorkHour) && timenow.before(starBreakkHour)) {
			endWorkHour.add(Calendar.HOUR_OF_DAY, -1);
			endWorkHour.add(Calendar.MINUTE, -30);
		}

		// endWorkHour
//		System.out.println("endWorkHour:" + endWorkHour.getTime());
//		System.out.println("Time Now:" + timenow.getTime());

		long remain = endWorkHour.getTimeInMillis() - timenow.getTimeInMillis();

		Calendar timeRemain = Calendar.getInstance();
		timeRemain.setTimeInMillis(remain);
		// System.out.println(timeRemain.getTime());

		int soHConlai = timeRemain.get(Calendar.HOUR_OF_DAY) - 7;
		int soPConLai = timeRemain.get(Calendar.MINUTE);

		ar[1] = soHConlai;
		ar[2] = soPConLai;

	}

	private Calendar validTimeNow(Calendar timenow, Calendar startWorkHour, Calendar starBreakkHour,
			Calendar endBreakkHour, Calendar endWorkHour) {

//		int count = 0;
//		int NowIsHoliday = 0 ;
//		NowIsHoliday = TinhToanSoNgayHople(0, 0, timenow.getTime(), count);
		
//		if (NowIsHoliday > 0) {
//			timenow.add(Calendar.DATE, NowIsHoliday);
//			timenow.set(Calendar.HOUR_OF_DAY, 8);
//			timenow.set(Calendar.MINUTE, 0);
//			timenow.set(Calendar.SECOND, 0);
//			timenow.set(Calendar.MILLISECOND, 0);
//
//		} else
			if (timenow.before(startWorkHour)) {

			timenow.set(Calendar.HOUR_OF_DAY, 8);
			timenow.set(Calendar.MINUTE, 0);
			timenow.set(Calendar.SECOND, 0);
			timenow.set(Calendar.MILLISECOND, 0);

		} else if (timenow.after(starBreakkHour) && timenow.before(endBreakkHour)) {

			timenow.set(Calendar.HOUR_OF_DAY, 13);
			timenow.set(Calendar.MINUTE, 30);
			timenow.set(Calendar.SECOND, 0);
			timenow.set(Calendar.MILLISECOND, 0);

		} else if (timenow.after(endWorkHour)) {
			int soLanLap = 0;
			int TomorrowIsholiday = TinhToanSoNgayHople(1, 0, timenow.getTime(), soLanLap);
			timenow.add(Calendar.DATE, TomorrowIsholiday);
			timenow.set(Calendar.HOUR_OF_DAY, 8);
			timenow.set(Calendar.MINUTE, 0);
			timenow.set(Calendar.SECOND, 0);
			timenow.set(Calendar.MILLISECOND, 0);

		}
		return timenow;

	}

	// sendnotify
	private void sendNotify(Boolean flagQltt, Boolean flagLddv, Boolean flagCvp, String serviceName, String cvpUserName,
			int cvpId, String cvpPhone, String lddvUserName, int lddvId, String lddvPhone, String serviceId,
			String empUserName, int empId, String empPhone, String userName, VT040000EntityRqApprove requestParam,
			String onceIdIssuedSV, String lddvFullName, String cvpFullName, String empFullName, String qlttFullName,
			int statusIssuedService, Boolean isAdmin, String myFullName) {

		Thread notiThread = new Thread() {
			public void run() {
				try {

					//
					VT040000EntityAdditionalInfo addInfomation = new VT040000EntityAdditionalInfo();
					addInfomation.setId(onceIdIssuedSV);

					String nameUserApprove = "";

					String roleToUserName;
					int statusIdIssuedSV = Constant.WAITING_APROVE_IS_SV;

					if (statusIssuedService == 0) {

						// if flag action is approve
						if (requestParam.getFlagAppove() == 1) {

							// QLTT Aprove
							if (flagQltt == false) {
								nameUserApprove = qlttFullName;

								if (flagLddv == false) {
									// send noti sms for LDDV

									// set title and status
									roleToUserName = Constant.PMQT_ROLE_MANAGER;

									// send for LDDV
									// build message and send notification
									// addInfomation.put("roleToUserName", roleToUserName);
									addInfomation.setRoleReceiver(roleToUserName);
									String messageNotifyLddv = MessageFormat.format(smsMessage.getProperty("S25"),
											empFullName, serviceId.toUpperCase());
									String titleLddv = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");
									notification.sendNotification(lddvUserName, addInfomation.toString(),
											messageNotifyLddv, titleLddv, Constant.TYPE_NOTIFY_MODUL04, userName,
											statusIdIssuedSV);

									// build message and send Sms
									String messageSmsLddv = MessageFormat.format(smsMessage.getProperty("S25"),
											empFullName, serviceId.toUpperCase());

									String typeSmsLddv = smsMessage.getProperty("TYPE_SMS_MODUL04_APPROVE");
									sms.sendSms(lddvId, messageSmsLddv, Constant.STATUS_NEW_SMS, lddvPhone,
											typeSmsLddv);

								} else if (flagCvp == false) {
									// send noti sms for Cvp

									// set title and status
									roleToUserName = Constant.PMQT_ROLE_MANAGER;

									// send for CVP
									// build message and send notification
									// roleToUserName = Constant.PMQT_ROLE_STAFF;
									addInfomation.setRoleReceiver(roleToUserName);
									String messageNotifyCvp = MessageFormat.format(smsMessage.getProperty("S25"),
											empFullName, serviceId.toUpperCase());
									String titleCvp = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");
									notification.sendNotification(cvpUserName, addInfomation.toString(),
											messageNotifyCvp, titleCvp, Constant.TYPE_NOTIFY_MODUL04, userName,
											statusIdIssuedSV);

									// build message and send Sms
									String messageSmsCvp = MessageFormat.format(smsMessage.getProperty("S25"),
											empFullName, serviceId.toUpperCase());

									String typeSmsCvp = smsMessage.getProperty("TYPE_SMS_MODUL04_APPROVE");
									sms.sendSms(cvpId, messageSmsCvp, Constant.STATUS_NEW_SMS, cvpPhone, typeSmsCvp);

								} else if (flagLddv == true && flagCvp == true) {

									// send for all emp excutive
									roleToUserName = Constant.PMQT_ROLE_STAFF;

									// find all receiver
									List<Map<String, Object>> mapResults = vt040000DAO.findServiceReceiver(serviceId);

									// for each receiver
									for (Map<String, Object> mapResult : mapResults) {

										// send for each other receiver
										// build message and send notification

										String messageNotifyRc = MessageFormat.format(smsMessage.getProperty("S26"),
												serviceId);
										String titleRc = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

										addInfomation.setRoleReceiver(roleToUserName);
										addInfomation.setEmpUserName(empUserName);
										notification.sendNotification((String) mapResult.get("reciveerName"),
												addInfomation.toString(), messageNotifyRc, titleRc,
												Constant.TYPE_NOTIFY_MODUL04, userName, statusIdIssuedSV);

										// build message and send Sms
										String messageSmsRc = MessageFormat.format(smsMessage.getProperty("S26"),
												serviceId);
										String typeSmsCvp = smsMessage.getProperty("TYPE_SMS_MODUL04_APPROVE");
										sms.sendSms((int) mapResult.get("reciveerId"), messageSmsRc,
												Constant.STATUS_NEW_SMS, (String) mapResult.get("reciveerPhone"),
												typeSmsCvp);
									}

								}

								// LDDV Aprove
							} else if (flagLddv == false) {
								nameUserApprove = lddvFullName;

								if (flagCvp == false && !lddvUserName.equals(cvpUserName)) {
									// send noti sms for Cvp
									roleToUserName = Constant.PMQT_ROLE_MANAGER;

									// send for CVP
									// build message and send notification
									// roleToUserName = Constant.PMQT_ROLE_STAFF;
									addInfomation.setRoleReceiver(roleToUserName);
									String messageNotifyCvp = MessageFormat.format(smsMessage.getProperty("S25"),empFullName,
											serviceId.toUpperCase());
									String titleCvp = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");
									notification.sendNotification(cvpUserName, addInfomation.toString(),
											messageNotifyCvp, titleCvp, Constant.TYPE_NOTIFY_MODUL04, userName,
											statusIdIssuedSV);

									// build message and send Smsk
									String messageSmsCvp = MessageFormat.format(smsMessage.getProperty("S25"),empFullName,
											serviceId.toUpperCase());
									String typeSmsCvp = smsMessage.getProperty("TYPE_SMS_MODUL04_APPROVE");
									sms.sendSms(cvpId, messageSmsCvp, Constant.STATUS_NEW_SMS, cvpPhone, typeSmsCvp);

								} else if (flagCvp == true || lddvUserName.equals(cvpUserName)) {

									// send for all emp excutive
									roleToUserName = Constant.PMQT_ROLE_STAFF;

									// find all receiver
									List<Map<String, Object>> mapResults = vt040000DAO.findServiceReceiver(serviceId);

									// for each receiver
									for (Map<String, Object> mapResult : mapResults) {

										// send for each other receiver
										// build message and send notification

										String messageNotifyRc = MessageFormat.format(smsMessage.getProperty("S26"),
												serviceId);
										String titleRc = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

										addInfomation.setRoleReceiver(roleToUserName);
										addInfomation.setEmpUserName(empUserName);
										notification.sendNotification((String) mapResult.get("reciveerName"),
												addInfomation.toString(), messageNotifyRc, titleRc,
												Constant.TYPE_NOTIFY_MODUL04, userName, statusIdIssuedSV);

										// build message and send Sms
										String messageSmsRc = MessageFormat.format(smsMessage.getProperty("S26"),
												serviceId);
										String typeSmsCvp = smsMessage.getProperty("TYPE_SMS_MODUL04_APPROVE");
										sms.sendSms((int) mapResult.get("reciveerId"), messageSmsRc,
												Constant.STATUS_NEW_SMS, (String) mapResult.get("reciveerPhone"),
												typeSmsCvp);
									}

								}

							} else if (flagCvp == false) {
								nameUserApprove = cvpFullName;

								// send for all emp excutive
								roleToUserName = Constant.PMQT_ROLE_STAFF;

								// find all receiver
								List<Map<String, Object>> mapResults = vt040000DAO.findServiceReceiver(serviceId);

								// for each receiver
								for (Map<String, Object> mapResult : mapResults) {

									// send for each other receiver
									// build message and send notification

									String messageNotifyRc = MessageFormat.format(smsMessage.getProperty("S26"),
											serviceId);
									String titleRc = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

									addInfomation.setRoleReceiver(roleToUserName);
									addInfomation.setEmpUserName(empUserName);
									notification.sendNotification((String) mapResult.get("reciveerName"),
											addInfomation.toString(), messageNotifyRc, titleRc,
											Constant.TYPE_NOTIFY_MODUL04, userName, statusIdIssuedSV);

									// build message and send Sms
									String messageSmsRc = MessageFormat.format(smsMessage.getProperty("S26"),
											serviceId);
									String typeSmsCvp = smsMessage.getProperty("TYPE_SMS_MODUL04_APPROVE");
									sms.sendSms((int) mapResult.get("reciveerId"), messageSmsRc,
											Constant.STATUS_NEW_SMS, (String) mapResult.get("reciveerPhone"),
											typeSmsCvp);
								}

							}

						} else {

							if (flagQltt == false) {

								nameUserApprove = qlttFullName;

							} else if (flagLddv == false) {

								nameUserApprove = lddvFullName;
							} else if (flagCvp == false) {

								nameUserApprove = cvpFullName;
							}
						}

						// send for all emp request
						// sms and notify for Emp
						String messageNotifyEmp = "";
						String titleEmp = "";
						String messageSmsEmp = "";
						String typeSmsEmp = "";
						String roleEmp = Constant.PMQT_ROLE_EMPLOYYEE;
						nameUserApprove = myFullName;

						// Case getStatus = ACCEPT
						if (requestParam.getFlagAppove() == 1) {

							// build message notification
							messageNotifyEmp = MessageFormat.format(smsMessage.getProperty("S24"), nameUserApprove,
									serviceId.toUpperCase());
							titleEmp = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

							// build message Sms
							messageSmsEmp = MessageFormat.format(smsMessage.getProperty("S24"), nameUserApprove,
									serviceId.toUpperCase());
							typeSmsEmp = smsMessage.getProperty("TYPE_SMS_MODUL04_APPROVE");

							// Case getStatus = refuse
						} else if (requestParam.getFlagAppove() == 2) {

							// build message notification
							messageNotifyEmp = MessageFormat.format(smsMessage.getProperty("S27"), nameUserApprove,
									serviceId.toUpperCase());
							titleEmp = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

							// build message Sms
							messageSmsEmp = MessageFormat.format(smsMessage.getProperty("S27"), nameUserApprove,
									serviceId.toUpperCase());
							typeSmsEmp = smsMessage.getProperty("TYPE_SMS_MODUL04_APPROVE");
						}

						// send notification and Sms
//						addInfomation.put("roleToUserName", roleEmp);
						addInfomation.setRoleReceiver(roleEmp);
						notification.sendNotification(empUserName, addInfomation.toString(), messageNotifyEmp, titleEmp,
								Constant.TYPE_NOTIFY_MODUL04, userName, statusIdIssuedSV);
						sms.sendSms(empId, messageSmsEmp, Constant.STATUS_NEW_SMS, empPhone, typeSmsEmp);

					}

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					logger.info("****************************THREAD NOTI ERROR********:");

				}
			}

		};

		notiThread.start();

	}

	/**
	 * update for issued Service
	 * 
	 * @param VT040000EntityRqUpdate
	 * @param Principal
	 * @param OAuth2Authentication
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase updateIssuedService(VT040000EntityRqUpdate requestParam, Principal principal,
			OAuth2Authentication authentication) {

		// take value from token
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();

		// timeNow
		Date timeNow = new Date(System.currentTimeMillis());

		// data info
		Map<String, Object> dataInfo = vt040000DAO.infoIssuedService(requestParam.getIssuedServiceId());
		int statusIssuedService = (int) dataInfo.get("status");

		// create Map Object cotain param
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("userName", userName);
		data.put("timeNow", timeNow);
		data.put("requestParam", requestParam);

		// set reponse default
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		if (checkStatusBeforeInvalid(statusIssuedService, requestParam.getFlag())) {
			response.setStatus(Constant.RESPONSE_ISSUED_SERVICE_CHANGED_STATUS);
			return response;
		}

		try {
			vt040000DAO.updateIssuedService(data);
			response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return response;
	}

	private Boolean checkStatusBeforeInvalid(int statusIssuedService, int flag) {
		if (flag == Constant.ISSUED_SERVICE_CANCEL

				&& (statusIssuedService == Constant.ISSUED_SERVICE_STATUS_EXECUTING
						|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_PENDING_EXECUTE
						|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_COMPLETE
						|| statusIssuedService == Constant.ISSUED_SERVICE_CANCEL
						|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_RECEIVE
						|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_REJECT
						|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_REJECT_APROVED)

		) {
			return true;
		}
		return false;

	}
	
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
