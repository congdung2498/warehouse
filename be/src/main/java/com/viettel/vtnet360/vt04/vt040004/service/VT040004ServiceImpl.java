package com.viettel.vtnet360.vt04.vt040004.service;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040004.dao.VT040004DAO;
import com.viettel.vtnet360.vt04.vt040004.entity.VT040004EntityDataForApprove;
import com.viettel.vtnet360.vt04.vt040004.entity.VT040004EntityRq;

/**
 * Class VT040004Controller
 * 
 * @author KienHT 4/10/2018
 * 
 */
@Service
public class VT040004ServiceImpl implements VT040004Service {

	@Autowired
	VT040004DAO vt040004DAO;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * User request issuesedService For Approve
	 * 
	 * @param VT040004EntityRq
	 * @param Principal
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase issuesedServiceForApprove(VT040004EntityRq requestParam, Principal principal,
			OAuth2Authentication authentication) {

		// get info User from Principal
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();

		// create MAP Object contain param
		Map<String, Object> dataPram = new HashMap<String, Object>();
		dataPram.put("requestParam", requestParam);
		dataPram.put("userName", userName);
		dataPram.put("fromIndex", requestParam.getPageNumber() * requestParam.getPageSize());
		dataPram.put("getSize", requestParam.getPageSize());
		dataPram.put(Constant.PMQT_ROLE_ADMIN, null);

		// check role user admin
		Collection<GrantedAuthority> arrayRoleToken = (Collection<GrantedAuthority>) oauth.getAuthorities();
		for (GrantedAuthority temp : arrayRoleToken) {
			// if user had role PMQT_ROLE_ADMIN
			if (Constant.PMQT_ROLE_ADMIN.equalsIgnoreCase(temp.getAuthority())) {
				dataPram.put(Constant.PMQT_ROLE_ADMIN, Constant.PMQT_ROLE_ADMIN);
				break;
			}
		}

		// set reponse default
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		// try executive
		try {
			List<VT040004EntityDataForApprove> data = vt040004DAO.issuesedServiceForApprove(dataPram);
			response.setData(data);
			response.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return response;
	}

}
