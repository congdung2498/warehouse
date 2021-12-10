package com.viettel.vtnet360.vt04.vt040004.service;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040004.entity.VT040004EntityRq;
import java.security.Principal;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * Class VT040004Controller
 * 
 * @author KienHT 4/10/2018
 * 
 */
public interface VT040004Service {

	/**
	 * User request issuesedService For Approve
	 * 
	 * @param VT040004EntityRq
	 * @param Principal
	 * @param OAuth2Authentication
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase issuesedServiceForApprove(VT040004EntityRq requestParam, Principal principal,
			OAuth2Authentication authentication);

}
