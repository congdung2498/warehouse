package com.viettel.vtnet360.vt05.vt050017.service;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050017.dao.VT050017DAO;
import com.viettel.vtnet360.vt05.vt050017.entity.VT050017DataResponse;
import com.viettel.vtnet360.vt05.vt050017.entity.VT050017RequestParam;

/**
 * @author DuyNK
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT050017ServiceImpl implements VT050017Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050017DAO vt050017DAO;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findData(VT050017RequestParam param, Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT050017DataResponse> dataResponse = null;
		try {
			// check role if HCDV => find by their place and unit
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV))) {
				param.setRoleAdmin(false);
			}
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				param.setRoleAdmin(true);
			}
			param.setPageNumber(param.getPageNumber() * param.getPageSize());
			dataResponse = vt050017DAO.findData(param);
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

}