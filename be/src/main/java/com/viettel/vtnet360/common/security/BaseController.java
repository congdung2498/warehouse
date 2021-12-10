package com.viettel.vtnet360.common.security;

import java.util.Properties;

public class BaseController {
  
  protected Account getAccountInfo(Properties properties) {
    return new Account(properties.getProperty("SECURITY_USERNAME"), properties.getProperty("SECURITY_PASSWORD"));
  }
  
  protected boolean isValidated(Properties properties, String username, String password) {
    if(username == null || password == null) return false;
    
    Account account = getAccountInfo(properties);
    String encryptUsername = EnDecryptUtils.encrypt(username);
    String encryptPassword = EnDecryptUtils.encrypt(password);
    
    if(account.getSecurityUsername().equals(encryptUsername) && account.getSecurityPassword().equals(encryptPassword)) return true;
    return false;
  }
}
