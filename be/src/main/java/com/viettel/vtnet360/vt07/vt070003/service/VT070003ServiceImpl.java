package com.viettel.vtnet360.vt07.vt070003.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070003.dao.VT070003DAO;
import com.viettel.vtnet360.vt07.vt070003.entity.OfficialDispatch;
import com.viettel.vtnet360.vt07.vt070003.entity.OfficialDispatchDoc;

@Service
public class VT070003ServiceImpl implements VT070003Service {

	@Autowired
	VT070003DAO vt070003dao;

	@Override
	public FolderDetail getOfficialInFolderById(long id) {
		FolderDetail folderDetail = vt070003dao.getOfficialInFolderById(id);
		return folderDetail;
	}

	@Override
	public List<OfficialDispatchDoc> getOfficialDispatchDocById(long officialDispatchId) {
		return vt070003dao.getOfficialDispatchDocById(officialDispatchId);
	}

	@Override
	public OfficialDispatch getOfficialDispatchById(long officialDispatchId) {
		return vt070003dao.getOfficialDispatchById(officialDispatchId);
	}
}
