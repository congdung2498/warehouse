package com.viettel.vtnet360.sms;

public class SmsService {
  
  private SmsClient        sms = null;
  
  public void send(String phoneNumber, String otp) {
    this.sms = new SmsClient(SmsConfig.getWs_url(), SmsConfig.getWs_username(), SmsConfig.getWs_password(), SmsConfig.getSender());
    String msisdn = prefix(phoneNumber);
    
    String content = "[VTNET360] ma bao mat " + otp + " co hieu luc trong 5 phut";
    this.sms.sendSMS(msisdn, content);
  }
  
  private String prefix(String phoneNumber) {
    String tmp = phoneNumber;
    if (tmp.startsWith("0")) tmp = phoneNumber + tmp.substring(1);
    if (!tmp.startsWith(phoneNumber)) tmp = phoneNumber + tmp;
    return tmp;
  }
}
