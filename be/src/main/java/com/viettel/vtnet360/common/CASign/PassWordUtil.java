package com.viettel.vtnet360.common.CASign;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class PassWordUtil {
  private static PassWordUtil instance;

  private PassWordUtil() {
  }

  public synchronized String encrypt(String plaintext) throws Exception {
    String hash = null;
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("SHA-1"); // step 2
      if (md != null) {
        md.update(plaintext.getBytes("UTF-8")); // step 3
        byte raw[] = md.digest(); // step 4
        hash = (new BASE64Encoder()).encode(raw); // step 5
      }
    } catch (NoSuchAlgorithmException e) {
    }
    return hash; // step 6
  }

  public static synchronized PassWordUtil getInstance() // step 1
  {
    if (instance == null) {
      instance = new PassWordUtil();
    }
    return instance;
  }
}