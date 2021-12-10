package com.viettel.vtnet360.vt05.vt050015.service;

import java.io.File;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050002.entity.Employee;
import com.viettel.vtnet360.vt05.vt050015.entity.ReportStationery;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface VT050015Service {
	public ResponseEntityBase getEmployee(Employee employee);

	public ResponseEntityBase getReportStationery();

	public ResponseEntityBase getFullfillStationery(ReportStationery rpStationery);

	public ResponseEntityBase getLastUser(String userName);

	public ResponseEntityBase searchReportStationery(ReportStationery rpStationery,Collection<GrantedAuthority> roleList);

	public File createExcel(ReportStationery condition, String loginUserName,Collection<GrantedAuthority> roleList) throws Exception;

	public ResponseEntityBase getLoginInfo(String userName);
	
	public ResponseEntityBase countReportStationery(ReportStationery rpStationery,Collection<GrantedAuthority> roleList);
	
	public ResponseEntityBase getHcdv(Employee employee);
	
	public ResponseEntityBase getEmployeeTCT(Employee employee);

}
