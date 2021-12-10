 package com.viettel.vtnet360.sms;

 import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
 public class SmsConfig {
   private static String ws_url;
   private static String ws_username;
   private static String ws_password;
   private static String mobile_prefix;
   private static String sender;
   
   @Autowired
   private static Properties sms;

   public static void init() {
     Properties propertiesFile = new Properties();
     FileInputStream propsFile = null;
     try {
       propsFile = new FileInputStream("../etc/sms.cfg");
       propertiesFile.load(propsFile);
       try {
         ws_url = propertiesFile.getProperty("smsws_url");
         ws_url = sms.getProperty("smsws_url");
         System.out.println("");
       } catch (Exception e) {
         ws_url = "http://192.168.176.42:8008/vasp/Service.asmx";
       }
       try {
         ws_username = propertiesFile.getProperty("smsws_username");
         ws_username = sms.getProperty("smsws_username");
       } catch (Exception e) {
         ws_username = "smsbilling";
       }
       try {
         ws_password = propertiesFile.getProperty("smsws_password");
       } catch (Exception e) {
         ws_password = "smsbilling";
       }
       try {
         mobile_prefix = propertiesFile.getProperty("mobile_prefix");
       } catch (Exception e) {
         mobile_prefix = "84";
       }
       try {
         sender = propertiesFile.getProperty("sender");
       } catch (Exception e) {
         sender = "159";
       }
     } catch (FileNotFoundException e) {
       e.printStackTrace();
       throw new RuntimeException("Server config not found.", e);
     } catch (IOException e) {
       throw new RuntimeException("Error while load properties in config file ../etc/sms.cfg", e);
     }
     finally {
       if (propsFile != null) {
         try {
           propsFile.close();
         } catch (IOException e) {
         }
       }
     }
   }

   public static String getConfigFile() { return "../etc/sms.cfg"; }

   public static String getMobile_prefix() { return mobile_prefix; }

   public static String getSender() { return sender; }

   public static String getWs_password() { return ws_password; }

   public static String getWs_url() { return ws_url; }

   public static String getWs_username() { return ws_username; }
 }