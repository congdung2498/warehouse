package com.viettel.vtnet360.common.security;

public class Account {
  
  private String securityUsername;
  private String securityPassword;
  
  
  public Account(String username, String password) {
    this.securityUsername = username;
    this.securityPassword = password;
  }
  
  
  public String getSecurityUsername() { return securityUsername; }
  public void setSecurityUsername(String username) { this.securityUsername = username; }

  public String getSecurityPassword() { return securityPassword; }
  public void setSecurityPassword(String password) { this.securityPassword = password; }
}
