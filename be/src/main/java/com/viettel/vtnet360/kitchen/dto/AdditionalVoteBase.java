package com.viettel.vtnet360.kitchen.dto;

import java.io.IOException;
import java.util.Date;
import org.apache.log4j.Logger;

import org.codehaus.jackson.map.ObjectMapper;

import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;

public class AdditionalVoteBase  extends AdditionalInfoBase {
  private final Logger logger = Logger.getLogger(this.getClass());
  private Date lunchDate;
  

  public AdditionalVoteBase(Date lunchDate) {
    this.lunchDate = lunchDate;
  }
  
  public AdditionalVoteBase() { }

  public Date getLunchDate() { return lunchDate; }
  public void setLunchDate(Date lunchDate) { this.lunchDate = lunchDate; }
  
  
  @Override
  public String toString() {
    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      AdditionalVoteBase jsonObject = new AdditionalVoteBase();
      jsonObject.setId(this.getId());
      jsonObject.setRoleReceiver(this.getRoleReceiver());
      jsonObject.setLunchDate(this.lunchDate);

      jsonStr = mapperObj.writeValueAsString(jsonObject);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
    return jsonStr;
  }
}
