package com.viettel.vtnet360.vt05.vt050015.service;

import java.io.File;
import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.viettel.vtnet360.vt05.vt050015.entity.ReportStationery;

public interface VT050015ExcelVPPService {
	public File createExcelVPP(ReportStationery reportStationery, Principal principal, OAuth2Authentication authentication,Collection<GrantedAuthority> roleList) throws Exception;
}
