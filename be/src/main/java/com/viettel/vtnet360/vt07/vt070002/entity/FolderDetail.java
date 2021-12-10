package com.viettel.vtnet360.vt07.vt070002.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;
import com.viettel.vtnet360.vt07.vt070003.entity.OfficialDispatch;
import com.viettel.vtnet360.vt07.vt070005.entity.PaymentSummaryVO;
import com.viettel.vtnet360.vt07.vt070005.entity.VoucherVO;

public class FolderDetail extends BaseEntity implements Serializable {
	private long folderId;
	private long tinBoxId;
	private String name;
	private String qrCode;
	private int status;
	private long borrowId;
	private int action; //1: create, 2: edit, 3: delete
	private String error;
	private int delFlag;
	private List<ProjectDetail> projects = new ArrayList<>();
	private List<VoucherVO> vouchers = new ArrayList<>();
	private List<PaymentSummaryVO> paymmentSummarys = new ArrayList<>();
	private List<OfficialDispatch> officialDispatchs = new ArrayList<>();

	public List<OfficialDispatch> getOfficialDispatchs() {
		return officialDispatchs;
	}
	public void setOfficialDispatchs(List<OfficialDispatch> officialDispatchs) {
		this.officialDispatchs = officialDispatchs;
	}
	public List<VoucherVO> getVouchers() {
		return vouchers;
	}
	public void setVouchers(List<VoucherVO> vouchers) {
		this.vouchers = vouchers;
	}
	public List<PaymentSummaryVO> getPaymmentSummarys() {
		return paymmentSummarys;
	}
	public void setPaymmentSummarys(List<PaymentSummaryVO> paymmentSummarys) {
		this.paymmentSummarys = paymmentSummarys;
	}
	private int totalProject;
	private int unit;
	private int type;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(long borrowId) {
		this.borrowId = borrowId;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<ProjectDetail> getProjects() {
		return projects;
	}
	public void setProjects(List<ProjectDetail> projects) {
		this.projects = projects;
	}
	public int getTotalProject() {
		return totalProject;
	}
	public void setTotalProject(int totalProject) {
		this.totalProject = totalProject;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
