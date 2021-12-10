package com.viettel.vtnet360.vt04.vt040007.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

public class VT040007EntityRqExecutive extends BaseEntity {

	/** ISSUES_SERVICE.ISSUES_SERVICE_ID */
	private String issuedServiceId;

	/**
	 * flagExecutive 3 Executiving 4 Pending Executive 5 Can't Executive 6 Complete
	 */
	private int flagExecutive;

	/** ISSUES_SERVICE.REASON */
	private String resonCantExecutive;

	/** ISSUES_SERVICE.POSTPONE_REASON */
	private String resonPostponeExecutive;

	/** ISSUES_SERVICE.POSTPONE_TO_TIME */
	private Date datePostpone;

	/** stationery */
	private VT040007EntityDataSt[] stationery;

	private int statusStart;
	
	private long realTimeTotal;
	
	public VT040007EntityRqExecutive() {
		super();
	}

	public VT040007EntityRqExecutive(String issuedServiceId, int flagExecutive, String resonCantExecutive,
			String resonPostponeExecutive, Date datePostpone, VT040007EntityDataSt[] stationery) {

		super();
		this.issuedServiceId = issuedServiceId;
		this.flagExecutive = flagExecutive;
		this.resonCantExecutive = resonCantExecutive;
		this.resonPostponeExecutive = resonPostponeExecutive;
		this.datePostpone = datePostpone;
		this.stationery = stationery;
	}

	public String getIssuedServiceId() {
		return issuedServiceId;
	}

	public void setIssuedServiceId(String issuedServiceId) {
		this.issuedServiceId = issuedServiceId;
	}

	public int getFlagExecutive() {
		return flagExecutive;
	}

	public void setFlagExecutive(int flagExecutive) {
		this.flagExecutive = flagExecutive;
	}

	public String getResonCantExecutive() {
		return resonCantExecutive;
	}

	public void setResonCantExecutive(String resonCantExecutive) {
		this.resonCantExecutive = resonCantExecutive;
	}

	public String getResonPostponeExecutive() {
		return resonPostponeExecutive;
	}

	public void setResonPostponeExecutive(String resonPostponeExecutive) {
		this.resonPostponeExecutive = resonPostponeExecutive;
	}

	public Date getDatePostpone() {
		return datePostpone;
	}

	public void setDatePostpone(Date datePostpone) {
		this.datePostpone = datePostpone;
	}

	public VT040007EntityDataSt[] getStationery() {
		return stationery;
	}

	public void setStationery(VT040007EntityDataSt[] stationery) {
		this.stationery = stationery;
	}

	public int getStatusStart() {
		return statusStart;
	}

	public void setStatusStart(int statusStart) {
		this.statusStart = statusStart;
	}

	public long getRealTimeTotal() {
		return realTimeTotal;
	}

	public void setRealTimeTotal(long realTimeTotal) {
		this.realTimeTotal = realTimeTotal;
	}

}
