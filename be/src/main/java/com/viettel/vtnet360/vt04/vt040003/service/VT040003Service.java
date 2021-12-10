package com.viettel.vtnet360.vt04.vt040003.service;

import java.security.Principal;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040003.entity.VT040003EntityRqREGService;

/**
 * Class interface VT040003Service
 * 
 * @author KienHT 27/09/2018
 * 
 */
public interface VT040003Service {
	
	/**
	 * User request issues Service 
	 * 
	 * @param VT040003EntityRqREGService and Principal
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase issuesService(VT040003EntityRqREGService requestParam, Principal principal)throws Exception;
}
