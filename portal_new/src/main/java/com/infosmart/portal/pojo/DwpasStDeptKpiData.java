package com.infosmart.portal.pojo;

import java.math.BigDecimal;

public class DwpasStDeptKpiData {
	private String id;
	private String deptId;
	private String deptName;
	private String kpiCode;
	private String kpiName;
	private String unit;
	private BigDecimal kpiFulfillValue;
	private BigDecimal kpiTaskValue;
	private String reportDate;
	private double completeRate;

	public double getCompleteRate() {
		if (this.kpiFulfillValue != null && kpiTaskValue != null) {
			BigDecimal val = this.kpiFulfillValue.divide(kpiTaskValue,5,BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(100));
			return val.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getKpiFulfillValue() {
		return kpiFulfillValue;
	}

	public void setKpiFulfillValue(BigDecimal kpiFulfillValue) {
		this.kpiFulfillValue = kpiFulfillValue;
	}

	public BigDecimal getKpiTaskValue() {
		return kpiTaskValue;
	}

	public void setKpiTaskValue(BigDecimal kpiTaskValue) {
		this.kpiTaskValue = kpiTaskValue;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

}
