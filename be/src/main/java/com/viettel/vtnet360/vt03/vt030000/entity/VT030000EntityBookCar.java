package com.viettel.vtnet360.vt03.vt030000.entity;

import java.util.Date;

/**
 * 
 * @author CuongHD
 *
 */
public class VT030000EntityBookCar extends VT030000EntitySystemCode {
		/** CAR_BOOKING.CAR_BOOKING_ID */ 
		private String bookCarId;
		
		/** CAR_BOOKING.EMPLOYEE_USER_NAME */
		private String userName;
		
		/** CAR_BOOKING.REASON */
		private String reason;
		
		/** CAR_BOOKING.START */
		private Date dateStart;
		
		/** CAR_BOOKING.END */
		private Date dateEnd;
		
		/** CAR_BOOKING.TYPE */
		private String type;
		
		/** CAR_BOOKING.SEAT */
		private String seat;
		
		/** CAR_BOOKING.ROUTE_TYPE */
		private String route;
		
		/** CAR_BOOKING.START_PLACE */
		private String startPlace;
		
		/** CAR_BOOKING.TAGET_PLACE */
		private String targetPlace;
		
		/** CAR_BOOKING.ROUTE */
		private String routeWay;
		
		/** CAR_BOOKING.TOTAL_PASSAGE */
		private int totalPartner;
		
		/** CAR_BOOKING.LIST_PASSAGE */
		private String listPartner;
		
		/** CAR_BOOKING.APPOVER_QLTT */
		private String approverDir;
		
		/** QLDV_EMPLOYEE.FULL_NAME */
		private String qlttName;
		
		/** CAR_BOOKING.FLAG_QLTT */
		private int statusDir;
		
		/** CAR_BOOKING.APPOVER_LDDV */
		private String approverLead;
		
		/** QLDV_EMPLOYEE.FULL_NAME */
		private String lddvName;
		
		/** CAR_BOOKING.FLAG_LDDV */
		private int statusLead;
		
		/** CAR_BOOKING.APPOVER_CVP */
		private String approverPre;
		
		/** QLDV_EMPLOYEE.FULL_NAME */
		private String cvpName;
		
		/** CAR_BOOKING.FLAG_CVP */
		private int statusPre;
		
		/** CAR_BOOKING.STATUS */
		private int status;
		
		/** CAR_BOOKING.DRIVER_SQUAD_ID */
		private String squadId;
		
		/** CAR_BOOKING.SQUAD_NAME */
		private String driverSquadName;
		
		public VT030000EntityBookCar() {
			super();
		}	

		public VT030000EntityBookCar(String masterClassType, String masterClassSeat, String masterClassRoute) {
			super(masterClassType, masterClassSeat, masterClassRoute);
		}

		

		public VT030000EntityBookCar(String bookCarId, String userName, String reason, Date dateStart, Date dateEnd,
				String type, String seat, String route, String startPlace, String targetPlace, String routeWay,
				int totalPartner, String listPartner, String approverDir, String qlttName, int statusDir,
				String approverLead, String lddvName, int statusLead, String approverPre, String cvpName, int statusPre,
				int status, String squadId, String driverSquadName) {
			super();
			this.bookCarId = bookCarId;
			this.userName = userName;
			this.reason = reason;
			this.dateStart = dateStart;
			this.dateEnd = dateEnd;
			this.type = type;
			this.seat = seat;
			this.route = route;
			this.startPlace = startPlace;
			this.targetPlace = targetPlace;
			this.routeWay = routeWay;
			this.totalPartner = totalPartner;
			this.listPartner = listPartner;
			this.approverDir = approverDir;
			this.qlttName = qlttName;
			this.statusDir = statusDir;
			this.approverLead = approverLead;
			this.lddvName = lddvName;
			this.statusLead = statusLead;
			this.approverPre = approverPre;
			this.cvpName = cvpName;
			this.statusPre = statusPre;
			this.status = status;
			this.squadId = squadId;
			this.driverSquadName = driverSquadName;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
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

		public int getTotalPartner() {
			return totalPartner;
		}

		public void setTotalPartner(int totalPartner) {
			this.totalPartner = totalPartner;
		}

		public String getListPartner() {
			return listPartner;
		}

		public void setListPartner(String listPartner) {
			this.listPartner = listPartner;
		}

		public String getApproverDir() {
			return approverDir;
		}

		public void setApproverDir(String approverDir) {
			this.approverDir = approverDir;
		}

		public int getStatusDir() {
			return statusDir;
		}

		public void setStatusDir(int statusDir) {
			this.statusDir = statusDir;
		}

		public String getApproverLead() {
			return approverLead;
		}

		public void setApproverLead(String approverLead) {
			this.approverLead = approverLead;
		}

		public int getStatusLead() {
			return statusLead;
		}

		public void setStatusLead(int statusLead) {
			this.statusLead = statusLead;
		}

		public String getApproverPre() {
			return approverPre;
		}

		public void setApproverPre(String approverPre) {
			this.approverPre = approverPre;
		}

		public int getStatusPre() {
			return statusPre;
		}

		public void setStatusPre(int statusPre) {
			this.statusPre = statusPre;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getQlttName() {
			return qlttName;
		}

		public void setQlttName(String qlttName) {
			this.qlttName = qlttName;
		}

		public String getLddvName() {
			return lddvName;
		}

		public void setLddvName(String lddvName) {
			this.lddvName = lddvName;
		}

		public String getCvpName() {
			return cvpName;
		}

		public void setCvpName(String cvpName) {
			this.cvpName = cvpName;
		}

		public String getBookCarId() {
			return bookCarId;
		}

		public void setBookCarId(String bookCarId) {
			this.bookCarId = bookCarId;
		}


		public String getSquadId() {
			return squadId;
		}

		public void setSquadId(String squadId) {
			this.squadId = squadId;
		}

		public String getDriverSquadName() {
			return driverSquadName;
		}

		public void setDriverSquadName(String driverSquadName) {
			this.driverSquadName = driverSquadName;
		}
}
