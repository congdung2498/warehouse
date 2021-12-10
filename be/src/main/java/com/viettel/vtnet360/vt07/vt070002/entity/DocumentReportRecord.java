package com.viettel.vtnet360.vt07.vt070002.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class DocumentReportRecord extends BaseEntity {
	private long projectId;
	private String projectName;
	private String packageName;
	private String contractName;
	private String constructionName;
	private String documentName;
	private String projectDoc;
	private String packageDoc;
	private String contractDoc;
	private String constructionDoc;
	private String tinBoxQRCode;
	private String tinBoxName;
	private String folderQRCode;
	private String folderName;
	private String warehouseName;
	private String position;
	private String positionQRCode;
	/** pageNumber in one page */
	private int pageNumber;
	/** number of records in a page */
	private int pageSize;
	/** total all records */
	private int totalRecords;
	
	private int totalTinbox;
	private int totalFolder;

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getConstructionName() {
		return constructionName;
	}

	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}

	public String getProjectDoc() {
		return projectDoc;
	}

	public void setProjectDoc(String projectDoc) {
		this.projectDoc = projectDoc;
	}

	public String getPackageDoc() {
		return packageDoc;
	}

	public void setPackageDoc(String packageDoc) {
		this.packageDoc = packageDoc;
	}

	public String getContractDoc() {
		return contractDoc;
	}

	public void setContractDoc(String contractDoc) {
		this.contractDoc = contractDoc;
	}

	public String getConstructionDoc() {
		return constructionDoc;
	}

	public void setConstructionDoc(String constructionDoc) {
		this.constructionDoc = constructionDoc;
	}

	public String getTinBoxQRCode() {
		return tinBoxQRCode;
	}

	public void setTinBoxQRCode(String tinBoxQRCode) {
		this.tinBoxQRCode = tinBoxQRCode;
	}

	public String getTinBoxName() {
		return tinBoxName;
	}

	public void setTinBoxName(String tinBoxName) {
		this.tinBoxName = tinBoxName;
	}

	public String getFolderQRCode() {
		return folderQRCode;
	}

	public void setFolderQRCode(String folderQRCode) {
		this.folderQRCode = folderQRCode;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getPageSize() {
		return pageSize;
	}
	
	public String getPositionQRCode() {
		return positionQRCode;
	}

	public void setPositionQRCode(String positionQRCode) {
		this.positionQRCode = positionQRCode;
	}

	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public int getTotalTinbox() {
		return totalTinbox;
	}

	public void setTotalTinbox(int totalTinbox) {
		this.totalTinbox = totalTinbox;
	}

	public int getTotalFolder() {
		return totalFolder;
	}

	public void setTotalFolder(int totalFolder) {
		this.totalFolder = totalFolder;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
}
