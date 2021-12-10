package com.viettel.vtnet360.vt02.vt020006.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.kitchen.dto.Dish;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002Kitchen;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006InfoToCheckKitchenExist;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006InfoToFindTotalLunchDate;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006InfoToGetListLunchDate;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006InfoToInsertDailyMeals;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006InfoTofindDayOff;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006Lunch;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006LunchDate;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006LunchInfo;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006LunchInforEachMonth;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006TotalLunchDate;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006UserInfoLunchDate;

/**
 * Class DAO for screen VT020006 : set lunch time
 * 
 * @author DuyNK 17/09/2018
 */
@Repository
@Transactional
public interface VT020006DAO {

	/**
	 * Select list days of week that periodic
	 * 
	 * @param info
	 * @return List<Integer>
	 */
	public List<Integer> findPeriodic(VT020006InfoToGetListLunchDate info);

	/**
	 * find kitchenID that periodic
	 * 
	 * @param info
	 * @return VT020002Kitchen
	 */
	public List<VT020002Kitchen> findKitchenIDPeriodic(VT020006InfoToGetListLunchDate info);

	/**
	 * Select list date of month
	 * 
	 * @param info
	 * @return List<VT020006LunchDate>
	 */
	public List<VT020006LunchDate> findListDate(VT020006InfoToGetListLunchDate info);

	/**
	 * Select info of one date
	 * 
	 * @param lunch
	 * @return VT020006LunchInfo
	 */
	public VT020006LunchInfo findLunchInfo(VT020006Lunch lunch);

	/**
	 * find menu of a date in a kitchenID
	 * 
	 * @param lunch
	 * @return List<String>
	 */
	public List<Dish> findMenuInfo(VT020006Lunch lunch);

	/**
	 * find all dayOff to compare before insert lunch date (lunchDate = dayOff => no
	 * insert)
	 * 
	 * @param status
	 * @return List<Date>
	 */
	public List<Date> findDayOff(int status);

	/**
	 * find all dayOff by month
	 * 
	 * @param info
	 * @return List<Date>
	 */
	public List<Date> findDayOffByMonth(VT020006InfoTofindDayOff info);

	/**
	 * insert new lunch calendar
	 * 
	 * @param lunch
	 */
	public void insertLunchDate(VT020006Lunch lunch);

	/**
	 * call when insert lunchDate periodic update all record that date of lunch <
	 * this date insert => IS_PERIODIC = 0
	 * 
	 * @param lunch
	 */
	public void updatePeriodicToInactive(VT020006Lunch lunch);

	/**
	 * delete lunch calendar follow list date
	 * 
	 * @param lunch
	 */
	public void deleteLunch(VT020006Lunch lunch);

	/**
	 * delete lunch calendar all from date > 1 date
	 * 
	 * @param lunch
	 */
	public void deleteAllLunch(VT020006Lunch lunch);

	/**
	 * update lunch calendar
	 * 
	 * @param lunch
	 */
	public void updateLunch(VT020006Lunch lunch);

	/**
	 * check lunch calendar existed or not
	 * 
	 * @param lunch
	 * @return Integer
	 */
	public int checkLunchDate(VT020006Lunch lunch);

	/**
	 * check kitchenID existed or not
	 * 
	 * @param info
	 * @return Integer
	 */
	public int checkKitchenExisted(VT020006InfoToCheckKitchenExist info);

	/**
	 * use for register auto by system for the lunch calendar of all employees in
	 * next month when start a new month
	 * 
	 * @param info
	 * @return List<VT020006UserInfoLunchDate>
	 */
	public List<VT020006UserInfoLunchDate> findListUser(VT020006InfoToGetListLunchDate info);

	/**
	 * delete record of the next month before insert auto by system
	 * 
	 * @param date
	 */
	public void deleteLunchDateOfNextMonth(Date date);

	/**
	 * use for get total lunch date of tomorrow to send sms to chef
	 * 
	 * @param info
	 * @return List<VT020006TotalLunchDate>
	 */
	public List<VT020006TotalLunchDate> findTotalLunchDate(VT020006InfoToFindTotalLunchDate info);
	
	public List<VT020006TotalLunchDate> findTotalLunchDateForDaily(VT020006InfoToFindTotalLunchDate info);
	
	/**
	 * insert to DAILY_MEALS total lunchDate in 16h30 each day
	 * 
	 * @param info
	 */
	public void insertDailyMeals(VT020006InfoToInsertDailyMeals info);
	
	/**
	 * find total lunchDate in 16h30 to check limit change
	 * 
	 * @param info
	 * @return Integer
	 */
	public int findTotalLunchDateOld(VT020006InfoToInsertDailyMeals info);
	
	/**
	 * find total lunchDate in now to check limit change
	 * 
	 * @param info
	 * @return Integer
	 */
	public int findTotalLunchDateNow(VT020006InfoToInsertDailyMeals info);
	
	/**
	 * get total lunch date
	 * @return 
	 */
	public int getTotalLunchDate(VT020006InfoToGetListLunchDate info);
	
	public List<VT020006LunchInforEachMonth> getInforLunchOfEmpEachMonth();
}
