package com.viettel.vtnet360.vt07.vt070005.entity;

import java.util.ArrayList;
import java.util.List;

public class ImportedDataRow {
	private String tinBoxQrCode;
	private String folderQrCode;
	private Integer type;
	private String name;
	private String docName;
	private String levelBaomatDoc;
	private String createUser;
	public String folderName;
	private String officialDispatchId;
	public long tinBoxId = 0;
	public long folderId = 0;
	public long paymentSummaryId=0;
	public long voucherId=0;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getLevelBaomatDoc() {
		return levelBaomatDoc;
	}

	public void setLevelBaomatDoc(String levelBaomatDoc) {
		this.levelBaomatDoc = levelBaomatDoc;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getOfficialDispatchId() {
		return officialDispatchId;
	}

	public void setOfficialDispatchId(String officialDispatchId) {
		this.officialDispatchId = officialDispatchId;
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

	public long getPaymentSummaryId() {
		return paymentSummaryId;
	}

	public void setPaymentSummaryId(long paymentSummaryId) {
		this.paymentSummaryId = paymentSummaryId;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}
	
}
