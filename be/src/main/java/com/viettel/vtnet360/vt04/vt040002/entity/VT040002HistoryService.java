package com.viettel.vtnet360.vt04.vt040002.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * class entity system code to get stationery type and unit calculation VT040002
 * 
 * @author KienPK-IIST 6/6/2019
 *
 */
public class VT040002HistoryService extends BaseEntity {

	private String userName;
	
	private String serviceId;
	/** service name */
	private String serviceName;

	/** date of handover */
	private Date timeReceive;
	
	private Long status;
	
	private String stationeryName;
	
	private String note;

	public VT040002HistoryService() {
		super();
	}

	public VT040002HistoryService(String codeValue, String codeName) {
		super();
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

	public Date getTimeReceive() {
		return timeReceive;
	}

	public void setTimeReceive(Date timeReceive) {
		this.timeReceive = timeReceive;
	}

	public String getStationeryName() {
		return stationeryName;
	}

	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
		
}
