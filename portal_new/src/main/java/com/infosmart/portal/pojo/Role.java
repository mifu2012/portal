package com.infosmart.portal.pojo;

public class Role {
	private Integer roleId;
	private String roleName;
	private String rights;
	private String dwmisRights;
	private String dwpasRights;
	private String reportRights;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRights() {
		return rights;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	public String getDwmisRights() {
		if(dwmisRights==null||dwmisRights.length()==0){
			dwmisRights="0";
		}
		return dwmisRights;
	}
	public void setDwmisRights(String dwmisRights) {
		this.dwmisRights = dwmisRights;
	}
	public String getDwpasRights() {
		if(dwpasRights==null){
			dwpasRights="0";
		}
		return dwpasRights;
	}
	public void setDwpasRights(String dwpasRights) {
		this.dwpasRights = dwpasRights;
	}
	public String getReportRights() {
		if(reportRights==null||reportRights.length()==0){
			reportRights="0";
		}
		return reportRights;
	}
	public void setReportRights(String reportRights) {
		this.reportRights = reportRights;
	}
	
}
