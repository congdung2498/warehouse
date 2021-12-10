package com.viettel.vtnet360.vt04.vt040003.service;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.checking.resgister.dao.CheckingDAO;
import com.viettel.vtnet360.common.util.CalculateDate;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt02.vt020006.dao.VT020006DAO;
import com.viettel.vtnet360.vt03.vt030000.entity.Employee;
import com.viettel.vtnet360.vt04.vt040000.dao.VT040000DAO;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityAdditionalInfo;
import com.viettel.vtnet360.vt04.vt040003.dao.VT040003DAO;
import com.viettel.vtnet360.vt04.vt040003.entity.VT040003EntityRqREGService;

/**
 * Class VT040003ServiceImpl
 * 
 * @author KienHT 27/09/2018
 * 
 */
@Service
public class VT040003ServiceImpl implements VT040003Service {

	@Autowired
	VT040003DAO vt040003DAO;

	@Autowired
	Notification notification;

	@Autowired
	Sms sms;

	@Autowired
	VT000000DAO vt000000DAO;

	@Autowired
	VT040000DAO vt040000DAO;
	
	@Autowired
	private VT020006DAO vt020006DAO;

	@Autowired
	Properties notifyMessage;

	@Autowired
	Properties smsMessage;
	
	@Autowired
	private CheckingDAO checkingDao;

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * User request issues Service
	 * 
	 * @param VT040003EntityRqREGService
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase issuesService(VT040003EntityRqREGService requestParam, Principal principal)
			throws Exception {

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
			// List<Map<String, Object>> recordNotvalid = vt040003DAO.isValid(data);
			List<Map<String, Object>> validService = vt040003DAO.isServiceValid(data);
			List<Map<String, Object>> lstInvalidService = vt040003DAO.inValidIssuesService(data);
			if (validService.isEmpty()) {
				response.setStatus(Constant.RESPONSE_EXIST_SERVICE_NOT_VALID);
			} else if (lstInvalidService != null && lstInvalidService.size() > 0) {
				// KienPK - IIST check issues service not yet complete
				response.setStatus(Constant.RESPONSE_SERVICE_NOT_COMPLETE);
			} else {
				// data.put("fullFillTime", validService.get(0).get(0).);
				Map<String, Object> mapObject = validService.get(0);
				int fullFillTime = (int) mapObject.get("FULL_FILL_TIME");

				List<Date> offDays = vt020006DAO.findDayOff(Constant.STATUS_ACTIVE);
				data.put("finishTime", CalculateDate.calculateFinishTime(fullFillTime, offDays));
				data.put("startTime", CalculateDate.calculateStartTime(offDays));
				data.put("fullFillTime", fullFillTime);

				CheckStartIssuedService(data, requestParam);
				vt040003DAO.issuesService(data);
				response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				String returnId = (String) data.get("returnedId");

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

	private void trysendSmsNotify(String returnId, VT040003EntityRqREGService requestParam, String userName) {

		Thread notiThread = new Thread() {
			public void run() {
				try {

					// send sms and notify
					String userNameManager;
					int toUserid = 0;
					String phoneNumber = null;
					String roleToUserName = null;
					String message = "";

					// find info by returnId
					Map<String, Object> data = vt040000DAO.infoIssuedService(returnId);

					// set issuedServiceId into json
					VT040000EntityAdditionalInfo addInfomation = new VT040000EntityAdditionalInfo();
					addInfomation.setId(returnId);
					
					Employee employee = null;

					// Case Issued Service have Approver QLTT
					if (requestParam.getAppoverQLTT() != null) {
						userNameManager = requestParam.getAppoverQLTT();
						roleToUserName = Constant.PMQT_ROLE_MANAGER;
					} else if (requestParam.getAppoverLDDV() != null){
						userNameManager = requestParam.getAppoverLDDV();
						roleToUserName = Constant.PMQT_ROLE_MANAGER;
					} else if (requestParam.getAppoverCVP() != null){
						userNameManager = requestParam.getAppoverCVP();
						roleToUserName = Constant.PMQT_ROLE_MANAGER;
					} else {
						userNameManager = requestParam.getServiceReceiveerUsername();
						employee = checkingDao.getEmployeeByUsername(userNameManager);
						roleToUserName = Constant.PMQT_ROLE_STAFF;
					}
					
					if (data.get("qlttId") != null) {
						toUserid = (int) data.get("qlttId");
						phoneNumber = (String) data.get("qlttPhone");
						message = "S23";
					} else if (data.get("lddvId") != null){
						toUserid = (int) data.get("lddvId");
						phoneNumber = (String) data.get("lddvPhone");
						message = "S23";
					} else if (data.get("cvpId") != null) {
						toUserid = (int) data.get("cvpId");
						phoneNumber = (String) data.get("cvpPhone");
						message = "S23";
					} else {
						if (employee != null) {
							toUserid = employee.getEmployeeId();
							phoneNumber = employee.getEmployeePhone();
							message = "S26";
						}	
					}
				    //  build message and send notification
					addInfomation.setRoleReceiver(roleToUserName);
					
					String messageNotify = null;
					String messageSms = null;
					if ("S23".equals(message)) {
						messageNotify = MessageFormat.format(smsMessage.getProperty(message),
								((String) data.get("empFullName")), ((String) data.get("serviceId")).toUpperCase());

						messageSms = MessageFormat.format(smsMessage.getProperty(message),
								((String) data.get("empFullName")), ((String) data.get("serviceId")).toUpperCase());
					}
					if ("S26".equals(message)) {
						messageNotify = MessageFormat.format(smsMessage.getProperty(message), requestParam.getServiceId());
						messageSms = MessageFormat.format(smsMessage.getProperty(message), requestParam.getServiceId());
					}
					
					String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");
					String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_APPROVE");
					
					// build message and send Sms
					notification.sendNotification(userNameManager, addInfomation.toString(), messageNotify, title,
							Constant.TYPE_NOTIFY_MODUL04, userName, Constant.WAITING_APROVE_IS_SV);
					
					sms.sendSms(toUserid, messageSms, Constant.STATUS_NEW_SMS, phoneNumber, typeSms);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					logger.info("****************************THREAD NOTI ERROR********:");
				}
			}
		};

		notiThread.start();

	}
	
	private void CheckStartIssuedService(Map<String, Object> data, VT040003EntityRqREGService requestParam) {
		if (requestParam.getAppoverQLTT() == null 
				&& requestParam.getAppoverCVP() == null 
				&& requestParam.getAppoverLDDV() == null) {
			settingTime(data);
			String receiverIssues = vt040003DAO.findReceiverIssue(requestParam.getServiceId());
			requestParam.setServiceReceiveerUsername(receiverIssues);
		}
	}

	private void settingTime(Map<String, Object> data) {
		int[] ar = new int[4];

		int fullFilltime = (int) data.get("fullFillTime");
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
		data.put("soHCongThem", soHCongThem);
		data.put("soPCongThem", soPCongThem);
		data.put("timeNowToPlus", timeNowToPlus.getTime());
		System.out.println("timeNowToPlus:" + timeNowToPlus);
		data.put("timeNow", timeNow.getTime());

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

		int count = 0;
		int NowIsHoliday = TinhToanSoNgayHople(0, 0, timenow.getTime(), count);
		
		if (NowIsHoliday > 0) {
			timenow.add(Calendar.DATE, NowIsHoliday);
			timenow.set(Calendar.HOUR_OF_DAY, 8);
			timenow.set(Calendar.MINUTE, 0);
			timenow.set(Calendar.SECOND, 0);
			timenow.set(Calendar.MILLISECOND, 0);
		
		} else if (timenow.before(startWorkHour)) {

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
	
	private int TinhToanSoNgayHople(int soNgayBoQua, int soNgayXet, Date timeNow, int soLanLap) {

		int dem = sqlTimKiem(soNgayBoQua, soNgayXet, timeNow);

		if (dem == 0) {
			if (soLanLap == 0) {
				return soNgayXet;
			} else {
				return soNgayXet + 1;
			}

		} else {
			int a = TinhToanSoNgayHople(soNgayBoQua + soNgayXet + 1, dem - 1, timeNow, ++soLanLap);

			return soNgayXet + a;
		}

	}

	private int sqlTimKiem(int soNgayBoQua, int soNgayXet, Date timeNow) {

		Map<String, Object> dataPram = new HashMap<String, Object>();
		dataPram.put("soNgayBoQua", soNgayBoQua);
		dataPram.put("soNgayXet", soNgayXet);
		dataPram.put("timeNow", timeNow);

		int dem = vt040000DAO.countInvalidDay(dataPram);
		return dem;
	}
	public int timSoNgayPlus(int soNgay, Date timeNow) {
		int soLanLap = 0;
		soNgay = TinhToanSoNgayHople(0, soNgay, timeNow, soLanLap);

		return soNgay;

	}

}
