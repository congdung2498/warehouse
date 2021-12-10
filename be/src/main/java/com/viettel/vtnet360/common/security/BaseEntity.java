package com.viettel.vtnet360.common.security;

public class BaseEntity {
  
  private String securityUsername;
  private String securityPassword;
  
  
  public String getSecurityUsername() { return securityUsername; }
  public void setSecurityUsername(String account) { this.securityUsername = account; }
  
  public String getSecurityPassword() { return securityPassword; }
  public void setSecurityPassword(String password) { this.securityPassword = password; }
}
