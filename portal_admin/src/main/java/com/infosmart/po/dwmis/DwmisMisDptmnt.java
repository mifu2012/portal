package com.infosmart.po.dwmis;

import com.infosmart.po.Page;

/**
 * DWMIS_MIS_DPTMNT 表
 * 
 * @author Administrator
 * 
 */

public class DwmisMisDptmnt {
	/**
	 * 部门ID
	 * */
	private String depId;
	/**
	 * 部门名称
	 * */
	private String depName;
	/**
	 * 部门序号
	 */
	private int depOrder;
	/**
	 * 部门组
	 */
	private int depGroupId;
	/**
	 * 用于分页
	 */

	private Page page;
	/**
	 * 部门分类
	 */
	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public int getDepOrder() {
		return depOrder;
	}

	public void setDepOrder(int depOrder) {
		this.depOrder = depOrder;
	}

	public int getDepGroupId() {
		return depGroupId;
	}

	public void setDepGroupId(int depGroupId) {
		this.depGroupId = depGroupId;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
