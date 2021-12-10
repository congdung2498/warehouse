package com.viettel.vtnet360.driving.dto;

import com.viettel.vtnet360.common.security.BaseEntity;

public class SearchData extends BaseEntity {
	private String driveSquadId ;
	private String searchDTO ;

	public String getDriveSquadId() {
    return driveSquadId;
  }


  public void setDriveSquadId(String driveSquadId) {
    this.driveSquadId = driveSquadId;
  }


  public String getSearchDTO() {
		return searchDTO;
	}


	public void setSearchDTO(String searchDTO) {
		this.searchDTO = searchDTO;
	}


	public SearchData(String searchDTO) {
		super();
		this.searchDTO = searchDTO;
	}


	public SearchData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
