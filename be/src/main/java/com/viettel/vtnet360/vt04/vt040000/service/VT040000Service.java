package com.viettel.vtnet360.vt04.vt040000.service;

import java.security.Principal;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityRqApprove;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityRqFind;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityRqUpdate;

/**
 * Class interface VT040003Service
 * 
 * @author KienHT 27/09/2018
 * 
 */
public interface VT040000Service {

	/**
	 * User find Service by request
	 * 
	 * @param VT040000EntityRqFind
	 * @param Principal
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findService(VT040000EntityRqFind requestParam, Principal principal);

	/**
	 * User find Issued Service by request
	 * 
	 * @param VT040000EntityRqFind
	 * @param Principal
	 * @param OAuth2Authentication
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findIssuedService(VT040000EntityRqFind requestParam, Principal principal,
			OAuth2Authentication authentication);

	/**
	 * find Detail Issued Service request
	 * 
	 * @param VT040000EntityRqFind
	 * @param Principal
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase finDatailService(VT040000EntityRqFind requestParam, Principal principal);

	/**
	 * approve Issued Service
	 * 
	 * @param VT040000EntityRqFind
	 * @param Principal
	 * @param OAuth2Authentication
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase approveIssuedService(VT040000EntityRqApprove requestParam, Principal principal,
			OAuth2Authentication authentication) throws Exception;

	/**
	 * update for issued Service
	 * 
	 * @param VT040000EntityRqUpdate
	 * @param Principal
	 * @param OAuth2Authentication
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase updateIssuedService(VT040000EntityRqUpdate requestParam, Principal principal,
			OAuth2Authentication authentication);

}
