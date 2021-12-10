package com.viettel.vtnet360.vt07.vt070002.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;
public class RequestParam extends BaseEntity implements Serializable {
	private long folderId;
	private long projectId;
	private long packageId;
	private long contractId;
	private long constructionId;
	private long tinBoxId;
	private String warehouseId;
	private int level; //0: get tinBox data, 1: get until folder data, 2: get until project data, 3: get until documents 
	private String name;
	private String qrCode;
	private int typeSearch = 0;
	private int pageNo;
	private int objectType; // 1: Package, 2: Contract, 3: Construction
	private List<ObjId> exceptObjIds = new ArrayList<>();	
	
	private int pageNumber;
	private int pageSize;
	
	private boolean viewAll;
	private String queryUser;
	private int unit;

	public long getFolderId() {
		return folderId;
	}


	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}


	public long getTinBoxId() {
		return tinBoxId;
	}


	public void setTinBoxId(long tinBoxId) {
		this.tinBoxId = tinBoxId;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public int getTypeSearch() {
		return typeSearch;
	}


	public void setTypeSearch(int typeSearch) {
		this.typeSearch = typeSearch;
	}


	public int getPageNo() {
		return pageNo;
	}


	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}


	public long getProjectId() {
		return projectId;
	}


	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}




	public int getObjectType() {
		return objectType;
	}


	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}


	public long getContractId() {
		return contractId;
	}


	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}


	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public List<ObjId> getExceptObjIds() {
		return exceptObjIds;
	}


	public void setExceptObjIds(List<ObjId> exceptObjIds) {
		this.exceptObjIds = exceptObjIds;
	}


	public long getPackageId() {
		return packageId;
	}


	public void setPackageId(long packageId) {
		this.packageId = packageId;
	}


	public long getConstructionId() {
		return constructionId;
	}


	public void setConstructionId(long constructionId) {
		this.constructionId = constructionId;
	}


	public String getQrCode() {
		return qrCode;
	}


	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public boolean isViewAll() {
		return viewAll;
	}


	public void setViewAll(boolean viewAll) {
		this.viewAll = viewAll;
	}


	public String getQueryUser() {
		return queryUser;
	}


	public void setQueryUser(String queryUser) {
		this.queryUser = queryUser;
	}


	public int getUnit() {
		return unit;
	}


	public void setUnit(int unit) {
		this.unit = unit;
	}

	
}
