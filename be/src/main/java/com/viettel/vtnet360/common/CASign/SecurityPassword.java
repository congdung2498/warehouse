package com.viettel.vtnet360.common.CASign;

import com.viettel.security.PassTranformer;
import com.viettel.vtnet360.vt00.common.utils.ResourceBundleUtils;

public class SecurityPassword {
	
	public static String encryptPassword(String pss){
		PassTranformer.setInputKey(ResourceBundleUtils.getResource("ws.autoSign.key"));
		return PassTranformer.encrypt(pss);
	}

	public static String decryptPassword(String pssEncrypt){
		PassTranformer.setInputKey(ResourceBundleUtils.getResource("ws.autoSign.key"));
		return PassTranformer.decrypt(pssEncrypt);
	}
	
	public static void main(String[] args) {
    System.out.println(SecurityPassword.encryptPassword("Binthoi123!QA"));
  }
}
