/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.vtnet360.vt00.vt000000.entity;

/**
 *
 * @author vipc
 */
public class VofficeUserSign {

    private Long userId;
    private String fullName;
    private String employeeCode;
    private String jobTile;
    private String email;
    private Long adOrgId;
    private String adOrgName;
    private Long signImageIndex;
    private boolean isPublicText;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getJobTile() {
        return jobTile;
    }

    public void setJobTile(String jobTile) {
        this.jobTile = jobTile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAdOrgId() {
        return adOrgId;
    }

    public void setAdOrgId(Long adOrgId) {
        this.adOrgId = adOrgId;
    }

    public String getAdOrgName() {
        return adOrgName;
    }

    public void setAdOrgName(String adOrgName) {
        this.adOrgName = adOrgName;
    }

    public Long getSignImageIndex() {
        return signImageIndex;
    }

    public void setSignImageIndex(Long signImageIndex) {
        this.signImageIndex = signImageIndex;
    }

    public boolean isIsPublicText() {
        return isPublicText;
    }

    public void setIsPublicText(boolean isPublicText) {
        this.isPublicText = isPublicText;
    }

}
