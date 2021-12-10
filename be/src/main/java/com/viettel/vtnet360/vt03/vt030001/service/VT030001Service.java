package com.viettel.vtnet360.vt03.vt030001.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityEmployee;

/**
 * 
 * @author CuongHD 08/09/2018
 *
 */
public interface VT030001Service {
	/**
	 * tim kiem doi xe
	 * 
	 * @param VT030000EntityDriveSquad object
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase findSquad(VT030000EntityDriveSquad object) throws Exception;
	
	/**
	 * Tim kiem tat ca lai xe thuoc vi tri
	 * 
	 * @param int placeId
	 * @return VT030000EntityEmployee
	 */
	public ResponseEntityBase findDriver(VT030000EntityEmployee obj);
	
	/**
	 * Them moi doi xe
	 * 
	 * @param VT030000EntityDriveSquad object
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase insertSquad(VT030000EntityDriveSquad object, Collection<GrantedAuthority> roleList) throws Exception;
	
	/**
	 * Xoa doi xe
	 * 
	 * @param VT030000EntityDriveSquad object
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase deleteSquad(VT030000EntityDriveSquad object, Collection<GrantedAuthority> roleList) throws Exception;
	

}
