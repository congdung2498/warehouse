package com.viettel.vtnet360.vt05.vt050000.entity;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;
import org.apache.log4j.Logger;
/**
 * @author DuyNK
 *
 */
public class VT050000AdditionalInfoToSendNotify extends AdditionalInfoBase {
	private final Logger logger = Logger.getLogger(this.getClass());
	/** role of user who send notify */
	private String roleUserSend;

	public VT050000AdditionalInfoToSendNotify() {

	}

	public VT050000AdditionalInfoToSendNotify(String id, String roleReceiver, String roleUserSend) {
		super(id, roleReceiver);
		this.roleUserSend = roleUserSend;
	}

	public String getRoleUserSend() {
		return roleUserSend;
	}

	public void setRoleUserSend(String roleUserSend) {
		this.roleUserSend = roleUserSend;
	}

	@Override
	public String toString() {
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";
		try {
			jsonStr = mapperObj.writeValueAsString(new VT050000AdditionalInfoToSendNotify(getId(), getRoleReceiver(), getRoleUserSend()));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return jsonStr;
	}
}
