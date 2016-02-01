package com.infosmart.portal.pojo.report;

import com.infosmart.portal.util.StringUtils;

/**
 * 报表列(字段)
 * 
 * @author infosmart
 * 
 */
public class SelfReportColumn {
	private Integer reportId;
	private String columnCode;
	private String columnShortCode;
	private String columnLabel;
	private int columnType;
	private String columnTypeName;
	private String columnClassName;
	private int isDataColumn;
	private int isQueryColumn;
	private int columnSort = 0;
	// 控件类型 0：输入框 1:整数型 2:小数型 3:日期型 4:下拉型
	private int inputType = 0;

	public String getColumnShortCode() {
		if (StringUtils.notNullAndSpace(columnCode)) {
			columnShortCode = columnCode.substring(columnCode.indexOf(".") + 1);
		}
		return columnShortCode;
	}

	public void setColumnShortCode(String columnShortCode) {
		this.columnShortCode = columnShortCode;
	}

	public void setInputType(int inputType) {
		this.inputType = inputType;
	}

	public int getColumnSort() {
		return columnSort;
	}

	public void setColumnSort(int columnSort) {
		this.columnSort = columnSort;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
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

	public int getIsDataColumn() {
		return isDataColumn;
	}

	public void setIsDataColumn(int isDataColumn) {
		this.isDataColumn = isDataColumn;
	}

	public int getIsQueryColumn() {
		return isQueryColumn;
	}

	public void setIsQueryColumn(int isQueryColumn) {
		this.isQueryColumn = isQueryColumn;
	}

	public int getInputType() {
		if (this.getColumnClassName() != null) {
			if (this.getColumnClassName().equalsIgnoreCase("java.lang.String")) {
				// 字符型
				inputType = 0;
			} else if (this.getColumnClassName().equalsIgnoreCase(
					"java.sql.Date")
					|| this.getColumnClassName().equalsIgnoreCase(
							"java.sql.Timestamp")) {
				// 日期型
				inputType = 4;
			} else if (this.getColumnClassName().equalsIgnoreCase(
					"java.lang.Integer")
					|| this.getColumnClassName().equalsIgnoreCase(
							"java.lang.Long")) {
				// 整数型
				inputType = 1;
			} else {
				// 小数型
				inputType = 2;
			}
		}
		return inputType;
	}

}
