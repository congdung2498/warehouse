package com.viettel.vtnet360.vt02.vt020010.entity;

/**
 * @author DuyNK
 *
 */
public class VT020010Unit {

	/** QLDV_UNIT.UNIT_ID */
	private int unitID;

	/** QLDV_UNIT.UNIT_NAME theo 3 cap */
	private String unitName;

	private String parentId;

	private String path;

	private String kitchenId;

	public VT020010Unit() {

	}

	public VT020010Unit(int unitID, String unitName) {
		super();
		this.unitID = unitID;
		this.unitName = unitName;
	}

	public int getUnitID() {
		return unitID;
	}

	public void setUnitID(int unitID) {
		this.unitID = unitID;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getKitchenId() {
		return kitchenId;
	}

	public void setKitchenId(String kitchenId) {
		this.kitchenId = kitchenId;
	}

}
