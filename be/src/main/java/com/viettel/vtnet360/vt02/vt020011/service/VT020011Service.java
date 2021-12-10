package com.viettel.vtnet360.vt02.vt020011.service;

import java.util.List;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020011.entity.VT020011DayOffSettingEntity;

/**
 * Class service for screen VT020011 : setting day off
 * 
 * @author DuyNK 05/09/2018
 */
public interface VT020011Service {

	/**
	 * Select all day off from DAY_OFF_SETTING
	 * 
	 * @param dayOff
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase getDayOff(VT020011DayOffSettingEntity dayOff);

	/**
	 * count total record day off
	 * 
	 * @param dayOff
	 * @return number of record day off
	 */
	public ResponseEntityBase countTotalDayOff(VT020011DayOffSettingEntity dayOff);

	/**
	 * Insert day off to DAY_OFF_SETTING
	 * 
	 * @param dayOff
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase insertDayOff(VT020011DayOffSettingEntity dayOff);

	/**
	 * Update day off to DAY_OFF_SETTING
	 * 
	 * @param dayOff
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase updateDayOff(VT020011DayOffSettingEntity dayOff);

	/**
	 * copy data
	 * 
	 * @param listDayOff
	 * @return status insert copy data
	 */
	public ResponseEntityBase copyDayOff(List<VT020011DayOffSettingEntity> listDayOff);

	/**
	 * get list of years
	 * 
	 * @return list of years
	 */
	public ResponseEntityBase findAllYears();
}
