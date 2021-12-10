package com.viettel.vtnet360.vt07.vt070005.entity;

import java.util.List;

public class PaymentSummaryVO {
	private long paymentSummaryId;
	private String name;
	private long folderId;
	private int status;
	private List<PaymentSummaryDocVO> paymentSummaryDocs;

	public PaymentSummaryVO() {
		super();
	}

	public long getPaymentSummaryId() {
		return paymentSummaryId;
	}

	public void setPaymentSummaryId(long paymentSummaryId) {
		this.paymentSummaryId = paymentSummaryId;
	}

	public List<PaymentSummaryDocVO> getPaymentSummaryDocs() {
		return paymentSummaryDocs;
	}

	public void setPaymentSummaryDocs(List<PaymentSummaryDocVO> paymentSummaryDocs) {
		this.paymentSummaryDocs = paymentSummaryDocs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getFolderId() {
		return folderId;
	}

	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
