package com.viettel.vtnet360.vt02.vt020010.entity;

import java.util.List;

/**
 * @author DuyNK
 *
 */
public class VT020010InfoToFindUnit {

	/** QLDV_UNIT.PARENT_UNIT_ID */
	private int unitParentID;
	
	private String kitchenId;
	
	/** list QLDV_UNIT.UNIT_NAME */
	private List<String> listUnitName;
	
	public VT020010InfoToFindUnit() { }

	public VT020010InfoToFindUnit(int unitParentID, List<String> listUnitName) {
    super();
    this.unitParentID = unitParentID;
    this.listUnitName = listUnitName;
  }
	
	public VT020010InfoToFindUnit(int unitParentID, String kitchenId, List<String> listUnitName) {
		super();
		this.unitParentID = unitParentID;
		this.kitchenId = kitchenId;
		this.listUnitName = listUnitName;
	}

	public int getUnitParentID() { return unitParentID; }
	public void setUnitParentID(int unitParentID) { this.unitParentID = unitParentID; }
	
	public String getKitchenId() { return kitchenId; }
  public void setKitchenId(String kitchenId) { this.kitchenId = kitchenId; }

  public List<String> getListUnitName() { return listUnitName; }
	public void setListUnitName(List<String> listUnitName) { this.listUnitName = listUnitName; }
}
