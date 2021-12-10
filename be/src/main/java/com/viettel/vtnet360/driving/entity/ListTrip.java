package com.viettel.vtnet360.driving.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

public class ListTrip extends BaseEntity {


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

	/** user name of employee who is booker */
	private String userNameBooker;

	/** info of employee */
	private String empName;
	/** phone number of employee */
	private String empPhone;
	
	private int[] selectedCarStatus;

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
	
	private String carId ;
	
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

	
	
	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getReasonAssigner() {
		return reasonAssigner;
	}

	public void setReasonAssigner(String reasonAssigner) {
		this.reasonAssigner = reasonAssigner;
	}

	public String getCarBookingId() {
		return carBookingId;
	}

	public void setCarBookingId(String carBookingId) {
		this.carBookingId = carBookingId;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
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

	public String getUserNameBooker() {
		return userNameBooker;
	}

	public void setUserNameBooker(String userNameBooker) {
		this.userNameBooker = userNameBooker;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public int[] getSelectedCarStatus() {
		return selectedCarStatus;
	}

	public void setSelectedCarStatus(int[] selectedCarStatus) {
		this.selectedCarStatus = selectedCarStatus;
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

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
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

	public String getDetailUnit() {
		return detailUnit;
	}

	public void setDetailUnit(String detailUnit) {
		this.detailUnit = detailUnit;
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

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getJourney() {
		return journey;
	}

	public void setJourney(String journey) {
		this.journey = journey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	
	
	public ListTrip(String carBookingId, String loginUserName, String driverName, String loginRole,
			String unitIdManager, String squadId, String squadName, String userNameDriver, String driverInfo,
			String carType, String userNameBooker, String empName, String empPhone, int[] selectedCarStatus,
			Date dateStart, Date dateEnd, String licensePlate, int numOfRows, int startRow, int rowSize,
			String averageRating, Date timeReadyStart, Date timeReadyEnd, int numOfPassenger, String listOfPassenger,
			int statusTrips, String route, String routeType, String seat, String carId, int rating, String reasonRefuse,
			String feedback, String appoverQltt, String appoverLddv, String appoverCvp, int flagQltt, int flagLddv,
			int flagCvp, String createUser, String updateUser, String reasonAssigner, int startPlace, String tagetPlace,
			String reason, String detailUnit, Boolean isAQltt, Boolean isAQldv, Boolean isACvp, String placeName,
			String journey, String type, String seatName, String fullName) {
		super();
		this.carBookingId = carBookingId;
		this.loginUserName = loginUserName;
		this.driverName = driverName;
		this.loginRole = loginRole;
		this.unitIdManager = unitIdManager;
		this.squadId = squadId;
		this.squadName = squadName;
		this.userNameDriver = userNameDriver;
		this.driverInfo = driverInfo;
		this.carType = carType;
		this.userNameBooker = userNameBooker;
		this.empName = empName;
		this.empPhone = empPhone;
		this.selectedCarStatus = selectedCarStatus;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.licensePlate = licensePlate;
		this.numOfRows = numOfRows;
		this.startRow = startRow;
		this.rowSize = rowSize;
		this.averageRating = averageRating;
		this.timeReadyStart = timeReadyStart;
		this.timeReadyEnd = timeReadyEnd;
		this.numOfPassenger = numOfPassenger;
		this.listOfPassenger = listOfPassenger;
		this.statusTrips = statusTrips;
		this.route = route;
		this.routeType = routeType;
		this.seat = seat;
		this.carId = carId;
		this.rating = rating;
		this.reasonRefuse = reasonRefuse;
		this.feedback = feedback;
		this.appoverQltt = appoverQltt;
		this.appoverLddv = appoverLddv;
		this.appoverCvp = appoverCvp;
		this.flagQltt = flagQltt;
		this.flagLddv = flagLddv;
		this.flagCvp = flagCvp;
		this.createUser = createUser;
		this.updateUser = updateUser;
		this.reasonAssigner = reasonAssigner;
		this.startPlace = startPlace;
		this.tagetPlace = tagetPlace;
		this.reason = reason;
		this.detailUnit = detailUnit;
		this.isAQltt = isAQltt;
		this.isAQldv = isAQldv;
		this.isACvp = isACvp;
		this.placeName = placeName;
		this.journey = journey;
		this.type = type;
		this.seatName = seatName;
		this.fullName = fullName;
	}

	public ListTrip() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
