package com.viettel.vtnet360.driving.service;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.driving.dto.AddingTimeNotiInfo;
import com.viettel.vtnet360.driving.dto.CarbookingNoti;
import com.viettel.vtnet360.driving.dto.SearchCarBooking;
import com.viettel.vtnet360.driving.dto.SearchData;
import com.viettel.vtnet360.driving.entity.Cars;
import com.viettel.vtnet360.driving.entity.DrivesSquad;
import com.viettel.vtnet360.driving.entity.ListCarBooking;
import com.viettel.vtnet360.driving.entity.ListTrip;
import com.viettel.vtnet360.driving.entity.Manager;
import com.viettel.vtnet360.driving.entity.Place;
import com.viettel.vtnet360.driving.request.dao.BookingDAO;
import com.viettel.vtnet360.driving.request.dao.DrivingDAO;
import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntitySmsVsNotify;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityAdditionalInfo;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Unit;
import com.viettel.vtnet360.vt02.vt020000.service.VT020000Service;
import com.viettel.vtnet360.vt03.vt030000.entity.Employee;
import com.viettel.vtnet360.vt03.vt030006.entity.VT030006UpdateBookCar;
import com.viettel.vtnet360.vt03.vt030006.service.VT030006Service;
import com.viettel.vtnet360.vt03.vt030008.dao.VT030008DAO;
import com.viettel.vtnet360.vt03.vt030008.entity.VT030008DriveCarInfo;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;

@Service
public class DrivingServiceImpl implements DrivingService {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private DrivingDAO drivingDAO;
	
	@Autowired
	private BookingDAO bookingDAO;
	
	@Autowired
  private VT000000DAO vt000000DAO;
	
	@Autowired
  private VT030008DAO vt030008dao;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
  private VT020000Service vt020000Service;
	
	@Autowired
  private VT030006Service vt030006Service;

  @Autowired
  private Notification notification;
  
  @Autowired
  private Properties notifyMessage;
  
  @Autowired
  private Properties smsMessage;

  @Autowired
  private Sms sms;

  @Override
  @Transactional(readOnly = true)
	public void createNotifyForMobileTest(String username) {
		String carBookingId = bookingDAO.getCarbookingId();
		AdditionalInfoBase notiInfo = new AdditionalInfoBase(carBookingId, Constant.PMQT_ROLE_EMPLOYYEE);
		notification.sendNotification(username, notiInfo.toString(),
				"Mời Đ/c đánh giá và cho ý kiến danh gia chuyen di", "ĐÁNH GIÁ DAT XE", 31, username, 0);
	}
  
