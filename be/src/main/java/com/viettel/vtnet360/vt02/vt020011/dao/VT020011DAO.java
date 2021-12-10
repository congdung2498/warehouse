package com.viettel.vtnet360.vt02.vt020011.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt02.vt020011.entity.VT020011DayOffSettingEntity;

/**
 * Class DAO for screen VT020011 : setting day off
 * 
 * @author DuyNK 09/08/2018
 */
@Repository
public interface VT020011DAO {

	/**
	 * get list all Day Off in DAY_OFF_SETTING
	 * 
	 * @param dayOff
	 * @return List<VT020011DayOffSettingEntity>
	 */
	public List<VT020011DayOffSettingEntity> findDayOff(VT020011DayOffSettingEntity dayOff);

	/**
	 * count total day off
	 * 
	 * @param dayOff
	 * @return Integer
	 */
	public int countTotalDayOff(VT020011DayOffSettingEntity dayOff);

	/** check Day Off exist in DAY_OFF_SETTING 1:exist 0:Not 
	 * 
	 * @param dayOff
	 * @return Integer
	 */
	public int checkDayOffExist(VT020011DayOffSettingEntity dayOff);

	/**
	 * insert new Day Off to DAY_OFF_SETTING
	 * 
	 * @param dayOff
	 * @return Integer
	 */
	public int insertDayOff(VT020011DayOffSettingEntity dayOff);

	/**
	 * update new Day Off to DAY_OFF_SETTING
	 * 
	 * @param dayOff
	 * @return Integer
	 */
	public int updateDayOff(VT020011DayOffSettingEntity dayOff);

	/**
	 * insert copy day off
	 * 
	 * @param listDayOff
	 * @return status insert copy day off
	 */
	public int insertCopyDayOff(List<VT020011DayOffSettingEntity> listDayOff);

	/**
	 * delete day off
	 * 
	 * @param dayOff
	 * @return status delete day off
	 */
	public int deleteDayOff(VT020011DayOffSettingEntity dayOff);

	/**
	 * get list of years
	 * 
	 * @return list of years
	 */
	public List<String> findAllYears();
	
	/**
	 * delete record lunchDate in dayOff setting
	 * 
	 * @param dayOff
	 */
	public void deleteLunchDateInDayOff(Date dayOff);
}
