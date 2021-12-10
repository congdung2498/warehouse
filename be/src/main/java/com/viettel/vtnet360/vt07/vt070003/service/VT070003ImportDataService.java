package com.viettel.vtnet360.vt07.vt070003.service;
import java.util.Collection;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070003.entity.FileUploadInfo;
import com.viettel.vtnet360.vt07.vt070003.entity.ImportedDataRow;

public interface VT070003ImportDataService {
	public Workbook getExcelTemplate(String propertyName, Collection<GrantedAuthority> userRoles);
	public Workbook bindResultDataToTemplate(List<ImportedDataRow> dataRows, Workbook template, String importType, Collection<GrantedAuthority> userRoles);
	public Object importFullData(List<ImportedDataRow> dataRows, Collection<GrantedAuthority> userRoles);
	
	public ResponseEntityBase uploadFile(MultipartFile file,OAuth2Authentication oauth,Collection<GrantedAuthority> userRoles, long documentId,long type);
	
	public FileUploadInfo viewFileUpload(OAuth2Authentication oauth,Collection<GrantedAuthority> userRoles, long documentId,long type);

	public Object importSimpleData(List<ImportedDataRow> dataRows, Collection<GrantedAuthority> userRoles);
}
