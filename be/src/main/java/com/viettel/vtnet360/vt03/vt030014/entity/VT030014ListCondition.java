package com.viettel.vtnet360.vt03.vt030014.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class entity condition to query in database VT030014
 * 
 * @author ThangBT 12/09/2018
 *
 */
public class VT030014ListCondition extends BaseEntity {


		/** id of car booking */
		private String carBookingId;
		/** user name of login user */
		private String loginUserName;
	
		private String driverName;
		
		/** role of login user */
		private String loginRole;
	
		/** list of unit id */
		private String unitIdManager;
	
		/** id of drive squad */
		private String squadId;
	
		/** name of drive squad */
		private String squadName;
	
		/** user name of driver */
		private String userNameDriver;
	
		/** id of driver */
		private String driverInfo;
	
		/** id of car */
		private String carType;
		
		/** name of car */
		private String carName;
	
		/** user name of employee who is booker */
		private String userNameBooker;
	
		/** info of employee */
		private String empName;
		/** phone number of employee */
		private String empPhone;
		
		private int[] selectedCarStatus;
	
		/** start date search */
		private Date startDate;
	
		/** end date search */
		private Date endDate;
	
		/** start date search */
		private Date dateStart;
	
		/** end date search */
		private Date dateEnd;
		/** license plate */
		private String licensePlate;
	
		/** total record */
		private int numOfRows;
	
		/** select from which row */
		private int startRow;
	
		/** number of row to select */
		private int rowSize;
	
		/** average rating */
		private String averageRating;
	
		/** realTime start */
		private Date timeReadyStart;
	
		/** realTime end */
		private Date timeReadyEnd;
	
		/** number of passenger */
		private int numOfPassenger;
		
		/** listOfPassenger */
		private String listOfPassenger;
	
		/** status of trips */
		private int statusTrips;
	
		/** route */
		private String route;
		
		/** routeType */
		private String routeType;
		
		/** seat */
		private String seat;
		
		/** rate of trip */
		private int rating;
	
		private String reasonRefuse;
	
		/** feedback of passenger */
		private String feedback;
		
		private String appoverQltt;
		
		private String appoverLddv;
		
		private String appoverCvp;
		
		private int flagQltt;
		
		private int flagLddv;
		
		private int flagCvp;
		
		private String createUser;
		
		private String updateUser;
		
		private String reasonAssigner;
		
		private int startPlace;
		
		private String tagetPlace;
		
		private String reason;
		
		private String detailUnit;
		
		private Boolean isAQltt;
		
		private Boolean isAQldv;
		
		private Boolean isACvp;
	
		private String placeName;
	
		private String journey;
		
		private String type;
		
		private String seatName;
		
		private String fullName;
		
		private String driverRating ;
		
		private String driverFeedback ;
		
		private String typeId ;
		
		private String seatId;
		
		private String bookedActualyTime ;
		
		private String timeAtTager ;
		
		private String timeFinish ;
		
		private String carId;
		
		private String driverUser;
		
		private String realyCarType;
		
		
		
	public VT030014ListCondition() {
		super();
	}
	
	
	
	
	  public String getBookedActualyTime() {
		return bookedActualyTime;
	}




	public void setBookedActualyTime(String bookedActualyTime) {
		this.bookedActualyTime = bookedActualyTime;
	}




	public String getTimeAtTager() {
		return timeAtTager;
	}




	public void setTimeAtTager(String timeAtTager) {
		this.timeAtTager = timeAtTager;
	}




	public String getTimeFinish() {
		return timeFinish;
	}




	public void setTimeFinish(String timeFinish) {
		this.timeFinish = timeFinish;
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




	public String getSeatId() {
		return seatId;
	}




	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}




