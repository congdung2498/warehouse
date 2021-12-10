package com.viettel.vtnet360.vt04.vt040007.service;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040007.entity.VT040007EntityRqExecutive;
import com.viettel.vtnet360.vt04.vt040007.entity.VT040007EntityRqFindSt;

import java.security.Principal;

/**
 * Class VT040007Service
 * 
 * @author KienHT 8/10/2018
 * 
 */
public interface VT040007Service {

	/**
	 * User executiveService
	 * 
	 * @param VT040007EntityRqExecutive,Principal
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase executiveService(VT040007EntityRqExecutive requestParam, Principal principal)
			throws Exception;

	/**
	 * User find Stationery
	 * 
	 * @param VT040007EntityRqExecutive,Principal
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findStationery(VT040007EntityRqFindSt requestParam, Principal principal);

}
