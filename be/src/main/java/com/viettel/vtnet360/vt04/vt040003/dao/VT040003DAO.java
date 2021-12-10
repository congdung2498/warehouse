package com.viettel.vtnet360.vt04.vt040003.dao;

import java.util.List;
import java.util.Map;

/**
 * Class interface VT040003DAO
 * 
 * @author KienHT 27/09/2018
 * 
 */
public interface VT040003DAO {

	/**
	 * User request issues Service
	 * 
	 * @param Map<String, Object>
	 */
	public void issuesService(Map<String, Object> data);

	/**
	 * find for valid issues Service
	 * 
	 * @param Map<String, Object>
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> isValid(Map<String, Object> data);
	
	
	/**
	 * find for valid issues Service
	 * 
	 * @param Map<String, Object>
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> isServiceValid(Map<String, Object> data);
	
	
	/**
	 * KienPK - IIST
	 * find  issues Service not yet complete
	 * 
	 * @param Map<String, Object>
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> inValidIssuesService(Map<String, Object> data);
	
	public String findReceiverIssue(String serviceId);

	
}
