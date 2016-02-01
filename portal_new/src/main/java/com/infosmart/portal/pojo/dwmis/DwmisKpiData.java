package com.infosmart.portal.pojo.dwmis;

import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.NumberFormatter;
import com.infosmart.portal.util.dwmis.UnitTransformer;

/**
 * 指标数据
 * 
 * @author infosmart
 * 
 */
public class DwmisKpiData {
	private BigInteger kpiId;
	// 指标编码
	private String kpiCode;
	// 指标信息
	private DwmisKpiInfo dwmisKpiInfo;
	// 统计日期
	private Date reportDate;
	/**
	 * 编码：小时1001、日1002、周1003、月1004、季1005、年1006 select * from MIS_TYPE mt where
	 * mt.group_id=1000 1002 1000 日 1003 1000 周 1004 1000 月 1005 1000 季
	 */
	private int dateType;
	/**
	 * 日统计指标
	 */
	public static final String DATE_TYPE_OF_DAY = "1002";
	/**
	 * 周统计指标
	 */
	public static final String DATE_TYPE_OF_WEEK = "1003";
	/**
	 * 月统计指标
	 */
	public static final String DATE_TYPE_OF_MONTH = "1004";

	/**
	 * 编码：当期值2001、期末值2002、峰值2003、谷值2004、周日平均2005、某月四周日平均2006，2007当年？ select *
	 * from MIS_TYPE mt where mt.group_id=2000;
	 */
	private int staCode;
	/**
	 * 默认统计值
	 */
	public static final String DEFAULT_STATISTICS = "2001";

	private BigDecimal value;

	// 上周值
	private BigDecimal lastWeekValue;

	private double showValue;

	private String kpiName;

	private String showDate;
	// 详情分析日期
	private String showReportDate;
	// 详情分析数据值
	private String showKpiValue;

	/** 分页 */
	private Page page;

	public Page getPage() {
		if (page == null) {
			page = new Page();
		}
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getShowReportDate() {
		showReportDate = DateUtils.fotmatDate2(reportDate);
		return showReportDate;
	}

	public void setShowReportDate(String showReportDate) {
		this.showReportDate = showReportDate;
	}

	public String getShowKpiValue() {
		showKpiValue = NumberFormatter.format(value, 2);

		return showKpiValue;
	}

	public void setShowKpiValue(String showKpiValue) {
		this.showKpiValue = showKpiValue;
	}

	public BigDecimal getLastWeekValue() {
		return lastWeekValue;
	}

	public void setLastWeekValue(BigDecimal lastWeekValue) {
		this.lastWeekValue = lastWeekValue;
	}

	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public double getShowValue() {
		if (this.dwmisKpiInfo != null && this.value != null) {
			BigDecimal tempValue = this.value.setScale(
					dwmisKpiInfo.getDecimalNum(), BigDecimal.ROUND_HALF_UP);
			return UnitTransformer.transform(tempValue.doubleValue(),
					dwmisKpiInfo.getSizeId(), dwmisKpiInfo);
		}
		return showValue;
	}

	public void setShowValue(double showValue) {
		this.showValue = showValue;
	}

	public BigInteger getKpiId() {
		return kpiId;
	}

	public void setKpiId(BigInteger kpiId) {
		this.kpiId = kpiId;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public DwmisKpiInfo getDwmisKpiInfo() {
		return dwmisKpiInfo;
	}

	public void setDwmisKpiInfo(DwmisKpiInfo dwmisKpiInfo) {
		this.dwmisKpiInfo = dwmisKpiInfo;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public int getDateType() {
		return dateType;
	}

	public void setDateType(int dateType) {
		this.dateType = dateType;
	}

	public int getStaCode() {
		return staCode;
	}

	public void setStaCode(int staCode) {
		this.staCode = staCode;
	}

}
