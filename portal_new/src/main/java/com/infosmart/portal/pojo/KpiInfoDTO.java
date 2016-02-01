package com.infosmart.portal.pojo;

import java.util.Date;

public class KpiInfoDTO {

	private static final long serialVersionUID = 741231858441822688L;

	// ========== properties ==========

	/**
	 * This property corresponds to db column <tt>KPI_CODE</tt>.
	 */
	private String kpiCode;

	/**
	 * This property corresponds to db column <tt>PARENT_CODE</tt>.
	 */
	private String parentCode;

	/**
	 * This property corresponds to db column <tt>KPI_NAME</tt>.
	 */
	private String kpiName;

	/**
	 * This property corresponds to db column <tt>DISP_NAME</tt>.
	 */
	private String dispName;

	/**
	 * This property corresponds to db column <tt>KPI_TYPE</tt>.
	 */
	private String kpiType;

	/**
	 * This property corresponds to db column <tt>IS_SHOW</tt>.
	 */
	private String isShow;

	/**
	 * This property corresponds to db column <tt>SHOW_ORDER</tt>.
	 */
	private int showOrder;

	/**
	 * This property corresponds to db column <tt>IS_AVERAGE</tt>.
	 */
	private String isAverage;

	/**
	 * This property corresponds to db column <tt>IS_MAX</tt>.
	 */
	private String isMax;

	/**
	 * This property corresponds to db column <tt>IS_VARIATION</tt>.
	 */
	private String isVariation;

	/**
	 * This property corresponds to db column <tt>IS_PERCENT</tt>.
	 */
	private String isPercent;

	/**
	 * This property corresponds to db column <tt>UNIT</tt>.
	 */
	private String unit;

	/**
	 * This property corresponds to db column <tt>DECIMAL_NUM</tt>.
	 */
	private int decimalNum;

	/**
	 * This property corresponds to db column <tt>CONVERT_NUM</tt>.
	 */
	private int convertNum;

	/**
	 * This property corresponds to db column <tt>CONVERT_TYPE</tt>.
	 */
	private String convertType;

	/**
	 * This property corresponds to db column <tt>IS_CAL_KPI</tt>.
	 */
	private String isCalKpi;

	/**
	 * This property corresponds to db column <tt>ROLE_FORMULA</tt>.
	 */
	private String roleFormula;

	/**
	 * This property corresponds to db column <tt>IS_USE</tt>.
	 */
	private String isUse;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;

	// ========== getters and setters ==========

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
	 * Getter method for property <tt>parentCode</tt>.
	 * 
	 * @return property value of parentCode
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * Setter method for property <tt>parentCode</tt>.
	 * 
	 * @param parentCode
	 *            value to be assigned to property parentCode
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	/**
	 * Getter method for property <tt>kpiName</tt>.
	 * 
	 * @return property value of kpiName
	 */
	public String getKpiName() {
		return kpiName;
	}

	/**
	 * Setter method for property <tt>kpiName</tt>.
	 * 
	 * @param kpiName
	 *            value to be assigned to property kpiName
	 */
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	/**
	 * Getter method for property <tt>dispName</tt>.
	 * 
	 * @return property value of dispName
	 */
	public String getDispName() {
		return dispName;
	}

