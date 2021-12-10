package com.viettel.vtnet360.vt07.vt070003.entity;

import java.util.ArrayList;
import java.util.List;

public class ImportedDataRow {
	private String tinBoxQrCode;
	private String folderQrCode;
	private String officialDispatchId;
	private Integer type;
	private String officialDispatchName;
	private String officialDispatchDocName;
	private String levelBaomatOfficialDispatchDoc;
	private String createUser;
	public String folderName;
	public long tinBoxId = 0;
	public long folderId = 0;
	private int rowAffected = 0;
	private int unit = 0;
	private List<Integer> errorCodes = new ArrayList<Integer>();
	private boolean criticalError = false;
	private String rowReport;

	public void insertErrorCode(int errorCode) {
		this.errorCodes.add(errorCode);
	}

	public String getTinBoxQrCode() {
		return tinBoxQrCode;
	}

	public void setTinBoxQrCode(String tinBoxQrCode) {
		this.tinBoxQrCode = tinBoxQrCode;
	}

	public String getFolderQrCode() {
		return folderQrCode;
	}

	public void setFolderQrCode(String folderQrCode) {
		this.folderQrCode = folderQrCode;
	}

	public String getOfficialDispatchId() {
		return officialDispatchId;
	}

	public void setOfficialDispatchId(String officialDispatchId) {
		this.officialDispatchId = officialDispatchId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOfficialDispatchName() {
		return officialDispatchName;
	}

	public void setOfficialDispatchName(String officialDispatchName) {
		this.officialDispatchName = officialDispatchName;
	}

	public String getOfficialDispatchDocName() {
		return officialDispatchDocName;
	}

	public void setOfficialDispatchDocName(String officialDispatchDocName) {
		this.officialDispatchDocName = officialDispatchDocName;
	}

	public String getLevelBaomatOfficialDispatchDoc() {
		return levelBaomatOfficialDispatchDoc;
	}

	public void setLevelBaomatOfficialDispatchDoc(String levelBaomatOfficialDispatchDoc) {
		this.levelBaomatOfficialDispatchDoc = levelBaomatOfficialDispatchDoc;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	public boolean isCriticalError() {
		return criticalError;
	}

	public void setCriticalError(boolean criticalError) {
		this.criticalError = criticalError;
	}

	public String getRowReport() {
		return rowReport;
	}

	public void setRowReport(String rowReport) {
		this.rowReport = rowReport;
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

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

}
