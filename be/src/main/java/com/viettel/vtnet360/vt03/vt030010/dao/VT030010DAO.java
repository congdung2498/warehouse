package com.viettel.vtnet360.vt03.vt030010.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.driving.dto.SearchData;
import com.viettel.vtnet360.vt03.vt030010.entity.BookingCarByEmployees;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;

@Repository
@Transactional
public interface VT030010DAO {
	public List<BookingCarByEmployees> getListBCbyEmployee();
	public List<VT030014ListCondition> getJourney();
	public List<VT030014ListCondition> getJourneyById(SearchData searchData);
} 
