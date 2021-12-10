package com.viettel.vtnet360.vt01.vt010000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class Entity VT010000EntityRqGetRecord
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT010000EntityRqGetRecord  extends BaseEntity {

	/** in_out_register.in_out_register_id **/
	private String inOutRegisterId;

	public VT010000EntityRqGetRecord() {
		super();
	}

	public VT010000EntityRqGetRecord(String inOutRegisterId) {
		super();
		this.inOutRegisterId = inOutRegisterId;
	}

	public String getInOutRegisterId() {
		return inOutRegisterId;
	}

	public void setInOutRegisterId(String inOutRegisterId) {
		this.inOutRegisterId = inOutRegisterId;
	}

}
