package com.viettel.vtnet360.vt02.vt020009.service;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020009.dao.VT020009DAO;
import com.viettel.vtnet360.vt02.vt020009.entity.VT020009InfoToFindReport;
import com.viettel.vtnet360.vt02.vt020009.entity.VT020009Report;

/**
 * Class serviceImpl for screen VT020009 : report kitchen rating
 * 
 * @author DuyNK 13/09/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT020009ServiceImpl implements VT020009Service {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT020009DAO vt020009DAO;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findReport(VT020009InfoToFindReport info, String userName, Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT020009Report> listReport = null;
		try {
			listReport = vt020009DAO.findReportItem(info);
			resp.setData(listReport);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
}
