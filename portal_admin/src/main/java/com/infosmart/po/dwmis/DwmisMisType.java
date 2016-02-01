package com.infosmart.po.dwmis;

/**
 * dwmis_mis_type表
 * 
 * @author Administrator
 * 
 */
public class DwmisMisType {

	private int typeId;
	private int groupId;
	private String typeName;
	private String detail;

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
