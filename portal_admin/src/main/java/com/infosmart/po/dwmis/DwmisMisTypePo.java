package com.infosmart.po.dwmis;

public class DwmisMisTypePo {
	/** 类型ID */
	private Integer typeId;
	/** 所属类型组ID */
	private String groupId;
	/** 类型名称 */
	private String typeName;
	/** 类型描述 */
	private String detail;
	/** 事记类别 */
	private int eventType;

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
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
