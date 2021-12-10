package com.viettel.vtnet360.vt03.vt030008.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.vtnet360.driving.dto.AddingTimeNotiInfo;
import com.viettel.vtnet360.driving.dto.DriverAndCarNotiInfo;
import com.viettel.vtnet360.driving.request.dao.BookingDAO;
import com.viettel.vtnet360.driving.service.BookingService;
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
import com.viettel.vtnet360.vt03.vt030008.dao.VT030008DAO;
import com.viettel.vtnet360.vt03.vt030008.entity.VT030008DriveCarInfo;
import com.viettel.vtnet360.vt03.vt030008.entity.VT030008RequestMatchCar;
import com.viettel.vtnet360.vt03.vt030008.entity.VT030008ResponseCars;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;

/**
 * Class VT030008ServiceImpl
 *
 * @author CuongHD
 *
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030008ServiceImpl implements VT030008Service {
  @Autowired
  private VT030008DAO vt030008dao;

  private final Logger logger = Logger.getLogger(this.getClass());

  @Autowired
  Notification notification;

  @Autowired
  Sms sms;

  @Autowired
  VT000000DAO vt000000DAO;

  @Autowired
  VT030000DAO vt030000dao;

  @Autowired
  private BookingDAO bookingDao;
  
  @Autowired
  private BookingService bookingService;

  @Autowired
  Properties notifyMessage;

  @Autowired
  Properties smsMessage;

  /**
   * Find Driver freedom
   *
   * @param VT030008RequestMatchCar
   *          obj
   * @return ResponseEntityBase
   * @throws Exception
   */
  @Override
  @Transactional(readOnly = true)
  public ResponseEntityBase findFreeDriving(VT030008RequestMatchCar obj) throws Exception {
    logger.info("********************* START EXECUTE APIVT030008_01 ***********************");
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    List<VT030008RequestMatchCar> data = new ArrayList<>();
    try {

      if (obj.getBookCarId() == null) {
        response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      } else {
        VT030014ListCondition info = vt030008dao.findCarBookingById(obj.getBookCarId());
        obj.setSquadId(info.getSquadId());
        data = vt030008dao.findFreeDriving(obj);
        response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    logger.info("********************* END EXECUTE APIVT030008_01 ***********************");
    return response;
  }

  /**
   * Take the car not working corresponding to the driver
   *
   * @param VT030008ResponseCars
   *          obj
   * @return ResponseEntityBase
   * @throws Exception
   */
  @Override
  @Transactional(readOnly = true)
  public ResponseEntityBase findCarMatchDriver(VT030008ResponseCars obj) throws Exception {
    logger.info("********************* START EXECUTE APIVT030008_02 ***********************");
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    List<VT030008ResponseCars> data = new ArrayList<>();
    try {
      obj.setMasterClassRoute(Constant.MASTER_CLASS_ROUTE_CAR);
      obj.setMasterClassSeat(Constant.MASTER_CLASS_SEAT_CAR);
      obj.setMasterClassType(Constant.MASTER_CLASS_TYPE_CAR);
      data = vt030008dao.findCarMatchDriver(obj);
      response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    logger.info("********************* END EXECUTE APIVT030008_02 ***********************");
    return response;
  }

  /**
   * update carId, userDriver, userAssigner in CAR_BOOKING
   *
   * @param VT030008DriveCarInfo
   *          obj
   * @param Collection<GrantedAuthority>
   *          roleList
   * @return ResponseEntityBase
   */
  @Override
  @Transactional
	public ResponseEntityBase matchCarAndDrive(VT030008DriveCarInfo obj, Collection<GrantedAuthority> roleList)
			throws Exception {
		logger.info("********************* START EXECUTE APIVT030008_03 ***********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT000000EntitySmsVsNotify> vt000000EntitySmsVsNotifies = new ArrayList<VT000000EntitySmsVsNotify>();
		List<VT030000NotifySmsInfo> infoCarRoutes = new ArrayList<VT030000NotifySmsInfo>();
		StringBuilder messContentToDriver = new StringBuilder("");
		// bổ sung luồng ghép xe
		if (obj.getListBookCarId() != null && obj.getListBookCarId().size() > 0) {
			boolean oneDriver = true;
			for (String bookCarId : obj.getListBookCarId()) {
				obj.setBookCarId(bookCarId);
				ResponseEntityBase responseTemp = this.mergeCar(obj, roleList);
				if (responseTemp.getStatus() != Constant.RESPONSE_STATUS_SUCCESS) {
					response = responseTemp;
					break;
				}
				response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				AddingTimeNotiInfo oldBooking = bookingDao.getCarbookingById(obj.getBookCarId());
				VT030000NotifySmsInfo info = new VT030000NotifySmsInfo(Constant.MASTER_CLASS_TYPE_CAR,
						Constant.MASTER_CLASS_SEAT_CAR, Constant.MASTER_CLASS_ROUTE_CAR,
						obj.getBookCarId());
				VT030000NotifySmsInfo infoCarRoute = vt030000dao.findInfoNotifySms(info);
				VT000000EntitySmsVsNotify inforNotifySmsDriver = new VT000000EntitySmsVsNotify();
				inforNotifySmsDriver = vt000000DAO.findNotifySmsByUserName(obj.getUserName());
				infoCarRoutes.add(infoCarRoute);
				vt000000EntitySmsVsNotifies.add(inforNotifySmsDriver);
				messContentToDriver.append(oldBooking.getEmpName() + " - " + oldBooking.getEmpPhone() + ",");
			}
			// send sms, notifi to driver
			if(response.getStatus() == Constant.RESPONSE_STATUS_SUCCESS) {
				String messToDriver = messContentToDriver.substring(0, messContentToDriver.length()-1);
				Date startDate = infoCarRoutes.stream().map(u -> u.getDateStart()).min(Date::compareTo).get();
				Date endDate = infoCarRoutes.stream().map(u -> u.getDateEnd()).max(Date::compareTo).get();
				String route = infoCarRoutes.get(0).getRoute();
				String startPlace = infoCarRoutes.get(0).getStartPlace();
				String targetPlace = infoCarRoutes.get(0).getTargetPlace();
				String phone = vt000000EntitySmsVsNotifies.get(0).getPhone();
				String toUserName = obj.getUserName();
				Integer userId = vt000000EntitySmsVsNotifies.get(0).getToUserId();
				sendSmsNotiToDriverMergeCar(messToDriver,startDate, endDate, route, startPlace, targetPlace, phone, toUserName, userId);
				response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
			}
		} else {
			Long end = vt030008dao.getDateEndOfReqest(obj.getBookCarId());
			Long currentTime = Calendar.getInstance().getTimeInMillis();
			if (currentTime < end) {
				return new ResponseEntityBase(Constant.RESPONSE_DATA_TOO_LONG, null);
			}
			try {
				if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
						|| roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_DOIXE))) {
					AddingTimeNotiInfo oldBooking = bookingDao.getCarbookingById(obj.getBookCarId());

					int driverFlag = vt030008dao.checkDriverIsActive(obj.getUserName());
					if (driverFlag < 1) {
						return new ResponseEntityBase(Constant.DRIVER_NOT_EXISTED, null);
					}
					int carFlag = vt030008dao.checkCarIsActive(obj.getCarId());
					if (carFlag != Constant.DELETE_FLAG) {
						return new ResponseEntityBase(Constant.CAR_NOT_EXISTED, null);
					}
					// Update PROCESS_STATUS in DRIVE_CAR table, in CARS table ,
					// in DRIVE
					vt030008dao.updateStateDriveCar(obj);
					vt030008dao.updateStateCar(obj);
					vt030008dao.updateStateDriver(obj);

					// update carId, userDriver, userAssigner in CAR_BOOKING
					// table

					int row = vt030008dao.updateCarBooking(obj);
					if (row == 0) {
						return response;
					}
					Thread notiThread = new Thread() {
						public void run() {
							try {
								String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
								VT030000NotifySmsInfo info = new VT030000NotifySmsInfo(Constant.MASTER_CLASS_TYPE_CAR,
										Constant.MASTER_CLASS_SEAT_CAR, Constant.MASTER_CLASS_ROUTE_CAR,
										obj.getBookCarId());
								VT030000NotifySmsInfo infoCarRoute = vt030000dao.findInfoNotifySms(info);
								VT000000EntitySmsVsNotify inforNotifySmsDriver = vt000000DAO
										.findNotifySmsByUserName(obj.getUserName());
								inforNotifySmsDriver.setToUserName(obj.getUserName());

								if (oldBooking.getStatus() == 1) {
									// tim kiem thong tin chuyen
									// xe
									// get info of Driver
									// info of Requester
									VT000000EntitySmsVsNotify inforNotifySmsRequester = vt000000DAO
											.findNotifySmsByUserName(oldBooking.getEmpUsername());
									inforNotifySmsRequester.setToUserName(oldBooking.getEmpUsername());

									// send notify to requester
									String messageNotify = MessageFormat.format(notifyMessage.getProperty("N17"),
											inforNotifySmsRequester.getCreateUser(),
											inforNotifySmsDriver.getCreateUser(), inforNotifySmsDriver.getPhone(),
											infoCarRoute.getType(), infoCarRoute.getSeat(),
											infoCarRoute.getLicensePlate(),
											new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE),
											new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE));

									AdditionalInfoBase addInfo = new AdditionalInfoBase();
									addInfo.setId(obj.getBookCarId());
									addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
									notification.sendNotification(inforNotifySmsRequester.getToUserName(),
											addInfo.toString(), messageNotify, title, Constant.TYPE_NOTIFY_MODUL03,
											obj.getUserName(), Constant.MANAGER_APPROVED);

									// send SMS to requester
									String messageSms = MessageFormat.format(smsMessage.getProperty("S17"),
											inforNotifySmsRequester.getCreateUser(),
											inforNotifySmsDriver.getCreateUser(), inforNotifySmsDriver.getPhone(),
											infoCarRoute.getType(), infoCarRoute.getSeat(),
											infoCarRoute.getLicensePlate(),
											new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE),
											new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE));
									String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
									sms.sendSms(inforNotifySmsRequester.getToUserId(), messageSms,
											Constant.STATUS_NEW_SMS, inforNotifySmsRequester.getPhone(), typeSms);

									// send to manager
									if (oldBooking.getQlttUsername() != null) {
										addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
										VT000000EntitySmsVsNotify qltt = vt000000DAO
												.findNotifySmsByUserName(oldBooking.getQlttUsername());
										qltt.setToUserName(oldBooking.getQlttUsername());
										notification.sendNotification(qltt.getToUserName(), addInfo.toString(),
												messageNotify, title, Constant.TYPE_NOTIFY_MODUL03,
												oldBooking.getQlttUsername(), Constant.MANAGER_APPROVED);
										sms.sendSms(qltt.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
												qltt.getPhone(), typeSms);
									}

									VT000000EntitySmsVsNotify qldv = vt000000DAO
											.findNotifySmsByUserName(oldBooking.getQldvUsername());
									qldv.setToUserName(oldBooking.getQldvUsername());

									addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
									notification.sendNotification(qldv.getToUserName(), addInfo.toString(),
											messageNotify, title, Constant.TYPE_NOTIFY_MODUL03,
											oldBooking.getQldvUsername(), Constant.MANAGER_APPROVED);
									sms.sendSms(qldv.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
											qldv.getPhone(), typeSms);

									// send notify to driver
									messageNotify = MessageFormat.format(notifyMessage.getProperty("N18"),
											inforNotifySmsRequester.getCreateUser(), inforNotifySmsRequester.getPhone(),
											oldBooking.getEmpUnitName(),
											new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE)
													.toUpperCase(),
											new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE)
													.toUpperCase(),
											infoCarRoute.getRoute().toUpperCase(),
											infoCarRoute.getStartPlace().toUpperCase(),
											infoCarRoute.getTargetPlace().toUpperCase());

									addInfo = new AdditionalInfoBase();
									addInfo.setId(obj.getBookCarId());
									addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYEE_DOIXE);
									notification.sendNotification(inforNotifySmsDriver.getToUserName(),
											addInfo.toString(), messageNotify, title, Constant.TYPE_NOTIFY_MODUL03,
											obj.getUserName(), Constant.MANAGER_APPROVED);

									// send SMS to driver
									messageSms = MessageFormat.format(smsMessage.getProperty("S18"),
											inforNotifySmsRequester.getCreateUser(), inforNotifySmsRequester.getPhone(),
											oldBooking.getEmpUnitName(),
											new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE)
													.toUpperCase(),
											new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE)
													.toUpperCase(),
											infoCarRoute.getRoute().toUpperCase(),
											infoCarRoute.getStartPlace().toUpperCase(),
											infoCarRoute.getTargetPlace().toUpperCase());
									typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
									sms.sendSms(inforNotifySmsDriver.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
											inforNotifySmsDriver.getPhone(), typeSms);
								}

								if (oldBooking.getStatus() == 4) {
									// TODO: change driver
									ObjectMapper mapper = new ObjectMapper();
									System.out.println(
											mapper.writerWithDefaultPrettyPrinter().writeValueAsString(infoCarRoute));
									if (obj.getUserName().equals(oldBooking.getDriverUsername()) == false) {
										String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
										AdditionalInfoBase addInfo = new AdditionalInfoBase();
										addInfo.setId(obj.getBookCarId());
										addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);

										DriverAndCarNotiInfo notiInfo = bookingDao.getDriverAndCarNotiInfo(obj);
										String messageNotify = MessageFormat.format(notifyMessage.getProperty("N109"),
												oldBooking.getEmpName(), notiInfo.getDriverFullName(),
												notiInfo.getDriverPhone(), infoCarRoute.getType(),
												infoCarRoute.getSeat(), infoCarRoute.getLicensePlate(),
												new DateUtil(oldBooking.getDateStart()).toString(DateUtil.FORMAT_DATE)
														.toUpperCase(),
												new DateUtil(oldBooking.getDateEnd()).toString(DateUtil.FORMAT_DATE)
														.toUpperCase());

										String messageSms = MessageFormat.format(smsMessage.getProperty("S109"),
												oldBooking.getEmpName(), notiInfo.getDriverFullName(),
												notiInfo.getDriverPhone(), infoCarRoute.getType(),
												infoCarRoute.getSeat(), infoCarRoute.getLicensePlate(),
												new DateUtil(oldBooking.getDateStart()).toString(DateUtil.FORMAT_DATE)
														.toUpperCase(),
												new DateUtil(oldBooking.getDateEnd()).toString(DateUtil.FORMAT_DATE)
														.toUpperCase());

										if (oldBooking.getEmpUsername() != null) {
											addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
											VT000000EntitySmsVsNotify qltt = vt000000DAO
													.findNotifySmsByUserName(oldBooking.getEmpUsername());
											qltt.setToUserName(oldBooking.getEmpUsername());
											notification.sendNotification(qltt.getToUserName(), addInfo.toString(),
													messageNotify, title, Constant.TYPE_NOTIFY_MODUL03,
													oldBooking.getEmpUsername(), Constant.MANAGER_APPROVED);
											sms.sendSms(qltt.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
													qltt.getPhone(), typeSms);
										}

										if (oldBooking.getQlttUsername() != null) {
											addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
											VT000000EntitySmsVsNotify qltt = vt000000DAO
													.findNotifySmsByUserName(oldBooking.getQlttUsername());
											qltt.setToUserName(oldBooking.getQlttUsername());
											notification.sendNotification(qltt.getToUserName(), addInfo.toString(),
													messageNotify, title, Constant.TYPE_NOTIFY_MODUL03,
													oldBooking.getQlttUsername(), Constant.MANAGER_APPROVED);
											sms.sendSms(qltt.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
													qltt.getPhone(), typeSms);
										}

										if (oldBooking.getQldvUsername() != null) {
											VT000000EntitySmsVsNotify qldv = vt000000DAO
													.findNotifySmsByUserName(oldBooking.getQldvUsername());
											qldv.setToUserName(oldBooking.getQldvUsername());
											addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
											notification.sendNotification(qldv.getToUserName(), addInfo.toString(),
													messageNotify, title, Constant.TYPE_NOTIFY_MODUL03,
													oldBooking.getQldvUsername(), Constant.MANAGER_APPROVED);
											sms.sendSms(qldv.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
													qldv.getPhone(), typeSms);
										}

										// Send to old and new driver
										String oldMessageNotify = MessageFormat.format(
												notifyMessage.getProperty("N110"), oldBooking.getRouteType(),
												oldBooking.getStartPlace(), oldBooking.getTargetPlace(),
												oldBooking.getLicensePlate(),
												new DateUtil(oldBooking.getDateStart()).toString(DateUtil.FORMAT_DATE)
														.toUpperCase(),
												new DateUtil(oldBooking.getDateEnd()).toString(DateUtil.FORMAT_DATE)
														.toUpperCase());

										String oldMessageSms = MessageFormat.format(smsMessage.getProperty("S110"),
												oldBooking.getRouteType(), oldBooking.getStartPlace(),
												oldBooking.getTargetPlace(), notiInfo.getLicensePlate(),
												new DateUtil(oldBooking.getDateStart()).toString(DateUtil.FORMAT_DATE)
														.toUpperCase(),
												new DateUtil(oldBooking.getDateEnd()).toString(DateUtil.FORMAT_DATE)
														.toUpperCase());

										// to old driver
										VT000000EntitySmsVsNotify oldDriver = vt000000DAO
												.findNotifySmsByUserName(oldBooking.getDriverUsername());
										oldDriver.setToUserName(oldBooking.getDriverUsername());
										addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYEE_DOIXE);
										notification.sendNotification(oldDriver.getToUserName(), addInfo.toString(),
												oldMessageNotify, title, Constant.TYPE_NOTIFY_MODUL03,
												oldDriver.getToUserName(), Constant.MANAGER_APPROVED);
										sms.sendSms(oldDriver.getToUserId(), oldMessageSms, Constant.STATUS_NEW_SMS,
												oldDriver.getPhone(), typeSms);

										// to new driver
										VT000000EntitySmsVsNotify inforNotifySmsRequester = vt000000DAO
												.findNotifySmsByUserName(obj.getUserName());
										inforNotifySmsRequester.setToUserName(obj.getUserName());
										messageNotify = MessageFormat.format(notifyMessage.getProperty("N18"),
												oldBooking.getEmpName(), oldBooking.getEmpPhone(),
												oldBooking.getEmpUnitName(),
												new DateUtil(oldBooking.getDateStart()).toString(DateUtil.FORMAT_DATE)
														.toUpperCase(),
												new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE)
														.toUpperCase(),
												infoCarRoute.getRoute().toUpperCase(),
												infoCarRoute.getStartPlace().toUpperCase(),
												infoCarRoute.getTargetPlace().toUpperCase());

										addInfo = new AdditionalInfoBase();
										addInfo.setId(obj.getBookCarId());
										addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYEE_DOIXE);
										notification.sendNotification(inforNotifySmsDriver.getToUserName(),
												addInfo.toString(), messageNotify, title, Constant.TYPE_NOTIFY_MODUL03,
												obj.getUserName(), Constant.MANAGER_APPROVED);

										messageSms = MessageFormat.format(smsMessage.getProperty("S18"),
												oldBooking.getEmpName(), oldBooking.getEmpPhone(),
												oldBooking.getEmpUnitName(),
												new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE)
														.toUpperCase(),
												new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE)
														.toUpperCase(),
												infoCarRoute.getRoute().toUpperCase(),
												infoCarRoute.getStartPlace().toUpperCase(),
												infoCarRoute.getTargetPlace().toUpperCase());
										typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
										sms.sendSms(inforNotifySmsDriver.getToUserId(), messageSms,
												Constant.STATUS_NEW_SMS, inforNotifySmsDriver.getPhone(), typeSms);
									}
								}
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
							}
						}
					};
					notiThread.start();
					response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		logger.info("********************* END EXECUTE APIVT030008_03 ***********************");
		return response;
	}

  /**
   * Cancel mactching car
   *
   * @param VT030008DriveCarInfo
   *          obj
   * @param Collection<GrantedAuthority>
   *          roleList
   * @return ResponseEntityBase
   * @throws Exception
   *
   */
  @Override
  @Transactional
  public ResponseEntityBase cancelMatching(VT030008DriveCarInfo obj, Collection<GrantedAuthority> roleList) throws Exception {
    logger.info("********************* START EXECUTE APIVT030008_04 ***********************");
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if (roleList.isEmpty()) {
        return response;
      }
      if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
          || roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_DOIXE))) {
        VT030014ListCondition carBooking = vt030008dao.findCarBookingById(obj.getBookCarId());
        int status = carBooking.getStatusTrips();
        if (status == obj.getProcessStatus()) {
          return response;
        }
        AddingTimeNotiInfo notiInfo = bookingDao.getCarbookingById(obj.getBookCarId());
        if (carBooking.getStatusTrips() == 1) {
          obj.setProcessStatus(9);
          vt030008dao.cancelMatching(obj);
        } else if (carBooking.getStatusTrips() == 4) {
          obj.setProcessStatus(3);
          obj.setCarId(carBooking.getCarId());
          obj.setUserName(carBooking.getDriverUser());
          // obj.setUserName(userDriver);
          // Update PROCESS_STATUS in DRIVE_CAR table
          vt030008dao.updateStateDriveCar(obj);
          vt030008dao.updateStateCar(obj);
          vt030008dao.updateStateDriver(obj);

          obj.setReasonRefuse(obj.getReasonRefuse() == null ? null : obj.getReasonRefuse().trim());
          // update carId, userDriver, userAssigner in CAR_BOOKING table
          vt030008dao.cancelMatching(obj);
        } else {
          return response;
        }

        // tim kiem thong tin chuyen xe
        VT030000NotifySmsInfo info = new VT030000NotifySmsInfo(Constant.MASTER_CLASS_TYPE_CAR,
            Constant.MASTER_CLASS_SEAT_CAR, Constant.MASTER_CLASS_ROUTE_CAR, obj.getBookCarId());
        VT030000NotifySmsInfo infoCarRoute = vt030000dao.findInfoNotifySms(info);
        Thread notiToRequester = new Thread() {
          public void run() {
            try {
              String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
              String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
              AdditionalInfoBase addInfo = new AdditionalInfoBase();
              addInfo.setId(obj.getBookCarId());

              if(notiInfo.getStatus() == 1) {
                String message = MessageFormat.format(notifyMessage.getProperty("N16"),
                    notiInfo.getEmpName(), notiInfo.getCarType(), notiInfo.getSeat(), 
                    new DateUtil(notiInfo.getDateStart()).toString(DateUtil.FORMAT_DATE),
                    new DateUtil(notiInfo.getDateEnd()).toString(DateUtil.FORMAT_DATE),
                    obj.getReasonRefuse());
                
                addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                VT000000EntitySmsVsNotify requestUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getEmpUsername());
                requestUser.setToUserName(notiInfo.getEmpUsername());
                bookingService.sendNotiAndSms(requestUser, message, title, addInfo);
                
                if(notiInfo.getQlttUsername() != null) {
                  VT000000EntitySmsVsNotify firstApprover = vt000000DAO.findNotifySmsByUserName(notiInfo.getQlttUsername());
                  firstApprover.setToUserName(notiInfo.getQlttUsername());
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                  bookingService.sendNotiAndSms(firstApprover, message, title, addInfo);
                }
                
                if(notiInfo.getQldvUsername() != null) {
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                  VT000000EntitySmsVsNotify secondApprover = vt000000DAO.findNotifySmsByUserName(notiInfo.getQldvUsername());
                  secondApprover.setToUserName(notiInfo.getQldvUsername());
                  bookingService.sendNotiAndSms(secondApprover, message, title, addInfo);
                }
                
                if(notiInfo.getCvpUsername() != null && !notiInfo.getCvpUsername().equals(notiInfo.getQldvUsername())) {
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_CVP);
                  VT000000EntitySmsVsNotify thirdApprover = vt000000DAO.findNotifySmsByUserName(notiInfo.getCvpUsername());
                  thirdApprover.setToUserName(notiInfo.getCvpUsername());
                  bookingService.sendNotiAndSms(thirdApprover, message, title, addInfo);
                }
              } else if(notiInfo.getStatus() == 4) {
                // get info of Driver
                VT000000EntitySmsVsNotify inforNotifySmsDriver = vt000000DAO.findNotifySmsByUserName(notiInfo.getDriverUsername());
                inforNotifySmsDriver.setToUserName(notiInfo.getDriverUsername());
                // info of Requester
                VT000000EntitySmsVsNotify inforNotifySmsRequester = vt000000DAO.findNotifySmsByUserName(notiInfo.getEmpUsername());
                inforNotifySmsRequester.setToUserName(notiInfo.getEmpUsername());

                // send notify to requester
                String messageNotify = MessageFormat.format(notifyMessage.getProperty("N101"),
                    notiInfo.getCarType().toUpperCase(), notiInfo.getSeat().toUpperCase(),
                    new DateUtil(notiInfo.getDateStart()).toString(DateUtil.FORMAT_DATE),
                    new DateUtil(notiInfo.getDateEnd()).toString(DateUtil.FORMAT_DATE),
                    infoCarRoute.getReasonAssigner().toUpperCase());
                addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                notification.sendNotification(inforNotifySmsRequester.getToUserName(), addInfo.toString(), messageNotify,
                    title, Constant.TYPE_NOTIFY_MODUL03, inforNotifySmsRequester.getToUserName(), Constant.MANAGER_APPROVED);

                // send SMS to requester
                String messageSms = MessageFormat.format(smsMessage.getProperty("S101"),
                    notiInfo.getCarType().toUpperCase(), notiInfo.getSeat().toUpperCase(),
                    new DateUtil(notiInfo.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
                    new DateUtil(notiInfo.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
                    infoCarRoute.getReasonAssigner().toUpperCase());
                sms.sendSms(inforNotifySmsRequester.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
                    inforNotifySmsRequester.getPhone(), typeSms);
                // send notify to driver
                messageNotify = MessageFormat.format(notifyMessage.getProperty("N100"),
                    infoCarRoute.getRoute().toUpperCase(), infoCarRoute.getStartPlace().toUpperCase(),
                    infoCarRoute.getTargetPlace().toUpperCase(), infoCarRoute.getLicensePlate().toUpperCase(),
                    new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
                    new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
                    infoCarRoute.getReasonAssigner().toUpperCase());
                title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
                addInfo = new AdditionalInfoBase();
                addInfo.setId(obj.getBookCarId());
                addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYEE_DOIXE);
                notification.sendNotification(inforNotifySmsDriver.getToUserName(), addInfo.toString(), messageNotify,
                    title, Constant.TYPE_NOTIFY_MODUL03, inforNotifySmsDriver.getToUserName(), Constant.MANAGER_APPROVED);
                // send SMS to driver
                messageSms = MessageFormat.format(smsMessage.getProperty("S100"), infoCarRoute.getRoute().toUpperCase(),
                    infoCarRoute.getStartPlace().toUpperCase(), infoCarRoute.getTargetPlace().toUpperCase(),
                    infoCarRoute.getLicensePlate().toUpperCase(),
                    new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
                    new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
                    infoCarRoute.getReasonAssigner().toUpperCase());
                typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
                sms.sendSms(inforNotifySmsDriver.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
                    inforNotifySmsDriver.getPhone(), typeSms);
                
                // send to managers
                String message = MessageFormat.format(notifyMessage.getProperty("N115"),
                    notiInfo.getEmpName(), notiInfo.getCarType(), notiInfo.getSeat(),
                    new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE),
                    new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE),
                    infoCarRoute.getReasonAssigner());
                
                if(notiInfo.getQlttUsername() != null) {
                  VT000000EntitySmsVsNotify firstApprover = vt000000DAO.findNotifySmsByUserName(notiInfo.getQlttUsername());
                  firstApprover.setToUserName(notiInfo.getQlttUsername());
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                  bookingService.sendNotiAndSms(firstApprover, message, title, addInfo);
                }
                
                if(notiInfo.getQldvUsername() != null) {
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                  VT000000EntitySmsVsNotify secondApprover = vt000000DAO.findNotifySmsByUserName(notiInfo.getQldvUsername());
                  secondApprover.setToUserName(notiInfo.getQldvUsername());
                  bookingService.sendNotiAndSms(secondApprover, message, title, addInfo);
                }
                
                if(notiInfo.getCvpUsername() != null && !notiInfo.getCvpUsername().equals(notiInfo.getQldvUsername())) {
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_CVP);
                  VT000000EntitySmsVsNotify thirdApprover = vt000000DAO.findNotifySmsByUserName(notiInfo.getCvpUsername());
                  thirdApprover.setToUserName(notiInfo.getCvpUsername());
                  bookingService.sendNotiAndSms(thirdApprover, message, title, addInfo);
                }
              }
            } catch (Exception e) {
              logger.error(e.getMessage(), e);
            }
          }
        };
        notiToRequester.start();
        response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return response;
  }

  /**
   * X? l� gh�p xe
   * @return
   */
  private ResponseEntityBase mergeCar(VT030008DriveCarInfo obj, Collection<GrantedAuthority> roleList)
          throws Exception {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    logger.info("********************* START EXECUTE APIVT030008_03 ***********************");
    Long end = vt030008dao.getDateEndOfReqest(obj.getBookCarId());
    Long currentTime = Calendar.getInstance().getTimeInMillis();
    if (currentTime < end) {
      return new ResponseEntityBase(Constant.RESPONSE_DATA_TOO_LONG, null);
    }
    try {
      if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
              || roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_DOIXE))) {
        AddingTimeNotiInfo oldBooking = bookingDao.getCarbookingById(obj.getBookCarId());

        int driverFlag = vt030008dao.checkDriverIsActive(obj.getUserName());
        if (driverFlag < 1) {
          return new ResponseEntityBase(Constant.DRIVER_NOT_EXISTED, null);
        }
        int carFlag = vt030008dao.checkCarIsActive(obj.getCarId());
        if (carFlag != Constant.DELETE_FLAG) {
          return new ResponseEntityBase(Constant.CAR_NOT_EXISTED, null);
        }
        // Update PROCESS_STATUS in DRIVE_CAR table, in CARS table ,
        // in DRIVE
        vt030008dao.updateStateDriveCar(obj);
        vt030008dao.updateStateCar(obj);
        vt030008dao.updateStateDriver(obj);

        // update carId, userDriver, userAssigner in CAR_BOOKING
        // table
        obj.setCarBookingType(Constant.CAR_BOOKING_TYPE_DEFAULT);
        int row = vt030008dao.updateCarBookingMerge(obj);
        if (row == 0) {
          return response;
        }
        Thread notiThread = new Thread() {
          public void run() {
            try {
              String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
              VT030000NotifySmsInfo info = new VT030000NotifySmsInfo(Constant.MASTER_CLASS_TYPE_CAR,
                      Constant.MASTER_CLASS_SEAT_CAR, Constant.MASTER_CLASS_ROUTE_CAR, obj.getBookCarId());
              VT030000NotifySmsInfo infoCarRoute = vt030000dao.findInfoNotifySms(info);
              VT000000EntitySmsVsNotify inforNotifySmsDriver = vt000000DAO.findNotifySmsByUserName(obj.getUserName());
              inforNotifySmsDriver.setToUserName(obj.getUserName());

              if (oldBooking.getStatus() == 1) { // da phe duyet
                // tim kiem thong tin chuyen
                // xe
                // get info of Driver
                // info of Requester
                VT000000EntitySmsVsNotify inforNotifySmsRequester = vt000000DAO.findNotifySmsByUserName(oldBooking.getEmpUsername());
                inforNotifySmsRequester.setToUserName(oldBooking.getEmpUsername());

                // send notify to requester
                String messageNotify = MessageFormat.format(notifyMessage.getProperty("N17"),
                        inforNotifySmsRequester.getCreateUser(), inforNotifySmsDriver.getCreateUser(),
                        inforNotifySmsDriver.getPhone(),
                        infoCarRoute.getType(), infoCarRoute.getSeat(),
                        infoCarRoute.getLicensePlate(),
                        new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE),
                        new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE));

                AdditionalInfoBase addInfo = new AdditionalInfoBase();
                addInfo.setId(obj.getBookCarId());
                addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                notification.sendNotification(inforNotifySmsRequester.getToUserName(), addInfo.toString(), messageNotify,
                        title, Constant.TYPE_NOTIFY_MODUL03, obj.getUserName(), Constant.MANAGER_APPROVED);

                // send SMS to requester
                String messageSms = MessageFormat.format(smsMessage.getProperty("S17"),
                        inforNotifySmsRequester.getCreateUser(), inforNotifySmsDriver.getCreateUser(),
                        inforNotifySmsDriver.getPhone(),
                        infoCarRoute.getType(), infoCarRoute.getSeat(),
                        infoCarRoute.getLicensePlate(),
                        new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE),
                        new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE));
                String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
                sms.sendSms(inforNotifySmsRequester.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
                        inforNotifySmsRequester.getPhone(), typeSms);

                //send to manager
                if (oldBooking.getQlttUsername() != null) {
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                  VT000000EntitySmsVsNotify qltt = vt000000DAO.findNotifySmsByUserName(oldBooking.getQlttUsername());
                  qltt.setToUserName(oldBooking.getQlttUsername());
                  notification.sendNotification(qltt.getToUserName(), addInfo.toString(), messageNotify,
                          title, Constant.TYPE_NOTIFY_MODUL03, oldBooking.getQlttUsername(), Constant.MANAGER_APPROVED);
                  sms.sendSms(qltt.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
                          qltt.getPhone(), typeSms);
                }

                VT000000EntitySmsVsNotify qldv = vt000000DAO.findNotifySmsByUserName(oldBooking.getQldvUsername());
                qldv.setToUserName(oldBooking.getQldvUsername());

                addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                notification.sendNotification(qldv.getToUserName(), addInfo.toString(), messageNotify,
                        title, Constant.TYPE_NOTIFY_MODUL03, oldBooking.getQldvUsername(), Constant.MANAGER_APPROVED);
                sms.sendSms(qldv.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
                        qldv.getPhone(), typeSms);

                // send notify to driver
//                messageNotify = MessageFormat.format(notifyMessage.getProperty("N18"),
//                        inforNotifySmsRequester.getCreateUser(), inforNotifySmsRequester.getPhone(),
//                        oldBooking.getEmpUnitName(),
//                        new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
//                        new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
//                        infoCarRoute.getRoute().toUpperCase(), infoCarRoute.getStartPlace().toUpperCase(),
//                        infoCarRoute.getTargetPlace().toUpperCase());
//
//                addInfo = new AdditionalInfoBase();
//                addInfo.setId(obj.getBookCarId());
//                addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYEE_DOIXE);
//                notification.sendNotification(inforNotifySmsDriver.getToUserName(), addInfo.toString(), messageNotify,
//                        title, Constant.TYPE_NOTIFY_MODUL03, obj.getUserName(), Constant.MANAGER_APPROVED);
//
//                // send SMS to driver
//                messageSms = MessageFormat.format(smsMessage.getProperty("S18"), inforNotifySmsRequester.getCreateUser(),
//                        inforNotifySmsRequester.getPhone(),
//                        oldBooking.getEmpUnitName(),
//                        new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
//                        new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
//                        infoCarRoute.getRoute().toUpperCase(), infoCarRoute.getStartPlace().toUpperCase(),
//                        infoCarRoute.getTargetPlace().toUpperCase());
//                typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
//                sms.sendSms(inforNotifySmsDriver.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
//                        inforNotifySmsDriver.getPhone(), typeSms);
              }

              if (oldBooking.getStatus() == 4 || oldBooking.getStatus() == 11) { // tr?ng th�i d� x?p xe 4 ho?c d� gh�p xe 11
                //TODO: change driver
                ObjectMapper mapper = new ObjectMapper();
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(infoCarRoute));
                if (obj.getUserName().equals(oldBooking.getDriverUsername()) == false) {
                  String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
                  AdditionalInfoBase addInfo = new AdditionalInfoBase();
                  addInfo.setId(obj.getBookCarId());
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);

                  DriverAndCarNotiInfo notiInfo = bookingDao.getDriverAndCarNotiInfo(obj);
                  String messageNotify = MessageFormat.format(notifyMessage.getProperty("N109"),
                          oldBooking.getEmpName(), notiInfo.getDriverFullName(), notiInfo.getDriverPhone(),
                          infoCarRoute.getType(), infoCarRoute.getSeat(), infoCarRoute.getLicensePlate(),
                          new DateUtil(oldBooking.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
                          new DateUtil(oldBooking.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase());

                  String messageSms = MessageFormat.format(smsMessage.getProperty("S109"),
                          oldBooking.getEmpName(), notiInfo.getDriverFullName(), notiInfo.getDriverPhone(),
                          infoCarRoute.getType(), infoCarRoute.getSeat(), infoCarRoute.getLicensePlate(),
                          new DateUtil(oldBooking.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
                          new DateUtil(oldBooking.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase());

                  if (oldBooking.getEmpUsername() != null) {
                    addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                    VT000000EntitySmsVsNotify qltt = vt000000DAO.findNotifySmsByUserName(oldBooking.getEmpUsername());
                    qltt.setToUserName(oldBooking.getEmpUsername());
                    notification.sendNotification(qltt.getToUserName(), addInfo.toString(), messageNotify,
                            title, Constant.TYPE_NOTIFY_MODUL03, oldBooking.getEmpUsername(), Constant.MANAGER_APPROVED);
                    sms.sendSms(qltt.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
                            qltt.getPhone(), typeSms);
                  }

                  if (oldBooking.getQlttUsername() != null) {
                    addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                    VT000000EntitySmsVsNotify qltt = vt000000DAO.findNotifySmsByUserName(oldBooking.getQlttUsername());
                    qltt.setToUserName(oldBooking.getQlttUsername());
                    notification.sendNotification(qltt.getToUserName(), addInfo.toString(), messageNotify,
                            title, Constant.TYPE_NOTIFY_MODUL03, oldBooking.getQlttUsername(), Constant.MANAGER_APPROVED);
                    sms.sendSms(qltt.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
                            qltt.getPhone(), typeSms);
                  }

                  if (oldBooking.getQldvUsername() != null) {
                    VT000000EntitySmsVsNotify qldv = vt000000DAO.findNotifySmsByUserName(oldBooking.getQldvUsername());
                    qldv.setToUserName(oldBooking.getQldvUsername());
                    addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                    notification.sendNotification(qldv.getToUserName(), addInfo.toString(), messageNotify,
                            title, Constant.TYPE_NOTIFY_MODUL03, oldBooking.getQldvUsername(), Constant.MANAGER_APPROVED);
                    sms.sendSms(qldv.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
                            qldv.getPhone(), typeSms);
                  }

                  //Send to old and new driver
                  String oldMessageNotify = MessageFormat.format(notifyMessage.getProperty("N110"),
                          oldBooking.getRouteType(), oldBooking.getStartPlace(), oldBooking.getTargetPlace(),
                          oldBooking.getLicensePlate(),
                          new DateUtil(oldBooking.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
                          new DateUtil(oldBooking.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase());

                  String oldMessageSms = MessageFormat.format(smsMessage.getProperty("S110"),
                          oldBooking.getRouteType(), oldBooking.getStartPlace(), oldBooking.getTargetPlace(),
                          notiInfo.getLicensePlate(),
                          new DateUtil(oldBooking.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
                          new DateUtil(oldBooking.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase());

                  //to old driver
                  VT000000EntitySmsVsNotify oldDriver = vt000000DAO.findNotifySmsByUserName(oldBooking.getDriverUsername());
                  oldDriver.setToUserName(oldBooking.getDriverUsername());
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYEE_DOIXE);
                  notification.sendNotification(oldDriver.getToUserName(), addInfo.toString(), oldMessageNotify,
                          title, Constant.TYPE_NOTIFY_MODUL03, oldDriver.getToUserName(), Constant.MANAGER_APPROVED);
                  sms.sendSms(oldDriver.getToUserId(), oldMessageSms, Constant.STATUS_NEW_SMS, oldDriver.getPhone(), typeSms);

                  // to new driver
//                  VT000000EntitySmsVsNotify inforNotifySmsRequester = vt000000DAO.findNotifySmsByUserName(obj.getUserName());
//                  inforNotifySmsRequester.setToUserName(obj.getUserName());
//                  messageNotify = MessageFormat.format(notifyMessage.getProperty("N18"),
//                          oldBooking.getEmpName(), oldBooking.getEmpPhone(), oldBooking.getEmpUnitName(),
//                          new DateUtil(oldBooking.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
//                          new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
//                          infoCarRoute.getRoute().toUpperCase(), infoCarRoute.getStartPlace().toUpperCase(),
//                          infoCarRoute.getTargetPlace().toUpperCase());
//
//
//                  addInfo = new AdditionalInfoBase();
//                  addInfo.setId(obj.getBookCarId());
//                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYEE_DOIXE);
//                  notification.sendNotification(inforNotifySmsDriver.getToUserName(), addInfo.toString(), messageNotify,
//                          title, Constant.TYPE_NOTIFY_MODUL03, obj.getUserName(), Constant.MANAGER_APPROVED);
//
//                  messageSms = MessageFormat.format(smsMessage.getProperty("S18"),
//                          oldBooking.getEmpName(), oldBooking.getEmpPhone(), oldBooking.getEmpUnitName(),
//                          new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
//                          new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(),
//                          infoCarRoute.getRoute().toUpperCase(), infoCarRoute.getStartPlace().toUpperCase(),
//                          infoCarRoute.getTargetPlace().toUpperCase());
//                  typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
//                  sms.sendSms(inforNotifySmsDriver.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
//                          inforNotifySmsDriver.getPhone(), typeSms);
                }
              }
            } catch (Exception e) {
              logger.error(e.getMessage(), e);
            }
          }
        };
        notiThread.start();
        response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
      }

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    logger.info("********************* END EXECUTE APIVT030008_03 ***********************");
    return response;
  }
  
  public void sendSmsNotiToDriverMergeCar(String messToDriver, Date startDate, Date endDate, String route, String startPlace, String targetPlace, String phone, String toUserName, Integer userId) {
	  try {
		  String messageNotify = "";
		  String messageSms = "";
		  String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
	      messageNotify = MessageFormat.format(notifyMessage.getProperty("N46"),
	    		  messToDriver,
	              new DateUtil(startDate).toString(DateUtil.FORMAT_DATE).toUpperCase(),
	              new DateUtil(endDate).toString(DateUtil.FORMAT_DATE).toUpperCase(),
	              route.toUpperCase(), startPlace.toUpperCase(),
	              targetPlace.toUpperCase());
	
	      AdditionalInfoBase addInfo = new AdditionalInfoBase();
	      addInfo = new AdditionalInfoBase();
	      addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYEE_DOIXE);
	      notification.sendNotification(toUserName, addInfo.toString(), messageNotify,
	              title, Constant.TYPE_NOTIFY_MODUL03, toUserName, Constant.MANAGER_APPROVED);
	
	      messageSms = MessageFormat.format(smsMessage.getProperty("S121"),
	    		  messToDriver,
	    		  new DateUtil(startDate).toString(DateUtil.FORMAT_DATE).toUpperCase(),
	    		  new DateUtil(endDate).toString(DateUtil.FORMAT_DATE).toUpperCase(),
	    		  route.toUpperCase(),  startPlace.toUpperCase(),
	    		  targetPlace.toUpperCase());
	      String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
	      sms.sendSms(userId, messageSms, Constant.STATUS_NEW_SMS,
	    		  phone, typeSms);
	  } catch(Exception e) {
		  logger.error(e.getMessage(), e);
	  }
  }
}
