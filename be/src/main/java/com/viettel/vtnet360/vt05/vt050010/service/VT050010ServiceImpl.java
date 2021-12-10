package com.viettel.vtnet360.vt05.vt050010.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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
import com.viettel.vtnet360.vt05.vt050002.dao.VT050002DAO;
import com.viettel.vtnet360.vt05.vt050002.entity.Place;
import com.viettel.vtnet360.vt05.vt050010.dao.VT050010DAO;
import com.viettel.vtnet360.vt05.vt050010.entity.CheckPlaceByUser;
import com.viettel.vtnet360.vt05.vt050010.entity.VT050010DataPlaceResponse;
import com.viettel.vtnet360.vt05.vt050010.entity.VT050010DataResponse;
import com.viettel.vtnet360.vt05.vt050010.entity.VT050010InfoToFindRequestInfo;
import com.viettel.vtnet360.vt05.vt050010.entity.VT050010RequestParam;

/**
 * @author DuyNK 04/10/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT050010ServiceImpl implements VT050010Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050010DAO vt050010DAO;

	@Autowired
  private VT050002DAO vt050002DAO;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findData(VT050010RequestParam param, String userName,
			Collection<GrantedAuthority> roleList) {
		
		///param.getTimeRequest() = 0;
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT050010DataResponse> dataResponse = null;
		try {
			// check role if VPTCT => find by their place
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))
					&& !roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				// check user logged on configed in STATIONERY_STAFF or not => true => find by
				// their place
				// else => no role => response error to client
				int checkConfigInStaff = vt050010DAO.checkVptctInStaffConfig(userName);
				// false
				if (checkConfigInStaff == Constant.NONE_EXIST_RECORD) {
					resp.setStatus(Constant.ERROR_VPTCT_NO_CONFIG_IN_STATIONERY_STAFF);
					return resp;
				}
				
				VT050010InfoToFindRequestInfo info = new VT050010InfoToFindRequestInfo();
				info.setAdmin(false);
				info.setHcvp(true);
	      info.setPlaceID(param.getPlaceID());
	      info.setUnitID(param.getUnitID());
	      info.setPageNumber(param.getPageNumber() * param.getPageSize());
	      info.setPageSize(param.getPageSize());
	      info.setListStatus(param.getListStatus());
	      info.setDateRequest(param.getDateRequest());
	      
	      // use fo get record that LDDV approve or pause executing or confirm received
	      // set info to find spending limit of unit
	      info.setmClass(Constant.STATIONERY_SPENDING_LIMIT_CLASS);
	      info.setmCode(Constant.STATIONERY_SPENDING_LIMIT_CODE);
	      // get in db
	      dataResponse = vt050010DAO.findInfoRequest(info);
	      resp.setData(dataResponse);
				// true
//				int placeID = vt050010DAO.findPlaceIDOfVptctUserName(userName);
//				param.setPlaceID(placeID);
			} 
			else if (!roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))
          && roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
			// set param to select in db
			VT050010InfoToFindRequestInfo info = new VT050010InfoToFindRequestInfo();
			info.setAdmin(true);
			info.setHcvp(false);
			info.setPlaceID(param.getPlaceID());
			info.setUnitID(param.getUnitID());
			info.setPageNumber(param.getPageNumber() * param.getPageSize());
			info.setPageSize(param.getPageSize());
			info.setListStatus(param.getListStatus());
			info.setDateRequest(param.getDateRequest());
//			Date dateInfo = sdf.parse(strDate); 
//			info.setDateRequestInfo(dateInfo);
			// use fo get record that LDDV approve or pause executing or confirm received
			// set info to find spending limit of unit
			info.setmClass(Constant.STATIONERY_SPENDING_LIMIT_CLASS);
			info.setmCode(Constant.STATIONERY_SPENDING_LIMIT_CODE);
			// get in db
			dataResponse = vt050010DAO.findInfoRequestAdmin(info);
			resp.setData(dataResponse);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
        throw (e);
      } catch (Exception e1) {
        // TODO Auto-generated catch block
				logger.error(e1.getMessage(), e1);
      }
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findPlaceByUserName(CheckPlaceByUser checkPlaceByUser, Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try{
		  if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))
          && !roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
		    checkPlaceByUser.setHCVPP(true);
//		    checkPlaceByUser.setAdmin(false);
			}else if (!roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))
          && roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
//			  checkPlaceByUser.setHCVPP(false);
        checkPlaceByUser.setAdmin(true);
      }
		  int page  = checkPlaceByUser.getPageNumber() * checkPlaceByUser.getPageSize();
		  checkPlaceByUser.setPageNumber(page);
		  List<VT050010DataPlaceResponse> placeID = vt050010DAO.findPlaceIDUserName(checkPlaceByUser);
		if(placeID != null && placeID.size() > 0){
		resp.setData(placeID);
		} else {
			resp.setData(null);
		}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
}