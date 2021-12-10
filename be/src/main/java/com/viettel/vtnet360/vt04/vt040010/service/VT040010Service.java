package com.viettel.vtnet360.vt04.vt040010.service;

import java.io.File;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.issuesService.entity.IssuesServiceSearch;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010Condition;

/**
 * interface service of VT040010
 * 
 * @author ThangBT 17/09/2018
 *
 */
public interface VT040010Service {

	public ResponseEntityBase findListReportService(VT040010Condition condition, Collection<GrantedAuthority> listRole)
			throws Exception;

	public ResponseEntityBase countTotalRecord(VT040010Condition condition, Collection<GrantedAuthority> listRole)
			throws Exception;

	public ResponseEntityBase findListStationery(VT040010Condition conStat) throws Exception;

	public ResponseEntityBase countTotalRecordStationery(VT040010Condition conStat) throws Exception;

	public File createExcel(IssuesServiceSearch issuesServiceSearch, Collection<GrantedAuthority> listRole) throws Exception;

}
