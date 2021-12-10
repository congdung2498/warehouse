package com.viettel.vtnet360.vt04.vt040001.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * class entity service VT040001
 * 
 * @author ThangBT 04/10/2018
 *
 */
public class VT040001ListService extends BaseEntity {

	/** user name of login user */
	private String loginUserName;

	/** role of login user */
	private String loginRole;

	/** id of place */
	private String placeId;

	/** name of place */
	private String placeName;

	/** id of service */
	private String serviceId;

	/** name of service */
	private String serviceName;

	/** id of unit received */
	private String unitId;

	/** name of unit received */
	private String unitName;

	/** 3 levels of unit received */
	private String detailUnit;

	/** name of receiver */
	private String receiverName;

	/** user name of receiver */
	private String receiverUserName;

	/** list user name of receiver */
	private String[] listReceiverUserName;

	/** response time */
	private String responseTime;

	/** status of service */
	private String statusService;
	
	/** fullFillTime of service */
	private Long fullFillTime;
	
	/** requireSign of service */
	private int requireSign;

	/** select from which row */
	private int startRow;

	/** number of row to select */
	private int rowSize;
	
	/**
	 * list of receivers separated by ','
	 */
	private String receivers;

	public VT040001ListService() {
		super();
	}

	public VT040001ListService(String placeId, String placeName, String serviceId, String serviceName, String unitId,
			String unitName, String detailUnit, String receiverName, String receiverUserName,
			String[] listReceiverUserName, String responseTime, String statusService, int startRow, int rowSize) {
		super();
		this.placeId = placeId;
		this.placeName = placeName;
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.unitId = unitId;
		this.unitName = unitName;
		this.detailUnit = detailUnit;
		this.receiverName = receiverName;
		this.receiverUserName = receiverUserName;
		this.listReceiverUserName = listReceiverUserName;
		this.responseTime = responseTime;
		this.statusService = statusService;
		this.startRow = startRow;
		this.rowSize = rowSize;
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

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
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

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverUserName() {
		return receiverUserName;
	}

	public void setReceiverUserName(String receiverUserName) {
		this.receiverUserName = receiverUserName;
	}

	public String[] getListReceiverUserName() {
		return listReceiverUserName;
	}

	public void setListReceiverUserName(String[] listReceiverUserName) {
		this.listReceiverUserName = listReceiverUserName;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getStatusService() {
		return statusService;
	}

	public void setStatusService(String statusService) {
		this.statusService = statusService;
	}

	public Long getFullFillTime() {
    return fullFillTime;
  }

  public void setFullFillTime(Long fullFillTime) {
    this.fullFillTime = fullFillTime;
  }

  public int getRequireSign() {
	return requireSign;
}

public void setRequireSign(int requireSign) {
	this.requireSign = requireSign;
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

	public String getReceivers() {
		return receivers;
	}

	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}
	
	
}
