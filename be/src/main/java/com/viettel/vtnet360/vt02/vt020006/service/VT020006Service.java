package com.viettel.vtnet360.vt02.vt020006.service;

import java.util.Date;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006ContentSms;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006InfoToGetListLunchDate;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006Lunch;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006ParamRequest;

/**
 * Class service for screen VT020006 : set lunch time
 * 
 * @author DuyNK 17/09/2018
 */
public interface VT020006Service {

	/**
	 * Select days of week periodic and list date of lunch
	 * 
	 * @param info
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase getInfoLunchDate(VT020006InfoToGetListLunchDate info);

	/**
	 * Select info of one date to update
	 * 
	 * @param lunch
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase getInfoOneLunch(VT020006Lunch lunch);

	/**
	 * Select info of menu in a date in a kitchenID
	 * 
	 * @param lunch
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase getInfoMenu(VT020006Lunch lunch);

	/**
	 * delete lunch date
	 * 
	 * @param param
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase deleteLunch(VT020006ParamRequest param);

	/**
	 * insert lunch date
	 * 
	 * @param param
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase insertLunch(VT020006ParamRequest param);

	/**
	 * update lunch date
	 * 
	 * @param param
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase updateLunch(VT020006ParamRequest param);

	/**
	 * use for get total lunch date to send sms to chef in 16h30
	 */
	public void sendSmsToChef16h30();

	/**
	 * use for get total lunch date to send sms to chef in 8h40
	 */
	public void sendSmsToChef8h40();
	
	/**
	 * use for get total meal and money each month to send sms to employee in 4th each month
	 */
	public void sendSmsMealInfEachMonthToEmployee();
	
	/**
	 * use for get detail lunch date for each unit to send sms to chef
	 *
	 * @param lunchDate
	 * @param kitchenID
	 * @return VT020006ContentSms
	 */
	public VT020006ContentSms findSmsDetail(Date lunchDate, String kitchenID);
	
	/**
	 * get total lunch date of tomorrow and save to DAILY_MEALS
	 */
	public void saveDailyMeals();
	
	/**
	 * @param totalOld    --- total number lunchDate in 16h30
	 * @param totalNow    --- total number lunchDate in now
	 * @param quantityOld --- total number lunchDate order by user before change (if
	 *                    insert quantityOld = 0)
	 * @param quantityNow --- total number lunchDate order by user want to change or
	 *                    insert
	 * @return
	 */
	public boolean checkConditionChangeNumberOfLunchDate(int totalOld, int totalNow, int quantityOld, int quantityNow);
	
	/**
	 * get total lunch date
	 * 
	 * @return
	 */
	public ResponseEntityBase getTotalLunchDate(VT020006InfoToGetListLunchDate info);
}
