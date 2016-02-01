package com.infosmart.portal.pojo;

import java.math.BigDecimal;

public class UserExperienceDataInfo {
	private String columnCode;
	private String KpiCode;
	private String comCode;
	private BigDecimal baseValue;
	public String getColumnCode() {
		return columnCode;
	}
	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}
	public String getKpiCode() {
		return KpiCode;
	}
	public void setKpiCode(String kpiCode) {
		KpiCode = kpiCode;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public BigDecimal getBaseValue() {
		return baseValue;
	}
	public void setBaseValue(BigDecimal baseValue) {
		this.baseValue = baseValue;
	}

}
