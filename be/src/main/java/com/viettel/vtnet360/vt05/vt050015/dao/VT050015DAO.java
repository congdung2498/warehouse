package com.viettel.vtnet360.vt05.vt050015.dao;

import java.util.List;

import com.viettel.vtnet360.vt05.vt050002.entity.Employee;
import com.viettel.vtnet360.vt05.vt050002.entity.Receiver;
import com.viettel.vtnet360.vt05.vt050015.entity.FullfillStationery;
import com.viettel.vtnet360.vt05.vt050015.entity.ReportStationery;
import com.viettel.vtnet360.vt05.vt050015.entity.ReportStationeryAll;
import com.viettel.vtnet360.vt05.vt050015.entity.VT050015StationeryExcel;

public interface VT050015DAO {
	public List<Employee> getEmployee(Employee employee);

	public List<ReportStationery> getReportStationery();

	public List<FullfillStationery> getFullfillStationery(ReportStationery rpStationery);

	public String getLastUser(String lastUser);

	public List<ReportStationery> searchReportStationery(ReportStationery rpStationery);
	
	public List<ReportStationeryAll> searchReportStationeryAll(ReportStationery rpStationery);

	/**
	 * @author VinhNVQ
	 * 
	 * @param reportStationery
	 * @return
	 */
	public List<VT050015StationeryExcel> findListStationeryExcelVPP(ReportStationery reportStationery);
	
	public Receiver getLoginInfo(String userName);
	
	public int  countReportStationery(ReportStationery rpStationery);
	
	public List<Employee> getHcdv(Employee employee);
	
	public List<Employee> getEmployeeTCT(Employee employee);
}
