package com.viettel.vtnet360.signVoffice.service;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.signVoffice.entity.ReportSynthetic;
import com.viettel.vtnet360.signVoffice.entity.SignVofficeEntity;
import com.viettel.vtnet360.signVoffice.entity.SignVofficeSearch;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityResultSign;
import com.viettel.vtnet360.vt04.vt040010.entity.VT040010Condition;

public interface SignVofficeService {
	public ResponseEntityBase findListSignVoffice(SignVofficeSearch signVofficeSearch,Collection<GrantedAuthority> listRole,String userName)throws Exception;
	
	public ResponseEntityBase insertSignVoffice(SignVofficeEntity signVofficeEntity,Principal principal)throws Exception;
	
//	public ResponseEntityBase findStationeryBySyntheticSignVoffice(ReportSynthetic reportSynthetic, Principal principal)throws Exception;

	public ResponseEntityBase exportStationeryBySyntheticSignVoffice(VT040010Condition condition, Principal principal)throws Exception;
	
	public ResponseEntityBase updateSignVoffice(SignVofficeEntity signVofficeEntity)throws Exception;

	public ResponseEntityBase findIssueServiceByDetailSignVoffice(VT040010Condition condition, Principal principal)throws Exception;

	public ResponseEntityBase findSignVofficeById(Long signVofficeId);
}
