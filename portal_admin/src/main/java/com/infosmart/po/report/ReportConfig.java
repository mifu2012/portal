package com.infosmart.po.report;

import java.util.ArrayList;
import java.util.List;

import com.infosmart.po.DimensionDetail;
import com.infosmart.po.DimensionDetailSec;
import com.infosmart.po.Page;

/**
 * 报表查询数据配置
 * 
 * @author infosmart
 * 
 */
public class ReportConfig {

	private Integer configId;// 主键ID,
	private Integer reportId;// 关联ID,
	private String columnLabel;// 页面显示名称,
	private Integer controlType;// 控件类型,
	private String code;// 维度code,
	private String defaultValue;// 默认值,
	private String defaultValueSec;//二级维度值
	private String columnCode;// 字段
	private String columnTypeName;// 字段类型描述
	private String columnClassName;// java字段类型
	private Integer columnSort;// '序号',
	private String dateFormat;// '日期格式',
	private Integer columnType;
	private Page page;
	private String codeName;// 维度中文名称
    private String codeNameSec;//二级维度名称
    
    /**
     * 绑定字段（用于下钻）
     */
    private String bandColumnField;
    
	public String getBandColumnField() {
		return bandColumnField;
	}

	public void setBandColumnField(String bandColumnField) {
		this.bandColumnField = bandColumnField;
	}

	public String getCodeNameSec() {
		return codeNameSec;
	}

	public void setCodeNameSec(String codeNameSec) {
		this.codeNameSec = codeNameSec;
	}

	public String getDefaultValueSec() {
		return defaultValueSec;
	}

	public void setDefaultValueSec(String defaultValueSec) {
		this.defaultValueSec = defaultValueSec;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	// 维度(下拉)明细列表
	private List<DimensionDetail> dimensionDetailList = new ArrayList<DimensionDetail>();
    private List<DimensionDetailSec> dimensionDetailSecList=new ArrayList<DimensionDetailSec>();
    
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

	public Integer getColumnType() {
		return columnType;
	}

	public void setColumnType(Integer columnType) {
		this.columnType = columnType;
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

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
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
