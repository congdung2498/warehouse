package com.viettel.vtnet360.vt02.vt020006.service;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.kitchen.dto.Dish;
import com.viettel.vtnet360.kitchen.menu.dao.LunchDAO;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.InputValidate;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002Kitchen;
import com.viettel.vtnet360.vt02.vt020003.dao.VT020003DAO;
import com.viettel.vtnet360.vt02.vt020006.dao.VT020006DAO;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006ContentSms;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006InfoToCheckKitchenExist;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006InfoToFindTotalLunchDate;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006InfoToGetListLunchDate;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006InfoToInsertDailyMeals;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006InfoTofindDayOff;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006Lunch;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006LunchDate;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006LunchInfo;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006LunchInforEachMonth;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006LunchReport;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006ParamRequest;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006TotalLunchDate;
import com.viettel.vtnet360.vt02.vt020010.dao.VT020010DAO;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010InfoToFindUnit;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010ReportDetail;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010RequestParam;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010Unit;

/**
 * Class serviceImpl for screen VT020006 : set lunch time
 * 
 * @author DuyNK 17/09/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT020006ServiceImpl implements VT020006Service {
	private final Logger logger = Logger.getLogger(this.getClass());
	private InputValidate validate = new InputValidate();

	@Autowired
	private VT020006DAO vt020006DAO;

	@Autowired
	private VT020010DAO vt020010DAO;

	@Autowired
	private VT020003DAO vt020003DAO;
	
	@Autowired
	Properties smsMessage;

	@Autowired
	Sms sms;
	
	@Autowired
  private LunchDAO      lunchDao;

	/**
	 * Select days of week periodic and list date of lunch
	 * 
	 * @param info
	 * @return ResponseEntityBase(status, VT020006LunchReport)
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase getInfoLunchDate(VT020006InfoToGetListLunchDate info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		VT020006LunchReport report = new VT020006LunchReport();
		List<Integer> periodic = null;
		List<VT020006LunchDate> listLunchDate = null;
		try {
			info.setQuantity(Constant.QUANTITY_NONE);
			info.setHasBooking(Constant.HAS_BOOKING_DEFAULT);
			info.setIsPeriodic(Constant.PERIODIC_DEFAULT);
			// find periodic(day of week)
			periodic = vt020006DAO.findPeriodic(info);
			periodic = convertDayOfWeekDBToJava(periodic);
			// find kitchenID periodic
			List<VT020002Kitchen> kitchens = vt020006DAO.findKitchenIDPeriodic(info);
			// find list date hasBooking
			listLunchDate = vt020006DAO.findListDate(info);
			// find dayOff in this month
			VT020006InfoTofindDayOff infoToFindDayOff = new VT020006InfoTofindDayOff(info.getMonth(), info.getYear(),
					Constant.STATUS_ACTIVE);
			List<Date> listDayOff = vt020006DAO.findDayOffByMonth(infoToFindDayOff);
			// if dayOff => quantity = -1 (code order by mobile)
			if (!listDayOff.isEmpty()) {
				for (int i = 0; i < listDayOff.size(); i++) {
					listLunchDate.add(new VT020006LunchDate(listDayOff.get(i), -1));
				}
			}
			report.setPeriodic(periodic);
			report.setListLunchDate(listLunchDate);
			if (kitchens.size() == 1) {
				report.setKitchenID(kitchens.get(0).getKitchenID());
				report.setKitchenName(kitchens.get(0).getKitchenName());
			}
			resp.setData(report);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	/**
	 * Select info of menu in a date in a kitchenID
	 * 
	 * @param lunch
	 * @return ResponseEntityBase(status, listDishName)
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase getInfoMenu(VT020006Lunch lunch) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Dish> listDishName = null;
		// validate input
		if (StringUtils.isBlank(lunch.getKitchenID()) || lunch.getLunchDate() == null) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			return resp;
		}
		// convert dayOfMenu to Date
		lunch.setLunchDate(validate.validateDate(lunch.getLunchDate()));
		try {
			listDishName = vt020006DAO.findMenuInfo(lunch);
			resp.setData(listDishName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	/**
	 * Select info of one date to update
	 * 
	 * @param lunch
	 * @return ResponseEntityBase(status, VT020006LunchInfo)
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase getInfoOneLunch(VT020006Lunch lunch) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		VT020006LunchInfo lunchInfo = new VT020006LunchInfo();
		List<Dish> listDish = null;
		// convert dayOfMenu to Date
		lunch.setLunchDate(validate.validateDate(lunch.getLunchDate()));
		try {
			// check lunch date existed in db. if not exist => return null
			int checkLunch = vt020006DAO.checkLunchDate(lunch);
			if (checkLunch == Constant.NONE_EXIST_RECORD) {
				resp.setData(null);
				return resp;
			} else {
				lunchInfo = vt020006DAO.findLunchInfo(lunch);
				VT020006Lunch infoToFindMenu = new VT020006Lunch(null, lunch.getLunchDate(), 0, 0, lunchInfo.getKitchenID(), 0, null);
				listDish = vt020006DAO.findMenuInfo(infoToFindMenu);
				lunchInfo.setListDishName(listDish);
				resp.setData(lunchInfo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	/**
	 * delete lunch date
	 * 
	 * @param param
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase deleteLunch(VT020006ParamRequest param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// get system date
			Calendar sysDate = Calendar.getInstance();
			// if sysDate > 16:30 => just allow change for sysDate + 2
			if (sysDate.get(Calendar.HOUR_OF_DAY) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
					|| (sysDate.get(Calendar.HOUR_OF_DAY) == Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
							&& sysDate.get(Calendar.MINUTE) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_MINUTE)) {
				sysDate.add(Calendar.DAY_OF_MONTH, 2);
			} else {
				sysDate.add(Calendar.DAY_OF_MONTH, 1);
			}
			// validate input
			if (param == null || param.getListPeriodic() == null || param.getListPeriodic().isEmpty()) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			} else {
				sysDate.setTime(validate.validateDate(sysDate.getTime()));
	
				// get lunch date for 2 month next need to delete
				List<Date> listLunchDate = new ArrayList<>();
				List<Date> listDayOff = vt020006DAO.findDayOff(Constant.STATUS_ACTIVE);
				for (int j = 0; j <= 1; j++) {
					Calendar dayOfMonth = Calendar.getInstance();
					dayOfMonth.add(Calendar.MONTH, j);
					dayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
	
					Calendar lastDayOfMonth = Calendar.getInstance();
					lastDayOfMonth.add(Calendar.MONTH, j + 1);
					lastDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
					lastDayOfMonth.add(Calendar.DATE, -1);
	
					// for 1 => last day of month to check. if this day of week is periodic = save
					// to data base
					for (int k = 1; k <= lastDayOfMonth.get(Calendar.DAY_OF_MONTH); k++) {
						dayOfMonth.set(Calendar.DAY_OF_MONTH, k);
						int dayOfWeek = dayOfMonth.get(Calendar.DAY_OF_WEEK);
						// check dayOfWeek in list periodic and not in list day off
						if (param.getListPeriodic().contains(dayOfWeek)
								&& !listDayOff.contains(validate.validateDate(dayOfMonth.getTime()))) {
							// check this date allowed or not
							if (validate.validateDate(dayOfMonth.getTime())
									.compareTo(validate.validateDate(sysDate.getTime())) >= 0) {
								listLunchDate.add(validate.validateDate(dayOfMonth.getTime()));
							}
						}
					}
				}
				if(listLunchDate != null && !listLunchDate.isEmpty()) {
					VT020006Lunch lunch = new VT020006Lunch();
					lunch.setUserName(param.getUserName());
					lunch.setListDateLunch(listLunchDate);
					// delete record > sysDate follow listPeriodic
					vt020006DAO.deleteLunch(lunch);
				}
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	/**
	 * insert lunch date
	 * 
	 * @param param
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase insertLunch(VT020006ParamRequest param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// check kitchenID existed or not
			int checkKitchenID = vt020006DAO.checkKitchenExisted(
					new VT020006InfoToCheckKitchenExist(param.getKitchenID(), Constant.STATUS_ACTIVE));
			if (checkKitchenID == Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			} else {
				// get system date
				Calendar sysDate = Calendar.getInstance();
				// if sysDate > 16:30 => just allow change for sysDate + 2
				if (sysDate.get(Calendar.HOUR_OF_DAY) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
						|| (sysDate.get(Calendar.HOUR_OF_DAY) == Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
								&& sysDate.get(Calendar.MINUTE) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_MINUTE)) {
					sysDate.add(Calendar.DAY_OF_MONTH, 2);
				} else {
					sysDate.add(Calendar.DAY_OF_MONTH, 1);
				}
				// validate input
				if (param == null || param.getListPeriodic().isEmpty()) {
					resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
					return resp;
				} else {
					sysDate.setTime(validate.validateDate(sysDate.getTime()));
					// delete all old record > sysDate
					VT020006Lunch lunchDelete = new VT020006Lunch(param.getUserName(), sysDate.getTime(), 0, 0, null, 0, null);
					vt020006DAO.deleteAllLunch(lunchDelete);
					// update all record(in this month) < sysDate => IS_PERIODIC = 0
					vt020006DAO.updatePeriodicToInactive(lunchDelete);
					// insert new record
					// set lunch date for 2 month next
					List<Date> listLunchDate = new ArrayList<>();
					List<Date> listDayOff = vt020006DAO.findDayOff(Constant.STATUS_ACTIVE);
					for (int j = 0; j <= 1; j++) {
						Calendar dayOfMonth = Calendar.getInstance();
						dayOfMonth.add(Calendar.MONTH, j);
						dayOfMonth.set(Calendar.DAY_OF_MONTH, 1);

						Calendar lastDayOfMonth = Calendar.getInstance();
						lastDayOfMonth.add(Calendar.MONTH, j + 1);
						lastDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
						lastDayOfMonth.add(Calendar.DATE, -1);

						// for 1 => last day of month to check. if this day of week is periodic = save
						// to data base
						for (int k = 1; k <= lastDayOfMonth.get(Calendar.DAY_OF_MONTH); k++) {
							dayOfMonth.set(Calendar.DAY_OF_MONTH, k);
							int dayOfWeek = dayOfMonth.get(Calendar.DAY_OF_WEEK);
							// check dayOfWeek in list periodic and not in list day off
							if (param.getListPeriodic().contains(dayOfWeek) && !listDayOff.contains(validate.validateDate(dayOfMonth.getTime()))) {
								// check this date allowed or not
								if (validate.validateDate(dayOfMonth.getTime()).compareTo(validate.validateDate(sysDate.getTime())) >= 0) {
									listLunchDate.add(dayOfMonth.getTime());
								}
							}
						}
					}
					VT020006Lunch lunch = new VT020006Lunch(param.getUserName(), null, Constant.QUANTITY_DEFAULT,
							Constant.HAS_BOOKING_DEFAULT, param.getKitchenID(), param.getUnitId(), Constant.PERIODIC_DEFAULT,
							listLunchDate);
					// insert to db
					vt020006DAO.insertLunchDate(lunch);
				}
			}
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	/**
	 * update lunch date
	 * 
	 * @param List<VT020006Lunch>
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase updateLunch(VT020006ParamRequest param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<Date> listLunchDate = new ArrayList<>();
		listLunchDate.add(param.getLunchDate());
		if(param.getQuantity() <= 0) param.setHasBooking(0);
		VT020006Lunch lunch = new VT020006Lunch(param.getUserName(), param.getLunchDate(), param.getQuantity(),
				param.getHasBooking(), param.getKitchenID(), param.getUnitId(), 0, listLunchDate);
		try {
			// check day update not in day off config
			List<Date> listDayOff = vt020006DAO.findDayOff(Constant.STATUS_ACTIVE);
			if (listDayOff.contains(validate.validateDate(param.getLunchDate()))) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
			// check kitchenID existed or not
			int checkKitchenID = vt020006DAO.checkKitchenExisted(
					new VT020006InfoToCheckKitchenExist(lunch.getKitchenID(), Constant.STATUS_ACTIVE));
			if (checkKitchenID == Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
			// get system date
			Calendar sysDate = Calendar.getInstance();
			// convert date to calendar for comparing
			Calendar lunchDate = Calendar.getInstance();
			lunchDate.setTime(lunch.getLunchDate());
			// info to check limited change number of lunchDate
			int totalOld;
			int totalNow;
			int quantityOld;
			int quantityNow = param.getQuantity();
			// if lunchDate = today
			if (validate.validateDate(lunchDate.getTime()).compareTo(validate.validateDate(sysDate.getTime())) == 0) {
				// if today <= 8h40 => check limit change number of lunchDate(10%) && update
				// if today > 8h40 => no update
				if (sysDate.get(Calendar.HOUR_OF_DAY) < Constant.TIME_CHANGE_ORDER_LUNCH_DATE_HOUR
						|| (sysDate.get(Calendar.HOUR_OF_DAY) == Constant.TIME_CHANGE_ORDER_LUNCH_DATE_HOUR
								&& sysDate.get(Calendar.MINUTE) <= Constant.TIME_CHANGE_ORDER_LUNCH_DATE_MINUTE)) {
					// find info of old order
					VT020006Lunch infoToFindOldOrder = new VT020006Lunch();
					infoToFindOldOrder.setKitchenID(param.getKitchenID());
					infoToFindOldOrder.setLunchDate(validate.validateDate(param.getLunchDate()));
					infoToFindOldOrder.setUserName(param.getUserName());
					VT020006LunchInfo lunchInfo = vt020006DAO.findLunchInfo(infoToFindOldOrder);
					// if kitchenID old != kitchenID new => oldQuantity = 0
					if (lunchInfo != null && lunchInfo.getKitchenID().equals(param.getKitchenID())) {
						quantityOld = lunchInfo.getQuantity();
					} else {
						quantityOld = 0;
					}
					
					if(param.getHasBooking() == Constant.PERIODIC_NONE) {
					  param.setQuantity(0);
					  quantityNow = 0;
					}
					
					// find total number of lunchDate in this kitchen save in 16h30
					totalOld = vt020006DAO.findTotalLunchDateOld(
							new VT020006InfoToInsertDailyMeals(param.getKitchenID(), 0, param.getLunchDate()));
					// find total number of lunchDate in this kitchen in now
					totalNow = vt020006DAO.findTotalLunchDateNow(
							new VT020006InfoToInsertDailyMeals(param.getKitchenID(), 0, param.getLunchDate()));
					if (!checkConditionChangeNumberOfLunchDate(totalOld, totalNow, quantityOld, quantityNow)) {
						resp.setStatus(Constant.LIMITED_CHANGE_lUNCH_DATE_ERROR);
						return resp;
					} else {
						// update to db
						// convert datetime to date
						lunch.setLunchDate(validate.validateDate(lunch.getLunchDate()));
						// validate input hasBooking = (0;1), 0 <= quantity <=20
						if (lunch.getHasBooking() != Constant.HAS_BOOKING_DEFAULT) {
							lunch.setHasBooking(Constant.HAS_BOOKING_NONE);
						}
						if (lunch.getQuantity() < Constant.QUANTITY_NONE
								|| lunch.getQuantity() > Constant.QUANTITY_LIMITED) {
							lunch.setQuantity(0);
						}
						// check if date not existed in db => insert else update
						int checkLunch = vt020006DAO.checkLunchDate(lunch);
						if (checkLunch > 0) {
						  lunch.setIsPeriodic(Constant.PERIODIC_NONE);
						  vt020006DAO.updateLunch(lunch);
						} else {
							lunch.setIsPeriodic(Constant.PERIODIC_NONE);
							vt020006DAO.insertLunchDate(lunch);
						}
						resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
						return resp;
					}
				} else {
					resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
					return resp;
				}
			}

			// if lunchDate = today + 1
			sysDate = Calendar.getInstance();
			sysDate.add(Calendar.DAY_OF_MONTH, 1);
			if (validate.validateDate(lunchDate.getTime()).compareTo(validate.validateDate(sysDate.getTime())) == 0) {
				// if today > 16h30 => check limit change number of lunchDate(10%) && update
				// if today < 16h30 => update
				if (sysDate.get(Calendar.HOUR_OF_DAY) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
						|| (sysDate.get(Calendar.HOUR_OF_DAY) == Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
								&& sysDate.get(Calendar.MINUTE) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_MINUTE)) {
					// find info of old order
					VT020006Lunch infoToFindOldOrder = new VT020006Lunch();
					infoToFindOldOrder.setKitchenID(param.getKitchenID());
					infoToFindOldOrder.setLunchDate(validate.validateDate(param.getLunchDate()));
					infoToFindOldOrder.setUserName(param.getUserName());
					VT020006LunchInfo lunchInfo = vt020006DAO.findLunchInfo(infoToFindOldOrder);
					// if kitchenID old != kitchenID new => oldQuantity = 0
					if (lunchInfo != null && lunchInfo.getKitchenID().equals(param.getKitchenID())) {
						quantityOld = lunchInfo.getQuantity();
					} else {
						quantityOld = 0;
					}
					
					if(param.getHasBooking() == Constant.PERIODIC_NONE) {
            param.setQuantity(0);
            quantityNow = 0;
          }
					
					// find total number of lunchDate in this kitchen save in 16h30
					totalOld = vt020006DAO.findTotalLunchDateOld(
							new VT020006InfoToInsertDailyMeals(param.getKitchenID(), 0, param.getLunchDate()));
					// find total number of lunchDate in this kitchen in now
					totalNow = vt020006DAO.findTotalLunchDateNow(
							new VT020006InfoToInsertDailyMeals(param.getKitchenID(), 0, param.getLunchDate()));
					if (!checkConditionChangeNumberOfLunchDate(totalOld, totalNow, quantityOld, quantityNow)) {
						resp.setStatus(Constant.LIMITED_CHANGE_lUNCH_DATE_ERROR);
						return resp;
					}
				}
				// update to db
				// convert datetime to date
				lunch.setLunchDate(validate.validateDate(lunch.getLunchDate()));
				// validate input hasBooking = (0;1), 0 <= quantity <=20
				if (lunch.getHasBooking() != Constant.HAS_BOOKING_DEFAULT) {
					lunch.setHasBooking(Constant.HAS_BOOKING_NONE);
				}
				if (lunch.getQuantity() < Constant.QUANTITY_NONE || lunch.getQuantity() > Constant.QUANTITY_LIMITED) {
					lunch.setQuantity(0);
				}
				// check if date not existed in db => insert else update
				int checkLunch = vt020006DAO.checkLunchDate(lunch);
				if (checkLunch > 0) {
				  lunch.setIsPeriodic(Constant.PERIODIC_NONE);
				  vt020006DAO.updateLunch(lunch);
				} else {
					lunch.setIsPeriodic(Constant.PERIODIC_NONE);
					vt020006DAO.insertLunchDate(lunch);
				}
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				return resp;
			}

			// if lunchDate = today + 2
			sysDate = Calendar.getInstance();
			sysDate.add(Calendar.DAY_OF_MONTH, 2);
			if (validate.validateDate(lunchDate.getTime()).compareTo(validate.validateDate(sysDate.getTime())) >= 0) {
				// update to db
				// convert datetime to date
				lunch.setLunchDate(validate.validateDate(lunch.getLunchDate()));
				// validate input hasBooking = (0;1), 0 <= quantity <=20
				if (lunch.getHasBooking() != Constant.HAS_BOOKING_DEFAULT) {
					lunch.setHasBooking(Constant.HAS_BOOKING_NONE);
				}
				if (lunch.getQuantity() < Constant.QUANTITY_NONE || lunch.getQuantity() > Constant.QUANTITY_LIMITED) {
					lunch.setQuantity(0);
				}
				// check if date not existed in db => insert else update
				int checkLunch = vt020006DAO.checkLunchDate(lunch);
				if (checkLunch > 0) {
            lunch.setIsPeriodic(Constant.PERIODIC_NONE);
            vt020006DAO.updateLunch(lunch);
				} else {
					lunch.setIsPeriodic(Constant.PERIODIC_NONE);
					vt020006DAO.insertLunchDate(lunch);
				}
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				return resp;
			}
			// if lunchDate < today => no insert
			sysDate = Calendar.getInstance();
			if (validate.validateDate(lunchDate.getTime()).compareTo(validate.validateDate(sysDate.getTime())) < 0) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	/**
	 * use for convert day of week in db => day of week java
	 * 
	 * @param list
	 * @return
	 */
	public List<Integer> convertDayOfWeekDBToJava(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == 0)
				list.set(i, 2);
			else if (list.get(i) == 1)
				list.set(i, 3);
			else if (list.get(i) == 2)
				list.set(i, 4);
			else if (list.get(i) == 3)
				list.set(i, 5);
			else if (list.get(i) == 4)
				list.set(i, 6);
			else if (list.get(i) == 5)
				list.set(i, 7);
			else if (list.get(i) == 6)
				list.set(i, 1);
		}
		return list;
	}

	/**
	 * use for get total lunch date to send sms to chef in 16h30
	 */
	@Override
	public void sendSmsToChef16h30() {
		try {
			// get time tomorrow
			Calendar tomorrow = Calendar.getInstance();
			tomorrow.add(Calendar.DAY_OF_MONTH, 1);
			List<VT020006TotalLunchDate> listInfo = vt020006DAO.findTotalLunchDate(
					new VT020006InfoToFindTotalLunchDate(tomorrow.getTime(), Constant.HAS_BOOKING_DEFAULT));
			for (int i = 0; i < listInfo.size(); i++) {
				String kitchenName = listInfo.get(i).getKitchenName();
				int userID = listInfo.get(i).getUserID();
//				String chefName = listInfo.get(i).getChefName();
				String chefPhone = listInfo.get(i).getChefPhone();
				// get content sms ( for each unit)
				VT020006ContentSms contentSms = findSmsDetail(tomorrow.getTime(), listInfo.get(i).getKitchenID());
				// send sms
				// get time tomorrow
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String date = df.format(tomorrow.getTime());
				int dayOffWeek = tomorrow.get(Calendar.DAY_OF_WEEK);
				// conten
				String conten = MessageFormat.format(smsMessage.getProperty("S33"), kitchenName, date, dayOffWeek,
						contentSms.getTotal(), contentSms.getContent());
				// send sms
				String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL02");
				try {
					//send sms to chef
					sms.sendSms(userID, conten, Constant.STATUS_NEW_SMS, chefPhone, typeSms);
					//send sms to people who is configed in KITCHEN_PHONE_GET_SMS
					List<String> listPhoneNumber = vt020003DAO.findListPhoneNumberReceiveSms(listInfo.get(i).getKitchenID());
					for (int j = 0; j < listPhoneNumber.size(); j++) {
						sms.sendSms(-1, conten, Constant.STATUS_NEW_SMS, listPhoneNumber.get(j), typeSms);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw (e);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
	}

	/**
	 * use for get total lunch date to send sms to chef in 8h40
	 */
	@Override
	public void sendSmsToChef8h40() {
		try {
			// get time today
			Calendar today = Calendar.getInstance();
			today.set(Calendar.YEAR, 2020);
			today.set(Calendar.MONTH, 1);
			today.set(Calendar.DAY_OF_MONTH, 15);
			System.out.println(today);
			
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
      
      String dateInString = "17/03/2020";
      Date date = df.parse(dateInString);
      
			
			List<VT020006TotalLunchDate> listInfo = vt020006DAO.findTotalLunchDate(
					new VT020006InfoToFindTotalLunchDate(date, Constant.HAS_BOOKING_DEFAULT));
			
			ObjectMapper mapper = new ObjectMapper();
			System.out.println("listInfo " + mapper.writeValueAsString(listInfo));
			
			for (int i = 0; i < listInfo.size(); i++) {
				String kitchenName = listInfo.get(i).getKitchenName();
				int userID = listInfo.get(i).getUserID();
//				String chefName = listInfo.get(i).getChefName();
				String chefPhone = listInfo.get(i).getChefPhone();
				// get content sms ( for each unit)
				System.out.println(listInfo.get(i).getKitchenID());
				VT020006ContentSms contentSms = findSmsDetail(date, listInfo.get(i).getKitchenID());
				// send sms
				// get time today
				//DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				//String date = df.format(today.getTime());
				int dayOffWeek = today.get(Calendar.DAY_OF_WEEK);
				// conten
				String conten = MessageFormat.format(smsMessage.getProperty("S33"), kitchenName, date, dayOffWeek,
						contentSms.getTotal(), contentSms.getContent());
				// send sms
				String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL02");
				try {
					//send sms to chef
					sms.sendSms(userID, conten, Constant.STATUS_NEW_SMS, chefPhone, typeSms);
					//send sms to people who is configed in KITCHEN_PHONE_GET_SMS
					List<String> listPhoneNumber = vt020003DAO.findListPhoneNumberReceiveSms(listInfo.get(i).getKitchenID());
					for (int j = 0; j < listPhoneNumber.size(); j++) {
						sms.sendSms(-1, conten, Constant.STATUS_NEW_SMS, listPhoneNumber.get(j), typeSms);
					}
				} catch (Exception e) {
				  System.out.println(e.getMessage());
					logger.error(e.getMessage(), e);
					throw (e);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//throw (e);
		}
	}

	/**
	 * get total lunch date of tomorrow and save to DAILY_MEALS
	 */
	@Override
	public void saveDailyMeals() {
		try {
			// get time tomorrow
			Calendar tomorrow = Calendar.getInstance();
			tomorrow.add(Calendar.DAY_OF_MONTH, 1);
			List<VT020006TotalLunchDate> listInfo = vt020006DAO.findTotalLunchDateForDaily(
					new VT020006InfoToFindTotalLunchDate(tomorrow.getTime(), Constant.HAS_BOOKING_DEFAULT));
			for (int i = 0; i < listInfo.size(); i++) {
				int total = listInfo.get(i).getTotal();
				// insert total lunch date in DAILY_MEALS
				VT020006InfoToInsertDailyMeals info = new VT020006InfoToInsertDailyMeals(listInfo.get(i).getKitchenID(),
						total, null);
				vt020006DAO.insertDailyMeals(info);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
	}

	/**
	 * @param totalOld    --- total number lunchDate in 16h30
	 * @param totalNow    --- total number lunchDate in now
	 * @param quantityOld --- total number lunchDate order by user before change (if
	 *                    insert quantityOld = 0)
	 * @param quantityNow --- total number lunchDate order by user want to change or
	 *                    insert
	 * @return boolean
	 */
	@Override
	public boolean checkConditionChangeNumberOfLunchDate(int totalOld, int totalNow, int quantityOld, int quantityNow) {
	  double limit = lunchDao.getLunchPercent() / 100;
		if (Math.abs(totalNow - quantityOld + quantityNow - totalOld) <= totalOld * limit) {
			return true;
		}
		return false;
	}

	/**
	 * use for get detail lunch date for each unit to send sms to chef
	 */
	@Override
	public VT020006ContentSms findSmsDetail(Date lunchDate, String kitchenID) {
		VT020006ContentSms contentSms = new VT020006ContentSms();
		try {
			VT020010RequestParam param = new VT020010RequestParam();
			param.setHasBooking(Constant.HAS_BOOKING_DEFAULT);
			param.setQuantity(Constant.QUANTITY_NONE);
			param.setLunchDate(lunchDate);
			param.setKitchenID(kitchenID);
			List<VT020010ReportDetail> listReportItem = vt020010DAO.findReportItemByDate(param);
			
			// trace so suat an
			logger.info("kientn sms so suat an: " );
			String listReportItemLogs = vt020010DAO.findReportItemByDateLog(param);
	    logger.info(listReportItemLogs);
	    System.out.println(listReportItemLogs);
			// end trace so suat an
			
			// list unitName
			List<String> listUnitName = new ArrayList<>();
//			listUnitName.add(Constant.UNIT_NAME_BAN);
			listUnitName.add(Constant.UNIT_NAME_KHOI);
			// get all unit by unitId PARENT_UNIT_ID that UNIT_NAME not like 'khoi%'
			List<VT020010Unit> listUnitNotLikeKhoi = vt020010DAO
					.findAllUnitNotLikeKhoi(new VT020010InfoToFindUnit(Constant.UNIT_PARENT_ID, listUnitName));
			// get all unit by unitId PARENT_UNIT_ID that UNIT_NAME like 'khoi%'
			List<Integer> listUnitLikeKhoi = vt020010DAO
					.findAllUnitLikeKhoi(new VT020010InfoToFindUnit(Constant.UNIT_PARENT_ID, listUnitName));
			// get all unit by unitId PARENT_UNIT_ID that Unit Parent has UNIT_NAME like
			// 'khoi%' ( name with 1 level)
			List<VT020010Unit> listUnitChildOfKhoi = vt020010DAO.findAllUnitChildOfKhoiNoParent(listUnitLikeKhoi);

			// list all id need for synthesis report
			List<VT020010Unit> listUnit = new ArrayList<>();
			for (int i = 0; i < listUnitNotLikeKhoi.size(); i++) {
				listUnit.add(listUnitNotLikeKhoi.get(i));
			}
			for (int i = 0; i < listUnitChildOfKhoi.size(); i++) {
				listUnit.add(listUnitChildOfKhoi.get(i));
			}
			// get smsContent
			String smsContent = "";
			int totalSms = 0;
			// find total for each unit
			// for all unit
			
			for (int i = 0; i < listUnit.size(); i++) {
			  int total = 0;
			  // for lunchDate in a day
			  for (int j = listReportItem.size() - 1; j >= 0; j--) {
			    // if this employee has unitPath contains unitID in list => find total
			    if(listReportItem.get(j).getUnitPath() != null) {
			      if (listReportItem.get(j).getUnitPath().contains(String.valueOf(listUnit.get(i).getUnitID()))) {
	            total += listReportItem.get(j).getTotal();
	            listReportItem.remove(j);
	          }
			    }
			  }
			  // create report for this unit in this lunchDate if total > 0
			  if (total > 0) {
			    //	          CompareUnitAcronym compare = new CompareUnitAcronym();
			    //	          String unitAcronym = compare.getUnitAcronym(listUnit.get(i).getUnitName());
			    VT020010Unit vt020010Unit  = new VT020010Unit();
			    vt020010Unit.setKitchenId(kitchenID);
			    vt020010Unit.setUnitID(listUnit.get(i).getUnitID());
			    vt020010Unit.setPath(listUnit.get(i).getPath());

			    String unitName = "";
			    if(isKhoiCoQuanUnit(vt020010Unit, listUnitChildOfKhoi)) {
			      unitName = vt020010DAO.getUnitShortNameKhoi(vt020010Unit);
			    } else if(isTrungTamUnit(vt020010Unit, listUnitNotLikeKhoi)) {
			      unitName = vt020010DAO.getUnitShortName(vt020010Unit);
			    } else {
			      unitName = vt020010DAO.getUnitShortNameKhoi(vt020010Unit);
			    }

			    smsContent += unitName + ": " + total + ", ";
			  }
			  totalSms += total;
			}
			
			int total = 0;
			if(listReportItem.size() > 0) {
			  for (int j = listReportItem.size() - 1; j >= 0; j--) {
			    // if this employee has unitPath contains unitID in list => find total
			    total += listReportItem.get(j).getTotal();
			    listReportItem.remove(j);
        }
			  totalSms += total;
			  smsContent += "Khác(không có đơn vị)" + ": " + total + ", ";
			}

			smsContent = smsContent.substring(0, smsContent.length() - 2);
			// set content and total lunchDate
			contentSms.setContent(smsContent);
			contentSms.setTotal(totalSms);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return contentSms;
	}

	@Override
	@Transactional
	public ResponseEntityBase getTotalLunchDate(VT020006InfoToGetListLunchDate info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		int total = 0;
		
		try {
			info.setQuantity(Constant.QUANTITY_NONE);
			info.setHasBooking(Constant.HAS_BOOKING_DEFAULT);
			info.setIsPeriodic(Constant.PERIODIC_DEFAULT);
			
			total = vt020006DAO.getTotalLunchDate(info);
			resp.setData(total);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		
		return resp;
	}

	private boolean isKhoiCoQuanUnit(VT020010Unit currtenUnit, List<VT020010Unit> units) {
	  for(VT020010Unit unit : units) {
	    if(currtenUnit.getUnitID() == unit.getUnitID()) return true;
	  }
	  return false;
	}
	
	private boolean isTrungTamUnit(VT020010Unit currtenUnit, List<VT020010Unit> units) {
    for(VT020010Unit unit : units) {
      if(currtenUnit.getUnitID() == unit.getUnitID()) return true;
    }
    return false;
  }
	@Override
	public void sendSmsMealInfEachMonthToEmployee() {
		try {
			List<VT020006LunchInforEachMonth> listInfo = vt020006DAO.getInforLunchOfEmpEachMonth();
			for (int i = 0; i < listInfo.size(); i++) {
				String typeSms = smsMessage.getProperty("TYPE_SMS_LUNCH_EACH_MONTH");
				String content = MessageFormat.format(smsMessage.getProperty("S120"), listInfo.get(i).getMonth(), listInfo.get(i).getTotalMeal(), listInfo.get(i).getTotalMoney());
				try {
					Integer employeeId = listInfo.get(i).getEmployeeId();
					if(employeeId == null) {
						employeeId = -1;
					}
					sms.sendSms(employeeId, content, Constant.STATUS_NEW_SMS, listInfo.get(i).getPhoneNumber(), typeSms);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw (e);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		
	}
}
