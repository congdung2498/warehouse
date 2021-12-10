package com.viettel.vtnet360.vt01.vt010011.service;

import java.io.File;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt01.vt010011.entity.VT010011ListCondition;
import com.viettel.vtnet360.vt01.vt010011.entity.VT010011ListEmployee;

/**
 * interface of services VT010011
 * 
 * @author ThangBT 07/09/2018
 *
 */
public interface VT010011Service {

	/**
	 * get list of employee
	 * 
	 * @param empInfo
	 * @return List<VT010011ListEmployee>
	 * @throws Exception
	 */
	public ResponseEntityBase findListEmployee(VT010011ListEmployee emp) throws Exception;

	/**
	 * get list registration go out
	 * 
	 * @param vlc
	 * @param principal
	 * @return List<VT010011ListCondition>
	 * @throws Exception
	 */
	public ResponseEntityBase findListInOut(VT010011ListCondition vlc, Collection<GrantedAuthority> listRole)
			throws Exception;

	/**
	 * count total record VT010011
	 * 
	 * @param vlc
	 * @param principal
	 * @return number of records
	 * @throws Exception
	 */
	public ResponseEntityBase countTotalRecord(VT010011ListCondition vlc, Collection<GrantedAuthority> listRole)
			throws Exception;

	/**
	 * get excel file report
	 * 
	 * @param vlc
	 * @param listRole
	 * @return excel file
	 * @throws Exception
	 */
	public File createExcelOutputExcel(VT010011ListCondition vlc, Collection<GrantedAuthority> listRole)
			throws Exception;

}
