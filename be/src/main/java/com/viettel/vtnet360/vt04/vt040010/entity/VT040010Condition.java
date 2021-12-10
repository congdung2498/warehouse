package com.viettel.vtnet360.vt04.vt040010.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class entity condition to query in database VT040010
 * 
 * @author ThangBT 19/09/2018
 *
 */
public class VT040010Condition extends BaseEntity {

	/** user name of login user */
	private String loginUserName;

	/** role of login user */
	private String loginRole;

	/** id of place */
	private String placeId;

	/** name of place */
	private String placeName;

	/** list of unit id */
	private String[] listUnitId;

	/** list of status */
	private String[] listStatus;

	/** id of service */
	private String serviceId;

	/** name of service */
	private String serviceName;

	/** user name of receiver */
	private String receiverUserName;

	/** name of receiver */
	private String receiverName;

	/** start date search */
	private Date startDate;

	/** end date search */
	private Date endDate;

	/** select from which row */
	private int startRow;

	/** number of row to select */
	private int rowSize;

	/** flag if user is manager */
	private int manager;

	private int[] status;
	
	private Long syntheticSignVofficeId;

	private Long detailSignVofficeId;
	
	public VT040010Condition() {
		super();
	}

	public VT040010Condition(String loginUserName, String loginRole, String placeId, String placeName,
			String[] listUnitId, String[] listStatus, String serviceId, String serviceName, String receiverUserName,
			String receiverName, Date startDate, Date endDate, int startRow, int rowSize, int manager, int[] status) {
		super();
		this.loginUserName = loginUserName;
		this.loginRole = loginRole;
		this.placeId = placeId;
		this.placeName = placeName;
		this.listUnitId = listUnitId;
		this.listStatus = listStatus;
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.receiverUserName = receiverUserName;
		this.receiverName = receiverName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startRow = startRow;
		this.rowSize = rowSize;
		this.manager = manager;
		this.status = status;
	}

	public int[] getStatus() {
		return status;
	}

	public void setStatus(int[] status) {
		this.status = status;
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

	public String[] getListUnitId() {
		return listUnitId;
	}

	public void setListUnitId(String[] listUnitId) {
		this.listUnitId = listUnitId;
	}

	public String[] getListStatus() {
		return listStatus;
	}

	public void setListStatus(String[] listStatus) {
		this.listStatus = listStatus;
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

	public String getReceiverUserName() {
		return receiverUserName;
	}

	public void setReceiverUserName(String receiverUserName) {
		this.receiverUserName = receiverUserName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public int getManager() {
		return manager;
	}

	public void setManager(int manager) {
		this.manager = manager;
	}

	public Long getSyntheticSignVofficeId() {
		return syntheticSignVofficeId;
	}

	public void setSyntheticSignVofficeId(Long syntheticSignVofficeId) {
		this.syntheticSignVofficeId = syntheticSignVofficeId;
	}

	public Long getDetailSignVofficeId() {
		return detailSignVofficeId;
	}

	public void setDetailSignVofficeId(Long detailSignVofficeId) {
		this.detailSignVofficeId = detailSignVofficeId;
	}
	
	
}
