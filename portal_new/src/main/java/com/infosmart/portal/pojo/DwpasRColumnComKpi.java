package com.infosmart.portal.pojo;

import java.util.Date;

/**
 * 栏目信息
 * 
 * @author infosmart
 * 
 */
public class DwpasRColumnComKpi {
	private String columnId;
	private String columnCode;
	private String columnName;
	private String comKpiCode;
	private String comKpiName;
	private String userType;
	private String value1;
	private String value2;
	private String value3;
	private Date gmtCreate;

	public String getComKpiName() {
		return comKpiName;
	}

	public void setComKpiName(String comKpiName) {
		this.comKpiName = comKpiName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
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

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
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