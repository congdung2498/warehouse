package com.viettel.vtnet360.vt05.vt050002.entity;

public class Role {
	private String roleId;
	private String roleName;
	private String jobCode;

	public Role() {
	}

	public Role(String roleId, String roleName, String jobCode) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.jobCode = jobCode;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

}
