package com.infosmart.portal.vo.dwpas;

import java.math.BigDecimal;
import java.util.Date;

import com.infosmart.portal.util.Constants;

public class ProdKpiData {
	private String flashPar;

	/**
	 * 警报阀值
	 */
	private BigDecimal alertValue = Constants.ZERO;
	/**
	 * 基础值
	 */
	private BigDecimal baseValue = Constants.ZERO;

	private String color;

	private boolean hasChild;

	/**
	 * 指标编码
	 */
	private String kpiCode;

	/**
	 * 最大值
	 */
	private BigDecimal maxValue = Constants.ZERO;

	/**
	 * 最小值
	 */
	private BigDecimal minValue = Constants.ZERO;

	/**
	 * 是否超过警报阀值
	 */
	private boolean overAlert;

	/**
	 * 是否超过 预警阀值
	 */
	private boolean overPreAlert;

	/**
	 * 父产品ID、
	 */
	private String parentId;

	/**
	 * 预警阀值
	 */
	private BigDecimal preAlertValue = Constants.ZERO;

	/**
	 * 产品ID、
	 */
	private String productId;

	/**
	 * 产品编码
	 */
	private String productName;

	/**
	 * 数据时间
	 */
	private Date reportDate;

	public ProdKpiData() {

	}

	public ProdKpiData(String productId) {
		super();
		this.productId = productId;
	}

	public String getFlashPar() {
		return flashPar;
	}

	public void setFlashPar(String flashPar) {
		this.flashPar = flashPar;
	}

	public BigDecimal getAlertValue() {
		return alertValue;
	}

	public BigDecimal getBaseValue() {
		return baseValue;
	}

	public String getColor() {
		return color;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public BigDecimal getMaxValue() {
		return maxValue;
	}

	public BigDecimal getMinValue() {
		return minValue;
	}

	public String getParentId() {
		return parentId;
	}

	public BigDecimal getPreAlertValue() {
		return preAlertValue;
	}

	public String getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public boolean isOverAlert() {
		return overAlert;
	}

	public boolean isOverPreAlert() {
		return overPreAlert;
	}

	public void setAlertValue(BigDecimal alertValue) {
		this.alertValue = alertValue;

	}

	public void setBaseValue(BigDecimal baseValue) {
		this.baseValue = baseValue;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}

	public void setOverAlert(boolean overAlert) {
		this.overAlert = overAlert;
	}

	public void setOverPreAlert(boolean overPreAlert) {
		this.overPreAlert = overPreAlert;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setPreAlertValue(BigDecimal preAlertValue) {
		this.preAlertValue = preAlertValue;

	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
}
