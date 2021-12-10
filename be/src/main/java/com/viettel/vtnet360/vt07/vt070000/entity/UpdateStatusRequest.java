package com.viettel.vtnet360.vt07.vt070000.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class UpdateStatusRequest extends BaseEntity {
	private int id;
	private int searchType;
	private int isSynchrony;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public int getIsSynchrony() {
		return isSynchrony;
	}

	public void setIsSynchrony(int isSynchrony) {
		this.isSynchrony = isSynchrony;
	}

}
