package com.viettel.vtnet360.vt05.vt050006.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004DataResponse;
import com.viettel.vtnet360.vt05.vt050006.dao.VT050006DAO;
import com.viettel.vtnet360.vt05.vt050006.entity.VT050006DataResponse;
import com.viettel.vtnet360.vt05.vt050006.entity.VT050006RequestParam;
import com.viettel.vtnet360.vt05.vt050012.dao.VT050012DAO;

/**
 * @author DuyNK 04/10/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT050006ServiceImpl implements VT050006Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050006DAO vt050006DAO;

	@Autowired
  private VT050012DAO vt050012DAO;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findDataToApprove(VT050006RequestParam param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		VT050006DataResponse dataResponse = null;
		try {
			// get info of HCDV request
		  String limitDate = vt050012DAO.getLimitDateEnd();
			dataResponse = vt050006DAO.findInfoRequest(param.getRequestID());
			if(dataResponse != null) {
			// get detail of request
			List<VT050004DataResponse> listData = vt050006DAO.findInfoRequestDetail(param.getRequestID());
			dataResponse.setDateLimitEnd(Integer.valueOf(limitDate));
			dataResponse.setListData(listData);
			resp.setData(dataResponse);
			} else {
			  resp.setData(null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findDataToApproveProcess(VT050006RequestParam param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		VT050006DataResponse dataResponse = null;
		try {
			// get info of HCDV request
		  String limitDate = vt050012DAO.getLimitDateEnd();
			dataResponse = vt050006DAO.findInfoRequest(param.getRequestID());
			if(dataResponse != null) {
			// get detail of request
			List<VT050004DataResponse> listData = vt050006DAO.findInfoRequestDetailProcess(param.getRequestID());
			dataResponse.setDateLimitEnd(Integer.valueOf(limitDate));
			dataResponse.setListData(listData);
			resp.setData(dataResponse);
			} else {
			  resp.setData(null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findInfoRequestDetailProcessDetails(VT050006RequestParam param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		VT050006DataResponse dataResponse = null;
		try {
			// get info of HCDV request
		  String limitDate = vt050012DAO.getLimitDateEnd();
			dataResponse = vt050006DAO.findInfoRequest(param.getRequestID());
			if(dataResponse != null) {
			// get detail of request
			List<VT050004DataResponse> listData = vt050006DAO.findInfoRequestDetailProcessDetails(param.getRequestID());
			dataResponse.setDateLimitEnd(Integer.valueOf(limitDate));
			dataResponse.setListData(listData);
			resp.setData(dataResponse);
			} else {
			  resp.setData(null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
}