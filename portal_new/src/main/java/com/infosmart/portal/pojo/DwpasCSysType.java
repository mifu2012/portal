package com.infosmart.portal.pojo;

import java.util.Date;

/**
 * 系统指标信息
 * 对应表:dwpas_c_sys_type
 * @author Administrator
 * 
 */
public class DwpasCSysType {

	/**
	 * 类型ID
	 */
	private String typeId;

	/**
	 * 所属类型组
	 */
	private String groupId;

	/**
	 * 类型名称
	 */
	private String typeName;

	/**
	 * 类型描述
	 */
	private String detail;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 最后修改时间
	 */
	private Date gmtModified;

	/**
	 * 是否已被选
	 */
	private boolean marked;

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

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

}
