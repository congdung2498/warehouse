package com.viettel.vtnet360.vt07.vt070000.entity;

import java.util.ArrayList;
import java.util.List;

public class ImportedDataRow {
	public String tinBoxQrCode;
	public String createUser;
	public String folderQrCode;
	public String folderName;
	public String projectName;
	public String projectDoc;
	public String packageName;
	public String packageDoc;
	public String contractName;
	public String contractDoc;
	public String constructionName;
	public String constructionDoc;
	
	public String levelBaoMatProject;
	public String levelBaoMatPackage;
	public String levelBaoMatContract;
	public String levelBaoMatConstruction;

	public int rowAffected = 0;
	public int unit = 0;
	public List<Integer> errorCodes = new ArrayList<Integer>();
	public boolean criticalError = false;
	public long tinBoxId = 0;
	public long folderId = 0;
	public long projectId = 0;
	public String rowReport;
	
	public String getTinBoxQrCode() {
		return tinBoxQrCode;
	}
	public void setTinBoxQrCode(String tinBoxQrCode) {
		this.tinBoxQrCode = tinBoxQrCode;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getFolderQrCode() {
		return folderQrCode;
	}
	public void setFolderQrCode(String folderQrCode) {
		this.folderQrCode = folderQrCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectDoc() {
		return projectDoc;
	}
	public void setProjectDoc(String projectDoc) {
		this.projectDoc = projectDoc;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageDoc() {
		return packageDoc;
	}
	public void setPackageDoc(String packageDoc) {
		this.packageDoc = packageDoc;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getContractDoc() {
		return contractDoc;
	}
	public void setContractDoc(String contractDoc) {
		this.contractDoc = contractDoc;
	}
	public String getConstructionName() {
		return constructionName;
	}
	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}
	public String getConstructionDoc() {
		return constructionDoc;
	}
	public void setConstructionDoc(String constructionDoc) {
		this.constructionDoc = constructionDoc;
	}
	public int getRowAffected() {
		return rowAffected;
	}
	public void setRowAffected(int rowAffected) {
		this.rowAffected = rowAffected;
	}
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	public List<Integer> getErrorCodes() {
		return errorCodes;
	}
	public void setErrorCodes(List<Integer> errorCodes) {
		this.errorCodes = errorCodes;
	}	
	public void insertErrorCode(int errorCode) {
		this.errorCodes.add(errorCode);
	}
	public boolean isCriticalError() {
		return criticalError;
	}
	public void setCriticalError(boolean criticalError) {
		this.criticalError = criticalError;
	}
	public long getTinBoxId() {
		return tinBoxId;
	}
	public void setTinBoxId(long tinBoxId) {
		this.tinBoxId = tinBoxId;
	}
	public long getFolderId() {
		return folderId;
	}
	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public String getRowReport() {
		return rowReport;
	}
	public void setRowReport(String rowReport) {
		this.rowReport = rowReport;
	}
	public String getLevelBaoMatProject() {
		return levelBaoMatProject;
	}
	public void setLevelBaoMatProject(String levelBaoMatProject) {
		this.levelBaoMatProject = levelBaoMatProject;
	}
	public String getLevelBaoMatPackage() {
		return levelBaoMatPackage;
	}
	public void setLevelBaoMatPackage(String levelBaoMatPackage) {
		this.levelBaoMatPackage = levelBaoMatPackage;
	}
	public String getLevelBaoMatContract() {
		return levelBaoMatContract;
	}
	public void setLevelBaoMatContract(String levelBaoMatContract) {
		this.levelBaoMatContract = levelBaoMatContract;
	}
	public String getLevelBaoMatConstruction() {
		return levelBaoMatConstruction;
	}
	public void setLevelBaoMatConstruction(String levelBaoMatConstruction) {
		this.levelBaoMatConstruction = levelBaoMatConstruction;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	
}
