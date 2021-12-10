package com.viettel.vtnet360.driving.request.dao;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.driving.dto.AddingTimeNotiInfo;
import com.viettel.vtnet360.driving.dto.CarBookingAlert;
import com.viettel.vtnet360.driving.dto.CarbookingNoti;
import com.viettel.vtnet360.driving.dto.DriverAndCarNotiInfo;
import com.viettel.vtnet360.driving.dto.SearchCarBooking;
import com.viettel.vtnet360.driving.entity.ListTrip;
import com.viettel.vtnet360.vt03.vt030008.entity.VT030008DriveCarInfo;

public interface BookingDAO {
  
  public int getCarBookingMinute();
  
  public int getCarBookingCount();
  
  public int countSquadByUsername(String username);
  
  public void updateAddMoreTime(ListTrip listTrip);
  
  public void updateCarBooking(String bookingId);
  
  public DriverAndCarNotiInfo getDriverAndCarNotiInfo(VT030008DriveCarInfo obj);
  
  public AddingTimeNotiInfo getCarbookingById(String carbookingId);
  
  public int checkUpdateMoreTime(ListTrip listTrip);
	
  public CarbookingNoti getBookingNoti(SearchCarBooking config);
  
  public String getCarbookingId();
  
  public List<String> getCarbookingToAlert(Date date);
  
  public List<CarBookingAlert> getCarbookingAlerted();
  
  public void deleteCarBookingAlert(String bookindId);
  
  public void createBookingAlert(String bookingId);
  
  public void updateBookingAlert(CarBookingAlert booking);
  
  public void updateCancelCarBooking(String bookingId);
  
  public void updateCancelStatusCar(String bookingId);
  
  public void updateCancelStatusDriveCar(String bookingId);
  
  public void updateCancelStatusDriver(String bookingId);
}
