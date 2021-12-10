/* 
 * Copyright 2011 Viettel Telecom. All rights reserved. 
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package com.viettel.vtnet360.vt00.vt000000.entity;

import java.util.Date;

/**
 *
 * @author: ThuanNHT
 * @version: 1.0
 * @since: 1.0
 */
public class SignVofficeLogBO {

    private Long signVofficeId;
    private Long action;
    private String userCreate;
    private Long id;
    private Date updateTime;
    private String content;

    
    public Long getSignVofficeId() {
        return signVofficeId;
    }

    public void setSignVofficeId(Long signVofficeId) {
        this.signVofficeId = signVofficeId;
    }

    
    public Long getAction() {
        return action;
    }

    public void setAction(Long action) {
        this.action = action;
    }

    
    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SignVofficeLogBO() {
    }

    public SignVofficeLogBO(Long id) {
        this.id = id;
    }
}
