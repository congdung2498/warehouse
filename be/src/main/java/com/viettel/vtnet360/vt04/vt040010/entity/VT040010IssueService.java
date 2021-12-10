package com.viettel.vtnet360.vt04.vt040010.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ThangBT 20/10/2018
 *
 */
public class VT040010IssueService {

	/** id of issue service */
	private String issueServiceId;

	/** name of unit */
	private String unitName;

	/** name of service */
	private String serviceName;

	/** date start */
	private Date timeStartPlan;

	/** reciverName */
	private String reciverName;

	/** name of requesting employee */
	private String empName;

	private int status;

	/** note */
	private String note;

	/** list of stationery */
	private List<VT040010Stationery> stationeries;

	public VT040010IssueService() {
		super();
		this.stationeries = new ArrayList<>();
	}

	public VT040010IssueService(String issueServiceId, String unitName, String serviceName, Date timeStartPlan,
			String empName, String note) {
		super();
		this.issueServiceId = issueServiceId;
		this.unitName = unitName;
		this.serviceName = serviceName;
		this.timeStartPlan = timeStartPlan;
		this.empName = empName;
		this.note = note;
		this.stationeries = new ArrayList<>();
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

	public String getIssueServiceId() {
		return issueServiceId;
	}

	public void setIssueServiceId(String issueServiceId) {
		this.issueServiceId = issueServiceId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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

	public List<VT040010Stationery> getStationeries() {
		return stationeries;
	}

	public void setStationeries(List<VT040010Stationery> stationeries) {
		this.stationeries = stationeries;
	}

	public void addStationery(VT040010Stationery stationery) {
		if (!this.stationeries.contains(stationery)) {
			this.stationeries.add(stationery);
		}
	}
}
