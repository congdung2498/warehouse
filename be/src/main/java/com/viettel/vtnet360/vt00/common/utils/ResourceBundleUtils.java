/*
 * Copyright (C) 2010 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vtnet360.vt00.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

/**
 *
 * @author thienkq1@viettel.com.vn
 * @since 12,Apr,2010
 * @version 1.0
 */
public class ResourceBundleUtils {

    /** rb.*/
    private static ResourceBundle rb = null;
    private static final String pathMailConfig = "mail";
    private static Properties fileMailConfig = null;
    private static Logger logger = Logger.getLogger(ResourceBundleUtils.class);

    /** Creates a new instance of ResourceBundleUtils */
    private ResourceBundleUtils() {
    }

    public static String getMailConfigByKey(String key) {
        String rs = null;
        if (fileMailConfig == null || fileMailConfig.isEmpty()) {
//            File f = new File(pathMailConfig);
            rb = ResourceBundle.getBundle(pathMailConfig);
            rs = rb.getString(key);

        }
        return rs;
    }

    /**
     * Config DB
    public static String getValueByKeyConfig(String key) {
    File f = new File(path);
    logger.info("path File:" + f.getAbsolutePath());
    InputStream is = null;
    try {
    is = new FileInputStream(f);
    } catch (FileNotFoundException ex) {
    logger.error(ex);
    }
    Properties pr = new Properties();
    try {
    pr.load(is);
    } catch (IOException ex) {
    logger.error(ex);
    }
    return pr.getProperty(key);
    }

    /**
     * method get resource
     * @param key String
     * @return String
     */
    public static String getResource(String key) {
        rb = ResourceBundle.getBundle("config");
        return rb.getString(key);
    }

    public static String getValueByKey(String key) {
        rb = ResourceBundle.getBundle("config");
        return rb.getString(key);
    }

    public static String getPathImportConfigFile() {
        rb = ResourceBundle.getBundle("config");
        return rb.getString("construction.importconfig.filepath");
    }

    /*public static void main(String[] args) throws Exception {
        // TODO code application logic here        
        System.out.println(ResourceBundleUtils.getMailConfigByKey("SMTP_HOST_NAME"));
        //gui mail
        MailUtil mailUtil = new MailUtil();
        String lstUser = "thaont19@viettel.com.vn,anttt2@viettel.com.vn";
        mailUtil.sendMail(lstUser.split(","), "test gui mail TKTU");
        System.out.println("gui mail xong");
    }*/
}
