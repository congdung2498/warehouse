package com.viettel.vtnet360.vt03.vt030013.service;

import java.io.File;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030013.entity.CarDriverManage;
import com.viettel.vtnet360.vt03.vt030013.entity.CarPlate;
import com.viettel.vtnet360.vt03.vt030013.entity.Driver;
import com.viettel.vtnet360.vt03.vt030013.entity.ReportSearch;
import com.viettel.vtnet360.vt03.vt030013.entity.RequestSearch;
import com.viettel.vtnet360.vt05.vt050015.entity.ReportStationery;

public interface VT030013Service {
	public ResponseEntityBase getCarDriverManage(RequestSearch rqSearch);

	public ResponseEntityBase getLocationList();

	public ResponseEntityBase getLicensePlate(CarPlate plate, Collection<GrantedAuthority> roleList);

	public ResponseEntityBase getSquadIdByQLDX(String userName);
	
	public ResponseEntityBase getDriverInSquad(Driver driver, Collection<GrantedAuthority> roleList);
	
	public File createExcel(CarDriverManage carDriverManage, String loginUserName) throws Exception;
	
	public ResponseEntityBase getCarDriverManageCount(RequestSearch rqSearch);
	 
	public ResponseEntityBase getListFreeCar(RequestSearch rqSearch);
}
