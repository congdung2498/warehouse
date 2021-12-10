package com.viettel.vtnet360.vt02.vt020006.entity;

/**
 * @author DuyNK
 *
 */
public class VT020006ContentSms {

	/** total lunchDate in a day */
	private int total;
	
	/** total lunchDate in a day in each unit */
	private String content;
	
	public VT020006ContentSms() {
	
	}

	public VT020006ContentSms(int total, String content) {
		super();
		this.total = total;
		this.content = content;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
