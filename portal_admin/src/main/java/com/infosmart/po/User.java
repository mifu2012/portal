package com.infosmart.po;

import java.util.Date;

public class User {
	private Integer userId;
	private String loginname;
	private String username;
	private String password;
	private String rights;
	private Integer status;
	private Integer roleId;
	private Date lastLogin;
	private Role role;
	private Page page;
	private Date lastLoginStart;
	private Date lastLoginEnd;
	private String reportRights;
	private String dwpasRights;
	private String dwmisRights;
	private String selfRights;
	
	private String[] dwpasRightList;
	

	public void setDwpasRightList(String[] dwpasRightList) {
		this.dwpasRightList = dwpasRightList;
	}

	public String[] getDwpasRightList() {
		if(!"0".equals(dwpasRights) && dwpasRights!=null && dwpasRights.length()>0){
			dwpasRightList = dwpasRights.split(",");
		}
		return dwpasRightList;
	}

	

	public User() {

	}

	public User(Integer userId, String loginname, String username) {
		super();
		this.userId = userId;
		this.loginname = loginname;
		this.username = username;
	}

	public String getSelfRights() {
		return selfRights;
	}

	public void setSelfRights(String selfRights) {
		this.selfRights = selfRights;
	}

	public String getReportRights() {
		return reportRights;
	}

	public void setReportRights(String reportRights) {
		this.reportRights = reportRights;
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

	public String getDwmisRights() {
		return dwmisRights;
	}

	public void setDwmisRights(String dwmisRights) {
		this.dwmisRights = dwmisRights;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getLastLoginStart() {
		return lastLoginStart;
	}

	public void setLastLoginStart(Date lastLoginStart) {
		this.lastLoginStart = lastLoginStart;
	}

	public Date getLastLoginEnd() {
		return lastLoginEnd;
	}

	public void setLastLoginEnd(Date lastLoginEnd) {
		this.lastLoginEnd = lastLoginEnd;
	}

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
