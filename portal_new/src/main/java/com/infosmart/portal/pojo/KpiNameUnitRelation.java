package com.infosmart.portal.pojo;

/**
 * 通用指标单位关系模型
 * 
 * @author gentai.huang
 * 
 */
public class KpiNameUnitRelation {

	/**
	 * 通用指标名称
	 */
	private String kpiName;

	/**
	 * 单位
	 */
	private String unit;

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

}
