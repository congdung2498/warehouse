package com.viettel.vtnet360.kitchen.dto;

public class AbbreviationsDTO {

	private String kitchenId;
	private String kitchenName;
	private Long unitId;
	private String unitName;
	private String shortName;
	private String note;
	private String path;
	private String createUser;
	private String detailUnit;
	
	
	
	public String getDetailUnit() {
    return detailUnit;
  }

  public void setDetailUnit(String detailUnit) {
    this.detailUnit = detailUnit;
  }

  public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	public String getKitchenName() {
		return kitchenName;
	}

	public void setKitchenName(String kitchenName) {
		this.kitchenName = kitchenName;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	

	public AbbreviationsDTO(String kitchenId, String kitchenName, Long unitId, String unitName, String shortName,
			String note, String path, String createUser) {
		super();
		this.kitchenId = kitchenId;
		this.kitchenName = kitchenName;
		this.unitId = unitId;
		this.unitName = unitName;
		this.shortName = shortName;
		this.note = note;
		this.path = path;
		this.createUser = createUser;
	}

	public AbbreviationsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
