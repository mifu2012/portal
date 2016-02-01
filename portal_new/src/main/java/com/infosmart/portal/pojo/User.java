package com.infosmart.portal.pojo;

import java.util.Date;

/**
 * 用户信息
 * 
 * 
 */
public class User {
	private Integer userId;
	private String loginName;
	private String passWord;
	private String userName;
	private String rights;
	private Integer status;
	private Integer roleId;
	private Integer templateId;
	private Date lastLogin;
	private String dwmisRights;
	private String dwpasRights;
	private String reportRights;
	private String selfRights;

	private Role role;// 权限

	public User() {
	}

	public User(Integer userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}

	public User(Integer userId, String loginName, String userName) {
		super();
		this.userId = userId;
		this.loginName = loginName;
		this.userName = userName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getSelfRights() {
		return selfRights;
	}

	public void setSelfRights(String selfRights) {
		this.selfRights = selfRights;
	}

	public String getDwmisRights() {
		if (dwmisRights == null || dwmisRights.length() == 0)
			dwmisRights = "0";
		return dwmisRights;
	}

	public void setDwmisRights(String dwmisRights) {
		this.dwmisRights = dwmisRights;
	}


	public String getDwpasRights() {
		if (dwpasRights == null) {
			dwpasRights = "0";
		}
		return dwpasRights;
	}

	public void setDwpasRights(String dwpasRights) {
		this.dwpasRights = dwpasRights;
	}

	public String getReportRights() {
		if (reportRights == null || reportRights.length() == 0)
			reportRights = "0";
		return reportRights;
	}

	public void setReportRights(String reportRights) {
		this.reportRights = reportRights;
	}

}
