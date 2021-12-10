package com.viettel.vtnet360.vt03.vt030010.service;



import com.viettel.vtnet360.driving.dto.SearchData;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;


public interface VT030010Service {
	public ResponseEntityBase getListBCbyEmployee();
	public ResponseEntityBase getJourney();
	public ResponseEntityBase getJourneyById(SearchData searchData);
}
