package com.viettel.vtnet360.vt07.vt070005.entity;

public class PaymentSummaryDocVO {
	private long id;
	private long paymentSummaryId;
	private String name;
	private long folderId;
	private int type;
	private String levelBaomat;

	public PaymentSummaryDocVO() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPaymentSummaryId() {
		return paymentSummaryId;
	}

	public void setPaymentSummaryId(long paymentSummaryId) {
		this.paymentSummaryId = paymentSummaryId;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLevelBaomat() {
		return levelBaomat;
	}

	public void setLevelBaomat(String levelBaomat) {
		this.levelBaomat = levelBaomat;
	}

}
