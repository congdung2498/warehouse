package com.viettel.vtnet360.vt01.vt010000.service;

import java.security.Principal;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityCard;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRpGetData;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRpGetRecord;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRpMgAp;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqGetData;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqGetRecord;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqMgAp;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqUpdateRecord;

/**
 * Class interface VT010000Service
 * 
 * @author KienHT 11/08/2018
 * 
 */
public interface VT010000Service {

	/**
	 * find list entity common for 3 sceen VT010005,VT010006,VT010007
	 * 
	 * @param VT010000EntityRqGetData and Principal
	 * @return VT010000EntityRpGetData
	 */
	public VT010000EntityRpGetData findListInOut(VT010000EntityRqGetData requestParam, Principal principal,OAuth2Authentication authentication) throws Exception;

	/**
	 * find List InOut Get Record
	 * 
	 * @param requestParam VT010000EntityRqGetRecord and Principal
	 * @return VT010000EntityRpGetRecord
	 */
	public VT010000EntityRpGetRecord findListInOutGetRecord(
			VT010000EntityRqGetRecord requestParam, Principal principal) throws Exception;

	/**
	 * inOut Manager Approve
	 * 
	 * @param VT010000EntityRqMgAp and Principal principal
	 * @return VT010000EntityRpMgAp
	 */
	public VT010000EntityRpMgAp inOutManagerApprove(
			VT010000EntityRqMgAp requestParam, Principal principal,OAuth2Authentication authentication) throws Exception;
	
	
	/**
	 * update one Record
	 * 
	 * @param requestParam VT010000EntityRqUpdateRecord
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase updateRecord(
			VT010000EntityRqUpdateRecord requestParam) throws Exception;
	
	
	/**
	 * update one Record
	 * 
	 * @param VT010000EntityCard
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase idCard(
			VT010000EntityCard requestParam) throws Exception;
	
	/**
	 * auto Update Record OutDate by timeNow
	 * 
	 */
	public void autoUpdateRecordOutDate() throws Exception;
	
	
	/**
	 * auto Update Record OutDate by timeNow Ver2
	 * 
	 */
	public void autoUpdateRecordOutDateVer2() throws Exception;
	
	

	
}
