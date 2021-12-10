package com.viettel.vtnet360.vt03.vt030009.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntitySystemCode;

public class VT030009ResponseCarRoute extends VT030000EntitySystemCode{
	// CAR_BOOKING.CAR_BOOKING_ID
	private String bookCarId;
	
	// CAR_BOOKING.EMPLOYEE_USER_NAME
	private String userName;
	
	// QLDV_EMPLOYEE.FULL_NAME
	private String fullName;
	
	// QLDV_EMPLOYEE.FULL_NAME
	private String fullNameRequester;
	
	// QLDV_EMPLOYEE.PHONE_NUMNER
	private String emplPhone;
	
	// CARS.CAR_ID
	private String carId;
	
	// M_SYSTEM_CODE.CODE_NAME WITH MASTER_CLASS = S001
	private String type;
	
	// M_SYSTEM_CODE.CODE_NAME WITH MASTER_CLASS = S002
	private String seat;
	
	// M_SYSTEM_CODE.CODE_NAME WITH MASTER_CLASS = S003
	private String route;
	
	// CAR_BOOKING.LICENSE_PLATE 
	private String licensePlate;
	
	// CAR_BOOKING.START 
	private Date dateStart;
	
	// CAR_BOOKING.END 
	private Date dateEnd;
	
	// CAR_BOOKING.TOTAL_PASSAGE 
	private int totalPartner;
	
	// PLACE.PLACE_NAME
	private String startPlace;
	
	// PLACE.PLACE_NAME
	private String targetPlace;
	
	// CAR_BOOKING.ROUTE 
	private String routeWay;
	
	// CAR_BOOKING.STATUS 
	private int status;
	
	// list status
	private int[] listStatus;
	
	private String processByRole;
	
	// page Number
	private int pageNumber;
	
	// pageSize
	private int pageSize;
	
	// detailUnit
	private String detailUnit ;
	
	// phoneNumber
	private String phoneNumber ;
	
	// fullNameUser
	private String fullNameUser;
	
	// driverSquadName
	private String squadName;
	
	// lai xe danh gia
	private int driverRating ;
	
	// lai xe phan hoi
	private String driverFeedback ;
	
	// thoi gian thuc te xuat phat
	private Date timeStart;

	// thoi gian thuc te den
	private Date timeFinish;
	
	//thoi gian thuc te xuat phat
	private Date realStart;

	// thoi gian thuc te den
	private Date realEnd;
	
	private Integer ratting;
	
	private Integer carBookingType;
	
	private List<VT030009UserInfor> userInforList;
	
	


	public Date getRealStart() {
    return realStart;
  }

  public void setRealStart(Date realStart) {
    this.realStart = realStart;
  }

  public Date getRealEnd() {
    return realEnd;
  }

  public void setRealEnd(Date realEnd) {
    this.realEnd = realEnd;
  }

  public Integer getRatting() {
		return ratting;
	}

	public void setRatting(Integer ratting) {
		this.ratting = ratting;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}


	public Date getTimeFinish() {
		return timeFinish;
	}

	public void setTimeFinish(Date timeFinish) {
		this.timeFinish = timeFinish;
	}

	public int getDriverRating() {
		return driverRating;
	}

	public void setDriverRating(int driverRating) {
		this.driverRating = driverRating;
	}

	public String getDriverFeedback() {
		return driverFeedback;
	}

	public void setDriverFeedback(String driverFeedback) {
		this.driverFeedback = driverFeedback;
	}

	public String getDetailUnit() {
		return detailUnit;
	}

	public void setDetailUnit(String detailUnit) {
		this.detailUnit = detailUnit;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFullNameUser() {
		return fullNameUser;
	}

	public void setFullNameUser(String fullNameUser) {
		this.fullNameUser = fullNameUser;
	}

	
	public String getSquadName() {
		return squadName;
	}

	public void setSquadName(String squadName) {
		this.squadName = squadName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullNameRequester() {
		return fullNameRequester;
	}

	public void setFullNameRequester(String fullNameRequester) {
		this.fullNameRequester = fullNameRequester;
	}

	public String getEmplPhone() {
		return emplPhone;
	}

	public void setEmplPhone(String emplPhone) {
		this.emplPhone = emplPhone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public int getTotalPartner() {
		return totalPartner;
	}

	public void setTotalPartner(int totalPartner) {
		this.totalPartner = totalPartner;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getTargetPlace() {
		return targetPlace;
	}

	public void setTargetPlace(String targetPlace) {
		this.targetPlace = targetPlace;
	}

	public String getRouteWay() {
		return routeWay;
	}

	public void setRouteWay(String routeWay) {
		this.routeWay = routeWay;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int[] getListStatus() {
		return listStatus;
	}

	public void setListStatus(int[] listStatus) {
		this.listStatus = listStatus;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getBookCarId() {
		return bookCarId;
	}

	public void setBookCarId(String bookCarId) {
		this.bookCarId = bookCarId;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getProcessByRole() {
		return processByRole;
	}

	public void setProcessByRole(String processByRole) {
		this.processByRole = processByRole;
	}

	public List<VT030009UserInfor> getUserInforList() {
		return userInforList;
	}

	public void setUserInforList(List<VT030009UserInfor> userInforList) {
		this.userInforList = userInforList;
	}

	public Integer getCarBookingType() {
		return carBookingType;
	}

	public void setCarBookingType(Integer carBookingType) {
		this.carBookingType = carBookingType;
	}

	
	
	

}
