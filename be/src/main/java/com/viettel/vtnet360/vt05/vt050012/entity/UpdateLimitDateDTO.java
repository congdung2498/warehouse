package com.viettel.vtnet360.vt05.vt050012.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class UpdateLimitDateDTO extends BaseEntity {
	private String codeName ;

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public UpdateLimitDateDTO(String codeName) {
		super();
		this.codeName = codeName;
	}

	public UpdateLimitDateDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
