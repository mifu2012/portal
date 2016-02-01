package com.infosmart.po.report;

public class GroupingField {
	//分组字段名
	private String fieldName;
	//是否数据列
	private int isDataColumn;
	
	public int getIsDataColumn() {
		return isDataColumn;
	}
	public void setIsDataColumn(int isDataColumn) {
		this.isDataColumn = isDataColumn;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
