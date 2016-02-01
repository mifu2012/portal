package com.infosmart.po.report;

/**
 * 报表字段
 * 
 * @author infosmart
 * 
 */
public class ReportField {
	private String columnCode;// 字段
	private String columnFullCode;// 全拼
	private int columnType;// 类型
	private String columnTypeName;// 字段类型描述
	private String columnClassName;// java字段类型
	private String columnLabel;// 页面显示名称
	private int columnScale;// 字段精度

	// 控件类型 0：输入框 1:整数型 2:小数型 3:日期型 4:下拉型
	private int inputType = 0;

	/*
	 * public int getInputType() { if (this.getColumnClassName() != null) { if
	 * (this.getColumnClassName().equalsIgnoreCase("java.lang.String")) { // 字符型
	 * inputType = 0; } else if (this.getColumnClassName().equalsIgnoreCase(
	 * "java.sql.Date") || this.getColumnClassName().equalsIgnoreCase(
	 * "java.sql.Timestamp")) { // 日期型 inputType = 3; } else if
	 * (this.getColumnClassName().equalsIgnoreCase( "java.lang.Integer") ||
	 * this.getColumnClassName().equalsIgnoreCase( "java.lang.Long")) { // 整数型
	 * inputType = 1; } else { // 小数型 inputType = 2; } } return inputType; }
	 */
	public int getInputType() {
		if (this.getColumnType() == 12 || this.getColumnType() == 1
				|| this.getColumnType() == -1 || this.getColumnType() == -4) {
			// 字符型
			inputType = 0;
		} else if (91 <= this.getColumnType() && this.getColumnType() <= 93) {
			// 日期型
			inputType = 3;
		} else if (this.getColumnType() == 4 || this.getColumnType() == -5
				|| this.getColumnType() == -6) {
			// 整数型
			inputType = 1;
		} else {
			// 小数型
			inputType = 2;
		}
		return inputType;
	}

	public String getColumnFullCode() {
		return columnFullCode;
	}

	public void setColumnFullCode(String columnFullCode) {
		this.columnFullCode = columnFullCode;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public int getColumnType() {
		return columnType;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	public String getColumnClassName() {
		return columnClassName;
	}

	public void setColumnClassName(String columnClassName) {
		this.columnClassName = columnClassName;
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}

	public int getColumnScale() {
		return columnScale;
	}

	public void setColumnScale(int columnScale) {
		this.columnScale = columnScale;
	}
}
