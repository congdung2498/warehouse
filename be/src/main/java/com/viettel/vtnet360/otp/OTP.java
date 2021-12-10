package com.viettel.vtnet360.otp;

import java.util.Date;

public class OTP {
  
  private String  username;
  private String  otp;
  private int     count;
  private String  capcha;
  private Date    createDate;
  
  
  public OTP() { }
  
  public OTP(String username, String otp, int count, String capcha, Date createDate) {
    this.username = username;
    this.otp = otp;
    this.count = count;
    this.capcha = capcha;
    this.createDate = createDate;
  }
  
  
  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }

  public String getOtp() { return otp; }
  public void setOtp(String otp) { this.otp = otp; }
  
  public int getCount() { return count; }
  public void setCount(int count) { this.count = count; }
  
  public String getCapcha() { return capcha; }
  public void setCapcha(String capcha) { this.capcha = capcha; }
  
  public Date getCreateDate() { return createDate; }
  public void setCreateDate(Date createDate) { this.createDate = createDate; }
}
