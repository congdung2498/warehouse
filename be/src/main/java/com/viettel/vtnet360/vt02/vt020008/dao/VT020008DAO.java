package com.viettel.vtnet360.vt02.vt020008.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt02.vt020008.entity.VT020008InfoCheckHasBooking;
import com.viettel.vtnet360.vt02.vt020008.entity.VT020008ParamRequest;

/**
 * Class DAO for screen VT020008 : rating lunch
 * 
 * @author DuyNK 19/09/2018
 */
@Repository
public interface VT020008DAO {

	/**
	 * check lunch date existed
	 * 
	 * @param param
	 * @return Integer (0:not existed 1:existed)
	 */
	public int checkLunchExisted(VT020008ParamRequest param);

	/**
	 * get kitchenID by userName and dateLunch
	 * 
	 * @param param
	 * @return Integer (0:not existed 1:existed)
	 */
	public String findKitchenID(VT020008ParamRequest param);

	/**
	 * update add rating and comment
	 * 
	 * @param param
	 * @return Integer (0:error 1:success)
	 */
	public int updateRating(VT020008ParamRequest param);

	/**
	 * find menu of this userName
	 * 
	 * @param info
	 * @return List<String>
	 */
	public List<String> findMenuInfo(VT020008InfoCheckHasBooking info);
}
