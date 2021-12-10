package com.viettel.vtnet360.vt04.vt040001.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * class entity service receiver VT040001
 * 
 * @author ThangBT 04/10/2018
 *
 */
public class VT040001ListEmployee extends BaseEntity {

	/** id of service */
	private String serviceId;

	/** name of receiver */
	private String receiverName;

	/** user name of receiver */
	private String receiverUserName;

	/** list user name of receiver who is selected */
	private String[] listUserNameReceiver;

	/** total receiver available */
	private int numOfReceiver;

	private String unitId;

	public VT040001ListEmployee() {
		super();
	}

	public VT040001ListEmployee(String serviceId, String receiverName, String receiverUserName,
			String[] listUserNameReceiver, int numOfReceiver, String unitId) {
		super();
		this.serviceId = serviceId;
		this.receiverName = receiverName;
		this.receiverUserName = receiverUserName;
		this.listUserNameReceiver = listUserNameReceiver;
		this.numOfReceiver = numOfReceiver;
		this.unitId = unitId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
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

	public String[] getListUserNameReceiver() {
		return listUserNameReceiver;
	}

	public void setListUserNameReceiver(String[] listUserNameReceiver) {
		this.listUserNameReceiver = listUserNameReceiver;
	}

	public int getNumOfReceiver() {
		return numOfReceiver;
	}

	public void setNumOfReceiver(int numOfReceiver) {
		this.numOfReceiver = numOfReceiver;
	}
}
