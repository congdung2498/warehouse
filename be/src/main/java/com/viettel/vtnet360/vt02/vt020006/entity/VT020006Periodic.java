package com.viettel.vtnet360.vt02.vt020006.entity;

/**
 * @author DuyNK 12/09/2018
 */
public class VT020006Periodic {

	/** days Of week that periodic = 1 -- Mon = 0, Tue = 1,... */
	private int dayOfWeek;

	public VT020006Periodic() {

	}

	public VT020006Periodic(int dayOfWeek) {
		super();
		this.dayOfWeek = dayOfWeek;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

}
