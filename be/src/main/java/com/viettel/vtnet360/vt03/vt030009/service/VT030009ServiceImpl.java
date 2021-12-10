package com.viettel.vtnet360.vt03.vt030009.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntitySmsVsNotify;
import com.viettel.vtnet360.vt03.Common.DateUtil;
import com.viettel.vtnet360.vt03.vt030000.dao.VT030000DAO;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000NotifySmsInfo;
import com.viettel.vtnet360.vt03.vt030007.entity.VT030007InforDetailMergeCar;
import com.viettel.vtnet360.vt03.vt030008.dao.VT030008DAO;
import com.viettel.vtnet360.vt03.vt030009.dao.VT030009DAO;
import com.viettel.vtnet360.vt03.vt030009.entity.VT030009RequestGetUser;
import com.viettel.vtnet360.vt03.vt030009.entity.VT030009ResponseCarRoute;
import com.viettel.vtnet360.vt03.vt030009.entity.VT030009UpdateProcessCarRoute;
import com.viettel.vtnet360.vt03.vt030009.entity.VT030009UserInfor;

/**
 * Class VT030009ServiceImpl
 * 
 * @author CuongHD
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030009ServiceImpl implements VT030009Service {

	@Autowired
	private VT030009DAO vt030009dao;

	@Autowired
	Notification notification;

	@Autowired
	Sms sms;

	@Autowired
	VT000000DAO vt000000DAO;

	@Autowired
	VT030000DAO vt030000dao;

	@Autowired
	Properties notifyMessage;

	@Autowired
	Properties smsMessage;
	
	@Autowired
	VT030008DAO vt030008dao;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * Update process for car's route
	 * 
	 * @param VT030009UpdateProcessCarRoute obj
	 * @param Collection<GrantedAuthority> listRole
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	@Override
	@Transactional
	public ResponseEntityBase updateCarRoute(VT030009UpdateProcessCarRoute obj, Collection<GrantedAuthority> listRole)
			throws Exception {
		logger.info("********************* START EXECUTE APIVT030009_01 ***********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT030009UpdateProcessCarRoute> listUpdateProcessCarRoute = null;
		try {
			if (listRole.isEmpty()) {
				return response;
			}
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_DOIXE))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_EMPLOYEE_DOIXE))) {
				// Update PROCESS_STATUS in DRIVE_CAR
				VT030009ResponseCarRoute vt030009ResponseCarRoute = vt030009dao.findCarRoute(obj);
				Integer carTypeBooking = vt030009ResponseCarRoute.getCarBookingType();
				if(carTypeBooking != null && Constant.CAR_BOOKING_TYPE_DEFAULT == carTypeBooking) {
					listUpdateProcessCarRoute = vt030009dao.getInforUpdateProcessCarRoute(vt030009ResponseCarRoute);
				}
				vt030009dao.updateStateDriveCar(obj); 
				vt030009dao.updateStateCar(obj);
				vt030009dao.updateStateDriver(obj);
				int status = vt030008dao.getStatus(obj.getBookCarId());
				if(status == obj.getStatus()) {
					return response;
				}
				// Update process for car's route
				int rowEffect = 0;
				if(listUpdateProcessCarRoute != null && carTypeBooking != null && Constant.CAR_BOOKING_TYPE_DEFAULT == carTypeBooking) {
					for (VT030009UpdateProcessCarRoute vt030009UpdateProcessCarRoute : listUpdateProcessCarRoute) {
						vt030009UpdateProcessCarRoute.setStatus(obj.getStatus());
						vt030009UpdateProcessCarRoute.setTimeReadyStart(obj.getTimeReadyStart());
						vt030009UpdateProcessCarRoute.setTimeAtTarget(obj.getTimeAtTarget());
						vt030009UpdateProcessCarRoute.setTimeReadyFinish(obj.getTimeReadyFinish());
						rowEffect = vt030009dao.updateCarRoute(vt030009UpdateProcessCarRoute);
					}
				}else {
					rowEffect = vt030009dao.updateCarRoute(obj);
				}
				if (rowEffect == 0) {
					return response;
				}
				Thread notiThread = new Thread() {
					public void run() {
						// tim kiem thong tin chuyen xe
						VT030000NotifySmsInfo info = new VT030000NotifySmsInfo(Constant.MASTER_CLASS_TYPE_CAR,
								Constant.MASTER_CLASS_SEAT_CAR, Constant.MASTER_CLASS_ROUTE_CAR, obj.getBookCarId());
						VT030000NotifySmsInfo infoCarRoute = vt030000dao.findInfoNotifySms(info);
						// tim kiem username cua truong ban xe
						String userName = vt030009dao.findManager(obj.getUserDriver());
						// find info of manager
						VT000000EntitySmsVsNotify inforNotifySmsManager = vt000000DAO.findNotifySmsByUserName(userName);
						inforNotifySmsManager.setToUserName(userName);
						// when car start running
						if (Constant.STATUS_ROUTE_CAR_START == obj.getStatus()) {

							// send notify to manager of driver
							String messageNotify = MessageFormat.format(notifyMessage.getProperty("N19"), infoCarRoute.getFullName(), infoCarRoute.getLicensePlate().toUpperCase(),
									 infoCarRoute.getStartPlace().toUpperCase(),
									infoCarRoute.getTargetPlace().toUpperCase(),  new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
									new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase());
							String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
							AdditionalInfoBase addInfo = new AdditionalInfoBase();
							addInfo.setId(obj.getBookCarId());
							addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_DOIXE);
							notification.sendNotification(inforNotifySmsManager.getToUserName(), addInfo.toString(),
									messageNotify, title, Constant.TYPE_NOTIFY_MODUL03, obj.getUserDriver(),
									Constant.STATUS_ROUTE_CAR_START);

							// send SMS to manager of driver
							String messageSms = MessageFormat.format(smsMessage.getProperty("S19"), infoCarRoute.getFullName(), infoCarRoute.getLicensePlate().toUpperCase(),
									 infoCarRoute.getStartPlace().toUpperCase(),
									infoCarRoute.getTargetPlace().toUpperCase(), new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
									new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase());
							String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
							sms.sendSms(inforNotifySmsManager.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
									inforNotifySmsManager.getPhone(), typeSms);

						} else if (Constant.STATUS_ROUTE_CAR_COME_BACK == obj.getStatus()) {
							// send notify to manager of driver
							String messageNotify = MessageFormat.format(notifyMessage.getProperty("N21"),infoCarRoute.getFullName(), infoCarRoute.getLicensePlate().toUpperCase(),
									 infoCarRoute.getStartPlace().toUpperCase(),
									infoCarRoute.getTargetPlace().toUpperCase(),  new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
									new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase());
							String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
							AdditionalInfoBase addInfo = new AdditionalInfoBase();
							addInfo.setId(obj.getBookCarId());
							addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_DOIXE);
							notification.sendNotification(inforNotifySmsManager.getToUserName(), addInfo.toString(),
									messageNotify, title, Constant.TYPE_NOTIFY_MODUL03, obj.getUserDriver(),
									Constant.STATUS_ROUTE_CAR_COME_BACK);

							// send SMS to manager of driver
							String messageSms = MessageFormat.format(smsMessage.getProperty("S21"),infoCarRoute.getFullName(), infoCarRoute.getLicensePlate().toUpperCase(),
									 infoCarRoute.getStartPlace().toUpperCase(),
									infoCarRoute.getTargetPlace().toUpperCase(),  new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
									new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase());
							String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
							sms.sendSms(inforNotifySmsManager.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
									inforNotifySmsManager.getPhone(), typeSms);
							
							//send to user vote
							String voteMessgage = "Yêu cầu đặt xe của đ/c đã được hoàn thành. Vui lòng vào đánh giá chuyến xe";
							addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
							String employeeUsername = null;
							VT030009ResponseCarRoute vt030009ResponseCarRoute = vt030009dao.findCarRoute(obj);
							Integer carTypeBooking = vt030009ResponseCarRoute.getCarBookingType();
							if(carTypeBooking != null && Constant.CAR_BOOKING_TYPE_DEFAULT == carTypeBooking) {
								List<VT030009UpdateProcessCarRoute> listUpdateProcessCarRoute = vt030009dao.getInforUpdateProcessCarRoute(vt030009ResponseCarRoute);
								for (VT030009UpdateProcessCarRoute vt030009UpdateProcessCarRoute : listUpdateProcessCarRoute) {
									employeeUsername = vt030009dao.findEmployee(vt030009UpdateProcessCarRoute.getBookCarId());
									VT000000EntitySmsVsNotify userNoti = vt000000DAO.findNotifySmsByUserName(employeeUsername);
									userNoti.setToUserName(employeeUsername);
									notification.sendNotification(userNoti.getToUserName(), addInfo.toString(),
									    voteMessgage, "ĐÁNH GIÁ CHUYẾN XE", 31, obj.getUserName(),
		                  Constant.STATUS_ROUTE_CAR_COME_BACK);
									sms.sendSms(userNoti.getToUserId(), voteMessgage, Constant.STATUS_NEW_SMS, userNoti.getPhone(), typeSms);
								}
							} else {
								employeeUsername = vt030009dao.findEmployee(obj.getBookCarId());
								VT000000EntitySmsVsNotify userNoti = vt000000DAO.findNotifySmsByUserName(employeeUsername);
								userNoti.setToUserName(employeeUsername);
								notification.sendNotification(userNoti.getToUserName(), addInfo.toString(),
								    voteMessgage, "ĐÁNH GIÁ CHUYẾN XE", 31, obj.getUserName(),
	                  Constant.STATUS_ROUTE_CAR_COME_BACK);
								sms.sendSms(userNoti.getToUserId(), voteMessgage, Constant.STATUS_NEW_SMS, userNoti.getPhone(), typeSms);
							}
							
							
						}
					}
				};
				notiThread.start();
				response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("********************* END EXECUTE APIVT030009_01 ***********************");
		return response;
	}

	/**
	 * Get information of route
	 * 
	 * @param VT030009UpdateProcessCarRoute obj,
	 * @param Collection<GrantedAuthority> listRole
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly=true)
	public ResponseEntityBase findCarRoute(VT030009UpdateProcessCarRoute obj, Collection<GrantedAuthority> listRole)
			throws Exception {
		logger.info("********************* START EXECUTE APIVT030009_02 ***********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		VT030009ResponseCarRoute data = null;
		try {
			obj.setMasterClassRoute(Constant.MASTER_CLASS_ROUTE_CAR);
			obj.setMasterClassSeat(Constant.MASTER_CLASS_SEAT_CAR);
			obj.setMasterClassType(Constant.MASTER_CLASS_TYPE_CAR);
			data = vt030009dao.findCarRoute(obj);
			if(data != null && Constant.CAR_BOOKING_TYPE_DEFAULT == data.getCarBookingType()) {
				VT030009RequestGetUser requestGetUser = new VT030009RequestGetUser();
				requestGetUser.setCarId(data.getCarId());
				requestGetUser.setDateStart(data.getDateStart());
				List<VT030009UserInfor> userInfor = vt030009dao.getUserInfor(requestGetUser);
				if(userInfor != null && !userInfor.isEmpty()) {
					data.setUserInforList(userInfor);
				}
				// lay thong tin tong so nguoi tham gia
				VT030007InforDetailMergeCar inforDetailMergeCar = vt030009dao.getInforDetailMergeCar(requestGetUser);
				if(inforDetailMergeCar != null) {
					data.setTotalPartner(inforDetailMergeCar.getTotalPassage());
					data.setDateStart(inforDetailMergeCar.getStartDate());
					data.setDateEnd(inforDetailMergeCar.getEndDate());
				}
			}
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			response.setData(null);
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		logger.info("********************* END EXECUTE APIVT030009_02 ***********************");
		return response;
	}

	/**
	 * Tim kiem tat ca yeu cau da duoc xep xe, den vi tri, hoan thanh
	 * 
	 * @param VT030009ResponseCarRoute obj
	 * @param Collection<GrantedAuthority> listRole
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly=true)
	public ResponseEntityBase getListRequest(VT030009ResponseCarRoute obj, Collection<GrantedAuthority> listRole)
			throws Exception {
		logger.info("********************* START EXECUTE APIVT030009_03 ***********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT030009ResponseCarRoute> data = new ArrayList<>();
		try {
			if (!listRole.isEmpty()) {
				if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
					obj.setProcessByRole(Constant.PMQT_ROLE_ADMIN);
				} else if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_DOIXE))) {
					obj.setProcessByRole(Constant.PMQT_ROLE_MANAGER_DOIXE);
				} else if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_EMPLOYEE_DOIXE))) {
					obj.setProcessByRole(Constant.PMQT_ROLE_EMPLOYEE_DOIXE);
				}
				obj.setMasterClassRoute(Constant.MASTER_CLASS_ROUTE_CAR);
				obj.setMasterClassSeat(Constant.MASTER_CLASS_SEAT_CAR);
				obj.setMasterClassType(Constant.MASTER_CLASS_TYPE_CAR);
				data = vt030009dao.getListRequestMergeCar(obj);
//				if(obj.getListStatus() != null && obj.getListStatus().length > 0) {
////					List<Integer> listStatusTemp = Arrays.stream(obj.getListStatus()).boxed().collect(Collectors.toList());
////					if(listStatusTemp.contains(Constant.STATUS_MEG) || obj.getCarBookingType() == Constant.CAR_BOOKING_TYPE_DEFAULT) {
//						
////					} else {
////						data = vt030009dao.getListRequest(obj);
////					}
//				} else {
//					data = vt030009dao.getListRequest(obj);
//				}
				response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
			}
		} catch (Exception e) {
			response.setData(null);
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		logger.info("********************* END EXECUTE APIVT030009_03 ***********************");
		return response;
	}
}
