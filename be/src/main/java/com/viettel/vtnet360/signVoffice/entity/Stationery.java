package com.viettel.vtnet360.signVoffice.entity;

import java.util.Date;
import java.util.List;

public class Stationery {
	private String issueServiceId;
	
	private Date timeStartPlan;
	
	private String serviceId;
	
	private String serviceName;
	
	private String stationeryId;

	private String stationeryName;

	private String calculationUnit;
	
	private String codeName;
	
	private int quantity;
	
	private String fullName;
	
	private String fullNameRequest;
	
	private List<String> listStatineryId;

	public String getStationeryId() {
		return stationeryId;
	}

	public void setStationeryId(String stationeryId) {
		this.stationeryId = stationeryId;
	}

	public Date getTimeStartPlan() {
		return timeStartPlan;
	}

	public void setTimeStartPlan(Date timeStartPlan) {
		this.timeStartPlan = timeStartPlan;
	}

	public String getStationeryName() {
		return stationeryName;
	}

	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}

	public String getCalculationUnit() {
		return calculationUnit;
	}

	public void setCalculationUnit(String calculationUnit) {
		this.calculationUnit = calculationUnit;
	}

	public List<String> getListStatineryId() {
		return listStatineryId;
	}

	public void setListStatineryId(List<String> listStatineryId) {
		this.listStatineryId = listStatineryId;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getIssueServiceId() {
		return issueServiceId;
	}

	public void setIssueServiceId(String issueServiceId) {
		this.issueServiceId = issueServiceId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullNameRequest() {
		return fullNameRequest;
	}

	public void setFullNameRequest(String fullNameRequest) {
		this.fullNameRequest = fullNameRequest;
	}

}
