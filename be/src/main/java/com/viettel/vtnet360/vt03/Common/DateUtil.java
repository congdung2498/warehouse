package com.viettel.vtnet360.vt03.Common;

import java.util.Date;

public class DateUtil extends Date {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FORMAT_DATE = "dd/MM/yyyy HH:mm";
	
	
	public DateUtil(Date date) {
		this.setTime(date.getTime());
	}
	public String toString(String format) {
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(format);
		return dateFormat.format(this);
	}
}
