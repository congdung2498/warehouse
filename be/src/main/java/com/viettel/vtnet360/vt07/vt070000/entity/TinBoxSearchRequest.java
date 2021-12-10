package com.viettel.vtnet360.vt07.vt070000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class TinBoxSearchRequest extends BaseEntity {
	private int warehouseId;
	private String keyword;
	private boolean byTinBox;
	private boolean byFolder;
	private boolean byProject;
	private boolean byPackage;
	private boolean byContract;
	private boolean byConstruction;
	private int searchType;
	
	public boolean isByTinBox() {
		return byTinBox;
	}
	public void setByTinBox(boolean byTinBox) {
		this.byTinBox = byTinBox;
	}
	public boolean isByFolder() {
		return byFolder;
	}
	public void setByFolder(boolean byFolder) {
		this.byFolder = byFolder;
	}
	public boolean isByProject() {
		return byProject;
	}
	public void setByProject(boolean byProject) {
		this.byProject = byProject;
	}
	public boolean isByPackage() {
		return byPackage;
	}
	public void setByPackage(boolean byPackage) {
		this.byPackage = byPackage;
	}
	public boolean isByContract() {
		return byContract;
	}
	public void setByContract(boolean byContract) {
		this.byContract = byContract;
	}
	public boolean isByConstruction() {
		return byConstruction;
	}
	public void setByConstruction(boolean byConstruction) {
		this.byConstruction = byConstruction;
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
	public int getSearchType() {
		return searchType;
	}
	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}
	@Override
	public String toString() {
		return "TinBoxSearchRequest [warehouseId=" + warehouseId + ", keyword=" + keyword + ", byTinBox=" + byTinBox
				+ ", byFolder=" + byFolder + ", byProject=" + byProject + ", byPackage=" + byPackage + ", byContract="
				+ byContract + ", byConstruction=" + byConstruction + ", searchType=" + searchType + "]";
	}
	
}
