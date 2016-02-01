package com.infosmart.portal.vo;

import java.math.BigDecimal;

/**
 * 矩形图数据
 * 
 * @author infosmart
 * 
 */
public class RectangularData {
	// 矩形名称
	private String chartName;
	// 矩形的值
	private BigDecimal chartValue;

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public BigDecimal getChartValue() {
		return chartValue;
	}

	public void setChartValue(BigDecimal chartValue) {
		this.chartValue = chartValue;
	}

}