	public String getTypeId() {
		return typeId;
	}




	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}




	public String getDriverRating() {
		return driverRating;
	}




	public void setDriverRating(String driverRating) {
		this.driverRating = driverRating;
	}




	public String getDriverFeedback() {
		return driverFeedback;
	}




	public void setDriverFeedback(String driverFeedback) {
		this.driverFeedback = driverFeedback;
	}




	public String getReasonAssigner() {
		return reasonAssigner;
	}



	public void setReasonAssigner(String reasonAssigner) {
		this.reasonAssigner = reasonAssigner;
	}



	public String getDriverName() {
		return driverName;
	}



	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}



	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getSeatName() {
		return seatName;
	}


	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}


	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getJourney() {
		return journey;
	}


	public void setJourney(String journey) {
		this.journey = journey;
	}


	public String getPlaceName() {
		return placeName;
	}


	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}


	public String getEmpPhone() {
		return empPhone;
	}


	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}


	public Boolean getIsAQltt() {
		return isAQltt;
	}


	public void setIsAQltt(Boolean isAQltt) {
		this.isAQltt = isAQltt;
	}


	public Boolean getIsAQldv() {
		return isAQldv;
	}


	public void setIsAQldv(Boolean isAQldv) {
		this.isAQldv = isAQldv;
	}


	public Boolean getIsACvp() {
		return isACvp;
	}


	public void setIsACvp(Boolean isACvp) {
		this.isACvp = isACvp;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public String getLicensePlate() {
		return licensePlate;
	}


	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}


	public String getDetailUnit() {
		return detailUnit;
	}

	public void setDetailUnit(String detailUnit) {
		this.detailUnit = detailUnit;
	}



	public String getCarBookingId() {
		return carBookingId;
	}



	public void setCarBookingId(String carBookingId) {
		this.carBookingId = carBookingId;
	}



	public Date getTimeReadyStart() {
		return timeReadyStart;
	}



	public void setTimeReadyStart(Date timeReadyStart) {
		this.timeReadyStart = timeReadyStart;
	}



	public Date getTimeReadyEnd() {
		return timeReadyEnd;
	}



	public void setTimeReadyEnd(Date timeReadyEnd) {
		this.timeReadyEnd = timeReadyEnd;
	}



	public int getNumOfPassenger() {
		return numOfPassenger;
	}



	public void setNumOfPassenger(int numOfPassenger) {
		this.numOfPassenger = numOfPassenger;
	}



	public String getListOfPassenger() {
		return listOfPassenger;
	}



	public void setListOfPassenger(String listOfPassenger) {
		this.listOfPassenger = listOfPassenger;
	}



	public int getStatusTrips() {
		return statusTrips;
	}



	public void setStatusTrips(int statusTrips) {
		this.statusTrips = statusTrips;
	}



	public String getRoute() {
		return route;
	}



	public void setRoute(String route) {
		this.route = route;
	}



	public String getRouteType() {
		return routeType;
	}



	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}



	public String getSeat() {
		return seat;
	}



	public void setSeat(String seat) {
		this.seat = seat;
	}



	public int getRating() {
		return rating;
	}



	public void setRating(int rating) {
		this.rating = rating;
	}



	public String getReasonRefuse() {
		return reasonRefuse;
	}



	public void setReasonRefuse(String reasonRefuse) {
		this.reasonRefuse = reasonRefuse;
	}



	public String getFeedback() {
		return feedback;
	}



	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}



	public String getAppoverQltt() {
		return appoverQltt;
	}



	public void setAppoverQltt(String appoverQltt) {
		this.appoverQltt = appoverQltt;
	}



	public String getAppoverLddv() {
		return appoverLddv;
	}



	public void setAppoverLddv(String appoverLddv) {
		this.appoverLddv = appoverLddv;
	}



	public String getAppoverCvp() {
		return appoverCvp;
	}



	public void setAppoverCvp(String appoverCvp) {
		this.appoverCvp = appoverCvp;
	}



	public int getFlagQltt() {
		return flagQltt;
	}



	public void setFlagQltt(int flagQltt) {
		this.flagQltt = flagQltt;
	}



	public int getFlagLddv() {
		return flagLddv;
	}



	public void setFlagLddv(int flagLddv) {
		this.flagLddv = flagLddv;
	}



	public int getFlagCvp() {
		return flagCvp;
	}



	public void setFlagCvp(int flagCvp) {
		this.flagCvp = flagCvp;
	}



	public String getCreateUser() {
		return createUser;
	}



	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}



	public String getUpdateUser() {
		return updateUser;
	}



	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}


	public int getStartPlace() {
		return startPlace;
	}



	public void setStartPlace(int startPlace) {
		this.startPlace = startPlace;
	}



	public String getTagetPlace() {
		return tagetPlace;
	}



	public void setTagetPlace(String tagetPlace) {
		this.tagetPlace = tagetPlace;
	}



	public String getReason() {
		return reason;
	}



	public void setReason(String reason) {
		this.reason = reason;
	}



	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getLoginRole() {
		return loginRole;
	}

	public void setLoginRole(String loginRole) {
		this.loginRole = loginRole;
	}

	public String getUnitIdManager() {
		return unitIdManager;
	}

	public void setUnitIdManager(String unitIdManager) {
		this.unitIdManager = unitIdManager;
	}

	

	
	public String getSquadId() {
		return squadId;
	}




	public void setSquadId(String squadId) {
		this.squadId = squadId;
	}




	public String getSquadName() {
		return squadName;
	}



	public void setSquadName(String squadName) {
		this.squadName = squadName;
	}



	public String getUserNameDriver() {
		return userNameDriver;
	}

	public void setUserNameDriver(String userNameDriver) {
		this.userNameDriver = userNameDriver;
	}

	public String getDriverInfo() {
		return driverInfo;
	}

	public void setDriverInfo(String driverInfo) {
		this.driverInfo = driverInfo;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}
	
	public String getCarName() {
    return carName;
  }

  public void setCarName(String carName) {
    this.carName = carName;
  }

  public String getUserNameBooker() {
		return userNameBooker;
	}

	public void setUserNameBooker(String userNameBooker) {
		this.userNameBooker = userNameBooker;
	}

	public String getEmpInfo() {
		return empName;
	}

	public void setEmpInfo(String empName) {
		this.empName = empName;
	}

	public int[] getSelectedCarStatus() {
		return selectedCarStatus;
	}

	public void setSelectedCarStatus(int[] selectedCarStatus) {
		this.selectedCarStatus = selectedCarStatus;
	}

	



	public Date getStartDate() {
		return startDate;
	}




	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}




	public Date getEndDate() {
		return endDate;
	}




	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}




	public int getNumOfRows() {
		return numOfRows;
	}

	public void setNumOfRows(int numOfRows) {
		this.numOfRows = numOfRows;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public String getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(String averageRating) {
		this.averageRating = averageRating;
	}

	public String getCarId() {
    return carId;
  }

  public void setCarId(String carId) {
    this.carId = carId;
  }

  public String getDriverUser() {
    return driverUser;
  }

  public void setDriverUser(String driverUser) {
    this.driverUser = driverUser;
  }

  public String getRealyCarType() {
    return realyCarType;
  }

  public void setRealyCarType(String realyCarType) {
    this.realyCarType = realyCarType;
  }
}
