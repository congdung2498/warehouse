package com.viettel.vtnet360.vt02.vt020000.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity for Unit inherit structure
 * @author VinhNVQ 9/9/2018
 *
 */
public class VT020000MegaUnit {
	private Long id;
    private String name;
    private Long parentId;
    private String path;
    private Long order;
    private List<VT020000MegaUnit> childrenItems; 

    public VT020000MegaUnit() {
        this.id = null;
        this.name = "";     
        this.parentId = null;
        this.childrenItems = new ArrayList<VT020000MegaUnit>();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long i) {
        id = i;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public List<VT020000MegaUnit> getChildrenItems() {
        return childrenItems;
    }
    public void setChildrenItems(List<VT020000MegaUnit> childrenItems) {
        this.childrenItems = childrenItems;
    }
    public void addChildrenItem(VT020000MegaUnit childrenItem){
        if(!this.childrenItems.contains(childrenItem))
            this.childrenItems.add(childrenItem);
    }

    @Override
    public String toString() {
        return "Unit [Id=" + id + ", name=" + name + ", parentId="
                + parentId + ", childrenItems=" + childrenItems + "]";
    }

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}
}
