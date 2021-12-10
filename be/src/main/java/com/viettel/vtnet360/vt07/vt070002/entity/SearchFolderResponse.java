package com.viettel.vtnet360.vt07.vt070002.entity;

public class SearchFolderResponse {
	private long tinBoxId;
	private String tinBoxQrCode;
	private String tinBoxName;
	private long folderId;
	private String folderQrCode;
	private String folderName;
	private int totalFoundRecord;
	
	public long getTinBoxId() {
		return tinBoxId;
	}
	public void setTinBoxId(long tinBoxId) {
		this.tinBoxId = tinBoxId;
	}
	public String getTinBoxQrCode() {
		return tinBoxQrCode;
	}
	public void setTinBoxQrCode(String tinBoxQrCode) {
		this.tinBoxQrCode = tinBoxQrCode;
	}
	public String getTinBoxName() {
		return tinBoxName;
	}
	public void setTinBoxName(String tinBoxName) {
		this.tinBoxName = tinBoxName;
	}
	public long getFolderId() {
		return folderId;
	}
	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}
	public String getFolderQrCode() {
		return folderQrCode;
	}
	public void setFolderQrCode(String folderQrCode) {
		this.folderQrCode = folderQrCode;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public int getTotalFoundRecord() {
		return totalFoundRecord;
	}
	public void setTotalFoundRecord(int totalFoundRecord) {
		this.totalFoundRecord = totalFoundRecord;
	}
	
	
}
