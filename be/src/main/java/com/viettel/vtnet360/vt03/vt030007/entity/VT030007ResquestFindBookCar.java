package com.viettel.vtnet360.vt03.vt030007.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntitySystemCode;

/**
 * 
 * @author CuongHD
 *
 */
public class VT030007ResquestFindBookCar extends VT030000EntitySystemCode {
	/** QLDV_EMPLOYEE.UNIT_ID*/
	private String unitId; // unit search 
	
	/** Unit of user */
	private String unitUser;
	
	/** QLDV_UNIT.UNIT_NAME*/
	private String userName;
	
	
	private String userManager;

	/** CAR_BOOKING.ROLE */
	private String role;

	/** CAR_BOOKING.TITLE  */
	private String title;

	/** CAR_BOOKING.PHONE_NUMBERS */
	private String emplPhone;

	/** CAR_BOOKING.TOTAL_PASSAGE */
	private String totalPartner;
	
	/** CAR_BOOKING.START  */
	private Date timeStart;

	/** CAR_BOOKING.END */
	private Date timeEnd;
	
	/** CAR_BOOKING.TYPE  */
	private String typeCar;

	/** CAR_BOOKING.SEAT */
	private String seat;
	
	/** CAR_BOOKING.ROUTE_TYPE */
	private String route;
	
	/** */
	private int pageNumber;
	
	
	private int pageSize;
	
	private List<Integer> listStatus;
	
	private String processByRole;
	
	private int startPlace;

	private String tagetPlace;
	
	
	public VT030007ResquestFindBookCar() {
		super();
	}

	public VT030007ResquestFindBookCar(String masterClassType, String masterClassSeat, String masterClassRoute) {
		super(masterClassType, masterClassSeat, masterClassRoute);
	}

	public VT030007ResquestFindBookCar(String unitId, String userName, String role, String title, String emplPhone,
			String totalPartner, Date timeStart, Date timeEnd, String typeCar, String seat, String route,
			 List<Integer> listStatus, int pageNumber, int pageSize, String processByRole) {
		super();
		this.unitId = unitId;
		this.userName = userName;
		this.role = role;
		this.title = title;
		this.emplPhone = emplPhone;
		this.totalPartner = totalPartner;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.typeCar = typeCar;
		this.seat = seat;
		this.route = route;
		this.listStatus = listStatus;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.processByRole = processByRole;
	}

	public Date getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}
	public Date getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getTypeCar() {
		return typeCar;
	}
	public void setTypeCar(String typeCar) {
		this.typeCar = typeCar;
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

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getTotalPartner() {
		return totalPartner;
	}

	public void setTotalPartner(String totalPartner) {
		this.totalPartner = totalPartner;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserManager() {
		return userManager;
	}

	public void setUserManager(String userManager) {
		this.userManager = userManager;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmplPhone() {
		return emplPhone;
	}

	public void setEmplPhone(String emplPhone) {
		this.emplPhone = emplPhone;
	}

	public List<Integer> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<Integer> listStatus) {
		this.listStatus = listStatus;
	}

	public String getProcessByRole() {
		return processByRole;
	}

	public void setProcessByRole(String processByRole) {
		this.processByRole = processByRole;
	}

	public String getUnitUser() {
		return unitUser;
	}

	public void setUnitUser(String unitUser) {
		this.unitUser = unitUser;
	}
	
	public String getTagetPlace() {
		return tagetPlace;
	}

	public void setTagetPlace(String tagetPlace) {
		this.tagetPlace = tagetPlace;
	}


	public int getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(int startPlace) {
		this.startPlace = startPlace;
	}
}
