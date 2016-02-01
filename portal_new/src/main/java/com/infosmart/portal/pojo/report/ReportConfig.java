package com.infosmart.portal.pojo.report;

import java.util.ArrayList;
import java.util.List;

import com.infosmart.portal.util.StringUtils;

/**
 * 报表数据配置
 * 
 * @author hgt
 * 
 */
public class ReportConfig {

	private Integer configId;// 主键ID,
	private Integer reportId;// 关联ID,
	private String columnLabel;// 页面显示名称,
	private Integer controlType;// 控件类型,
	private String code;// 维度code,
	private String defaultValue;// 默认值,
	private String defaultValueSec;// 二级维度值
	private String columnCode;// 字段
	private String columnShortCode;
	private int columnType;
	private String columnTypeName;// 字段类型描述
	private String columnClassName;// java字段类型
	private Integer columnSort;// '序号',
	private String dateFormat;// '日期格式'

	private int isDataColumn;
	private int isQueryColumn;

	// 控件类型 0：输入框 1:整数型 2:小数型 3:日期型 4:下拉型
	private int inputType = 0;

	public String getColumnShortCode() {
		if (StringUtils.notNullAndSpace(columnCode)
				&& columnCode.indexOf(".") != -1) {
			columnShortCode = columnCode.substring(columnCode.indexOf(".") + 1);
		}
		return columnShortCode;
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
				inputType = 3;
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

	public int getColumnType() {
		return columnType;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	// 维度(下拉)明细列表
	private List<DimensionDetail> dimensionDetailList = new ArrayList<DimensionDetail>();
	private List<DimensionDetailSec> dimensionDetailSecList = new ArrayList<DimensionDetailSec>();

	public String getDefaultValueSec() {
		return defaultValueSec;
	}

	public void setDefaultValueSec(String defaultValueSec) {
		this.defaultValueSec = defaultValueSec;
	}

	public List<DimensionDetailSec> getDimensionDetailSecList() {
		return dimensionDetailSecList;
	}

	public void setDimensionDetailSecList(
			List<DimensionDetailSec> dimensionDetailSecList) {
		this.dimensionDetailSecList = dimensionDetailSecList;
	}

	public List<DimensionDetail> getDimensionDetailList() {
		return dimensionDetailList;
	}

	public void setDimensionDetailList(List<DimensionDetail> dimensionDetailList) {
		this.dimensionDetailList = dimensionDetailList;
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
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

	public Integer getColumnSort() {
		return columnSort;
	}

	public void setColumnSort(Integer columnSort) {
		this.columnSort = columnSort;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Integer getControlType() {
		return controlType;
	}

	public void setControlType(Integer controlType) {
		this.controlType = controlType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
