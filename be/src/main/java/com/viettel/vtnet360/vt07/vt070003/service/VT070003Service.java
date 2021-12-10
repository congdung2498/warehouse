package com.viettel.vtnet360.vt07.vt070003.service;

import java.util.List;

import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070003.entity.OfficialDispatch;
import com.viettel.vtnet360.vt07.vt070003.entity.OfficialDispatchDoc;

public interface VT070003Service {
	FolderDetail getOfficialInFolderById(long id);

	List<OfficialDispatchDoc> getOfficialDispatchDocById(long officialDispatchId);

	OfficialDispatch getOfficialDispatchById(long officialDispatchId);

}
