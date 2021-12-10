package com.viettel.vtnet360.vt03.vt030013.entity;

import java.util.ArrayList;
import java.util.List;



public class MegaUnit {
	private int unitId;
	private String unitName;
	private int parentId;
	private List<MegaUnit> childrenItems;
	
	public MegaUnit() {
		this.childrenItems = new ArrayList<MegaUnit>();
	}

	public MegaUnit(int unitId, String unitName, int parentId, List<MegaUnit> childrenItems) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
		this.parentId = parentId;
		this.childrenItems = new ArrayList<MegaUnit>();
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public List<MegaUnit> getChildrenItems() {
		return childrenItems;
	}

	public void setChildrenItems(List<MegaUnit> childrenItems) {
		this.childrenItems = childrenItems;
	}
	
	
	public void addChildrenItem(MegaUnit childrenItem){
        if(!this.childrenItems.contains(childrenItem))
            this.childrenItems.add(childrenItem);
    }
	
}
