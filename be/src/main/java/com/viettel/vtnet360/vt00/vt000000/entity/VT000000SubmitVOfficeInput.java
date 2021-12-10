/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.vtnet360.vt00.vt000000.entity;

/**
 *
 * @author KienPK
 */
public class VT000000SubmitVOfficeInput {

    private Long suggestId;
    private String username;
    private String password;
    private String docTitle;
    private String registerNumber;
    private String emailToSearch;
    private String emails;
    private String signImageIndexs;
    private String isPublicTexts;
    private String listSuggestId;
    private Long formOfDocumentId;
    private Long areaOfDocumentId;
    private Long signVofficeId;
	private int typeSign;
    //VIPC fix user with job title START
    private String jobTitles;
//    truongdv
    private Long type;
    private String fileName;
    private int isSign;
    private String transCode;

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
    
//end
    public String getJobTitles() {
        return jobTitles;
    }

    public void setJobTitles(String jobTitles) {
        this.jobTitles = jobTitles;
    }
    //VIPC fix user with job title END
    
    public Long getFormOfDocumentId() {
        return formOfDocumentId;
    }

    public void setFormOfDocumentId(Long formOfDocumentId) {
        this.formOfDocumentId = formOfDocumentId;
    }

    public Long getAreaOfDocumentId() {
        return areaOfDocumentId;
    }

    public void setAreaOfDocumentId(Long areaOfDocumentId) {
        this.areaOfDocumentId = areaOfDocumentId;
    }
    
    public String getListSuggestId() {
        return listSuggestId;
    }

    public void setListSuggestId(String listSuggestId) {
        this.listSuggestId = listSuggestId;
    }
    
    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getSignImageIndexs() {
        return signImageIndexs;
    }

    public void setSignImageIndexs(String signImageIndexs) {
        this.signImageIndexs = signImageIndexs;
    }

    public String getIsPublicTexts() {
        return isPublicTexts;
    }

    public void setIsPublicTexts(String isPublicTexts) {
        this.isPublicTexts = isPublicTexts;
    }

    public String getEmailToSearch() {
        return emailToSearch;
    }

    public void setEmailToSearch(String emailToSearch) {
        this.emailToSearch = emailToSearch;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public Long getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(Long suggestId) {
        this.suggestId = suggestId;
    }

	public Long getSignVofficeId() {
		return signVofficeId;
	}

	public void setSignVofficeId(Long signVofficeId) {
		this.signVofficeId = signVofficeId;
	}

	public int getTypeSign() {
		return typeSign;
	}

	public void setTypeSign(int typeSign) {
		this.typeSign = typeSign;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getIsSign() {
		return isSign;
	}

	public void setIsSign(int isSign) {
		this.isSign = isSign;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public VT000000SubmitVOfficeInput() {
		super();
	}

	public VT000000SubmitVOfficeInput(Long suggestId, String username, String password, String docTitle,
			String registerNumber, String emailToSearch, String emails, String signImageIndexs, String isPublicTexts,
			String listSuggestId, Long formOfDocumentId, Long areaOfDocumentId, String jobTitles, Long type) {
		super();
		this.suggestId = suggestId;
		this.username = username;
		this.password = password;
		this.docTitle = docTitle;
		this.registerNumber = registerNumber;
		this.emailToSearch = emailToSearch;
		this.emails = emails;
		this.signImageIndexs = signImageIndexs;
		this.isPublicTexts = isPublicTexts;
		this.listSuggestId = listSuggestId;
		this.formOfDocumentId = formOfDocumentId;
		this.areaOfDocumentId = areaOfDocumentId;
		this.jobTitles = jobTitles;
		this.type = type;
	}    
}
