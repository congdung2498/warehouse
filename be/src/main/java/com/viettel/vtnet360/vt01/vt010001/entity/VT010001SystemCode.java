package com.viettel.vtnet360.vt01.vt010001.entity;

import java.io.Serializable;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Created by VTN-PTPM-NV64 on 3/6/2019.
 */
public class VT010001SystemCode extends BaseEntity implements Serializable {
  private String codeValue;
  private String codeName;
  private String masterClass;


  public String getCodeValue() { return codeValue; }
  public void setCodeValue(String codeValue) { this.codeValue = codeValue; }

  public String getCodeName() { return codeName; }
  public void setCodeName(String codeName) { this.codeName = codeName; }

  public String getMasterClass() { return masterClass; }
  public void setMasterClass(String masterClass) { this.masterClass = masterClass; }
}