	/**
	 * Setter method for property <tt>dispName</tt>.
	 * 
	 * @param dispName
	 *            value to be assigned to property dispName
	 */
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}

	/**
	 * Getter method for property <tt>kpiType</tt>.
	 * 
	 * @return property value of kpiType
	 */
	public String getKpiType() {
		return kpiType;
	}

	/**
	 * Setter method for property <tt>kpiType</tt>.
	 * 
	 * @param kpiType
	 *            value to be assigned to property kpiType
	 */
	public void setKpiType(String kpiType) {
		this.kpiType = kpiType;
	}

	/**
	 * Getter method for property <tt>isShow</tt>.
	 * 
	 * @return property value of isShow
	 */
	public String getIsShow() {
		return isShow;
	}

	/**
	 * Setter method for property <tt>isShow</tt>.
	 * 
	 * @param isShow
	 *            value to be assigned to property isShow
	 */
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	/**
	 * Getter method for property <tt>showOrder</tt>.
	 * 
	 * @return property value of showOrder
	 */
	public int getShowOrder() {
		return showOrder;
	}

	/**
	 * Setter method for property <tt>showOrder</tt>.
	 * 
	 * @param showOrder
	 *            value to be assigned to property showOrder
	 */
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	/**
	 * Getter method for property <tt>isAverage</tt>.
	 * 
	 * @return property value of isAverage
	 */
	public String getIsAverage() {
		return isAverage;
	}

	/**
	 * Setter method for property <tt>isAverage</tt>.
	 * 
	 * @param isAverage
	 *            value to be assigned to property isAverage
	 */
	public void setIsAverage(String isAverage) {
		this.isAverage = isAverage;
	}

	/**
	 * Getter method for property <tt>isMax</tt>.
	 * 
	 * @return property value of isMax
	 */
	public String getIsMax() {
		return isMax;
	}

	/**
	 * Setter method for property <tt>isMax</tt>.
	 * 
	 * @param isMax
	 *            value to be assigned to property isMax
	 */
	public void setIsMax(String isMax) {
		this.isMax = isMax;
	}

	/**
	 * Getter method for property <tt>isVariation</tt>.
	 * 
	 * @return property value of isVariation
	 */
	public String getIsVariation() {
		return isVariation;
	}

	/**
	 * Setter method for property <tt>isVariation</tt>.
	 * 
	 * @param isVariation
	 *            value to be assigned to property isVariation
	 */
	public void setIsVariation(String isVariation) {
		this.isVariation = isVariation;
	}

	/**
	 * Getter method for property <tt>isPercent</tt>.
	 * 
	 * @return property value of isPercent
	 */
	public String getIsPercent() {
		return isPercent;
	}

	/**
	 * Setter method for property <tt>isPercent</tt>.
	 * 
	 * @param isPercent
	 *            value to be assigned to property isPercent
	 */
	public void setIsPercent(String isPercent) {
		this.isPercent = isPercent;
	}

	/**
	 * Getter method for property <tt>unit</tt>.
	 * 
	 * @return property value of unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Setter method for property <tt>unit</tt>.
	 * 
	 * @param unit
	 *            value to be assigned to property unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Getter method for property <tt>decimalNum</tt>.
	 * 
	 * @return property value of decimalNum
	 */
	public int getDecimalNum() {
		return decimalNum;
	}

	/**
	 * Setter method for property <tt>decimalNum</tt>.
	 * 
	 * @param decimalNum
	 *            value to be assigned to property decimalNum
	 */
	public void setDecimalNum(int decimalNum) {
		this.decimalNum = decimalNum;
	}

	/**
	 * Getter method for property <tt>convertNum</tt>.
	 * 
	 * @return property value of convertNum
	 */
	public int getConvertNum() {
		return convertNum;
	}

	/**
	 * Setter method for property <tt>convertNum</tt>.
	 * 
	 * @param convertNum
	 *            value to be assigned to property convertNum
	 */
	public void setConvertNum(int convertNum) {
		this.convertNum = convertNum;
	}

	/**
	 * Getter method for property <tt>convertType</tt>.
	 * 
	 * @return property value of convertType
	 */
	public String getConvertType() {
		return convertType;
	}

	/**
	 * Setter method for property <tt>convertType</tt>.
	 * 
	 * @param convertType
	 *            value to be assigned to property convertType
	 */
	public void setConvertType(String convertType) {
		this.convertType = convertType;
	}

	/**
	 * Getter method for property <tt>isCalKpi</tt>.
	 * 
	 * @return property value of isCalKpi
	 */
	public String getIsCalKpi() {
		return isCalKpi;
	}

	/**
	 * Setter method for property <tt>isCalKpi</tt>.
	 * 
	 * @param isCalKpi
	 *            value to be assigned to property isCalKpi
	 */
	public void setIsCalKpi(String isCalKpi) {
		this.isCalKpi = isCalKpi;
	}

	/**
	 * Getter method for property <tt>roleFormula</tt>.
	 * 
	 * @return property value of roleFormula
	 */
	public String getRoleFormula() {
		return roleFormula;
	}

	/**
	 * Setter method for property <tt>roleFormula</tt>.
	 * 
	 * @param roleFormula
	 *            value to be assigned to property roleFormula
	 */
	public void setRoleFormula(String roleFormula) {
		this.roleFormula = roleFormula;
	}

	/**
	 * Getter method for property <tt>isUse</tt>.
	 * 
	 * @return property value of isUse
	 */
	public String getIsUse() {
		return isUse;
	}

	/**
	 * Setter method for property <tt>isUse</tt>.
	 * 
	 * @param isUse
	 *            value to be assigned to property isUse
	 */
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	/**
	 * Getter method for property <tt>gmtCreate</tt>.
	 * 
	 * @return property value of gmtCreate
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * Setter method for property <tt>gmtCreate</tt>.
	 * 
	 * @param gmtCreate
	 *            value to be assigned to property gmtCreate
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/**
	 * Getter method for property <tt>gmtModified</tt>.
	 * 
	 * @return property value of gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 * Setter method for property <tt>gmtModified</tt>.
	 * 
	 * @param gmtModified
	 *            value to be assigned to property gmtModified
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Override
	public String toString() {
		return this.kpiCode;
	}
}
