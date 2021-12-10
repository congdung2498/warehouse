package com.viettel.vtnet360.vt02.vt020000.entity;

/**
 * Entity for kitchen (id and name)
 * @author VinhNVQ 9/9/2018
 *
 */
public class VT020000Kitchen {
	String kitchenId;
	String label;
	
	public VT020000Kitchen(String kitchenId, String label) {
		super();
		this.label = label;
		this.kitchenId = kitchenId;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getKitchenId() {
		return kitchenId;
	}
	
	public void setKitchenId(String kitchenId) {
		this.kitchenId = kitchenId;
	}
}
