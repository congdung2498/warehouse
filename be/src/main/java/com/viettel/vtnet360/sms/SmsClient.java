 package com.viettel.vtnet360.sms;

 import java.io.UnsupportedEncodingException;
 import org.apache.commons.codec.binary.Base64;
 import sendmt.MtStub;

 public class SmsClient {
   public static final int SUCCESS_CODE = 0;
   private MtStub stub = null;
   private String sender = "159";
   private String ws_url;
   private String ws_username;
   private String ws_password;

   public SmsClient(String ws_url, String ws_username, String ws_password, String sender) {
     this.sender = sender;
     this.ws_url = ws_url;
     this.ws_username = ws_username;
     this.ws_password = ws_password;
     this.stub = new MtStub(ws_url, "http://tempuri.org/", ws_username, ws_password);
   }

   public int sendSMS(String isdn, String content) {
     if (this.stub == null) this.stub = new MtStub(ws_url, "http://tempuri.org/", ws_username, ws_password);
     
     try {
       String messageEncoded = getBase64(content);
       int result = this.stub.send("0", "BTS_FM", this.sender, isdn, "0", messageEncoded, "1");
       System.out.println("send status " + result);
       return result;
     } catch (Exception e) {
       e.printStackTrace();
     }
     return 1;
   }

   public String getBase64(String value) {
     try {
       return new String(Base64.encodeBase64(value.getBytes("ASCII")), "ASCII");
     } catch (UnsupportedEncodingException e) {
     }
     return "";
   }
 }
