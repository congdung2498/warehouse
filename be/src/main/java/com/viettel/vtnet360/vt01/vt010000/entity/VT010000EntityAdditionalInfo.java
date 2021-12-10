package com.viettel.vtnet360.vt01.vt010000.entity;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;

public class VT010000EntityAdditionalInfo extends AdditionalInfoBase {
	private final Logger logger = Logger.getLogger(this.getClass());
	/** status*/
	private int status;

	public VT010000EntityAdditionalInfo() {
		super();
	}

	public VT010000EntityAdditionalInfo(int status) {
		super();
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";
		try {

			VT010000EntityAdditionalInfo jsonObject = new VT010000EntityAdditionalInfo();
			jsonObject.setId(this.getId());
			jsonObject.setRoleReceiver(this.getRoleReceiver());
			jsonObject.setStatus(this.status);

			jsonStr = mapperObj.writeValueAsString(jsonObject);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return jsonStr;
	}

}
