package com.viettel.vtnet360.vt07.vt070000.entity;

public class DocumentDetails {
	private int documentId;
	private int folderId;
	private String folderQrCode;
	private String folderName;
	private String projectName;
	private String packageName;
	private String contractName;
	private String constructionName;
	private String voucherName;
	private String voucherBookName;
	private String officialDispatchTravelsName;// cong van di
	private String incomingOfficialDispatchName;// cong van den
	private String paymentSummaryName;// bang tong hop thanh toan
	private String type;
	private String documentName;
	private boolean isSynchronyProject;
	private boolean isSynchronyPackage;
	private boolean isSynchronyContract;
	private boolean isSynchronyConstruction;
	private int folderType;

	public int getFolderType() {
		return folderType;
	}

	public void setFolderType(int folderType) {
		this.folderType = folderType;
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getConstructionName() {
		return constructionName;
	}

	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}

	public String getVoucherName() {
		return voucherName;
	}

	public void setVoucherName(String voucherName) {
		this.voucherName = voucherName;
	}

	public String getVoucherBookName() {
		return voucherBookName;
	}

	public void setVoucherBookName(String voucherBookName) {
		this.voucherBookName = voucherBookName;
	}

	public String getOfficialDispatchTravelsName() {
		return officialDispatchTravelsName;
	}

	public void setOfficialDispatchTravelsName(String officialDispatchTravelsName) {
		this.officialDispatchTravelsName = officialDispatchTravelsName;
	}

	public String getIncomingOfficialDispatchName() {
		return incomingOfficialDispatchName;
	}

	public void setIncomingOfficialDispatchName(String incomingOfficialDispatchName) {
		this.incomingOfficialDispatchName = incomingOfficialDispatchName;
	}

	public String getPaymentSummaryName() {
		return paymentSummaryName;
	}

	public void setPaymentSummaryName(String paymentSummaryName) {
		this.paymentSummaryName = paymentSummaryName;
	}

	public boolean getIsSynchronyProject() {
		return isSynchronyProject;
	}

	public void setIsSynchronyProject(boolean isSynchronyProject) {
		this.isSynchronyProject = isSynchronyProject;
	}

	public boolean getIsSynchronyPackage() {
		return isSynchronyPackage;
	}

	public void setIsSynchronyPackage(boolean isSynchronyPackage) {
		this.isSynchronyPackage = isSynchronyPackage;
	}

	public boolean getIsSynchronyContract() {
		return isSynchronyContract;
	}

	public void setIsSynchronyContract(boolean isSynchronyContract) {
		this.isSynchronyContract = isSynchronyContract;
	}

	public boolean getIsSynchronyConstruction() {
		return isSynchronyConstruction;
	}

	public void setIsSynchronyConstruction(boolean isSynchronyConstruction) {
		this.isSynchronyConstruction = isSynchronyConstruction;
	}

}
