package com.infosmart.po;

import java.math.BigDecimal;

import com.infosmart.util.StringUtils;

public class DwpasDepartmentKpiInfo {
	private String id;
	private String deptId;
	private String deptName;
	private String kpiCode;
	private String kpiName;
	private String unit;
	private BigDecimal kpiFulfillValue;
	private BigDecimal kpiTaskValue;
	private Page page;
	private String reportDate;

	private String reportDateDesc;

	public String getReportDateDesc() {
		if (StringUtils.notNullAndSpace(reportDate)) {
			if (reportDate.indexOf("-") == -1) {
				if (this.reportDate.length() == 6) {
					this.reportDateDesc = this.reportDate.substring(0, 4) + "-"
							+ this.reportDate.substring(4, 6);
				} else {
					this.reportDateDesc = this.reportDate;
				}

			}
		}
		return reportDateDesc;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
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
