package com.viettel.vtnet360.signVoffice.entity;

import java.util.Date;
import java.util.List;

public class SignVofficeEntity {
	private Long signVofficeId;
	private String documentCode;
	private String content;
	private String requestUserName;
	private String fullName;
	private String lastSignEmail;
	private Date signTime;
	private int status;
	private Date createTime;
	private Date approveTime;
	private int type;
	private String signComment;
	private String file;
	private String[] issuedServiceId;
	private Date fromDate;
	private Date toDate;
	private String[] listUnitId;
	private String controlId;
	private String transCode;
	private String lastSignFullName;
	private Long voTextId;
	private String userNameVoffice;
	private String passwordVoffice;
	private List<SignVofficeEntity> detailPdfs;
	

	public Long getSignVofficeId() {
		return signVofficeId;
	}

	public void setSignVofficeId(Long signVofficeId) {
		this.signVofficeId = signVofficeId;
	}

	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRequestUserName() {
		return requestUserName;
	}

	public void setRequestUserName(String requestUserName) {
		this.requestUserName = requestUserName;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String[] getIssuedServiceId() {
		return issuedServiceId;
	}

	public void setIssuedServiceId(String[] issuedServiceId) {
		this.issuedServiceId = issuedServiceId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String[] getListUnitId() {
		return listUnitId;
	}

	public void setListUnitId(String[] listUnitId) {
		this.listUnitId = listUnitId;
	}

	public String getControlId() {
		return controlId;
	}

	public void setControlId(String controlId) {
		this.controlId = controlId;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getLastSignEmail() {
		return lastSignEmail;
	}

	public void setLastSignEmail(String lastSignEmail) {
		this.lastSignEmail = lastSignEmail;
	}

	public String getSignComment() {
		return signComment;
	}

	public void setSignComment(String signComment) {
		this.signComment = signComment;
	}

	public String getLastSignFullName() {
		return lastSignFullName;
	}

	public void setLastSignFullName(String lastSignFullName) {
		this.lastSignFullName = lastSignFullName;
	}

	public Long getVoTextId() {
		return voTextId;
	}

	public void setVoTextId(Long voTextId) {
		this.voTextId = voTextId;
	}

	public String getUserNameVoffice() {
		return userNameVoffice;
	}

	public void setUserNameVoffice(String userNameVoffice) {
		this.userNameVoffice = userNameVoffice;
	}

	public String getPasswordVoffice() {
		return passwordVoffice;
	}

	public void setPasswordVoffice(String passwordVoffice) {
		this.passwordVoffice = passwordVoffice;
	}

  public List<SignVofficeEntity> getDetailPdfs() {
    return detailPdfs;
  }

  public void setDetailPdfs(List<SignVofficeEntity> detailPdfs) {
    this.detailPdfs = detailPdfs;
  }
}
