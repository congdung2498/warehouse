package com.viettel.vtnet360.vt07.vt070000.entity.search;

public class PackageDetailSearch {
	private int packageId;
	private String packageName;
	private String projectName;
	private String folderName;
	private String folderQRCode;
	private String tinBoxQRCode;
	private String tinBoxName;
	private boolean isSynchrony;
	private int tinBoxId;
	private int folderId;
	private int projectId;
	private String column;
	private String row;
	private String height;
	private String slotQRCode;

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getTinBoxId() {
		return tinBoxId;
	}

	public void setTinBoxId(int tinBoxId) {
		this.tinBoxId = tinBoxId;
	}

	public boolean isSynchrony() {
		return isSynchrony;
	}

	public void setSynchrony(boolean isSynchrony) {
		this.isSynchrony = isSynchrony;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getFolderQRCode() {
		return folderQRCode;
	}

	public void setFolderQRCode(String folderQRCode) {
		this.folderQRCode = folderQRCode;
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

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getSlotQRCode() {
		return slotQRCode;
	}

	public void setSlotQRCode(String slotQRCode) {
		this.slotQRCode = slotQRCode;
	}

}
