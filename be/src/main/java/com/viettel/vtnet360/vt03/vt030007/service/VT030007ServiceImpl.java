package com.viettel.vtnet360.vt03.vt030007.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
//import com.viettel.vtnet360.vt03.vt030000.dao.VT030000DAO;
import com.viettel.vtnet360.vt03.vt030007.dao.VT030007DAO;
import com.viettel.vtnet360.vt03.vt030007.entity.VT030007EntityBookCarInfo;
import com.viettel.vtnet360.vt03.vt030007.entity.VT030007ResquestFindBookCar;

/**
 * 
 * @author CuongHD
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030007ServiceImpl implements VT030007Service {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private VT030007DAO vt030007dao;
	
	
	@Override
	@Transactional
	public ResponseEntityBase findBookCarListApprove(VT030007ResquestFindBookCar obj, Collection<GrantedAuthority> roleList) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT030007EntityBookCarInfo> data = new ArrayList<>();
		try {
			if (!roleList.isEmpty()) {
				// get title of employee
				for (GrantedAuthority x : roleList) {
					if (x.getAuthority().equals(Constant.PMQT_ROLE_MANAGER)) {
						obj.setRole(Constant.PMQT_ROLE_MANAGER);
					} else if (x.getAuthority().equals(Constant.PMQT_ROLE_MANAGER_CVP)) {
						obj.setRole(Constant.PMQT_ROLE_MANAGER_CVP);
					}
				}
			}
			
			if(obj.getPageNumber() > 0) obj.setPageNumber(obj.getPageNumber() * obj.getPageSize());
			data = vt030007dao.findBookCarListApprove(obj);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			response.setData(null);
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		logger.info("********************* END EXECUTE APIVT030007_01 ***********************");
		return response;
	}
	
	@Override
	@Transactional
	public ResponseEntityBase findBookCarList(VT030007ResquestFindBookCar obj) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT030007EntityBookCarInfo> data = new ArrayList<>();
		try {
			if(obj.getPageNumber() > 0) obj.setPageNumber(obj.getPageNumber() * obj.getPageSize());
			data = vt030007dao.findBookCarList(obj);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			response.setData(null);
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		logger.info("********************* END EXECUTE APIVT030007_01 ***********************");
		return response;
	}
	
	@Override
	@Transactional
	public ResponseEntityBase findBookCarRequest(VT030007ResquestFindBookCar obj, Collection<GrantedAuthority> roleList)
			throws Exception {
		logger.info("********************* START EXECUTE APIVT030007_01 ***********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		boolean isAdmin = false;
		List<VT030007EntityBookCarInfo> data = new ArrayList<>();
		try {
			if (!roleList.isEmpty()) {
				// get title of employee
				for (GrantedAuthority x : roleList) {
					if (x.getAuthority().equals(Constant.PMQT_ROLE_ADMIN)) {
						obj.setRole(Constant.PMQT_ROLE_ADMIN);
						isAdmin = true;
						break;
					}
					
				}
 				if(!isAdmin) {
					obj.setRole(obj.getProcessByRole());
				}
				obj.setMasterClassRoute(Constant.MASTER_CLASS_ROUTE_CAR);
				obj.setMasterClassSeat(Constant.MASTER_CLASS_SEAT_CAR);
				obj.setMasterClassType(Constant.MASTER_CLASS_TYPE_CAR);
				if(obj.getPageNumber() > 0) obj.setPageNumber(obj.getPageNumber() * obj.getPageSize());
				data = vt030007dao.findBookCarRequest(obj);
				response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
			} else {
				response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			}
		} catch (Exception e) {
			response.setData(null);
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		logger.info("********************* END EXECUTE APIVT030007_01 ***********************");
		return response;
	}

	@Override
	public ResponseEntityBase findBookCarRequestOrder(VT030007ResquestFindBookCar obj,
			Collection<GrantedAuthority> roleList) throws Exception {
		logger.info("********************* START EXECUTE APIVT030007_01 ***********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		boolean isAdmin = false;
		List<VT030007EntityBookCarInfo> data = new ArrayList<>();
		try {
			if (!roleList.isEmpty()) {
				// get title of employee
				for (GrantedAuthority x : roleList) {
					if (x.getAuthority().equals(Constant.PMQT_ROLE_ADMIN)) {
						obj.setRole(Constant.PMQT_ROLE_ADMIN);
						isAdmin = true;
						break;
					}
				}
 				if(!isAdmin) {
					obj.setRole(obj.getProcessByRole());
				}
				obj.setMasterClassRoute(Constant.MASTER_CLASS_ROUTE_CAR);
				obj.setMasterClassSeat(Constant.MASTER_CLASS_SEAT_CAR);
				obj.setMasterClassType(Constant.MASTER_CLASS_TYPE_CAR);
				if(obj.getPageNumber() > 0) obj.setPageNumber(obj.getPageNumber() * obj.getPageSize());
				ObjectMapper mapper = new ObjectMapper();
				System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
				data = vt030007dao.findBookCarRequestOrder(obj);
				response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
			} else {
				response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			}
		} catch (Exception e) {
			response.setData(null);
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		logger.info("********************* END EXECUTE APIVT030007_01 ***********************");
		return response;
	}

}
