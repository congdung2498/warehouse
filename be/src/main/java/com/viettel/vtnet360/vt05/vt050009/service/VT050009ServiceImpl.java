//package com.viettel.vtnet360.vt05.vt050009.service;
//
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.viettel.vtnet360.vt00.common.Constant;
//import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
//import com.viettel.vtnet360.vt05.vt050004.entity.VT050004DataResponse;
//import com.viettel.vtnet360.vt05.vt050009.dao.VT050009DAO;
//import com.viettel.vtnet360.vt05.vt050009.entity.VT050009DataResponse;
//import com.viettel.vtnet360.vt05.vt050009.entity.VT050009RequestParam;
//
///**
// * @author DuyNK 04/10/2018
// */
//@Service
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//public class VT050009ServiceImpl implements VT050009Service {
//
//	private final Logger logger = Logger.getLogger(this.getClass());
//
//	@Autowired
//	private VT050009DAO vt050009DAO;
//
//	@Override
//	@Transactional(readOnly = true)
//	public ResponseEntityBase findDataToApprove(VT050009RequestParam param) {
//		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
//		VT050009DataResponse dataResponse = null;
//		try {
//			// get info of VPTCT request
//			dataResponse = vt050009DAO.findInfoRequest(param.getRequestID());
//			// get detail of request
//			List<VT050004DataResponse> listData = vt050009DAO.findInfoRequestDetail(param.getRequestID());
//			dataResponse.setListData(listData);
//			resp.setData(dataResponse);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw (e);
//		}
//		return resp;
//	}
//}