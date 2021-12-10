package com.viettel.vtnet360.vt03.vt030014.service;

import java.io.File;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.driving.entity.ListTrip;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListEmployee;

/**
 * interface of services VT030014
 * 
 * @author ThangBT 07/09/2018
 *
 */
public interface VT030014Service {

	public ResponseEntityBase findListEmployee(VT030014ListEmployee emp) throws Exception;

	public ResponseEntityBase findListEmployeePhone(VT030014ListEmployee emp) throws Exception;

	public ResponseEntityBase findListDriver(VT030014ListEmployee driverInfo) throws Exception;

	public ResponseEntityBase findListTrip(VT030014ListCondition condition, Collection<GrantedAuthority> listRole)
			throws Exception;

	public ResponseEntityBase countTotalRecord(VT030014ListCondition condition, Collection<GrantedAuthority> listRole)
			throws Exception;

	public File createExcelOutputExcel(VT030014ListCondition condition, Collection<GrantedAuthority> listRole)
			throws Exception;

}
