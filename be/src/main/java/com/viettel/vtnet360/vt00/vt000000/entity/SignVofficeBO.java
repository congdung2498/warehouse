/* 
 * Copyright 2011 Viettel Telecom. All rights reserved. 
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package com.viettel.vtnet360.vt00.vt000000.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author: ThuanNHT
 * @version: 1.0
 * @since: 1.0
 */
public class SignVofficeBO implements Serializable{

    private String status;
    private String lastSignEmail;
    private String signComment;
    private String transCode;
    private String appCode;
    private String documentCode;
    private String publishOganizationCode;
    private String publishDate;
    private Date updateTime;
    private Long id;
    private Date createTime;
    private String type;
    private String docTitle;
    private String statusName;

    @Transient
    public String getStatusName() {
        if (status != null) {
            if (status.equals("0")) {
                statusName= "ĐANG TRÌNH KÝ";
            } else if (status.equals("3")) {
                statusName= "ĐÃ KÝ";
            } else if (status.equals("5")) {
                statusName= "ĐÃ BAN HÀNH";
            }else {
                statusName = "BỊ TỪ CHỐI";
            }
        }
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    
    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }
    

    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    public String getLastSignEmail() {
        return lastSignEmail;
    }

    public void setLastSignEmail(String lastSignEmail) {
        this.lastSignEmail = lastSignEmail;
    }

    
    public String getSignComment() {
        return signComment;
    }

    public void setSignComment(String signComment) {
        this.signComment = signComment;
    }

    
    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    
    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    
    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    
    public String getPublishOganizationCode() {
        return publishOganizationCode;
    }

    public void setPublishOganizationCode(String publishOganizationCode) {
        this.publishOganizationCode = publishOganizationCode;
    }

    
    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SignVofficeBO() {
    }

    public SignVofficeBO(Long id) {
        this.id = id;
    }
}
