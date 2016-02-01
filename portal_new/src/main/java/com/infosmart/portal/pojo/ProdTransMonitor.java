package com.infosmart.portal.pojo;

import java.math.BigDecimal;

public class ProdTransMonitor {
	private String prodId;
	// 产品名称
	private String prodName;
	// 排名变化
	private int rankChange;

	public int getRankChange() {
		return rankChange;
	}

	public void setRankChange(int rankChange) {
		this.rankChange = rankChange;
	}

	// 业务笔数变化率
	private BigDecimal changeRate;
	// 求助率
	private BigDecimal helpCount;
	// 求助率单位
	private String helpCountUnit;
	// 业务笔数峰值
	private BigDecimal topValue;
	// 业务笔数均值
	private BigDecimal avgValue;
	// 业务笔数
	private BigDecimal nowValue;

	// 业务笔数单位
	private String unit;

	// 峰值时间
	private String topTime;

	/**
	 * 
	 * @return
	 */

	// 显示的number号
	private int number;
	// 排名
	private int indexVal;
	// 占比
	private BigDecimal percentage;

	// 占比显示 带%
	private String percentageForDisplay;

	// 排名变化
	private BigDecimal transCount;
	private String trend;

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getTopTime() {
		return topTime;
	}

	public void setTopTime(String topTime) {
		this.topTime = topTime;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public BigDecimal getChangeRate() {
		return changeRate;
	}

	public void setChangeRate(BigDecimal changeRate) {
		this.changeRate = changeRate;
	}

	public BigDecimal getHelpCount() {
		return helpCount;
	}

	public void setHelpCount(BigDecimal helpCount) {
		this.helpCount = helpCount;
	}

	public BigDecimal getTopValue() {
		return topValue;
	}

	public void setTopValue(BigDecimal topValue) {
		this.topValue = topValue;
	}

	public BigDecimal getAvgValue() {
		return avgValue;
	}

	public void setAvgValue(BigDecimal avgValue) {
		this.avgValue = avgValue;
	}

	public BigDecimal getNowValue() {
		return nowValue;
	}

	public void setNowValue(BigDecimal nowValue) {
		this.nowValue = nowValue;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getHelpCountUnit() {
		return helpCountUnit;
	}

	public void setHelpCountUnit(String helpCountUnit) {
		this.helpCountUnit = helpCountUnit;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getIndexVal() {
		return indexVal;
	}

	public void setIndexVal(int indexVal) {
		this.indexVal = indexVal;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public String getPercentageForDisplay() {
		return percentageForDisplay;
	}

	public void setPercentageForDisplay(String percentageForDisplay) {
		this.percentageForDisplay = percentageForDisplay;
	}

	public BigDecimal getTransCount() {
		return transCount;
	}

	public void setTransCount(BigDecimal transCount) {
		this.transCount = transCount;
	}

	public String getTrend() {
		return trend;
	}

	public void setTrend(String trend) {
		this.trend = trend;
	}

	public BigDecimal getNowValueWithOutDefault() {

		return nowValue;
	}
}
