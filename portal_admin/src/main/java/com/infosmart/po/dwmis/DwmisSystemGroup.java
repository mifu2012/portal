package com.infosmart.po.dwmis;

import java.util.List;

public class DwmisSystemGroup {
	private Integer groupId;
	private String groupName;
	private List<DwmisSystemParamType> dwmisSystemParamTypeList;
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<DwmisSystemParamType> getDwmisSystemParamTypeList() {
		return dwmisSystemParamTypeList;
	}
	public void setDwmisSystemParamTypeList(
			List<DwmisSystemParamType> dwmisSystemParamTypeList) {
		this.dwmisSystemParamTypeList = dwmisSystemParamTypeList;
	}

}
