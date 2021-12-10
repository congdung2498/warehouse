package com.viettel.vtnet360.signVoffice.request.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.signVoffice.entity.ReportSynthetic;
import com.viettel.vtnet360.signVoffice.entity.SignVofficeEntity;
import com.viettel.vtnet360.signVoffice.entity.SignVofficeSearch;
import com.viettel.vtnet360.signVoffice.entity.Stationery;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010Condition;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010StationeryExcel;

@Repository
public interface SignVofficeDao {

	public List<SignVofficeEntity> findListSignVoffice(SignVofficeSearch signVofficeSearch);

	public int totalSignVoffice(SignVofficeSearch signVofficeSearch);
	
	public void insertSignVoffice(SignVofficeEntity signVofficeEntity);
	
	public int updateIssuedServiceReport(SignVofficeEntity signVofficeEntity);
	
	public List<ReportSynthetic> findIssueServiceBySyntheticSignVoffice(ReportSynthetic reportSynthetic);
	
	public List<Stationery> findStationeryById(Stationery stationery);

	public int updateSignVoffice(SignVofficeEntity signVofficeEntity);
	
	public List<VT040010StationeryExcel> findIssueServiceByDetailSignVoffice(VT040010Condition condition);
	
	public ReportSynthetic findUnitByListId(ReportSynthetic reportSynthetic);
	
	public String findUnitByUserName(String  userName);
	
	public SignVofficeEntity findSignVofficeById(Long signVofficeId);
	
	public List<VT040010StationeryExcel> findIssueServiceByIds(@Param("services")List<VT040010StationeryExcel> services);
	
	public List<SignVofficeEntity> findIssueServiceBySignVofficeId(long signVofficeId);
}