  @Override
  @Transactional(readOnly = true)
  public CarbookingNoti getCarbookingById(SearchCarBooking config) {
    return bookingDAO.getBookingNoti(config);
  }
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase searchData(String searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT030014ListCondition> list = null;

		try {
			list = drivingDAO.searchData(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase searchManagerUnit(String searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT030014ListCondition> list = null;

		try {
			list = drivingDAO.searchManagerUnit(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase searchManagerChief(String searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT030014ListCondition> list = null;

		try {
			list = drivingDAO.searchManagerChief(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase searchPlaceStart(SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Place> list = null;

		try {
			list = drivingDAO.searchPlaceStart(searchData);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase searchUnit(int searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		String string;

		try {
			string = drivingDAO.searchUnit(searchDTO);
			resp.setData(string);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchFullName(int searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		String string;

		try {
			string = drivingDAO.searchFullName(searchDTO);
			resp.setData(string);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchPhoneNumber(int searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		String string;

		try {
			string = drivingDAO.searchPhoneNumber(searchDTO);
			resp.setData(string);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchEmail(int searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		String string;

		try {
			string = drivingDAO.searchEmail(searchDTO);
			resp.setData(string);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchDriveName(String searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
		    List<Manager> list = drivingDAO.searchDriveName(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchLicensePlate(String searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<String> list = drivingDAO.searchLicensePlate(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findAllListTrip(VT030014ListCondition condition, Collection<GrantedAuthority> listRole) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT030014ListCondition> trips = new ArrayList<>();
		
		if (!listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
        && !listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))
        && !listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP))
        && listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_DOIXE))) {
      int countSquadByUsername = bookingDAO.countSquadByUsername(condition.getLoginUserName());
      if(countSquadByUsername == 0) {
        reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        reb.setData(trips);
        return reb;
      }
    }
		
		try {
			logger.info("**************** Start get list car booking ****************");
			if (setUpCondition(condition, listRole)) {
			  
			  if (!listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
            && (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))
            || listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP)))) {
			    condition.setLoginRole(Constant.PMQT_ROLE_MANAGER);
			    
			    if(condition.getUnitIdManager() != null) {
	          VT020000Unit unit = vt020000Service.getUnitParent(condition.getUnitIdManager());
	          if(unit != null) condition.setUnitIdManager(unit.getUnitId().toString());
	        }
			  }
			  
				trips = drivingDAO.findAllListTrip(condition);
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

	public boolean setUpCondition(VT030014ListCondition condition, Collection<GrantedAuthority> listRole) throws Exception {
		boolean isRole = false;
		try {
			if (listRole != null && !listRole.isEmpty()) {
				if (condition.getDateStart() != null) {
					Date startDate = condition.getDateStart();
					Calendar cal = GregorianCalendar.getInstance();
					cal.setTime(startDate);
					String newDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DAY_OF_MONTH) + " 00:00:00";
					condition.setDateStart(new SimpleDateFormat(Constant.DATE_FORMAT).parse(newDate));
				}

				if (condition.getDateEnd() != null) {
					Date endDate = condition.getDateEnd();
					Calendar cal = GregorianCalendar.getInstance();
					cal.setTime(endDate);
					String newDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DAY_OF_MONTH) + " 23:59:59";
					condition.setDateEnd(new SimpleDateFormat(Constant.DATE_FORMAT).parse(newDate));
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

	@Override
	public ResponseEntityBase updateCarDetails(ListTrip listTrip, Collection<GrantedAuthority> listRole, String username) {
	  ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
	  String startLong = String.valueOf(listTrip.getDateStart().getTime());
	  startLong = startLong.substring(0, startLong.length() - 3) + "000";
	  listTrip.setDateStart(new Date(Long.parseLong(startLong)));

	  String endLong = String.valueOf(listTrip.getDateEnd().getTime());
	  endLong = endLong.substring(0, endLong.length() - 3) + "000";
	  listTrip.setLoginUserName(username);
	  
	  AddingTimeNotiInfo oldBooking = bookingDAO.getCarbookingById(listTrip.getCarBookingId());
	  if(listTrip.getDateEnd().compareTo(oldBooking.getDateEnd()) <= 0) {
	    System.out.println("You should be here");
	    drivingDAO.updateBooking(listTrip);
	    
	    if(oldBooking.getStatus() == 4) {
	      Thread notiThread = new Thread() {
	        public void run() {
	          createNewSMSAndNoti(oldBooking, listTrip, oldBooking.getAppoverQltt(), listTrip.getDateStart(), listTrip.getDateEnd());
	        }
	      };
	      notiThread.start();
	    }
	    return resp;
	  }
	  
	  int checkDuplicateTime = bookingDAO.checkUpdateMoreTime(listTrip);
	  if(checkDuplicateTime <= 0) {
	    drivingDAO.updateCarDetails(listTrip);

	    if(oldBooking.getStatus() == 4) {
	      VT030008DriveCarInfo driverCarInfo = new VT030008DriveCarInfo();
	      driverCarInfo.setCarId(oldBooking.getCarId());
	      driverCarInfo.setUserName(oldBooking.getDriverUsername());
	      driverCarInfo.setUserAssigner(username);
	      driverCarInfo.setProcessStatus(0);

	      vt030008dao.updateStateDriveCar(driverCarInfo);
	      vt030008dao.updateStateCar(driverCarInfo);
	      vt030008dao.updateStateDriver(driverCarInfo);
	      bookingDAO.updateCarBooking(listTrip.getCarBookingId());
	    }

	    Thread notiThread = new Thread() {
	      public void run() {
	        createSMSAndNoti(oldBooking, listTrip, oldBooking.getAppoverQltt());
	      }
	    };
	    notiThread.start();
	  } else {
	    resp.setData(null);
	    resp.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);
	  }

	  return resp;
	}

	@Override
	public ResponseEntityBase updateRejectCarDetails(ListTrip listTrip) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
		  AddingTimeNotiInfo oldBooking = bookingDAO.getCarbookingById(listTrip.getCarBookingId());
			drivingDAO.updateRejectCarDetails(listTrip);
			if(oldBooking.getStatus() == 1) {
        Thread notiThread = new Thread() {
          public void run() {
            DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
            VT010000EntityAdditionalInfo addInfomation = new VT010000EntityAdditionalInfo();
            addInfomation.setId(listTrip.getCarBookingId());
            
            VT000000EntitySmsVsNotify leaderNotifySmsManager = vt000000DAO.findNotifySmsByUserName(oldBooking.getSquadLeadUsername());
            leaderNotifySmsManager.setToUserName(oldBooking.getSquadLeadUsername());

            //N118 = Đ/c {0} có yêu cầu sử dụng {1} {2} cho hành trình từ {3} – {4} đã HỦY yêu cầu. Lý do: NHÂN VIÊN HỦY
            
            String message = MessageFormat.format(notifyMessage.getProperty("N118"), 
                oldBooking.getEmpName(), oldBooking.getCarType(), oldBooking.getSeat(),
                dateformat.format(oldBooking.getDateStart()), dateformat.format(oldBooking.getDateEnd()));

            //send to quan ly doi xe
            addInfomation.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_DOIXE);
            notification.sendNotification(leaderNotifySmsManager.getToUserName(), addInfomation.toString(),
                message, title, Constant.TYPE_NOTIFY_MODUL03, oldBooking.getSquadLeadUsername(), 1);
            sms.sendSms(leaderNotifySmsManager.getToUserId(), message, Constant.STATUS_NEW_SMS, leaderNotifySmsManager.getPhone(), title);

            if(oldBooking.getQldvUsername() != null) {
              VT000000EntitySmsVsNotify carDriverInfo = vt000000DAO.findNotifySmsByUserName(oldBooking.getQldvUsername());
              carDriverInfo.setToUserName(oldBooking.getQldvUsername());
              //send to driver 
              notification.sendNotification(carDriverInfo.getToUserName(), addInfomation.toString(),
                  message, title, Constant.TYPE_NOTIFY_MODUL03, oldBooking.getQldvUsername(), 1);
              sms.sendSms(carDriverInfo.getToUserId(), message, Constant.STATUS_NEW_SMS, carDriverInfo.getPhone(), title);
            }
            
            if(oldBooking.getQlttUsername() != null) {
              VT000000EntitySmsVsNotify carDriverInfo = vt000000DAO.findNotifySmsByUserName(oldBooking.getQlttUsername());
              carDriverInfo.setToUserName(oldBooking.getQlttUsername());
              //send to driver 
              notification.sendNotification(carDriverInfo.getToUserName(), addInfomation.toString(),
                  message, title, Constant.TYPE_NOTIFY_MODUL03, oldBooking.getQlttUsername(), 1);
              sms.sendSms(carDriverInfo.getToUserId(), message, Constant.STATUS_NEW_SMS, carDriverInfo.getPhone(), title);
            }
            
          }
        };
        notiThread.start();
      }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase insertCarDetails(ListTrip listTrip) {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			int checklistTrip = drivingDAO.insertCarDetails(listTrip);
			if (checklistTrip > 0) {
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return reb;

	}

	@Override
	public ResponseEntityBase updateNewCarDetails(ListTrip listTrip) {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			int checklistTrip = drivingDAO.updateNewCarDetails(listTrip);
			if (checklistTrip > 0) {
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return reb;
	}

	@Override
	public ResponseEntityBase updateCompleteCarDetails(ListTrip listTrip) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			drivingDAO.updateCompleteCarDetails(listTrip);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase updateRenewCarDetails(ListTrip listTrip) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			drivingDAO.updateRenewCarDetails(listTrip);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchApproverQltt(String searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<String> list = drivingDAO.searchApproverQltt(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchApproverQldv(String searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<String> list = drivingDAO.searchApproverQldv(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchApproverQlcvp(String searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<String> list = drivingDAO.searchApproverQlcvp(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchPlaceName(SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<VT030014ListCondition> place = drivingDAO.searchPlaceName(searchData);
			resp.setData(place);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	

	@Override
	public ResponseEntityBase searchAllStatusCar() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<Integer> list = drivingDAO.searchAllStatusCar();
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase updateCarJourney(ListTrip listTrip) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			drivingDAO.updateCarJourney(listTrip);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase updateCarRoute(ListTrip listTrip) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			drivingDAO.updateCarRoute(listTrip);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchDriveSquadName(SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<DrivesSquad> list = drivingDAO.searchDriveSquadName(searchData);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchLicensePlate(SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<Cars> list = drivingDAO.searchLicensePlate(searchData);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchAllStatusDrive() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<Integer> list = drivingDAO.searchAllStatusDrive();
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchAllStatusProcessDrive() {
	  ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
	  
	  try {
	    List<Integer> list = drivingDAO.searchAllStatusProcessDrive();
	    resp.setData(list);
	  } catch (Exception e) {
	    resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
	    logger.error(e.getMessage(), e);
	    throw (e);
	  }
	  return resp;
	}
	@Override
	public ResponseEntityBase getStatusCar() {
	  ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
	  
	  try {
	    List<Integer> list = drivingDAO.getStatusCar();
	    resp.setData(list);
	  } catch (Exception e) {
	    resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
	    logger.error(e.getMessage(), e);
	    throw (e);
	  }
	  return resp;
	}

	@Override
	public ResponseEntityBase searchSuggestionLicensePlate(SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<Cars> list = drivingDAO.searchSuggestionLicensePlate(searchData);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getPlaceByAll() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<VT030014ListCondition> list = drivingDAO.getPlaceByAll();
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public String getPhoneByUserName(String userName) {
		String phone = null;
		
		try {
			phone = drivingDAO.getPhoneByUserName(userName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return phone;
	}

	@Override
	public Employee getPhoneAndUserBySquadLeader(String driverSquadId) {
		Employee phoneAndUser = null;
		
		try {
			phoneAndUser = drivingDAO.getPhoneBySquadLeader(driverSquadId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return phoneAndUser;
	}
	
	@Override
	public ResponseEntityBase getQlttByUserName(SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT030014ListCondition> list = null;

		try {
			list = drivingDAO.getQlttByUserName(searchData);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getQldvByUserName(SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT030014ListCondition> list = null;

		try {
			list = drivingDAO.getQldvByUserName(searchData);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getQlcvpByUserName(SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT030014ListCondition> list = null;

		try {
			list = drivingDAO.getQlcvpByUserName(searchData);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchRoueType(String searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		String list = null;

		try {
			list = drivingDAO.searchRoueType(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getRoleByUser(String searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		String list = null;

		try {
			list = drivingDAO.getRoleByUser(searchDTO);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase updateCarRefuse(ListTrip listTrip) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			drivingDAO.updateCarRefuse(listTrip);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase updateCarApprove(ListTrip listTrip) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			drivingDAO.updateCarApprove(listTrip);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase updateListCarApprove(ListCarBooking listCarBooking, Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
		  VT030006UpdateBookCar updating = new VT030006UpdateBookCar();
		  updating.setUserName(listCarBooking.getLoginUsername());
		  updating.setStatus(1);
		  updating.setListId(listCarBooking.getListCar());
		  vt030006Service.updateRequest(updating, roleList);
			//drivingDAO.updateListCarApprove(listCarBooking);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;

	}

	@Override
	public ResponseEntityBase findAllListTripById(VT030014ListCondition condition, Collection<GrantedAuthority> listRole)
			throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		List<VT030014ListCondition> trips = new ArrayList<>();

		try {
			logger.info("**************** Start get list car booking ****************");

			if (setUpCondition(condition, listRole)) {
				trips = drivingDAO.findAllListTripById(condition);
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

	@Override
	public ResponseEntityBase searchPlaceStartAll() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<VT030014ListCondition> place = drivingDAO.searchPlaceStartAll();
			resp.setData(place);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getQltt() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<VT030014ListCondition> place = drivingDAO.getQltt();
			resp.setData(place);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getQldv() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<VT030014ListCondition> place = drivingDAO.getQldv();
			resp.setData(place);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getCvp() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<VT030014ListCondition> place = drivingDAO.getCvp();
			resp.setData(place);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchDriveNameIsFree(ListTrip listTrip) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<ListTrip> list = drivingDAO.searchDriveNameIsFree(listTrip);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase searchCarsIsFree(ListTrip listTrip) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

		try {
			List<ListTrip> list = drivingDAO.searchCarsIsFree(listTrip);
			resp.setData(list);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase updateCarRefuseOrder(ListTrip listTrip) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			drivingDAO.updateCarRefuseOrder(listTrip);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase updateCarOrder(ListTrip listTrip) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			drivingDAO.updateDriveCarApprove(listTrip);
			drivingDAO.updateCarOrderApprove(listTrip);
			drivingDAO.updateBookingCarApprove(listTrip);
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		return resp;
	}
	
	private void createNewSMSAndNoti(AddingTimeNotiInfo oldBooking, ListTrip listTrip, String userName, Date newStart, Date newEnd) {
	  DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
    VT010000EntityAdditionalInfo addInfomation = new VT010000EntityAdditionalInfo();
    addInfomation.setId(listTrip.getCarBookingId());
	  
	  VT000000EntitySmsVsNotify leaderNotifySmsManager = vt000000DAO.findNotifySmsByUserName(oldBooking.getSquadLeadUsername());
    leaderNotifySmsManager.setToUserName(oldBooking.getSquadLeadUsername());

    String message = MessageFormat.format(notifyMessage.getProperty("N120"), 
        oldBooking.getEmpName(), oldBooking.getEmpUnitName(), 
        dateformat.format(oldBooking.getDateStart()), dateformat.format(oldBooking.getDateEnd()),
        dateformat.format(newStart), dateformat.format(newEnd),
        oldBooking.getStartPlace(), oldBooking.getTargetPlace(),
        oldBooking.getLicensePlate());

    //send to quan ly doi xe
    addInfomation.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_DOIXE);
    notification.sendNotification(leaderNotifySmsManager.getToUserName(), addInfomation.toString(),
        message, title, Constant.TYPE_NOTIFY_MODUL03, oldBooking.getSquadLeadUsername(), 1);
    sms.sendSms(leaderNotifySmsManager.getToUserId(), message, Constant.STATUS_NEW_SMS, leaderNotifySmsManager.getPhone(), title);

    if(oldBooking.getDriverUsername() != null) {
      VT000000EntitySmsVsNotify carDriverInfo = vt000000DAO.findNotifySmsByUserName(oldBooking.getDriverUsername());
      carDriverInfo.setToUserName(oldBooking.getDriverUsername());
      //send to driver 
      notification.sendNotification(carDriverInfo.getToUserName(), addInfomation.toString(),
          message, title, Constant.TYPE_NOTIFY_MODUL03, oldBooking.getDriverUsername(), 1);
      sms.sendSms(carDriverInfo.getToUserId(), message, Constant.STATUS_NEW_SMS, carDriverInfo.getPhone(), title);
    }
	}
	
	
	private void createSMSAndNoti(AddingTimeNotiInfo oldBooking, ListTrip listTrip, String userName) {
	  if(oldBooking.getStatus() == 10) return;
	  
	  DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	  String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
	  VT010000EntityAdditionalInfo addInfomation = new VT010000EntityAdditionalInfo();
    addInfomation.setId(listTrip.getCarBookingId());
	  
    String notiMessage = MessageFormat.format(notifyMessage.getProperty("N102"), 
        oldBooking.getEmpName(),
        dateformat.format(oldBooking.getDateStart()), dateformat.format(oldBooking.getDateEnd()),
        dateformat.format(listTrip.getDateStart()), dateformat.format(listTrip.getDateEnd()));

    if(oldBooking.getAppoverQltt() != null) {
      VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(oldBooking.getAppoverQltt());
      notiUser.setToUserName(oldBooking.getAppoverQltt());
      addInfomation.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
      bookingService.sendNotiAndSms(notiUser, notiMessage, title, addInfomation);
    }
    
	  if(oldBooking.getStatus() == 4) {
	    VT000000EntitySmsVsNotify leaderNotifySmsManager = vt000000DAO.findNotifySmsByUserName(oldBooking.getSquadLeadUsername());
	    leaderNotifySmsManager.setToUserName(oldBooking.getSquadLeadUsername());

	    VT000000EntitySmsVsNotify carDriverInfo = vt000000DAO.findNotifySmsByUserName(oldBooking.getDriverUsername());
	    carDriverInfo.setToUserName(oldBooking.getDriverUsername());

	    String leaderNoti = MessageFormat.format(notifyMessage.getProperty("N114"), 
	        carDriverInfo.getCreateUser(),
	        dateformat.format(oldBooking.getDateStart()), dateformat.format(oldBooking.getDateEnd()),
	        oldBooking.getLicensePlate());

	    String leaderSms = MessageFormat.format(smsMessage.getProperty("S114"),
	        carDriverInfo.getCreateUser(), 
	        dateformat.format(oldBooking.getDateStart()), dateformat.format(oldBooking.getDateEnd()),
	        oldBooking.getLicensePlate());

	    //send to quan ly doi xe
	    addInfomation.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_DOIXE);
	    notification.sendNotification(leaderNotifySmsManager.getToUserName(), addInfomation.toString(),
	        leaderNoti, title, Constant.TYPE_NOTIFY_MODUL03, oldBooking.getSquadLeadUsername(), 1);
	    sms.sendSms(leaderNotifySmsManager.getToUserId(), leaderSms, Constant.STATUS_NEW_SMS, leaderNotifySmsManager.getPhone(), title);

	    //send to driver 
	    addInfomation.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYEE_DOIXE);
	    String carMessage = MessageFormat.format(notifyMessage.getProperty("N117"), 
	        oldBooking.getEmpName(), oldBooking.getEmpPhone(),
	        dateformat.format(oldBooking.getDateStart()), dateformat.format(oldBooking.getDateEnd()),
	        oldBooking.getLicensePlate());

	    String carMessageSms = MessageFormat.format(smsMessage.getProperty("S117"),
	        oldBooking.getEmpName(), oldBooking.getEmpPhone(),
	        dateformat.format(oldBooking.getDateStart()), dateformat.format(oldBooking.getDateEnd()),
	        oldBooking.getLicensePlate());

	    notification.sendNotification(carDriverInfo.getToUserName(), addInfomation.toString(),
	        carMessage, title, Constant.TYPE_NOTIFY_MODUL03, oldBooking.getDriverUsername(), 1);
	    sms.sendSms(carDriverInfo.getToUserId(), carMessageSms, Constant.STATUS_NEW_SMS, carDriverInfo.getPhone(), title);
	  }
  }

}
