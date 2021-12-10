package com.viettel.vtnet360.vt07.vt070002.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt07.vt070002.entity.ProjectDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.SearchFolderResponse;

public interface FolderService {
	public List<SearchFolderResponse> searchFolderByNameOrQrCode(String keyword, int pageNumber, int pageSize, Collection<GrantedAuthority> userRoles);
	List<ProjectDetail> getProjectsInFolder(long folderId, Collection<GrantedAuthority> userRoles);
	List<ProjectDetail> getProjectsNotInFolder(long folderId, Collection<GrantedAuthority> userRoles);
}
