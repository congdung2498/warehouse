/**
 * 
 */
package com.viettel.vtnet360.vt06.vt060001.service;

import java.util.List;

import com.viettel.vtnet360.vt06.vt060001.entity.SearchCondition;
import com.viettel.vtnet360.vt06.vt060001.entity.VersionDetail;

/**
 * @author VinhNVQ
 *
 */
public interface VT060001Service {
	/**
	 * @author VinhNVQ
	 * @param searchCondition
	 * @return List<VersionDetail>
	 * @throws Exception
	 */
	public List<VersionDetail> getListVersionDetail(SearchCondition searchCondition) throws Exception;
	
	/**
	 * @author VinhNVQ
	 * @param insertCondition
	 * @return 
	 * @throws Exception
	 */
	public boolean insertVersionDetail(VersionDetail insertCondition) throws Exception;

	/**
	 * @author VinhNVQ
	 * @param insertCondition
	 * @return boolean
	 * @throws Exception
	 */
	boolean updateVersionDetail(VersionDetail insertCondition) throws Exception;

	/**
	 * @author VinhNVQ
	 * @param updateCondition
	 * @return boolean
	 * @throws Exception
	 */
	boolean deleteVersionDetail(SearchCondition updateCondition) throws Exception;

	/**
	 * @author VinhNVQ
	 * @param deviceType
	 * @return VersionDetail
	 * @throws Exception
	 */
	VersionDetail getVersionDetail(String deviceType) throws Exception;
	
	/**
	 * @author VinhNVQ
	 * @param deviceType
	 * @param string 
	 * @param Version
	 * @return boolean
	 * @throws Exception
	 */
	void setUserVersionDetail(String deviceType, String version, String userName) throws Exception;
}
