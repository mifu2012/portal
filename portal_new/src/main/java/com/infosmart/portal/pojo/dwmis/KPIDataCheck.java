package com.infosmart.portal.pojo.dwmis;

/**
 * KPI各字段为空的统计对象
 * 
 * @author infosmart
 * 
 */
public class KPIDataCheck {
	/**
	 * KPI数据表的总记录数
	 */
	private int totalRecord;

	/**
	 * 数据时间为空的记录条数
	 */
	private int reportDateNullCount;

	/**
	 * 数据值为空的记录条数
	 */
	private int valueNullCount;

	/**
	 * 时间粒度为空的记录条数
	 */
	private int dateTypeNullCount;

	/**
	 * 统计方式为空的记录条数
	 */
	private int staCodeNullCount;

	/**
	 * KPICODE为空的记录条数
	 */
	private int kpiCodeNullCount;

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getReportDateNullCount() {
		return reportDateNullCount;
	}

	public void setReportDateNullCount(int reportDateNullCount) {
		this.reportDateNullCount = reportDateNullCount;
	}

	public int getValueNullCount() {
		return valueNullCount;
	}

	public void setValueNullCount(int valueNullCount) {
		this.valueNullCount = valueNullCount;
	}

	public int getDateTypeNullCount() {
		return dateTypeNullCount;
	}

	public void setDateTypeNullCount(int dateTypeNullCount) {
		this.dateTypeNullCount = dateTypeNullCount;
	}

	public int getStaCodeNullCount() {
		return staCodeNullCount;
	}

	public void setStaCodeNullCount(int staCodeNullCount) {
		this.staCodeNullCount = staCodeNullCount;
	}

	public int getKpiCodeNullCount() {
		return kpiCodeNullCount;
	}

	public void setKpiCodeNullCount(int kpiCodeNullCount) {
		this.kpiCodeNullCount = kpiCodeNullCount;
	}
}
