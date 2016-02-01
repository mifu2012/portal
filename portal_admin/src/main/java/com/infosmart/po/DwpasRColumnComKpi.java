package com.infosmart.po;

import java.util.Date;

public class DwpasRColumnComKpi {
	private String columnId;
	private String value1;
	private String value2 = "-1";
	private String value3;
	private String userType;
	private String comKpiCode;
	private Date gmtCreate;
	private String newColumnId;// ;临时字段

	private Integer orderNum; // 插入获取通用指标排序用

	public DwpasRColumnComKpi() {
	}

	public DwpasRColumnComKpi(String columnId, String comKpiCode,
			Integer orderNum) {
		super();
		this.columnId = columnId;
		this.comKpiCode = comKpiCode;
		this.orderNum = orderNum;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getNewColumnId() {
		return newColumnId;
	}

	public void setNewColumnId(String newColumnId) {
		this.newColumnId = newColumnId;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getComKpiCode() {
		return comKpiCode;
	}

	public void setComKpiCode(String comKpiCode) {
		this.comKpiCode = comKpiCode;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
}
