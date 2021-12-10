package com.viettel.vtnet360.vt04.vt040000.entity;

import java.util.Date;

/**
 * Class VT040000EntityDataFindService
 * 
 * @author KienHT 27/09/2018
 * 
 */
public class VT040000EntityDataFindService {

	/** SERVICES.SERVICE_ID */
	private String serviceId;

	/** SERVICES.SERVICE_NAME */
	private String serviceName;

	/** SERVICES.SERVICE_LOCATION */
	private int serviceLocation;

	/** SERVICES.UNIT_ID */
	private int unitId;

	/** SERVICES.FULL_FILL_TIME */
	private int fullFillTime;

	/** SERVICES.STATUS */
	private int status;

	/** SERVICES.CREATE_USER */
	private String createUser;

	/** SERVICES.CREATE_DATE */
	private Date createDate;

	/** SERVICES.UPDATE_USER */
	private String updateUser;

	/** SERVICES.UPDATE_DATE */
	private Date updateDate;
	
	/** SERVICES.REQUIRE_SIGN */
	private int requireSign;

	public VT040000EntityDataFindService() {
		super();
	}

	public VT040000EntityDataFindService(String serviceId, String serviceName, int serviceLocation, int unitId,
			int fullFillTime, int status, String createUser, Date createDate, String updateUser, Date updateDate) {
		super();
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.serviceLocation = serviceLocation;
		this.unitId = unitId;
		this.fullFillTime = fullFillTime;
		this.status = status;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
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

	public int getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(int serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getFullFillTime() {
		return fullFillTime;
	}

	public void setFullFillTime(int fullFillTime) {
		this.fullFillTime = fullFillTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getRequireSign() {
		return requireSign;
	}

	public void setRequireSign(int requireSign) {
		this.requireSign = requireSign;
	}

}
