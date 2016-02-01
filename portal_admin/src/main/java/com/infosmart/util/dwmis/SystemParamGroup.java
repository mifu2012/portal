package com.infosmart.util.dwmis;


/**
 * 系统类型分组S
 * 
 * @author hgt
 * 
 */
public class SystemParamGroup {
	private int groupId;
	private String groupName;
	private String groupDesc;

	public SystemParamGroup(int groupId, String groupName) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

}
