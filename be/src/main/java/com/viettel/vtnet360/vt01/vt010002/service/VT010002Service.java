package com.viettel.vtnet360.vt01.vt010002.service;

import java.security.Principal;

import com.viettel.vtnet360.vt01.vt010002.entity.VT010002EntityRpUpdate;
import com.viettel.vtnet360.vt01.vt010002.entity.VT010002EntityRqUpdate;

/**
 * Class interface VT010002Service for VT010002
 * 
 * @author KienHT 11/08/2018
 * 
 */
public interface VT010002Service {

	/**
	 * inOut Register Update
	 * 
	 * @param VT010002EntityRqUpdate
	 * @param Principal
	 * @return VT010002EntityRpUpdate
	 */
	public VT010002EntityRpUpdate inOutRegisterUpdate(VT010002EntityRqUpdate requestParam, Principal principal)
			throws Exception;
}
