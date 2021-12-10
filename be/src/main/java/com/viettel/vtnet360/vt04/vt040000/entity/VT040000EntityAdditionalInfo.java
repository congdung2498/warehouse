package com.viettel.vtnet360.vt04.vt040000.entity;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;
import org.apache.log4j.Logger;
public class VT040000EntityAdditionalInfo extends AdditionalInfoBase {
	private final Logger logger = Logger.getLogger(this.getClass());
	/** empUserName */
	private String empUserName;

	public VT040000EntityAdditionalInfo() {
		super();
	}

	public VT040000EntityAdditionalInfo(String empUserName) {
		super();
		this.empUserName = empUserName;
	}

	public String getEmpUserName() {
		return empUserName;
	}

	public void setEmpUserName(String empUserName) {
		this.empUserName = empUserName;
	}

	@Override
	public String toString() {
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";
		try {

			VT040000EntityAdditionalInfo jsonObject = new VT040000EntityAdditionalInfo();
			jsonObject.setId(this.getId());
			jsonObject.setRoleReceiver(this.getRoleReceiver());
			jsonObject.setEmpUserName(this.empUserName);

			jsonStr = mapperObj.writeValueAsString(jsonObject);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return jsonStr;
	}

}
