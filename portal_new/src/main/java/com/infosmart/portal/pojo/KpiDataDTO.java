package com.infosmart.portal.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class KpiDataDTO {

	/** 指标id */
	private long kpiId;

	/** 指标code */
	private String kpiCode;
	/**
	 * 3个预留字段
	 */
	private BigDecimal value1;

	private BigDecimal value2;

	private BigDecimal value3;

	/** 基础值 */
	private BigDecimal baseValue;

	/** 关联的指标信息 */
	private KpiInfoDTO kpiInfo;

	/** 发生时间 */
	private String kpiDate;

	/** 峰值时间 */
	private String gmtMaxTime;

	/** 指标类型,月、日 */
	private String dateType;

	/** 排序 */
	private int sortCnt;

	/** 显示值 */
	private BigDecimal showValue;

	/** 平均值 */
	private BigDecimal averageValue;

	/** 平均值显示 */
	private BigDecimal averageShow;

	/** 最大值 */
	private BigDecimal maxValue;

	/** 最大显示值 */
	private BigDecimal maxShow;

	/** 最小值 */
	private BigDecimal minValue;

	/** 最小显示值 */
	private BigDecimal minShow;

	/** 趋势比 */
	private BigDecimal trendValue;

	/** 占比 */
	private BigDecimal perValue;

	/** 创建之间 */
	private Date gmtCreate;

	/** 修改时间 */
	private Date gmtModified;

	public long getKpiId() {
		return kpiId;
	}

	public void setKpiId(long kpiId) {
		this.kpiId = kpiId;
	}

	public BigDecimal getValue1() {
		return value1;
	}

	public void setValue1(BigDecimal value1) {
		this.value1 = value1;
	}

	public BigDecimal getValue2() {
		return value2;
	}

	public void setValue2(BigDecimal value2) {
		this.value2 = value2;
	}

	public BigDecimal getValue3() {
		return value3;
	}

	public void setValue3(BigDecimal value3) {
		this.value3 = value3;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public int getSortCnt() {
		return sortCnt;
	}

	public void setSortCnt(int sortCnt) {
		this.sortCnt = sortCnt;
	}

	public String getGmtMaxTime() {
		return gmtMaxTime;
	}

	public void setGmtMaxTime(String gmtMaxTime) {
		this.gmtMaxTime = gmtMaxTime;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getKpiDate() {
		return kpiDate;
	}

	public void setKpiDate(String kpiDate) {
		this.kpiDate = kpiDate;
	}

	public BigDecimal getBaseValue() {
		return baseValue;
	}

	public BigDecimal getShowValue() {
		return showValue;
	}

	public BigDecimal getAverageValue() {
		return averageValue;
	}

	public BigDecimal getMaxValue() {
		return maxValue;
	}

	public BigDecimal getMinValue() {
		return minValue;
	}

	public BigDecimal getTrendValue() {
		return trendValue;
	}

	public BigDecimal getPerValue() {
		return perValue;
	}

	public void setBaseValue(BigDecimal baseValue) {
		this.baseValue = baseValue;
	}

	public void setShowValue(BigDecimal showValue) {
		this.showValue = showValue;
	}

	public void setAverageValue(BigDecimal averageValue) {
		this.averageValue = averageValue;
	}

	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}

	public void setTrendValue(BigDecimal trendValue) {
		this.trendValue = trendValue;
	}

	public void setPerValue(BigDecimal perValue) {
		this.perValue = perValue;
	}

	/**
	 * Getter method for property <tt>kpiCode</tt>.
	 * 
	 * @return property value of kpiCode
	 */
	public String getKpiCode() {
		return kpiCode;
	}

	/**
	 * Setter method for property <tt>kpiCode</tt>.
	 * 
	 * @param kpiCode
	 *            value to be assigned to property kpiCode
	 */

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	/**
	 * Getter method for property <tt>kpiInfo</tt>.
	 * 
	 * @return property value of kpiInfo
	 */
	public KpiInfoDTO getKpiInfo() {
		return kpiInfo;
	}

	/**
	 * Setter method for property <tt>kpiInfo</tt>.
	 * 
	 * @param kpiInfo
	 *            value to be assigned to property kpiInfo
	 */
	public void setKpiInfo(KpiInfoDTO kpiInfo) {
		this.kpiInfo = kpiInfo;
	}

	@Override
	public String toString() {
		return super.toString();// this.kpiCode + "-" +
								// this.getKpiInfo().getKpiCode();
	}

	public BigDecimal getAverageShow() {
		return averageShow;
	}

	public void setAverageShow(BigDecimal averageShow) {
		this.averageShow = averageShow;
	}

	public BigDecimal getMaxShow() {
		return maxShow;
	}

	public void setMaxShow(BigDecimal maxShow) {
		this.maxShow = maxShow;
	}

	public BigDecimal getMinShow() {
		return minShow;
	}

	public void setMinShow(BigDecimal minShow) {
		this.minShow = minShow;
	}
}
