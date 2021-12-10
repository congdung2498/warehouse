package com.viettel.vtnet360.vt07.vt070002.service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt07.vt070002.dao.VT070002DAO;
import com.viettel.vtnet360.vt07.vt070002.entity.ProjectDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.SearchFolderResponse;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class FolderServiceImpl implements FolderService{
	
	@Autowired
	VT070002DAO vt070002dao;
	
	private int getUnit(Collection<GrantedAuthority> userRoles) {
		for (GrantedAuthority temp : userRoles) {
	        if (Constant.PMQT_ROLE_WAREHOUSE_GROUP.equalsIgnoreCase(temp.getAuthority())) {
	        	return 1;
	        }
		}
		return 0;
	}
	
	@Override
	public List<SearchFolderResponse> searchFolderByNameOrQrCode(String keyword, int pageNumber, int pageSize, Collection<GrantedAuthority> userRoles){
		List<SearchFolderResponse> result = new ArrayList<SearchFolderResponse>();
		int offset = (pageNumber - 1) * pageSize;
		int unit = getUnit(userRoles);
		int totalFoundRecord = unit == 1 ? vt070002dao.searchFolderByNameOrQrCodeTotalRecordGroup(keyword, unit)
				: vt070002dao.searchFolderByNameOrQrCodeTotalRecord(keyword, unit);
		result.addAll(unit == 1 ? vt070002dao.searchFolderByNameOrQrCodeGroup(keyword, offset, pageSize, unit)
				: vt070002dao.searchFolderByNameOrQrCode(keyword, offset, pageSize, unit));
		if(result.size()>0) {
			result.get(0).setTotalFoundRecord(totalFoundRecord);
		}
		return result;
	}

	@Override
	public List<ProjectDetail> getProjectsInFolder(long folderId, Collection<GrantedAuthority> userRoles) {
		List<ProjectDetail> result = new ArrayList<ProjectDetail>();
		if(getUnit(userRoles) == 1) {
			result.addAll(vt070002dao.getProjectsInFolderGroup(folderId));
		}
		else {
			result.addAll(vt070002dao.getProjectsInFolder(folderId));
		}
		return result;
	}

	@Override
	public List<ProjectDetail> getProjectsNotInFolder(long folderId, Collection<GrantedAuthority> userRoles) {
		List<ProjectDetail> result = new ArrayList<ProjectDetail>();
		result.addAll(getUnit(userRoles) == 1 ? vt070002dao.getProjectsNotInFolderGroup(folderId, getUnit(userRoles))
				: vt070002dao.getProjectsNotInFolder(folderId, getUnit(userRoles)));
		return result;
	}

}
