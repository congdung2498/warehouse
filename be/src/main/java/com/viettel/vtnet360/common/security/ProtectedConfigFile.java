package com.viettel.vtnet360.common.security;

public class ProtectedConfigFile {
	
    public static void main(String[] args) throws Exception {
        System.out.println("driver password: " + EnDecryptUtils.encrypt("org.mariadb.jdbc.Driver"));
        System.out.println("url password: " + EnDecryptUtils.encrypt("jdbc:mariadb://172.16.19.31:3306/vtnet"));
        System.out.println("username password: " + EnDecryptUtils.encrypt("vtnetuser"));
        System.out.println("password: " + EnDecryptUtils.encrypt("vietnamvodich"));
    }
}