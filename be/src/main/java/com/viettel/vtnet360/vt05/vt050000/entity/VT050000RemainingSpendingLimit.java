package com.viettel.vtnet360.vt05.vt050000.entity;

/**
 * @author DuyNK 04/10/2018
 */
public class VT050000RemainingSpendingLimit {

	/** spending limit of employee */
	private double spendingLimit;

	public VT050000RemainingSpendingLimit() {

	}

	public VT050000RemainingSpendingLimit(double spendingLimit) {
		super();
		this.spendingLimit = spendingLimit;
	}

	public double getSpendingLimit() {
		return spendingLimit;
	}

	public void setSpendingLimit(double spendingLimit) {
		this.spendingLimit = spendingLimit;
	}
}
