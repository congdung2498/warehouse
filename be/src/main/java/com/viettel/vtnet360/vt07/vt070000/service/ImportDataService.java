package com.viettel.vtnet360.vt07.vt070000.service;
import java.util.Collection;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt07.vt070000.entity.ImportedDataRow;
import com.viettel.vtnet360.vt07.vt070000.entity.search.DocumentDetailSearch;

public interface ImportDataService {
	public Workbook getExcelTemplate(String propertyName, Collection<GrantedAuthority> userRoles);
	public Workbook bindResultDataToTemplate(List<ImportedDataRow> dataRows, Workbook template, String importType, Collection<GrantedAuthority> userRoles);
	public Object importFullData(List<ImportedDataRow> dataRows, Collection<GrantedAuthority> userRoles);
	public Object importSimpleData(List<ImportedDataRow> dataRows, Collection<GrantedAuthority> userRoles);
	public Workbook bindSearchDocumentData(List<DocumentDetailSearch> dataRows, Workbook template, Collection<GrantedAuthority> userRoles);
}
