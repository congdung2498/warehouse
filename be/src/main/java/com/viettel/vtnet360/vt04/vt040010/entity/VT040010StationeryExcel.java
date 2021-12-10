package com.viettel.vtnet360.vt04.vt040010.entity;

import java.util.Date;

public class VT040010StationeryExcel {

	/** id of unit */
	private String unitId;

	/** name of unit */
	private String unitName;

	/** 3 levels of unit */
	private String detailUnit;

	/** id of issue service id */
	private String issueServiceId;

	/** name of service */
	private String serviceName;

	/** id of stationery */
	private String stationeryId;

	/** name of stationery */
	private String stationeryName;

	/** unit calculation */
	private String stationeryUnitCal;

	/** quantity of stationery */
	private double stationeryQuan;

	/** date start */
	private Date timeStartPlan;

	/** name of requesting employee */
	private String empName;

	private String reciverName;

	private int status;

	/** note */
	private String note;

	public VT040010StationeryExcel() {
		super();
	}

	public VT040010StationeryExcel(String unitId, String unitName, String detailUnit, String issueServiceId,
			String serviceName, String stationeryId, String stationeryName, String stationeryUnitCal,
			double stationeryQuan, Date timeStartPlan, String empName, String reciverName, int status, String note) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
		this.detailUnit = detailUnit;
		this.issueServiceId = issueServiceId;
		this.serviceName = serviceName;
		this.stationeryId = stationeryId;
		this.stationeryName = stationeryName;
		this.stationeryUnitCal = stationeryUnitCal;
		this.stationeryQuan = stationeryQuan;
		this.timeStartPlan = timeStartPlan;
		this.empName = empName;
		this.reciverName = reciverName;
		this.status = status;
		this.note = note;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReciverName() {
		return reciverName;
	}

	public void setReciverName(String reciverName) {
		this.reciverName = reciverName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDetailUnit() {
		return detailUnit;
	}

	public void setDetailUnit(String detailUnit) {
		this.detailUnit = detailUnit;
	}

	public String getIssueServiceId() {
		return issueServiceId;
	}

	public void setIssueServiceId(String issueServiceId) {
		this.issueServiceId = issueServiceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getStationeryId() {
		return stationeryId;
	}

	public void setStationeryId(String stationeryId) {
		this.stationeryId = stationeryId;
	}

	public String getStationeryName() {
		return stationeryName;
	}

	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}

	public String getStationeryUnitCal() {
		return stationeryUnitCal;
	}

	public void setStationeryUnitCal(String stationeryUnitCal) {
		this.stationeryUnitCal = stationeryUnitCal;
	}

	public double getStationeryQuan() {
		return stationeryQuan;
	}

	public void setStationeryQuan(double stationeryQuan) {
		this.stationeryQuan = stationeryQuan;
	}

	public Date getTimeStartPlan() {
		return timeStartPlan;
	}

	public void setTimeStartPlan(Date timeStartPlan) {
		this.timeStartPlan = timeStartPlan;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
