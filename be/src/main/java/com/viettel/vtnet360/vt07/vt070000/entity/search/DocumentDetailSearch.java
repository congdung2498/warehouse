package com.viettel.vtnet360.vt07.vt070000.entity.search;

import com.viettel.vtnet360.common.security.BaseEntity;

public class DocumentDetailSearch extends BaseEntity {
	private int docId;
	private String docName;
	private String constractionName;
	private String contracName;
	private String packageName;
	private String projectName;
	private String levelBaoMat;
	private String path;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getConstractionName() {
		return constractionName;
	}

	public void setConstractionName(String constractionName) {
		this.constractionName = constractionName;
	}

	public String getContracName() {
		return contracName;
	}

	public void setContracName(String contracName) {
		this.contracName = contracName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getLevelBaoMat() {
		return levelBaoMat;
	}

	public void setLevelBaoMat(String levelBaoMat) {
		this.levelBaoMat = levelBaoMat;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
