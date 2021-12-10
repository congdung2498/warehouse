package com.viettel.vtnet360.vt00.common;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author DuyNK
 *
 */
public class AdditionalInfoBase {
	private final Logger logger = Logger.getLogger(this.getClass());
	/** id of record when notify */
	private String id;

	/** role of user who receive notify */
	private String roleReceiver;

	public AdditionalInfoBase() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleReceiver() {
		return roleReceiver;
	}

	public void setRoleReceiver(String roleReceiver) {
		this.roleReceiver = roleReceiver;
	}

	public AdditionalInfoBase(String id, String roleReceiver) {
		super();
		this.id = id;
		this.roleReceiver = roleReceiver;
	}

	@Override
	public String toString() {
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";
		try {
			jsonStr = mapperObj.writeValueAsString(new AdditionalInfoBase(id, roleReceiver));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return jsonStr;
	}
}
