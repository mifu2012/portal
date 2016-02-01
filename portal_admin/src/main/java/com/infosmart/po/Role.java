package com.infosmart.po;

public class Role {
	private Integer roleId;
	private String roleName;
	private String rights;
	private String reportRights;
	private String dwpasRights;
	private String dwmisRights;
	
	//
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
	
}
