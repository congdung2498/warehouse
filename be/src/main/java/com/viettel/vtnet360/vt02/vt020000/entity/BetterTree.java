package com.viettel.vtnet360.vt02.vt020000.entity;

import java.util.List;

public class BetterTree {
	private int id;
    private String name;
    private int parentId;
    private List<BetterTree> childrenItems;
    
	public BetterTree(int id, String name, int parentId) {
		super();
		this.id = id;
		this.name = name;
		this.parentId = parentId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public List<BetterTree> getChildrenItems() {
		return childrenItems;
	}
	public void setChildrenItems(List<BetterTree> childrenItems) {
		this.childrenItems = childrenItems;
	}
	
	public void addToTree(VT020000Unit unit, String path) {
		if(path != null && Integer.parseInt(path.split("\\/")[1]) == parentId) {
			
		}
	}
}
