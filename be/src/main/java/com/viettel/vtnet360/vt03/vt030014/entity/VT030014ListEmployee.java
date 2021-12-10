package com.viettel.vtnet360.vt03.vt030014.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class entity for employee, employee's phone, driver
 * 
 * @author ThangBT 07/09/2018
 *
 */
public class VT030014ListEmployee extends BaseEntity {

	/** column common */
	private String result;

	/** user name of employee */
	private String userNameBooker;

	/** user name of employee */
	private String userNameDriver;

	/** phone number of employee */
	private String empPhone;

	/** id of drive squad */
	private String squadId;

	/** list of unit id for role QL */
	private String unitIdManager;

	public VT030014ListEmployee() {
		super();
	}


	public VT030014ListEmployee(String result, String userNameBooker, String userNameDriver, String empPhone,
			String squadId, String unitIdManager) {
		super();
		this.result = result;
		this.userNameBooker = userNameBooker;
		this.userNameDriver = userNameDriver;
		this.empPhone = empPhone;
		this.squadId = squadId;
		this.unitIdManager = unitIdManager;
	}


	public String getSquadId() {
		return squadId;
	}



	public void setSquadId(String squadId) {
		this.squadId = squadId;
	}



	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUserNameBooker() {
		return userNameBooker;
	}

	public void setUserNameBooker(String userNameBooker) {
		this.userNameBooker = userNameBooker;
	}

	public String getUserNameDriver() {
		return userNameDriver;
	}

	public void setUserNameDriver(String userNameDriver) {
		this.userNameDriver = userNameDriver;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public String getUnitIdManager() {
		return unitIdManager;
	}

	public void setUnitIdManager(String unitIdManager) {
		this.unitIdManager = unitIdManager;
	}
}
