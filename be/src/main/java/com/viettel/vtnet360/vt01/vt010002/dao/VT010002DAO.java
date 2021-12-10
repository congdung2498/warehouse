package com.viettel.vtnet360.vt01.vt010002.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.viettel.vtnet360.vt01.vt010002.entity.VT010002EntityRqUpdate;

/**
 * Class interface VT010002DAO
 * 
 * @author KienHT 11/08/2018
 * 
 */
public interface VT010002DAO {

	/**
	 * inOut Register Update
	 * 
	 * @param HashMap<String,Object>
	 */
	public void inOutRegisterUpdate(HashMap<String, Object> data);

	/**
	 * checkStatusRecordUpdate
	 * 
	 * @param VT010002EntityRqUpdate
	 * @return int
	 */
	public int checkStatusRecordUpdate(VT010002EntityRqUpdate requestParam);

	/**
	 * check Status Update
	 * 
	 * @param String
	 * @return VT010002EntityRqUpdate
	 */
	public VT010002EntityRqUpdate findOldTimePlan(String inOutRegisterId);

	/**
	 * find for valid issues Register IN OUT
	 * 
	 * @param Map<String, Object>
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> isValid(Map<String, Object> data);

}
