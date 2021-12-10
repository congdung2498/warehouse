package com.viettel.vtnet360.vt05.vt050013.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013InfoToEditRequest;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013ParamToRating;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013ParamToRatingVPTCT;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013RequestParam;

/**
 * @author DuyNK 09/10/2018
 */
public interface VT050013Service {

	/**
	 * find info request of this user logged on
	 * 
	 * @param param
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findData(VT050013RequestParam param);

	/**
	 * rating
	 * 
	 * @param param
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase rating(VT050013ParamToRating param);

	/**
	 * cancel request
	 * 
	 * @param param
	 * @param userName
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase cancelRequest(VT050013RequestParam param, String userName);

	/**
	 * edit request or delete some item in request
	 * 
	 * @param info
	 * @param roleList
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase editRequest(VT050013InfoToEditRequest info, Collection<GrantedAuthority> roleList);
	
	public ResponseEntityBase ratingVPTCT(VT050013ParamToRatingVPTCT param);
	
	public ResponseEntityBase ratingHCDV(VT050013ParamToRatingVPTCT param);
	
	public ResponseEntityBase findDataVPTCT(VT050013RequestParam param);
}
