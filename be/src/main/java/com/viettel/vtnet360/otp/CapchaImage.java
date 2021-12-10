package com.viettel.vtnet360.otp;

public class CapchaImage {
  
  private byte[] image;
  private String capcha;
  
  public CapchaImage(byte[] image, String capcha) {
    this.image = image;
    this.capcha = capcha;
  }
  
  
  public byte[] getImage() { return image; }
  public void setImage(byte[] image) { this.image = image; }
  
  public String getCapcha() { return capcha; }
  public void setCapcha(String capcha) { this.capcha = capcha; }
}
