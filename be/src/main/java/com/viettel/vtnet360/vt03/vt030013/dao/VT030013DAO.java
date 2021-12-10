package com.viettel.vtnet360.vt03.vt030013.dao;

import java.util.List;

import com.viettel.vtnet360.vt03.vt030001.entity.DriveSquad;
import com.viettel.vtnet360.vt03.vt030002.entity.Seat;
import com.viettel.vtnet360.vt03.vt030002.entity.Type;
import com.viettel.vtnet360.vt03.vt030013.entity.CarDriverManage;
import com.viettel.vtnet360.vt03.vt030013.entity.CarPlate;
import com.viettel.vtnet360.vt03.vt030013.entity.Driver;
import com.viettel.vtnet360.vt03.vt030013.entity.ReportSearch;
import com.viettel.vtnet360.vt03.vt030013.entity.RequestSearch;
import com.viettel.vtnet360.vt03.vt030013.entity.Unit;
import com.viettel.vtnet360.vt05.vt050015.entity.ReportStationery;

public interface VT030013DAO {
	
	public List<CarDriverManage> getCarDriverManage(RequestSearch rqSearch);
	
	public DriveSquad getSquadIdByQLDX(String userName);

	public List<Unit> getLocationList();

	public List<Seat> getCarSeat();
	
	public List<Type> getCarType();
	
	public List<CarPlate> getLicensePlate(CarPlate plate);
	
	//public List<CarPlate> getLicensePlate(CarPlate plate);
	
	public List<Driver> getDriverInSquad(Driver driver);
	
	public List<CarDriverManage> reportCarDriverManage(CarDriverManage carDriverManage);
	
	public Integer getCarDriverManageCount(RequestSearch rqSearch);
	
	public List<CarDriverManage> getListFreeCar(RequestSearch rqSearch);

	public int totalFreeCar(RequestSearch rqSearch);
}
