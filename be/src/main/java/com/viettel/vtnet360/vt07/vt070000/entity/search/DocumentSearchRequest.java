package com.viettel.vtnet360.vt07.vt070000.entity.search;

import com.viettel.vtnet360.common.security.BaseEntity;

public class DocumentSearchRequest extends BaseEntity {
	private int warehouseId;
	private String keyword;
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
