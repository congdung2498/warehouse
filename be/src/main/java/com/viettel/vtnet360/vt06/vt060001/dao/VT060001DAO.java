package com.viettel.vtnet360.vt06.vt060001.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt06.vt060001.entity.SearchCondition;
import com.viettel.vtnet360.vt06.vt060001.entity.VersionDetail;

/**
 * 
 * @author VinhNVQ
 * @since 05/12/2018
 * 
 */
@Repository
public interface VT060001DAO {
	
	/**
	 * 
	 * @param searchCondition
	 * @return
	 * @throws Exception
	 */
	public List<VersionDetail> getListVersionDetail(SearchCondition searchCondition) throws Exception;
	
	/**
	 * 
	 * @param insertCondition
	 * @return
	 * @throws Exception
	 */
	public int insertVersionDetail(VersionDetail insertCondition) throws Exception;
	
	/**
	 * 
	 * @param updateCondition
	 * @return
	 * @throws Exception
	 */
	public int updateStatusOldVersion(SearchCondition updateCondition) throws Exception;

	/**
	 * 
	 * @param deviceType
	 * @return
	 * @throws Exception
	 */
	public VersionDetail getVersionDetail(String deviceType) throws Exception;

	/**
	 * 
	 * @param deviceType
	 * @param version
	 * @return
	 */
	public int setUserVersionDetail(Map<String, String> map) throws Exception;
	
}
