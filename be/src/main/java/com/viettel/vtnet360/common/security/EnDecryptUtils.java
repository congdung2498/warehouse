package com.viettel.vtnet360.common.security;

import org.apache.log4j.Logger;

import com.viettel.security.PassTranformer;
import com.viettel.vtnet360.vt00.common.Constant;

public class EnDecryptUtils {

  private static Logger logger = Logger.getLogger(EnDecryptUtils.class);

  public synchronized static String encrypt(String input, String salt) {
    PassTranformer.setInputKey(salt);
    return PassTranformer.encrypt(input);
  }

  public synchronized static String encrypt(String input) {
    return encrypt(input, Constant.DEFAULT_SALT);
  }

  public synchronized static String decrypt(String input, String salt) {
    try {
      PassTranformer.setInputKey(salt);
      return PassTranformer.decrypt(input);
    } catch (Exception ex) {
      logger.error("PassTranformer decrypt error :" + input, ex);
      return input;
    }
  }

  public synchronized static String decrypt(String input) {
    return decrypt(input, Constant.DEFAULT_SALT);
  }
}
