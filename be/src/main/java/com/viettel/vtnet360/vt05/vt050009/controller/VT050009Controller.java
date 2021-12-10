//package com.viettel.vtnet360.vt05.vt050009.controller;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.viettel.vtnet360.vt00.common.Constant;
//import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
//import com.viettel.vtnet360.vt05.vt050009.entity.VT050009RequestParam;
//import com.viettel.vtnet360.vt05.vt050009.service.VT050009Service;
//
///**
// * @author DuyNK 09/10/2018
// */
//@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_CVP')")
//@RestController
//@RequestMapping("/com/viettel/vtnet360/vt05/vt050009")
//public class VT050009Controller {
//
//	private final Logger logger = Logger.getLogger(this.getClass());
//
//	@Autowired
//	private VT050009Service vt050009Service;
//
//	/**
//	 * find data of 1 request to approve
//	 * 
//	 * @param param
//	 * @return ResponseEntity<ResponseEntityBase>
//	 */
//	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
//	public ResponseEntity<ResponseEntityBase> vt050005FindDataToApprove(@RequestBody VT050009RequestParam param) {
//		logger.info("*********** vt050009_01 Find Data To Approve start***********");
//		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
//		try {
//			resp = vt050009Service.findDataToApprove(param);
//		} catch (Exception e) {
//			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
//		}
//		logger.info("*********** vt050009_01 Find Data To Approve end***********");
//		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
//	}
//}
